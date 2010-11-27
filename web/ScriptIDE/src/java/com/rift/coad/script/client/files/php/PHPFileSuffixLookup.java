/*
 * ScriptIDE: The coadunation ide for editing scripts in coadunation.
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
 * PHPFileSuffixLookup.java
 */


// package path
package com.rift.coad.script.client.files.php;

/**
 * This object performs a type lookup.
 *
 * @author brett chaldecott
 */
public class PHPFileSuffixLookup {


    /**
     * This method returns a suffix value.
     *
     * @param type The type of file.
     * @return The suffix for that file.
     */
    public static String getSuffix(String type) {
        String suffix = "";
        if (type.equals(Constants.FILE_TYPES[1])) {
            suffix = Constants.FILE_SUFFIXES[0];
        } else if (type.equals(Constants.FILE_TYPES[2])) {
            suffix = Constants.FILE_SUFFIXES[1];
        } else if (type.equals(Constants.FILE_TYPES[3])) {
            suffix = Constants.FILE_SUFFIXES[2];
        } else if (type.equals(Constants.FILE_TYPES[4])) {
            suffix = Constants.FILE_SUFFIXES[3];
        } else if (type.equals(Constants.FILE_TYPES[5])) {
            suffix = Constants.FILE_SUFFIXES[4];
        } else if (type.equals(Constants.FILE_TYPES[6])) {
            suffix = Constants.FILE_SUFFIXES[5];
        }
        return suffix;
    }


    /**
     * This method returns a suffix value.
     *
     * @param type The type of file.
     * @return The suffix for that file.
     */
    public static String getTypeForSuffix(String type) {
        String suffix = "";
        if (type.equals(Constants.FILE_SUFFIXES[0])) {
            suffix = Constants.FILE_TYPES[1];
        } else if (type.equals(Constants.FILE_SUFFIXES[1])) {
            suffix = Constants.FILE_TYPES[2];
        } else if (type.equals(Constants.FILE_SUFFIXES[2])) {
            suffix = Constants.FILE_TYPES[3];
        } else if (type.equals(Constants.FILE_SUFFIXES[3])) {
            suffix = Constants.FILE_TYPES[4];
        } else if (type.equals(Constants.FILE_SUFFIXES[4])) {
            suffix = Constants.FILE_TYPES[5];
        } else if (type.equals(Constants.FILE_SUFFIXES[5])) {
            suffix = Constants.FILE_TYPES[6];
        }
        return suffix;
    }


    /**
     * This method returns the suffix for the given name.
     *
     * @param name The string containing the name of the file.
     * @return The string containing the suffix for the name.
     */
    public static String getSuffixForName(String name) {
        return name.substring(name.lastIndexOf(".") + 1);
    }


    /**
     * This method returns true if the file is executable.
     *
     * @param name The name of the file to execute.
     * @return TRUE if executable, FALSE if not.
     */
    public static boolean isFileViewable(String name) {
        String suffix = getSuffixForName(name);
        if (suffix.equals(Constants.FILE_SUFFIXES[0]) ||
                suffix.equals(Constants.FILE_SUFFIXES[3])) {
            return true;
        }
        return false;
    }
}
