/*
 * ScriptBroker: The script broker daemon.
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
 * FileTypes.java
 */


// package path
package com.rift.coad.script.broker;

/**
 * This class defines common daemon constants.
 *
 * @author brett chaldecott
 */
public class Constants {
    /**
     * The base directory for all the scripts within a coadunation os installation.
     */
    public final static String SCRIPT_BASE = "script_base";

    /**
     * The script revision information
     */
    public final static String REVISION = "http://www.coadunation.net/schema/rdf/1.0/script#ScriptRevision";

    /**
     * the change reference
     */
    public final static String CHANGE_REF = "http://www.coadunation.net/schema/rdf/1.0/script#ScriptChangeRef";


    /**
     * The service
     */
    public final static String SERVICE = "script_distribution_daemon";


    /**
     * The file types.
     */
    public final static String[] FILE_TYPES = new String[]{
        "groovy","py","property","xml"};


    /**
     * The repository that the script manager is using.
     */
    public final static String SCRIPT_REPOSITORY = "script_repository";

    /**
     * The branch within the repository
     */
    public final static String SCRIPT_BRANCH = "script_branch";


}
