/*
 * CoaduntionSemantics: The semantic library for coadunation os
 * Copyright (C) 2009  2015 Burntjam
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
 * DateHelperTest.java
 */

package com.rift.coad.rdf.semantic.util;

import java.util.Date;
import junit.framework.TestCase;

/**
 * This class is here to test the date helper.
 *
 * @author brett chaldecott
 */
public class DateHelperTest extends TestCase {
    
    public DateHelperTest(String testName) {
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
     * Test of parse method, of class DateHelper.
     */
    public void testDate() throws Exception {
        System.out.println("date");
        Date currentTime = new Date();
        DateHelper helper = new DateHelper(currentTime);
        String value = helper.toString();
        Date result = DateHelper.parse(value);
        assertEquals(currentTime, result);
    }

    
}
