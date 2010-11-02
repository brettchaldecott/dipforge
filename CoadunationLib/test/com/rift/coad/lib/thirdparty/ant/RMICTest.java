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
 * RMICTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.thirdparty.ant;

import junit.framework.*;
import java.io.File;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.taskdefs.Rmic;
import org.apache.tools.ant.types.Path;
import com.rift.coad.lib.common.RandomGuid;
import com.rift.coad.lib.common.FileUtil;

/**
 *
 * @author mincemeat
 */
public class RMICTest extends TestCase {
    
    public RMICTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(RMICTest.class);
        
        return suite;
    }

    /**
     * Test of parse method, of class com.rift.coad.lib.thirdparty.ant.RMIC.
     */
    public void testParse() throws Exception {
        System.out.println("parse");
        String tmpDir = System.getProperty("test.tmp.dir");
        File destDir = new File(tmpDir,RandomGuid.getInstance().getGuid());
        destDir.mkdir();
        File testDir = new File(System.getProperty("dist.jar"));
        File[] classPath = new File[] { testDir };
        
        RMIC instance = new RMIC(classPath,
                "com.rift.coad.lib.thirdparty.ant.rmic.RMIClass",destDir);
        
        instance.parse();
        
    }
    
}
