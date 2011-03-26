/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.semantic.jdo.basic;

import com.rift.coad.rdf.semantic.Resource;
import java.util.Calendar;
import com.rift.coad.rdf.semantic.jdo.generator.ClassOntologyGenerator;
import com.rift.coad.rdf.semantic.jdo.basic.test.TestListObject;
import com.rift.coad.rdf.semantic.jdo.basic.test.TestBaseObject;
import com.rift.coad.rdf.semantic.jdo.basic.test.TestSubObject;
import java.util.List;
import java.util.ArrayList;
import com.rift.coad.rdf.semantic.ontology.OntologyManagerFactory;
import com.rift.coad.rdf.semantic.ontology.OntologyManager;
import com.rift.coad.rdf.semantic.ontology.OntologyConstants;
import com.rift.coad.rdf.semantic.persistance.PersistanceManagerFactory;
import com.rift.coad.rdf.semantic.persistance.PersistanceManager;
import com.rift.coad.rdf.semantic.persistance.jena.JenaStoreTypes;
import com.rift.coad.rdf.semantic.persistance.PersistanceConstants;
import java.util.Properties;
import com.rift.coad.rdf.semantic.ontology.OntologySession;
import com.rift.coad.rdf.semantic.persistance.PersistanceResource;
import com.rift.coad.rdf.semantic.persistance.PersistanceSession;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author brettc
 */
public class BasicJDOProxyFactoryTest {

    public BasicJDOProxyFactoryTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of createJDOProxy method, of class BasicJDOProxyFactory.
     */
    @Test
    public void testCreateJDOProxy() throws Exception {
        System.out.println("createJDOProxy");
        Class type = TestBaseObject.class;
        Properties properties = new Properties();
        properties.put(PersistanceConstants.PERSISTANCE_MANAGER_CLASS,
                "com.rift.coad.rdf.semantic.persistance.jena.JenaPersistanceManager");
        properties.put(JenaStoreTypes.JENA_STORE_TYPE, JenaStoreTypes.XML);
        PersistanceManager persistanceManager =
                PersistanceManagerFactory.init(properties);
        PersistanceSession persistanceSession = persistanceManager.getSession();


        Properties ontologyProperties = new Properties();
        ontologyProperties.put(OntologyConstants.ONTOLOGY_MANAGER_CLASS,
                "com.rift.coad.rdf.semantic.ontology.jena.JenaOntologyManager");
        OntologyManager ontologyManager = OntologyManagerFactory.init(ontologyProperties);
        OntologySession ontologySession = ontologyManager.getSession();

        List<Class> types = new ArrayList<Class>();
        types.add(TestSubObject.class);
        types.add(TestBaseObject.class);
        types.add(TestListObject.class);
        ClassOntologyGenerator generator = new ClassOntologyGenerator(
                ontologySession, types);
        generator.processTypes();
        
        TestSubObject subObject = new TestSubObject("subobject1",1,2.2);
        TestBaseObject baseObject = new TestBaseObject("testbase1", 1, subObject);
        baseObject.getListObjects().add(new TestListObject("testlist1", 1111, Calendar.getInstance()));
        baseObject.getListObjects().add(new TestListObject("testlist2", 2222, Calendar.getInstance()));
        BasicJDOPersistanceHandler instance = new BasicJDOPersistanceHandler(baseObject,
            persistanceSession, ontologySession);
        PersistanceResource persistanceResource = instance.persist();
        Object expResult = null;
        TestBaseObject testBase = BasicJDOProxyFactory.createJDOProxy(
                type, persistanceSession, persistanceResource, ontologySession);
        System.out.println(testBase.toString());
        assertEquals(1, testBase.getCount());
        assertEquals("testbase1", testBase.getName());

        System.out.println(testBase.getSubObject().getDub());
        System.out.println(testBase.toString());
        //assertEquals(2.2, testBase.getSubObject().getDub());
        testBase.getSubObject().setDub(3.3);
        System.out.println(testBase.getSubObject().getDub());

        Resource resource = (Resource)testBase;
        System.out.println("Resource uri is : " + resource.getURI());

        List<TestListObject> objects = testBase.getListObjects();
        assertEquals(2, objects.size());
        boolean found = false;
        for (TestListObject testObj : objects) {
            System.out.println(testObj.getName());
            if (testObj.getName().equals("testlist1")) {
                found = true;
                break;
            }
        }
        assertEquals(true, found);

        resource = BasicJDOProxyFactory.createJDOProxy(
                Resource.class, persistanceSession, persistanceResource,
                ontologySession);

        System.out.println("RDF XML [" + persistanceSession.dumpXML() + "]");

    }

}