package com.rift.dipforge.ls.parser.obj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rift.dipforge.ls.parser.obj.Expression.ExpressionBlock;


/**
 * This object contains the comparison information.
 * 
 * @author brett chaldecott
 */
public class Comparison implements Serializable {

	/**
	 * The serialization version for this object.
	 */
	private static final long serialVersionUID = 1L;
	
	
	public class ComparisonBlock implements Serializable {

		/**
		 * This is the serialization virsion information for this object.
		 */
		private static final long serialVersionUID = 1L;
		
		// private member variables
		private String operation;
		private Relation statement;
		
		
		/**
		 * This constructor sets up the comparison information.
		 * 
		 * @param operation The operation.
		 * @param statement The statement.
		 */
		public ComparisonBlock(String operation, Relation statement) {
			super();
			this.operation = operation;
			this.statement = statement;
		}


		/**
		 * This is the operation to perform on the values.
		 * 
		 * @return The operation to perform. 
		 */
		public String getOperation() {
			return operation;
		}

		
		/**
		 * This method sets the operation.
		 * 
		 * @param operation The string containing the operation.
		 */
		public void setOperation(String operation) {
			this.operation = operation;
		}

		
		/**
		 * This method retrieves the statement.
		 * 
		 * @return The reference to the statement.
		 */
		public Relation getStatement() {
			return statement;
		}

		
		/**
		 * This method sets the statement information.
		 * 
		 * @param statement The statement to set.
		 */
		public void setStatement(Relation statement) {
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
			ComparisonBlock other = (ComparisonBlock) obj;
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
		private Comparison getOuterType() {
			return Comparison.this;
		}


		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "ComparisonBlock [operation=" + operation + ", statement="
					+ statement + "]";
		}
	}
	
	
	// private member variables
	private Relation initialValue;
	private List<ComparisonBlock> blocks = new ArrayList<ComparisonBlock>();
	
	
	/**
	 * The default constructor
	 */
	public Comparison() {
		
	}


	/**
	 * This method returns the initial value.
	 * 
	 * @return The initial value.
	 */
	public Relation getInitialValue() {
		return initialValue;
	}

	
	/**
	 * This method sets the initial value.
	 * 
	 * @param initialValue This method sets the initial value.
	 */
	public void setInitialValue(Relation initialValue) {
		this.initialValue = initialValue;
	}


	/**
	 * This method retrieves a list of blocks.
	 * 
	 * @return The list of blocks.
	 */
	public List<ComparisonBlock> getBlocks() {
		return blocks;
	}

	
	/**
	 * This method sets the list of blocks.
	 * 
	 * @param blocks The list of blocks.
	 */
	public void setBlocks(List<ComparisonBlock> blocks) {
		this.blocks = blocks;
	}
	
	/**
	 * This method adds a new block for the given relation comparison.
	 * 
	 * @param operation The operation to perform.
	 * @param value The value to perform the comparison on.
	 */
	public void addBlock(String operation, Relation value) {
		this.blocks.add(new ComparisonBlock(operation,value));
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
		Comparison other = (Comparison) obj;
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
		return "Comparison [initialValue=" + initialValue + ", blocks="
				+ blocks + "]";
	}
	
	
	
	
}
