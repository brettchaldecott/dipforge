/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  2015 Burntjam
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
 * JMXBeanInfo.java
 *
 * The JMX object responsible for storing the bean information.
 */

package com.rift.coad.lib.deployment;

// java imports
import java.util.List;
import java.util.ArrayList;

/**
 * The JMX object responsible for storing the bean information.
 *
 * @author Brett Chaldecott
 */
public class JMXBeanInfo extends BeanInfo {
    
    // class private member variables
    private String objectName = null;
    
    /** 
     * Creates a new instance of JMXBeanInfo 
     */
    public JMXBeanInfo() {
    }
    
    
    /**
     * The getter for the object name.
     *
     * @return The name of the object.
     */
    public String getObjectName() {
        return objectName;
    }
    
    
    /**
     * The method is responsible for setting the object name.
     *
     * @param objectName The name of the object to set.
     */
    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }
    
    
    /**
     * This method will return true if the object is initialized and false if it
     * is not.
     *
     * @return TRUE if initialized and FALSE if not.
     */
    public boolean isInitialized() {
        if (objectName != null) {
            return super.isInitialized();
        }
        return false;
    }
}
