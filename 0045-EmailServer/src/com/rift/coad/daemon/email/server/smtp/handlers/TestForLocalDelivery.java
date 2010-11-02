/*
 * Email Server: The email server interface
 * Copyright (C) 2008  Rift IT Contracting
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
 * TestForLocalDelivery.java
 */

// package path
package com.rift.coad.daemon.email.server.smtp.handlers;

// java imports
import java.rmi.RemoteException;
import java.util.List;

// log4j imports
import org.apache.log4j.Logger;

// email server imports
import com.rift.coad.daemon.email.types.Address;
import com.rift.coad.daemon.email.smtp.Handler;
import com.rift.coad.daemon.email.smtp.MessageInfo;
import com.rift.coad.daemon.email.smtp.SMTPException;
import com.rift.coad.daemon.email.server.ServerConfig;
import com.rift.coad.daemon.email.server.config.ServerDomain;

/**
 * This object tests to see if the mail should be delivered locally or to a
 * remote destination.
 *
 * @author brett chaldecott 
 */
public class TestForLocalDelivery implements Handler {
    
    // private member variables
    private Logger log = Logger.getLogger(TestForLocalDelivery.class);
    
    /**
     * Creates a new instance of TestForLocalDelivery 
     */
    public TestForLocalDelivery() {
    }
    
    
    /**
     * This method processes the message delivery request.
     *
     * @return The message information to return.
     * @param info The information to utilize for this request.
     * @exception SMTPException
     * @exception RemoteException
     */
    public MessageInfo process(MessageInfo info) throws SMTPException, 
            RemoteException {
        try {
            List targets = info.getRCPTs();
            if (targets.size() != 1) {
                throw new SMTPException("The target is invalid must be a " +
                        "single addres");
            }
            Address address = (Address)targets.get(0);
            if (ServerConfig.getInstance().getDomain().checkForLocalDomain(
                    address.getDomain())) {
                info.setType(1);
            } else {
                info.setType(2);
            }
            return info;
        } catch (SMTPException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to process the request because : " + 
                    ex.getMessage(),ex);
            throw new SMTPException(
                    "Failed to process the request because : " + 
                    ex.getMessage(),ex);
        }
    }

}
