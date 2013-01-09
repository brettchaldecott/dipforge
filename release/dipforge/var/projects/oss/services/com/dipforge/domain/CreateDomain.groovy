/*
 * oss: Description
 * Copyright (C) Tue Jan 08 04:53:28 SAST 2013 owner 
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
 * CreateDomain.groovy
 * @author brett chaldecott
 */

package com.dipforge.domain


// imports
import com.rift.coad.util.connection.ConnectionManager
import com.rift.coad.daemon.rdbusermanager.RDBUserManagementMBean
import java.util.Date
import org.apache.log4j.Logger;


/**
 * This object is responsible for creating a new user
 */
class CreateDomain {
    
    static Logger log = Logger.getLogger("com.dipforge.log.pckg.com.dipforge.domain.CreateDomain");
    
    
    /**
     * This is a test call.
     */
    def createDomain(def Domain) {
        log.info("#######  This is a domain test " + Domain.getName())
    
    }

}