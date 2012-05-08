/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rift.coad.rdf.semantic;

import com.rift.coad.rdf.semantic.jdo.basic.test.TestBaseObject;
import com.rift.coad.rdf.semantic.jdo.basic.test.TestListObject;
import com.rift.coad.rdf.semantic.jdo.basic.test.TestSubObject;
import com.rift.coad.rdf.semantic.jdo.generator.ClassOntologyGenerator;
import com.rift.coad.rdf.semantic.ontology.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author brett
 */
public class SessionManagerBuilderTest {
    
    public SessionManagerBuilderTest() {
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
     * Test of createManager method, of class SessionManagerBuilder.
     */
    @Test
    public void testCreateManager() throws Exception {
        System.out.println("createManager");
        
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
        
        
        
        Properties properties = new Properties();
        properties.put("persistance_manager", "com.rift.coad.rdf.semantic.persistance.jena.JenaPersistanceManager");
        properties.put("ontology_manager", "com.rift.coad.rdf.semantic.ontology.jena.JenaOntologyManager");
        properties.put("jena_store_type", "xml");
        properties.put("ontology_contents", ontologySession.dumpXML());
        SessionManager result = SessionManagerBuilder.createManager(properties);
        Session session = result.getSession();
        
        TestSubObject subObject = new TestSubObject("subobject1",1,2.2);
        TestBaseObject baseObject = new TestBaseObject("testbase1", 1, subObject);
        session.persist(subObject);
        session.persist(baseObject);
        
        
        Resource resource = session.get(Resource.class, new URI("http://dipforge.sourceforge.net/schema/rdf/1.0/testsubobject#TestSubObject/1"));
        List resourceProperties = resource.listProperties();
        assertEquals(resourceProperties.size(), 4);
        
        for (Object objProp : resourceProperties) {
            Property prop = (Property)objProp;
            System.out.println(prop.toString());
        }
        assertEquals(subObject.getName(),resource.getProperty(String.class, "http://dipforge.sourceforge.net/schema/rdf/1.0/testsubobject#Name"));
        
        OntologyClass ontologyClass = resource.getProperty(OntologyClass.class, RDFConstants.SYNTAX_NAMESPACE + "#" + RDFConstants.TYPE_LOCALNAME);
        assertEquals(ontologyClass.getURI().toString(),"http://dipforge.sourceforge.net/schema/rdf/1.0/testsubobject#TestSubObject");
        
        
        TestSubObject subObject2 = session.get(TestSubObject.class, 1);
        assertEquals("" + subObject.getDub(), "" + subObject2.getDub());
        assertEquals(subObject.getName(), subObject2.getName());
        assertEquals(subObject.getNumber(), subObject2.getNumber());
    }
}
