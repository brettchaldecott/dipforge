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

// package path
package com.rift.dipforge.ls.parser.obj;

import java.io.Serializable;

public class NegationStatement extends Statement implements Serializable {

	/**
	 * The serial version for this object.
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean negation = false;
	private IncrementStatement increment;
	
	
	/**
	 * The default constructor for the negation statement
	 */
	public NegationStatement() {
		
	}


	/**
	 * This method returns true if this is a negation.
	 * 
	 * @return TRUE if a negation.
	 */
	public boolean isNegation() {
		return negation;
	}
	
	
	/**
	 * This method sets the negation flag
	 * 
	 * @param negation TRUE if negation FALSE if not.
	 */
	public void setNegation(boolean negation) {
		this.negation = negation;
	}


	/**
	 * This method returns the reference to the increment statement
	 * 
	 * @return This method returns the increment statement value.
	 */
	public IncrementStatement getIncrement() {
		return increment;
	}

	
	/**
	 * This method sets the increment statement value.
	 * 
	 * @param increment The value to set for the increment statement.
	 */
	public void setIncrement(IncrementStatement increment) {
		this.increment = increment;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((increment == null) ? 0 : increment.hashCode());
		result = prime * result + (negation ? 1231 : 1237);
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
		NegationStatement other = (NegationStatement) obj;
		if (increment == null) {
			if (other.increment != null)
				return false;
		} else if (!increment.equals(other.increment))
			return false;
		if (negation != other.negation)
			return false;
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NegationStatement [negation=" + negation + ", increment="
				+ increment + "]";
	}

	
	
}
