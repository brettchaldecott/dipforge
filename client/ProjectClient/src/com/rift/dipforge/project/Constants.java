/*
 * ProjectClient: The project client interface..
 * Copyright (C) 2011  Rift IT Contracting
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

package com.rift.dipforge.project;

/**
 * The constants for the project client and project manager.
 *
 * @author brett chaldecott
 */
public class Constants {
    
    /**
     * The key in the configuration used to identify the project dir
     */
    public final static String PROJECT_BASE = "project_base";


    /**
     * The constant for the project information file.
     */
    public final static String PROJECT_INFO_FILE = "project.properties";


    /**
     * The key in the configuration used to identify the project template directory.
     */
    public final static String TEMPLATE_DIR = "template_dir";


    /**
     * The template suffix information.
     */
    public final static String TEMPLATE_SUFFIX = ".template";


    /**
     * The project types
     */
    public final static String PROJECT_TYPES = "project_types.xml";

}
