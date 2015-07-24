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
 * DeliverLocal.java
 */

// package path
package com.rift.coad.daemon.email.server.smtp.handlers;

// java imports
import java.rmi.RemoteException;
import java.util.List;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.File;
import java.io.ByteArrayInputStream;

// log4j imports
import org.apache.log4j.Logger;

// java mail api
import javax.mail.*;
import javax.mail.internet.*;

// mail dir
import net.ukrpost.storage.maildir.MaildirStore;
import net.ukrpost.storage.maildir.MaildirQuota;

// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;

// email server imports
import com.rift.coad.daemon.email.smtp.Handler;
import com.rift.coad.daemon.email.smtp.MessageInfo;
import com.rift.coad.daemon.email.smtp.SMTPException;
import com.rift.coad.daemon.email.smtp.Server;
import com.rift.coad.daemon.email.smtp.Header;
import com.rift.coad.daemon.email.types.Address;
import com.rift.coad.daemon.email.server.ServerConfig;
import com.rift.coad.daemon.email.server.config.ServerForward;
import com.rift.coad.daemon.email.server.config.ServerEmailbox;
import com.rift.coad.daemon.email.server.config.ServerForward;
import com.rift.coad.daemon.email.server.db.Emailbox;

/**
 * This object handles the local delivery of messages.
 *
 * @author brett chaldecott
 */
public class DeliverLocal implements Handler {
    
    // class constants
    private final static String SMTP_SERVER_JNDI = "smtp_server_jndi";
    private final static String DEFAULT_SMTP_SERVER_JNDI =
            "java:comp/env/bean/email/SMTPServer";
    
    // private member variables
    private Logger log = Logger.getLogger(TestForLocalDelivery.class);
    private Server smtpServer = null;
    private Configuration config = null;
    
    
    /**
     * Creates a new instance of DeliverLocal
     */
    public DeliverLocal() throws SMTPException {
        try {
            config = ConfigurationFactory.getInstance().
                    getConfig(DeliverLocal.class);
    
        } catch (Throwable ex) {
            log.error("Failed to retrieve the configuration : " +
                    ex.getMessage(),ex);
            throw new SMTPException(
                    "Failed to retrieve the configuration : " +
                    ex.getMessage(),ex);
        }
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
            Address rcpt = (Address)info.getRCPTs().get(0);
            Server server = getServer();
            com.rift.coad.daemon.email.smtp.Message message =
                    server.getMessage(info.getId());
            
            
            // check for a forward
            List addresses =
                    ServerConfig.getInstance().getForward().getForward(
                    rcpt.getDomain(),rcpt.getLocalPart());
            if (addresses.size() != 0) {
                message.setRCPTs(new ArrayList());
                for (int index = 0; index < addresses.size(); index++) {
                    message.addRCPT(new com.rift.coad.daemon.email.types.Address(
                            (String)addresses.get(index)));
                }
                server.sendMessage(message);
                return info;
            }
            
            // check for an alias
            String address = ServerConfig.getInstance().getAlias().getEmailbox(
                    rcpt.getAddress());
            if (address != null) {
                message.setRCPTs(new ArrayList());
                message.addRCPT(new com.rift.coad.daemon.email.types.Address(
                        address));
                server.sendMessage(message);
                return info;
            }
            
            // retrieve the email address
            Emailbox emailbox = ServerConfig.getInstance().getEmailbox().
                    getEmail(rcpt.getAddress());
            if (emailbox == null) {
                String dropBox =
                        ServerConfig.getInstance().getDomain().getDomainDropBox(
                        rcpt.getDomain());
                if (dropBox != null) {
                    message.setRCPTs(new ArrayList());
                    message.addRCPT(new com.rift.coad.daemon.email.types.Address(
                            address));
                    server.sendMessage(message);
                    return info;
                } else {
                    message.setRCPTs(message.getFrom());
                    message.setData("Coadunation Email Server: " +
                            "The address [" + rcpt.getAddress() + "]" +
                            " is not valid");
                    server.sendMessage(message);
                    return info;
                }
            }
            
            // setup the path
            File path = new File(ServerConfig.getInstance().getMailDir() + 
                    File.separator + emailbox.getPath());
            URLName url = new URLName(path.toURL().toString().replace("file:","maildir:"));
            
            // construct a byte input stream that can be used by the mime
            // message object
            StringBuffer completMessage = new StringBuffer();
            List headers = message.getHeaders();
            for (int index = 0; index < headers.size(); index++) {
                completMessage.append(headers.get(index).toString()).
                        append("\r\n");
            }
            completMessage.append("\r\n").
                    append(message.getData()).append("\r\n.\r\n");
            
            ByteArrayInputStream in = new ByteArrayInputStream(
                    completMessage.toString().getBytes());
            
            // setup the message
            MimeMessage mm = new MimeMessage((Session)null,in);
            
            // write the message to the mail store
            java.util.Properties p = new java.util.Properties();
            p.put("mail.store.maildir.autocreatedir", "true");
            Session session = Session.getDefaultInstance(p);
            MaildirStore store =
                    (MaildirStore)session.getStore(url);
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);
            inbox.appendMessages(new Message[]{mm});
            inbox.close(false);
            // processing complete
            return info;
        } catch (Throwable ex) {
            log.error("Failed to process the local delivery request : " +
                    ex.getMessage(),ex);
            throw new SMTPException(
                    "Failed to process the local delivery request : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns a reference to the server.
     *
     * @return The reference to the SMTP server.
     * @exception SMTPException
     */
    private Server getServer() throws SMTPException {
        try {
            if (smtpServer == null) {
                Context context = new InitialContext();
                smtpServer = (Server)context.lookup(
                        config.getString(SMTP_SERVER_JNDI,
                        DEFAULT_SMTP_SERVER_JNDI));
            }
            return smtpServer;
        } catch (Throwable ex) {
            log.error("Failed to make a connection to the smtp server : " +
                    ex.getMessage(),ex);
            throw new SMTPException(
                    "Failed to make a connection to the smtp server : " +
                    ex.getMessage(),ex);
        }
    }
}
