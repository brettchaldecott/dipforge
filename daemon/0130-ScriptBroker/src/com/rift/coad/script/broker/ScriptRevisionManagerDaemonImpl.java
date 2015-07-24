/*
 * ScriptBroker: The script broker daemon.
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
 * ScriptRevisionManagerDaemonImpl.java
 */


// package path
package com.rift.coad.script.broker;

// java imports
import com.rift.coad.daemon.messageservice.AsyncCallbackHandler;
import com.rift.coad.daemon.messageservice.rpc.RPCMessageClient;
import com.rift.coad.rdf.semantic.Resource;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil;
import com.rift.coad.script.broker.rdf.RDFScriptChangeSet;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

// log4j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.script.broker.rdf.RDFScriptInfo;
import com.rift.coad.revision.rdf.RDFRevisionInfo;
import com.rift.coad.script.broker.rdf.RDFScriptChangeInfo;
import com.rift.coad.script.broker.rdf.ScriptActions;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.rdf.semantic.SPARQLResultRow;
import com.rift.coad.revision.RevisionManagerDaemon;
import com.rift.coad.script.broker.helper.ScriptCommitHelper;
import com.rift.coad.script.broker.helper.ScriptCommitHelperAsync;
import com.rift.coad.util.connection.ConnectionManager;
import java.io.File;

/**
 * This object implements the revision manager.
 *
 * @author brett
 */
public class ScriptRevisionManagerDaemonImpl implements ScriptRevisionManagerDaemon, AsyncCallbackHandler {

    // class constants
    private final static String OPEN_CHANGE_SET_ID = "open_change_set";


    // static member variables
    private static Logger log = Logger.getLogger(ScriptRevisionManagerDaemonImpl.class);


    // private member variables
    private String scriptBase;
    private String repository;
    private String branch;


    /**
     * The constructor for the script revision manager daemon.
     */
    public ScriptRevisionManagerDaemonImpl() throws ScriptBrokerException {
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
     * This method returns the revision information for the specified script that is managed
     * by this revision manager.
     *
     * @param script The script to retrieve the revision information for.
     * @return This reference to the revision information.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     */
    public RDFRevisionInfo getRevision(RDFScriptInfo script) throws ScriptBrokerException, UnknownEntryException {
        try {
            Session session = SemanticUtil.getInstance(ScriptManagerDaemonImpl.class).getSession();
            Resource resource = session.getResource(RDFScriptInfo.class, script, script.getObjId());
            List<Resource> resources = resource.listProperties(Constants.REVISION);
            if (resources.size() != 1) {
                log.error("No revision exists for this file.");
                throw new UnknownEntryException(
                        "No revision exists for this file.");
            }
            return resources.get(0).get(RDFRevisionInfo.class);
        } catch (UnknownEntryException ex) {
            throw ex;
        } catch (com.rift.coad.rdf.semantic.session.UnknownEntryException ex) {
            log.error("Failed to retrieve the revision information : " + ex.getMessage(),ex);
            throw new UnknownEntryException
                    ("Failed to retrieve the revision information : " + ex.getMessage(),ex);
        } catch (Exception ex) {
            log.error("Failed to retrieve the revision information : " + ex.getMessage(),ex);
            throw new ScriptBrokerException
                    ("Failed to retrieve the revision information : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method sets the revision information for the specified script.
     *
     * @param script The script containing the revision information.
     * @param revision The revision information
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     */
    public void setRevision(RDFScriptInfo script, RDFRevisionInfo revision) throws ScriptBrokerException {
        try {
            Session session = SemanticUtil.getInstance(ScriptManagerDaemonImpl.class).getSession();
            try {
                Resource resource = session.getResource(RDFScriptInfo.class, script, script.getObjId());
                List<Resource> resources = resource.listProperties(Constants.REVISION);
                for (Resource entry : resources) {
                    resource.removeProperty(Constants.REVISION, entry);
                }
                resource.addProperty(Constants.REVISION, revision);
            } catch (com.rift.coad.rdf.semantic.session.UnknownEntryException ex) {
                Resource resource = session.persist(script);
                resource.addProperty(Constants.REVISION, revision);
            }
        } catch (Exception ex) {
            log.error("Failed to set the revision information : " + ex.getMessage(),ex);
            throw new ScriptBrokerException
                    ("Failed to set the revision information : " + ex.getMessage(),ex);
        }
    }


    /**
     *
     * @param path
     * @param fileName
     * @return
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     */
    public List<RDFScriptInfo> listScriptRevisions(String path, String fileName) throws
            ScriptBrokerException {
        try {
            RevisionManagerDaemon daemon = (RevisionManagerDaemon)
                    ConnectionManager.getInstance().getConnection(
                    RevisionManagerDaemon.class, "revision/RevisionManagerDaemon");
            String repositoryPath = repository + File.separator + branch + File.separator + path
                    + File.separator + fileName;
            List<RDFRevisionInfo> revisions = daemon.listRevisions(repository, branch, repositoryPath);
            List<RDFScriptInfo> result = new ArrayList<RDFScriptInfo>();
            for (RDFRevisionInfo revision : revisions) {
                result.add(new RDFScriptInfo(path.replace("["+File.separator+"]", "."),
                        fileName, com.rift.coad.revision.rdf.RepositoryTypes.FILE,
                        null, revision.getVersion()));
            }
            return result;
        } catch (Exception ex) {
            log.error("Failed to list the revisions for the path and file : " +
                    ex.getMessage(),ex);
            throw new ScriptBrokerException("Failed to list the revisions for the path and file : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method returns a list of changes.
     *
     * @return This list of changes.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     */
    public List<RDFScriptChangeInfo> listLocalChanges() throws ScriptBrokerException {
        try {
            Session session = SemanticUtil.getInstance(ScriptManagerDaemonImpl.class).getSession();
            try {
                Resource resource = session.getResource(RDFScriptInfo.class,
                        new RDFScriptChangeSet(), OPEN_CHANGE_SET_ID);
                List<Resource> resources = resource.listProperties(Constants.CHANGE_REF);
                List<RDFScriptChangeInfo> result = new ArrayList<RDFScriptChangeInfo>();
                for (Resource entry : resources) {
                    result.add(entry.get(RDFScriptChangeInfo.class));
                }
                return result;
            } catch (com.rift.coad.rdf.semantic.session.UnknownEntryException ex) {
                return new ArrayList<RDFScriptChangeInfo>();
            }
        } catch (Exception ex) {
            log.error("Failed to retrieve the list of open changes : " + ex.getMessage(),ex);
            throw new ScriptBrokerException(
                    "Failed to retrieve the list of open changes : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method adds a change to the script revision manager.
     *
     * @param change The change to register with the script revision manager.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     */
    public RDFScriptChangeInfo addChange(String action, RDFScriptInfo change) throws ScriptBrokerException {
        try {
            Session session = SemanticUtil.getInstance(ScriptManagerDaemonImpl.class).getSession();
            RDFScriptChangeInfo result = new RDFScriptChangeInfo(change,action);
            try {
                Resource resource = session.getResource(RDFScriptInfo.class,
                        new RDFScriptChangeSet(), OPEN_CHANGE_SET_ID);
                // check for existing change
                for (Resource propertyResource : resource.listProperties(Constants.CHANGE_REF)) {
                    RDFScriptChangeInfo currentProperty = propertyResource.get(RDFScriptChangeInfo.class);
                    if (!change.equals(currentProperty.getScript())) {
                        continue;
                    }
                    if (currentProperty.getChange().equals(ScriptActions.ADD) && action.equals(ScriptActions.REMOVE)) {
                        session.remove(currentProperty);
                        return result;
                    } else if (currentProperty.getChange().equals(ScriptActions.REMOVE) && action.equals(ScriptActions.ADD)) {
                        session.remove(currentProperty);
                        result.setChange(ScriptActions.UPDATE);
                        break;
                    } else if (currentProperty.getChange().equals(ScriptActions.UPDATE) && action.equals(ScriptActions.REMOVE)) {
                        session.remove(currentProperty);
                        break;
                    }
                }

                resource.addProperty(Constants.CHANGE_REF, result);
            } catch (com.rift.coad.rdf.semantic.session.UnknownEntryException ex) {
                Resource resource = session.persist(new RDFScriptChangeSet(OPEN_CHANGE_SET_ID,
                        "OpenChangeSet", "Open Change Set", new Date()));
                resource.addProperty(Constants.CHANGE_REF, result);
            }
            return result;
        } catch (Exception ex) {
            log.error("Failed to add the change : " + ex.getMessage(),ex);
            throw new ScriptBrokerException(
                    "Failed to add the change : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method commits the changes.
     *
     * @param changes The list of changes.
     * @param message The list of changes.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     */
    public RDFScriptChangeSet commitChanges(List<RDFScriptChangeInfo> changes, String message) throws ScriptBrokerException {
        try {
            Session session = SemanticUtil.getInstance(ScriptManagerDaemonImpl.class).getSession();
            Resource resource = session.getResource(RDFScriptInfo.class,
                    new RDFScriptChangeSet(), OPEN_CHANGE_SET_ID);
            List<Resource> changeList = resource.listProperties(Constants.CHANGE_REF);
            List<RDFScriptChangeInfo> changeSetList = new ArrayList<RDFScriptChangeInfo>();
            // retrieve the current list of changes attached to the open transaction
            for (Resource entry : changeList) {
                changeSetList.add(entry.get(RDFScriptChangeInfo.class));
            }

            // loop through the list of changes
            RDFScriptChangeSet result = new RDFScriptChangeSet(new Date().toString(), message);
            Resource changeSetResource = session.persist(result);
            for (RDFScriptChangeInfo change : changes) {
                // make the
                int index = changeSetList.indexOf(change);
                if (index == -1) {
                    log.info("The change required in the change set was not found : " + change.toString());
                    throw new ScriptBrokerException
                            ("The change required in the change set was not found : " + change.toString());
                }
                // check on the validity of the change
                File file = new File(this.scriptBase,change.getScript().getFileName());
                if (change.getChange().equals(ScriptActions.ADD) ||
                        change.getChange().equals(ScriptActions.UPDATE)) {
                    if (!file.exists()) {
                        log.error("Attempting to commit a file that does not exist : " + file.getName());
                        throw new ScriptBrokerException
                                ("Attempting to commit a file that does not exist : " + file.getName());
                    }
                } else if (change.getChange().equals(ScriptActions.REMOVE)){
                    if (file.exists()) {
                        log.error("Attempting to commit a removal of a file that exists : " + file.getName());
                        throw new ScriptBrokerException
                                ("Attempting to commit a removal of a file that exists : " + file.getName());
                    }
                }

                // commit the changes
                ScriptCommitHelperAsync helper = (ScriptCommitHelperAsync)RPCMessageClient.create("script/RevisionManagementDaemon",
                        ScriptCommitHelper.class, ScriptCommitHelperAsync.class, "script/helper/CommitHelper");
                String messageId = helper.commitChange(change);

                // persist the message id to the change.
                change.setMessageId(messageId);
                session.persist(change);

                // retrieve the resource for the specified entry.
                Resource changeSetEntry = changeList.get(index);
                resource.removeProperty(Constants.CHANGE_REF, changeSetEntry);
                changeSetResource.addProperty(Constants.CHANGE_REF, changeSetEntry);
            }
            return result;
        } catch (ScriptBrokerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed commit the changes : " + ex.getMessage(),ex);
            throw new ScriptBrokerException(
                    "Failed commit the changes : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns a list of changes sets.
     *
     * @return The list of change sets.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     */
    public List<RDFScriptChangeSet> listChangeSets() throws ScriptBrokerException {
        try {
            Session session = SemanticUtil.getInstance(ScriptManagerDaemonImpl.class).getSession();
            List<SPARQLResultRow> entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/script#ScriptChangeSet> . " +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/script#ChangeSetCreatedDate> ?ChangeSetCreatedDate . } " +
                    "ORDER BY ?ChangeSetCreatedDate").execute();

            List<RDFScriptChangeSet> result = new ArrayList<RDFScriptChangeSet>();
            for (SPARQLResultRow entry : entries) {
                result.add(entry.get(0).cast(RDFScriptChangeSet.class));
            }
            return result;
        } catch (Exception ex) {
            log.error("Failed to retrieve the list of change sets : " + ex.getMessage(),ex);
            throw new ScriptBrokerException
                    ("Failed to retrieve the list of change sets : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is called to deal with successfull processed commits.
     *
     * @param messageId The message id
     * @param correllationId The correllation id.
     * @param result The result object.
     * @throws java.rmi.RemoteException
     */
    public void onSuccess(String messageId, String correllationId,
            Object result) throws RemoteException {
        try {
            Session session = SemanticUtil.getInstance(ScriptManagerDaemonImpl.class).getSession();
            List<SPARQLResultRow> entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/script#ScriptChangeInfo> . " +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/script#ScriptMessageId> ?MessageId . " +
                    "FILTER (?MessageId = ${MessageId}) }} ").setString("MessageId", messageId).execute();
            if (entries.size() != 1) {
                log.error("Failed to set the revision information associated with the change : " + messageId);
                return;
            }
            RDFScriptChangeInfo changeInfo = entries.get(0).get(0).cast(RDFScriptChangeInfo.class);
            changeInfo.setStatus(com.rift.coad.script.broker.rdf.Constants.STATUS_SUCCESFUL);
            RDFRevisionInfo revision = (RDFRevisionInfo)result;
            changeInfo.setRevision(revision);
            changeInfo.getScript().setVersion(revision.getVersion());
            session.persist(changeInfo);
        } catch (Exception ex) {
            log.error("Failed to handle the success call for [" + messageId
                    + "] because : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is called to deal with the failures.
     *
     * @param messageId The id of the message.
     * @param arg1
     * @param arg2
     * @throws java.rmi.RemoteException
     */
    public void onFailure(String messageId, String correllationId, Throwable cause) throws RemoteException {
        try {
            Session session = SemanticUtil.getInstance(ScriptManagerDaemonImpl.class).getSession();
            List<SPARQLResultRow> entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/script#ScriptChangeInfo> . " +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/script#ScriptMessageId> ?MessageId . " +
                    "FILTER (?MessageId = ${MessageId}) }} ").setString("MessageId", messageId).execute();
            if (entries.size() != 1) {
                log.error("Failed to set the revision information associated with the change : " + messageId);
                return;
            }
            RDFScriptChangeInfo changeInfo = entries.get(0).get(0).cast(RDFScriptChangeInfo.class);
            changeInfo.setStatus(com.rift.coad.script.broker.rdf.Constants.STATUS_FAILURE
                    + " : "+ cause.getMessage());
            session.persist(changeInfo);
        } catch (Exception ex) {
            log.error("Failed to handle the failure call for [" + messageId
                    + "] because : " + ex.getMessage(),ex);
        }
    }



}
