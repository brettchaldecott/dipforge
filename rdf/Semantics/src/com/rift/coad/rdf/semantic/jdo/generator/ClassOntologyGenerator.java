/*
 * CoaduntionSemantics: The semantic library for coadunation os
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
 * ClassOntologyGenerator.java
 */

package com.rift.coad.rdf.semantic.jdo.generator;

import com.rift.coad.rdf.semantic.Resource;
import com.rift.coad.rdf.semantic.jdo.mapping.JavaRDFTypeMapping;
import com.rift.coad.rdf.semantic.jdo.obj.ClassInfo;
import com.rift.coad.rdf.semantic.jdo.obj.MethodInfo;
import com.rift.coad.rdf.semantic.ontology.OntologyClass;
import com.rift.coad.rdf.semantic.ontology.OntologyProperty;
import com.rift.coad.rdf.semantic.ontology.OntologySession;
import com.rift.coad.rdf.semantic.persistance.PersistanceIdentifier;
import com.rift.coad.rdf.semantic.util.ClassTypeInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * This object generates an ontology file based on the list of classes an places
 * the entries in the ontology ontology object responsible for managing them.
 *
 * @author brett chaldecott
 */
public class ClassOntologyGenerator {
    
    // private member variables
    private OntologySession session;
    private List<Class> types;


    /**
     * This constructor sets the ontology session information.
     *
     * @param session The session information.
     */
    public ClassOntologyGenerator(OntologySession session) {
        this.session = session;
        types = new ArrayList<Class>();
    }


    /**
     * This constructor sets the session information and the type information.
     *
     * @param session The session object.
     * @param types The list of types.
     */
    public ClassOntologyGenerator(OntologySession session, List<Class> types) {
        this.session = session;
        this.types = types;
    }


    
    /**
     * This method is called to add the type.
     * 
     * @param type The list of types.
     */
    public void addType(Class type) {
        this.types.add(type);
    }


    /**
     * This method is called to process the types.
     */
    public void processTypes() throws GeneratorException {
        try {
            for (Class type : types) {
                processType(type);
            }
        } catch (Exception ex) {
            throw new GeneratorException("Failed to generate the ontology : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method is called to process the type passed into it.
     *
     * @param classRef The class reference
     * @throws GeneratorException
     */
    private void processType(Class classRef) throws GeneratorException {
        try {
            ClassInfo info = ClassInfo.interrogateClass(classRef);
            PersistanceIdentifier identifier = PersistanceIdentifier.getInstance(
                    info.getNamespace(), info.getLocalName());
            if (session.hasClass(identifier.toURI())) {
                return;
            }
            OntologyClass result = session.createClass(identifier.toURI());
            for (MethodInfo methodInfo : info.getGetters()) {
                PersistanceIdentifier methodIdentifier = PersistanceIdentifier.getInstance(
                    methodInfo.getNamespace(), methodInfo.getLocalName());
                OntologyProperty property = null;
                if (session.hasProperty(methodIdentifier.toURI())) {
                    property = session.getProperty(methodIdentifier.toURI());
                } else {
                    property = session.createProperty(methodIdentifier.toURI());
                }
                Class returnType = methodInfo.getMethodRef().getReturnType();
                if (returnType.equals(Resource.class) ||
                        ClassTypeInfo.isCollection(returnType)) {
                    continue;
                }
                property.setType(JavaRDFTypeMapping.getRDFTypeURI(
                        methodInfo.getMethodRef().getReturnType()));
                if (!ClassTypeInfo.isBasicType(returnType) &&
                        !types.contains(returnType)) {
                    processType(returnType);
                }
                result.addProperty(property);
            }
        } catch (Exception ex) {
            throw new GeneratorException("Failed to generate the ontology  for ["
                    + classRef.getName() + "]: " +
                    ex.getMessage(),ex);
        }
    }
}
