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
 * BasicResourceTest.java
 */

package com.rift.coad.rdf.semantic.resource;

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
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.rift.coad.rdf.semantic.Resource;
import com.rift.coad.rdf.semantic.session.BasicSession;
import com.rift.coad.rdf.semantic.session.test.TestBean;
import java.io.File;
import java.util.List;

/**
 *
 * @author brett chaldecott
 */
public class BasicResourceTest extends TestCase {
    
    public BasicResourceTest(String testName) {
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
        TestBean bean4 = instance.get(TestBean.class, obj1, "1");
        Resource resource4 = instance.getResource(TestBean.class, obj1, "1");
        bean4 = resource4.get(TestBean.class);
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

        for (Resource resource : resources) {
            TestBean bean = resource.get(TestBean.class);
            if (bean.getId().equals("2")) {
                resource4.removeProperty("http://www.coadunation.net/test#ref", bean, "2");
            }
        }

        resources = resource4.listProperties("http://www.coadunation.net/test#ref");
        this.assertEquals(resources.size(), 1);

        for (Resource resource : resources) {
            TestBean bean = resource.get(TestBean.class);
            if (bean.getId().equals("3")) {
                resource4.removeProperty("http://www.coadunation.net/test#ref", resource);
            }
        }

        resources = resource4.listProperties("http://www.coadunation.net/test#ref");
        this.assertEquals(resources.size(), 0);

        instance.dump(System.out, "RDF/XML-ABBREV");
    }

}
