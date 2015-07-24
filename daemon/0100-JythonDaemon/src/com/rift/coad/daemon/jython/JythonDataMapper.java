/*
 * GroovyDaemonClient: The client libraries for the groovy data mapper.
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
 * JythonDataMapper.java
 */

// package path
package com.rift.coad.daemon.jython;

// java imports
import java.rmi.RemoteException;

// log4j imports
import org.apache.log4j.Logger;

// coadunation import
import com.rift.coad.datamapper.DataMapper;
import com.rift.coad.datamapper.DataMapperException;
import com.rift.coad.lib.configuration.ConfigurationException;
import com.rift.coad.rdf.objmapping.base.DataType;
import com.rift.coad.rdf.semantic.RDFFormats;
import com.rift.coad.rdf.semantic.SPARQLResultRow;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.config.Basic;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import org.python.util.PythonInterpreter;

/**
 * This object handles the mapping of calls onto the daemon.
 *
 * @author brett chaldecott
 */
public class JythonDataMapper implements DataMapper {


    // private import
    private static Logger log = Logger.getLogger(JythonDataMapper.class);

    // the script location
    private String scriptLocal = "";
    private String tmpLocation = "";


    /**
     * The default constructor
     */
    public JythonDataMapper() throws JythonDaemonException {
        try {
            com.rift.coad.lib.configuration.Configuration coadConfig =
                    com.rift.coad.lib.configuration.ConfigurationFactory.
                    getInstance().getConfig(com.rift.coad.daemon.jython.
                    JythonDaemonImpl.class);
            scriptLocal = coadConfig.getString("script_location");
            tmpLocation = coadConfig.getString("coadunation_temp");
        } catch (ConfigurationException ex) {
            log.error("Failed to set jython properties :" +ex.getMessage(),ex);
            throw new JythonDaemonException(
                    "Failed to set jython properties :" + ex.getMessage(),ex);
        }
    }


    /**
     * This method executes the given method providing the parameters.
     *
     * @param serviceId The service id for the call
     * @param method The method that is being invoked.
     * @param parameters The parameters containing the data to execute this call.
     * @return The result of the call.
     * @throws com.rift.coad.datamapper.DataMapperException
     */
    public DataType execute(String serviceId, String method, DataType[] parameters) throws DataMapperException {
        try {
            File scriptFile = new File(scriptLocal, method);
            FileInputStream fis = new FileInputStream(scriptFile);
            PythonInterpreter inter = new PythonInterpreter();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            inter.setOut(output);
            inter.setErr(output);
            for (DataType parameter : parameters) {
                Session session = Basic.initSessionManager().getSession();
                session.persist(parameter);
                inter.set(parameter.getDataName(),session.dump(RDFFormats.XML_ABBREV));
            }
            inter.execfile(fis);
            Session session = Basic.initSessionManager().getSession();
            session.persist(output.toString());
            List<SPARQLResultRow> entries = session.
                    createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/base#IdForDataType> ?typeId . }")
                    .execute();
            for (SPARQLResultRow row : entries) {
                try {
                    return row.get(0).cast(DataType.class);
                } catch (Throwable ex) {
                    log.debug("Failed to cast the entry because : " + ex.getMessage(),ex);
                }
            }
            return null;
        } catch (Exception ex) {
            log.error("Failed to execute the script : " + ex.getMessage(),ex);
            throw new DataMapperException
                ("Failed to execute the script : " + ex.getMessage(),ex);
        }
    }

}
