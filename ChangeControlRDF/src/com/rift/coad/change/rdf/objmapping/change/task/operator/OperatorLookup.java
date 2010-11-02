/*
 * ChangeControlRDF: The rdf information for the change control system.
 * Copyright (C) 2009  Rift IT Contracting
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

package com.rift.coad.change.rdf.objmapping.change.task.operator;

// java imports
import java.lang.reflect.Field;

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
            Field[] fields = OperatorConstants.class.getDeclaredFields();
            for (Field field : fields) {
                if ((name.equalsIgnoreCase(field.getName())) || name.equals(field.get(new String()).toString())) {
                    return new Operator(field.get(new String()).toString(),field.getName());
                }
            }
            throw new LogicalException("The operrator [" + name + "] could not be found");
        } catch (LogicalException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new LogicalException("Failed to retrieve the oeprator : " + ex.getMessage(),ex);
        }
    }
}
