/*
 * CoaduntionSemantics: The semantic library for coadunation os
 * Copyright (C) 2011  2015 Burntjam
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
 * TestBaseObject.java
 */


package com.rift.coad.rdf.semantic.jdo.basic.test;

import com.rift.coad.rdf.semantic.annotation.Identifier;
import com.rift.coad.rdf.semantic.annotation.LocalName;
import com.rift.coad.rdf.semantic.annotation.Namespace;
import com.rift.coad.rdf.semantic.annotation.PropertyLocalName;
import com.rift.coad.rdf.semantic.annotation.CollectionTypeParameter;
import java.util.ArrayList;
import java.util.List;

/**
 * This object represents a test object
 *
 * @author brettc
 */
@Namespace("http://dipforge.sourceforge.net/schema/rdf/1.0/testbaseobject")
@LocalName("TestObject")
public class TestBaseObject {

    private String name;
    private int count;
    private TestSubObject subObject;
    private List<TestListObject> listObjects = new ArrayList<TestListObject>();

    
    /**
     * Required default constructor
     */
    public TestBaseObject() {
    }


    /**
     * 
     * @param name
     * @param count
     * @param subObject
     */
    public TestBaseObject(String name, int count, TestSubObject subObject) {
        this.name = name;
        this.count = count;
        this.subObject = subObject;
    }


    @PropertyLocalName("Count")
    public int getCount() {
        return count;
    }


    @PropertyLocalName("Count")
    public void setCount(int count) {
        this.count = count;
    }

    
    @PropertyLocalName("ListObjects")
    //@CollectionTypeParameter("com.rift.coad.rdf.semantic.jdo.basic.test.TestListObject")
    public List<TestListObject> getListObjects() {
        return listObjects;
    }

    @PropertyLocalName("ListObjects")
    public void setListObjects(List<TestListObject> listObjects) {
        this.listObjects = listObjects;
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

    @PropertyLocalName("SubObject")
    public TestSubObject getSubObject() {
        return subObject;
    }

    @PropertyLocalName("SubObject")
    public void setSubObject(TestSubObject subObject) {
        this.subObject = subObject;
    }

    @Override
    public String toString() {
        return "TestBaseObject{" + "name=" + name + "count=" + count + "subObject=" + subObject + "listObjects=" + listObjects + '}';
    }



    

}
