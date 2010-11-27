/*
 * AuditTrail: The audit trail server
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
 * AuditTrailServerImpl.java
 */
// the package path
package com.rift.coad.audit;

// java imports
import java.rmi.RemoteException;
import java.util.List;
import java.util.ArrayList;

// log4j import
import org.apache.log4j.Logger;

// coaduantion imports
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil;
import com.rift.coad.rdf.objmapping.audit.LogEntry;
import com.rift.coad.rdf.objmapping.inventory.Host;
import com.rift.coad.rdf.objmapping.service.SoftwareService;
import com.rift.coad.rdf.semantic.SPARQLResultRow;
import com.rift.coad.rdf.semantic.Session;

/**
 * The audit trail server.
 *
 * @author brett chaldecott
 */
public class AuditTrailServerImpl implements AuditTrailServer {

    // private member variables
    private static Logger log = Logger.getLogger(AuditTrailServerImpl.class);

    /**
     * The default constructor.
     */
    public AuditTrailServerImpl() {
    }

    /**
     * This method returns the list of hosts.
     *
     * @return The string containing the list of hosts.
     * @throws com.rift.coad.audit.AuditTrailException
     * @throws java.rmi.RemoteException
     */
    public List<String> listHosts() throws AuditTrailException {
        try {
            Session session = SemanticUtil.getInstance(AuditTrailLoggerImpl.class).getSession();
            List<SPARQLResultRow> entries = session.
                    createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/inventory#Host> . " +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/inventory#Hostname> ?Hostname . } " +
                    "ORDER BY ?Hostname").execute();
            List<String> result = new ArrayList<String>();
            for (SPARQLResultRow entry : entries) {
                System.out.println("Looping through the results");
                result.add(entry.get(0).cast(Host.class).getHostname());
            }
            return result;
        } catch (Exception ex) {
            log.error("Failed to retrieve the list of hosts : " + ex.getMessage(), ex);
            throw new AuditTrailException("Failed to retrieve the list of hosts : " + ex.getMessage(), ex);
        }
    }


    /**
     * This method returns the list of sources.
     *
     * @param host The string indicating the host to list sources for.
     * @return The list of host entries.
     * @throws com.rift.coad.audit.AuditTrailException
     * @throws java.rmi.RemoteException
     */
    public List<String> listSources(String host) throws AuditTrailException {
        try {
            Session session = SemanticUtil.getInstance(AuditTrailLoggerImpl.class).getSession();
            List<SPARQLResultRow> entries = session.
                    createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/service#SoftwareService> . " +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/service#SoftwareHostname> ?hostname . " +
                    "FILTER (?hostname = ${hostname}) } ").setString("hostname", host)
                    .execute();
            List<String> result = new ArrayList<String>();
            for (SPARQLResultRow entry : entries) {
                try {
                    result.add(entry.get(0).cast(SoftwareService.class).getName());
                } catch(Exception ex) {
                    log.error("Failed to find an entry : " + ex.getMessage(),ex);
                    // ignore
                }
            }
            return result;
        } catch (Exception ex) {
            log.error("Failed to retrieve the list of software : " + ex.getMessage(), ex);
            throw new AuditTrailException("Failed to retrieve the list of software : " + ex.getMessage(), ex);
        }
    }
    

    /**
     * This method returns the log entries.
     *
     * @param filter The filter information for the query.
     * @return Audit trail entries that match the filter.
     * @throws com.rift.coad.audit.AuditTrailException
     */
    public List<LogEntry> queryAuditTrail(AuditTrailFilter filter) throws AuditTrailException {
        try {
            StringBuffer queryStr = new StringBuffer();
            queryStr.append("SELECT ?s WHERE { ?s a <http://www.coadunation.net/schema/rdf/1.0/audit#LogEntry> . ").
                    append("?s <http://www.coadunation.net/schema/rdf/1.0/audit#CorrelationId> ?CorrelationId . ").
                    append("?s <http://www.coadunation.net/schema/rdf/1.0/audit#ExternalId> ?ExternalId . ").
                    append("?s <http://www.coadunation.net/schema/rdf/1.0/audit#Hostname> ?Hostname . ").
                    append("?s <http://www.coadunation.net/schema/rdf/1.0/audit#Status> ?Status . ").
                    append("?s <http://www.coadunation.net/schema/rdf/1.0/audit#Source> ?Source . ").
                    append("?s <http://www.coadunation.net/schema/rdf/1.0/audit#User> ?User .  ").
                    append("?s <http://www.coadunation.net/schema/rdf/1.0/audit#Time> ?Time . ");

            StringBuffer sparqlFilter = new StringBuffer();

            String sep = "";
            if (!filter.getCorrelationId().equals("")) {
                sparqlFilter.append(" ?CorrelationId = \"").
                        append(filter.getCorrelationId()).append("\" ");
                sep = "&&";
            }
            if (!filter.getExternalId().equals("")) {
                sparqlFilter.append(sep).append(" ?ExternalId = \"").
                        append(filter.getExternalId()).append("\" ");
                sep = "&&";
            }
            if (!filter.getHostname().equals("")) {
                sparqlFilter.append(sep).append(" ?Hostname = \"").
                        append(filter.getHostname()).append("\" ");
                sep = "&&";
            }
            if (!filter.getStatus().equals("")) {
                sparqlFilter.append(sep).append(" ?Status = \"").
                        append(filter.getStatus()).append("\" ");
                sep = "&&";
            }
            if (!filter.getSource().equals("")) {
                sparqlFilter.append(sep).append(" ?Source = \"").
                        append(filter.getSource()).append("\" ");
                sep = "&&";
            }
            if (!filter.getUser().equals("")) {
                sparqlFilter.append(sep).append(" ?User = \"").
                        append(filter.getUser()).append("\" ");
                sep = "&&";
            }
            if (sparqlFilter.length() > 0) {
                queryStr.append("FILTER (").append(sparqlFilter).append(")");
            }

            queryStr.append(" } ORDER BY DESC(?Time) ");
            if (filter.getMaxRows() > 0) {
                queryStr.append(" LIMIT ").append(filter.getMaxRows());
            } else {
                queryStr.append(" LIMIT 10");
            }

            System.out.println(queryStr.toString());

            List<SPARQLResultRow> entries = SemanticUtil.getInstance(AuditTrailLoggerImpl.class).getSession().
                    createSPARQLQuery(queryStr.toString()).execute();
            List<LogEntry> result = new ArrayList<LogEntry>();
            for (SPARQLResultRow row : entries) {
                System.out.println("Retrieve the log entry");
                result.add(row.get(0).cast(LogEntry.class));
            }
            return result;
        } catch (Exception ex) {
            log.error("Failed to perform the query : " + ex.getMessage(), ex);
            throw new AuditTrailException("Failed to perform the query : " + ex.getMessage(), ex);
        }
    }
}
