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
 * BeanConnector.java
 *
 * This class is responsible for supplying connections to the required beans.
 */

// package path
package com.rift.coad.lib.deployment.bean;

// java imports
import java.util.Set;

// coadunation imports
import com.rift.coad.lib.bean.BeanWrapper;

/**
 * This class is responsible for supplying connections to the required beans.
 *
 * @author Brett Chaldecott
 */
public class BeanConnector {
    
    // the classes private member variables
    private static BeanConnector singleton = null;
    private BeanManager beanManager = null;
    
    /** 
     * Creates a new instance of BeanConnector 
     *
     * @param beanManager The reference to the bean manager.
     */
    private BeanConnector(BeanManager beanManager) {
        this.beanManager = beanManager;
    }
    
    
    /**
     * The method responsible for instanciating a new bean connector.
     *
     * @param beanManager The reference to the bean manager.
     */
    public static synchronized void init(BeanManager beanManager) {
        if (singleton == null) {
            singleton = new BeanConnector(beanManager);
        }
    }
    
    
    /**
     * This method is responsible for returning a reference to the bean
     * bean connector.
     *
     * @return The reference to the bean connector.
     */
    public static synchronized BeanConnector getInstance() throws BeanException {
        if (singleton == null) {
            throw new BeanException(
                    "The bean connector has not bean initialized.");
        }
        return singleton;
    }
    
    
    /**
     * This method returns the list of keys.
     *
     * @return The list of beans managed by this object.
     */
    public Set getKeys() {
        return beanManager.getKeys();
    }
    
    
    /**
     * Retrieve the bean that matches the key
     *
     * @return Return the object identified by the key.
     * @param key The key identifying the bean.
     */
    public Object getBean(String key) {
        BeanWrapper beanWrapper = (BeanWrapper)beanManager.getBean(key);
        if (beanWrapper == null) {
            return beanWrapper;
        }
        return beanWrapper.getProxy();
    }
}
