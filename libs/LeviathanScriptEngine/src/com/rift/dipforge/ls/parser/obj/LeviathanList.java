/*
 * LeviathanScriptEngine: The implementation of the Leviathan script engin.
 * Copyright (C) 2012  2015 Burntjam
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
 * LeviathanList.java
 */

package com.rift.dipforge.ls.parser.obj;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * This object is a representation of a list.
 * 
 * @author brett chaldecott
 */
public class LeviathanList implements Serializable{
	
	/**
	 * The serialization version id
	 */
	private static final long serialVersionUID = 1L;
	
	// private member variables
	private List list;
	private Map map;
	
	
	/**
	 * The constructor that sets up the leviathan list as a map.
	 * 
	 * @param map The map.
	 */
	public LeviathanList(Map map) {
		super();
		this.map = map;
	}
	
	
	/**
	 * This constructor sets up the leviathan list as a list
	 * @param list
	 */
	public LeviathanList(List list) {
		super();
		this.list = list;
	}
	
	
	/**
	 * This method returns the list stored within.
	 * 
	 * @return The list of the object.
	 */
	public List getList() {
		return list;
	}
	
	/**
	 * The method that retrieves the map.
	 * 
	 * @return The map contained within.
	 */
	public Map getMap() {
		return map;
	}
	
	
	/**
	 * This method returns the hash code for this object
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((list == null) ? 0 : list.hashCode());
		result = prime * result + ((map == null) ? 0 : map.hashCode());
		return result;
	}
	
	
	/**
	 * This method returns true if this object equals another.
	 * 
	 * @return boolean TRUE if equals 
         * @param obj The object to perform the comparison on
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LeviathanList other = (LeviathanList) obj;
		if (list == null) {
			if (other.list != null)
				return false;
		} else if (!list.equals(other.list))
			return false;
		if (map == null) {
			if (other.map != null)
				return false;
		} else if (!map.equals(other.map))
			return false;
		return true;
	}
	
	
}
