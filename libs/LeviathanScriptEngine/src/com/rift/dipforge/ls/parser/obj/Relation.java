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
 * Relation.java
 */

// package path
package com.rift.dipforge.ls.parser.obj;

// the imports
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * The relation between object
 * 
 * @author brett chaldecott
 *
 */
public class Relation implements Serializable {

	/**
	 * The version uid for this class.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The mult statement block
	 * 
	 * @author brett chaldecott
	 */
	public class RelationStatementBlock implements Serializable {
		
		// private member variables
		private String operation;
		private AddStatement statement;
		
		/**
		 * The default constructor
		 */
		public RelationStatementBlock() {
			
		}

		/**
		 * The constructor that sets all the values.
		 * 
		 * @param operation The string containing the operation
		 * @param statement The statement information.
		 */
		public RelationStatementBlock(String operation, AddStatement statement) {
			super();
			this.operation = operation;
			this.statement = statement;
		}

		/**
		 * This method retrieves the operation information
		 * 
		 * @return The operation information.
		 */
		public String getOperation() {
			return operation;
		}
		
		
		/**
		 * This method sets the operation type.
		 * 
		 * @param operation The string containing the operation type.
		 */
		public void setOperation(String operation) {
			this.operation = operation;
		}

		
		/**
		 * This method retrieves the statement information.
		 * 
		 * @return The string containing the statement information.
		 */
		public AddStatement getStatement() {
			return statement;
		}

		
		/**
		 * This method sets the statement information.
		 * 
		 * @param statement The reference to the statement
		 */
		public void setStatement(AddStatement statement) {
			this.statement = statement;
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
					+ ((operation == null) ? 0 : operation.hashCode());
			result = prime * result
					+ ((statement == null) ? 0 : statement.hashCode());
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
			RelationStatementBlock other = (RelationStatementBlock) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (operation == null) {
				if (other.operation != null)
					return false;
			} else if (!operation.equals(other.operation))
				return false;
			if (statement == null) {
				if (other.statement != null)
					return false;
			} else if (!statement.equals(other.statement))
				return false;
			return true;
		}

		/**
		 * @return This method returns the outer type.
		 */
		private Relation getOuterType() {
			return Relation.this;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "RelationStatementBlock [operation=" + operation
					+ ", statement=" + statement + "]";
		}
	}

	
	// private member variables
	private AddStatement initialValue;
	private List<RelationStatementBlock> blocks = new ArrayList<RelationStatementBlock>();

	/**
	 * The default constructor of the relationship object.
	 */
	public Relation() {
	}
	
	
	/**
	 * This method returns a reference to the intial value.
	 * 
	 * @return This method returns the initial value.
	 */
	public AddStatement getInitialValue() {
		return initialValue;
	}

	
	/**
	 * This method sets the initial value
	 * 
	 * @param initialValue The intial value for the object.
	 */
	public void setInitialValue(AddStatement initialValue) {
		this.initialValue = initialValue;
	}


	/**
	 * This method returns a list of blocks.
	 * 
	 * @return The list of blocks
	 */
	public List<RelationStatementBlock> getBlocks() {
		return blocks;
	}


	/**
	 * This method sets the list of blocks.
	 * 
	 * @param blocks The list of blocks
	 */
	public void setBlocks(List<RelationStatementBlock> blocks) {
		this.blocks = blocks;
	}

	
	/**
	 * This method adds a block to the list of blocks.
	 * 
	 * @param operation The operation to execute.
	 * @param value The value
	 */
	public void addBlock(String operation, AddStatement value) {
		this.blocks.add(new RelationStatementBlock(operation, value));
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((blocks == null) ? 0 : blocks.hashCode());
		result = prime * result
				+ ((initialValue == null) ? 0 : initialValue.hashCode());
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
		Relation other = (Relation) obj;
		if (blocks == null) {
			if (other.blocks != null)
				return false;
		} else if (!blocks.equals(other.blocks))
			return false;
		if (initialValue == null) {
			if (other.initialValue != null)
				return false;
		} else if (!initialValue.equals(other.initialValue))
			return false;
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Relation [initialValue=" + initialValue + ", blocks=" + blocks
				+ "]";
	}
	
	
	
	
		
}
