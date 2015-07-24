/*
 * SchemaUtils: Utilities implemented for the RDF schema.
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
 * DirectoryUtil.java
 */


// package path
package com.rift.coad.schema.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The directory util.
 *
 * @author brett chaldecott
 */
public class DirectoryUtil {

    
    /**
     * This method recurses throw a directory structure and returns the list of files
     * that match the given pattern.
     * 
     * @param path The path to perform the check on.
     * @param suffix The suffix.
     * @return The list of files.
     */
    public static List<File> recurseDirectory(File path, String suffix) throws DirectoryException {
        try {
            List<File> result = new ArrayList<File>();
            File[] files = path.listFiles();
            for (File file: files) {
                if (file.isDirectory()) {
                    result.addAll(recurseDirectory(file,suffix));
                } else if (file.isFile()) {
                    if (file.getName().endsWith(suffix)) {
                        result.add(file);
                    }
                }
            }
            return result;
        } catch (DirectoryException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new DirectoryException("Failed to recurse through the directory : " +
                    ex.getMessage(),ex);
        }
    }

}
