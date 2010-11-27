/*
 * GroovyDaemonClient: The client libraries for the groovy data mapper.
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
 * GroovyManager.java
 */

package com.rift.coad.groovy;

// java imports
import com.rift.coad.rdf.objmapping.base.DataType;
import com.rift.coad.rdf.objmapping.resource.ResourceBase;
import com.rift.coad.rdf.semantic.RDFFormats;
import com.rift.coad.rdf.semantic.SPARQLResultRow;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.config.Basic;
import java.rmi.RemoteException;
import java.util.List;

// log 4j imports
import org.apache.log4j.Logger;

// coadunation import
import com.rift.coad.util.connection.ConnectionManager;
import java.util.ArrayList;


/**
 * This object is responsible for managing the groovy environment.
 *
 * @author brett chaldecott
 */
public class GroovyManager implements GroovyManagerMBean {


    // private member variables
    private static Logger log = Logger.getLogger(GroovyManager.class);

    /**
     * The default constructor
     */
    public GroovyManager() {
    }


    /**
     * This method returns the version information.
     *
     * @return The string containing the version information.
     * @throws java.rmi.RemoteException
     */
    public String getVersion() {
        return "1.0";
    }


    /**
     * This method returns the name of the groovy manager.
     *
     * @return The string containing the name of the groovy manager.
     */
    public String getName() {
        return this.getClass().getName();
    }


    /**
     * This method returns the description of the groovy manager.
     *
     * @return The string containing the description of the groovy manager.
     */
    public String getDescription() {
        return "The groovy manager.";
    }


    /**
     * This method returns a list of all the scripts.
     *
     * @return The list of all the groovy scripts.
     * @throws com.rift.coad.groovy.GroovyDaemonException
     */
    public List<String> listScripts() throws GroovyDaemonException {
        try {
            GroovyDaemon server = (GroovyDaemon)ConnectionManager.getInstance().
                    getConnection(GroovyDaemon.class, "java:comp/env/bean/groovy/Daemon");
            return server.listScripts();
        } catch (GroovyDaemonException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to retrieve the list of scripts : " + ex.getMessage(),ex);
            throw new GroovyDaemonException
                ("Failed to retrieve the list of scripts : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is responsible for executing the groovy manager.
     *
     * @param scriptPath The path to the script.
     * @return The string containing the result.
     * @throws com.rift.coad.groovy.GroovyDaemonException
     */
    public String execute(String scriptPath) throws GroovyDaemonException {
        try {
            GroovyDaemon server = (GroovyDaemon)ConnectionManager.getInstance().
                    getConnection(GroovyDaemon.class, "java:comp/env/bean/groovy/Daemon");
            return server.execute(scriptPath);
        } catch (GroovyDaemonException ex) {
            throw new GroovyDaemonException(ex.getMessage());
        } catch (Exception ex) {
            log.error("Failed to execute the script : " + ex.getMessage(),ex);
            throw new GroovyDaemonException
                ("Failed to execute the script : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method executes the script path and xml.
     *
     * @param scriptPath The path to the script.
     * @param xmlParameters The xml parameters.
     * @return The result of the execution.
     * @throws com.rift.coad.groovy.GroovyDaemonException
     */
    public String execute(String scriptPath, String xmlParameters) throws GroovyDaemonException {
        try {
            Session session = Basic.initSessionManager().getSession();
            session.persist(xmlParameters);
            List<SPARQLResultRow> entries = session.
                    createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/base#IdForDataType> ?typeId . }")
                    .execute();
            List<DataType> parameters = new ArrayList<DataType>();
            for (SPARQLResultRow row : entries) {
                try {
                    parameters.add(row.get(0).cast(DataType.class));
                } catch (Throwable ex) {
                    log.debug("Failed to cast the entry because : " + ex.getMessage(),ex);
                }
            }
            GroovyDaemon server = (GroovyDaemon)ConnectionManager.getInstance().
                    getConnection(GroovyDaemon.class, "java:comp/env/bean/groovy/Daemon");
            Session session2 = Basic.initSessionManager().getSession();
            session2.persist(
                    server.execute(scriptPath, parameters.toArray(new DataType[0])));
            return session2.dump(RDFFormats.XML_ABBREV);
        } catch (GroovyDaemonException ex) {
            throw new GroovyDaemonException(ex.getMessage());
        } catch (Exception ex) {
            log.error("Failed to execute the script : " + ex.getMessage(),ex);
            throw new GroovyDaemonException
                ("Failed to execute the script : " + ex.getMessage(),ex);
        }
    }

}
