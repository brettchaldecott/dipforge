/*
 * Tomcat: The deployer for the tomcat daemon
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
 * EARConstants.java
 */

package com.rift.coad.daemon.tomcat.ear;

/**
 * The constants for the ear.
 *
 * @author brett chaldecott
 */
public class EARConstants {

    /**
     * The local member variables
     */
    public final static String EAR_DEPLOYMENT_PROPERTIES = "ear_deployment_properties";


    /**
     * The ear file list name
     */
    public final static String EAR_FILE_LIST_NAME = "ear_file_list.properties";

    
    /**
     * This method sets the file context list name
     */
    public final static String CONTEXT_FILE_LIST_NAME = "context_file_list.properties";


    /**
     * This constants identifies the ear configuration directory.
     */
    public final static String EAR_CONFIG_DIR = "ear_config_dir";
}
