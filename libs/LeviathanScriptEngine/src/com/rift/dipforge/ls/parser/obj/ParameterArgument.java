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
 * ParameterArgument.java
 */

// package path
package com.rift.dipforge.ls.parser.obj;

// serializable
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * The parameter argument
 * 
 * @author brett chaldecott
 */
public class ParameterArgument extends StatementArgument  implements Serializable {

	/**
	 * The version information for the serialization
	 */
	private static final long serialVersionUID = 1L;
	
	// private member variables
	private String name;
	private List<Expression> expressions = new ArrayList<Expression>();
	
	
	/**
	 * The default constructor
	 */
	public ParameterArgument() {
		// TODO Auto-generated constructor stub
	}

	
	
	/**
	 * This method returns the name of the parameter argument.
	 *  
	 * @return The name of the argument.
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * This method sets the name of the parameter argument.
	 * 
	 * @param name The name of the argument.
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * This method returns the expressions
	 * 
	 * @return The expression value.
	 */
	public List<Expression> getExpressions() {
		return expressions;
	}


	/**
	 * This method sets the expression value
	 * 
	 * @param expression The expression value.
	 */
	public void setExpressions(List<Expression> expressions) {
		this.expressions = expressions;
	}

	
	/**
	 * This method addes an expression
	 * 
	 * @param expression The new expression to add
	 */
	public void addExpression(Expression expression) {
		this.expressions.add(expression);
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((expressions == null) ? 0 : expressions.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		ParameterArgument other = (ParameterArgument) obj;
		if (expressions == null) {
			if (other.expressions != null)
				return false;
		} else if (!expressions.equals(other.expressions))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ParameterArgument [name=" + name + ", expressions="
				+ expressions + "]";
	}


		
	
}
