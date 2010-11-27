/*
 * ScriptBroker: The script broker daemon.
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
 * TemplateException.java
 */

package com.rift.coad.script.broker;

import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This is the test of the template helper class.
 *
 * @author brett chaldecott
 */
public class TemplateHelperTest {

    public TemplateHelperTest() {
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
     * Test of parse method, of class TemplateHelper.
     */
    @Test
    public void testParse() throws Exception {
        System.out.println("parse");
        TemplateHelper instance = new TemplateHelper("./testfile.tmp");
        String expResult = "# this is a test file\n" +
            "\n" +
            "value1=bob\n" +
            "value2=tom\n" +
            "value2.2=tom\n" + 
            "value3=jill";
        Map<String,String> values = new HashMap<String,String>();
        values.put("value1", "bob");
        values.put("value2", "tom");
        values.put("value3", "jill");
        instance.setParameters(values);
        String result = instance.parse();
        assertEquals(expResult, result);
    }

}