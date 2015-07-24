/*
 * ScriptIDE: The coadunation ide for editing scripts in coadunation.
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
 * Constants.java
 */

// package path
package com.rift.coad.script.client.files.php;

/**
 * The constants.
 *
 * @author brett chaldecott
 */
public class Constants {

    /**
     * The class constants.
     */
    public final static String[] FILE_TYPES = new String[] {
        "Scope","PHP","XML","INI", "HTML","CSS","JS"
    };


    /**
     * File suffixes.
     */
    public final static String[] FILE_SUFFIXES = new String[]{
        "php","xml", "ini", "html", "css", "js"};


    /**
     * The name of the file icon.
     */
    public final static String FILE_ICON = "text-x-generic.png";
}
