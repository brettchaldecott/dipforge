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
 * Variable.java
 */
package com.rift.dipforge.ls.parser.obj;

// the imports
import java.io.Serializable;


/**
 * @author brett
 *
 * The assignment object.
 */
public class Assignment implements Serializable {

	/**
	 * The version number for this object.
	 */
	private static final long serialVersionUID = 1L;
	
	// private member variables
	private Object value;
	
	/**
	 * The assignment operation.
	 */
	public Assignment() {
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * This constructor sets up the initial value.
	 * @param value
	 */
	public Assignment(Object value) {
		this.value = value;
	}

	/**
	 * This method returns the value contained within.
	 * 
	 * @return The value.
	 */
	public Object getValue() {
		return value;
	}

	
	/**
	 * This method sets the value.
	 * 
	 * @param value The object value.
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Assignment other = (Assignment) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Assignment [value=" + value + "]";
	}
	
	
	
}
