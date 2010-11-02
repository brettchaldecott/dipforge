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
 * XMLConfigurationFactoryTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.configuration.xml;

import junit.framework.*;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.ConfigurationException;
import com.rift.coad.lib.configuration.Configuration;

/**
 *
 * @author mincemeat
 */
public class XMLConfigurationFactoryTest extends TestCase {
    
    public XMLConfigurationFactoryTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(XMLConfigurationFactoryTest.class);
        
        return suite;
    }

    /**
     * Test of class com.rift.coad.lib.configuration.xml.XMLConfigurationFactory.
     */
    public void testConfig() throws Exception {
        System.out.println("getConfig");
        
        ConfigurationFactory instance = ConfigurationFactory.getInstance();
        
        if (instance == null) {
            fail("Failed to load configuration.");
        }
        
        // retrieve a general section
        Configuration config = instance.getConfig(java.lang.String.class);
        if (config.getString("gen1").equals("value 1") == false) {
            fail("Should have found [value 1] got [" + config.getString("gen1") + "]");
        } else if (config.getString("gen2").equals("value 2") == false) {
            fail("Should have found [value 2] got [" + config.getString("gen1") + "]");
        } else if (config.getLong("gen3") != 3) {
            fail("Should have found [3] got [" + config.getLong("gen3") + "]");
        }
        
        // the local class data
        config = instance.getConfig(XMLConfigurationFactoryTest.class);
        if (config.getString("gen1").equals("value 1") == false) {
            fail("Should have found [value 1] got [" + config.getString("gen1") + "]");
        } else if (config.getString("gen2").equals("value 2") == false) {
            fail("Should have found [value 2] got [" + config.getString("gen1") + "]");
        } else if (config.getLong("gen3") != 3) {
            fail("Should have found [3] got [" + config.getLong("gen3") + "]");
        } else if (config.getString("key1").equals("value 4") == false) {
            fail("Should have found [value 4].");
        } else if (config.getString("key2").equals("value 5") == false) {
            fail("Should have found [value 5].");
        } else if (config.getLong("key3") != 6) {
            fail("Should have found [6] got [" + config.getLong("key3") + "]");
        } else if (config.isBoolean("key4") != true) {
            fail("Check for is boolean on a boolean value failed.");
        } else if (config.getBoolean("key4") != true) {
            fail("Should have found [true] got [" + config.getBoolean("key4") + 
                    "]");
        } else if (config.getBoolean("key5") != false) {
            fail("Should have found [false] got [" + 
                    config.getBoolean("key4") + "]");
        }
        
    }
    
}
