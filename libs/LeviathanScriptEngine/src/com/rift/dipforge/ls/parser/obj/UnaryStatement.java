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
 * UnaryStatement.java
 */

// the package path
package com.rift.dipforge.ls.parser.obj;

// imports
import java.io.Serializable;


/**
 * The implementation of the unary statement container.
 * 
 * @author brett chaldecott
 */
public class UnaryStatement extends Statement implements Serializable {

	/**
	 * The version information for this object.
	 */
	private static final long serialVersionUID = 1L;
	
	private String operation;
	private NegationStatement negation;
	
	/**
	 * The default constructor
	 */
	public UnaryStatement() {
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * This method returns the operation information.
	 * 
	 * @return The operation being performed on the unary.
	 */
	public String getOperation() {
		return operation;
	}

	
	/**
	 * The set operation used on the operation.
	 * 
	 * @param operation The string containing the operation information.
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	
	/**
	 * This method returns the negation statement.
	 * 
	 * @return This method returns the negation information.
	 */
	public NegationStatement getNegation() {
		return negation;
	}

	
	/**
	 * This method sets the negation information.
	 *  
	 * @param negation The reference to the negation information.
	 */
	public void setNegation(NegationStatement negation) {
		this.negation = negation;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((negation == null) ? 0 : negation.hashCode());
		result = prime * result
				+ ((operation == null) ? 0 : operation.hashCode());
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
		UnaryStatement other = (UnaryStatement) obj;
		if (negation == null) {
			if (other.negation != null)
				return false;
		} else if (!negation.equals(other.negation))
			return false;
		if (operation == null) {
			if (other.operation != null)
				return false;
		} else if (!operation.equals(other.operation))
			return false;
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UnaryStatement [operation=" + operation + ", negation="
				+ negation + "]";
	}
	
	
	
}
