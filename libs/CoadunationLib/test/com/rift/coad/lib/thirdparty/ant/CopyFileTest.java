/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  2015 Burntjam
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
 * CopyFileTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.thirdparty.ant;

import junit.framework.*;
import java.io.File;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.BuildEvent;

/**
 *
 * @author mincemeat
 */
public class CopyFileTest extends TestCase {
    
    public CopyFileTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(CopyFileTest.class);
        
        return suite;
    }

    /**
     * Test of copy method, of class com.rift.coad.lib.thirdparty.ant.CopyFile.
     */
    public void testCopy() throws Exception {
        System.out.println("copy");
        
        File testFile = new File("./testconfig.xml");
        File destFile = new File("./tmp/testconfig.xml");
        CopyFile instance = new CopyFile(testFile,destFile);
        
        instance.copy();
        
        if (!destFile.isFile()) {
            fail("Failed to copy the file");
        }
        
        destFile.delete();
    }
    
}
