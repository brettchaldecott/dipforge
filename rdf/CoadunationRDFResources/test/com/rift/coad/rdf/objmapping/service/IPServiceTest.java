/*
 * CoadunationRDFResources: The rdf resource object mappings.
 * Copyright (C) 2009  2015 Burntjam
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
 * IPServiceTest.java
 */

package com.rift.coad.rdf.objmapping.service;

import junit.framework.TestCase;

// coadunation semantics
import com.rift.coad.rdf.semantic.*;
import com.rift.coad.rdf.semantic.config.Basic;


/**
 *
 * @author brett
 */
public class IPServiceTest extends TestCase {
    
    public IPServiceTest(String testName) {
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
     * Test of getId method, of class Organisation.
     */
    public void testIPService() throws Exception {
        System.out.println("testIPService");
        SessionManager manager = Basic.initSessionManager();
        Session session = manager.getSession();

        IPService service1 = new IPService("1", "test1", "test-1");
        session.persist(service1);
        IPService service2 = new IPService("test2", "test-2");
        session.persist(service2);

        session.dump(System.out, "RDF/XML-ABBREV");
    }

}
