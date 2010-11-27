/*
 * EMailServer: The email server implementation.
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
 * SmtpRequest.java
 */

// the package path
package com.rift.coad.daemon.email.server.imap;

// java imports
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.Enumeration;

// log4j imports
import org.apache.log4j.Logger;

// mail dir imports
import net.ukrpost.storage.maildir.MaildirMessage;

// email imports
import com.rift.coad.daemon.email.server.ServerException;

/**
 * This object is responsible for storing the content of the part and formatting
 * it appropriatly.
 *
 * @author brett chaldecott
 */
public class ImapPart {
    
    // class singletons
    private static Logger log = Logger.getLogger(ImapPart.class);
    
    // private member variables
    private String content = null;
    private int size = 0;
    private int lines = 0;
    
    
    /**
     * Creates a new instance of ImapPart
     */
    public ImapPart(MaildirMessage maildirMessage) throws ServerException {
        InputStreamReader inStream = null;
        try {
            Enumeration lines = maildirMessage.getAllHeaderLines();
            StringBuffer email = new StringBuffer();
            while (lines.hasMoreElements()) {
                email.append((String)lines.nextElement()).append("\r\n");
                this.lines++;
            }
            email.append("\r\n");
            this.lines++;
            
            inStream = new InputStreamReader(
                    maildirMessage.getRawInputStream());
            BufferedReader reader = new BufferedReader(inStream);
            String line = null;
            while ((line = reader.readLine()) != null){
                if (line.equals(".")) {
                    break;
                }
                email.append(line).append("\r\n");
                this.lines++;
            }
            this.content = email.toString();
            size = this.content.length();
        } catch (Exception ex) {
            log.error("Failed to get the line count : " + ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to get the line count : " + ex.getMessage(),ex);
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (Exception ex) {
                    // ingore
                }
            }
        }
    }
    
    
    /**
     * Creates a new instance of ImapPart
     */
    public ImapPart(InputStream content) throws ServerException {
        InputStreamReader inStream = null;
        try {
            inStream = new InputStreamReader(
                    content);
            BufferedReader reader = new BufferedReader(inStream);
            String line = null;
            StringBuffer buffer = new StringBuffer();
            while ((line = reader.readLine()) != null){
                if (line.equals(".")) {
                    break;
                }
                buffer.append(line).append("\r\n");
                this.lines++;
            }
            this.content = buffer.toString();
            size = this.content.length();
        } catch (Exception ex) {
            log.error("Failed to get the line count : " + ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to get the line count : " + ex.getMessage(),ex);
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (Exception ex) {
                    // ingore
                }
            }
        }
    }
    
    
    /**
     * This method counts the number of lines in a message
     */
    public int getLines() {
        return lines;
    }
    
    
    /**
     * This method returns the size of the object.
     */
    public int getSize() {
        return size;
    }
    
    
    /**
     * This method returns the content.
     */
    public String getContent() {
        return content;
    }
}
