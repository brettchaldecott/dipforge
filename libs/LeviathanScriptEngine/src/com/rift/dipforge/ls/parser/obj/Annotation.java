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
 * Annotation.java
 */

package com.rift.dipforge.ls.parser.obj;

// imports
import java.io.Serializable;


/**
 * This object contains the annotation information. 
 * 
 * @author brett chaldecott
 */
public class Annotation implements Serializable {
	
	
	
	/**
	 * The serial version number for this object
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private LsList list;
	
	/**
	 * The default constructor for the annotation object
	 */
	public Annotation() {
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * This constructor for the annotation object.
	 * 
	 * @param name The name of the annotation.
	 * @param list The list of annotations.
	 */
	public Annotation(String name, LsList list) {
		super();
		this.name = name;
		this.list = list;
	}



	/**
	 * This method 
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * This method sets the name of the object.
	 * @param name The name of the object.
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	/**
	 * This method returns the contained by the annotations
	 * 
	 * @return The list contained by this object.
	 */
	public LsList getList() {
		return list;
	}

	
	/**
	 * This method is called to set the 
	 * @param list
	 */
	public void setList(LsList list) {
		this.list = list;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((list == null) ? 0 : list.hashCode());
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
		Annotation other = (Annotation) obj;
		if (list == null) {
			if (other.list != null)
				return false;
		} else if (!list.equals(other.list))
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
		return "Annotation [name=" + name + ", list=" + list + "]";
	}

	
	
	
	
}
