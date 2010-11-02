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
 * SPARQLListTest.java
 */

package com.rift.coad.rdf.semantic.query.sparql;

import com.rift.coad.rdf.semantic.RDFFormats;
import com.rift.coad.rdf.semantic.SPARQLResultRow;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.SessionManager;
import com.rift.coad.rdf.semantic.config.Basic;
import com.rift.coad.rdf.semantic.session.test.TestBean;
import java.util.List;
import junit.framework.TestCase;

/**
 * This sparql list method.
 *
 * @author brett chaldecott
 */
public class SPARQLListTest extends TestCase {
    
    public SPARQLListTest(String testName) {
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
     * Test of generateQuery method, of class SPARQLTokenParser.
     */
    public void testQuery() throws Exception {
        System.out.println("query");
        SessionManager manager = Basic.initSessionManager();
        Session session = manager.getSession();
        session.persist(new TestBean("1", "test-name", "test-type"));
        session.persist(new TestBean("2", "test-name", "test-type"));
        session.persist(new TestBean("3", "test-name", "test-type"));
        List<SPARQLResultRow> list = session.createSPARQLQuery(
                "SELECT ?s ?Name ?Type WHERE " +
                " { ?s a <http://www.coadunation.net/schema/rdf/1.0/test#TestBean> ." +
                "  ?s <http://www.coadunation.net/schema/rdf/1.0/test#Name> ?Name ." +
                " ?s <http://www.coadunation.net/schema/rdf/1.0/test#ObjectType> ?Type . " +
                " ?s <http://www.coadunation.net/schema/rdf/1.0/test#ObjectType> ${Type} . }")
                .setString("Type", "test-type").execute();
        for (SPARQLResultRow row : list) {
            System.out.println (row.get(0).cast(String.class));
            TestBean bean = row.get(0).cast(TestBean.class);
            System.out.println("Id:" + bean.getId());
            System.out.println("Name: " + row.get("Name").cast(String.class));
            System.out.println("Type: " + row.get("Type").cast(String.class));
        }
        System.out.println(session.dump(RDFFormats.XML_ABBREV));
    }
}
