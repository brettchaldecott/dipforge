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
 * Workflow.java
 */

// package path
package com.rift.dipforge.ls.parser.obj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * The definition of the workflow class
 * 
 * @author brett chaldecott
 */
public class Workflow extends Block implements Serializable {
	
	/**
	 * This static variable contains the version number for 
	 */
	private static final long serialVersionUID = 1L;
	
	// private member variables
	private String name;
	private List<TypeDefinition> definedTypes = new ArrayList<TypeDefinition>();
	private List<Annotation> annotations = new ArrayList<Annotation>();
	
	
	
	
	/**
	 * This method returns the name of the workflow.
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * This method sets the name of the workflow.
	 * 
	 * @param name The name of the 
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * This method returns the list of type definitions.
	 * 
	 * @return The list of type definitions.
	 */
	public List<TypeDefinition> getDefinedTypes() {
		return definedTypes;
	}
	
	
	/**
	 * This method sets the definition types.
	 * 
	 * @param definedTypes
	 */
	public void setDefinedTypes(List<TypeDefinition> definedTypes) {
		this.definedTypes = definedTypes;
	}

	
	/**
	 * This method returns the list of annotations associated with a flow
	 * 
	 * @return This method returns the list of annotations associated with an object.
	 */
	public List<Annotation> getAnnotations() {
		return annotations;
	}

	
	/**
	 * This method sets the list of annotations
	 * 
	 * @param annotations
	 */
	public void setAnnotations(List<Annotation> annotations) {
		this.annotations = annotations;
	}

	@Override
	public String toString() {
		return "Workflow [name=" + name + ",\n definedTypes=" + definedTypes
				+ ",\n annotations=" + annotations + "\n[" + super.toString() + "]]";
	}
	
	
	
}
