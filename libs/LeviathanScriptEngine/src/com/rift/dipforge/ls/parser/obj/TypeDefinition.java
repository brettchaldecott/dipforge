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
 * TypeDefinition.java
 */

// package name
package com.rift.dipforge.ls.parser.obj;

import java.io.Serializable;


/**
 * The object defines a type definition object.
 * 
 * @author brett chaldecott
 */
public class TypeDefinition implements Serializable {

	/**
	 * This static variable contains the version number for 
	 */
	private static final long serialVersionUID = 1L;

	// private member variables
	private String uri;
	private String name;
	
	
	/**
	 * The default contructor
	 */
	public TypeDefinition() {
	}
	
	
	/**
	 * The constructor that sets all the member variables
	 * @param uri
	 * @param name
	 */
	public TypeDefinition(String uri, String name) {
		super();
		this.uri = uri;
		this.name = name;
	}
	
	
	/**
	 * This method retrieves the uri.
	 * 
	 * @return The uri to remove.
	 */
	public String getUri() {
		return uri;
	}
	
	/**
	 * This method sets the URI.
	 * 
	 * @param uri The new URI string.
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	
	/**
	 * This method retrieves the name.
	 * 
	 * @return The name of the type.
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * This method sets the type name.
	 * 
	 * @param name The name of the type.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TypeDefinition [uri=" + uri + ", name=" + name + "]";
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
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
		TypeDefinition other = (TypeDefinition) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}
	
	
	
}
