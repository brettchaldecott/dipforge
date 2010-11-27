/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  Rift IT Contracting
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
 * XMLConfigurationException.java
 *
 * XMLConfigurationFactory.java
 *
 * This class extends the configurtion factory to provide a XML version of the
 * API.
 */

// package
package com.rift.coad.lib.configuration.xml;

// coadunation imports
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.ConfigurationException;
import com.rift.coad.lib.configuration.Configuration;

/**
 * This class extends the configurtion factory to provide a XML version of the
 * API.
 *
 * @author Brett Chaldecott
 */
public class XMLConfigurationFactory extends ConfigurationFactory {
    
    // the classes private member variables
    private XMLConfigurationParser parser = null;
    
    /** 
     * Creates a new instance of XMLConfigurationFactory 
     */
    public XMLConfigurationFactory() throws XMLConfigurationException {
        parser = new XMLConfigurationParser();
    }

    
    /**
     * This method returns a reference to the configuration class scoped for the
     * class reference.
     *
     * @return Configuration The reference to the configuration class.
     * @param classRef The reference to the class that the configuration will be 
     *          retrieve for
     * @exception ConfigurationException
     */
    public Configuration getConfig(Class classRef) 
        throws ConfigurationException {
        if (parser.modified())
        {
            parser = new XMLConfigurationParser();
        }
        return parser.getConfig(classRef);
    }
}
