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

// imports
import java.io.File;
import java.util.List;

/**
 * This method is called
 *
 * @author brett chaldecott
 */
public class XMLListGenerator {

    // private member variable
    private String baseUrl;
    private String baseDirectory;
    private List<File> files;
    private String suffix;

    /**
     * This constructor sets up the internal member variables.
     *
     * @param baseUrl The base url
     * @param baseDirectory The base directory.
     * @param files The list of files.
     */
    public XMLListGenerator(String baseUrl, String baseDirectory, List<File> files) {
        this.baseUrl = baseUrl;
        this.baseDirectory = baseDirectory;
        this.files = files;
    }

    /**
     * This constructor sets up the internal member variables.
     *
     * @param baseUrl The base url
     * @param baseDirectory The base directory.
     * @param files The list of files.
     * @param suffix The suffix of the file.
     */
    public XMLListGenerator(String baseUrl, String baseDirectory, List<File> files, String suffix) {
        this.baseUrl = baseUrl;
        this.baseDirectory = baseDirectory;
        this.files = files;
        this.suffix = suffix;
    }


    /**
     * This method generates the list.
     *
     * @return The output.
     */
    public String generateXMLList() throws SchemaUtilException {
        try {
            StringBuffer result = new StringBuffer();
            result.append("<files>\n");
            for (File file: files) {
                result.append("    <file name=\"");
                String path = file.getPath();
                if (path.contains(baseDirectory)) {
                    path = path.substring(baseDirectory.length());
                }
                if (suffix != null && path.endsWith(suffix)) {
                    path = path.substring(0,path.length() - suffix.length());
                }
                path = path.replace('\\','/');
                result.append(baseUrl).append(path);
                result.append("\"/>\n");
            }
            result.append("</files>\n");
            return result.toString();
        } catch (Exception ex) {
            throw new SchemaUtilException("Failed to generate the XML list : " +
                    ex.getMessage(),ex);
        }
    }
}
