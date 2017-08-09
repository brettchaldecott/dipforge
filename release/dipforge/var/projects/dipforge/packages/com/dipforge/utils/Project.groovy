/*
 * dipforge: Description
 * Copyright (C) Fri Apr 07 03:07:36 UTC 2017 owner 
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
 * Project.groovy
 * @author admin
 */

package com.dipforge.utils

import java.io.File

class Project {
    // class constants
    def static PROJECT_PATH= "/var/projects/"
    
    /**
     * This method returns the specified file 
     */
    def static getFile(def project, def path) {
        return new File("${Dipforge.DIPFORGE_HOME}${PROJECT_PATH}${project}/${path}")
    }
    
    /**
     * This method creates the specified file
     */
    def static createFile(def project, def path) {
        return new File("${Dipforge.DIPFORGE_HOME}${PROJECT_PATH}${project}/${path}")
    }
    
    
    /**
     * This method returns the specified file 
     */
    def static mkdirs(def project, def path) {
        return new File("${Dipforge.DIPFORGE_HOME}${PROJECT_PATH}${project}/${path}").mkdirs()
    }
    
    
    
}