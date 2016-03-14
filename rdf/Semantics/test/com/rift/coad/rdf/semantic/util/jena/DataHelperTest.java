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
 * DataHelperTest.java
 */

package com.rift.coad.rdf.semantic.util.jena;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import com.rift.coad.rdf.semantic.util.DateHelper;
import java.util.Date;
import junit.framework.TestCase;

/**
 * Test the data helper.
 *
 * @author brett
 */
public class DataHelperTest extends TestCase {
    
    public DataHelperTest(String testName) {
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
     * Test of date method, of class DataHelper.
     */
    public void testDate() {
        System.out.println("date");
        Model model = ModelFactory.createDefaultModel();
        Date expResult = new Date();
        Literal l = DataHelper.toLiteral(model, new DateHelper(expResult).toString());
        Date result = DataHelper.date(l);
        assertEquals(expResult, result);
    }

}
