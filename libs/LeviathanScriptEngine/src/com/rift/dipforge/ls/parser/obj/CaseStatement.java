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
 * ForStatement.java
 */

// package name
package com.rift.dipforge.ls.parser.obj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * This is the definition of the case statement.
 * 
 * @author brett chaldecott
 */
public class CaseStatement extends Statement implements Serializable {

	/**
	 * The case statement.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The case block containing the evaluation information and the block to execute.
	 * 
	 * @author brett chaldecott
	 */
	public class CaseBlock implements Serializable {
		// serialization version
		private static final long serialVersionUID = 1L;
		
		/**
		 * The private expression 
		 */
		private Expression expression;
		private Block block;
		
		/**
		 * The default constructor
		 */
		public CaseBlock() {
			// TODO Auto-generated constructor stub
		}
		
		
		/**
		 * The expression to perform the comparison on.
		 * 
		 * @param expression The expression to execute.
		 * @param block The block to perform the equals on.
		 */
		public CaseBlock(Expression expression, Block block) {
			super();
			this.expression = expression;
			this.block = block;
		}


		/**
		 * This method returns the expression.
		 * 
		 * @return The expression to perform the evaluation on.
		 */
		public Expression getExpression() {
			return expression;
		}

		/**
		 * This method sets the expression.
		 * 
		 * @param expression This method sets the expression.
		 */
		public void setExpression(Expression expression) {
			this.expression = expression;
		}

		
		/**
		 * This method retrieves the block information.
		 * 
		 * @return The reference to the block.
		 */
		public Block getBlock() {
			return block;
		}

		
		/**
		 * This method sets the block reference.
		 * 
		 * @param block The new block reference.
		 */
		public void setBlock(Block block) {
			this.block = block;
		}


		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((block == null) ? 0 : block.hashCode());
			result = prime * result
					+ ((expression == null) ? 0 : expression.hashCode());
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
			CaseBlock other = (CaseBlock) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (block == null) {
				if (other.block != null)
					return false;
			} else if (!block.equals(other.block))
				return false;
			if (expression == null) {
				if (other.expression != null)
					return false;
			} else if (!expression.equals(other.expression))
				return false;
			return true;
		}


		/**
		 * This method get outer type
		 * 
		 * @return The reference to the case statement.
		 */
		private CaseStatement getOuterType() {
			return CaseStatement.this;
		}
		
		
		
	}
	
	// private member variables
	private Expression expression;
	private List<CaseBlock> blocks = new ArrayList<CaseBlock>();
	
	
	/**
	 * The default constructor
	 */
	public CaseStatement() {
		
	}
	
	
	/**
	 * This method returns the expression value.
	 * 
	 * @return The expression to perfrom the comparison on.
	 */
	public Expression getExpression() {
		return expression;
	}


	/**
	 * This method sets the expression.
	 * 
	 * @param expression The expression value.
	 */
	public void setExpression(Expression expression) {
		this.expression = expression;
	}
	
	
	/**
	 * This method returns the list of blocks to check through
	 * 
	 * @return The list of blocks.
	 */
	public List<CaseBlock> getBlocks() {
		return blocks;
	}

	
	/**
	 * The setter for the list of blocks.
	 * 
	 * @param blocks The list of blocks
	 */
	public void setBlocks(List<CaseBlock> blocks) {
		this.blocks = blocks;
	}


	/**
	 * This method adds a new block.
	 * 
	 * @param exp The expression to perform the comparison on.
	 * @param blk The block that will be executed if the expression matches.
	 */
	public void addBlock(Expression exp, Block blk) {
		this.blocks.add(new CaseBlock(exp,blk));
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
				+ ((expression == null) ? 0 : expression.hashCode());
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
		CaseStatement other = (CaseStatement) obj;
		if (blocks == null) {
			if (other.blocks != null)
				return false;
		} else if (!blocks.equals(other.blocks))
			return false;
		if (expression == null) {
			if (other.expression != null)
				return false;
		} else if (!expression.equals(other.expression))
			return false;
		return true;
	}

	
	
}
