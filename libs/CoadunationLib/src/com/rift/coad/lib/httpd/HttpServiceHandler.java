/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  Rift IT Contracting
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * HttpServiceHandler.java
 *
 * This object is responsible for handling the HTTP service requests.
 * It handles both WebService requests and the RMI class loader requests.
 * 
 */

// package path
package com.rift.coad.lib.httpd;

// java imports
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Iterator;
import java.lang.reflect.Constructor;

// logging import
import org.apache.log4j.Logger;

// apache imports
import org.apache.http.HttpServerConnection;
import org.apache.http.protocol.HttpService;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.FileEntity;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

// axis libraries
import org.apache.axis.configuration.EngineConfigurationFactoryFinder;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.AxisEngine;
import org.apache.axis.server.AxisServer;
import org.apache.axis.management.ServiceAdmin;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.handlers.JAXRPCHandler;
import org.w3c.dom.Document;
import org.apache.axis.utils.XMLUtils;
import org.apache.axis.handlers.soap.SOAPService;
import org.apache.axis.providers.java.RPCProvider;
import org.apache.axis.constants.Scope;
import javax.xml.soap.MimeHeader;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPMessage;
import org.apache.axis.Constants;
import org.apache.axis.transport.http.NonBlockingBufferedInputStream;

// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.deployment.webservice.WebServiceConnector;
import com.rift.coad.lib.security.AuthorizationException;
import com.rift.coad.lib.security.UserSession;
import com.rift.coad.lib.security.Validator;
import com.rift.coad.lib.security.login.AuthenticationException;
import com.rift.coad.lib.security.login.SessionLogin;
import com.rift.coad.lib.security.login.handlers.PasswordInfoHandler;
import com.rift.coad.lib.security.sudo.Sudo;
import com.rift.coad.lib.security.sudo.SudoCallbackHandler;
import com.rift.coad.lib.security.user.UserSessionManager;
import com.rift.coad.lib.security.user.UserSessionManagerAccessor;
import com.rift.coad.lib.thread.BasicThread;
import com.rift.coad.lib.thirdparty.axis.AxisManager;
import com.rift.coad.lib.thirdparty.base64.Base64;
import com.rift.coad.lib.webservice.WebServiceWrapper;
import com.rift.coad.lib.webservice.WebServiceException;


/**
 * This object is responsible for handling the HTTP service requests.
 * It handles both the WebService requests and the RMI class loader requests.
 *
 * @author Brett Chaldecott
 */
public class HttpServiceHandler implements HttpRequestHandler, 
        SudoCallbackHandler {
    
    
    // class static member variables
    private final static String AUTH_HEADER = "Authorization";
    private final static String CHALLENGE_HEADER = "WWW-Authenticate";
    private final static String BASIC = "Basic";
    private final static String SESSION_ID = "sessionid";
    private final static String CODE_BASE = "/codebase/";
    
    // the private member variables
    private Logger log =
            Logger.getLogger(HttpServiceHandler.class.getName());
    private HttpRequestCookieManager httpRequestCookieManager = null;
    private String realm = null;
    private HttpRequest request = null;
    private HttpResponse response = null;
    private String clientStubDir = null;
    
    
    /**
     * Creates a new instance of HttpServiceHandler
     *
     * @param realm The security realm for the challange
     */
    public HttpServiceHandler(String realm) throws HttpException {
        this.realm = realm;
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    HttpServiceHandler.class);
            clientStubDir = config.getString("Client_Stub");
        } catch (Exception ex) {
            throw new HttpException("Failed to init the http service handler : "
                    + ex.getMessage(),ex);
        }
        
    }
    
    
    /**
     * This method is responsible for responding to the http server requests.
     *
     * @param request The object containing the request value.
     * @param response The method that encloses the http response value.
     * @exception HttpException
     * @exception IOException
     */
    public void handle(HttpRequest request, HttpResponse response, 
            HttpContext context) throws HttpException, IOException {
        try {
            this.request = request;
            this.response = response;
            
            // Here we check for a client stub code request.
            log.debug("Received a requests");
            String target = request.getRequestLine().getUri();
            String filePath = URLDecoder.decode(target,MimeTypes.UTF_8);
            if (filePath.indexOf(CODE_BASE) == 0) {
                returnClientStubCode(request, response, filePath);
                return;
            }
            
            
            // instanciate the HttpRequestCookieManager
            httpRequestCookieManager = new
                    HttpRequestCookieManager(request, response);
            
            // authenticate the user
            if (sudoAndProcess() == false) {
                response.setStatusCode(HttpStatus.SC_UNAUTHORIZED);
                response.addHeader(new BasicHeader(CHALLENGE_HEADER,BASIC + " realm="
                        + "\"" + realm + "\""));
                return;
            }
            
        } catch (Exception ex) {
            log.error("Failed to respond to the request : " + ex.getMessage(),
                    ex);
            response.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            response.setEntity(new StringEntity("Internal server error [" + 
                        ex.getMessage() + "].","UTF-8"));
        }
    }
    
    /**
     * This method returns the client stub code.
     *
     * @param request The request on the client.
     * @param response The response to the client request.
     * @param path The path to make the request on.
     */
    private void returnClientStubCode(HttpRequest request, 
            HttpResponse response, String path) throws HttpException {
        try {
            File file = new File(clientStubDir,path.substring(
                    CODE_BASE.length() - 1));
            if (!file.exists()) {
                response.setStatusCode(HttpStatus.SC_NOT_FOUND);
                StringEntity body = new StringEntity("File not found [" +
                        file.getPath() + "]","UTF-8");
                response.setEntity(body);
                return;
            } else if (!file.canRead() || file.isDirectory()) {
                response.setStatusCode(HttpStatus.SC_FORBIDDEN);
                StringEntity body = new StringEntity("Access Denied","UTF-8");
                response.setEntity(body);
                return;
            }
            response.setStatusCode(HttpStatus.SC_OK);
            FileEntity fileEntity = new FileEntity(file,
                    "application/x-compressed");
            response.setEntity(fileEntity);
            log.info("Return the file [" + file.getPath() + "]");
        } catch (Exception ex) {
            log.error("Failed to retrieve the file : " + 
                    ex.getMessage(),ex);
            throw new HttpException("Failed to retrieve the file : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to authenticate a user
     *
     * @exception HttpdException
     */
    private boolean sudoAndProcess() throws HttpdException {
        
        try {
            // check for an active session
            CookieWrapper cookieWrapper =
                    httpRequestCookieManager.getCookie(SESSION_ID);
            
            // check if the session is valid
            log.debug("Check for cookie [" + cookieWrapper + "]");
            if (cookieWrapper != null) {
                String sessionId = cookieWrapper.getValue();
                log.info("Retrieve the session id [" + sessionId 
                        + "] from cookie.");
                try {
                    UserSessionManagerAccessor.getInstance().
                            getUserSessionManager().getSessionById(sessionId);
                    Sudo.sudoThreadBySessionId(sessionId,this);
                    return true;
                } catch (com.rift.coad.lib.security.user.UserException ex) {
                    // ignore
                }
            }
            
            // check for auth
            log.debug("Check for an auth header.");
            if (request.containsHeader(AUTH_HEADER)) {
                log.info("Auth user.");
                Header header = request.getFirstHeader(AUTH_HEADER);
                if (!checkForBasic(header)) {
                    return false;
                }
                String decodedValue = decodeHeader(header);
                String sessionId = authenticate(decodedValue);
                log.debug("Session id is [" + sessionId + "]");
                httpRequestCookieManager.addCookie(new CookieWrapper(
                        SESSION_ID,sessionId));
                Sudo.sudoThreadBySessionId(sessionId,this);
                return true;
            }
            
            // assuming that the user will run as a guest.
            process();
            return true;
        } catch (AuthorizationException ex) {
            log.error("Insufficiant permission [" + ex.getMessage() + "]",ex);
            return false;
        } catch (AuthenticationException ex) {
            log.info("Authentication failed [" + ex.getMessage() + "]",ex);
            return false;
        } catch (Exception ex) {
            throw new HttpdException("Failed to auth the user because : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method performs the actuall processing of the request.
     */
    public void process() throws Exception {
        
        try {
            // retrieve the file path
            String target = request.getRequestLine().getUri();
            String filePath = URLDecoder.decode(target,MimeTypes.UTF_8);
            
            // strip the file and look for question mark
            String strippedFile = filePath;
            int getParam = filePath.indexOf('?');
            if (getParam != -1) {
                strippedFile = filePath.substring(0,getParam);
            }
            
            // retrieve the web service
            log.info("Find the file [" + strippedFile + "]");
            WebServiceWrapper webServiceWrapper = (WebServiceWrapper)
                    WebServiceConnector.getInstance().getService(strippedFile);
            if (webServiceWrapper == null) {
                response.setStatusCode(HttpStatus.SC_NOT_FOUND);
                response.setEntity(new StringEntity("The Web Service [" + 
                        strippedFile + "] does not exist.",MimeTypes.UTF_8));
                return;
            }
            
            // validate that we can call it
            Validator.validate(this.getClass(),webServiceWrapper.getRole());
            
            // retrieve the request type
            String method = request.getRequestLine().getMethod();
            
            if (method.equalsIgnoreCase("GET") &&
                    getParam != -1 && (filePath.substring(getParam + 1).
                equalsIgnoreCase("wsdl"))){
                String result = webServiceWrapper.generateWSDL();
                StringEntity stringEntity = new StringEntity(result);
                stringEntity.setContentType(MimeTypes.XML);
                response.setEntity(stringEntity);
                return;
            }
            else if (request instanceof BasicHttpEntityEnclosingRequest) {
                BasicHttpEntityEnclosingRequest post = 
                        (BasicHttpEntityEnclosingRequest)request;
                HttpEntity entity = post.getEntity();
                
                // setup the mime headers
                MimeHeaders mimeHeaders = new MimeHeaders();
                Header[] headerList = request.getAllHeaders();
                for (int i = 0; i < headerList.length; i++) {
                    mimeHeaders.addHeader(headerList[i].getName(),
                            headerList[i].getValue());
                }
                
                String result = webServiceWrapper.processRequest(
                        entity.getContent(),mimeHeaders);
                StringEntity stringEntity = new StringEntity(result);
                stringEntity.setContentType(MimeTypes.XML);
                response.setEntity(stringEntity);
                return;
            }
            response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
            response.setEntity(new StringEntity("Unrecognised request",
                    MimeTypes.UTF_8));
        } catch (AuthorizationException ex) {
            log.error("In sufficiant privaleges : " + ex.getMessage(),
                    ex);
            throw ex;
        } catch (WebServiceException ex) {
            log.error("Failed to process the soap request : " + ex.getMessage(),
                    ex);
            response.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            StringEntity stringEntity = new StringEntity(ex.getMessage(),
                    MimeTypes.UTF_8);
            stringEntity.setContentType(MimeTypes.XML);
            response.setEntity(stringEntity);
            
        } catch (Exception ex) {
            log.error("Failed to process the soap request : " + ex.getMessage(),
                    ex);
            response.setStatusCode(HttpStatus.SC_NOT_FOUND);
            response.setEntity(new StringEntity("Failed to invoke the request " +
                    "because : " + ex.getMessage(),MimeTypes.UTF_8));
        }
    }
    
    
    /**
     * This method validates that a basic auth request has been made.
     *
     * @return TRUE if a basic auth request has been made, FALSE if not.
     * @param authHeader The auth header to retrieve.
     */
    private boolean checkForBasic(Header authHeader) {
        if (authHeader.getValue().toLowerCase().contains(BASIC.toLowerCase())) {
            return true;
        }
        return false;
    }
    
    
    /**
     * This method decodes the header.
     *
     * @return The string value of the decoded header.
     * @param authHeader The header to containing the auth information.
     */
    private String decodeHeader(Header authHeader) {
        String encodedValue = authHeader.getValue().trim();
        String fullValue = encodedValue;
        encodedValue = encodedValue.substring(encodedValue.indexOf(" ")).trim();
        String decodedValue = new String(Base64.decode(encodedValue));
        log.debug("Full value [" + fullValue + "] Encoded value [" 
                + encodedValue + "] decoded value [" 
                + decodedValue + "]");
        return decodedValue;
    }
    
    
    /**
     * This method uses the decoded header authenticate the user.
     *
     * @return The string containing the new session id.
     * @param decodedHeaderValue The decoded header value containing the user
     *          name and password.
     */
    private String authenticate(String decodedHeaderValue) 
            throws AuthenticationException, HttpdException {
        try {
            log.debug("Decoded header value [" + decodedHeaderValue 
                    + "] colon value [" + decodedHeaderValue.indexOf(':')
                    + "]");
            // check for a traditional = seperator
            String username = null;
            String password = null;
            int pos = decodedHeaderValue.indexOf('=');
            if (pos != -1) {
                username = decodedHeaderValue.substring(0,pos);
                password = decodedHeaderValue.substring(pos + 1);
                
            } 
            // check for a colon seperator
            else if ((pos = decodedHeaderValue.indexOf(':')) != -1) {
                username = decodedHeaderValue.substring(0,pos).trim();
                password = decodedHeaderValue.substring(pos + 1).trim();
            } else {
                throw new HttpdException("Authentication not recognised.");
            }
            
            // authenticate
            log.debug("User name [" + username + "] password [" + password + "]");
            SessionLogin sessionLogin = new SessionLogin(
                    new PasswordInfoHandler(username,password));
            sessionLogin.login();
            return sessionLogin.getUser().getSessionId();
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new HttpdException("Failed to authenticate the user : " + 
                    ex.getMessage(),ex);
        }
    }
    
}
