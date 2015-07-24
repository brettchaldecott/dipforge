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

// package
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * The definition of the add statement.
 * 
 * @author brett chaldecott
 */
public class AddStatement extends Statement implements Serializable {

	/**
	 * The serial version
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * This method adds a statement block
	 * 
	 * @author brett chaldecott
	 */
	public class AddStatementBlock implements Serializable {

		/**
		 * The serialization version information.
		 */
		private static final long serialVersionUID = 1L;
		
		private String operation;
		private MultStatement statement;
		
		/**
		 * The default constructor
		 */
		public AddStatementBlock() {
			
		}
		
		
		/**
		 * This constructor sets all the internal member variables.
		 * 
		 * @param operation The operation to perform.
		 * @param statement The statement.
		 */
		public AddStatementBlock(String operation, MultStatement statement) {
			super();
			this.operation = operation;
			this.statement = statement;
		}


		/**
		 * The operation being performed on the statement
		 * 
		 * @return The operation.
		 */
		public String getOperation() {
			return operation;
		}


		/**
		 * The operation to perform on the add statement.
		 * 
		 * @param operation The operation to perform
		 */
		public void setOperation(String operation) {
			this.operation = operation;
		}

		
		/**
		 * The statement to execute.
		 * 
		 * @return The mult statement.
		 */
		public MultStatement getStatement() {
			return statement;
		}

		/**
		 * The statement to execute.
		 * 
		 * @param statement The statement.
		 */
		public void setStatement(MultStatement statement) {
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
			AddStatementBlock other = (AddStatementBlock) obj;
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
		 * The outer type information
		 * 
		 * @return The outer type
		 */
		private AddStatement getOuterType() {
			return AddStatement.this;
		}


		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "AddStatementBlock [operation=" + operation + ", statement="
					+ statement + "]";
		}
		
	}
	
	
	// private member variables
	private MultStatement initialValue;
	private List<AddStatementBlock> blockStatement = new ArrayList<AddStatementBlock>();
	
	
	/**
	 * The default constructor
	 */
	public AddStatement() {
		
	}

	
	/**
	 * This constructor sets up all the internal values.
	 * 
	 * @param initialValue The initial value.
	 * @param blockStatement The block statement.
	 */
	public AddStatement(MultStatement initialValue,
			List<AddStatementBlock> blockStatement) {
		super();
		this.initialValue = initialValue;
		this.blockStatement = blockStatement;
	}


	/**
	 * This method returns the initial value.
	 * 
	 * @return The reference to the mult statement.
	 */
	public MultStatement getInitialValue() {
		return initialValue;
	}

	
	/**
	 * This method sets the initial value.
	 * 
	 * @param initialValue The reference to the initial value.
	 */
	public void setInitialValue(MultStatement initialValue) {
		this.initialValue = initialValue;
	}

	
	/**
	 * This method returns a reference to the list of statements.
	 * 
	 * @return The list of add statements.
	 */
	public List<AddStatementBlock> getBlockStatement() {
		return blockStatement;
	}

	/**
	 * This method returns a reference to the block statement
	 * 
	 * @param blockStatement The reference to the block statement.
	 */
	public void setBlockStatement(List<AddStatementBlock> blockStatement) {
		this.blockStatement = blockStatement;
	}
	
	
	/**
	 * This method adds a new block statement
	 * 
	 * @param operation The operation to perform on the value.
	 * @param value The that the operation will be performed on.
	 */
	public void addBlockStatement(String operation, MultStatement value) {
		this.blockStatement.add(new AddStatementBlock(operation,value));
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((blockStatement == null) ? 0 : blockStatement.hashCode());
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
		AddStatement other = (AddStatement) obj;
		if (blockStatement == null) {
			if (other.blockStatement != null)
				return false;
		} else if (!blockStatement.equals(other.blockStatement))
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
		return "AddStatement [initialValue=" + initialValue
				+ ", blockStatement=" + blockStatement + "]";
	}
	
	
	
	
}
