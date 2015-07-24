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
 * ContinueStatement.java
 */

// package path
package com.rift.dipforge.ls.parser.obj;

// import
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * The implementation of the if statement object
 * 
 * @author brett chaldecott
 */
public class IfStatement extends Statement implements Serializable {

	public enum IfStatementType {
		IF,
		ELSE_IF,
		ELSE
	};
	
	/**
	 * The serial version
	 */
	private static final long serialVersionUID = 1L;
	
	public class IfBlock implements Serializable {

		/**
		 * This serialization version id
		 */
		private static final long serialVersionUID = 1L;
		
		private IfStatementType type;
		private Expression expression;
		private Block block;
		
		/**
		 * The default constructor of the if statement.
		 */
		public IfBlock() {
			// TODO Auto-generated constructor stub
		}

		/**
		 * This method retrieves the if statement type for this block.
		 * 
		 * @return The type of statement.
		 */
		public IfStatementType getType() {
			return type;
		}

		
		/**
		 * This method sets the if statement type.
		 * 
		 * @param type The type of statement.
		 */
		public void setType(IfStatementType type) {
			this.type = type;
		}

		
		/**
		 * This method gets the expression used to evaluate this object.
		 * 
		 * @return The reference to the expression.
		 */
		public Expression getExpression() {
			return expression;
		}

		
		/**
		 * This method sets the expression information.
		 * 
		 * @param expression The expression.
		 */
		public void setExpression(Expression expression) {
			this.expression = expression;
		}

		
		/**
		 * This method gets the block information.
		 * 
		 * @return This method returns the block information.
		 */
		public Block getBlock() {
			return block;
		}

		
		/**
		 * This method sets the block information.
		 * 
		 * @param block The reference to the block information.
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
			result = prime * result + ((type == null) ? 0 : type.hashCode());
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
			IfBlock other = (IfBlock) obj;
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
			if (type != other.type)
				return false;
			return true;
		}

		private IfStatement getOuterType() {
			return IfStatement.this;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "IfBlock [type=" + type + ", expression=" + expression
					+ ", block=" + block + "]";
		}
		
		
		
		
	}
	
	// private member variables
	private List<IfBlock> blocks = new ArrayList<IfBlock>();
	
	
	/**
	 * The default constructor.
	 */
	public IfStatement() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * This method retrieves the list of blocks.
	 * 
	 * -@return The list of blocks.
	 */
	public List<IfBlock> getBlocks() {
		return blocks;
	}


	/**
	 * This method sets the block list.
	 * @param blocks
	 */
	public void setBlocks(List<IfBlock> blocks) {
		this.blocks = blocks;
	}
	
	
	/**
	 * This method adds a new block.
	 * 
	 * @param block The block to add.
	 */
	public void addBlock(Block block) {
		IfBlock ifBlock = new IfBlock();
		ifBlock.setBlock(block);
		ifBlock.setType(IfStatementType.ELSE);
		blocks.add(ifBlock);
	}
	
	
	/**
	 * This method adds a new block. 
	 * 
	 * @param type The type of block to add.
	 * @param expression The expression.
	 * @param block The block
	 */
	public void addBlock(IfStatementType type, Expression expression, Block block) {
		IfBlock ifBlock = new IfBlock();
		ifBlock.setExpression(expression);
		ifBlock.setBlock(block);
		ifBlock.setType(type);
		blocks.add(ifBlock);
	}
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((blocks == null) ? 0 : blocks.hashCode());
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
		IfStatement other = (IfStatement) obj;
		if (blocks == null) {
			if (other.blocks != null)
				return false;
		} else if (!blocks.equals(other.blocks))
			return false;
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IfStatement [blocks=" + blocks + "]";
	}
	
	
	
}
