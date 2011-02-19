/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.semantic.persistance.jena;

import com.rift.coad.rdf.semantic.persistance.PersistanceConstants;
import com.rift.coad.rdf.semantic.persistance.PersistanceIdentifier;
import com.rift.coad.rdf.semantic.persistance.PersistanceManagerFactory;
import com.rift.coad.rdf.semantic.persistance.PersistanceQuery;
import com.rift.coad.rdf.semantic.persistance.PersistanceResource;
import com.rift.coad.rdf.semantic.persistance.PersistanceResultRow;
import com.rift.coad.rdf.semantic.persistance.PersistanceTransaction;
import com.rift.coad.rdf.semantic.persistance.PersistanceUnknownException;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.util.List;
import java.util.Properties;
import junit.framework.TestCase;

/**
 *
 * @author brettc
 */
public class JenaPersistanceSessionTest extends TestCase {
    
    public JenaPersistanceSessionTest(String testName) {
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
     * Test of beginTransaction method, of class JenaPersistanceSession.
     */
    public void testBeginTransaction() throws Exception {
        System.out.println("beginTransaction");
        Properties properties = new Properties();
        properties.put(PersistanceConstants.PERSISTANCE_MANAGER_CLASS,
                "com.rift.coad.rdf.semantic.persistance.jena.JenaPersistanceManager");
        properties.put(JenaStoreTypes.JENA_STORE_TYPE, JenaStoreTypes.XML);
        JenaPersistanceManager persistanceManager =
                (JenaPersistanceManager)PersistanceManagerFactory.init(properties);

        JenaPersistanceSession instance = 
                (JenaPersistanceSession)persistanceManager.getSession();
        PersistanceTransaction expResult = instance.getTransaction();
        PersistanceTransaction result = instance.getTransaction();
        assertEquals(expResult, result);
    }

    /**
     * Test of getResource method, of class JenaPersistanceSession.
     */
    public void testGetResource_URI() throws Exception {
        System.out.println("getResource");
        URI uri = new URI(
                "http://www.coadunation.net/schema/rdf/1.0/organisation#"
                + "Organisation/com.rift.coad.rdf.objmapping.organisation.Organisation/2");
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

        JenaPersistanceManager persistanceManager =
                (JenaPersistanceManager)PersistanceManagerFactory.init(properties);

        JenaPersistanceSession instance =
                (JenaPersistanceSession)persistanceManager.getSession();
        //PersistanceResource expResult = null;
        JenaPersistanceResource result =
                (JenaPersistanceResource)instance.getResource(uri);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        System.out.println("Properties : " + result.listProperties());
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getResource method, of class JenaPersistanceSession.
     */
    public void testGetResource_PersistanceIdentifier() throws Exception {
        System.out.println("getResource");
        URI uri = new URI(
                "http://www.coadunation.net/schema/rdf/1.0/organisation#"
                + "Organisation/com.rift.coad.rdf.objmapping.organisation.Organisation/2");
        String namespace = "http://www.coadunation.net/schema/rdf/1.0/organisation";
        String localName = "Organisation/com.rift.coad.rdf.objmapping.organisation.Organisation/2";
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

        JenaPersistanceManager persistanceManager =
                (JenaPersistanceManager)PersistanceManagerFactory.init(properties);
        PersistanceIdentifier identifier = PersistanceIdentifier.
                getInstance(namespace, localName);
        JenaPersistanceSession instance = 
                (JenaPersistanceSession)persistanceManager.getSession();
        PersistanceResource result = instance.getResource(identifier);
        assertEquals(uri.toString(), result.getURI().toString());
    }

    /**
     * Test of createResource method, of class JenaPersistanceSession.
     */
    public void testCreateResource_URI() throws Exception {
        System.out.println("createResource");
        URI uri = new URI(
                "http://www.coadunation.net/schema/rdf/1.0/organisation#"
                + "Organisation/com.rift.coad.rdf.objmapping.organisation.Organisation/3");
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
        JenaPersistanceManager persistanceManager =
                (JenaPersistanceManager)PersistanceManagerFactory.init(properties);
        JenaPersistanceSession instance =
                (JenaPersistanceSession)persistanceManager.getSession();
        PersistanceResource result = instance.createResource(uri);
        assertEquals(uri.toURL(), result.getURI().toURL());
    }

    
    /**
     * Test of createResource method, of class JenaPersistanceSession.
     */
    public void testCreateResource_PersistanceIdentifier() throws Exception {
        System.out.println("createResource");
        URI uri = new URI(
                "http://www.coadunation.net/schema/rdf/1.0/organisation#"
                + "Organisation/com.rift.coad.rdf.objmapping.organisation.Organisation/2");
        String namespace = "http://www.coadunation.net/schema/rdf/1.0/organisation";
        String localName = "Organisation/com.rift.coad.rdf.objmapping.organisation.Organisation/2";
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

        JenaPersistanceManager persistanceManager =
                (JenaPersistanceManager)PersistanceManagerFactory.init(properties);
        PersistanceIdentifier identifier = PersistanceIdentifier.
                getInstance(namespace, localName);
        JenaPersistanceSession instance =
                (JenaPersistanceSession)persistanceManager.getSession();
        PersistanceResource result = instance.createResource(identifier);
        assertEquals(uri.toString(), result.getURI().toString());
    }

    /**
     * Test of removeResource method, of class JenaPersistanceSession.
     */
    public void testRemoveResource_URI() throws Exception {
        System.out.println("removeResource");
        URI uri = new URI(
                "http://www.coadunation.net/schema/rdf/1.0/organisation#"
                + "Organisation/com.rift.coad.rdf.objmapping.organisation.Organisation/2");
        String namespace = "http://www.coadunation.net/schema/rdf/1.0/organisation";
        String localName = "Organisation/com.rift.coad.rdf.objmapping.organisation.Organisation/2";
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

        JenaPersistanceManager persistanceManager =
                (JenaPersistanceManager)PersistanceManagerFactory.init(properties);
        JenaPersistanceSession instance =
                (JenaPersistanceSession)persistanceManager.getSession();
        instance.removeResource(uri);
        try {
            instance.getResource(uri);
            fail("The resource was not removed.");
        } catch (PersistanceUnknownException ex) {
            // ignore
        }
    }

    /**
     * Test of removeResource method, of class JenaPersistanceSession.
     */
    public void testRemoveResource_PersistanceIdentifier() throws Exception {
        System.out.println("removeResource");
        URI uri = new URI(
                "http://www.coadunation.net/schema/rdf/1.0/organisation#"
                + "Organisation/com.rift.coad.rdf.objmapping.organisation.Organisation/2");
        String namespace = "http://www.coadunation.net/schema/rdf/1.0/organisation";
        String localName = "Organisation/com.rift.coad.rdf.objmapping.organisation.Organisation/2";
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

        JenaPersistanceManager persistanceManager =
                (JenaPersistanceManager)PersistanceManagerFactory.init(properties);
        PersistanceIdentifier identifier = PersistanceIdentifier.
                getInstance(namespace, localName);
        JenaPersistanceSession instance =
                (JenaPersistanceSession)persistanceManager.getSession();
        instance.removeResource(identifier);
        try {
            instance.getResource(identifier);
            fail("The resource was not removed.");
        } catch (PersistanceUnknownException ex) {
            // ignore
        }
    }

    /**
     * Test of createQuery method, of class JenaPersistanceSession.
     */
    public void testCreateQuery() throws Exception {
        System.out.println("createQuery");
        String queryStr = "SELECT ?s WHERE { ?s a <http://www.coadunation.net/schema/rdf/1.0/organisation#Organisation> ." +
                "?s  <http://www.coadunation.net/schema/rdf/1.0/organisation#Id>  ${id}.}";
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

        JenaPersistanceManager persistanceManager =
                (JenaPersistanceManager)PersistanceManagerFactory.init(properties);
        JenaPersistanceSession instance =
                (JenaPersistanceSession)persistanceManager.getSession();
        List<PersistanceResultRow> result = instance.createQuery(queryStr).
                setString("id","1").execute();
        if (result.size() == 0) {
            fail("No results");
        } else {
            PersistanceResultRow row = result.get(0);
            PersistanceResource resource = row.get(PersistanceResource.class, 0);
            assertEquals("http://www.coadunation.net/schema/rdf/1.0/organisation#"
                    + "Organisation/com.rift.coad.rdf.objmapping.organisation.Organisation/1",
                    resource.getURI().toString());
        }
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of dumpXML method, of class JenaPersistanceSession.
     */
    public void testDumpXML() throws Exception {
        System.out.println("dumpXML");
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

        JenaPersistanceManager persistanceManager =
                (JenaPersistanceManager)PersistanceManagerFactory.init(properties);
        JenaPersistanceSession instance =
                (JenaPersistanceSession)persistanceManager.getSession();
        //String expResult = new String(buffer);
        String result = instance.dumpXML();
        assertEquals(instance.dumpXML(), result);
    }

}
