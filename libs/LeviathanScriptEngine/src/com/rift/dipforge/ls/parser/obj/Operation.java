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
 * Operation.java
 */

package com.rift.dipforge.ls.parser.obj;

// imports
import java.io.Serializable;

public class Operation implements Serializable {

	/**
	 * The version number for this object.
	 */
	private static final long serialVersionUID = 1L;
	
	// private member variables
	private String value;
	
	/**
	 * The default constructor
	 */
	public Operation() {
		
	}
	
	/**
	 * This constructor
	 * 
	 * @param value
	 */
	public Operation(String value) {
		super();
		this.value = value;
	}

	
	/**
	 * This method returns the value for the operation.
	 * 
	 * @return This method returns the value for the operation.
	 */
	public String getValue() {
		return value;
	}
	
	
	/**
	 * This method is called to set the value of the operator
	 * 
	 * @param value The value of the operator.
	 */
	public void setValue(String value) {
		this.value = value;
	}

	
	/**
	 * This method returns the hash code.
	 * 
	 * @return The hash code for the operation.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	
	
	/**
	 * This method returns TRUE if the target object passed in is equals, false if not.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Operation other = (Operation) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	
	/**
	 * This method returns the operation value.
	 */
	@Override
	public String toString() {
		return "Operation [value=" + value + "]";
	}
	
	
	
}
