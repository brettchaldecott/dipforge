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
 * MultStatement.java
 */

// package path
package com.rift.dipforge.ls.parser.obj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * This  object contains the multiplication information.
 * 
 * @author brett chaldecott
 */
public class MultStatement extends Statement implements Serializable {

	
	/**
	 * The serialization information.
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * The mult statement block
	 * 
	 * @author brett chaldecott
	 */
	public class MultStatementBlock implements Serializable {

		/**
		 * The serialization version information.
		 */
		private static final long serialVersionUID = 1L;
		
		
		private String operation;
		private UnaryStatement blockValue;
		
		
		/**
		 * The default constructor
		 */
		public MultStatementBlock() {
			
		}

		
		
		/**
		 * This constructor sets up all the internal variables.
		 * 
		 * @param operation The operation to perform.
		 * @param blockValue The block value.
		 */
		public MultStatementBlock(String operation, UnaryStatement blockValue) {
			super();
			this.operation = operation;
			this.blockValue = blockValue;
		}

		
		/**
		 * This method retrieves the operation
		 * 
		 * @return The string containing the operational value.
		 */
		public String getOperation() {
			return operation;
		}

		
		/**
		 * This method sets the operation.
		 * 
		 * @param operation The operational value.
		 */
		public void setOperation(String operation) {
			this.operation = operation;
		}


		/**
		 * This method retrieves the block value.
		 * 
		 * @return The reference to the block value.
		 */
		public UnaryStatement getBlockValue() {
			return blockValue;
		}
		
		
		/**
		 * This method sets the block value.
		 *  
		 * @param blockValue The block value.
		 */
		public void setBlockValue(UnaryStatement blockValue) {
			this.blockValue = blockValue;
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
					+ ((blockValue == null) ? 0 : blockValue.hashCode());
			result = prime * result
					+ ((operation == null) ? 0 : operation.hashCode());
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
			MultStatementBlock other = (MultStatementBlock) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (blockValue == null) {
				if (other.blockValue != null)
					return false;
			} else if (!blockValue.equals(other.blockValue))
				return false;
			if (operation == null) {
				if (other.operation != null)
					return false;
			} else if (!operation.equals(other.operation))
				return false;
			return true;
		}


		/**
		 * The outer type.
		 * 
		 * @return The outer type information.
		 */
		private MultStatement getOuterType() {
			return MultStatement.this;
		}

		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "MultStatementBlock [operation=" + operation
					+ ", blockValue=" + blockValue + "]";
		}
		
		
		
		
		
	}
	
	// This is the initial value of the mult statement
	private UnaryStatement initialValue;
	private List<MultStatementBlock> blocks = new ArrayList<MultStatementBlock>();
	
	
	/**
	 * The default constructor
	 */
	public MultStatement() {
		
	}


	public UnaryStatement getInitialValue() {
		return initialValue;
	}


	public void setInitialValue(UnaryStatement initialValue) {
		this.initialValue = initialValue;
	}


	public List<MultStatementBlock> getBlocks() {
		return blocks;
	}


	public void setBlocks(List<MultStatementBlock> blocks) {
		this.blocks = blocks;
	}
	
	
	/**
	 * This method adds a block
	 * 
	 * @param operation The operation to perform.
	 * @param statement The statement
	 */
	public void addBlock(String operation, UnaryStatement statement) {
		blocks.add(new MultStatementBlock(operation,statement));
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
		MultStatement other = (MultStatement) obj;
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
		return "MultStatement [initialValue=" + initialValue + ", blocks="
				+ blocks + "]";
	}
	
	
	
	
}
