/*
 * CoaduntionSemantics: The semantic library for coadunation os
 * Copyright (C) 2009  Rift IT Contracting
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
 * BasicSession.java
 */

package com.rift.coad.rdf.semantic.session;

import com.rift.coad.rdf.semantic.Query;
import com.rift.coad.rdf.semantic.SPARQLQuery;
import com.rift.coad.rdf.semantic.Transaction;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import junit.framework.TestCase;
import thewebsemantic.Bean2RDF;
import thewebsemantic.RDF2Bean;
import thewebsemantic.binding.Jenabean;
import thewebsemantic.Sparql;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.rift.coad.rdf.semantic.Resource;
import com.rift.coad.rdf.semantic.session.test.TestBean;
import java.io.File;
import java.util.List;


/**
 *
 * @author brett
 */
public class BasicSessionTest extends TestCase {
    
    public BasicSessionTest(String testName) {
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
     * Test of getTransaction method, of class BasicSession.
     */
    public void testGetTransaction() throws Exception {
        System.out.println("getTransaction");
        BasicSession instance = new BasicSession(ModelFactory.createDefaultModel(),ModelFactory.createDefaultModel());
        Transaction expResult = instance.getTransaction();
        Transaction result = instance.getTransaction();
        assertEquals(expResult, result);
    }

    /**
     * Test of persist method, of class BasicSession.
     */
    public void testPersist_InputStream() throws Exception {
        System.out.println("persist");
        InputStream in = new FileInputStream("test.xml");
        BasicSession instance = new BasicSession(ModelFactory.createDefaultModel(),ModelFactory.createDefaultModel());
        instance.persist(in);
        in.close();
    }

    /**
     * Test of persist method, of class BasicSession.
     */
    public void testPersist_String() throws Exception {
        System.out.println("persist");
        File file = new File("test2.xml");
        FileInputStream in = new FileInputStream(file);
        byte[] bytes = new byte[(int)file.length()];
        in.read(bytes, 0, (int)file.length());
        String rdf = new String(bytes);
        BasicSession instance = new BasicSession(ModelFactory.createDefaultModel(),ModelFactory.createDefaultModel());
        instance.persist(rdf);
        String result = instance.dump("RDF/XML-ABBREV");
        this.assertEquals(result.length(), rdf.length());
    }

    /**
     * Test of persist method, of class BasicSession.
     */
    public void testPersist_Object() throws Exception {
        System.out.println("persist");
        Object obj1 = new TestBean("1", "test-name1", "test-type");
        Object obj2 = new TestBean("2", "test-name2", "test-type");
        Object obj3 = new TestBean("3", "test-name3", "test-type");
        BasicSession instance = new BasicSession(ModelFactory.createDefaultModel(),ModelFactory.createDefaultModel());
        Resource resource1 = instance.persist(obj1);
        Resource resource2 = instance.persist(obj2);
        Resource resource3 = instance.persist(obj3);
        resource1.addProperty("http://www.coadunation.net/test#ref", obj2);
        resource1.addProperty("http://www.coadunation.net/test#ref", resource3);
        instance.dump(System.out, "RDF/XML-ABBREV");
        Resource resource4 = instance.getResource(TestBean.class, obj1, "1");
        TestBean bean4 = resource4.get(TestBean.class);
        bean4.setName("test-bean4");
        resource4.update(bean4);
        instance.dump(System.out, "RDF/XML-ABBREV");
        this.assertEquals(bean4.getId(), "1");
        List<Resource> resources = resource4.listProperties("http://www.coadunation.net/test#ref");
        boolean found1 = false;
        boolean found2 = false;
        this.assertEquals(resources.size(), 2);
        for (Resource resource : resources) {
            TestBean bean = resource.get(TestBean.class);
            if (bean.getId().equals("2")) {
                found1 = true;
            }
            if (bean.getId().equals("3")) {
                found2 = true;
            }
        }
        if (!found1 || !found2) {
            fail(String.format("Failed to retrieve the properties %b %b",found1,found2));
        }
    }

    /**
     * Test of dump method, of class BasicSession.
     */
    public void testDump_String() throws Exception {
        System.out.println("dump");
        File file = new File("test2.xml");
        FileInputStream in = new FileInputStream(file);
        byte[] bytes = new byte[(int)file.length()];
        in.read(bytes, 0, (int)file.length());
        String rdf = new String(bytes);
        BasicSession instance = new BasicSession(ModelFactory.createDefaultModel(),ModelFactory.createDefaultModel());
        instance.persist(rdf);
        String result = instance.dump("RDF/XML-ABBREV");
        this.assertEquals(result.length(), rdf.length());
    }

    /**
     * Test of dump method, of class BasicSession.
     */
    public void testDump_OutputStream_String() throws Exception {
        System.out.println("dump");
        File file = new File("test2.xml");
        FileInputStream in = new FileInputStream(file);
        byte[] bytes = new byte[(int)file.length()];
        in.read(bytes, 0, (int)file.length());
        String rdf = new String(bytes);
        BasicSession instance = new BasicSession(ModelFactory.createDefaultModel(),ModelFactory.createDefaultModel());
        instance.persist(rdf);
        instance.dump(System.out, "RDF/XML-ABBREV");
    }

    /**
     * Test of remove method, of class BasicSession.
     */
    public void testRemove_Object() throws Exception {
        System.out.println("remove");
        Object obj = new TestBean();
        BasicSession instance = new BasicSession(ModelFactory.createDefaultModel(),ModelFactory.createDefaultModel());
        instance.persist(obj);
        String value1 = instance.dump("RDF/XML-ABBREV");
        instance.remove(obj);
        String value2 = instance.dump("RDF/XML-ABBREV");
        if (value1.length() == value2.length()) {
            fail("The remove method has not worked");
        } else {
            System.out.println("Old length [" + value1.length() + "][" + value2.length() + "]");
        }
    }

    /**
     * Test of remove method, of class BasicSession.
     */
    public void testRemove_String() throws Exception {
        System.out.println("remove");
        File file = new File("test2.xml");
        FileInputStream in = new FileInputStream(file);
        byte[] bytes = new byte[(int)file.length()];
        in.read(bytes, 0, (int)file.length());
        String rdf = new String(bytes);
        BasicSession instance = new BasicSession(ModelFactory.createDefaultModel(),ModelFactory.createDefaultModel());
        instance.persist(rdf);
        String value1 = instance.dump("RDF/XML-ABBREV");
        instance.remove(rdf);
        String value2 = instance.dump("RDF/XML-ABBREV");
        if (value1.length() == value2.length()) {
            fail("The remove method has not worked");
        } else {
            System.out.println("Old length [" + value1.length() + "][" + value2.length() + "]");
        }
    }

    /**
     * Test of getType method, of class BasicSession.
     */
    public void testGetType() throws Exception {
        System.out.println("getType");
        TestBean obj = new TestBean("", "test-name", "test-type");
        Model config = ModelFactory.createDefaultModel();
        Model store = ModelFactory.createDefaultModel();
        Bean2RDF configBeanWritter = new Bean2RDF(config);
        configBeanWritter.save(obj);
        BasicSession instance = new BasicSession(config,store);
        TestBean type = instance.getType(TestBean.class, "test-type");
        assertEquals(obj.getName(), type.getName());
        assertEquals(obj.getObjectType(), type.getObjectType());
        type = instance.getType(TestBean.class, TestBean.class.getName());
        assertEquals(null, type.getName());
        assertEquals(null, type.getObjectType());
    }

    /**
     * Test of get method, of class BasicSession.
     */
    public void testGet_3args_1() throws Exception {
        System.out.println("get");
        TestBean obj = new TestBean("id1", "test-name", "test-type");
        TestBean obj2 = new TestBean("id2", "test-name", "test-type");
        obj2.setManagementObject("managementObject");
        obj2.setMemberVariableName("memberVariableName");
        Model config = ModelFactory.createDefaultModel();
        Model store = ModelFactory.createDefaultModel();
        Bean2RDF configBeanWritter = new Bean2RDF(store);
        configBeanWritter.save(obj);
        configBeanWritter.save(obj2);
        BasicSession instance = new BasicSession(config,store);
        TestBean result = instance.get(TestBean.class, obj, "id1");
        assertEquals(obj.getId(), result.getId());
        assertEquals(obj.getName(), result.getName());
        assertEquals(obj.getObjectType(), result.getObjectType());
        TestBean result2 = instance.get(TestBean.class, obj2, "id2");
        assertEquals(obj2.getId(), result2.getId());
        assertEquals(obj2.getName(), result2.getName());
        assertEquals(obj2.getObjectType(), result2.getObjectType());
        assertEquals(obj2.getManagementObject(), result2.getManagementObject());
        assertEquals(obj2.getMemberVariableName(), result2.getMemberVariableName());
    }

    /**
     * Test of get method, of class BasicSession.
     */
    public void testGet_3args_2() throws Exception {
        System.out.println("get");
        TestBean obj = new TestBean("id1", "test-name", "test-type");
        Model config = ModelFactory.createDefaultModel();
        Model store = ModelFactory.createDefaultModel();
        Bean2RDF configBeanWritter = new Bean2RDF(store);
        configBeanWritter.save(obj);
        BasicSession instance = new BasicSession(config,store);
        TestBean result1 = instance.get(TestBean.class, "test-type", "id1");
        assertEquals(obj.getId(), result1.getId());
        assertEquals(obj.getName(), result1.getName());
        assertEquals(obj.getObjectType(), result1.getObjectType());

    }

    /**
     * Test of createInstance method, of class BasicSession.
     */
    public void testCreateInstance() throws Exception {
        System.out.println("createInstance");
        TestBean obj = new TestBean("", "test-name", "test-type");
        Model config = ModelFactory.createDefaultModel();
        Model store = ModelFactory.createDefaultModel();
        Bean2RDF configBeanWritter = new Bean2RDF(config);
        configBeanWritter.save(obj);
        BasicSession instance = new BasicSession(config,store);
        TestBean type = instance.createInstance(TestBean.class, "test-type");
        assertEquals(obj.getName(), type.getName());
        assertEquals(obj.getObjectType(), type.getObjectType());
        type = instance.createInstance(TestBean.class, TestBean.class.getName());
        assertEquals(null, type.getName());
        assertEquals(null, type.getObjectType());
    }

    /**
     * Test of createQuery method, of class BasicSession.
     */
    public void testCreateQuery() throws Exception {
        System.out.println("createQuery");
        String queryString = "SELECT ?s WHERE { ?s a <http://www.coadunation.net/schema/rdf/1.0/organisation#Organisation> ." +
                "?s  <http://www.coadunation.net/schema/rdf/1.0/organisation#Id>  \"1\".}";
        BasicSession instance = new BasicSession(ModelFactory.createDefaultModel(),ModelFactory.createDefaultModel());
        Query result = instance.createQuery(queryString);
        if (!(result instanceof com.rift.coad.rdf.semantic.query.BasicQuery)) {
            fail("Did not instanciate the appropriate query object.");
        }
    }

    /**
     * Test of createSPARQLQuery method, of class BasicSession.
     */
    public void testCreateSPARQLQuery() throws Exception {
        System.out.println("createQuery");
        String queryString = "SELECT ?s WHERE { ?s a <http://www.coadunation.net/schema/rdf/1.0/organisation#Organisation> ." +
                "?s  <http://www.coadunation.net/schema/rdf/1.0/organisation#Id>  \"1\".}";
        BasicSession instance = new BasicSession(ModelFactory.createDefaultModel(),ModelFactory.createDefaultModel());
        SPARQLQuery result = instance.createSPARQLQuery(queryString);
        if (!(result instanceof com.rift.coad.rdf.semantic.query.BasicSPARQLQuery)) {
            fail("Did not instanciate the appropriate query object.");
        }
    }

}
