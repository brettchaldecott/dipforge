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
 * Block.java
 */

// package path
package com.rift.dipforge.ls.parser.obj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



/**
 * @author brett chaldecott
 *
 * This object represents a block or scope.
 */
public class Block extends Statement implements Serializable {
	
	
	/**
	 * The id of the serialization version.
	 */
	private static final long serialVersionUID = 1L;
	
	// private member variables
	private List<Statement> statements = new ArrayList<Statement>();
	private Block parent;
	
	
	/**
	 * The default constructor
	 */
	public Block() {
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * The list of statements
	 * @return
	 */
	public List<Statement> getStatements() {
		return statements;
	}

	
	/**
	 * This method sets the statement list.
	 * 
	 * @param statements
	 */
	public void setStatements(List<Statement> statements) {
		this.statements = statements;
	}


	/**
	 * This method retrieves the parent variable
	 * 
	 * @return The name of the parent.
	 */
	public Block getParent() {
		return parent;
	}

	
	/**
	 * This method sets the parent variable
	 * 
	 * @param parent The reference to the parent.
	 */
	public void setParent(Block parent) {
		this.parent = parent;
	}
	
	
	/**
	 * This method returns the variable reference
	 * 
	 * @param call The variable will make the call.
	 * @return Variable reference
	 */
	public Variable getVariable(CallStatement call) {
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result
				+ ((statements == null) ? 0 : statements.hashCode());
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
		Block other = (Block) obj;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (statements == null) {
			if (other.statements != null)
				return false;
		} else if (!statements.equals(other.statements))
			return false;
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		//return "Block [statements=" + statements + ", parent=" + parent + "]";
		StringBuilder builder = new StringBuilder();
		for (Statement statement : statements) {
			builder.append("type:").append(statement.getClass().getName()).append("\n");
			builder.append("info:").append(statement.toString()).append("\n");
		}
		return "Block [statements=[" + statements.size() + "][" 
			+ builder.toString() + "]";
	}
	
	
	
}
