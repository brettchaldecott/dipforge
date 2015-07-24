/*
 * CoadunationTypeManage: The resource type manager.
 * Copyright (C) 2011  2015 Burntjam
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
 * PublishSubscriber.java
 */
package com.rift.coad.type.subscriber;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The interface for the publish subscriber object.
 * 
 * @author brett chaldecott
 */
public interface PublishSubscriber extends Remote {
    
    /**
     * The service name
     */
    public final static String SERVICE_NAME = "publish_subscriber";
    
    
    /**
     * This method will be called when ever new RDF is published.
     */
    public void rdfPublished() throws RemoteException;
}
