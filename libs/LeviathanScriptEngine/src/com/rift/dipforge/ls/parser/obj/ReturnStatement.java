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
 * ReturnStatement.java
 */

// package path
package com.rift.dipforge.ls.parser.obj;

import java.io.Serializable;


/**
 * The implementation of the return statement.
 * 
 * @author brett chaldecott
 */
public class ReturnStatement extends Statement implements Serializable {

	/**
	 * The version number for this serializable object. 
	 */
	private static final long serialVersionUID = 1L;
	
	private Expression expression;
	
	/**
	 * The default constructor
	 */
	public ReturnStatement() {
		
	}
	
	
	/**
	 * The constructor that sets up the fields.
	 * 
	 * @param expression The reference to values.
	 */
	public ReturnStatement(Expression expression) {
		super();
		this.expression = expression;
	}
	
	
	/**
	 * This method returns the expression set inside.
	 * 
	 * @return The reference to the expression object.
	 */
	public Expression getExpression() {
		return expression;
	}

	
	/**
	 * This method sets the expression object.
	 * 
	 * @param expression The reference to the expression object.
	 */
	public void setExpression(Expression expression) {
		this.expression = expression;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((expression == null) ? 0 : expression.hashCode());
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
		ReturnStatement other = (ReturnStatement) obj;
		if (expression == null) {
			if (other.expression != null)
				return false;
		} else if (!expression.equals(other.expression))
			return false;
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ReturnStatement [expression=" + expression + "]";
	}
	
	
}
