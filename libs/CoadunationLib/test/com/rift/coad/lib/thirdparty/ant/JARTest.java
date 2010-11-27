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
 * JARTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.thirdparty.ant;

import junit.framework.*;
import java.io.File;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.taskdefs.Jar;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.taskdefs.LogOutputStream;
import org.apache.tools.ant.util.TeeOutputStream;
import org.apache.tools.ant.BuildEvent;

/**
 *
 * @author mincemeat
 */
public class JARTest extends TestCase {
    
    public JARTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(JARTest.class);
        
        return suite;
    }

    /**
     * Test of archive method, of class com.rift.coad.lib.thirdparty.ant.JAR.
     */
    public void testArchive() throws Exception {
        System.out.println("archive");
        
        File buildDir = new File(System.getProperty("build.dir"));
        File jarFile = new File(System.getProperty("test.tmp.dir") + 
                "/build.jar");
        
        JAR instance = new JAR(buildDir,jarFile);
        
        instance.archive();
        
    }
    
}
