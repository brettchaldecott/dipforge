/*
 * JythonDaemon: The jython daemon
 * Copyright (C) 2009  2015 Burntjam
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
 * JythonMappingManagerDaemonImpl.java
 */

// package path
package com.rift.coad.daemon.jython;

// java imports
import java.util.List;

// log4j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.datamapperbroker.rdf.DataMapperMethod;
import com.rift.coad.rdf.semantic.RDFFormats;
import com.rift.coad.rdf.semantic.SPARQLResultRow;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.config.Basic;
import com.rift.coad.util.connection.ConnectionManager;

/**
 * This object is the implementation of the coadunation management object.
 *
 * @author brett chaldecott
 */
public class JythonMappingManager implements JythonMappingManagerMBean {

    // private member variables
    private static Logger log = Logger.getLogger(JythonMappingManager.class);

    /**
     * The default constructor for the jython mapping manager.
     */
    public JythonMappingManager() {
    }



    /**
     * This method returns the version information for the jython mapping manager.
     *
     * @return The string containing the version information.
     */
    public String getVersion() {
       return "1.0";
    }


    /**
     * This method returns the name of the
     *
     * @return The name of this object.
     */
    public String getName() {
        return this.getClass().getName();
    }


    /**
     * This method returns a description of the groovy mapping manager.
     *
     * @return The string containing the description of the mapping manager.
     */
    public String getDescription() {
        return "Jython Mapping Manager";
    }


    /**
     * This method is used to export the data list of data mappings for this manager.
     *
     * @return The string containing the exported data mappings.
     * @throws com.rift.coad.jython.GroovyDaemonException
     */
    public String exportDataMappingsInXML() throws JythonDaemonException {
        try {
            JythonMappingManagerDaemon server = (JythonMappingManagerDaemon)ConnectionManager.getInstance().
                    getConnection(JythonMappingManagerDaemon.class, "java:comp/env/bean/jython/MappingManagerDaemon");
            List<DataMapperMethod> methods = server.listMethods();
            Session session2 = Basic.initSessionManager().getSession();
            for (int index = 0; index < methods.size(); index++) {
                DataMapperMethod method = methods.get(index);
                session2.persist(method);
            }
            return session2.dump(RDFFormats.XML_ABBREV);
        } catch (JythonDaemonException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to export the data mappings in xml : " + ex.getMessage(),ex);
            throw new JythonDaemonException
                    ("Failed to export the data mappings in xml : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is called to import the data mappings
     *
     * @param xml The xml containing the data mappings to remove
     * @throws com.rift.coad.jython.GroovyDaemonException
     */
    public void importDataMappingFromXML(String xml) throws JythonDaemonException {
        try {
            JythonMappingManagerDaemon server = (JythonMappingManagerDaemon)ConnectionManager.getInstance().
                    getConnection(JythonMappingManagerDaemon.class, "java:comp/env/bean/jython/MappingManagerDaemon");
            Session session = Basic.initSessionManager().getSession();
            session.persist(xml);
            List<SPARQLResultRow> entries = session.
                    createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/datamapper#DataMapperMethod> .}")
                    .execute();
            for (SPARQLResultRow row : entries) {
                DataMapperMethod newMethod = row.get(0).cast(DataMapperMethod.class);
                server.addMethod(newMethod);
            }
        } catch (Exception ex) {
            log.error("Failed to import the data mappings from xml : " + ex.getMessage(),ex);
            throw new JythonDaemonException
                    ("Failed to import the data mappings from xml : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is called to delete the specified data mapping.
     *
     * @param name The string containing the data mapping.
     * @throws com.rift.coad.daemon.jython.GroovyDaemonException
     */
    public void deleteDataMapping(String name) throws JythonDaemonException {
        try {
            JythonMappingManagerDaemon server = (JythonMappingManagerDaemon)ConnectionManager.getInstance().
                    getConnection(JythonMappingManagerDaemon.class, "java:comp/env/bean/jython/MappingManagerDaemon");
            List<DataMapperMethod> methods = server.listMethods();
            Session session2 = Basic.initSessionManager().getSession();
            for (int index = 0; index < methods.size(); index++) {
                DataMapperMethod method = methods.get(index);
                if (method.getName().equals(name)) {
                    server.removeMethod(method);
                    return;
                }
            }
        } catch (JythonDaemonException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to remove the mapping : " + ex.getMessage(),ex);
            throw new JythonDaemonException
                    ("Failed to remove the mapping : " + ex.getMessage(),ex);
        }
    }

    
}
