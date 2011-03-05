/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.semantic.jdo.basic.collection;

import com.rift.coad.rdf.semantic.persistance.PersistanceIdentifier;
import java.util.ArrayList;
import com.rift.coad.rdf.semantic.ontology.OntologySession;
import com.rift.coad.rdf.semantic.ontology.OntologyManagerFactory;
import com.rift.coad.rdf.semantic.ontology.OntologyManager;
import com.rift.coad.rdf.semantic.ontology.OntologyConstants;
import com.rift.coad.rdf.semantic.persistance.PersistanceManagerFactory;
import com.rift.coad.rdf.semantic.persistance.jena.JenaStoreTypes;
import com.rift.coad.rdf.semantic.persistance.PersistanceConstants;
import com.rift.coad.rdf.semantic.persistance.PersistanceSession;
import com.rift.coad.rdf.semantic.persistance.PersistanceManager;
import java.util.Properties;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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
public class BasicJDOListTest {

    // private member variables
    private PersistanceManager persistanceManager;
    private PersistanceSession persistanceSession;
    private OntologyManager ontologyManager;
    private OntologySession ontologySession;
    private List<String> base;
    private PersistanceIdentifier identifier;

    public BasicJDOListTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        Properties properties = new Properties();
        properties.put(PersistanceConstants.PERSISTANCE_MANAGER_CLASS,
                "com.rift.coad.rdf.semantic.persistance.jena.JenaPersistanceManager");
        properties.put(JenaStoreTypes.JENA_STORE_TYPE, JenaStoreTypes.XML);
        persistanceManager =
                PersistanceManagerFactory.init(properties);
        persistanceSession = persistanceManager.getSession();


        Properties ontologyProperties = new Properties();
        ontologyProperties.put(OntologyConstants.ONTOLOGY_MANAGER_CLASS,
                "com.rift.coad.rdf.semantic.ontology.jena.JenaOntologyManager");
        ontologyManager = OntologyManagerFactory.init(ontologyProperties);
        ontologySession = ontologyManager.getSession();

        base = new ArrayList<String>();
        base.add(new String("test1"));
        base.add(new String("test2"));
        base.add(new String("test3"));

        identifier = PersistanceIdentifier.getInstance("http://test/bob", "test");
    }

    @After
    public void tearDown() {
        
    }

    /**
     * Test of size method, of class BasicJDOList.
     */
    @Test
    public void testSize() throws Exception {
        System.out.println("size");
        BasicJDOList<String> instance = new BasicJDOList<String>(base, persistanceSession,
                ontologySession,identifier);
        int expResult = 3;
        int result = instance.size();
        assertEquals(expResult, result);
    }

    /**
     * Test of isEmpty method, of class BasicJDOList.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        BasicJDOList<String> instance = new BasicJDOList<String>(base, persistanceSession,
                ontologySession,identifier);
        boolean expResult = false;
        boolean result = instance.isEmpty();
        assertEquals(expResult, result);
    }

    /**
     * Test of contains method, of class BasicJDOList.
     */
    @Test
    public void testContains() {
        System.out.println("contains");
        Object o = new String("test1");
        BasicJDOList<String> instance = new BasicJDOList<String>(base, persistanceSession,
                ontologySession,identifier);
        boolean expResult = true;
        boolean result = instance.contains(o);
        assertEquals(expResult, result);
    }

    /**
     * Test of iterator method, of class BasicJDOList.
     */
    @Test
    public void testIterator() {
        System.out.println("iterator");
        BasicJDOList<String> instance = new BasicJDOList<String>(base, persistanceSession,
                ontologySession,identifier);
        Iterator result = instance.iterator();
        assertEquals(true, result.hasNext());
        result.next();
        assertEquals(true, result.hasNext());
        result.next();
        assertEquals(true, result.hasNext());
        result.next();
        assertEquals(false, result.hasNext());
    }

    /**
     * Test of toArray method, of class BasicJDOList.
     */
    @Test
    public void testToArray_0args() {
        System.out.println("toArray");
        BasicJDOList<String> instance = new BasicJDOList<String>(base, persistanceSession,
                ontologySession,identifier);
        Object[] result = instance.toArray();
        assertEquals(3, result.length);
    }

    /**
     * Test of toArray method, of class BasicJDOList.
     */
    @Test
    public void testToArray_ObjectArr() {
        System.out.println("toArray");
        BasicJDOList<String> instance = new BasicJDOList<String>(base, persistanceSession,
                ontologySession,identifier);
        String[] result = instance.toArray(new String[0]);
        assertEquals(3, result.length);
    }

    /**
     * Test of add method, of class BasicJDOList.
     */
    @Test
    public void testAdd_Object() {
        System.out.println("add");
        String e = new String("bob");
        BasicJDOList<String> instance = new BasicJDOList<String>(base, persistanceSession,
                ontologySession,identifier);
        boolean expResult = false;
        try {
            boolean result = instance.add(e);
            fail("This object should throw");
        } catch (Exception ex) {
            // ignore
        }
    }

    /**
     * Test of remove method, of class BasicJDOList.
     */
    @Test
    public void testRemove_Object() {
        System.out.println("remove");
        String o = "Test";
        BasicJDOList<String> instance = new BasicJDOList<String>(base, persistanceSession,
                ontologySession,identifier);
        boolean expResult = false;
        try {
            boolean result = instance.remove(o);
            fail("This object should throw");
        } catch (Exception ex) {
            // ignore
        }
    }

    /**
     * Test of containsAll method, of class BasicJDOList.
     */
    @Test
    public void testContainsAll() {
        System.out.println("containsAll");
        List<String> c = new ArrayList<String>();
        c.add("test1");
        BasicJDOList<String> instance = new BasicJDOList<String>(base, persistanceSession,
                ontologySession,identifier);
        boolean expResult = true;
        boolean result = instance.containsAll(c);
        assertEquals(expResult, result);
    }

    /**
     * Test of addAll method, of class BasicJDOList.
     */
    @Test
    public void testAddAll_Collection() {
        System.out.println("addAll");
        List<String> c = new ArrayList<String>();
        c.add("test4");
        BasicJDOList<String> instance = new BasicJDOList<String>(base, persistanceSession,
                ontologySession,identifier);
        try {
            boolean expResult = true;
            boolean result = instance.addAll(c);
            fail("This method is expected to throw");
        } catch (Exception ex) {
            // ignore
        }
    }

    /**
     * Test of addAll method, of class BasicJDOList.
     */
    @Test
    public void testAddAll_int_Collection() {
        System.out.println("addAll");
        int index = 0;
        List<String> c = new ArrayList<String>();
        c.add("test4");
        BasicJDOList<String> instance = new BasicJDOList<String>(base, persistanceSession,
                ontologySession,identifier);
        try {
            boolean expResult = false;
            boolean result = instance.addAll(index, c);
            fail("This method must throw");
        } catch (Exception ex) {
            // ignore
        }
    }

    /**
     * Test of removeAll method, of class BasicJDOList.
     */
    @Test
    public void testRemoveAll() {
        System.out.println("removeAll");
        List<String> c = new ArrayList<String>();
        c.add("test2");
        BasicJDOList<String> instance = new BasicJDOList<String>(base, persistanceSession,
                ontologySession,identifier);
        try {
            boolean expResult = false;
            boolean result = instance.removeAll(c);
            fail("This method removes the throw");
        } catch (Exception ex) {
            // ignore
        }
    }

    /**
     * Test of retainAll method, of class BasicJDOList.
     */
    @Test
    public void testRetainAll() {
        System.out.println("retainAll");
        List<String> c = new ArrayList<String>();
        c.add("test2");
        BasicJDOList<String> instance = new BasicJDOList<String>(base, persistanceSession,
                ontologySession,identifier);
        try {
            boolean expResult = false;
            boolean result = instance.retainAll(c);
            fail("This method must throw");
        } catch (Exception ex) {
            // ignore
        }
    }

    /**
     * Test of clear method, of class BasicJDOList.
     */
    @Test
    public void testClear() {
        System.out.println("clear");
        BasicJDOList<String> instance = new BasicJDOList<String>(base, persistanceSession,
                ontologySession,identifier);
        try {
            instance.clear();
            fail("This method must throw");
        } catch (Exception ex) {
            // ignore
        }
    }

    /**
     * Test of get method, of class BasicJDOList.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        int index = 0;
        BasicJDOList<String> instance = new BasicJDOList<String>(base, persistanceSession,
                ontologySession,identifier);
        String expResult = "test1";
        String result = instance.get(index);
        assertEquals(expResult, result);
    }

    /**
     * Test of set method, of class BasicJDOList.
     */
    @Test
    public void testSet() {
        System.out.println("set");
        int index = 0;
        String element = "test4";
        BasicJDOList<String> instance = new BasicJDOList<String>(base, persistanceSession,
                ontologySession,identifier);
        Object expResult = "test4";
        try {
            Object result = instance.set(index, element);
            fail("This method is expected to throw");
        } catch (Exception ex) {
            // ignore
        }
    }

    /**
     * Test of add method, of class BasicJDOList.
     */
    @Test
    public void testAdd_int_Object() {
        System.out.println("add");
        int index = 0;
        String element = "test5";
        BasicJDOList<String> instance = new BasicJDOList<String>(base, persistanceSession,
                ontologySession,identifier);
        try {
            instance.add(index, element);
            fail("This method is expected to throw");
        } catch (Exception ex) {
            // ignore
        }
    }

    /**
     * Test of remove method, of class BasicJDOList.
     */
    @Test
    public void testRemove_int() {
        System.out.println("remove");
        int index = 0;
        BasicJDOList<String> instance = new BasicJDOList<String>(base, persistanceSession,
                ontologySession,identifier);
        try {
            Object expResult = "test1";
            Object result = instance.remove(index);
            fail ("This method is expected to throw");
        } catch (Exception ex) {
            //
        }
    }

    /**
     * Test of indexOf method, of class BasicJDOList.
     */
    @Test
    public void testIndexOf() {
        System.out.println("indexOf");
        String o = "test2";
        BasicJDOList<String> instance = new BasicJDOList<String>(base, persistanceSession,
                ontologySession,identifier);
        int expResult = 1;
        int result = instance.indexOf(o);
        assertEquals(expResult, result);
    }

    /**
     * Test of lastIndexOf method, of class BasicJDOList.
     */
    @Test
    public void testLastIndexOf() {
        System.out.println("lastIndexOf");
        Object o = "test3";
        BasicJDOList<String> instance = new BasicJDOList<String>(base, persistanceSession,
                ontologySession,identifier);
        int expResult = 2;
        int result = instance.lastIndexOf(o);
        assertEquals(expResult, result);
    }

    /**
     * Test of listIterator method, of class BasicJDOList.
     */
    @Test
    public void testListIterator_0args() {
        System.out.println("listIterator");
        BasicJDOList<String> instance = new BasicJDOList<String>(base, persistanceSession,
                ontologySession,identifier);
        ListIterator result = instance.listIterator();
        assertEquals(true, result.hasNext());
        result.next();
        assertEquals(true, result.hasNext());
        result.next();
        assertEquals(true, result.hasNext());
        result.next();
        assertEquals(false, result.hasNext());
    }

    /**
     * Test of listIterator method, of class BasicJDOList.
     */
    @Test
    public void testListIterator_int() {
        System.out.println("listIterator");
        int index = 0;
        BasicJDOList<String> instance = new BasicJDOList<String>(base, persistanceSession,
                ontologySession,identifier);
        ListIterator expResult = instance.listIterator(index);
        ListIterator result = instance.listIterator(index);
        assertEquals(true, result.hasNext());
        result.next();
        assertEquals(true, result.hasNext());
        result.next();
        assertEquals(true, result.hasNext());
        result.next();
        assertEquals(false, result.hasNext());
    }

    /**
     * Test of subList method, of class BasicJDOList.
     */
    @Test
    public void testSubList() {
        System.out.println("subList");
        int fromIndex = 0;
        int toIndex = 1;
        BasicJDOList<String> instance = new BasicJDOList<String>(base, persistanceSession,
                ontologySession,identifier);
        List expResult = instance.subList(fromIndex, toIndex);
        List result = instance.subList(fromIndex, toIndex);
        assertEquals(expResult.size(), result.size());
    }

}