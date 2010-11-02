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
 * JarUtilTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.common;

import com.rift.coad.lib.common.*;
import junit.framework.*;
import java.io.File;
import java.util.Enumeration;
import java.util.jar.JarFile;
import java.util.jar.JarEntry;

/**
 *
 * @author mincemeat
 */
public class JarUtilTest extends TestCase {
    
    public JarUtilTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(JarUtilTest.class);
        
        return suite;
    }

    /**
     * Test of extract method, of class com.rift.coad.lib.common.JarUtil.
     */
    public void testExtract() throws Exception {
        System.out.println("extract");
        
        File source = new File(System.getProperty("test.jar"));
        File destination = new File(System.getProperty("test.tmp.dir") +
                "/" + RandomGuid.getInstance().getGuid());
        
        System.out.println("Extract to [" + destination.getAbsolutePath() + "]");
        JarUtil.extract(source, destination);
        
        File testFile = new File(destination.getAbsolutePath()
                + "/META-INF/MANIFEST.MF");
        if (testFile.isFile() == false) {
            fail("Did not extract the coadunation.xml entry");
        }
        destination.delete();
    }
    
}
