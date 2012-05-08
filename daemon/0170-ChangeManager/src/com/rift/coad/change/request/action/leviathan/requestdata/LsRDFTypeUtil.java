/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2012  Rift IT Contracting
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
 * LsRDFTypeManager.java
 */

// package path
package com.rift.coad.change.request.action.leviathan.requestdata;

import com.rift.coad.rdf.semantic.RDFConstants;
import com.rift.coad.rdf.semantic.Resource;
import com.rift.coad.rdf.semantic.ontology.OntologyClass;
import com.rift.coad.rdf.semantic.ontology.OntologyProperty;
import com.rift.coad.rdf.semantic.persistance.PersistanceIdentifier;
import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.parser.obj.CallStatement;
import java.lang.reflect.Field;
import java.util.List;

/**
 * This util helps the ls rdf type util.
 * 
 * @author brett chaldecott
 */
public class LsRDFTypeUtil {
    
    /**
     * This method returns the resource identified by the call statement.
     * 
     * @param variable The variable to interigate.
     * @param entries The entries.
     * @return The reference to the resource
     */
    public static Resource getResource(Resource variable, 
            List<CallStatement.CallStatementEntry> entries)
            throws EngineException {
        try {
            Resource result = variable;
            for (int index = 0;
                    index < entries.size(); index++) {
                CallStatement.CallStatementEntry entry =
                        entries.get(index);
                OntologyClass typeResource = getOntologyClass(result);
                List<OntologyProperty> properties = typeResource.listProperties();
                boolean found = false;
                for (OntologyProperty property : properties) {
                    if (property.getLocalname().equals(entry.getName())) {
                        result = result.getProperty(Resource.class, 
                                property.getURI().toString());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    throw new EngineException("The entry [" + entry.getName() + 
                            "] was not found on the resource [" + 
                            result.getURI().toString() + "]");
                }
            }
            return result;
        } catch (EngineException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new EngineException(
                    "Failed to retrieve the field information because " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the reference to the ontology class that defines this object.
     * 
     * @param resource The resource.
     * @return The reference to the ontology class
     * @throws EngineException 
     */
    public static OntologyClass getOntologyClass(Resource resource) 
            throws EngineException {
        try {
            PersistanceIdentifier typeIdentifier = 
                    PersistanceIdentifier.getInstance(RDFConstants.SYNTAX_NAMESPACE,
                    RDFConstants.TYPE_LOCALNAME);
            return resource.getProperty(OntologyClass.class,
                    typeIdentifier.toURI().toString());
        } catch (Exception ex) {
            throw new EngineException(
                    "This object returns the ontology class reference : " + 
                    ex.getMessage());
        }
    }
}
