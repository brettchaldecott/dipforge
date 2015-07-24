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
 * List.java
 */

// package path
package com.rift.dipforge.ls.parser.obj;

// the serialize import
import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * The generic list object.
 * 
 * @author brett chaldecott
 */
public class LsList implements Serializable {
	
	/**
	 * serialization version information.
	 */
	private static final long serialVersionUID = 1L;
	
	// private member variables
	private Map valueMap;
	private List valueList;
	
	/**
	 * The default constructor
	 */
	public LsList() {
		// TODO Auto-generated constructor stub
	}

	public LsList(Map valueMap) {
		super();
		this.valueMap = valueMap;
	}

	public LsList(List valueList) {
		super();
		this.valueList = valueList;
	}

	public Map getValueMap() {
		return valueMap;
	}

	public void setValueMap(Map valueMap) {
		this.valueMap = valueMap;
	}

	public List getValueList() {
		return valueList;
	}

	public void setValueList(List valueList) {
		this.valueList = valueList;
	}

	@Override
	public String toString() {
		return "LsList [valueMap=" + valueMap + ", valueList=" + valueList
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((valueList == null) ? 0 : valueList.hashCode());
		result = prime * result
				+ ((valueMap == null) ? 0 : valueMap.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LsList other = (LsList) obj;
		if (valueList == null) {
			if (other.valueList != null)
				return false;
		} else if (!valueList.equals(other.valueList))
			return false;
		if (valueMap == null) {
			if (other.valueMap != null)
				return false;
		} else if (!valueMap.equals(other.valueMap))
			return false;
		return true;
	}
	
	
	
}
