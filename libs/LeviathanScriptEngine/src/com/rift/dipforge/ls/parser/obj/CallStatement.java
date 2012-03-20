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
 * VariableCall.java
 */

// package path
package com.rift.dipforge.ls.parser.obj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * The object that represents the information about a variable
 * 
 * @author brett chaldecott
 *
 */
/**
 * @author brett
 *
 */
public class CallStatement extends Statement implements Serializable {
	
	/**
	 * This is the definition of the call statement entry.
	 * 
	 * @author brett chaldecott
	 */
	public class CallStatementEntry implements Serializable {
		
		/**
		 * The serialization version uid
		 */
		private static final long serialVersionUID = 1L;
		
		// private member variables.
		private String name;
		private StatementArgument argument;
		
		/**
		 * This is the default constructor for the call statement entry.
		 */
		public CallStatementEntry() {
			
		}
		
		/**
		 * The constructor that creates the new call statement.
		 * 
		 * @param name The name of the call statement.
		 */
		public CallStatementEntry(String name) {
			this.name = name;
		}

		/**
		 * This method returns the name for the call statement.
		 * 
		 * @return The name for the call statement.
		 */
		public String getName() {
			return name;
		}

		
		/**
		 * This method sets the name for the call statement.
		 * 
		 * @param name The name of the call statement.
		 */
		public void setName(String name) {
			this.name = name;
		}

		
		/**
		 * This method retrieves the argument to the call statement name.
		 * 
		 * @return The statement argument.
		 */
		public StatementArgument getArgument() {
			return argument;
		}

		
		/**
		 * This method sets the name of the call statement arugment
		 * 
		 * @param argument
		 */
		public void setArgument(StatementArgument argument) {
			this.argument = argument;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((argument == null) ? 0 : argument.hashCode());
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
			CallStatementEntry other = (CallStatementEntry) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (argument == null) {
				if (other.argument != null)
					return false;
			} else if (!argument.equals(other.argument))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}

		private CallStatement getOuterType() {
			return CallStatement.this;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "CallStatementEntry [name=" + name + ", argument="
					+ argument + "]";
		}
		
		
		
		
		
	}
	
	/**
	 * The id that identifies this object for serialization and deserialization
	 */
	private static final long serialVersionUID = 1L;
	
	// private member variables
	private List<CallStatementEntry> entries = new ArrayList<CallStatementEntry>();
	private Assignment assignment = null;
	
	/**
	 * The default constructor.
	 */
	public CallStatement() {
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * This method retrieves a list of the entries.
	 * 
	 * @return The list of entries
	 */
	public List<CallStatementEntry> getEntries() {
		return entries;
	}
	
	
	/**
	 * This method sets the list of entries.
	 * 
	 * @param entries The list of entries.
	 */
	public void setEntries(List<CallStatementEntry> entries) {
		this.entries = entries;
	}
	
	
	/**
	 * This method adds a new entry to the list of entries.
	 * 
	 * @param name The name of the entry to add.
	 * @return The reference to the newly created call statement.
	 */
	public CallStatementEntry addEntry(String name) {
		CallStatementEntry entry = new CallStatementEntry(name);
		entries.add(entry);
		return entry;
	}
	
	
	/**
	 * This method returns the reference to the assignment.
	 * 
	 * @return The reference to the initial type.
	 */
	public Assignment getAssignment() {
		return assignment;
	}

	
	/**
	 * This method returns the reference to the assignment.
	 * 
	 * @param assignment The reference to the new assignment value.
	 */
	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((assignment == null) ? 0 : assignment.hashCode());
		result = prime * result + ((entries == null) ? 0 : entries.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CallStatement other = (CallStatement) obj;
		if (assignment == null) {
			if (other.assignment != null)
				return false;
		} else if (!assignment.equals(other.assignment))
			return false;
		if (entries == null) {
			if (other.entries != null)
				return false;
		} else if (!entries.equals(other.entries))
			return false;
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CallStatement [entries=" + entries + ", assignment="
				+ assignment + "]";
	}

	
	
		
}
