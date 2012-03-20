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
 * LsListArgument.java
 */

// package path
package com.rift.dipforge.ls.parser.obj;

// java imports
import java.io.Serializable;


/**
 * This object deals with the list of arguments.
 * 
 * @author brett chaldecott
 */
public class LsListArgument extends StatementArgument implements Serializable {

	/**
	 * The serialization information
	 */
	private static final long serialVersionUID = 1L;
	
	// private member variables
	private Expression expression;
	
	
	/**
	 * The default constructor
	 */
	public LsListArgument() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * This method returns the expression.
	 * 
	 * @return This method returns the expression.
	 */
	public Expression getExpression() {
		return expression;
	}


	/**
	 * This method sets the expression
	 * 
	 * @param expression The expression.
	 */
	public void setExpression(Expression expression) {
		this.expression = expression;
	}


	/**
	 * This method returns the string value
	 */
	@Override
	public String toString() {
		return "LsListArgument [expression=" + expression + "]";
	}

	
	/**
	 * This method returns the hash code value.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((expression == null) ? 0 : expression.hashCode());
		return result;
	}

	
	/**
	 * This method returns true if the values are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LsListArgument other = (LsListArgument) obj;
		if (expression == null) {
			if (other.expression != null)
				return false;
		} else if (!expression.equals(other.expression))
			return false;
		return true;
	}
	
	
	
	
}
