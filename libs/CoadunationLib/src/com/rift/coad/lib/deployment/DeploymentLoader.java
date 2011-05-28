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
 * XMLConfigurationException.java
 *
 * DeploymentLoader.java
 *
 * This class is reponsible for loading the Coaduncation jar files so that they
 * can be deployed within Coadunation.
 */

// package
package com.rift.coad.lib.deployment;

// class imports
import java.lang.ClassLoader;
import java.io.*;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.common.ClassUtil;
import com.rift.coad.lib.common.FileUtil;
import com.rift.coad.lib.common.JarUtil;
import com.rift.coad.lib.common.RandomGuid;
import com.rift.coad.lib.common.ResourceReader;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.deployment.rmi.TieGenerator;
import com.rift.coad.lib.loader.MasterClassLoader;
import com.rift.coad.lib.thirdparty.ant.CopyFile;
import com.rift.coad.lib.thirdparty.ant.JAR;
import com.rift.coad.lib.thirdparty.ant.JavaC;
import com.rift.coad.lib.thirdparty.ant.RMIC;

/**
 *
 * @author Brett Chaldecott
 */
public class DeploymentLoader {
    
    /**
     * This class is responsible for supplying a lookup mechanism for stub code
     * files based on the class loader that loaded them.
     */
    public static class ClassLoaderLookup {
        // the class singleton
        private static ClassLoaderLookup singleton = null;
        
        // private member variables
        private Map lookupMap = new HashMap();
        private Map<String, DeploymentLoader> loaderMap = new HashMap<String,DeploymentLoader>();
        
        /**
         * The constructor of the class loader lookup
         */
        private ClassLoaderLookup() {
            
        }
        
        
        /**
         * This method returns a singleton reference and creates one if it does
         * not exist.
         *
         * @return a reference to the class loader lookup singleton.
         */
        public static synchronized ClassLoaderLookup getInstance() {
            if (singleton == null) {
                singleton = new ClassLoaderLookup();
            }
            return singleton;
        }
        
        
        /**
         * This method adds a class loader to the list.
         *
         * @param loader The reference to the loader to add.
         */
        protected void addClassLoader(DeploymentLoader loader) {
            lookupMap.put(loader.getClassLoader(),
                    loader.getClientStubCodeName());
            loaderMap.put(loader.getFile().getName(),loader);
        }
        
        
        /**
         * This method removes an entry from the class loader map.
         *
         * @param loader The loader to remove.
         */
        protected void removeClassLoader(DeploymentLoader loader) {
            lookupMap.remove(loader.getClassLoader());
            loaderMap.remove(loader.getFile().getName());
        }
        
        
        /**
         * This method returns the stub code name for the supplied loader.
         *
         * @return The name of stub code file for the supplied loader.
         * @param loader The name of the loader.
         */
        public String getStubCodeForLoader(ClassLoader loader) {
            return (String)lookupMap.get(loader);
        }


        /**
         * This method return the class loader identified by the file name
         *
         * @param name The name of the file.
         * @return The reference to the identified class loader.
         */
        public ClassLoader getClassLoaderByFileName(String name) {
            if (!loaderMap.containsKey(name)) {
                return null;
            }
            return loaderMap.get(name).getClassLoader();
        }
        
    }

    // the class constants
    public static final String META_FILE = "META-INF/coadunation.xml";
    public static final String TEMP_DIRECTORY = "temp_dir";
    private static final String PREFIX = "tmp";
    private static final String SUFFIX = ".jar";
    private static final String CLIENT_STUB = "Client_Stub";
    private static final String SUB_SUFFIX = "_Stub" + SUFFIX;
    
    // the class log variable
    protected Logger log =
            Logger.getLogger(DeploymentLoader.class.getName());
    
    // The classes member variabable
    private Configuration config = null;
    private File file = null;
    private File tmpDir = null;
    private URLClassLoader classLoader = null;
    private DeploymentInfo deploymentInfo = null;
    private long lastModified = 0;
    private boolean successful = false;
    private String clientStubCodeName = null;
    
    /**
     * Creates a new instance of DeploymentLoader
     *
     * @param file The file to load.
     * @exception DeploymentException
     */
    public DeploymentLoader(File file) throws DeploymentException {
        try {
            // instanciate the URLClass loader
            this.file = file;
            lastModified = file.lastModified();
            
            // retrieve the configuration
            config = ConfigurationFactory.getInstance().
                    getConfig(this.getClass());
            
            // create the temporary file
            tmpDir = createTmpDir(file);
            
            // create stubs
            createStubs(tmpDir);
            
            // list the contents of the directory
            File[] jars = FileUtil.filter(tmpDir.listFiles(),".jar");
            
            // url load list
            URL[] urlList = new URL[jars.length + 1];
            urlList[0] = tmpDir.toURL();
            log.debug("Load url :" + tmpDir.toURL());
            for (int index = 0; index < jars.length; index++) {
                log.debug("Load url :" + jars[index].toURL().toString());
                urlList[index + 1] = jars[index].toURL();
            }
            
            // load the temporary file
            classLoader = new URLClassLoader(urlList,
                    MasterClassLoader.getInstance().getLoader());
            
            // retrieve the coadunation.xml file from the meta base directory
            String xmlSource = new ResourceReader(META_FILE,classLoader)
            .getDocument();
            
            // parse the coadunation file
            CoadunationParser xmlParser = new CoadunationParser(xmlSource);
            deploymentInfo = xmlParser.getDeploymentInfo();
            
            // set the successful flag appropriatly
            successful = true;
            
            // add a reference to the class loader lookup
            ClassLoaderLookup.getInstance().addClassLoader(this);
        } catch (Exception ex) {
            log.error("Failed to load the file [" +
                    file.toString() + "] :" + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * The getter method for the file object.
     *
     * @return The file of the deployment loader.
     */
    public File getFile() {
        return file;
    }
    
    
    /**
     * This method returns the temporary directory loaded by this object.
     *
     * @return The temporary file object loaded by this object.
     */
    public File getTmpDir() {
        return tmpDir;
    }
    
    
    /**
     * This method will retrieve the stored last modified time.
     *
     * @return The long containing the last modified time of the file when it
     *      was loaded into memory.
     */
    public long getLastModified() {
        return lastModified;
    }
    
    
    /**
     * Retrieve the deployment information for this bean.
     *
     * @return The reference to the deployment information for this jar.
     */
    public DeploymentInfo getDeploymentInfo() {
        return deploymentInfo;
    }
    
    
    /**
     * This method will return a reference to the requested class.
     *
     * @return The reference to the loaded class.
     * @param className The name of the class to load into memory.
     * @exception DeploymentException
     */
    public Class getClass(String className) throws DeploymentException {
        try {
            return classLoader.loadClass(className);
        } catch (Exception ex) {
            throw new DeploymentException("Failed to load the class : " +
                    className,ex);
        }
    }
    
    
    /**
     * The method to retrieve the class loader.
     *
     * @return The reference to the class loader.
     */
    public ClassLoader getClassLoader() {
        return classLoader;
    }
    
    
    /**
     * This method returns true if this object has been sucessfully loaded.
     *
     * @return TRUE if successfully loaded FALSE if not.
     */
    public boolean getSuccessful() {
        return successful;
    }
    
    /**
     * This method returns the name of the client stub code file.
     *
     * @return The string containing the name of the client stub code file.
     */
    public String getClientStubCodeName() {
        return clientStubCodeName;
    }
    
    
    /**
     * This method returns the hash code for this object which is based on the
     * file path.
     *
     * @return The hash code for this object based on the file path.
     */
    public int hashCode() {
        return file.getPath().hashCode();
    }
    
    
    /**
     * This method determines if the deployment loader contains the same values.
     *
     * @return TRUE if they point at the same file, FALSE if not.
     * @param obj The object to perform the comparison on.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof DeploymentLoader)) {
            return false;
        }
        DeploymentLoader loader = (DeploymentLoader)obj;
        if (null == loader) {
            return false;
        }
        if (loader.getFile().getPath().equals(file.getPath())) {
            return true;
        }
        return false;
    }
    
    
    /**
     * This method will generate a temporary file using the source file.
     *
     * @return The path to the newly created temporary file.
     */
    private File createTmpDir(File source) throws DeploymentException {
        try {
            File tmpDir = new File(config.getString(TEMP_DIRECTORY),
                    RandomGuid.getInstance().getGuid());
            if (tmpDir.mkdirs() == false) {
                throw new DeploymentException("Failed to create the director ["
                        + tmpDir.getAbsolutePath() + "]");
            }
            JarUtil.extract(source,tmpDir);
            return tmpDir;
        } catch (DeploymentException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to create the temporary directory : " + 
                    ex.getMessage(),ex);
            throw new DeploymentException(
                    "Failed to create the temporary directory : " + 
                    ex.getMessage(), ex);
        }
    }
    
    
    /**
     * This method creates the stub code for the deployment loader.
     *
     * @param tmpDir The directory in which all the class from which the stub
     *         code is required can be found
     * @exception DeploymentException
     */
    private void createStubs(File tmpDir) throws DeploymentException {
        try {
            File stubDir = new File(tmpDir,
                    RandomGuid.getInstance().getGuid());
            if (stubDir.mkdirs() == false) {
                throw new DeploymentException("Failed to create the stub " +
                        "directory [" + stubDir.getAbsolutePath() + "]");
            }
            
            // jars
            File[] jars = FileUtil.filter(tmpDir.listFiles(),".jar");
            
            // url load list
            File[] classPath = new File[jars.length + 2];
            classPath[0] = tmpDir;
            for (int index = 0; index < jars.length; index++) {
                classPath[index + 1] = jars[index];
            }
            classPath[classPath.length - 1] = stubDir;
            
            createTies(tmpDir,tmpDir,stubDir);
            
            /*
            This is not required as the RMI class loader will take car of it.
            // list the contents of the directory
            for (int index = 0; index < jars.length; index++) {
                createTies(tmpDir,jars[index],stubDir);
            }*/
            
            // compile the ties
            JavaC javaC = new JavaC(classPath,stubDir,stubDir);
            javaC.compileClasses();
            
            // create the RMI stubs
            RMIC rmic = new RMIC(classPath,stubDir,"**/*.class",stubDir);
            rmic.parse();
            
            // create the jar
            String clientFileName = file.getName();
            clientFileName = clientFileName.substring(0,
                    clientFileName.indexOf('.')) + SUB_SUFFIX;
            File stubJar = new File(tmpDir.getPath() + 
                    File.separator + clientFileName);
            JAR jar = new JAR(stubDir,stubJar);
            jar.archive();
            
            File clientStubJar = new File(config.getString(CLIENT_STUB) + 
                    File.separator + clientFileName);
            CopyFile copyFile = new CopyFile(stubJar,clientStubJar);
            copyFile.copy();
            clientStubCodeName = clientFileName;
        } catch (Exception ex) {
            log.error("Failed to create the stub : " + ex.getMessage(),
                    ex);
            throw new DeploymentException(
                    "Failed to create the stub : " + ex.getMessage(),
                    ex);
        }
    }
    
    
    /**
     * This method creates stub for the given path in a the supplied stub dir.
     *
     * @param tmpDir The temporary directory.
     * @param path The path to use to create the entry from.
     * @param stubDir The stub directory to use.
     * @exception DeploymentException
     */
    private void createTies(File tmpDir, File path, 
            File stubDir) throws DeploymentException {
        try {
            // load the temporary file
            URLClassLoader classLoader = new URLClassLoader(new URL[] 
                    {path.toURL()},MasterClassLoader.getInstance().getLoader());
            
            // perform the check 
            if (classLoader.getResource(META_FILE) == null) {
                // this is not a coadunation entry ignore
                return;
            }
            
            // retrieve the coadunation.xml file from the meta base directory
            String xmlSource = new ResourceReader(META_FILE,classLoader).
                    getDocument();
            
            // parse the coadunation file
            CoadunationParser xmlParser = new CoadunationParser(xmlSource);
            DeploymentInfo deploymentInfo = xmlParser.getDeploymentInfo();
            
            // loop through the entry set
            Map beans = deploymentInfo.getBeans();
            Iterator iter = beans.keySet().iterator();
            while(iter.hasNext()) {
                BeanInfo beanInfo = (BeanInfo)beans.get(iter.next());
                TieGenerator tieGenerator = new TieGenerator(tmpDir,stubDir,
                        beanInfo);
                tieGenerator.generate();
            }
            
            beans = deploymentInfo.getJmxBeans();
            iter = beans.keySet().iterator();
            while(iter.hasNext()) {
                JMXBeanInfo beanInfo = (JMXBeanInfo)beans.get(iter.next());
                TieGenerator tieGenerator = new TieGenerator(tmpDir,stubDir,
                        beanInfo);
                tieGenerator.generate();
            }
            
        } catch (Exception ex) {
            log.error("Failed to create the ties for [" + path.getPath() 
                    + "] because : " + ex.getMessage(), ex);
            throw new DeploymentException(
                    "Failed to create the ties for [" + path.getPath() 
                    + "] because : " + ex.getMessage(), ex);
        }
    }
    
}
