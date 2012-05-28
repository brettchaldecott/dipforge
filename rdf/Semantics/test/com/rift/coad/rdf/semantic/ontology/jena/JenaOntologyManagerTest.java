/*
 * Semantics: The semantic library for coadunation os
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
 * JenaOntologyManagerTest.java
 */

package com.rift.coad.rdf.semantic.ontology.jena;

import com.rift.coad.rdf.semantic.ontology.OntologyConstants;
import com.rift.coad.rdf.semantic.ontology.OntologyManagerFactory;
import com.rift.coad.rdf.semantic.ontology.OntologySession;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import junit.framework.TestCase;

/**
 * This is the test interface for the jena ontology.
 *
 * @author brett chaldecott
 */
public class JenaOntologyManagerTest extends TestCase {
    
    public JenaOntologyManagerTest(String testName) {
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
     * Test of getName method, of class JenaOntologyManager.
     */
    public void testGetName() throws Exception {
        System.out.println("getName");
        Properties properties = new Properties();
        properties.put(OntologyConstants.ONTOLOGY_MANAGER_CLASS,
                "com.rift.coad.rdf.semantic.ontology.jena.JenaOntologyManager");
        File testFile = new File("./base.xml");
        FileInputStream in = new FileInputStream(testFile);
        byte[] buffer = new byte[(int)testFile.length()];
        in.read(buffer);
        in.close();
        properties.put(OntologyConstants.ONTOLOGY_CONTENTS, new String(buffer));
        JenaOntologyManager instance =
                (JenaOntologyManager)OntologyManagerFactory.init(properties);

        String expResult = "JenaOntologyManager";
        String result = instance.getName();
        assertEquals(expResult, result);
        
        JenaOntologyManager instance2 =
                (JenaOntologyManager)OntologyManagerFactory.init(properties);
        assertEquals(instance2, instance);
    }


    /**
     * Test of getDescription method, of class JenaOntologyManager.
     */
    public void testGetDescription() throws Exception {
        System.out.println("getDescription");
        Properties properties = new Properties();
        properties.put(OntologyConstants.ONTOLOGY_MANAGER_CLASS,
                "com.rift.coad.rdf.semantic.ontology.jena.JenaOntologyManager");
        File testFile = new File("./base.xml");
        FileInputStream in = new FileInputStream(testFile);
        byte[] buffer = new byte[(int)testFile.length()];
        in.read(buffer);
        in.close();
        properties.put(OntologyConstants.ONTOLOGY_CONTENTS, new String(buffer));
        JenaOntologyManager instance =
                (JenaOntologyManager)OntologyManagerFactory.init(properties);
        String expResult = "The jena ontology manager";
        String result = instance.getDescription();
        assertEquals(expResult, result);
    }

    /**
     * Test of getVersion method, of class JenaOntologyManager.
     */
    public void testGetVersion() throws Exception {
        System.out.println("getVersion");
        Properties properties = new Properties();
        properties.put(OntologyConstants.ONTOLOGY_MANAGER_CLASS,
                "com.rift.coad.rdf.semantic.ontology.jena.JenaOntologyManager");
        File testFile = new File("./base.xml");
        FileInputStream in = new FileInputStream(testFile);
        byte[] buffer = new byte[(int)testFile.length()];
        in.read(buffer);
        in.close();
        properties.put(OntologyConstants.ONTOLOGY_CONTENTS, new String(buffer));
        JenaOntologyManager instance =
                (JenaOntologyManager)OntologyManagerFactory.init(properties);
        String expResult = "1.0.1";
        String result = instance.getVersion();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSession method, of class JenaOntologyManager.
     */
    public void testGetSession() throws Exception {
        System.out.println("getSession");
        Properties properties = new Properties();
        properties.put(OntologyConstants.ONTOLOGY_MANAGER_CLASS,
                "com.rift.coad.rdf.semantic.ontology.jena.JenaOntologyManager");
        File testFile = new File("./base.xml");
        FileInputStream in = new FileInputStream(testFile);
        byte[] buffer = new byte[(int)testFile.length()];
        in.read(buffer);
        in.close();
        properties.put(OntologyConstants.ONTOLOGY_CONTENTS, new String(buffer));
        JenaOntologyManager instance =
                (JenaOntologyManager)OntologyManagerFactory.init(properties);
        OntologySession result = instance.getSession();
        assertEquals(result.getClass().getName(), "com.rift.coad.rdf.semantic.ontology.jena.JenaOntologySession");
    }

}
