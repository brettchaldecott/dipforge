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
import com.rift.coad.audit.dao.LogEntryDAO;
import com.rift.coad.audit.dto.LogEntry;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ArrayList;

// log4j import
import org.apache.log4j.Logger;

// coaduantion imports
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil;
import com.rift.coad.rdf.semantic.SPARQLResultRow;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.types.network.Host;
import com.rift.coad.rdf.types.network.Service;

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
                    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/common/Network#Host> . " +
                    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/common/Network#Name> ?Hostname . } " +
                    "ORDER BY ?Hostname").execute();
            List<String> result = new ArrayList<String>();
            for (SPARQLResultRow entry : entries) {
                System.out.println("Looping through the results");
                result.add(entry.get(Host.class, 0).getName());
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
                    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/common/Network#Service> . " +
                    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/common/Network#Name> ?ServiceName. } " +
                    "ORDER BY ?ServiceName").execute();
            List<String> result = new ArrayList<String>();
            for (SPARQLResultRow entry : entries) {
                try {
                    result.add(entry.get(Service.class,0).getName());
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
            queryStr.append("SELECT ?s WHERE {").
                    append("?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/common/AuditTrail#LogEntry> . ").
                    append("?s <http://dipforge.sourceforge.net/schema/rdf/1.0/common/AuditTrail#CorrelationId> ?CorrelationId . ").
                    append("?s <http://dipforge.sourceforge.net/schema/rdf/1.0/common/AuditTrail#ExternalId> ?ExternalId . ").
                    append("?s <http://dipforge.sourceforge.net/schema/rdf/1.0/common/AuditTrail#Host> ?Host . ").
                    append("?Host <http://dipforge.sourceforge.net/schema/rdf/1.0/common/Network#Name> ?Hostname . ").
                    append("?s <http://dipforge.sourceforge.net/schema/rdf/1.0/common/AuditTrail#Status> ?Status . ").
                    append("?s <http://dipforge.sourceforge.net/schema/rdf/1.0/common/AuditTrail#Service> ?Service . ").
                    append("?Service <http://dipforge.sourceforge.net/schema/rdf/1.0/common/Network#Name> ?Source . ").
                    append("?s <http://dipforge.sourceforge.net/schema/rdf/1.0/common/AuditTrail#User> ?User .  ").
                    append("?User <http://dipforge.sourceforge.net/schema/rdf/1.0/common/Operation#Name> ?UserName .  ").
                    append("?s <http://dipforge.sourceforge.net/schema/rdf/1.0/common/AuditTrail#Time> ?Time . ");

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
                sparqlFilter.append(sep).append(" ?UserName = \"").
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
                LogEntryDAO dao = row.get(LogEntryDAO.class, 0);
                result.add(new LogEntry(dao.getId(), dao.getHost().getName(),dao.getService().getName(),
                        dao.getUser().getName(), dao.getTime(), dao.getStatus(),dao.getCorrelationId(),
                        dao.getExternalId(),dao.getRequest()));
            }
            return result;
        } catch (Exception ex) {
            log.error("Failed to perform the query : " + ex.getMessage(), ex);
            throw new AuditTrailException("Failed to perform the query : " + ex.getMessage(), ex);
        }
    }
}
