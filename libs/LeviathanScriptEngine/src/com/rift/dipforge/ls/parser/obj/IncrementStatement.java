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
	private String variable;
	
	/**
	 * The default constructor
	 */
	public IncrementStatement() {
		
	}

	/**
	 * The constructor that sets up all the properties of this object.
	 * 
	 * @param operation The operator name.
	 * @param variable The identifier for the variable
	 */
	public IncrementStatement(String operation, String variable) {
		super();
		this.operation = operation;
		this.variable = variable;
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
	 * This method returns the identifier for the variable being manipulated
	 * 
	 * @return The value of the increment statement.
	 */
	public String getVariable() {
		return variable;
	}

	
	/**
	 * This method sets the variable value.
	 * 
	 * @param variable The value the increment is performed on.
	 */
	public void setVariable(String variable) {
		this.variable = variable;
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
		result = prime * result + ((variable == null) ? 0 : variable.hashCode());
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
		if (variable == null) {
			if (other.variable != null)
				return false;
		} else if (!variable.equals(other.variable))
			return false;
		return true;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IncrementStatement [operation=" + operation + ", variable="
				+ variable + "]";
	}
	
	
	
	
}
