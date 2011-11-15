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
import java.util.List;


/**
 * This method describes the mapping of a method
 * 
 * @author brett chaldecott
 */
@Namespace("http://dipforge.sourceforge.net/schema/rdf/1.0/common/Mapping")
@LocalName("MethodMapping")
public class MethodMapping {
    
    private String id;
    private String jndi;
    private String project;
    private String className;
    private String methodName;
    private List<ParameterMapping> typeParameters;
    
    /**
     * The default constructor for the method mapping.
     */
    public MethodMapping() {
    }

    
    /**
     * The constructor that sets all internal values.
     * 
     * @param jndi The reference to the jndi object.
     * @param project The project name.
     * @param className The className path.
     * @param typeParameters The type parameters
     */
    public MethodMapping(String jndi, String project, String className,
            String methodName, List<ParameterMapping> typeParameters) {
        this.jndi = jndi;
        this.project = project;
        this.className = className;
        this.methodName = methodName;
        this.typeParameters = typeParameters;
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
     * This method gets the types.
     * 
     * @return The array of type URIS
     */
    @PropertyLocalName("TypeParameters")
    public List<ParameterMapping> getTypeParameters() {
        return typeParameters;
    }
    
    
    /**
     * This method sets the type uris
     * 
     * @param typeURIs The string containing the type uri's
     */
    @PropertyLocalName("TypeParameters")
    public void setTypeParameters(List<ParameterMapping> typeParameters) {
        this.typeParameters = typeParameters;
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
        if (this.typeParameters.equals(other.typeParameters)) {
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
        hash = 31 * hash + this.typeParameters.hashCode();
        return hash;
    }
    
    
}
