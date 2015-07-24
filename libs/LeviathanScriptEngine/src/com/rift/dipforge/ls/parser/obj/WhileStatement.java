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
 * WhileStatement.java
 */

// package path
package com.rift.dipforge.ls.parser.obj;

// imports
import java.io.Serializable;

public class WhileStatement extends Statement implements Serializable {

	/**
	 * The serialization version information.
	 */
	private static final long serialVersionUID = 1L;
	
	// private member variables
	private Expression expression;
	private Block block;
	
	
	/**
	 * The default constructor
	 */
	public WhileStatement() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * This method returns the expression used to evaluate the loop.
	 * 
	 * @return The expression used to evaluate this loop.
	 */
	public Expression getExpression() {
		return expression;
	}

	
	/**
	 * This method sets the expression used to evaluate this loop.
	 * 
	 * @param expression The expression used to evaluate the loop.
	 */
	public void setExpression(Expression expression) {
		this.expression = expression;
	}


	/**
	 * This method returns a block.
	 * 
	 * @return The block returned. 
	 */
	public Block getBlock() {
		return block;
	}

	
	/**
	 * This method sets a block.
	 * 
	 * @param block The block to set.
	 */
	public void setBlock(Block block) {
		this.block = block;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((block == null) ? 0 : block.hashCode());
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
		WhileStatement other = (WhileStatement) obj;
		if (block == null) {
			if (other.block != null)
				return false;
		} else if (!block.equals(other.block))
			return false;
		if (expression == null) {
			if (other.expression != null)
				return false;
		} else if (!expression.equals(other.expression))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "WhileStatement [expression=" + expression + ", block=" + block
				+ "]";
	}
	
	
	
	
}
