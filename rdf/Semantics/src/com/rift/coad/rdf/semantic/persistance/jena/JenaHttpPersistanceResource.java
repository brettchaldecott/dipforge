/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rift.coad.rdf.semantic.persistance.jena;

import com.rift.coad.rdf.semantic.persistance.PersistanceException;
import com.rift.coad.rdf.semantic.persistance.PersistanceIdentifier;
import com.rift.coad.rdf.semantic.persistance.PersistanceProperty;
import com.rift.coad.rdf.semantic.persistance.PersistanceQueryException;
import com.rift.coad.rdf.semantic.persistance.PersistanceResource;
import com.rift.coad.rdf.semantic.persistance.jena.http.HttpModel;
import java.net.URI;
import java.util.Calendar;
import java.util.List;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.Triple;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.log4j.Logger;

/**
 * The jena http persistance resource
 *
 * @author brett chaldecott
 */
public class JenaHttpPersistanceResource extends JenaPersistanceResource {

    // class static variables
    private static Logger log = Logger.getLogger(JenaHttpPersistanceResource.class);

    private boolean isInited = false;

    public JenaHttpPersistanceResource(Model jenaModel, Resource resource) {
        super(jenaModel, resource);
    }

    @Override
    public Resource getResource() {
        try{
            init();
        } catch (Exception ex) {
            log.error("Failed to init the jena http persistance resource : " + ex.getMessage(),ex);
        }
        return super.getResource();
    }

    @Override
    public void removeProperty(PersistanceIdentifier identifier, PersistanceResource resource) throws PersistanceException {
        init();
        super.removeProperty(identifier, resource);
    }

    @Override
    public void removeProperty(PersistanceIdentifier identifier) throws PersistanceException {
        init();
        super.removeProperty(identifier);
    }

    @Override
    public PersistanceProperty getProperty(PersistanceIdentifier identifier) throws PersistanceException {
        init();
        return super.getProperty(identifier);
    }

    @Override
    public List<PersistanceProperty> listProperties(PersistanceIdentifier identifier) throws PersistanceException {
        init();
        return super.listProperties(identifier);
    }

    @Override
    public List<PersistanceProperty> listProperties() throws PersistanceException {
        init();
        return super.listProperties();
    }

    @Override
    public PersistanceProperty createProperty(PersistanceIdentifier identifier) throws PersistanceException {
        init();
        return super.createProperty(identifier);
    }

    @Override
    public boolean hasProperty(PersistanceIdentifier identifier) throws PersistanceException {
        init();
        return super.hasProperty(identifier);
    }

    @Override
    public PersistanceIdentifier getPersistanceIdentifier() {
        try{
            init();
        } catch (Exception ex) {
            log.error("Failed to init the jena http persistance resource : " + ex.getMessage(),ex);
        }
        return super.getPersistanceIdentifier();
    }

    @Override
    public String getValueAsString() throws PersistanceException {
        init();
        return super.getValueAsString();
    }

    @Override
    public Calendar getValueAsCalendar() throws PersistanceException {
        init();
        return super.getValueAsCalendar();
    }

    @Override
    public float getValueAsFloat() throws PersistanceException {
        init();
        return super.getValueAsFloat();
    }

    @Override
    public double getValueAsDouble() throws PersistanceException {
        init();
        return super.getValueAsDouble();
    }

    @Override
    public char getValueAsCharacter() throws PersistanceException {
        init();
        return super.getValueAsCharacter();
    }

    @Override
    public long getValueAsLong() throws PersistanceException {
        init();
        return super.getValueAsLong();
    }

    @Override
    public boolean getValueAsBoolean() throws PersistanceException {
        init();
        return super.getValueAsBoolean();
    }

    @Override
    public URI getURI() throws PersistanceException {
        init();
        return super.getURI();
    }

    
    
    
    /**
     * This method is called to init a node if the current node is a uri. This
     * is required if a HTTP SPARQL Query is being used
     *
     * @throws PersistanceQueryException
     */
    private void init() throws PersistanceException {
        if (isInited) {
            return;
        }
        // if the node is not a uri something went wrong
        if (!resource.isURIResource()) {
            throw new PersistanceException(
                    "Something is wrong with the data set.");
        }
        // force a sub query to retrieve all the sub values over http
        HttpModel httpModel = (HttpModel) jenaModel;
        Query query = QueryFactory.create(
                String.format("SELECT * WHERE { <%s> ?predicate ?object . }", resource.getURI()));
        QueryExecution executioner = QueryExecutionFactory.sparqlService(httpModel.getServiceUrl(), query);
        ResultSet resultSet = executioner.execSelect();
        Node rdfSubject = resource.asNode();
        Dataset dataset = DatasetFactory.create();
        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.next();
            RDFNode rdfPrediate = solution.get("predicate");
            RDFNode rdfObject = solution.get("object");
            dataset.asDatasetGraph().getDefaultGraph().add(
                    Triple.create(rdfSubject, rdfPrediate.asNode(), rdfObject.asNode()));
        }
        super.resource = dataset.getDefaultModel().getRDFNode(rdfSubject).asResource();
        //super.jenaModel = dataset.getDefaultModel();
        isInited = true;
    }

}
