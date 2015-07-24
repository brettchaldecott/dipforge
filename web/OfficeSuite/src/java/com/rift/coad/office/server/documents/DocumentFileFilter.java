/*
 * OfficeSuite: The implementation of the office product suite.
 * Copyright (C) 2010  2015 Burntjam
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
 * DocumentFileFilter.java
 */

package com.rift.coad.office.server.documents;

import java.io.File;

/**
 * This calss is the implementailt in of the file filter
 * @author brett
 */
public class DocumentFileFilter implements java.io.FileFilter {
    
    // private member variables
    private String fileName;
    
    /**
     * The 
     * 
     * @param fileName
     */
    public DocumentFileFilter(String fileName) {
        this.fileName = fileName;
    }
    
    
    /**
     * This method returns true if the file is to be accepted.
     * 
     * @param The file to perform the check on.
     * @return TRUE if they match, FALSE if not.
     */
    public boolean accept(File name) {
        if (name.getName().equals(fileName)) {
            return true;
        }
        return false;
    }

}
