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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * The definition of the method.
 * 
 * -@author brett chaldecott
 */
public class MethodDefinition extends Block implements Serializable {
	
	
	/**
	 * The serialization version information
	 */
	private static final long serialVersionUID = 1L;
	
	// private member variables
	private String type;
	private String name;
	private List<Variable> parameters = new ArrayList<Variable>();
	
	
	/**
	 * The default constructor.
	 */
	public MethodDefinition() {
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * The constructor that sets all the variables.
	 * 
	 * @param type The name of the type.
	 * @param name The name of this method.
	 * @param parameters The list of parameters.
	 */
	public MethodDefinition(String type, String name, List<Variable> parameters) {
		super();
		this.type = type;
		this.name = name;
		this.parameters = parameters;
	}
	
	

	/**
	 * This method returns the type name for this method return type.
	 * 
	 * @return The string containing the type name.
	 */
	public String getType() {
		return type;
	}


	/**
	 * This method sets the type name
	 * 
	 * @param type The string containing the type.
	 */
	public void setType(String type) {
		this.type = type;
	}


	/**
	 * This method returns the name of the definition.
	 * 
	 * @return The method returns the name of the method.
	 */
	public String getName() {
		return name;
	}


	/**
	 * This method sets the name of this method.
	 * 
	 * @param name The name of this method.
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	/**
	 * The list of parameters or initial variables passed into this object.
	 * @return
	 */
	public List<Variable> getParameters() {
		return parameters;
	}

	
	/**
	 * This method sets the 
	 * @param parameters
	 */
	public void setParameters(List<Variable> parameters) {
		this.parameters = parameters;
		List<Statement> statements = new ArrayList<Statement>();
		statements.addAll(parameters);
		this.setStatements(statements);
	}
	
	
	/**
	 * This method adds a new parameter.
	 * 
	 * @param name The name of the parameter to add
	 */
	public void addParameter(String name) {
		Variable var = new Variable();
		var.setName(name);
		var.setType(Types.DEF);
		parameters.add(var);
		this.getStatements().add(var);
	}


	/* (non-Javadoc)
	 * @see com.rift.dipforge.ls.parser.obj.Block#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((parameters == null) ? 0 : parameters.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see com.rift.dipforge.ls.parser.obj.Block#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MethodDefinition other = (MethodDefinition) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parameters == null) {
			if (other.parameters != null)
				return false;
		} else if (!parameters.equals(other.parameters))
			return false;
		return true;
	}


	/* (non-Javadoc)
	 * @see com.rift.dipforge.ls.parser.obj.Block#toString()
	 */
	@Override
	public String toString() {
		return "MethodDefinition [name=" + name + ", parameters=" + parameters
				+ "]";
	}
	
	
	
}
