/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.semantic.persistance.jena;

import com.rift.coad.rdf.semantic.persistance.PersistanceConstants;
import com.rift.coad.rdf.semantic.persistance.PersistanceManagerFactory;
import com.rift.coad.rdf.semantic.persistance.PersistanceSession;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import junit.framework.TestCase;

/**
 *
 * @author brettc
 */
public class JenaPersistanceManagerTest extends TestCase {
    
    public JenaPersistanceManagerTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of getName method, of class JenaPersistanceManager.
     */
    public void testGetName() throws Exception {
        System.out.println("getName");
        Properties properties = new Properties();
        properties.put(PersistanceConstants.PERSISTANCE_MANAGER_CLASS,
                "com.rift.coad.rdf.semantic.persistance.jena.JenaPersistanceManager");
        properties.put(JenaStoreTypes.JENA_STORE_TYPE, JenaStoreTypes.XML);
        JenaPersistanceManager instance = 
                (JenaPersistanceManager)PersistanceManagerFactory.init(properties);
        String expResult = "JenaPersistanceManager";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getRDFStore method, of class JenaPersistanceManager.
     */
    public void testGetRDFStore() throws Exception {
        System.out.println("getRDFStore");
        Properties properties = new Properties();
        properties.put(PersistanceConstants.PERSISTANCE_MANAGER_CLASS,
                "com.rift.coad.rdf.semantic.persistance.jena.JenaPersistanceManager");
        properties.put(JenaStoreTypes.JENA_STORE_TYPE, JenaStoreTypes.XML);
        JenaPersistanceManager instance = 
                (JenaPersistanceManager)PersistanceManagerFactory.init(properties);
        String expResult = "Jena";
        String result = instance.getRDFStore();
        assertEquals(expResult, result);
    }

    /**
     * Test of getVersion method, of class JenaPersistanceManager.
     */
    public void testGetVersion() throws Exception {
        System.out.println("getVersion");
        Properties properties = new Properties();
        properties.put(PersistanceConstants.PERSISTANCE_MANAGER_CLASS,
                "com.rift.coad.rdf.semantic.persistance.jena.JenaPersistanceManager");
        properties.put(JenaStoreTypes.JENA_STORE_TYPE, JenaStoreTypes.XML);
        JenaPersistanceManager instance = 
                (JenaPersistanceManager)PersistanceManagerFactory.init(properties);
        String expResult = "1.0.1";
        String result = instance.getVersion();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSession method, of class JenaPersistanceManager.
     */
    public void testGetSession() throws Exception {
        System.out.println("getSession");
        Properties properties = new Properties();
        properties.put(PersistanceConstants.PERSISTANCE_MANAGER_CLASS,
                "com.rift.coad.rdf.semantic.persistance.jena.JenaPersistanceManager");
        properties.put(JenaStoreTypes.JENA_STORE_TYPE, JenaStoreTypes.XML);
        File testFile = new File("./test.xml");
        FileInputStream in = new FileInputStream(testFile);
        byte[] buffer = new byte[(int)testFile.length()];
        in.read(buffer);
        in.close();
        properties.put(PersistanceConstants.XML_RDF_CONTENTS, new String(buffer));
        JenaPersistanceManager instance =
                (JenaPersistanceManager)PersistanceManagerFactory.init(properties);
        PersistanceSession result = instance.getSession();
        System.out.println(result.dumpXML());
    }

    /**
     * Test of close method, of class JenaPersistanceManager.
     */
    public void testClose() throws Exception {
        System.out.println("close");
        Properties properties = new Properties();
        properties.put(PersistanceConstants.PERSISTANCE_MANAGER_CLASS,
                "com.rift.coad.rdf.semantic.persistance.jena.JenaPersistanceManager");
        properties.put(JenaStoreTypes.JENA_STORE_TYPE, JenaStoreTypes.XML);
        File testFile = new File("./test.xml");
        FileInputStream in = new FileInputStream(testFile);
        byte[] buffer = new byte[(int)testFile.length()];
        in.read(buffer);
        in.close();
        properties.put(PersistanceConstants.XML_RDF_CONTENTS, new String(buffer));
        JenaPersistanceManager instance =
                (JenaPersistanceManager)PersistanceManagerFactory.init(properties);
        instance.close();
        
    }

}
