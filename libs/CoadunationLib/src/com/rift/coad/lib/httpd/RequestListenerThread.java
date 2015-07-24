/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  2015 Burntjam
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
 * Copyright August 26, 2006 Rift Marketing CC
 *
 * This class is responsible for monitoring all incoming HTTP connections and
 * than passing of to the http request processing objects.
 */

// package path
package com.rift.coad.lib.httpd;

// java imports
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.SocketException;

// logging import
import org.apache.log4j.Logger;

// jakatar imports
import org.apache.http.HttpServerConnection;
import org.apache.http.impl.DefaultHttpServerConnection;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.ResponseContent;
import org.apache.http.protocol.ResponseDate;
import org.apache.http.protocol.ResponseServer;
import org.apache.http.protocol.ResponseConnControl;
import org.apache.http.protocol.BasicHttpProcessor;
import org.apache.http.protocol.HttpRequestHandlerRegistry;
import org.apache.http.protocol.HttpService;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.DefaultHttpServerConnection;

// coadunation imports
import com.rift.coad.lib.thread.BasicThread;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.security.ThreadsPermissionContainer;

/**
 * This class is responsible for monitoring all incoming HTTP connections and
 * than passing of to the http request processing objects.
 *
 * @author Brett Chaldecott
 */
public class RequestListenerThread extends BasicThread {
    
    // class constants
    public final static String HTTP_HOST = "http_host";
    public final static String HTTP_PORT = "http_port";
    private final static String SOCKET_TIMEOUT = "socket_timeout";
    private final static int DEFAULT_SOCKET_TIMEOUT = 500;
    private final static String HTTP_TIMEOUT = "http_timeout";
    private final static int DEFAULT_HTTP_TIMEOUT = 5000;
    private final static String HTTP_BUFFER_SIZE = "http_buffer_size";
    private final static int DEFAULT_BUFFER_SIZE = 8;
    private final static String ORIGIN_SERVER = "Coadunation-WebServices/1.1";
    private final static String DOCUMENT_ROOT_PARAM = "server.docroot";
    private final static String HTTP_DOCUMENT_ROOT_CONFIG = "server_doc_root";
    private final static String HTTP_SECURITY_REALM = "realm";
    private final static String DEFAULT_HTTP_SECURITY_REALM = "coadunation";
    
    // the class log variable
    protected Logger log =
        Logger.getLogger(RequestListenerThread.class.getName());
    
    // the private member variables
    private ThreadStateMonitor monitor = new ThreadStateMonitor();
    private HttpRequestManager httpRequestManager = null;
    private ServerSocket serversocket = null;
    private HttpParams params = null;
    private String securityRealm = null;
    
    
    /**
     * Creates a new instance of RequestListenerThread
     * 
     * 
     * @param httpRequestManager The request manager.
     * @exception HHttpdException
     */
    public RequestListenerThread(HttpRequestManager httpRequestManager) 
            throws HttpdException, Exception {
        try {
            this.httpRequestManager = httpRequestManager;
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    this.getClass());
            
            // init the server socket
            this.serversocket = new ServerSocket((int)config.
                    getLong(HTTP_PORT,HttpDaemon.DEFAULT_PORT));
            
            // setup the parameters
            this.params = new BasicHttpParams();
            
            // retrieve the http timeout
            int timeout = (int)config.getLong(HTTP_TIMEOUT,DEFAULT_HTTP_TIMEOUT);
            
            // retrieve the buffer size in kilo bytes
            int kBufferSize = (int)config.getLong(HTTP_BUFFER_SIZE,
                    DEFAULT_BUFFER_SIZE);
            
            // the realm
            securityRealm = config.getString(HTTP_SECURITY_REALM,
                    DEFAULT_HTTP_SECURITY_REALM);
            
            // set the parameters
            this.params
                .setIntParameter(CoreConnectionPNames.SO_TIMEOUT, timeout)
                .setIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, kBufferSize * 1024)
                .setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, false)
                .setBooleanParameter(CoreConnectionPNames.TCP_NODELAY, true)
                .setParameter(CoreProtocolPNames.ORIGIN_SERVER, ORIGIN_SERVER)
                .setParameter("server.docroot", "/");
            
        } catch (Exception ex) {
            throw new HttpdException("Failed to start the request listerner thread : " +
                    ex.getMessage(),ex);
        }
        
        
        
    }
    
    
    /**
     * This method replaces the run method in the BasicThread.
     *
     * @exception Exception
     */
    public void process() throws Exception {
        while (monitor.isTerminated() == false) {
            try {
                log.debug("Wait for a socket connection");
                Socket socket = this.serversocket.accept();
                DefaultHttpServerConnection conn = 
                        new DefaultHttpServerConnection();
                log.debug("Process request from : " + socket.getInetAddress());
                conn.bind(socket, this.params);
                
                // Set up the HTTP protocol processor
                BasicHttpProcessor httpproc = new BasicHttpProcessor();
                httpproc.addInterceptor(new ResponseDate());
                httpproc.addInterceptor(new ResponseServer());
                httpproc.addInterceptor(new ResponseContent());
                httpproc.addInterceptor(new ResponseConnControl());

                // Set up request handlers
                HttpRequestHandlerRegistry reqistry = new HttpRequestHandlerRegistry();
                reqistry.register("*", new HttpServiceHandler(securityRealm));
                    
                // Set up the HTTP service
                HttpService httpService = new HttpService(
                        httpproc, 
                        new DefaultConnectionReuseStrategy(), 
                        new DefaultHttpResponseFactory());
                httpService.setParams(this.params);
                httpService.setHandlerResolver(reqistry);
                
                // add the object to the queue for processing
                log.debug("Add a request to the queue");
                httpRequestManager.addRequest(new Request(httpService,conn));
                
            } catch (SocketTimeoutException ex){
                // ignore the time out we do not want to block in definitly
            } catch (SocketException ex) {
                if (!serversocket.isClosed()) {
                    log.error("Failed to process an http request : " + 
                        ex.getMessage(),ex);
                }
            } catch (Exception ex) {
                log.error("Failed to process an http request : " + 
                        ex.getMessage(),ex);
            }
        }
    }
    
    
    /**
     * This method will be implemented by child objects to terminate the
     * processing of this thread.
     */
    public void terminate() {
        try {
            serversocket.close();
        } catch (Exception ex) {
            log.error("Failed to close the socket : " + ex.getMessage(),ex);
        }
        monitor.terminate(false);
    }
    
}
