/*
 * ProjectDaemon: The project daemon implementation
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
 * ProjectTypeManagerImpl.java
 */

package com.rift.dipforge.project;

import com.rift.coad.datamapperbroker.util.DataMapperBrokerUtil;
import com.rift.coad.rdf.types.mapping.MethodMapping;
import com.rift.dipforge.project.factory.ProjectBean;
import com.rift.dipforge.project.factory.ProjectFactory;
import com.rift.dipforge.project.method.XMLMethodMappingParser;
import java.util.List;
import org.apache.log4j.Logger;



/**
 * This class
 * 
 * @author brett chaldecott
 */
public class ProjectMethodManagerImpl implements ProjectMethodManager {

    // class static variables
    private static Logger log = Logger.getLogger(ProjectMethodManagerImpl.class);

    
    /**
     * The default constructor of the project method manager.
     */
    public ProjectMethodManagerImpl() {
    }

    
    /**
     * This method is responsible for publishing the methods.
     * 
     * @param project The string containing name of the project to publish the methods for.
     * @throws ProjectException
     */
    public void publishMethods(String content) throws ProjectException {
        try {
            XMLMethodMappingParser parser = new XMLMethodMappingParser(
                    content);
            DataMapperBrokerUtil brokerUtil = new DataMapperBrokerUtil();
            for (String jndi : parser.getJNDIList()) {
                List<MethodMapping> methods = parser.getMethodMapping(jndi);
                brokerUtil.register(methods);
            }
        } catch (Exception ex) {
            log.error("Failed to publish the methods : " + ex.getMessage(),ex);
            throw new ProjectException
                    ("Failed to publish the methods : " + ex.getMessage(),ex);
        }
    }
    
    
}
