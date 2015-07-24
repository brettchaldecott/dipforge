/*
 * CommonTypes: The common types
 * Copyright (C) 2011  2015 Burntjam
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
 * MethodMapping.java
 */
package com.rift.coad.rdf.types.mapping;

// annotations
import com.rift.coad.rdf.semantic.annotation.Identifier;
import com.rift.coad.rdf.semantic.annotation.LocalName;
import com.rift.coad.rdf.semantic.annotation.Namespace;
import com.rift.coad.rdf.semantic.annotation.PropertyLocalName;
import com.rift.coad.rdf.types.TypesException;
import java.io.Serializable;
import java.security.MessageDigest;


/**
 * This method maps the parameters.
 * 
 * @author brett chaldecott
 */
@Namespace("http://dipforge.sourceforge.net/schema/rdf/1.0/common/ParameterMapping")
@LocalName("Parameter")
public class ParameterMapping implements Serializable {
    
    private String id;
    private String methodId;
    private String name;
    private String type;

    
    /**
     * The default constructor.
     */
    public ParameterMapping() {
    }

    
    /**
     * This constructor is called to setup the parameter information.
     * 
     * @param methodId The id of the method this parameter is tied to.
     * @param name The name of this parameter.
     * @param type The type of this parameter.
     * @exception TypesException
     */
    public ParameterMapping(String methodId, String name, String type) 
        throws TypesException {
        this.methodId = methodId;
        this.name = name;
        this.type = type;
        generateHashId();
    }

    
    /**
     * This method sets the id of the parameter mapping object.
     * 
     * @return The string containing the id of this parameter object.
     */
    @Identifier()
    @PropertyLocalName("Id")
    public String getId() {
        return id;
    }

    
    /**
     * This method sets the id of the parameter.
     * 
     * @param id The id of the parameter mapping object. 
     */
    @PropertyLocalName("Id")
    public void setId(String id) {
        this.id = id;
    }

    
    /**
     * This method returns the id of the method this parameter is tied to.
     * 
     * @return The string containing the method id value.
     */
    @PropertyLocalName("MethodId")
    public String getMethodId() {
        return methodId;
    }

    
    /**
     * This method sets the method id that this parameter is tied to.
     * 
     * @param methodId The id of the method. 
     */
    @PropertyLocalName("MethodId")
    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }
    
    
    /**
     * This method retrieves the name of this parameter.
     * 
     * @return The string containing the name of this parameter.
     */
    @PropertyLocalName("Name")
    public String getName() {
        return name;
    }

    
    /**
     * This method sets the name of the parameter.
     * 
     * @param name The new name of the parameter
     */
    @PropertyLocalName("Name")
    public void setName(String name) {
        this.name = name;
    }

    
    /**
     * This method retrieves the name of the type parameter.
     * 
     * @return The string containing the name of the type.
     */
    @PropertyLocalName("Type")
    public String getType() {
        return type;
    }

    
    
    /**
     * This method sets the type of the parameter.
     * 
     * @param type The string containing the new type name.
     */
    @PropertyLocalName("Type")
    public void setType(String type) {
        this.type = type;
    }
    
    
    /**
     * This method determines is the parameter values are equal.
     * 
     * @param obj The object to perform the equals operation on.
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
        final ParameterMapping other = (ParameterMapping) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.type == null) ? (other.type != null) : !this.type.equals(other.type)) {
            return false;
        }
        return true;
    }

    
    /**
     * This method returns the hash code.
     * 
     * @return The integer containing the hash code. 
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 83 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 83 * hash + (this.type != null ? this.type.hashCode() : 0);
        return hash;
    }
    
    
    /**
     * This method is called to generate the hash id for this parameter.
     * 
     * @throws TypesException 
     */
    private void generateHashId() throws TypesException {
        try {
            StringBuffer sbValueBeforeMD5 = new StringBuffer();
            // init the md5 hash
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            
            sbValueBeforeMD5.append(this.methodId);
            sbValueBeforeMD5.append(":");
            sbValueBeforeMD5.append(this.name);
            sbValueBeforeMD5.append(":");
            sbValueBeforeMD5.append(this.type);
            
            // generate the md5 hash value.
            String valueBeforeMD5 = sbValueBeforeMD5.toString();
            md5.update(sbValueBeforeMD5.toString().getBytes());
            byte[] array = md5.digest();
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < array.length; ++j) {
                int b = array[j] & 0xFF;
                if (b < 0x10) sb.append('0');
                sb.append(Integer.toHexString(b));
            }
            
            this.id = sb.toString();
            
        } catch (Exception ex) {
            throw new TypesException("Failed to generate the hash id : " + 
                    ex.getMessage(),ex);
        }
    }

    
    /**
     * This method returns the list of parameters.
     * 
     * @return The list of parameters.
     */
    @Override
    public String toString() {
        return "ParameterMapping{" + "id=" + id + ", methodId=" + methodId + ", name=" + name + ", type=" + type + '}';
    }
    
    
    
}
