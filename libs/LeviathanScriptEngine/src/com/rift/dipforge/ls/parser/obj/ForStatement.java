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
 * ForStatement.java
 */

// package path
package com.rift.dipforge.ls.parser.obj;

// the imports
import java.io.Serializable;


/**
 * The for statement definition.
 * 
 * @author brett chaldecott
 */
public class ForStatement extends Block implements Serializable {

	/**
	 * The version information for this object.
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Variable initialValue;
	private Expression comparison;
	private Expression increment;
	private Block child;
	
	/**
	 * The default constructor.
	 */
	public ForStatement() {
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * This method returns the initial value object.
	 * @return The 
	 */
	public Variable getInitialValue() {
		return initialValue;
	}

	public void setInitialValue(Variable initialValue) {
		this.initialValue = initialValue;
		this.getStatements().add(initialValue);
	}

	public Expression getComparison() {
		return comparison;
	}

	public void setComparison(Expression comparison) {
		this.comparison = comparison;
	}

	/**
	 * This method returns the increment expression.
	 * 
	 * @return
	 */
	public Expression getIncrement() {
		return increment;
	}

	
	/**
	 * This method increments the child statement.
	 * 
	 * @param increment The child statment
	 */
	public void setIncrement(Expression increment) {
		this.increment = increment;
	}


	/**
	 * This method returns the child reference.
	 * 
	 * @return The reference to the child.
	 */
	public Block getChild() {
		return child;
	}


	/**
	 * This method sets the child.
	 * 
	 * @param child The child reference.
	 */
	public void setChild(Block child) {
		this.child = child;
	}
	
	
	
}
