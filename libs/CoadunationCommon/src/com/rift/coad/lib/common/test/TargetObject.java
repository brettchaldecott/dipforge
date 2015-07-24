/*
 * WebLibs: Misc web utils and tools
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
 * TargetObject.java
 */

package com.rift.coad.lib.common.test;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a test object
 *
 * @author brett chaldecott
 */
public class TargetObject {

    private int id;
    private String name;
    private String value;
    private String[] arrayList;
    private List<String> list = new ArrayList<String>();

    public TargetObject() {
    }

    public TargetObject(int id, String name, String value, String[] arrayList) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.arrayList = arrayList;
    }

    public String[] getArrayList() {
        return arrayList;
    }

    public void setArrayList(String[] arrayList) {
        this.arrayList = arrayList;
    }

    
    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    


}
