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
 * TextFileTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.common;

import com.rift.coad.lib.common.*;
import junit.framework.*;
import java.io.File;
import java.io.FileReader;

/**
 *
 * @author mincemeat
 */
public class TextFileTest extends TestCase {
    
    public TextFileTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(TextFileTest.class);
        
        return suite;
    }

    /**
     * Test of getTextDocument method, of class com.rift.coad.lib.common.TextFile.
     */
    public void testGetTextDocument() throws Exception {
        System.out.println("getTextDocument");
        
        TextFile instance = new TextFile(new File(System.getProperty(
                "xml.config.path")));
        
        String result = instance.getTextDocument();
        
    }
    
}
