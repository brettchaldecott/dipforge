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
 * RDFRevisionInfo.java
 */

package com.rift.coad.revision.rdf;

import com.rift.coad.rdf.objmapping.base.DataType;

import java.util.Date;
import thewebsemantic.Identifier;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;

/**
 * This object represents the revision information for the type manager.
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/revision#")
@RdfType("RevisionInfo")
public class RDFRevisionInfo extends DataType {

    // private member variables
    private String file;
    private String type;
    private String repository;
    private String branch;
    private String version;
    private Date changeDate;
    private String author;


    /**
     * The default constructor for the revision object.
     */
    public RDFRevisionInfo() {
    }


    /**
     * The constructor that sets up all the member variables except the tag.
     *
     * @param file The name of the file the change belongs to.
     * @param type The type of file.
     * @param repository The repository that the revision is part of.
     * @param branch The branch the revision is on.
     * @param version The version information for the revision.
     * @param changeDate The date the change occurred on.
     * @param author The author of the changes.
     */
    public RDFRevisionInfo(String file, String type, String repository, String branch,
            String version, Date changeDate, String author) {
        this.file = file;
        this.type = type;
        this.repository = repository;
        this.branch = branch;
        this.version = version;
        this.changeDate = changeDate;
        this.author = author;
    }


    /**
     * This method returns the object id for the RDF.
     *
     * @return The string containing the object id.
     */
    @Override
    public String getObjId() {
        return  repository + "." + branch + "." + file + "." + version;
    }


    /**
     * This method returns the string containing the author information.
     *
     * @return The string containing the author information.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/revision#Author")
    public String getAuthor() {
        return author;
    }

    
    /**
     * This method sets the author information.
     * 
     * @param author The string containing the author information.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/revision#Author")
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * This method retrieves the branch information.
     *
     * @return The string containing the branch information.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/revision#Branch")
    @Identifier()
    public String getBranch() {
        return branch;
    }


    /**
     * This method sets the branch information.
     *
     * @param branch The string containing the branch information.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/revision#Branch")
    @Identifier()
    public void setBranch(String branch) {
        this.branch = branch;
    }

    
    /**
     * The string containing the repository information.
     * 
     * @return The string containing the repository name.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/revision#Repository")
    public String getRepository() {
        return repository;
    }


    /**
     * This method sets the repository information.
     *
     * @param repository The string containing the repository information.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/revision#Repository")
    public void setRepository(String repository) {
        this.repository = repository;
    }

    
    /**
     * The date the change was made to this revision.
     *
     * @return The string containing the change information.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/revision#ChangeDate")
    public Date getChangeDate() {
        return changeDate;
    }


    /**
     * This method sets the change date information
     *
     * @param changeDate This method sets the change date.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/revision#ChangeDate")
    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }


    /**
     * This method returns the file information.
     *
     * @return The string containing the file information.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/revision#File")
    @Identifier()
    public String getFile() {
        return file;
    }

    /**
     * This method sets the file information.
     *
     * @param file The path to the file.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/revision#File")
    public void setFile(String file) {
        this.file = file;
    }


    /**
     * This method returns the type information.
     *
     * @return The string containing the file type information.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/revision#Type")
    public String getType() {
        return type;
    }


    /**
     * This method sets the type information.
     *
     * @param type The string containing the type information.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/revision#Type")
    public void setType(String type) {
        this.type = type;
    }


    /**
     * This method returns the version information.
     *
     * @return The string containing the version information.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/revision#Version")
    @Identifier
    public String getVersion() {
        return version;
    }


    /**
     * This method sets the version information.
     *
     * @param version This method sets the version information
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/revision#Version")
    public void setVersion(String version) {
        this.version = version;
    }


    /**
     * This method sets the equals operator.
     *
     * @param obj The object containing the equals information.
     * @return This method returns true if the objects are equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RDFRevisionInfo other = (RDFRevisionInfo) obj;
        if ((this.file == null) ? (other.file != null) : !this.file.equals(other.file)) {
            return false;
        }
        if ((this.branch == null) ? (other.branch != null) : !this.branch.equals(other.branch)) {
            return false;
        }
        if ((this.version == null) ? (other.version != null) : !this.version.equals(other.version)) {
            return false;
        }
        return true;
    }


    /**
     * This method returns the hash code for the object.
     *
     * @return The string containing the hash code for the object.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (this.file != null ? this.file.hashCode() : 0);
        hash = 29 * hash + (this.branch != null ? this.branch.hashCode() : 0);
        hash = 29 * hash + (this.version != null ? this.version.hashCode() : 0);
        return hash;
    }


    /**
     * This method returns the string vale of this object.
     *
     * @return The string version of this object.
     */
    public String toString() {
        return "[File: " + file + "\n" +
            "Type: " + type + "\n" +
            "Repository: " + repository + "\n" +
            "Branch: " + branch + "\n" +
            "Version: " + version + "\n" +
            "Date: " + changeDate + "\n" +
            "Author: " + author + "]\n";
    }
}
