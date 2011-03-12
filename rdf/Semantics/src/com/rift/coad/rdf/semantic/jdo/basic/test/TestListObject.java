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
 * TestListObject.java
 */

package com.rift.coad.rdf.semantic.jdo.basic.test;

import com.rift.coad.rdf.semantic.annotation.Identifier;
import com.rift.coad.rdf.semantic.annotation.LocalName;
import com.rift.coad.rdf.semantic.annotation.Namespace;
import com.rift.coad.rdf.semantic.annotation.PropertyLocalName;
import java.util.Calendar;

/**
 * The list object method
 *
 * @author brett chaldecott
 */
@Namespace("http://dipforge.sourceforge.net/schema/rdf/1.0/test")
@LocalName("TestListObject")
public class TestListObject {


    public String name;
    public long timestamp;
    public Calendar date;

    
    /**
     * Required default constructor
     */
    public TestListObject() {
    }



    /**
     * 
     * @param name
     * @param timestamp
     * @param date
     */
    public TestListObject(String name, long timestamp, Calendar date) {
        this.name = name;
        this.timestamp = timestamp;
        this.date = date;
    }

    @PropertyLocalName("Date")
    public Calendar getDate() {
        return date;
    }

    @PropertyLocalName("Date")
    public void setDate(Calendar date) {
        this.date = date;
    }

    @Identifier()
    @PropertyLocalName("Name")
    public String getName() {
        return name;
    }

    @PropertyLocalName("Name")
    public void setName(String name) {
        this.name = name;
    }


    @PropertyLocalName("TimeStamp")
    public long getTimestamp() {
        return timestamp;
    }

    
    @PropertyLocalName("dub")
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


    

}
