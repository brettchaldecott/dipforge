/*
 * CoadunationRDFResources: The rdf resource object mappings.
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
 * DataType.java
 */

// package path
package com.rift.coad.rdf.objmapping.base;

// java imports
import java.lang.annotation.Annotation;

// the semantic imports
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;
import thewebsemantic.Uri;
import thewebsemantic.RdfProperty;
import thewebsemantic.ManagementObject;
import thewebsemantic.MemberVariableName;

// log4j import
import org.apache.log4j.Logger;

// coadunation imports
import thewebsemantic.ObjTypeId;

/**
 * The data type information
 * 
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/base#")
@RdfType("DataType")
public abstract class DataType extends RDFBase {
    // member variables.
    private String basicType;
    private String idForDataType;
    private String associatedObject = null;
    private String dataName;


    /**
     * The default constructor
     */
    public DataType() {
        try {
            basicType = this.getClass().getName();
            idForDataType = this.getClass().getName();
        } catch (Exception ex) {
            Logger.getLogger(DataType.class).fatal("Failed to generate a unique id: " +
                    ex.getMessage(),ex);
        }

        dataName = null;
    }


    /**
     * The name of the data type.
     *
     * @param dataName The name of the object.
     */
    public DataType(String dataName) {
        try {
            basicType = this.getClass().getName();
            idForDataType = this.getClass().getName();
        } catch (Exception ex) {
            Logger.getLogger(DataType.class).fatal("Failed to generate a unique id: " +
                    ex.getMessage(),ex);
        }
        this.dataName = dataName;
    }

    
    /**
     * The constructor that sets all the internal member variables.
     * 
     * @param idForDataType The type of the object.
     * @param dataName The name of this object.
     */
    public DataType(String idForDataType, String dataName) {
        basicType = this.getClass().getName();
        this.idForDataType = idForDataType;
        this.dataName = dataName;
    }


    /**
     * This method returns the composite index for the object
     *
     * @return This method returns the composite id.
     */
    @Uri()
    public final String getUri() {
        Annotation[] annotations = this.getClass().getAnnotations();
        String namespace = "http://www.coadunation.net/schema/rdf/1.0/base#";
        String rdfType = "DataType";
        for (Annotation annot : annotations) {
            if (annot.annotationType().equals(thewebsemantic.Namespace.class)) {
                namespace = ((thewebsemantic.Namespace)annot).value();
            }
            if (annot.annotationType().equals(thewebsemantic.RdfType.class)) {
                rdfType = ((thewebsemantic.RdfType)annot).value();
            }
        }
        String objectId = getObjId();
        if (objectId == null) {
            objectId = "";
        }
        if (this.associatedObject != null) {
            return namespace + rdfType + "/" + associatedObject + "/" + idForDataType + "/"  + getDataName() + "/"+ objectId;
        } else {
            return namespace + rdfType + "/" + idForDataType + "/"+ objectId;
        }

    }
    
    
    /**
     * This method returns the type information for this object.
     * 
     * @return The string containing the basic type information.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#BasicTypeId")
    public String getBasicType() {
        return basicType;
    }


    /**
     * This method is responsible for setting the basic type information.
     *
     * @param basicType The type information
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#BasicTypeId")
    public void setBasicType(String basicType) {
        this.basicType = basicType;
    }


    /**
     * This method returns the id for this data type.
     * 
     * @return This method returns the id for data type
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#IdForDataType")
    @ObjTypeId()
    public String getIdForDataType() {
        return idForDataType;
    }


    /**
     * This method sets the id for the data type.
     *
     * @param idForDataType The id for this data type
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#DataTypeId")
    public void setIdForDataType(String idForDataType) {
        this.idForDataType = idForDataType;
    }
    
    
    /**
     * This method returns the associated object data type id.
     * 
     * @return The string containing the associated data type id.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#AssociatedDataTypeId")
    @ManagementObject()
    public String getAssociatedObject() {
        return associatedObject;
    }


    /**
     * This method sets the assocated object data type information.
     *
     * @param associatedObject The string containing the associated object data type id.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#AssociatedDataTypeId")
    public void setAssociatedObject(String associatedObject) {
        this.associatedObject = associatedObject;
    }


    /**
     * The name of the object.
     *
     * @return This method returns the data name.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#DataTypeName")
    @MemberVariableName()
    public final String getDataName() {
        if (dataName == null) {
            return this.getClass().getSimpleName();
        }
        return dataName;
    }


    /**
     * This method sets the final name of the data type.
     *
     * @param dataName The name of the data type.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#DataTypeName")
    public final void setDataName(String dataName) {
        this.dataName = dataName;
    }


    /**
     * This method is defined to return a unique identifier for the sub object.
     *
     * @return The return type for the object.
     */
    public abstract String getObjId();


    /**
     * The equals method.
     * @param obj The object to perform the comparison on.
     * @return TRUE if equals, FALSE if not.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DataType other = (DataType) obj;
        if ((this.basicType == null) ? (other.basicType != null) : !this.basicType.equals(other.basicType)) {
            return false;
        }
        if ((this.idForDataType == null) ? (other.idForDataType != null) : !this.idForDataType.equals(other.idForDataType)) {
            return false;
        }
        if ((this.associatedObject == null) ? (other.associatedObject != null) : !this.associatedObject.equals(other.associatedObject)) {
            return false;
        }
        if ((this.dataName == null) ? (other.dataName != null) : !this.dataName.equals(other.dataName)) {
            return false;
        }
        String rid = getObjId();
        String lid = other.getObjId();
        if (rid == lid) {
            return true;
        } else if ((rid != null) && (lid != null) && rid.equals(lid)) {
            return true;
        }
        return false;
    }

    
    /**
     * This method is called to clone the data type object.
     * 
     * @return It duplicate of the object.
     * @throws java.lang.CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            Object result = this.getClass().newInstance();
            Field[] fields  = this.getClass().getFields();
            for (Field field : fields) {
                Object variable = field.get(this);
                if (variable instanceof DataType) {
                    variable = DataType.class.cast(variable).clone();
                } else if (variable.getClass().isArray()) {
                    variable = copyArray(variable);
                }
                field.set(result, variable);
            }
            Method[] methods = this.getClass().getMethods();
            for (Method method : methods) {
                if (method.getName().matches("get[A-Za-z0-9-]*")) {
                    String methodName = method.getName().replace("get", "set");
                    Method setter = null;
                    try {
                        setter = result.getClass().getMethod(methodName,method.getReturnType());
                    } catch (java.lang.NoSuchMethodException ex) {
                        // ignore getters that have no setters
                        continue;
                    }
                    if (isBasicType(method.getReturnType())) {
                        // make a simple copy
                        setter.invoke(result, new Object[]{method.invoke(this, new Object[]{})});
                    } else if (method.getReturnType().isArray()) {
                        // duplicate the array
                        Object src = method.invoke(this, new Object[]{});
                        Object arrayValue =copyArray(src);
                        if (arrayValue != null) {
                            setter.invoke(result,new Object[]{setter.getParameterTypes()[0].cast(arrayValue)});
                        } else {
                            setter.invoke(result,new Object[]{setter.getParameterTypes()[0].cast(src)});
                        }
                    } else if (method.getReturnType().equals(DataType.class)) {
                        // duplicate the data type
                        setter.invoke(result, new Object[]{DataType.class.cast(method.invoke(this, new Object[]{})).clone()});
                    } else {
                        // attempt a simple copy
                        setter.invoke(result, new Object[]{method.invoke(this, new Object[]{})});
                    }
                } else if (method.getName().matches("is[A-Za-z0-9-]*")) {
                    String methodName = method.getName().replace("get", "set");
                    Method setter = null;
                    try {
                        setter = result.getClass().getMethod(methodName,method.getReturnType());
                    } catch (java.lang.NoSuchMethodException ex) {
                        // ignore getters that have no setters
                        continue;
                    }
                    setter.invoke(result, new Object[]{method.invoke(this, new Object[]{})});
                }
            }
            return result;
        } catch (CloneNotSupportedException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new java.lang.RuntimeException(
                    "Failed to clone the object : " + ex.getMessage(),ex);
        }
    }
    

    /**
     * This method handles the copying of an array.
     *
     * @param array The array to copy.
     * @return The result.
     * @throws java.lang.CloneNotSupportedException
     */
    private Object copyArray(Object array) throws CloneNotSupportedException {
        if (array == null) {
            return array;
        }
        Object result = Array.newInstance(
                array.getClass().getComponentType(), Array.getLength(array));
        for (int index = 0; index < Array.getLength(array); index++) {
            Object value = Array.get(array, index);
            if (value.getClass().isPrimitive()) {
                Array.set(result, index, value);
            } else if (value.getClass().isArray()) {
                Array.set(result, index, copyArray(value));
            } else if (value instanceof DataType) {
                Array.set(result, index, DataType.class.cast(value).clone());
            } else {
                // hope assignment will solve the problem
                Array.set(result, index, value);
            }
        }
        return result;
    }


    /**
     * This method returns TRUE if the type returns is of basic type.
     *
     * @param <T> The type of object handled by this object.
     * @param c The type being delt with.
     * @return TRUE if of basic type, FALSE if not.
     */
    private <T> boolean isBasicType(Class<T> c) {
        if (c.equals(String.class)) {
            return true;
        } else if (c.equals(Date.class)) {
            return true;
        } else if (c.equals(Calendar.class)) {
            return true;
        } else if (c.equals(BigDecimal.class)) {
            return true;
        } else if (Integer.TYPE.equals(c)) {
            return true;
        } else if (Long.TYPE.equals(c)) {
            return true;
        } else if (Double.TYPE.equals(c)) {
            return true;
        } else if (Float.TYPE.equals(c)) {
            return true;
        } else if (Character.TYPE.equals(c)) {
            return true;
        } else if (Short.TYPE.equals(c)) {
            return true;
        } else if (c.isPrimitive()) {
            return true;
        }
        return false;
    }
}
