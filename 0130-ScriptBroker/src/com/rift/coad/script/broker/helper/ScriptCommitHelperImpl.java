/*
 * ScriptBroker: The script broker daemon.
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
 * ScriptRevisionManagerDaemonImpl.java
 */


// package path
package com.rift.coad.script.broker.helper;

// java import
import java.io.File;

// log object
import org.apache.log4j.Logger;


// coadunation imports
import com.rift.coad.script.broker.ScriptBrokerException;
import com.rift.coad.script.broker.rdf.RDFScriptChangeInfo;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.revision.RevisionManagerDaemon;
import com.rift.coad.revision.rdf.RDFRevisionInfo;
import com.rift.coad.script.broker.Constants;
import com.rift.coad.script.broker.ScriptManagerDaemonImpl;
import com.rift.coad.script.broker.ScriptRevisionManagerDaemonImpl;
import com.rift.coad.script.broker.rdf.ScriptActions;
import com.rift.coad.util.connection.ConnectionManager;


/**
 * This object is responsible for committing the helper changes.
 *
 * @author brett chaldecott
 */
public class ScriptCommitHelperImpl implements ScriptCommitHelper {

    // log object reference
    private static Logger log = Logger.getLogger(ScriptCommitHelperImpl.class);

    // private member variables
    private String scriptBase;
    private String repository;
    private String branch;


    /**
     * The default constructor.
     */
    public ScriptCommitHelperImpl() throws ScriptBrokerException {
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    ScriptManagerDaemonImpl.class);
            scriptBase = config.getString(Constants.SCRIPT_BASE);
            repository = config.getString(Constants.SCRIPT_REPOSITORY);
            branch = config.getString(Constants.SCRIPT_BRANCH);

        } catch (Exception ex) {
            log.error("Failed to instanciate the revision manager daemon : " +
                    ex.getMessage(),ex);
            throw new ScriptBrokerException
                    ("Failed to instanciate the revision manager daemon : " +
                    ex.getMessage(),ex);
        }
    }



    /**
     * This method is called to commit the changes.
     *
     * @param changeInfo The changes to commit.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     */
    public RDFRevisionInfo commitChange(RDFScriptChangeInfo changeInfo) throws ScriptBrokerException {
        try {
            RevisionManagerDaemon daemon = (RevisionManagerDaemon)
                    ConnectionManager.getInstance().getConnection(
                    RevisionManagerDaemon.class, "revision/RevisionManagerDaemon");
            String path = changeInfo.getScript().getFileName();
            String repositoryPath = repository + File.separator + branch + File.separator + path;
            if (changeInfo.getChange().equals(ScriptActions.ADD)) {
                return daemon.addEntry(repository, branch,
                        repositoryPath, readFile(path));
            } else if (changeInfo.getChange().equals(ScriptActions.UPDATE)) {
                return daemon.updateEntry(repository, branch,
                        repositoryPath, readFile(path));
            } else {
                daemon.deleteEntry(repository, branch,
                        repositoryPath);
                return null;
            }
        } catch (ScriptBrokerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to commit the change : " + ex.getMessage(),ex);
            throw new ScriptBrokerException
                    ("Failed to commit the change : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is called to read in the file.
     *
     * @param path The path to the file.
     * @return The string containing the contents of the file.
     * @throws ScriptBrokerException
     */
    private String readFile(String path) throws ScriptBrokerException {
        try {
            File file = new File(this.scriptBase,path);
            java.io.FileInputStream in = new java.io.FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            in.read(bytes);
            in.close();
            return new String(bytes);
        } catch (java.io.FileNotFoundException ex) {
            log.error("The file [" + path + "] was not found : " + ex.getMessage(),ex);
            throw new ScriptBrokerException
                    ("The file [" + path + "] was not found : " + ex.getMessage(),ex);
        } catch (Exception ex) {
            log.error("Failed to read the file : " + ex.getMessage(),ex);
            throw new ScriptBrokerException
                    ("Failed to read the file : " + ex.getMessage(),ex);
        }
    }
}
