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
 * SMTPProcessingList.java
 */


// package path
package com.rift.coad.daemon.email.server.smtp;

// java imports
import java.util.Map;
import java.util.Date;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;


// coadunation imports
import com.rift.coad.util.transaction.UserTransactionWrapper;
import com.rift.coad.util.transaction.CoadunationHashMap;

// email server imports
import com.rift.coad.daemon.email.smtp.SMTPException;

/**
 * This object contains a list of all the messages that are being actively
 * processed by the Coadunation Message service.
 *
 * @author brett
 */
public class SMTPProcessingList {
    
    
    
    // private member variables
    private Map smtpProcessingList = new CoadunationHashMap();
    
    
    /** 
     * Creates a new instance of SMTPProcessingList
     */
    public SMTPProcessingList() {
    }
    
    
    /**
     * This method adds a new message to the queue.
     *
     * @param message The message to add to the queue.
     */
    public void addMessage(SMTPServerMessage message) {
        smtpProcessingList.put(message.getMessageServiceId(),message);
    }
    
    
    /**
     * This method returns a reference to the requested message or null.
     *
     * @return A reference to the request message or null.
     * @param messageServiceId The id of the message to retrieve.
     */
    public SMTPServerMessage getMessage(String messageServiceId) {
        return (SMTPServerMessage)smtpProcessingList.get(messageServiceId);
    }
    
    
    /**
     * This method removes from the processing list the message with the
     * matching message service id.
     *
     * @param messageServiceId The id of the message service.
     */
    public void removeMessage(String messageServiceId) {
        smtpProcessingList.remove(messageServiceId);
    }
    
    
    /**
     * This object is responsible for checking the date.
     *
     * @param timeout The timeout for the call.
     * @exception SMTPException
     */
    public void checkList(long timeout) throws SMTPException {
        Date startTime = new Date();
        while (true) {
            UserTransactionWrapper transaction = null;
            try {
                
                transaction = new UserTransactionWrapper();
                transaction.begin();
                if (smtpProcessingList.size()  == 0) {
                    return;
                }
            } catch (Throwable ex) {
                throw new SMTPException("Failed to check the list : " + 
                        ex.getMessage(),ex);
            } finally {
                if (transaction != null) {
                    try {
                        transaction.release();
                    } catch (Exception ex) {
                        // ignore
                    }
                }
            }
            try {
                
                // synchronize on this object
                synchronized (this) {
                    Date currentTime = new Date();
                    if ((startTime.getTime() + timeout) < currentTime.getTime()) {
                        return;
                    }
                    wait((startTime.getTime() + timeout) - 
                            currentTime.getTime());
                }
            } catch (Throwable ex) {
                throw new SMTPException("Failed to check the list : " + 
                        ex.getMessage(),ex);
            }  
        }
    }
    
    
    /**
     * This method returns the list within.
     *
     * @return The object containing the map.
     */
    public Map getList() {
        return this.smtpProcessingList;
    }
}
