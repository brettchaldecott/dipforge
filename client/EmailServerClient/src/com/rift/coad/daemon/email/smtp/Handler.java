/*
 * Email Server: The email server interface
 * Copyright (C) 2008  2015 Burntjam
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
 * Handler.java
 */

// package
package com.rift.coad.daemon.email.smtp;

// java import
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface will be implemented by objects handling SMTP requests.
 *
 * @author brett chaldecott
 */
public interface Handler extends Remote {
    /**
     * This message is called to process a message.
     *
     * @return The message information.
     * @param info The message to process.
     * @exception SMTPException
     * @exception RemoteException
     */
    public MessageInfo process(MessageInfo info) throws SMTPException, 
            RemoteException;
}
