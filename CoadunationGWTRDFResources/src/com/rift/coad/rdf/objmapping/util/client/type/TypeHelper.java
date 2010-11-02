/*
 * CoadunationRDFResources: The rdf resource object mappings.
 * Copyright (C) 2010  Rift IT Contracting
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
 * TypeManager.java
 */

package com.rift.coad.rdf.objmapping.util.client.type;

import com.rift.coad.rdf.objmapping.client.base.DataType;
import com.rift.coad.rdf.objmapping.client.base.number.RDFDouble;
import com.rift.coad.rdf.objmapping.client.base.number.RDFInteger;
import com.rift.coad.rdf.objmapping.client.base.number.RDFLong;
import com.rift.coad.rdf.objmapping.client.base.number.RDFFloat;
import com.rift.coad.rdf.objmapping.client.base.str.GenericString;
import com.smartgwt.client.util.SC;

/**
 * The type helper that provides the ability to
 *
 * @author brett chaldecott
 */
public class TypeHelper {

    /**
     * This method is used to return the value of a given data type.
     *
     * @param value The value to extract the string value from.
     * @return The string value.
     */
    public static String getValueAsString(DataType value) {
        if (value.getBasicType().equals(TypeManager.TYPES[23])) {
            //SC.say("Value : " + ((GenericString)value).getValue());
            return ((GenericString)value).getValue();
        } else if (value.getBasicType().equals(TypeManager.TYPES[50])) {
            return "" + ((RDFLong)value).getValue();
        } else if (value.getBasicType().equals(TypeManager.TYPES[51])) {
            return "" + ((RDFInteger)value).getValue();
        } else if (value.getBasicType().equals(TypeManager.TYPES[52])) {
            return "" + ((RDFFloat)value).getValue();
        } else if (value.getBasicType().equals(TypeManager.TYPES[53])) {
            return "" + ((RDFDouble)value).getValue();
        }
        SC.say("Unknown type : " + value.getBasicType());
        return value.toString();
    }


    /**
     *
     * @param type
     * @param value
     * @return
     */
    public static DataType getTypeFromString(String type, String value) {
        try {
            if (type.equals(TypeManager.TYPES[23])) {
                GenericString stringValue = (GenericString)TypeManager.getType(type);
                stringValue.setValue(value);
                return stringValue;
                //return ((GenericString)value).getValue();
            } else if (type.equals(TypeManager.TYPES[50])) {
                RDFLong longValue = (RDFLong)TypeManager.getType(type);
                longValue.setValue(Long.parseLong(value));
                return longValue;
                //return "" + ((RDFLong)value).getValue();
            } else if (type.equals(TypeManager.TYPES[51])) {
                RDFInteger integerValue = (RDFInteger)TypeManager.getType(type);
                integerValue.setValue(Integer.parseInt(value));
                return integerValue;
            } else if (type.equals(TypeManager.TYPES[52])) {
                RDFFloat floatValue = (RDFFloat)TypeManager.getType(type);
                floatValue.setValue(Float.parseFloat(value));
                return floatValue;
            } else if (type.equals(TypeManager.TYPES[53])) {
                RDFDouble doubleValue = (RDFDouble)TypeManager.getType(type);
                doubleValue.setValue(Double.parseDouble(value));
                return doubleValue;
            }
        } catch (Exception ex) {
            SC.say("Failed to set the type ["+type+"]value : " + ex.getMessage());
            throw new java.lang.RuntimeException("Failed to retrieve the type : " +
                    ex.getMessage());
        }
        SC.say("Un-recognised type ["+type+"]");
        throw new java.lang.RuntimeException("The type was not recognised : " +
                    type);
    }
}
