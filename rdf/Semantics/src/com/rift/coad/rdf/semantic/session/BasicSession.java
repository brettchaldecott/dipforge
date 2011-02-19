/*
 * CoaduntionSemantics: The semantic library for coadunation os
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
 * BasicSession.java
 */

// the package path
package com.rift.coad.rdf.semantic.session;

// java imports
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.annotation.Annotation;
import java.util.Collection;

// log4j imports
import org.apache.log4j.Logger;

// jena bean imports
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
//import thewebsemantic.Bean2RDF;
//import thewebsemantic.RDF2Bean;
//import thewebsemantic.Sparql;
import static com.hp.hpl.jena.graph.Node.ANY;
import static com.hp.hpl.jena.graph.Node.createURI;

// coadunation imports
import com.rift.coad.rdf.semantic.basic.*;
import com.rift.coad.rdf.semantic.Query;
import com.rift.coad.rdf.semantic.SPARQLQuery;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.SessionException;
import com.rift.coad.rdf.semantic.Transaction;
import com.rift.coad.rdf.semantic.annotation.helpers.NamespaceHelper;
import com.rift.coad.rdf.semantic.annotation.helpers.LocalNameHelper;
import com.rift.coad.rdf.semantic.query.BasicQuery;
import com.rift.coad.rdf.semantic.query.BasicSPARQLQuery;
import com.rift.coad.rdf.semantic.resource.BasicResource;

/**
 * This object represents a basic session.
 *
 * @author brett chaldecott
 */
public class BasicSession implements Session {
    // class singletons
    private static Logger log = Logger.getLogger(BasicSession.class);

    public Transaction getTransaction() throws SessionException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void persist(InputStream in) throws SessionException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void persist(String rdf) throws SessionException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <T> T persist(T object) throws SessionException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String dumpXML() throws SessionException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void dumpXML(OutputStream out) throws SessionException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <T> T remove(T target) throws SessionException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void remove(String rdf) throws SessionException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <T> T get(Class<T> c, Serializable identifier) throws SessionException, UnknownEntryException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Query createQuery(String queryString) throws SessionException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SPARQLQuery createSPARQLQuery(String queryString) throws SessionException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

   
    
}
