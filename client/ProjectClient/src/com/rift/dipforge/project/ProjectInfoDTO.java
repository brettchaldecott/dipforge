/*
 * ProjectClient: The project client interface..
 * Copyright (C) 2011  2015 Burntjam
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
 * ProjectTypeManager.java
 */

package com.rift.dipforge.project;

import java.io.Serializable;
import java.util.Date;

/**
 * The project information.
 *
 * @author brett chaldecott
 */
public class ProjectInfoDTO implements Serializable {

    private String name;
    private String description;
    private Date created;
    private Date modified;
    private String author;
    private String modifiedBy;

    /**
     * The default constructor.
     */
    public ProjectInfoDTO() {
    }


    /**
     * The constructor that sets all the values.
     *
     * @param name
     * @param description
     */
    public ProjectInfoDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * This method retrieves the description
     *
     * @return The string containing the description.
     */
    public String getDescription() {
        return description;
    }


    /**
     * The setter for the description
     *
     * @param description The description.
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * This method retrieves the name of the project.
     *
     * @return The name of the project.
     */
    public String getName() {
        return name;
    }


    /**
     * This method sets the name of the project.
     *
     * @param name The name of the project
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /**
     * This method returns the author information.
     * 
     * @return The string containing the author information.
     */
    public String getAuthor() {
        return author;
    }


    /**
     * This method sets the author information.
     *
     * @param author The string containing the author information.
     */
    public void setAuthor(String author) {
        this.author = author;
    }


    /**
     * The created date.
     *
     * @return The date it was created.
     */
    public Date getCreated() {
        return created;
    }


    /**
     * Set the created date.
     * @param created The date this was created.
     */
    public void setCreated(Date created) {
        this.created = created;
    }


    /**
     * The getter for the modified date.
     *
     * @return The date this object was modified on.
     */
    public Date getModified() {
        return modified;
    }


    /**
     * The setter for the modified date.
     *
     * @param modified The modified date.
     */
    public void setModified(Date modified) {
        this.modified = modified;
    }


    /**
     * The getter for the modified date.
     *
     * @return The modified by date.
     */
    public String getModifiedBy() {
        return modifiedBy;
    }


    /**
     * The setter for the modified by date.
     *
     * @param modifiedBy The modified by date.
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }


    /**
     * The string containing  the project information.
     *
     * @return The string containing the project information
     */
    @Override
    public String toString() {
        return "ProjectInfoDTO{" + "name=" + name + "description="
                + description + "created=" + created + "modified="
                + modified + "author=" + author + "modifiedBy=" + modifiedBy + '}';
    }

}
