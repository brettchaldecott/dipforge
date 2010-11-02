/*
 * BasicCoadunationBean: The coaduntion basic sample
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
 * BeanImpl.java
 *
 */

package com.test;

import java.rmi.RemoteException;

/**
 *
 * @author Glynn Chaldecott
 */
public class BasicBeanImpl implements BasicBeanInterface {
    
    /** Creates a new instance of BeanImpl */
    public BasicBeanImpl() {
    }

    public void testMethod() throws RemoteException {
        System.out.println("The test method has been called");
    }
    
}
