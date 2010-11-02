/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  Rift IT Contracting
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
 * DeploymentLoaderTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.deployment;

import junit.framework.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import com.rift.coad.lib.common.ResourceReader;



/**
 *
 * @author mincemeat
 */
public class DeploymentLoaderTest extends TestCase {
    
    public DeploymentLoaderTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(DeploymentLoaderTest.class);
        
        return suite;
    }

    /**
     * Test of class com.rift.coad.lib.deployment.DeploymentLoader.
     */
    public void testInitLoader() throws Exception {
        System.out.println("testClassLoader");
        
        // instanciate the deployment loader
        DeploymentLoader instance = new DeploymentLoader(
                new java.io.File(System.getProperty("test.jar")));
        
        // load load in
        com.rift.coad.lib.deployment.DeploymentInfo info = 
                instance.getDeploymentInfo();
        if (!info.getName().equals("CoadunationTest")) {
            fail("The name of the coadunation entry is invalid");
        }
    }

}
