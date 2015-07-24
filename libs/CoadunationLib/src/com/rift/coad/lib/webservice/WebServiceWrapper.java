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
 * WebServiceWrapper.java
 *
 * The web service wrapper responsible for wrapping the SOAP web service
 * information.
 */

// package paths
package com.rift.coad.lib.webservice;

// java imports
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.ClassLoader;
import java.net.InetAddress;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Vector;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.namespace.QName;
import java.lang.reflect.Method;
import javax.naming.InitialContext;
import javax.transaction.Status;
import javax.transaction.UserTransaction;


// w3c imports
import org.w3c.dom.Document;

// logging import
import org.apache.log4j.Logger;

// ibm imports
import com.ibm.wsdl.OperationImpl;

// jaxb imports
import javax.xml.soap.MimeHeaders;

// axis imports
import org.apache.axis.AxisFault;
import org.apache.axis.AxisEngine;
import org.apache.axis.Constants;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.constants.Scope;
import org.apache.axis.description.ServiceDesc;
import org.apache.axis.handlers.soap.SOAPService;
import org.apache.axis.message.SOAPEnvelope;
import org.apache.axis.message.SOAPFault;
import org.apache.axis.providers.java.RPCProvider;
import org.apache.axis.transport.http.NonBlockingBufferedInputStream;
import org.apache.axis.server.AxisServer;
import org.apache.axis.utils.ClassUtils;
import org.apache.axis.utils.Messages;
import org.apache.axis.utils.XMLUtils;
import org.apache.axis.wsdl.gen.Parser;
import org.apache.axis.wsdl.symbolTable.SymbolTable;
import org.apache.axis.wsdl.symbolTable.BaseType;
import org.apache.axis.wsdl.symbolTable.BindingEntry;
import org.apache.axis.wsdl.symbolTable.Type;
import org.apache.axis.wsdl.symbolTable.DefinedType;
import javax.xml.rpc.encoding.TypeMapping;
import javax.xml.rpc.encoding.TypeMappingRegistry;


// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.deployment.WebServiceInfo;
import com.rift.coad.lib.deployment.DeploymentLoader;
import com.rift.coad.lib.httpd.HttpDaemon;
import com.rift.coad.lib.httpd.MimeTypes;
import com.rift.coad.lib.thirdparty.axis.AxisManager;
import com.rift.coad.lib.thirdparty.axis.AxisException;

/**
 * The web service wrapper responsible for wrapping the SOAP web service
 * information.
 *
 * @author Brett Chaldecott
 */
public class WebServiceWrapper {
    
    // the class log variable
    protected Logger log =
            Logger.getLogger(WebServiceWrapper.class.getName());
    
    // class constants
    private final static String PARSER_PATTERN =
            "address[\\s]+location=\"[a-z0-9://_-]*\"";
    private final static String SERVICE_END_POINT_FORMAT =
            "address location=\"http://%s:%d%s\"";
    private final static String URL_FORMAT =
            "http://%s:%d%s";
    private final static String HOST = "host";
    private final static String PORT = "port";
    private final static String TRANSPORT_NAME = "SimpleHTTP";
    
    
    // the classes member variables
    private String serviceEndPoint = null;
    private String url = null;
    private String path = null;
    private String role = null;
    private SOAPService service = null;
    private ClassLoader classLoader = null;
    private boolean transaction = false;
    private InitialContext context = null;
    private UserTransaction ut = null;
    private Vector extraClasses = null;
    
    /**
     * Creates a new instance of WebServiceWrapper.
     *
     * @param webServiceInfo The reference to the web service information.
     * @param deploymentLoader The object responsible for loading a deployment
     *        file.
     * @exception WebServiceException
     */
    public WebServiceWrapper(WebServiceInfo webServiceInfo,
            DeploymentLoader deploymentLoader)
            throws WebServiceException {
        
        try {
            Configuration config = ConfigurationFactory.getInstance().
                    getConfig(WebServiceWrapper.class);
            context = new InitialContext();
            
            String host = config.getString(HOST,
                    InetAddress.getLocalHost().getCanonicalHostName());
            int port = (int)config.getLong(PORT,HttpDaemon.DEFAULT_PORT);
            
            path = webServiceInfo.getPath();
            role = webServiceInfo.getRole();
            serviceEndPoint = String.format(
                    SERVICE_END_POINT_FORMAT,host,port,path);
            url = String.format(URL_FORMAT,host,port,path);
            
            // retrieve a reverence to the axis engine
            AxisEngine engine = AxisManager.getInstance().getServer();
            
            // test the class
            deploymentLoader.getClass(webServiceInfo.getClassName());
            
            // try different handlers
            service = new SOAPService(new WebServiceInvoker(
                    deploymentLoader.getClassLoader()));
            
            service.setName(webServiceInfo.getClassName());
            service.setOption(RPCProvider.OPTION_CLASSNAME,
                    webServiceInfo.getClassName());
            service.setOption(RPCProvider.OPTION_ALLOWEDMETHODS, "*");
            service.setOption(RPCProvider.OPTION_SCOPE,
                    Scope.DEFAULT.getName());
            
            // validate
            String wsdlPath = deploymentLoader.getTmpDir().getAbsolutePath() +
                    File.separator +
                    webServiceInfo.getWSDLPath();
            validate(wsdlPath,deploymentLoader.getClassLoader().loadClass(
                    webServiceInfo.getClassName()));
            extraClasses = webServiceInfo.getClasses();
            generateTypeMapping(wsdlPath,deploymentLoader.getClassLoader().loadClass(
                    webServiceInfo.getClassName()));
            // wsdl parameters
            ServiceDesc serviceDesc = service.getServiceDescription();
            serviceDesc.setWSDLFile(wsdlPath);
            
            service.setEngine(engine);
            service.init();
            
            // set the class loader
            classLoader = deploymentLoader.getClassLoader();
            
            // set the class loader
            ClassUtils.setClassLoader(webServiceInfo.getClassName(),
                    deploymentLoader.getClassLoader());
            
            transaction = webServiceInfo.getTransaction();
            if (transaction) {
                ut = (UserTransaction)context.lookup(
                        "java:comp/UserTransaction");
            }
            
            
            
        } catch (Exception ex) {
            throw new WebServiceException(
                    "Failed to initialize the web service : " + ex.getMessage()
                    ,ex);
        }
    }
    
    
    /**
     * This method returns the path to the web service.
     *
     * @return The string containing the path to the web service.
     */
    public String getPath() {
        return path;
    }
    
    
    /**
     * This method returns the role of the web service.
     *
     * @return The string containing the role information.
     */
    public String getRole() {
        return role;
    }
    
    
    /**
     * This method return the reference to the soap service.
     *
     * @return The reference to the soap service.
     */
    public SOAPService getService() {
        return service;
    }
    
    
    /**
     * This method returns the class loader that can be used to load the service.
     *
     * @return The reference to the class loader.
     */
    public ClassLoader getClassLoader() {
        return classLoader;
    }
    
    
    /**
     * This method returns a string containing the genreated WSDL
     *
     * @return String The string containing the wsdl.
     * @exception WebServiceException
     */
    public String generateWSDL() throws
            WebServiceException {
        // setup the axis engine
        MessageContext msgContext = null;
        try {
            msgContext = setupContext();
            AxisServer engine = AxisManager.getInstance().getServer();
            engine.generateWSDL(msgContext);
            Document doc = (Document) msgContext.getProperty("WSDL");
            XMLUtils.normalize(doc.getDocumentElement());
            String wsdl = XMLUtils.PrettyDocumentToString(doc);
            Pattern pattern = Pattern.compile(PARSER_PATTERN,
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
            return pattern.matcher(wsdl).replaceFirst(serviceEndPoint);
        } catch (WebServiceException ex) {
            throw ex;
        } catch (AxisException ex) {
            throw new WebServiceException(
                    "Failed to retrieve a valid axis reference : " +
                    ex.getMessage(),ex);
        } catch (Exception ex) {
            processException(msgContext,ex);
        }
        // this statement will never get reached as the process exception will
        // always throw, but to prevent the compiler from complaining it is
        // put here.
        return null;
    }
    
    
    /**
     * This message takes a string message block and returns a string message
     * block.
     *
     * @return The string result.
     * @param message The message to process.
     * @exception WebServiceException
     */
    public String processRequest(String message) throws WebServiceException {
        return processRequest(new ByteArrayInputStream(message.getBytes()),
                new MimeHeaders());
    }
    
    
    /**
     * This method process the input stream passed to it and returns a string
     * message.
     *
     * @return An XML string result of the request.
     * @param in The input stream containing the message to process.
     * @param headers The headers to process.
     * @exception WebServiceException
     */
    public String processRequest(InputStream in, MimeHeaders headers) throws
            WebServiceException {
        MessageContext msgContext = null;
        boolean ownTransaction = false;
        try {
            if (transaction &&
                    (ut.getStatus() == Status.STATUS_NO_TRANSACTION)) {
                ut.begin();
                ownTransaction = true;
            }
            msgContext = setupContext();
            AxisServer engine = AxisManager.getInstance().getServer();
            NonBlockingBufferedInputStream inputStream = new
                    NonBlockingBufferedInputStream();
            inputStream.setInputStream(in);
            
            // setup a message request
            Message requestMsg = new Message(inputStream,false,headers);
            msgContext.setRequestMessage(requestMsg);
            
            // invoke the request
            engine.invoke(msgContext);
            
            // retrieve the result
            Message responseMsg = msgContext.getResponseMessage();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            responseMsg.writeTo(output);
            output.flush();
            if (ownTransaction) {
                commitTransaction();
                ownTransaction = false;
            }
            return output.toString();
        } catch (WebServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            processException(msgContext,ex);
        } finally {
            if (ownTransaction) {
                try {
                    if (ut.getStatus() == Status.STATUS_ACTIVE) {
                        ut.rollback();
                    }
                } catch (Exception ex2) {
                    log.error("Failed to rollback the changes : " +
                            ex2.getMessage(),ex2);
                }
            }
        }
        
        // this statement will never get reached as the process exception will
        // always throw, but to prevent the compiler from complaining it is
        // put here.
        return null;
    }
    
    
    /**
     * This method validates the WSDL file
     *
     * @param wsdlPath The path to the wsdl file to perform the validation for.
     * @param ref The reference to the class to validate.
     * @exception WebServiceException
     */
    private void validate(String wsdlPath,Class ref) throws WebServiceException {
        try {
            // TODO: Improve WSDL validation to check more than just the operation names
            // This will be done at a later date when it becomes a problem
            
            Parser parser = new Parser();
            parser.run(wsdlPath);
            Set operations = getOpertations(parser);
            Method[] methods = ref.getDeclaredMethods();
            for (Iterator iter = operations.iterator(); iter.hasNext();) {
                OperationImpl operation = (OperationImpl)iter.next();
                boolean found = false;
                for (int index = 0; index < methods.length; index++) {
                    if (methods[index].getName().equals(operation.getName())) {
                        found = true;
                    }
                }
                if (!found) {
                    throw new WebServiceException("The operation [" +
                            operation.getName() + "] not found on [" +
                            ref.getName() + "]");
                }
            }
        } catch (WebServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Validation on [" + wsdlPath + "] failed : " +
                    ex.getMessage(),ex);
            throw new WebServiceException("Validation on [" + wsdlPath
                    + "] failed : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the list of operations on a wsdl interface.
     *
     * @return The set containing the list of operations.
     * @param parser The reference to the parser
     */
    private Set getOpertations(Parser parser) throws WebServiceException {
        SymbolTable symbolTable = parser.getSymbolTable();
        Map index = symbolTable.getHashMap();
        for (Iterator iter = index.keySet().iterator(); iter.hasNext();) {
            QName key = (QName)iter.next();
            Object value = index.get(key);
            if (value instanceof Vector) {
                Vector list = (Vector)value;
                for (int count = 0; count < list.size(); count++) {
                    Object listValue = list.get(count);
                    if (listValue instanceof BindingEntry) {
                        BindingEntry entry = (BindingEntry)listValue;
                        return entry.getOperations();
                    }
                }
            }
        }
        throw new WebServiceException(
                "There are no operations defined for this wsdl file");
    }
    
    /**
     * This method validates the WSDL file
     *
     * @param wsdlPath The path to the wsdl file to perform the validation for.
     * @param ref The reference to the class to validate.
     * @exception WebServiceException
     */
    private void generateTypeMapping(String wsdlPath,Class ref) throws
            WebServiceException {
        try {
            Parser parser = new Parser();
            parser.run(wsdlPath);
            SymbolTable symbolTable = parser.getSymbolTable();
            Map index = parser.getSymbolTable().getTypeIndex();
            //this.service.getTypeMappingRegistry().register("http://schemas.xmlsoap.org/soap/encoding/")
            Map classTypes = getTypes(ref);
            TypeMappingRegistry registry = this.service.getTypeMappingRegistry();
            TypeMapping typeMapping = registry.createTypeMapping();
            for (Iterator iter = index.keySet().iterator(); iter.hasNext();) {
                QName key = (QName)iter.next();
                Type value = (Type)index.get(key);
                if (value instanceof DefinedType) {
                    DefinedType defType = (DefinedType)value;
                    
                    if (defType.getRefType() == null) {
                        Class refType = (Class)classTypes.get(defType.getQName().getLocalPart());
                        if (refType == null && 
                                defType.getQName().getLocalPart().equalsIgnoreCase("MapItem")) {
                            refType = java.util.Map.class;
                        } else if (refType == null) {
                            continue;
                        }
                        log.debug("Key [" + key.toString() + "] value [" +
                                defType.getQName().getLocalPart()
                                + "] [" + defType.getBaseType() + "] [" +
                                defType.toString() + "] [" + refType.getName() + "]");
                        typeMapping.register(refType,defType.getQName(),
                                new org.apache.axis.encoding.ser.BeanSerializerFactory(
                                refType,defType.getQName()),
                                new org.apache.axis.encoding.ser.BeanDeserializerFactory(
                                refType,defType.getQName()));
                    }
                    
                }
            }
            this.service.getTypeMappingRegistry().register(
                    "http://schemas.xmlsoap.org/soap/encoding/",typeMapping);
        } catch (Exception ex) {
            log.error("Failed to retrieve the type mapping [" + wsdlPath
                    + "] because : " +
                    ex.getMessage(),ex);
            throw new WebServiceException(
                    "Failed to retrieve the type mapping [" + wsdlPath
                    + "] failed : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method setups the message context
     *
     * @returns A message context.
     * @exception WebServiceException
     */
    private MessageContext setupContext() throws WebServiceException {
        try {
            // setup the axis engine
            AxisServer engine = AxisManager.getInstance().getServer();
            MessageContext msgContext = new MessageContext(engine);
            msgContext.setTransportName(TRANSPORT_NAME);
            msgContext.setProperty(MessageContext.TRANS_URL, url);
            msgContext.setProperty(Constants.MC_REALPATH,path);
            msgContext.setProperty(Constants.MC_RELATIVE_PATH,path);
            msgContext.setService(service);
            return msgContext;
        } catch (Exception ex) {
            log.error("Failed to setup the message context : " +
                    ex.getMessage(),ex);
            throw new WebServiceException(
                    "Failed to setup the message context : " + ex.getMessage(),
                    ex);
        }
    }
    
    
    /**
     * This method handles the exceptions generated by axis generically.
     *
     * @param msgContext The message context.
     * @param ex The exception to throw
     */
    private void processException(MessageContext msgContext,Exception ex) throws
            WebServiceException {
        try {
            // check that the message context has been set
            if (msgContext == null) {
                throw new WebServiceException("Failed to process request : " +
                        ex.getMessage(),ex);
            }
            
            log.debug("Fault messsage is : " + ex.getMessage(), ex);
            
            // handle the axis fault
            AxisFault af;
            if (ex instanceof AxisFault) {
                af = (AxisFault) ex;
                log.debug(Messages.getMessage("serverFault00"), af);
            } else {
                af = AxisFault.makeFault(ex);
            }
            
            // There may be headers we want to preserve in the
            // response message - so if it's there, just add the
            // FaultElement to it.  Otherwise, make a new one.
            Message responseMsg = msgContext.getResponseMessage();
            if (responseMsg == null) {
                responseMsg = new Message(af);
                responseMsg.setMessageContext(msgContext);
            } else {
                SOAPEnvelope env = responseMsg.getSOAPEnvelope();
                env.clearBody();
                env.addBodyElement(new SOAPFault((AxisFault) ex));
            }
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            responseMsg.writeTo(output);
            output.flush();
            
            // throw an exception with the xml content wrapped properly
            throw new WebServiceException(output.toString(),MimeTypes.XML);
        } catch (WebServiceException ex2) {
            throw ex2;
        } catch (Exception ex2) {
            log.error("Failed to process the exception : " +
                    ex2.getMessage(),ex2);
            throw new WebServiceException(
                    "Failed to process the exception : " + ex2.getMessage(),
                    ex2);
        }
    }
    
    
    /**
     * This method returns the list of types for the class.
     */
    public Map getTypes(Class ref) throws WebServiceException {
        Map entries = new HashMap();
        Method[] methods = ref.getMethods();
        for (int index = 0; index < methods.length; index++) {
            Class returnType = methods[index].getReturnType();
            Class[] parameterTypes = methods[index].getParameterTypes();
            Class[] exceptionTypes = methods[index].getExceptionTypes();
            if (!returnType.isPrimitive() && !returnType.isArray()) {
                entries.put(returnType.getSimpleName(),returnType);
            } else if (returnType.isArray() &&
                    !returnType.getComponentType().isPrimitive()) {
                entries.put(returnType.getComponentType().getSimpleName(),
                        returnType.getComponentType());
            }
            for (int paramIndex = 0; paramIndex < parameterTypes.length;
            paramIndex++) {
                Class param = parameterTypes[paramIndex];
                if (!param.isPrimitive() && !param.isArray()) {
                    entries.put(param.getSimpleName(),param);
                } else if (param.isArray() &&
                        !param.getComponentType().isPrimitive()) {
                    entries.put(param.getComponentType().getSimpleName(),
                            param.getComponentType());
                }
            }
            for (int exceptionIndex = 0; exceptionIndex < exceptionTypes.length;
            exceptionIndex++) {
                Class exception = exceptionTypes[exceptionIndex];
                if (exception != java.rmi.RemoteException.class) {
                    entries.put(exception.getSimpleName(),exception);
                }
            }
            
        }
        for (int index = 0; index < extraClasses.size(); index++) {
            String className = (String)extraClasses.get(index);
            try {
                Class extraClass = Class.forName(
                        className,false,ref.getClassLoader());
                entries.put(extraClass.getSimpleName(),extraClass);
            } catch (Throwable ex) {
                log.error("Failed to retrieve the class [" + className 
                        + "] because : " + ex.getMessage(),ex);
            }
        }
        return entries;
    }


    /**
     * Attempt to commit the transaction
     *
     * @param ut The user transaction to commit.
     */
    private void commitTransaction() throws Exception {
        javax.transaction.TransactionManager jtaTransManager = null;
        javax.transaction.Transaction transaction = null;
        try {
            jtaTransManager = (javax.transaction.TransactionManager)context.
                    lookup("java:comp/TransactionManager");
            transaction = jtaTransManager.getTransaction();
            transaction.commit();
        } catch (Exception ex) {
            log.error("Failed to commit the transaction : " + ex.getMessage(),ex);
            try {
                if (transaction != null && transaction.getStatus() != Status.STATUS_ROLLEDBACK
                      && transaction.getStatus() != Status.STATUS_COMMITTED) {
                    log.info("Calling rollback to attempt to undo the changes");
                    transaction.rollback();
                    log.info("After calling rollback to attempt to undo the changes");
                }
            } catch (Exception ex2) {
                log.error("Failed to rollback a failed commit : " +
                        ex2.getMessage(),ex2);
            }
            throw ex;
        }
    }

}
