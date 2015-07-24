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
 * JMXBeanConnector.java
 *
 * This object is responsible for retrieving a reference to the JMX bean when
 * requested.
 */

// package path
package com.rift.coad.lib.deployment.jmxbean;

// java imports
import java.util.Set;

// coadunation imports
import com.rift.coad.lib.bean.BeanWrapper;

/**
 * This object is responsible for retrieving a reference to the JMX bean when
 * requested.
 *
 * @author Brett Chaldecott
 */
public class JMXBeanConnector {
    
    // the classes private member variables
    private static JMXBeanConnector singleton = null;
    private JMXBeanManager beanManager = null;
    
    /** 
     * Creates a new instance of JMXBeanConnector 
     */
    private JMXBeanConnector(JMXBeanManager beanManager) {
        this.beanManager = beanManager;
    }
    
    
    /**
     * This method will be responsible for initializing the jmx bean connector.
     *
     * @param beanManager The reference to the bean manager.
     */
    public static synchronized void init(JMXBeanManager beanManager) {
        if (singleton == null) {
            singleton = new JMXBeanConnector(beanManager);
        }
    }
    
    
    /**
     * This method returns the JMXBean connector instance.
     *
     * @return The reference to the jmx bean connector.
     * @exception JMXException
     */
    public static synchronized JMXBeanConnector getInstance() 
            throws JMXException {
        if (singleton == null) {
            throw new JMXException (
                    "The JMX Bean connector has not been initialized.");
        }
        return singleton;
    }
    
    
    /**
     * Retrieve the bind keys. These are the standard lookup keys.
     *
     * @return The list of beans managed by this object.
     */
    public Set getJMXBeanKeys() {
        return beanManager.getBindKeys();
    }
    
    
    /**
     * This method will return the reference to the wrapper object using the
     * bind key identifier.
     *
     * @return The object identified by the bind key.
     * @param key The bind key to retrieve the object for.
     */
    public Object getJMXBean(String key) {
        BeanWrapper beanWrapper = (BeanWrapper)beanManager.getBindObject(key);
        if (beanWrapper == null) {
            return beanWrapper;
        }
        return beanWrapper.getProxy();
    }
}
