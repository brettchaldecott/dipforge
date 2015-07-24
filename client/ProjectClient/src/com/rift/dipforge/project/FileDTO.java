/*
 * ProjectClient: The project client interface.
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
 * FileDTO.java
 */

package com.rift.dipforge.project;

import java.io.Serializable;
import java.util.Date;

/**
 * The file DTO
 *
 * @author brett chaldecott
 */
public class FileDTO implements Serializable {
    // private member variables
    private String name;
    private String path;
    private int type;
    private Date created;
    private String creator;
    private Date modified;
    private String modifier;

    /**
     * The default constructor
     */
    public FileDTO() {
    }


    /**
     * This constructor sets up all internal member variables
     *
     * @param name The name of the file.
     * @param path The path to the file.
     * @param type The type of file.
     * @param created The date the file was created on.
     * @param creator The creator of the file.
     * @param modified The last time the file was modified
     * @param modifier The modifier of this file.
     */
    public FileDTO(String name, String path, int type, Date created, String creator, Date modified, String modifier) {
        this.name = name;
        this.path = path;
        this.type = type;
        this.created = created;
        this.creator = creator;
        this.modified = modified;
        this.modifier = modifier;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    /**
     * This method returns the file type for this entity.
     *
     * @return int identifying the file type, 0 = Directory, 1 = File
     */
    public int getType() {
        return type;
    }


    /**
     * This method sets the file type.
     *
     * @param type int identifying the file type, 0 = Directory, 1 = File
     */
    public void setType(int type) {
        this.type = type;
    }


    /**
     * This method determines if the objects are equal.
     * @param obj The object to perform the comparison on.
     * @return TRUE if equal, FALSE if not.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FileDTO other = (FileDTO) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.path == null) ? (other.path != null) : !this.path.equals(other.path)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        return true;
    }


    /**
     * This method returns the hash code value.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 47 * hash + (this.path != null ? this.path.hashCode() : 0);
        hash = 47 * hash + this.type;
        return hash;
    }


    /**
     * This method generates a string representation of this object.
     *
     * @return The string representation of this object.
     */
    @Override
    public String toString() {
        return "FileDTO{" + "name=" + name + "path=" + path + "type=" + type
                + "created=" + created + "creator=" + creator + "modified=" +
                modified + "modifier=" + modifier + '}';
    }



}
