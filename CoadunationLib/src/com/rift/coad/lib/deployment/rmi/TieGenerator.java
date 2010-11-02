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
 * TieGenerator.java
 *
 * This class is responsible for generating the tie classes for the RMI
 * interfaces. These Tie classes will act as container boundary for validation
 * purposes much like the home interface.
 */

// package path
package com.rift.coad.lib.deployment.rmi;

// java imports
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URLClassLoader;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.bean.BeanPattern;
import com.rift.coad.lib.common.ClassUtil;
import com.rift.coad.lib.common.FileUtil;
import com.rift.coad.lib.common.RandomGuid;
import com.rift.coad.lib.common.ResourceReader;
import com.rift.coad.lib.deployment.BeanInfo;

/**
 * This class is responsible for generating the tie classes for the RMI
 * interfaces. These Tie classes will act as container boundary for validation
 * purposes much like the home interface.
 *
 * @author Brett Chaldecott
 */
public class TieGenerator {
    
    // the class constants
    private final static String TIE_TEMPLATE =
            "com/rift/coad/lib/deployment/rmi/resources/TieTemplate.txt";
    private final static String METHOD_TEMPLATE =
            "com/rift/coad/lib/deployment/rmi/resources/BasicMethodTemplate.txt";
    private final static String VOID_METHOD_TEMPLATE =
            "com/rift/coad/lib/deployment/rmi/resources/" +
            "VoidMethodTemplate.txt";
    private final static String CACHE_METHOD_TEMPLATE =
            "com/rift/coad/lib/deployment/rmi/resources/CacheMethodTemplate.txt";
    private final static String KEY_CACHE_ADD_METHOD_TEMPLATE =
            "com/rift/coad/lib/deployment/rmi/resources/" +
            "KeyCacheAddMethodTemplate.txt";
    private final static String KEY_CACHE_FIND_METHOD_TEMPLATE =
            "com/rift/coad/lib/deployment/rmi/resources/" +
            "KeyCacheFindMethodTemplate.txt";
    private final static String KEY_CACHE_REMOVE_METHOD_TEMPLATE =
            "com/rift/coad/lib/deployment/rmi/resources/" +
            "KeyCacheRemoveMethodTemplate.txt";
    private final static String VOID_KEY_CACHE_REMOVE_METHOD_TEMPLATE =
            "com/rift/coad/lib/deployment/rmi/resources/" +
            "VoidKeyCacheRemoveMethodTemplate.txt";
    private final static String TRANSACTION_METHOD_TEMPLATE =
            "com/rift/coad/lib/deployment/rmi/resources/" +
            "TransactionBasicMethodTemplate.txt";
    private final static String TRANSACTION_VOID_METHOD_TEMPLATE =
            "com/rift/coad/lib/deployment/rmi/resources/" +
            "TransactionVoidMethodTemplate.txt";
    private final static String TRANSACTION_CACHE_METHOD_TEMPLATE =
            "com/rift/coad/lib/deployment/rmi/resources/" +
            "TransactionCacheMethodTemplate.txt";
    private final static String TRANSACTION_KEY_CACHE_ADD_METHOD_TEMPLATE =
            "com/rift/coad/lib/deployment/rmi/resources/" +
            "TransactionKeyCacheAddMethodTemplate.txt";
    private final static String TRANSACTION_KEY_CACHE_FIND_METHOD_TEMPLATE =
            "com/rift/coad/lib/deployment/rmi/resources/" +
            "TransactionKeyCacheFindMethodTemplate.txt";
    private final static String TRANSACTION_KEY_CACHE_REMOVE_METHOD_TEMPLATE =
            "com/rift/coad/lib/deployment/rmi/resources/" +
            "TransactionKeyCacheRemoveMethodTemplate.txt";
    private final static String
            TRANSACTION_VOID_KEY_CACHE_REMOVE_METHOD_TEMPLATE =
            "com/rift/coad/lib/deployment/rmi/resources/" +
            "TransactionVoidKeyCacheRemoveMethodTemplate.txt";
    private final static String
            CATCH_EXCEPTION_TEMPLATE =
            "com/rift/coad/lib/deployment/rmi/resources/" +
            "CatchException.txt";
    private final static String
            CATCH_RUNTIME_EXCEPTION_TEMPLATE =
            "com/rift/coad/lib/deployment/rmi/resources/" +
            "CatchRuntimeException.txt";
    private final static String
            CATCH_REMOTE_EXCEPTION_TEMPLATE =
            "com/rift/coad/lib/deployment/rmi/resources/" +
            "CatchRemoteException.txt";
    private final static String METHOD_PARAMETER = "%s%s p%d";
    private final static String PARAMETER = "%s p%d";
    private final static String TIE_CLASS_NAME = "%s_CoadTie";
    
    // the class log variable
    protected Logger log =
            Logger.getLogger(TieGenerator.class.getName());
    
    // private member variables
    private File dir = null;
    private File targetDir = null;
    private BeanInfo beanInfo = null;
    private ClassLoader classLoader = null;
    
    // templates
    private String tieTemplate = null;
    private String basicMethodTemplate = null;
    private String voidMethodTemplate = null;
    private String cacheMethodTemplate = null;
    private String keyCacheAddMethodTemplate = null;
    private String keyCacheFindMethodTemplate = null;
    private String keyCacheRemoveMethodTemplate = null;
    private String voidKeyCacheRemoveMethodTemplate = null;
    private String transactionBasicMethodTemplate = null;
    private String transactionVoidMethodTemplate = null;
    private String transactionCacheMethodTemplate = null;
    private String transactionKeyCacheAddMethodTemplate = null;
    private String transactionKeyCacheFindMethodTemplate = null;
    private String transactionKeyCacheRemoveMethodTemplate = null;
    private String transactionVoidKeyCacheRemoveMethodTemplate = null;
    private String catchTemplate = null;
    private String runtimeCatchTemplate = null;
    private String remoteCatchTemplate = null;
    
    /**
     * Creates a new instance of TieGenerator
     *
     * @param dir The directory containing all the jars.
     * @param targetDir The target directory for the tie class.
     * @param classes The class to generate the ties for.
     * @exception RMIException
     */
    public TieGenerator(File dir, File targetDir, BeanInfo beanInfo) throws
            RMIException {
        this.dir = dir;
        this.targetDir = targetDir;
        this.beanInfo = beanInfo;
        initClassLoader();
        
        try {
            tieTemplate = new ResourceReader(TIE_TEMPLATE).getDocument();
            basicMethodTemplate =
                    new ResourceReader(METHOD_TEMPLATE).getDocument();
            voidMethodTemplate =
                    new ResourceReader(VOID_METHOD_TEMPLATE).getDocument();
            cacheMethodTemplate =
                    new ResourceReader(CACHE_METHOD_TEMPLATE).getDocument();
            keyCacheAddMethodTemplate =
                    new ResourceReader(KEY_CACHE_ADD_METHOD_TEMPLATE)
                    .getDocument();
            keyCacheFindMethodTemplate =
                    new ResourceReader(KEY_CACHE_FIND_METHOD_TEMPLATE)
                    .getDocument();
            keyCacheRemoveMethodTemplate =
                    new ResourceReader(KEY_CACHE_REMOVE_METHOD_TEMPLATE)
                    .getDocument();
            voidKeyCacheRemoveMethodTemplate =
                    new ResourceReader(VOID_KEY_CACHE_REMOVE_METHOD_TEMPLATE)
                    .getDocument();
            transactionBasicMethodTemplate =
                    new ResourceReader(TRANSACTION_METHOD_TEMPLATE).
                    getDocument();
            transactionVoidMethodTemplate =
                    new ResourceReader(TRANSACTION_VOID_METHOD_TEMPLATE).
                    getDocument();
            transactionCacheMethodTemplate =
                    new ResourceReader(
                    TRANSACTION_CACHE_METHOD_TEMPLATE).
                    getDocument();
            transactionKeyCacheAddMethodTemplate =
                    new ResourceReader(
                    TRANSACTION_KEY_CACHE_ADD_METHOD_TEMPLATE)
                    .getDocument();
            transactionKeyCacheFindMethodTemplate =
                    new ResourceReader(
                    TRANSACTION_KEY_CACHE_FIND_METHOD_TEMPLATE)
                    .getDocument();
            transactionKeyCacheRemoveMethodTemplate =
                    new ResourceReader(
                    TRANSACTION_KEY_CACHE_REMOVE_METHOD_TEMPLATE)
                    .getDocument();
            transactionVoidKeyCacheRemoveMethodTemplate =
                    new ResourceReader(
                    TRANSACTION_VOID_KEY_CACHE_REMOVE_METHOD_TEMPLATE)
                    .getDocument();
            catchTemplate =
                    new ResourceReader(
                    CATCH_EXCEPTION_TEMPLATE)
                    .getDocument();
            remoteCatchTemplate = new ResourceReader(
                    CATCH_REMOTE_EXCEPTION_TEMPLATE)
                    .getDocument();
            runtimeCatchTemplate =
                    new ResourceReader(
                    CATCH_RUNTIME_EXCEPTION_TEMPLATE)
                    .getDocument();
        } catch (Exception ex) {
            log.error("Failed retrieve the resource : " + ex.getMessage(),ex);
            throw new RMIException("Failed retrieve the resource : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method generates the Tie classes.
     *
     * @exception RMIException
     */
    public void generate() throws RMIException {
        Class ref = getClass(beanInfo.getClassName());
        createTie(targetDir,ref);
        
        // misc classes
        Vector classes = beanInfo.getClasses();
        for (int index = 0; index < classes.size(); index++) {
            ref = getClass((String)classes.get(index));
            createTie(targetDir,ref);
        }
    }
    
    
    /**
     * This method inits the class loader.
     *
     * @exception RMIException
     */
    private void initClassLoader() throws RMIException {
        try {
            File[] jars = FileUtil.filter(dir.listFiles(),"jar");
            URL[] urls = new URL[jars.length + 1];
            urls[0] = dir.toURL();
            for (int index = 0; index < jars.length; index++) {
                urls[index+1] = jars[index].toURL();
            }
            
            classLoader = new URLClassLoader(
                    urls,this.getClass().getClassLoader());
        } catch (Exception ex) {
            log.error("Failed to init the class loader : " + ex.getMessage(),ex);
            throw new RMIException("Failed to init the class loader : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns a reference to the class using the class name.
     *
     * @return The reference to the class to retrieve.
     * @param className The name of the class to retrieve.
     * @exception RMIException
     */
    private Class getClass(String className) throws RMIException {
        try {
            return classLoader.loadClass(className);
        } catch (Exception ex) {
            log.error("Failed to retrieve the class: " + ex.getMessage(),ex);
            throw new RMIException("Failed to retrieve the class : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method creates the package directory.
     *
     * @return The package directory
     * @param ref The reference to the class to retrieve.
     * @exception RMIException
     */
    private File createPackageDir(File tmpDir,Class ref) throws RMIException {
        try {
            String className = ref.getCanonicalName();
            StringTokenizer tokenizer = new StringTokenizer(className,".");
            File base = tmpDir;
            int numElements = tokenizer.countTokens() - 1;
            String seperator = "";
            for(int count = 0; count < numElements; count++) {
                File newDir = new File(base,tokenizer.nextToken());
                newDir.mkdir();
                base = newDir;
            }
            return base;
        } catch (Exception ex) {
            log.error("Failed to create the package: " + ex.getMessage(),ex);
            throw new RMIException("Failed to create the package dir : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method creates the tie file.
     *
     * @param ref The reference to the class.
     * @param packageDir The package directory.
     * @exception RMIException
     */
    private void createTie(File tmpDir,Class ref) throws RMIException {
        try {
            if (!ClassUtil.testForParent(ref,
                    java.rmi.Remote.class)) {
                // ignore as it has not remote interface
                return;
            }
            File packageDir = createPackageDir(tmpDir,ref);
            Vector interfaces = getInterfaces(ref);
            String methods = "";
            Iterator iter = interfaces.iterator();
            String interfaceList = getImplements(ref);
            while(iter.hasNext()) {
                Class interfaceRef = (Class)iter.next();
                Method[] methodList = interfaceRef.getDeclaredMethods();
                for (int index = 0; index < methodList.length; index++) {
                    methods += generateCodeForMethod(methodList[index],ref);
                }
            }
            
            // parse the tie class template
            String tieClassName = String.format(TIE_CLASS_NAME,ref.
                    getSimpleName());
            String tieClass = new String(tieTemplate);
            tieClass = tieClass.replaceAll("%package%","package " +
                    ref.getPackage().getName() + ";");
            tieClass = tieClass.replaceAll("%tieClassName%",tieClassName);
            tieClass = tieClass.replaceAll("%implements%",interfaceList);
            tieClass = tieClass.replaceAll("%target%",ref.getName());
            tieClass = tieClass.replaceAll("%methods%",methods);
            
            // write out the tie class
            File tieClassFile = new File(packageDir,tieClassName + ".java");
            FileWriter fileWriter = new FileWriter(tieClassFile);
            fileWriter.write(tieClass);
            fileWriter.close();
        } catch (Exception ex) {
            log.error("Failed to create the tie : " + ex.getMessage(),ex);
            throw new RMIException("Failed to create the tie : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This recursive method retrieve a lit of interfaces that inherit from
     * Remote.
     *
     * @return The list of interfaces that inherit from remote.
     * @param ref The reference to the class to retrieve the list for.
     * @exception RMIException
     */
    private Vector getInterfaces(Class ref) throws RMIException {
        if (ref == null) {
            return new Vector();
        } else if (ref.getName().equals(java.lang.Object.class.getName())) {
            return new Vector();
        }
        Vector results = new Vector();
        Class[] interfaces = ref.getInterfaces();
        for (int index = 0; index < interfaces.length; index++) {
            if (ClassUtil.testForParent(interfaces[index],"java.rmi.Remote")) {
                results.add(interfaces[index]);
                results.addAll(getInterfaces(interfaces[index]));
            }
        }
        results.addAll(getInterfaces(ref.getSuperclass()));
        return results;
    }
    
    /**
     * This method returns the list of RMI implemented interfaces.
     *
     * @return The list of implemented interfaces.
     * @param ref The reference.
     * @exception RMIException
     */
    public String getImplements(Class ref) throws RMIException {
        String interfaceList = "";
        Class[] interfaces = ref.getInterfaces();
        for (int index = 0; index < interfaces.length; index++) {
            if (ClassUtil.testForParent(interfaces[index],"java.rmi.Remote")) {
                interfaceList +=  "," + interfaces[index].getName();
            }
        }
        return interfaceList;
    }
    
    
    /**
     * This method generates the code for a given method.
     *
     * @return The string containing the code for the tie method.
     * @param method The method that the code is getting generated for.
     * @exception RMIException
     */
    private String generateCodeForMethod(Method method,Class ref) 
            throws RMIException {
        try {
            String methodName = method.getName();
            String returnType = classTypeToString(method.getReturnType());
            Class[] parametersTypes = method.getParameterTypes();
            Method targetMethod = ref.getMethod(methodName,
                    method.getParameterTypes());
            String fullParameters = "";
            String parameters = "";
            String seperator = "";
            for (int index = 0; index < parametersTypes.length; index++) {
                fullParameters += String.format(METHOD_PARAMETER,seperator,
                        classTypeToString(parametersTypes[index]),index+1);
                parameters += String.format(PARAMETER,seperator,index+1);
                seperator = ",";
            }

            // validate exceptions on interface
            Class[] exceptionList = method.getExceptionTypes();
            if (!findException(exceptionList, java.rmi.RemoteException.class)) {
                throw new RMIException("Interface method [" + method.getName() + 
                        "] must throw a java.rmi.RemoteException");
            }

            // process the exceptions
            String exceptions = "";
            seperator = "";
            String catchBlock = "";
            Class[] targetExceptionList = targetMethod.getExceptionTypes();
            for (int index = 0; index < exceptionList.length; index++) {
                exceptions += seperator + exceptionList[index].getName();
                seperator = ",";
                
                if (findException(targetExceptionList,exceptionList[index])) {
                    if (exceptionList[index] == 
                            com.rift.coad.lib.security.SecurityException.class)
                    {
                        // ignore the security exception as these get wrapped
                        // specially.
                        continue;
                    }
                    else if (exceptionList[index] == java.rmi.RemoteException.class)
                    {
                        catchBlock += remoteCatchTemplate.replaceAll("%exception%",
                                exceptionList[index].getName());
                    }
                    else
                    {
                        catchBlock += catchTemplate.replaceAll("%exception%",
                                exceptionList[index].getName());
                    }
                }
            }
            catchBlock += runtimeCatchTemplate;
            catchBlock = catchBlock.replace("%methodName%",methodName);

            // retrieve a copy of the template to parse
            String template = new String(getTemplate(method));
            template = template.replaceAll("%methodName%",methodName);
            template = template.replaceAll("%returnType%",returnType);
            template = template.replaceAll("%fullParameters%",fullParameters);
            template = template.replaceAll("%parameters%",parameters);
            template = template.replaceAll("%catchs%",catchBlock);
            template = template.replaceAll("%exceptions%",exceptions);
            return template;
        } catch (RMIException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RMIException("Failed to generate the exception list : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method converts the class type to a string.
     *
     * @return The string definition of the object.
     * @param The type of object.
     */
    private String classTypeToString(Class type) {
        // deal with a basic type
        if (type.isArray()) {
            return type.getComponentType().getName() + "[]";
        }
        return type.getName();
    }
    
    
    /**
     * This method returns the template for the given class.
     *
     * @return The string containing the template.
     * @param method The method to retrieve the template for.
     * @exception RMIException
     */
    private String getTemplate(Method method) throws RMIException {
        
        // retrieve the method and parameter list and check for a remove cache
        // method.
        String methodName = method.getName();
        Pattern removePattern = Pattern.compile(BeanPattern.REMOVE_PATTERN);
        Class[] parametersTypes = method.getParameterTypes();
        Class returnType = method.getReturnType();
        if (beanInfo.getCacheResults() && !beanInfo.getTransaction() &&
                (parametersTypes.length == 1) && (
                ClassUtil.testForParent(parametersTypes[0],
                java.io.Serializable.class)) && removePattern.matcher(methodName)
                .find() && returnType.getName().equals("void")) {
            return voidKeyCacheRemoveMethodTemplate;
        } else if (beanInfo.getCacheResults() && beanInfo.getTransaction()
        && (parametersTypes.length == 1) && (
                ClassUtil.testForParent(parametersTypes[0],
                java.io.Serializable.class)) && removePattern.matcher(methodName)
                .find() && returnType.getName().equals("void")) {
            return transactionVoidKeyCacheRemoveMethodTemplate;
        } else if (beanInfo.getCacheResults() && !beanInfo.getTransaction()
        && (parametersTypes.length == 1) && (
                ClassUtil.testForParent(parametersTypes[0],
                java.io.Serializable.class)) && removePattern.matcher(methodName)
                .find()) {
            return keyCacheRemoveMethodTemplate;
        } else if (beanInfo.getCacheResults() && beanInfo.getTransaction()
        && (parametersTypes.length == 1) && (
                ClassUtil.testForParent(parametersTypes[0],
                java.io.Serializable.class)) && removePattern.matcher(methodName)
                .find()) {
            return transactionKeyCacheRemoveMethodTemplate;
        } else if (beanInfo.getCacheResults() &&
                removePattern.matcher(methodName).find()) {
            throw new RMIException("The bean cache result flag is set but " +
                    "there is no way identifying this entry in cache. Must " +
                    "supply the index key as a serializable object to a remove " +
                    "method.");
        } else if (returnType.getName().equals("void") &&
                !beanInfo.getTransaction()) {
            return voidMethodTemplate;
        } else if (returnType.getName().equals("void") &&
                beanInfo.getTransaction()) {
            return transactionVoidMethodTemplate;
        }
        
        // check for a basic return type
        if (!ClassUtil.testForParent(returnType,java.rmi.Remote.class) &&
                !beanInfo.getTransaction()) {
            return basicMethodTemplate;
        } else if (!ClassUtil.testForParent(returnType,java.rmi.Remote.class) &&
                beanInfo.getTransaction()) {
            return transactionBasicMethodTemplate;
        }
        
        // check for a caching return type
        Pattern findPattern = Pattern.compile(BeanPattern.FIND_PATTERN);
        Pattern addPattern = Pattern.compile(BeanPattern.ADD_PATTERN);
        if (beanInfo.getCacheResults() && !beanInfo.getTransaction() &&
                addPattern.matcher(methodName).find()) {
            return keyCacheAddMethodTemplate;
        } else if (beanInfo.getCacheResults() && beanInfo.getTransaction() &&
                addPattern.matcher(methodName).find()) {
            return transactionKeyCacheAddMethodTemplate;
        } else if (beanInfo.getCacheResults() && !beanInfo.getTransaction()
        && findPattern.matcher(methodName).find()&&
                (parametersTypes.length == 1) && (
                ClassUtil.testForParent(parametersTypes[0],
                java.io.Serializable.class))) {
            return keyCacheFindMethodTemplate;
        } else if (beanInfo.getCacheResults() && beanInfo.getTransaction()
        && findPattern.matcher(methodName).find()&&
                (parametersTypes.length == 1) && (
                ClassUtil.testForParent(parametersTypes[0],
                java.io.Serializable.class))) {
            return transactionKeyCacheFindMethodTemplate;
        } else if (beanInfo.getCacheResults()
        && findPattern.matcher(methodName).find()&&
                (parametersTypes.length != 1)) {
            throw new RMIException("The bean cache result flag is set but " +
                    "there is no way identifying this entry in cache. Must " +
                    "supply the index key as a serializable object to a find " +
                    "method.");
        }
        
        // return a none caching method
        if (beanInfo.getTransaction()) {
            return transactionCacheMethodTemplate;
        } else {
            return cacheMethodTemplate;
        }
    }
    
    
    /**
     * This method will return true if the exception is found in the list of
     * exceptions.
     *
     * @return TRUE if the exception is found.
     * @param list The list of exceptions to check.
     * @param exception The exception to perform the comparison for.
     */
    private boolean findException(Class[] list, Class exception) {
        for (int index = 0; index < list.length; index++) {
            if (list[index].getName().equals(exception.getName())) {
                return true;
            }
        }
        return false;
    }
}
