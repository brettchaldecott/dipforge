/*
 * ChangeControlRDF: The rdf information for the change control system.
 * Copyright (C) 2009  2015 Burntjam
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
 * OperatorLookup.java
 */

package com.rift.coad.change.rdf.objmapping.client.change.task.operator;

// java imports
//import java.lang.reflect.Field;

/**
 * The tool for returning operator instances.
 *
 * @author brett chaldecott
 */
public class OperatorLookup {

    /**
     * This static method returns a reference to the specified operator.
     *
     * @param name The name of the operator to retrieve.
     * @return The reference to the operator.
     * @throws com.rift.coad.change.rdf.objmapping.change.task.operator.LogicalException
     */
    public static Operator get(String name) throws LogicalException {
        try {
            // logical operations
            if (name.equals("AND") || name.equals(OperatorConstants.AND)) {
                return new Operator(OperatorConstants.AND,"AND");
            } else if (name.equals("OR") || name.equals(OperatorConstants.OR)) {
                return new Operator(OperatorConstants.OR,"OR");
            } else if (name.equals("NOT") || name.equals(OperatorConstants.NOT)) {
                return new Operator(OperatorConstants.NOT,"NOT");
            } else if (name.equals("NOT_EQUAL") || name.equals(OperatorConstants.NOT_EQUAL)) {
                return new Operator(OperatorConstants.NOT_EQUAL,"NOT_EQUAL");
            } else if (name.equals("EQUAL") || name.equals(OperatorConstants.EQUAL)) {
                return new Operator(OperatorConstants.EQUAL,"EQUAL");
            } else if (name.equals("LESS") || name.equals(OperatorConstants.LESS)) {
                return new Operator(OperatorConstants.LESS,"LESS");
            } else if (name.equals("GREATER") || name.equals(OperatorConstants.GREATER)) {
                return new Operator(OperatorConstants.GREATER,"GREATER");
            } else if (name.equals("INCREMENT") || name.equals(OperatorConstants.INCREMENT)) {
                return new Operator(OperatorConstants.INCREMENT,"INCREMENT");
            } else if (name.equals("DECREMENT") || name.equals(OperatorConstants.DECREMENT)) {
                return new Operator(OperatorConstants.DECREMENT,"DECREMENT");
            } else if (name.equals("ADD") || name.equals(OperatorConstants.ADD)) {
                return new Operator(OperatorConstants.ADD,"ADD");
            } else if (name.equals("SUBTRACT") || name.equals(OperatorConstants.SUBTRACT)) {
                return new Operator(OperatorConstants.SUBTRACT,"SUBTRACT");
            } else if (name.equals("MULTIPLY") || name.equals(OperatorConstants.MULTIPLY)) {
                return new Operator(OperatorConstants.MULTIPLY,"MULTIPLY");
            } else if (name.equals("DIVIDE") || name.equals(OperatorConstants.DIVIDE)) {
                return new Operator(OperatorConstants.DIVIDE,"DIVIDE");
            } else if (name.equals("MOD") || name.equals(OperatorConstants.MOD)) {
                return new Operator(OperatorConstants.MOD,"MOD");
            }

            throw new LogicalException("The operator [" + name + "] could not be found.");
        } catch (LogicalException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new LogicalException("Failed to retrieve the oeprator : " + ex.getMessage(),ex);
        }
    }
}
