/*
 * RevisionManager: The revision manager client library
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
 * RevisionManagerDaemon.java
 */

// the package path
package com.rift.coad.revision;

// java imports
import com.rift.coad.revision.rdf.RepositoryTypes;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

// java imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;

// rdf imports
import com.rift.coad.rdf.semantic.Resource;
import com.rift.coad.rdf.semantic.SPARQLResultRow;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil;
import com.rift.coad.revision.rdf.RDFRevisionInfo;
import com.rift.coad.revision.rdf.RDFTag;
import java.util.Date;

/**
 * The implementation of the revision manager daemon.
 *
 * @author brett chaldecott
 */
public class RevisionManagerDaemonImpl implements RevisionManagerDaemon {

    // class constants
    private final static String REPOSITORY_BASE_PATH = "repository_base_path";
    private final static String REPOSITORY_FILE = ".repository_file";
    private final static String VERSION_SECTION = ".version_";
    private final static String REVISION_REF = "http://www.coadunation.net/schema/rdf/1.0/revision#RevisionRef";
    // the reference to the log path.
    private static Logger log = Logger.getLogger(RevisionManagerDaemonImpl.class);

    // private member variables
    private String path;

    /**
     * The constructor of the revision manager daemon.
     */
    public RevisionManagerDaemonImpl() throws RevisionManagerException {
        try {
            Configuration conf = ConfigurationFactory.getInstance().getConfig(
                    RevisionManagerDaemonImpl.class);
            path = conf.getString(REPOSITORY_BASE_PATH);
            File basePath = new File(path);
            if (!basePath.exists()) {
                basePath.mkdirs();
            } else if (!basePath.isDirectory()) {
                log.error("The base path [" + path + "] is not a directory");
                throw new RevisionManagerException(
                        "The base path [" + path + "] is not a directory");
            }
        } catch (RevisionManagerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to instantiate the revision manager : " + ex.getMessage(), ex);
            throw new RevisionManagerException("Failed to instantiate the revision manager : " + ex.getMessage(), ex);
        }
    }

    
    /**
     * This method creates the repository.
     *
     * @param name The name of the repository to create
     * @throws com.rift.coad.revision.RevisionManagerException
     */
    public void createRepository(String name) throws RevisionManagerException {
        try {
            File repositoryDir = new File(path + File.separator + name);
            if (repositoryDir.exists()) {
                log.error("The repository [" + name + "] already exists");
                throw new RevisionManagerException("The repository [" + name + "] already exists");
            }
            repositoryDir.mkdirs();
        } catch (RevisionManagerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to create the directories : " + ex.getMessage(), ex);
            throw new RevisionManagerException("Failed to create the directories : " + ex.getMessage(), ex);
        }
    }

    /**
     * The list of revisions scoped by the repository, branch and path.
     *
     * @param repository The respository to looking.
     * @param branch The branch within the repository to look in.
     * @param path The path.
     * @return The list of revisions.
     * @throws com.rift.coad.revision.RevisionManagerException
     */
    public List<RDFRevisionInfo> listRevisions(String repository, String branch, String path)
            throws RevisionManagerException {
        try {
            Session session = SemanticUtil.getInstance(RevisionManagerDaemonImpl.class).getSession();

            File repositoryDirectory = new File(generatePath(repository, branch, path));
            if (!repositoryDirectory.exists()) {
                log.info("The requested repository entry does not exist [" +
                        repository + "][" + branch + "][" + path + "]");

            }
            List<RDFRevisionInfo> result = new ArrayList<RDFRevisionInfo>();
            File[] files = repositoryDirectory.listFiles();
            if (files == null) {
                log.error("Failed to retrieve a list of files for [" +
                        repositoryDirectory.getPath() + "]");
                throw new RevisionManagerException(
                        "Failed to retrieve a list of files for [" +
                        repositoryDirectory.getPath() + "]");
            }
            for (File file : files) {
                if (file.getName().equals(".") || file.getName().equals("..")) {
                    continue;
                } else if (!file.isDirectory()) {
                    result.add(getRevision(session, repository, branch, path,
                            getFromFileNameVersion(file.getName())));
                } else if (file.getName().contains(REPOSITORY_FILE)) {
                    result.add(new RDFRevisionInfo(
                            path + File.separator + stripFileName(file.getName()),
                            RepositoryTypes.FILE,
                            repository, branch, null, null, null));
                } else {
                    result.add(getRevision(session, repository, branch, path + File.separator +
                            file.getName() + File.separator, "1"));
                }
            }
            return result;
        } catch (RevisionManagerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("This method returns a list of revisions : " + ex.getMessage(), ex);
            throw new RevisionManagerException("This method returns a list of revisions : " + ex.getMessage(), ex);
        }
    }

    /**
     * This method returns the contents of the entry identfied by the information.
     *
     * @param info The object to identify the information.
     * @return The string containing the information.
     * @throws com.rift.coad.revision.RevisionManagerException
     */
    public String getEntry(RDFRevisionInfo info) throws RevisionManagerException {
        String entryPath = generatePath(info.getRepository(), info.getBranch(), info.getFile())
                + VERSION_SECTION + info.getVersion();
        File entry = new File(entryPath);
        if (!entry.exists()) {
            log.info("Request to retrieve an entry that does not exist : " + entryPath);
            throw new RevisionManagerException("Request to retrieve an entry that does not exist : " + entryPath);
        } else if (entry.isDirectory()) {
            log.info("The requested entry is a directory : " + entryPath);
            throw new RevisionManagerException("The requested entry is a directory : " + entryPath);
        }
        try {
            FileInputStream in = new FileInputStream(entry);
            byte[] buffer = new byte[(int) entry.length()];
            in.read(buffer);
            in.close();
            return new String(buffer);
        } catch (Exception ex) {
            log.error("Failed to retrieve the file [" + entryPath + "] because : " +
                    ex.getMessage(), ex);
            throw new RevisionManagerException("Failed to retrieve the file [" + entryPath + "] because : " +
                    ex.getMessage(), ex);
        }
    }

    /**
     * This method adds a directory entry.
     *
     * @param repository The repository the entry belongs to.
     * @param branch The branch the entry belongs to.
     * @param path The path within the repository and branch.
     * @return The revision information.
     * @throws com.rift.coad.revision.RevisionManagerException
     */
    public RDFRevisionInfo addDirectory(String repository, String branch, String path)
            throws RevisionManagerException {
        try {
            checkPath(repository, branch, path);
            Session session = SemanticUtil.getInstance(RevisionManagerDaemonImpl.class).getSession();
            return createDirectories(session, path,repository, branch);
        } catch (RevisionManagerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to add the directory : " + ex.getMessage(), ex);
            throw new RevisionManagerException("Failed to add the directory : " +
                    ex.getMessage(), ex);
        }
    }

    
    /**
     * This method is called to add an entry to the repository.
     *
     * @param repository The repository to add the entry to.
     * @param branch The branch within the repository.
     * @param path The path within the repository.
     * @param contents The contents.
     * @return The object containing the revision information.
     * @throws com.rift.coad.revision.RevisionManagerException
     */
    public RDFRevisionInfo addEntry(String repository, String branch, String path, String contents)
            throws RevisionManagerException {
        try {
            checkPath(repository, branch, path);
            String directory = createRepositoryPath(path);
            Session session = SemanticUtil.getInstance(RevisionManagerDaemonImpl.class).getSession();
            createDirectories(session, directory,repository, branch);
            File entryDir = new File(this.path,directory);
            File[] entries = entryDir.listFiles();
            for (File entry : entries) {
                if (!entry.getName().equals("..") &&
                        !entry.getName().equals(".")) {
                    throw new RevisionManagerException("Duplicate entry [" + path + "]");
                }
            }
            String fileName = getFileName(path);
            File entryFile = new File(entryDir.getPath(),fileName + this.VERSION_SECTION + 1);
            FileOutputStream out = new FileOutputStream(entryFile);
            out.write(contents.getBytes());
            out.close();
            RDFRevisionInfo result = new RDFRevisionInfo(path, RepositoryTypes.FILE,
                    repository, branch,"1", new Date(),
                    com.rift.coad.lib.security.SessionManager.getInstance().
                    getSession().getUser().getName());
            session.persist(result);
            return result;
        } catch (RevisionManagerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to add the entry : " + ex.getMessage(), ex);
            throw new RevisionManagerException("Failed to add the entry : " +
                    ex.getMessage(), ex);
        }
    }


    /**
     * This method updates an entry managed by the revision manager.
     *
     * @param repository The respository the update is part of
     * @param branch The branch within the repository.
     * @param path The path within the repository.
     * @param contents The contents of the entry to update.
     * @return The return result.
     * @throws com.rift.coad.revision.RevisionManagerException
     */
    public RDFRevisionInfo updateEntry(String repository, String branch, String path, String contents) throws RevisionManagerException {
        try {
            checkPath(repository, branch, path);
            String directory = createRepositoryPath(path);
            File directoryPath = new File(this.path,directory);
            if (!directoryPath.exists() || !directoryPath.isDirectory()) {
                log.error("The path [" + path + "] is not a valid directory");
                throw new RevisionManagerException
                        ("The path [" + path + "] is not a valid directory");
            }
            File[] directoryList = directoryPath.listFiles();
            int count = 1;
            for (File entry : directoryList) {
                if (!entry.getName().equals(".") && !entry.getName().equals("..")) {
                    count++;
                }
            }
            String fileName = getFileName(path);
            File entryFile = new File(directoryPath.getPath(),fileName + this.VERSION_SECTION + count);
            if (entryFile.exists()) {
                log.error("The path [" + entryFile.getPath() + "] exists and the repository is corrupt");
                throw new RevisionManagerException
                        ("The path [" + entryFile.getPath() + "] exists and the repository is corrupt");
            }
            FileOutputStream out = new FileOutputStream(entryFile);
            out.write(contents.getBytes());
            out.close();
            Session session = SemanticUtil.getInstance(RevisionManagerDaemonImpl.class).getSession();
            RDFRevisionInfo result = new RDFRevisionInfo(path, RepositoryTypes.FILE,
                    repository, branch,"" + count, new Date(),
                    com.rift.coad.lib.security.SessionManager.getInstance().
                    getSession().getUser().getName());
            session.persist(result);
            return result;
        } catch (RevisionManagerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to update the entry : " + ex.getMessage(), ex);
            throw new RevisionManagerException("Failed to update the entry : " +
                    ex.getMessage(), ex);
        }
    }


    /**
     * This method is called to delete an entry from the repository.
     * 
     * @param repository This method is responsible for deleting a directory.
     * @param branch The branch
     * @param path The path
     * @throws com.rift.coad.revision.RevisionManagerException
     */
    public void deleteEntry(String repository, String branch, String path) throws RevisionManagerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    /**
     * This method is called create a tag.
     *
     * @param tag The name of the tag to create.
     * @param description The description of the tag to create.
     * @param revisions The revision is.
     * @throws com.rift.coad.revision.RevisionManagerException
     */
    public void createTag(String tag, String description, List<RDFRevisionInfo> revisions) throws RevisionManagerException {
        try {
            Session session = SemanticUtil.getInstance(RevisionManagerDaemonImpl.class).getSession();
            RDFTag rdfTag = new RDFTag(tag,description,
                    com.rift.coad.lib.security.SessionManager.getInstance().
                    getSession().getUser().getName());
            Resource resource = session.persist(rdfTag);
            for (RDFRevisionInfo revision : revisions) {
                resource.addProperty(REVISION_REF, revision);
            }
        } catch (Exception ex) {
            log.error("Failed to create the tag : " + ex.getMessage(),ex);
            throw new RevisionManagerException
                    ("Failed to create the tag : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method lists the tags.
     *
     * @return The list of tags.
     * @throws com.rift.coad.revision.RevisionManagerException
     */
    public List<RDFTag> listTags() throws RevisionManagerException {
        try {
            Session session = SemanticUtil.getInstance(RevisionManagerDaemonImpl.class).getSession();
            List<SPARQLResultRow> entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/revision#Tag> } ").execute();
            List<RDFTag> tags = new ArrayList<RDFTag>();
            for (SPARQLResultRow row : entries) {
                tags.add(row.get(0).cast(RDFTag.class));
            }
            return tags;
        } catch (Exception ex) {
            log.error("Failed to list the tags :" + ex.getMessage(), ex);
            throw new RevisionManagerException
                    ("Failed to list the tags :" + ex.getMessage(), ex);
        }
    }


    /**
     * This method return a list of revisions attached to a given tag.
     *
     * @param tag The name of the tag to retrieve.
     * @return This method returns a list of revisions entries for a tag.
     * @throws com.rift.coad.revision.RevisionManagerException
     */
    public List<RDFRevisionInfo> getTagList(String tag) throws RevisionManagerException {
        try {
            Session session = SemanticUtil.getInstance(RevisionManagerDaemonImpl.class).getSession();
            List<SPARQLResultRow> entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/revision#Tag> . " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/revision#TagName> ?TagName . " +
                    "FILTER (?TagName = ${TagName})} ").setString("TagName", tag).execute();
            List<RDFRevisionInfo> revisions = new ArrayList<RDFRevisionInfo>();
            if (entries.size() != 1) {
                log.error("The tag [" + tag + "] does not exist.");
                throw new RevisionManagerException
                        ("The tag [" + tag + "] does not exist.");
            }
            List<Resource> resources = entries.get(0).get(0).getResource().listProperties(tag);
            for (Resource resource : resources) {
                revisions.add(resource.get(RDFRevisionInfo.class));
            }
            return revisions;
        } catch (Exception ex) {
            log.error("Failed to retrieve a list of revisions for the tag : " +
                    ex.getMessage(),ex);
            throw new RevisionManagerException
                    ("Failed to retrieve a list of revisions for the tag : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method updates a tag definition
     *
     * @param tag The tag to remove.
     * @param revisions The revisions.
     * @throws com.rift.coad.revision.RevisionManagerException
     */
    public void updateTag(String tag, List<RDFRevisionInfo> revisions) throws RevisionManagerException {
       try {
            Session session = SemanticUtil.getInstance(RevisionManagerDaemonImpl.class).getSession();
            List<SPARQLResultRow> entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/revision#Tag> . " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/revision#TagName> ?TagName . " +
                    "FILTER (?TagName = ${TagName})} ").setString("TagName", tag).execute();
            if (entries.size() != 1) {
                log.error("The tag [" + tag + "] does not exist.");
                throw new RevisionManagerException
                        ("The tag [" + tag + "] does not exist.");
            }
            Resource resource = entries.get(0).get(0).getResource();
            for (RDFRevisionInfo revision : revisions) {
                resource.addProperty(REVISION_REF, revision);
            }
        } catch (Exception ex) {
            log.error("Failed to update the tag : " + ex.getMessage(),ex);
            throw new RevisionManagerException
                    ("Failed to update the tag : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method deletes the specified tag.
     * @param tag The tag reference to delete.
     * @throws com.rift.coad.revision.RevisionManagerException
     */
    public void deleteTag(String tag) throws RevisionManagerException {
        try {
            Session session = SemanticUtil.getInstance(RevisionManagerDaemonImpl.class).getSession();
            List<SPARQLResultRow> entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/revision#Tag> . " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/revision#TagName> ?TagName . " +
                    "FILTER (?TagName = ${TagName})} ").setString("TagName", tag).execute();
            if (entries.size() != 1) {
                log.error("The tag [" + tag + "] does not exist.");
                throw new RevisionManagerException
                        ("The tag [" + tag + "] does not exist.");
            }
            session.remove(entries.get(0).get(0).cast(RDFTag.class));
        } catch (Exception ex) {
            log.error("Failed to remove the tag : " + ex.getMessage(),ex);
            throw new RevisionManagerException
                    ("Failed to remove the tag : " + ex.getMessage(),ex);
        }
    }

    
    /**
     * This method generates the path
     *
     * @param repository
     * @param branch
     * @param path
     * @return
     * @throws com.rift.coad.revision.RevisionManagerException
     */
    private String generatePath(String repository, String branch, String path) throws RevisionManagerException {
        checkPath(repository, branch, path);
        // check if the path contains a suffix
        if (path.contains(".")) {
            return this.path + File.separator + path + REPOSITORY_FILE;
        } else {
            return this.path + File.separator + path;
        }
    }

    /**
     * This method retrieves the revision information.
     *
     * @param session The session that this is attached to.
     * @param repository The repository that this resides in.
     * @param branch The branch
     * @param path The path
     * @param version The version.
     * @return The reference to the retrieved revision information.
     * @throws com.rift.coad.revision.RevisionManagerException
     */
    private RDFRevisionInfo getRevision(Session session,
            String repository, String branch, String path, String version) throws RevisionManagerException {
        try {
            List<SPARQLResultRow> entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/revision#RevisionInfo> . " +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/revision#Repository> ?Repository . " +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/revision#Branch> ?Branch . " +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/revision#File> ?File . " +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/revision#Version> ?Version . " +
                    "FILTER " +
                    "   ((?Repository = ${Repository}) && " +
                    "   (?Branch = ${Branch}) && " +
                    "   (?File = ${Path}) && " +
                    "   (?Version = ${Version})) } ").
                    setString("Repository", repository).
                    setString("Branch", branch).
                    setString("Path", path).
                    setString("Version", version).execute();

            if (entries.size() != 1) {
                log.info("Failed to retrieve the [" + repository + "][" + branch + "][" + path + "][" + version + "]");
                return null;
            }
            return entries.get(0).get(0).cast(RDFRevisionInfo.class);
        } catch (Exception ex) {
            log.error("Failed to retrieve the revision information because : " + ex.getMessage(), ex);
            throw new RevisionManagerException(
                    "Failed to retrieve the revision information because : " + ex.getMessage(), ex);
        }
    }

    /**
     * This method returns the version number found within the file name.
     * @param fileName The name of the file.
     * @return The string containing the version information.
     */
    private String getFromFileNameVersion(String fileName) throws RevisionManagerException {
        int index = fileName.lastIndexOf(VERSION_SECTION);
        if (index == -1) {
            log.error("The version information is not encapsulated in the file name : " + fileName);
        }
        return fileName.substring(index + VERSION_SECTION.length());
    }

    /**
     * This method is used to strip the repository information from the back of the file.
     *
     * @return The string containing the repository information.
     * @param fileName The filename to strip.
     */
    private String stripFileName(String fileName) {
        if (fileName.contains(REPOSITORY_FILE)) {
            return fileName.substring(0, fileName.lastIndexOf(REPOSITORY_FILE));
        }
        return fileName;
    }

    /**
     * This method creates the specified directory path.
     *
     * @param session The session information.
     * @param repository The repository to create.
     * @param branch The branch to create.
     * @param path The path to create.
     * @return The rdf revision information.
     * @throws com.rift.coad.revision.RevisionManagerException
     */
    private RDFRevisionInfo createDirectories(Session session, String subPath, 
            String repository, String branch) throws RevisionManagerException {
        try {
            String[] entries = subPath.split("[/]");
            File currentDirectory = new File(this.path);
            RDFRevisionInfo result = new RDFRevisionInfo(null, RepositoryTypes.DIRECTORY,
                    repository, branch,"1", new Date(), 
                    com.rift.coad.lib.security.SessionManager.getInstance().
                    getSession().getUser().getName());
            StringBuffer relativePath = new StringBuffer();
            for (String entry : entries) {
                currentDirectory = new File(currentDirectory.getPath(), entry);
                relativePath.append(entry).append(File.separator);
                if (currentDirectory.exists() && currentDirectory.isDirectory()) {
                    continue;
                } else if (currentDirectory.exists() && !currentDirectory.isDirectory()) {
                    log.error("This is not a valid directory path [" + currentDirectory.getPath() + "]");
                    throw new RevisionManagerException("This is not a valid directory path [" + currentDirectory.getPath() + "]");
                }
                currentDirectory.mkdir();
                result.setFile(relativePath.toString());
                session.persist(result);
            }
            return result;
        } catch (RevisionManagerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to create the directory structure [" + subPath
                    + "] because : " + ex.getMessage(),ex);
            throw new RevisionManagerException(
                    "Failed to create the directory structure [" + subPath 
                    + "] because : " + ex.getMessage(),ex);
        }
    }

    
    /**
     * This method checks to see if the path is valid.
     *
     * @param repository The respository to check for in the path
     * @param branch The branch that must be present in the path.
     * @param path The path to add
     * @throws com.rift.coad.revision.RevisionManagerException
     */
    private void checkPath(String repository, String branch, String path) throws RevisionManagerException {
        if (!path.contains(repository) || !path.contains(branch)) {
            log.error("The path does not contain the repository or branch scoping");
            throw new RevisionManagerException
                    ("The path does not contain the repository or branch scoping");
        } else if (path.indexOf(repository) >= path.indexOf(branch)) {
            log.error("The path is incorrectly formatted.");
            throw new RevisionManagerException
                    ("The path is incorrectly formatted.");
        }
    }


    /**
     *
     * @param path
     * @return
     * @throws com.rift.coad.revision.RevisionManagerException
     */
    private String createRepositoryPath(String path) throws RevisionManagerException {
        return path + REPOSITORY_FILE;
    }


    /**
     * This method returns the file name.
     *
     * @param path The path containing the file name.
     * @return The string containing the file name.
     * @throws com.rift.coad.revision.RevisionManagerException
     */
    private String getFileName(String path) throws RevisionManagerException {
        String[] pathComponents = path.split("[/]");
        String fileName = pathComponents[pathComponents.length - 1];
        return fileName;
    }


}
