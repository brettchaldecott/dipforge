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
 * Expression.java
 */

// package path
package com.rift.dipforge.ls.parser.obj;

// imports
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rift.dipforge.ls.parser.obj.Relation.RelationStatementBlock;


/**
 * The expression that amaligimated expression.
 * 
 * @author brett chaldecott
 *
 */
public class Expression implements Serializable {

	/**
	 * This variable contains the serialization version information.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The expression block
	 * 
	 * @author brett chaldecott
	 *
	 */
	public class ExpressionBlock implements Serializable {
		
		/**
		 * The serial version uid.
		 */
		private static final long serialVersionUID = 1L;
		
		// private member variables
		private String operation;
		private Comparison statement;
		
		
		/**
		 * The expression block.
		 *  
		 * @param operation The operation.
		 * @param statement The statement.
		 */
		public ExpressionBlock(String operation, Comparison statement) {
			this.operation = operation;
			this.statement = statement;
		}


		/**
		 * This is the operation to perform.
		 * 
		 * @return The operation to perform.
		 */
		public String getOperation() {
			return operation;
		}
		
		
		/**
		 * This method sets the operation.
		 * 
		 * @param operation The operation to perform.
		 */
		public void setOperation(String operation) {
			this.operation = operation;
		}

		
		/**
		 * This method returns the statement information.
		 * 
		 * @return The reference to the relation ship.
		 */
		public Comparison getStatement() {
			return statement;
		}

		
		/**
		 * This method sets the statement.
		 * 
		 * @param statement The statement.
		 */
		public void setStatement(Comparison statement) {
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
			ExpressionBlock other = (ExpressionBlock) obj;
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
		 * @return
		 */
		private Expression getOuterType() {
			return Expression.this;
		}


		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "ExpressionBlock [operation=" + operation + ", statement="
					+ statement + "]";
		}
		
		
		
		
	}
	
	// private member variables
	private Comparison initialValue;
	private List<ExpressionBlock> blocks = new ArrayList<ExpressionBlock>();
	
	/**
	 * The default constructor
	 */
	public Expression() {
		
	}

	
	/**
	 * The initial value.
	 * 
	 * @return This method retrieves the initial value.
	 */
	public Comparison getInitialValue() {
		return initialValue;
	}

	/**
	 * This method sets the initial value.
	 * 
	 * @param initialValue This method sets the initial value.
	 */
	public void setInitialValue(Comparison initialValue) {
		this.initialValue = initialValue;
	}

	
	/**
	 * This method gets the blocks.
	 * 
	 * @return The list of blocks.
	 */
	public List<ExpressionBlock> getBlocks() {
		return blocks;
	}
	
	
	/**
	 * This method sets the blocks.
	 * 
	 * @param blocks The new list of blocks.
	 */
	public void setBlocks(List<ExpressionBlock> blocks) {
		this.blocks = blocks;
	}
	
	
	/**
	 * This method adds a block.
	 * 
	 * @param operation The operation used by the block.
	 * @param value The value.
	 */
	public void addBlock(String operation, Comparison value) {
		this.blocks.add(new ExpressionBlock(operation,value));
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
		Expression other = (Expression) obj;
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
		return "Expression [initialValue=" + initialValue + ", blocks="
				+ blocks + "]";
	}
	
	
	
	
}
