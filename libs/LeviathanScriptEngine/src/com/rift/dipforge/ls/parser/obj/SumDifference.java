/*
 * LeviathanScriptEngine: The implementation of the Leviathan script engin.
 * Copyright (C) 2012  Rift IT Contracting
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
 * SumDifference.java
 */

// package path
package com.rift.dipforge.ls.parser.obj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * The definition of the sum and difference object.
 * 
 * @author brett chaldecott
 */
public class SumDifference implements Serializable {
	
	/**
	 * The id of the version of this object.
	 */
	private static final long serialVersionUID = 1L;
	
	// private member variables
	private List arguments = new ArrayList();
	
	
	/**
	 * The default constructor
	 */
	public SumDifference() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * This method returns the list of arguments contained within.
	 * 
	 * @return The list of arguments.
	 */
	public List getArguments() {
		return arguments;
	}

	/**
	 * This method sets the arguments
	 * 
	 * @param arguments The list of arguments.
	 */
	public void setArguments(List arguments) {
		this.arguments = arguments;
	}


	/**
	 * This method returns the hashcode of this object.
	 * 
	 * @return An integer containing the hash code for this object.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((arguments == null) ? 0 : arguments.hashCode());
		return result;
	}


	/**
	 * This method performs an equals comparison on the object.
	 * 
	 * @param obj True if equal.
	 * @return TRUE if equal, FALSE if not.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SumDifference other = (SumDifference) obj;
		if (arguments == null) {
			if (other.arguments != null)
				return false;
		} else if (!arguments.equals(other.arguments))
			return false;
		return true;
	}


	/**
	 * This method returns a string representation of the sum object
	 */
	@Override
	public String toString() {
		return "SumDifference [arguments=" + arguments + "]";
	}
	
	
	
	
}
