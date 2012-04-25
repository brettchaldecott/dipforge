/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.semantic.ontology.jena;

import com.rift.coad.rdf.semantic.ontology.OntologyClass;
import com.rift.coad.rdf.semantic.ontology.OntologyConstants;
import com.rift.coad.rdf.semantic.ontology.OntologyManagerFactory;
import com.rift.coad.rdf.semantic.ontology.OntologyProperty;
import com.rift.coad.rdf.semantic.ontology.OntologyTransaction;
import com.rift.coad.rdf.semantic.types.DataType;
import com.rift.coad.rdf.semantic.types.XSDDataDictionary;
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
public class JenaOntologySessionTest extends TestCase {
    
    public JenaOntologySessionTest(String testName) {
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
     * Test of getTransaction method, of class JenaOntologySession.
     */
    public void testGetTransaction() throws Exception {
        System.out.println("getTransaction");
        Properties properties = new Properties();
        properties.put(OntologyConstants.ONTOLOGY_MANAGER_CLASS,
                "com.rift.coad.rdf.semantic.ontology.jena.JenaOntologyManager");
        File testFile = new File("./base.xml");
        FileInputStream in = new FileInputStream(testFile);
        byte[] buffer = new byte[(int)testFile.length()];
        in.read(buffer);
        in.close();
        properties.put(OntologyConstants.ONTOLOGY_CONTENTS, new String(buffer));
        JenaOntologyManager ontologyManager =
                (JenaOntologyManager)OntologyManagerFactory.init(properties);
        JenaOntologySession instance = (JenaOntologySession)ontologyManager.getSession();
        OntologyTransaction expResult = instance.getTransaction();
        OntologyTransaction result = instance.getTransaction();
        assertEquals(expResult, result);
    }

    /**
     * Test of createProperty method, of class JenaOntologySession.
     */
    public void testCreateProperty() throws Exception {
        System.out.println("createProperty");
        URI uri = new URI("http://www.test.com/testing/1.0.1/test#my");
        Properties properties = new Properties();
        properties.put(OntologyConstants.ONTOLOGY_MANAGER_CLASS,
                "com.rift.coad.rdf.semantic.ontology.jena.JenaOntologyManager");
        File testFile = new File("./base.xml");
        FileInputStream in = new FileInputStream(testFile);
        byte[] buffer = new byte[(int)testFile.length()];
        in.read(buffer);
        in.close();
        properties.put(OntologyConstants.ONTOLOGY_CONTENTS, new String(buffer));
        JenaOntologyManager ontologyManager =
                (JenaOntologyManager)OntologyManagerFactory.init(properties);
        JenaOntologySession instance = (JenaOntologySession)ontologyManager.getSession();
        OntologyProperty result = instance.createProperty(uri);
        assertEquals(uri.toString(), result.getURI().toString());
        System.out.println(instance.dumpXML());
        result.setType(XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_STRING));
        System.out.println(instance.dumpXML());
        DataType type = result.getType();
        assertEquals(type.getURI().toString(),XSDDataDictionary.XSD_NAMESPACE +
                "#" + XSDDataDictionary.XSD_STRING);
    }

    
    /**
     * Test of getProperty method, of class JenaOntologySession.
     */
    public void testGetProperty() throws Exception {
        System.out.println("getProperty");
        URI uri = new URI("http://www.test.com/testing/1.0.1/test#my");
        Properties properties = new Properties();
        properties.put(OntologyConstants.ONTOLOGY_MANAGER_CLASS,
                "com.rift.coad.rdf.semantic.ontology.jena.JenaOntologyManager");
        File testFile = new File("./base.xml");
        FileInputStream in = new FileInputStream(testFile);
        byte[] buffer = new byte[(int)testFile.length()];
        in.read(buffer);
        in.close();
        properties.put(OntologyConstants.ONTOLOGY_CONTENTS, new String(buffer));
        JenaOntologyManager ontologyManager =
                (JenaOntologyManager)OntologyManagerFactory.init(properties);
        JenaOntologySession instance = (JenaOntologySession)ontologyManager.getSession();
        OntologyProperty createdResult = instance.createProperty(uri);
        OntologyProperty result = instance.getProperty(uri);
        assertEquals(createdResult.getURI().toString(), result.getURI().toString());
        result.setType(XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_STRING));
        result = instance.getProperty(uri);
        DataType type = result.getType();
        assertEquals(type.getURI().toString(),XSDDataDictionary.XSD_NAMESPACE +
                "#" + XSDDataDictionary.XSD_STRING);
    }


    /**
     * Test of removeProperty method, of class JenaOntologySession.
     */
    public void testRemoveProperty() throws Exception {
        System.out.println("removeProperty");
        URI uri = new URI("http://www.test.com/testing/1.0.1/test#my");
        Properties properties = new Properties();
        properties.put(OntologyConstants.ONTOLOGY_MANAGER_CLASS,
                "com.rift.coad.rdf.semantic.ontology.jena.JenaOntologyManager");
        File testFile = new File("./base.xml");
        FileInputStream in = new FileInputStream(testFile);
        byte[] buffer = new byte[(int)testFile.length()];
        in.read(buffer);
        in.close();
        properties.put(OntologyConstants.ONTOLOGY_CONTENTS, new String(buffer));
        JenaOntologyManager ontologyManager =
                (JenaOntologyManager)OntologyManagerFactory.init(properties);
        JenaOntologySession instance = (JenaOntologySession)ontologyManager.getSession();
        OntologyProperty createdResult = instance.createProperty(uri);
        instance.removeProperty(uri);
        try {
            instance.getProperty(uri);
            fail("Retieve the property.");
        } catch (Exception ex) {
            // ignore
        }
    }

    /**
     * Test of createClass method, of class JenaOntologySession.
     */
    public void testCreateClass() throws Exception {
        System.out.println("createClass");
        URI uri = new URI("http://www.test.com/testing/1.0.1/test#my");
        URI classUri = new URI("http://www.test.com/testing/1.0.1/test#myClass");
        Properties properties = new Properties();
        properties.put(OntologyConstants.ONTOLOGY_MANAGER_CLASS,
                "com.rift.coad.rdf.semantic.ontology.jena.JenaOntologyManager");
        File testFile = new File("./base.xml");
        FileInputStream in = new FileInputStream(testFile);
        byte[] buffer = new byte[(int)testFile.length()];
        in.read(buffer);
        in.close();
        properties.put(OntologyConstants.ONTOLOGY_CONTENTS, new String(buffer));
        JenaOntologyManager ontologyManager =
                (JenaOntologyManager)OntologyManagerFactory.init(properties);
        JenaOntologySession instance = (JenaOntologySession)ontologyManager.getSession();
        OntologyProperty createdResult = instance.createProperty(uri);
        OntologyClass result = instance.createClass(classUri);
        assertEquals(classUri.toString(), result.getURI().toString());
        createdResult.setType(XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_STRING));
        result.addProperty(createdResult);
        System.out.println(instance.dumpXML());

    }

    /**
     * Test of getClass method, of class JenaOntologySession.
     */
    public void testGetClass() throws Exception {
        System.out.println("getClass");
        URI uri = new URI("http://www.test.com/testing/1.0.1/test#my");
        URI classUri = new URI("http://www.test.com/testing/1.0.1/test#myClass");
        URI uri2 = new URI("http://www.test.com/testing/1.0.1/test2#my");
        URI classUri2 = new URI("http://www.test.com/testing/1.0.1/test2#myClass");
        
        Properties properties = new Properties();
        properties.put(OntologyConstants.ONTOLOGY_MANAGER_CLASS,
                "com.rift.coad.rdf.semantic.ontology.jena.JenaOntologyManager");
        File testFile = new File("./base.xml");
        FileInputStream in = new FileInputStream(testFile);
        byte[] buffer = new byte[(int)testFile.length()];
        in.read(buffer);
        in.close();
        properties.put(OntologyConstants.ONTOLOGY_CONTENTS, new String(buffer));
        JenaOntologyManager ontologyManager =
                (JenaOntologyManager)OntologyManagerFactory.init(properties);
        JenaOntologySession instance = (JenaOntologySession)ontologyManager.getSession();
        OntologyProperty createdResult = instance.createProperty(uri);
        OntologyProperty createdResult2 = instance.createProperty(uri2);
        OntologyClass result = instance.createClass(classUri);
        OntologyClass result2 = instance.createClass(classUri2);
        assertEquals(classUri.toString(), result.getURI().toString());
        createdResult.setType(XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_STRING));
        result.addProperty(createdResult);
        result2.addProperty(createdResult2);
        OntologyClass expResult = instance.getClass(classUri);
        assertEquals(expResult.getURI().toString(), result.getURI().toString());
        System.out.println(instance.dumpXML());
        List<OntologyProperty> listProperty = expResult.listProperties();
        System.out.println(listProperty);
        assertEquals(1, listProperty.size());

    
        properties.put(OntologyConstants.ONTOLOGY_CONTENTS, instance.dumpXML());
        ontologyManager =
                (JenaOntologyManager)OntologyManagerFactory.init(properties);
        instance = (JenaOntologySession)ontologyManager.getSession();
        expResult = instance.getClass(classUri);
        listProperty = expResult.listProperties();
        System.out.println(listProperty);
        assertEquals(1, listProperty.size());
    }

    /**
     * Test of removeClass method, of class JenaOntologySession.
     */
    public void testRemoveClass() throws Exception {
        System.out.println("removeClass");
        URI uri = new URI("http://www.test.com/testing/1.0.1/test#my");
        URI classUri = new URI("http://www.test.com/testing/1.0.1/test#myClass");
        Properties properties = new Properties();
        properties.put(OntologyConstants.ONTOLOGY_MANAGER_CLASS,
                "com.rift.coad.rdf.semantic.ontology.jena.JenaOntologyManager");
        File testFile = new File("./base.xml");
        FileInputStream in = new FileInputStream(testFile);
        byte[] buffer = new byte[(int)testFile.length()];
        in.read(buffer);
        in.close();
        properties.put(OntologyConstants.ONTOLOGY_CONTENTS, new String(buffer));
        JenaOntologyManager ontologyManager =
                (JenaOntologyManager)OntologyManagerFactory.init(properties);
        JenaOntologySession instance = (JenaOntologySession)ontologyManager.getSession();
        OntologyProperty createdResult = instance.createProperty(uri);
        OntologyClass result = instance.createClass(classUri);
        assertEquals(classUri.toString(), result.getURI().toString());
        createdResult.setType(XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_STRING));
        result.addProperty(createdResult);
        instance.removeClass(classUri);
        try {
            instance.getClass(classUri);
            fail("Failed to remove the class");
        } catch (Exception ex) {
            // ignore
        }
    }

    /**
     * Test of dumpXML method, of class JenaOntologySession.
     */
    public void testDumpXML() throws Exception {
        System.out.println("dumpXML");
        System.out.println("createClass");
        URI uri = new URI("http://www.test.com/testing/1.0.1/test#my");
        URI classUri = new URI("http://www.test.com/testing/1.0.1/test#myClass");
        Properties properties = new Properties();
        properties.put(OntologyConstants.ONTOLOGY_MANAGER_CLASS,
                "com.rift.coad.rdf.semantic.ontology.jena.JenaOntologyManager");
        File testFile = new File("./base.xml");
        FileInputStream in = new FileInputStream(testFile);
        byte[] buffer = new byte[(int)testFile.length()];
        in.read(buffer);
        in.close();
        properties.put(OntologyConstants.ONTOLOGY_CONTENTS, new String(buffer));
        JenaOntologyManager ontologyManager =
                (JenaOntologyManager)OntologyManagerFactory.init(properties);
        JenaOntologySession instance = (JenaOntologySession)ontologyManager.getSession();
        OntologyProperty createdResult = instance.createProperty(uri);
        OntologyClass result = instance.createClass(classUri);
        assertEquals(classUri.toString(), result.getURI().toString());
        createdResult.setType(XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_STRING));
        result.addProperty(createdResult);
        assertEquals(instance.dumpXML(), instance.dumpXML());
    }

}
