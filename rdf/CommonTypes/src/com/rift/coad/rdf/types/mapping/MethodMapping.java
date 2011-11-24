/*
 * CommonTypes: The common types
 * Copyright (C) 2011  Rift IT Contracting
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

// package
package com.rift.coad.rdf.types.mapping;

// annotations
import com.rift.coad.rdf.semantic.annotation.Identifier;
import com.rift.coad.rdf.semantic.annotation.LocalName;
import com.rift.coad.rdf.semantic.annotation.Namespace;
import com.rift.coad.rdf.semantic.annotation.PropertyLocalName;
import com.rift.coad.rdf.types.TypesException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;


/**
 * This method describes the mapping of a method
 * 
 * @author brett chaldecott
 */
@Namespace("http://dipforge.sourceforge.net/schema/rdf/1.0/common/Mapping")
@LocalName("MethodMapping")
public class MethodMapping implements Serializable {
    
    private String id;
    private String jndi;
    private String project;
    private String className;
    private String methodName;
    private String version;
    private List<ParameterMapping> parameters;
    
    /**
     * The default constructor for the method mapping.
     */
    public MethodMapping() {
        this.parameters = new ArrayList<ParameterMapping>();
    }

    
    /**
     * The constructor that sets all internal values.
     * 
     * @param id The id of this method
     * @param jndi The reference to the jndi object.
     * @param project The project name.
     * @param className The className path.
     * @param typeParameters The type parameters
     */
    public MethodMapping(String jndi, String project, String className,
            String methodName) throws TypesException {
        this.jndi = jndi;
        this.project = project;
        this.className = className;
        this.methodName = methodName;
        this.parameters = new ArrayList<ParameterMapping>();
        this.generateHashId();
    }
    
    
    /**
     * The constructor that sets all internal values.
     * 
     * @param id The id of this method
     * @param jndi The reference to the jndi object.
     * @param project The project name.
     * @param className The className path.
     * @param parameters The parameter definitions
     */
    public MethodMapping(String jndi, String project, String className,
            String methodName, List<ParameterMapping> parameters)
        throws TypesException {
        this.jndi = jndi;
        this.project = project;
        this.className = className;
        this.methodName = methodName;
        this.parameters = parameters;
        this.generateHashId();
    }

    
    /**
     * This method returns the id of the method mapping.
     * 
     * @return The string containing the id of this method. 
     */
    @Identifier()
    @PropertyLocalName("id")
    public String getId() {
        return id;
    }

    
    /**
     * This method sets the id of the method mapping.
     * 
     * @param id This method sets the id of the method mapping
     */
    @PropertyLocalName("id")
    public void setId(String id) {
        this.id = id;
    }

    
    /**
     * This method retrieves the property name.
     * 
     * @return The JNDI value.
     */
    @PropertyLocalName("JNDI")
    public String getJndi() {
        return jndi;
    }

    
    /**
     * This method sets the JNDI reference.
     * 
     * @param jndi The jndi reference.
     */
    @PropertyLocalName("JNDI")
    public void setJndi(String jndi) {
        this.jndi = jndi;
    }

    
    /**
     * This method gets the project name
     * 
     * @return The name of the project
     */
    @PropertyLocalName("Project")
    public String getProject() {
        return project;
    }

    
    /**
     * This method sets the name of the project.
     * 
     * @param project This method sets the project name.
     */
    @PropertyLocalName("Project")
    public void setProject(String project) {
        this.project = project;
    }

    
    /**
     * This method retrieves the path to the className file.
     * 
     * @return The project type
     */
    @PropertyLocalName("ClassName")
    public String getClassName() {
        return className;
    }

    
    /**
     * This method sets the className path
     * 
     * @param className The string containing the new className path
     */
    @PropertyLocalName("ClassName")
    public void setClassName(String className) {
        this.className = className;
    }

    
    /**
     * This method returns the method name
     * 
     * @return This method return the name
     */
    @PropertyLocalName("MethodName")
    public String getMethodName() {
        return methodName;
    }

    
    /**
     * This method sets the name of the method.
     * 
     * @param methodName The name of the method.
     */
    @PropertyLocalName("MethodName")
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    
    
    /**
     * THis method returns the version information for this method.
     * 
     * @return The string containing the version information for this method.
     */
    @PropertyLocalName("Version")
    public String getVersion() {
        return version;
    }

    
    /**
     * This method sets the version information for the method mapping.
     * 
     * @param version The string containing the version information.
     */
    @PropertyLocalName("Version")
    public void setVersion(String version) {
        this.version = version;
    }
    
    
    /**
     * This method gets the parameter definition list.
     * 
     * @return The array of type URIS
     */
    @PropertyLocalName("Parameters")
    public List<ParameterMapping> getParameters() {
        return parameters;
    }

    
    /**
     * This method sets the parameter definition list
     * 
     * @param parameters The list of parameter definitions
     */
    @PropertyLocalName("Parameters")
    public void setParameters(List<ParameterMapping> parameters) {
        this.parameters = parameters;
    }

    
    /**
     * The equals operator.
     * 
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
        final MethodMapping other = (MethodMapping) obj;
        if ((this.jndi == null) ? (other.jndi != null) : !this.jndi.equals(other.jndi)) {
            return false;
        }
        if ((this.project == null) ? (other.project != null) : !this.project.equals(other.project)) {
            return false;
        }
        if ((this.className == null) ? (other.className != null) : !this.className.equals(other.className)) {
            return false;
        }
        if (this.parameters.equals(other.parameters)) {
            return false;
        }
        return true;
    }

    
    /**
     * This method returns the hash code for this object.
     * 
     * @return The integer containing the hash code for this object.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (this.jndi != null ? this.jndi.hashCode() : 0);
        hash = 31 * hash + (this.project != null ? this.project.hashCode() : 0);
        hash = 31 * hash + (this.className != null ? this.className.hashCode() : 0);
        hash = 31 * hash + this.parameters.hashCode();
        return hash;
    }
    
    
    /**
     * This method is called to generate the hash id. It uses the internal member
     * variables to generate a unique hash value.
     * 
     * @exception TypesException
     */
    private void generateHashId() throws TypesException {
        try {
            StringBuffer sbValueBeforeMD5 = new StringBuffer();
            // init the md5 hash
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            
            sbValueBeforeMD5.append(this.jndi);
            sbValueBeforeMD5.append(":");
            sbValueBeforeMD5.append(this.project);
            sbValueBeforeMD5.append(":");
            sbValueBeforeMD5.append(this.className);
            sbValueBeforeMD5.append(":");
            sbValueBeforeMD5.append(this.methodName);
            sbValueBeforeMD5.append(":");
            sbValueBeforeMD5.append(this.version);
            
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
}
