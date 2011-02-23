/*
 * CoaduntionSemantics: The semantic library for coadunation os
 * Copyright (C) 2011  Rift IT Contracting
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
 * BasicJDOPersistanceHandler.java
 */

package com.rift.coad.rdf.semantic.jdo.basic;

import com.rift.coad.rdf.semantic.persistance.PersistanceSession;
import com.rift.coad.rdf.semantic.ontology.OntologySession;
import com.rift.coad.rdf.semantic.ontology.OntologyManagerFactory;
import com.rift.coad.rdf.semantic.ontology.OntologyManager;
import java.io.FileInputStream;
import java.io.File;
import com.rift.coad.rdf.semantic.ontology.OntologyConstants;
import com.rift.coad.rdf.semantic.persistance.PersistanceManagerFactory;
import com.rift.coad.rdf.semantic.persistance.PersistanceManager;
import com.rift.coad.rdf.semantic.persistance.jena.JenaStoreTypes;
import com.rift.coad.rdf.semantic.persistance.PersistanceConstants;
import java.util.Properties;
import com.rift.coad.rdf.semantic.jdo.basic.test.TestSubObject;
import com.rift.coad.rdf.semantic.jdo.basic.test.TestBaseObject;
import com.rift.coad.rdf.semantic.Resource;
import com.rift.coad.rdf.semantic.persistance.PersistanceResource;
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
public class BasicJDOPersistanceHandlerTest {

    public BasicJDOPersistanceHandlerTest() {
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
     * Test of persist method, of class BasicJDOPersistanceHandler.
     */
    @Test
    public void testPersist() throws Exception {
        System.out.println("persist");
        Properties properties = new Properties();
        properties.put(PersistanceConstants.PERSISTANCE_MANAGER_CLASS,
                "com.rift.coad.rdf.semantic.persistance.jena.JenaPersistanceManager");
        properties.put(JenaStoreTypes.JENA_STORE_TYPE, JenaStoreTypes.XML);
        PersistanceManager persistanceManager =
                PersistanceManagerFactory.init(properties);
        PersistanceSession persistanceSession = persistanceManager.getSession();


        Properties ontologyProperties = new Properties();
        properties.put(OntologyConstants.ONTOLOGY_MANAGER_CLASS,
                "com.rift.coad.rdf.semantic.ontology.jena.JenaOntologyManager");
        File testFile = new File("./base.xml");
        FileInputStream in = new FileInputStream(testFile);
        byte[] buffer = new byte[(int)testFile.length()];
        in.read(buffer);
        in.close();
        properties.put(OntologyConstants.ONTOLOGY_CONTENTS, new String(buffer));
        OntologyManager ontologyManager = OntologyManagerFactory.init(properties);
        OntologySession ontologySession = ontologyManager.getSession();


        TestSubObject subObject = new TestSubObject();
        TestBaseObject baseObject = new TestBaseObject("testbase1", 1, null);
        BasicJDOPersistanceHandler instance = new BasicJDOPersistanceHandler(baseObject,
            persistanceSession, ontologySession);
        PersistanceResource result = instance.persist();
        System.out.println("RDF XML [" + persistanceSession.dumpXML() + "]");
        assertEquals(result.getURI().toString(), "http://dipforge.sourceforge.net/schema/rdf/1.0/test#TestObject/testbase1");
        
    }

    

}