/*
 * GroovyLib: The goovy environment manager
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
 * GroovyEnvironmentConstants.java
 */


package com.rift.dipforge.groovy.lib;

/**
 * The constants for the groovy environment.
 *
 * @author brett chaldecott
 */
public class GroovyEnvironmentConstants {

    /**
     * The library directory
     */
    public final static String DIPFORGE_LIB_DIR = "dipforge_lib_dir";


    /**
     * The base of the environment
     */
    public final static String ENVIRONMENT_BASE = "environment_base";

    /**
     * The library directory
     */
    public final static String LIB_DIR = "lib_dir";


    /**
     * The environment sub directories
     */
    public final static String ENVIRONMENT_SUB_DIRECTORIES = "environment_sub_directories";


    /**
     * The environmental libraries
     */
    public final static String ENVIRONMENT_LIBS_DIR = "environment_libs_dir";


    /**
     * This method closes the script down.
     */
    public final static String CLOSE_SCRIPT = "com/dipforge/close/Shutdown.groovy";
    
}
