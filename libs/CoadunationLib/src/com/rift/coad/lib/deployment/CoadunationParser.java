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
 * XMLConfigurationException.java
 *
 * CoadunationParser.java
 *
 * This object is responsible for parsing the contents of the of the Coadunation
 * xml file.
 */

package com.rift.coad.lib.deployment;

// java imports
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;
import java.io.StringReader;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;


/**
 * The parser is responsible for parsing the Coadunation xml file so that it can
 * be deployed within the coadunation server.
 *
 * @author Brett Chaldecott
 */
public class CoadunationParser {
    
    /**
     * The inner class responsible for handling the contents of the Coadunation
     * xml source document.
     */
    public class CoadunationHandler extends DefaultHandler {
        
        // the class static member variables
        private static final String COADUNATION = "coadunation";
        private static final String COADUNATION_VERSION = "version";
        private static final String COADUNATION_NAME = "name";
        private static final String DESCRIPTION = "description";
        private static final String WEB_SERVICES = "webservices";
        private static final String WEB_SERVICE = "webservice";
        private static final String PATH = "path";
        private static final String CLASSES = "classes";
        private static final String CACHE = "cache_by_key";
        private static final String CACHE_TIMEOUT = "cache_timeout";
        private static final String CLASS_NAME = "class";
        private static final String WSDL_PATH = "wsdl";
        private static final String ROLE = "role";
        private static final String USERNAME = "username";
        private static final String JMX_BEANS = "jmxbeans";
        private static final String BEAN = "bean";
        private static final String OBJECT_NAME = "objectName";
        private static final String BEANS = "beans";
        private static final String INTERFACE_NAME = "interface";
        private static final String BIND_NAME = "bindName";
        private static final String THREAD = "thread";
        private static final String THREAD_NUMBER = "number";
        private static final String TRANSACTION = "transaction";

        // the xpdl information object
        private DeploymentInfo deploymentInfo = null;
        
        // the processing variables
        private boolean inCoadunation = false;
        private boolean inDescription = false;
        private boolean inWebServices = false;
        private boolean inWebService = false;
        private boolean inPath = false;
        private boolean inInterface = false;
        private boolean inClassName = false;
        private boolean inClasses = false;
        private boolean inCache = false;
        private boolean inCacheTimeout = false;
        private boolean inWSDL = false;
        private boolean inRole = false;
        private boolean inUsername = false;
        private boolean inJMXBeans = false;
        private boolean inBean = false;
        private boolean inObjectName = false;
        private boolean inBeans = false;
        private boolean inBindName = false;
        private boolean inThread = false;
        private boolean inThreadNumber = false;
        private boolean inTransaction = false;
        private String inData = null;
        
        // the temporary objects
        private WebServiceInfo webServiceInfo = null;
        private JMXBeanInfo jmxBeanInfo = null;
        private BeanInfo beanInfo = null;
        private DeploymentThreadInfo threadInfo = null;
        
        /**
         * The constructor of the XPDL Handler
         */
        public CoadunationHandler(DeploymentInfo deploymentInfo) {
            this.deploymentInfo = deploymentInfo;
        }
        
        
        /**
         * Parse the start of an element 
         */
        public void startElement(String uri, String localName, String qName,
                         Attributes attributes) throws SAXException {
            // handle a package and retrieve the value information
            if (qName.compareToIgnoreCase(COADUNATION) == 0) {
                String version = (String)attributes.getValue(COADUNATION_VERSION);
                String name = (String)attributes.getValue(COADUNATION_NAME);
                if ((version == null) || (name == null)) {
                    throw new SAXException(
                            "Failed to retrieve either the name or the version");
                }
                deploymentInfo.setVersion(version);
                deploymentInfo.setName(name);
                inCoadunation = true;
            } else if (inCoadunation &&
                    qName.compareToIgnoreCase(DESCRIPTION) == 0) {
                inDescription = true;
                inData = "";
            } 
            // the web services section
            else if (inCoadunation &&
                    qName.compareToIgnoreCase(WEB_SERVICES) == 0) {
                inWebServices = true;
            } else if (inWebServices &&
                    qName.compareToIgnoreCase(WEB_SERVICE) == 0) {
                inWebService = true;
                webServiceInfo = new WebServiceInfo();
            } else if (inWebService &&
                    qName.compareToIgnoreCase(PATH) == 0) {
                inPath = true;
                inData = new String();
            } else if (inWebService && 
                    qName.compareToIgnoreCase(CLASS_NAME) == 0) {
                inClassName = true;
                inData = new String();
            } else if (inWebService && 
                    qName.compareToIgnoreCase(WSDL_PATH) == 0) {
                inWSDL = true;
                inData = new String();
            } else if (inWebService && 
                    qName.compareToIgnoreCase(ROLE) == 0) {
                inRole = true;
                inData = new String();
            } else if (inWebService && 
                    qName.compareToIgnoreCase(TRANSACTION) == 0) {
                inTransaction = true;
                inData = new String();
            } else if (inWebService &&
                    qName.compareToIgnoreCase(CLASSES) == 0) {
                inClasses = true;
                inData = new String();
            }
            // the jmx section
            else if (inCoadunation &&
                    qName.compareToIgnoreCase(JMX_BEANS) == 0) {
                inJMXBeans = true;
            } else if (inJMXBeans &&
                    qName.compareToIgnoreCase(BEAN) == 0) {
                jmxBeanInfo = new JMXBeanInfo();
                inBean = true;
            } else if (inJMXBeans && inBean &&
                    qName.compareToIgnoreCase(INTERFACE_NAME) == 0) {
                inInterface = true;
                inData = new String();
            } else if (inJMXBeans && inBean &&
                    qName.compareToIgnoreCase(CLASS_NAME) == 0) {
                inClassName = true;
                inData = new String();
            } else if (inJMXBeans && inBean &&
                    qName.compareToIgnoreCase(CLASSES) == 0) {
                inClasses = true;
                inData = new String();
            } else if (inJMXBeans && inBean &&
                    qName.compareToIgnoreCase(CACHE) == 0) {
                inCache = true;
                inData = new String();
            } else if (inJMXBeans && inBean &&
                    qName.compareToIgnoreCase(CACHE_TIMEOUT) == 0) {
                inCacheTimeout = true;
                inData = new String();
            } else if (inJMXBeans && inBean &&
                    qName.compareToIgnoreCase(TRANSACTION) == 0) {
                inTransaction = true;
                inData = new String();
            } else if (inJMXBeans && inBean &&
                    qName.compareToIgnoreCase(OBJECT_NAME) == 0) {
                inObjectName = true;
                inData = new String();
            } else if (inJMXBeans && inBean &&
                    qName.compareToIgnoreCase(BIND_NAME) == 0) {
                inBindName = true;
                inData = new String();
            } else if (inJMXBeans && inBean &&
                    qName.compareToIgnoreCase(ROLE) == 0) {
                inRole = true;
                inData = new String();
            } else if (inJMXBeans && inBean &&
                    qName.compareToIgnoreCase(USERNAME) == 0) {
                inUsername = true;
                inData = new String();
            } else if (inJMXBeans && inBean &&
                    qName.compareToIgnoreCase(THREAD) == 0) {
                inThread = true;
                threadInfo = new DeploymentThreadInfo();
            }
            // coadunation beans
            else if (inCoadunation &&
                    qName.compareToIgnoreCase(BEANS) == 0) {
                inBeans = true;
            } else if (inBeans &&
                    qName.compareToIgnoreCase(BEAN) == 0) {
                beanInfo = new BeanInfo();
                inBean = true;
            } else if (inBeans && inBean &&
                    qName.compareToIgnoreCase(INTERFACE_NAME) == 0) {
                inInterface = true;
                inData = new String();
            } else if (inBeans && inBean &&
                    qName.compareToIgnoreCase(CLASS_NAME) == 0) {
                inClassName = true;
                inData = new String();
            } else if (inBeans && inBean &&
                    qName.compareToIgnoreCase(CLASSES) == 0) {
                inClasses = true;
                inData = new String();
            } else if (inBeans && inBean &&
                    qName.compareToIgnoreCase(CACHE) == 0) {
                inCache = true;
                inData = new String();
            } else if (inBeans && inBean &&
                    qName.compareToIgnoreCase(CACHE_TIMEOUT) == 0) {
                inCacheTimeout = true;
                inData = new String();
            } else if (inBeans && inBean &&
                    qName.compareToIgnoreCase(TRANSACTION) == 0) {
                inTransaction = true;
                inData = new String();
            } else if (inBeans && inBean &&
                    qName.compareToIgnoreCase(BIND_NAME) == 0) {
                inBindName = true;
                inData = new String();
            } else if (inBeans && inBean &&
                    qName.compareToIgnoreCase(ROLE) == 0) {
                inRole = true;
                inData = new String();
            } else if (inBeans && inBean &&
                    qName.compareToIgnoreCase(USERNAME) == 0) {
                inUsername = true;
                inData = new String();
            } else if (inBeans && inBean &&
                    qName.compareToIgnoreCase(THREAD) == 0) {
                inThread = true;
                threadInfo = new DeploymentThreadInfo();
            }
            // setup the thread information
            else if (((inBeans && inBean && inThread) || 
                    (inJMXBeans && inBean && inThread)) && 
                    qName.compareToIgnoreCase(CLASS_NAME) == 0) {
                inClassName = true;
                inData = new String();
            } else if (((inBeans && inBean && inThread) || 
                    (inJMXBeans && inBean && inThread)) && 
                    qName.compareToIgnoreCase(USERNAME) == 0) {
                inUsername = true;
                inData = new String();
            } else if (((inBeans && inBean && inThread) || 
                    (inJMXBeans && inBean && inThread)) && 
                    qName.compareToIgnoreCase(THREAD_NUMBER) == 0) {
                inThreadNumber = true;
                inData = new String();
            }
            
        }
        
        /**
         * Read in the characters
         */
        public void characters(char[] ch, int start, int length) {
            if (inDescription || inPath || inClassName || inClasses || inWSDL || 
                    inRole || inObjectName || inInterface || inBindName ||
                    inUsername || inThreadNumber || inCache || inCacheTimeout ||
                    inTransaction) {
                inData += new String(ch,start,length);
            }
        }
        
        /**
         * Handle the end of an element
         */
        public void endElement(String uri, String localName, String qName) 
                throws SAXException {
            // handle a package and retrieve the value information
            if (qName.compareToIgnoreCase(COADUNATION) == 0) {
                inCoadunation = false;
            } else if (inCoadunation &&
                    qName.compareToIgnoreCase(DESCRIPTION) == 0) {
                inDescription = false;
                deploymentInfo.setDescription(inData.trim());
                inData = "";
            }
            // the web services section
            else if (inCoadunation &&
                    qName.compareToIgnoreCase(WEB_SERVICES) == 0) {
                inWebServices = false;
            } 
            else if (inWebServices &&
                    qName.compareToIgnoreCase(WEB_SERVICE) == 0) {
                if (webServiceInfo.isInitialized() == false) {
                    throw new SAXException(
                            "The web service information has not been setup " +
                            "correctly. Must contain [path][class][WSDL]" +
                            "[role]");
                }
                deploymentInfo.addWebService(webServiceInfo);
                inWebService = false;
                inData = new String();
            } else if (inWebService &&
                    qName.compareToIgnoreCase(PATH) == 0) {
                inPath = false;
                webServiceInfo.setPath(inData.trim());
                inData = new String();
            } else if (inWebService &&
                    qName.compareToIgnoreCase(CLASS_NAME) == 0) {
                inClassName = false;
                webServiceInfo.setClassName(inData.trim());
                inData = new String();
            } else if (inWebService &&
                    qName.compareToIgnoreCase(WSDL_PATH) == 0) {
                inWSDL = false;
                webServiceInfo.setWSDLPath(inData.trim());
                inData = new String();
            } else if (inWebService && 
                    qName.compareToIgnoreCase(ROLE) == 0) {
                inRole = false;
                webServiceInfo.setRole(inData.trim());
                inData = new String();
            } else if (inWebService && 
                    qName.compareToIgnoreCase(TRANSACTION) == 0) {
                String transactionResult = inData.trim();
                if (transactionResult.equalsIgnoreCase("yes") || 
                        transactionResult.equalsIgnoreCase("true")) {
                    webServiceInfo.setTransaction(true);
                }
                inTransaction = false;
                inData = new String();
            } else if (inWebService &&
                    qName.compareToIgnoreCase(CLASSES) == 0) {
                inClasses = false;
                webServiceInfo.addToClasses(inData.trim());
                inData = new String();
            }
            // the jmx section
            else if (inCoadunation &&
                    qName.compareToIgnoreCase(JMX_BEANS) == 0) {
                inJMXBeans = false;
            } else if (inJMXBeans &&
                    qName.compareToIgnoreCase(BEAN) == 0) {
                if (jmxBeanInfo.isInitialized() == false) {
                    throw new SAXException(
                            "The JMX Bean information has not been setup " +
                            "correctly. Must contain [class][objectName].");
                }
                deploymentInfo.addJmxBean(jmxBeanInfo);
                inBean = false;
            } else if (inJMXBeans && inBean && !inThread &&
                    qName.compareToIgnoreCase(INTERFACE_NAME) == 0) {
                jmxBeanInfo.setInterfaceName(inData.trim());
                inInterface = false;
            } else if (inJMXBeans && inBean && !inThread &&
                    qName.compareToIgnoreCase(CLASS_NAME) == 0) {
                jmxBeanInfo.setClassName(inData.trim());
                inClassName = false;
            } else if (inJMXBeans && inBean && !inThread &&
                    qName.compareToIgnoreCase(CLASSES) == 0) {
                jmxBeanInfo.addClass(inData.trim());
                inClasses = false;
            } else if (inJMXBeans && inBean && !inThread &&
                    qName.compareToIgnoreCase(CACHE) == 0) {
                String cacheResult = inData.trim();
                if (cacheResult.equalsIgnoreCase("yes") || 
                        cacheResult.equalsIgnoreCase("true")) {
                    jmxBeanInfo.setCacheResults(true);
                }
                inCache = false;
            } else if (inJMXBeans && inBean && !inThread &&
                    qName.compareToIgnoreCase(CACHE_TIMEOUT) == 0) {
                inCacheTimeout = false;
                jmxBeanInfo.setCacheTimeout(Long.parseLong(inData.trim()));
            } else if (inJMXBeans && inBean && !inThread &&
                    qName.compareToIgnoreCase(TRANSACTION) == 0) {
                String transactionResult = inData.trim();
                if (transactionResult.equalsIgnoreCase("yes") || 
                        transactionResult.equalsIgnoreCase("true")) {
                    jmxBeanInfo.setTransaction(true);
                }
                inTransaction = false;
            } else if (inJMXBeans && inBean && !inThread &&
                    qName.compareToIgnoreCase(OBJECT_NAME) == 0) {
                jmxBeanInfo.setObjectName(inData.trim());
                inObjectName = false;
            } else if (inJMXBeans && inBean && !inThread &&
                    qName.compareToIgnoreCase(BIND_NAME) == 0) {
                jmxBeanInfo.setBindName(inData.trim());
                inBindName = false;
            } else if (inJMXBeans && inBean && !inThread &&
                    qName.compareToIgnoreCase(ROLE) == 0) {
                jmxBeanInfo.setRole(inData.trim());
                inRole = false;
            } else if (inJMXBeans && inBean && !inThread &&
                    qName.compareToIgnoreCase(USERNAME) == 0) {
                jmxBeanInfo.setUsername(inData.trim());
                inUsername = false;
            } else if (inJMXBeans && inBean &&
                    qName.compareToIgnoreCase(THREAD) == 0) {
                if (threadInfo.isInitialized() == false) {
                    throw new SAXException(
                            "Invalid threading information was supplied [class]" 
                            + "[username] and [number] must be supplied.");
                }
                jmxBeanInfo.addThreadInfo(threadInfo);
                inThread = false;
            }
            // coadunation beans
            else if (inCoadunation &&
                    qName.compareToIgnoreCase(BEANS) == 0) {
                inBeans = false;
            } else if (inBeans &&
                    qName.compareToIgnoreCase(BEAN) == 0) {
                if (beanInfo.isInitialized() == false) {
                    throw new SAXException(
                            "The Coadunation bean information has not been setup " +
                            "correctly. Must contain [interface][class]" +
                            "[bindName][role].");
                }
                deploymentInfo.addBean(beanInfo);
                inBean = false;
            } else if (inBeans && inBean && !inThread &&
                    qName.compareToIgnoreCase(INTERFACE_NAME) == 0) {
                inInterface = false;
                beanInfo.setInterfaceName(inData.trim());
            } else if (inBeans && inBean && !inThread &&
                    qName.compareToIgnoreCase(CLASS_NAME) == 0) {
                inClassName = false;
                beanInfo.setClassName(inData.trim());
            } else if (inBeans && inBean && !inThread &&
                    qName.compareToIgnoreCase(CLASSES) == 0) {
                inClasses = false;
                beanInfo.addClass(inData.trim());
            } else if (inBeans && inBean && !inThread &&
                    qName.compareToIgnoreCase(CACHE) == 0) {
                String cacheResult = inData.trim();
                if (cacheResult.equalsIgnoreCase("yes") || 
                        cacheResult.equalsIgnoreCase("true")) {
                    beanInfo.setCacheResults(true);
                }
                inCache = false;
            } else if (inBeans && inBean && !inThread &&
                    qName.compareToIgnoreCase(CACHE_TIMEOUT) == 0) {
                inCacheTimeout = false;
                beanInfo.setCacheTimeout(Long.parseLong(inData.trim()));
            } else if (inBeans && inBean && !inThread &&
                    qName.compareToIgnoreCase(TRANSACTION) == 0) {
                String transactionResult = inData.trim();
                if (transactionResult.equalsIgnoreCase("yes") || 
                        transactionResult.equalsIgnoreCase("true")) {
                    beanInfo.setTransaction(true);
                }
                inCache = false;
            } else if (inBeans && inBean && !inThread &&
                    qName.compareToIgnoreCase(BIND_NAME) == 0) {
                inBindName = false;
                beanInfo.setBindName(inData.trim());
            } else if (inBeans && inBean && !inThread &&
                    qName.compareToIgnoreCase(ROLE) == 0) {
                inRole = false;
                beanInfo.setRole(inData.trim());
            } else if (inBeans && inBean && !inThread &&
                    qName.compareToIgnoreCase(USERNAME) == 0) {
                beanInfo.setUsername(inData.trim());
                inUsername = false;
            } else if (inBeans && inBean &&
                    qName.compareToIgnoreCase(THREAD) == 0) {
                if (threadInfo.isInitialized() == false) {
                    throw new SAXException(
                            "Invalid threading information was supplied [class]" +
                            "[username] and [number] must be supplied.");
                }
                beanInfo.addThreadInfo(threadInfo);
                inThread = false;
            }
            // setup the thread information
            else if (((inBeans && inBean && inThread) || 
                    (inJMXBeans && inBean && inThread)) && 
                    qName.compareToIgnoreCase(CLASS_NAME) == 0) {
                threadInfo.setClassName(inData.trim());
                inClassName = false;
            } else if (((inBeans && inBean && inThread) || 
                    (inJMXBeans && inBean && inThread)) && 
                    qName.compareToIgnoreCase(USERNAME) == 0) {
                threadInfo.setUsername(inData.trim());
                inUsername = false;
            } else if (((inBeans && inBean && inThread) || 
                    (inJMXBeans && inBean && inThread)) && 
                    qName.compareToIgnoreCase(THREAD_NUMBER) == 0) {
                threadInfo.setThreadNumber(Integer.parseInt(inData.trim()));
                inThreadNumber = false;
            }
        }
    }
    
    
    // the class member variables
    private CoadunationHandler handler = null;
    private DeploymentInfo deploymentInfo = null;
    
    /** 
     * Creates a new instance of CoadunationParser 
     *
     * @param sourceXML The source xml document.
     *
     * @throws DeploymentException
     */
    public CoadunationParser(String sourceXML) throws DeploymentException {
        try {
            deploymentInfo = new DeploymentInfo();
            handler = new CoadunationHandler(deploymentInfo);
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            InputSource source = new InputSource( 
                    new StringReader(sourceXML) ); 
            parser.parse(source,handler);
        } catch (Exception ex) {
            throw new DeploymentException(
                    "Failed to parse the source xml document :" + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * The method to retrieve the deployment information.
     *
     * @return The object containing the deployment information.
     */
    public DeploymentInfo getDeploymentInfo() {
        return deploymentInfo;
    }
}
