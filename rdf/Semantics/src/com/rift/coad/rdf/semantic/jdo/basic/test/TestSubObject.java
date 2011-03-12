/*
 * CoaduntionSemantics: The semantic library for coadunation os
 * Copyright (C) 2011  Rift IT Contracting
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
 * TestSubObject.java
 */

// package path
package com.rift.coad.rdf.semantic.jdo.basic.test;

import com.rift.coad.rdf.semantic.annotation.Identifier;
import com.rift.coad.rdf.semantic.annotation.LocalName;
import com.rift.coad.rdf.semantic.annotation.Namespace;
import com.rift.coad.rdf.semantic.annotation.PropertyLocalName;

/**
 * The test sub object.
 *
 * @author brett chaldecott
 */
@Namespace("http://dipforge.sourceforge.net/schema/rdf/1.0/test")
@LocalName("TestSubObject")
public class TestSubObject {

    public String name;
    public int number;
    public double dub;

    
    public TestSubObject() {
    }


    public TestSubObject(String name, int number, double dub) {
        this.name = name;
        this.number = number;
        this.dub = dub;
    }

    @PropertyLocalName("dub")
    public double getDub() {
        return dub;
    }

    @PropertyLocalName("dub")
    public void setDub(double dub) {
        this.dub = dub;
    }

    @PropertyLocalName("Name")
    public String getName() {
        return name;
    }

    @PropertyLocalName("Name")
    public void setName(String name) {
        this.name = name;
    }

    @Identifier()
    @PropertyLocalName("Number")
    public int getNumber() {
        return number;
    }

    @PropertyLocalName("Number")
    public void setNumber(int number) {
        this.number = number;
    }

    

}
