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
 * IncrementStatement.java
 */
package com.rift.dipforge.ls.parser.obj;

import java.io.Serializable;

/**
 * This object handles the increment
 * 
 * @author brett chaldecott
 */
public class IncrementStatement extends Statement implements Serializable {

	/**
	 * The serial version information.
	 */
	private static final long serialVersionUID = 1L;
	
	// private member variables
	private String operation;
	private Object value;
	
	/**
	 * The default constructor
	 */
	public IncrementStatement() {
		
	}

	/**
	 * The constructor that sets up all the properties of this object.
	 * 
	 * @param operation The operator name.
	 * @param value The
	 */
	public IncrementStatement(String operation, Object value) {
		super();
		this.operation = operation;
		this.value = value;
	}

	/**
	 * This method returns the operation type being performed by this increment.
	 * 
	 * @return The string containing the operation type.
	 */
	public String getOperation() {
		return operation;
	}

	
	/**
	 * This method sets the operation information
	 * 
	 * @param operation The string containing the operation information.
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	
	/**
	 * This method returns the value of the increment statement.
	 * 
	 * @return The value of the increment statement.
	 */
	public Object getValue() {
		return value;
	}

	
	/**
	 * This method sets the value that the increment will be performed on.
	 * 
	 * @param value The value the increment is performed on.
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
		result = prime * result
				+ ((operation == null) ? 0 : operation.hashCode());
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
		IncrementStatement other = (IncrementStatement) obj;
		if (operation == null) {
			if (other.operation != null)
				return false;
		} else if (!operation.equals(other.operation))
			return false;
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
		return "IncrementStatement [operation=" + operation + ", value="
				+ value + "]";
	}
	
	
	
	
}
