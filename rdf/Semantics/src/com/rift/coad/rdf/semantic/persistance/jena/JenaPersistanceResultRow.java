/*
 * Semantics: The semantic library for coadunation os
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
 * PersistanceProperty.java
 */
package com.rift.coad.rdf.semantic.persistance.jena;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import com.rift.coad.rdf.semantic.QueryException;
import com.rift.coad.rdf.semantic.RDFConstants;
import com.rift.coad.rdf.semantic.jdo.obj.JDODataType;
import com.rift.coad.rdf.semantic.persistance.PersistanceIdentifier;
import com.rift.coad.rdf.semantic.persistance.PersistanceQueryException;
import com.rift.coad.rdf.semantic.persistance.PersistanceResource;
import com.rift.coad.rdf.semantic.persistance.PersistanceResultRow;
import com.rift.coad.rdf.semantic.persistance.jena.http.HttpModel;
import com.rift.coad.rdf.semantic.types.DataType;
import com.rift.coad.rdf.semantic.types.XSDDataDictionary;
import com.rift.coad.rdf.semantic.util.jena.DataHelper;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.Triple;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;

/**
 * This method object contains the persistant result row information.
 *
 * @author brett chaldecott
 */
public class JenaPersistanceResultRow implements PersistanceResultRow {

    /**
     * The class that wraps a row entry.
     */
    public class JenaPersistanceRowEntry {

        protected Model jenaModel;
        protected RDFNode node;
        
        /**
         * The constructor responsible for retrieving the persistance
         * information
         *
         * @param node The node to retrieve.
         */
        public JenaPersistanceRowEntry(Model jenaModel,RDFNode node) {
            this.node = node;
            this.jenaModel = jenaModel;
        }

        /**
         * This method returns true if the object being referenced is a basic
         * type.
         *
         * @return This method returns true if this is a basic type.
         * @param name The name of the column to retrieve the result for.
         * @throws QueryException
         */
        public boolean isBasicType() throws PersistanceQueryException {
            return node.isLiteral();
        }

        
        /**
         * This method retrieves the URI of the object.
         *
         * @param name The name of the column to retrieve the URI for.
         * @return The data type information object.
         * @throws QueryException
         */
        public DataType getType() throws PersistanceQueryException {
            try {
                if (node.isLiteral()) {
                    return XSDDataDictionary.getTypeByURI(
                            node.asLiteral().getDatatypeURI());
                }
                PersistanceIdentifier typeIdentifier = PersistanceIdentifier.
                        getInstance(RDFConstants.SYNTAX_NAMESPACE,
                        RDFConstants.TYPE_LOCALNAME);
                Property property = node.getModel().getProperty(
                    typeIdentifier.toURI().toString());
                return new JDODataType(new URI(
                        node.asResource().getProperty(property).getResource().
                        getURI()));
            } catch (Exception ex) {
                throw new PersistanceQueryException(
                        "Failed to retrieve the type information : " + 
                        ex.getMessage(),ex);
            }
        }
        
        
        /**
         * This method returns the entry as the requested type.
         *
         * @param <T> The type to return.
         * @param t The type to return.
         * @return
         */
        public <T> T get(Class<T> t) throws PersistanceQueryException {
            if (DataHelper.isBasic(t)) {
                if (!node.isLiteral()) {
                    throw new PersistanceQueryException(
                            "Request for basic literal but Entry does not refer to one.");
                }
                return t.cast(DataHelper.convertLiteral(node, t));
            } else if (t.equals(PersistanceResource.class)) {
                if (!node.isResource()) {
                    throw new PersistanceQueryException(
                            "Request for Resource but Entry does not refer to one.");
                }
                return (T) new JenaPersistanceResource(jenaModel, node.as(Resource.class));
            } else if (t.equals(URI.class)) {
                if (!node.isURIResource()) {
                    throw new PersistanceQueryException(
                            "Request for URI but Entry does not refer to one.");
                }
                try {
                    return (T) new URI(node.toString());
                } catch (Exception ex) {
                    throw new PersistanceQueryException(
                            "Failed to convert the node to a URI : "
                            + ex.getMessage(), ex);
                }
            }
            throw new PersistanceQueryException(
                    "The requested type [" + t.getName()
                    + "] is not recognized");
        }
    }
    
    
    /**
     * This object is responsible for wrapping and managing the init process
     * based on sub query over http. This is performed in order to load entries
     * when required. If speed is a requirement rather optimize the SPARQL queries
     * and flatten them out.
     */
    public class JenaPersistanceHttpRowEntry extends JenaPersistanceRowEntry {
        
        private boolean isInited = false;
        
        public JenaPersistanceHttpRowEntry(Model jenaModel, RDFNode node) {
            super(jenaModel, node);
        }
        
        public boolean isBasicType() throws PersistanceQueryException {
            init();
            return super.isBasicType();
        }
        
        public DataType getType() throws PersistanceQueryException {
            init();
            return super.getType();
        }
        
        public <T> T get(Class<T> t) throws PersistanceQueryException {
            init();
            return super.get(t);
        }
        
        
        /**
         * This method is called to init a node if the current node is a uri.
         * This is required if a HTTP SPARQL Query is being used
         * @throws PersistanceQueryException 
         */
        private void init() throws PersistanceQueryException {
            if (isInited) {
                return;
            }
            if (super.isBasicType()) {
                //jenaModel = node.getModel();
                isInited = true;
                return;
            }
            // if the node is not a uri something went wrong
            if (!node.isURIResource()) {
                throw new PersistanceQueryException(
                        "Something is wrong with the data set.");
            }
            // force a sub query to retrieve all the sub values
            HttpModel httpModel = (HttpModel)jenaModel;
            Query query = QueryFactory.create(
                    String.format("SELECT * WHERE { <%s> ?predicate ?object . }", node.toString()));
            QueryExecution executioner = QueryExecutionFactory.sparqlService(httpModel.getServiceUrl(),query);
            ResultSet resultSet = executioner.execSelect();
            RDFNode rdfSubject = node;
            Dataset dataset = DatasetFactory.create();
            while(resultSet.hasNext()) {
                QuerySolution solution = resultSet.next();
                RDFNode rdfPrediate = solution.get("predicate");
                RDFNode rdfObject = solution.get("object");
                dataset.asDatasetGraph().getDefaultGraph().add(
                        Triple.create(rdfSubject.asNode(), rdfPrediate.asNode(), rdfObject.asNode()));
            }
            super.node = dataset.getDefaultModel().getRDFNode(rdfSubject.asNode());
            //super.jenaModel = dataset.getDefaultModel();
            isInited = true;
        }
    }
    
    
    // private member variable
    //private Model jenaModel;
    private QuerySolution solution;
    private int size;
    private List<String> columns = new ArrayList<String>();
    private List<JenaPersistanceRowEntry> entries =
            new ArrayList<JenaPersistanceRowEntry>();
    private Map<String, JenaPersistanceRowEntry> mapEntries =
            new HashMap<String, JenaPersistanceRowEntry>();

    /**
     * The constructor of the single row in the solution.
     *
     * @param solution The solution.
     */
    protected JenaPersistanceResultRow(Model jenaModel, QuerySolution solution) {
        //this.jenaModel = jenaModel;
        this.solution = solution;
        Iterator<String> iter = solution.varNames();
        while (iter.hasNext()) {
            String name = iter.next();
            columns.add(name);
            size++;
            JenaPersistanceRowEntry entry = null;
            if (jenaModel instanceof HttpModel) {
                entry =
                    new JenaPersistanceHttpRowEntry(jenaModel,solution.get(name));
            } else {
                entry =
                    new JenaPersistanceRowEntry(jenaModel,solution.get(name));
            }
            
            entries.add(entry);
            mapEntries.put(name, entry);
        }
    }

    /**
     * This method returns the number of columns that are
     *
     * @return
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public int size() throws PersistanceQueryException {
        return this.entries.size();
    }

    /**
     * This method returns the names of the columns in this result row.
     *
     * @return The string list containing the name of the columns.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public String[] getColumns() throws PersistanceQueryException {
        return columns.toArray(new String[0]);
    }

    /**
     * This method returns true if the object being referenced is a basic type.
     *
     * @return This method returns true if this is a basic type.
     * @param name The name of the column to retrieve the result for.
     * @throws PersistanceQueryException
     */
    public boolean isBasicType(String name) throws PersistanceQueryException {
        if (!mapEntries.containsKey(name)) {
            throw new PersistanceQueryException(
                    "The row does not contain the entry [" + name + "]");
        }
        return mapEntries.get(name).isBasicType();
    }

    /**
     * This method returns TRUE if the item is a basic type.
     *
     * @param index The index of the item.
     * @return TRUE if this is a basic type.
     * @throws PersistanceQueryException
     */
    public boolean isBasicType(int index) throws PersistanceQueryException {
        if (entries.size() <= index || index < 0) {
            throw new PersistanceQueryException(
                    "The index is out of scope [" + index + "]");
        }
        return entries.get(index).isBasicType();
    }

    
    /**
     * This method retrieves the URI of the object.
     *
     * @param name The name of the column to retrieve the URI for.
     * @return The data type information object.
     * @throws PersistanceQueryException
     */
    public DataType getType(String name) throws PersistanceQueryException {
        if (!mapEntries.containsKey(name)) {
            throw new PersistanceQueryException(
                    "The row does not contain the entry [" + name + "]");
        }
        return mapEntries.get(name).getType();
    }

    
    /**
     * This method returns the string containing the type uri.
     *
     * @param index The index of the type.
     * @return The reference to the type object
     * @throws QueryException
     */
    public DataType getType(int index) throws PersistanceQueryException {
        if (entries.size() <= index || index < 0) {
            throw new PersistanceQueryException(
                    "The index is out of scope [" + index + "]");
        }
        return entries.get(index).getType();
    }

    /**
     * This method returns the first sparql result entry identified by the name.
     * If there are multiple entries attached to a name use the index method or
     * getGroup method instead.
     *
     * @param name The name of entry ot retrieve.
     * @return The first result object attached to the given name.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public <T> T get(Class<T> t, String name) throws PersistanceQueryException {
        if (!mapEntries.containsKey(name)) {
            throw new PersistanceQueryException(
                    "The row does not contain the entry [" + name + "]");
        }
        return mapEntries.get(name).get(t);
    }

    
    /**
     * This method returns the entry identified by the index value.
     *
     * @param index The index number.
     * @return The identifier fo the index.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public <T> T get(Class<T> t, int index) throws PersistanceQueryException {
        if (entries.size() <= index || index < 0) {
            throw new PersistanceQueryException(
                    "The index is out of scope [" + index + "]");
        }
        return entries.get(index).get(t);
    }
}
