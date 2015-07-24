/*
 * EMailServer: The email server implementation.
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
 * ImapFetchBodyHandler.java
 */

package com.rift.coad.daemon.email.server.imap;

// java imports
import java.io.Writer;
import java.util.Enumeration;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.InputStreamReader;

// the mail imports
import javax.mail.Flags;
import javax.mail.Multipart;
import javax.mail.Header;

// log4j imports
import org.apache.log4j.Logger;

// mail dir
import net.ukrpost.storage.maildir.MaildirMessage;
import com.rift.coad.daemon.email.server.ServerException;

/**
 * This object is responsible for writting out the body of an object.
 *
 * @author brett chaldecott
 */
public class ImapFetchBodyHandler {
    
    /**
     * This defines the email sections to retrieve.
     */
    public class EmailSection {
        private List parts = null;
        private String part = null;
        private List fields = null;
        
        /**
         * This method parses the section string
         *
         * @param section The section string to parse.
         */
        public EmailSection(String section) {
            if (section.contains("HEADER.FIELDS.NOT")) {
                part = "HEADER.FIELDS.NOT";
                section = section.replaceAll("HEADER.FIELDS.NOT","");
            } else if (section.contains("HEADER.FIELDS")) {
                part = "HEADER.FIELDS";
                section = section.replaceAll("HEADER.FIELDS","");
            } else if (section.contains("HEADER")) {
                part = "HEADER";
                section = section.replaceAll("HEADER","");
            } else if (section.contains("MIME")) {
                part = "MIME";
                section = section.replaceAll("MIME","");
            } else if (section.contains("TEXT")) {
                part = "TEXT";
                section = section.replaceAll("TEXT","");
            }
            
            
            // retrieve the fields
            int begin = section.indexOf("(");
            int end = section.indexOf(")");
            if ((begin != -1) && (end != -1)) {
                String fieldStr = section.substring(begin, end);
                String[] fields = fieldStr.substring(1,fieldStr.length()).
                        split(" ");
                this.fields = new ArrayList();
                for (int index = 0; index < fields.length; index++) {
                    this.fields.add(fields[index].toUpperCase());
                }
                String beginStr = section.substring(0,begin);
                String endStr = section.substring(end+1,section.length());
                section = beginStr + endStr;
            }
            section = section.trim();
            if (!section.equals("")) {
                String[] partList = section.split("[.]");
                parts = new ArrayList();
                for (int index = 0; index < partList.length; index++) {
                    String part = partList[index];
                    if (part.equals("")) {
                        continue;
                    }
                    parts.add(new Integer(Integer.parseInt(part) - 1));
                }
            }
        }
        
        
        /**
         * Retrieve the list of parts to retrieve
         */
        public List getParts() {
            return parts;
        }
        
        
        /**
         * Retrieve the part name
         */
        public String getPart() {
            return part;
        }
        
        
        /**
         * This method returns the fields.
         */
        public List getFields() {
            return fields;
        }
        
    }
    
    
    // log object
    private static Logger log = Logger.getLogger(ImapFetchBodyHandler.class);
    
    // private member variables
    private Writer out = null;
    private ImapFetchHandler.Body bodyInfo = null;
    private MaildirMessage maildirMessage = null;
    private int start = 0;
    private int end = -1;
    
    /**
     * Creates a new instance of ImapFetchBodyHandler
     *
     * @param out The out put stream writer.
     * @param bodyInfo The information about the body.
     * @param maildirMessage The mail dir message object.
     */
    public ImapFetchBodyHandler(Writer out, ImapFetchHandler.Body bodyInfo,
            MaildirMessage maildirMessage) {
        this.out = out;
        this.bodyInfo = bodyInfo;
        this.maildirMessage = maildirMessage;
        start = bodyInfo.getStart();
        end = bodyInfo.getEnd();
        
    }
    
    
    /**
     * This method is called to process the body request.
     */
    public void printBody() throws ServerException {
        try {
            log.debug("Write out the body structure");
            
            String body = null;
            EmailSection section = null;
            if (bodyInfo.getBodyType().equals("RFC822")) {
                body = "RFC822";
                section = new EmailSection("");
            } else if (bodyInfo.getBodyType().equals("RFC822.HEADER")) {
                body = "RFC822.HEADER";
                section = new EmailSection("HEADER");
            } else if (bodyInfo.getBodyType().equals("RFC822.TEXT")) {
                body = "RFC822.TEXT";
                section = new EmailSection("TEXT");
            } else {
                body = String.format("BODY[%s]",
                        this.bodyInfo.getSection());
                section = new EmailSection(bodyInfo.getSection());
            }
            out.write(body);
            
            // write out the entire message
            if (section.getPart() == null) {
                processBody(section);
            } else if (section.getPart().contains("HEADER")) {
                processHeader(section);
            } else if (section.getPart().contains("MIME")) {
                processMime(section);
            } else if (section.getPart().contains("TEXT")) {
                processText(section);
            }
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to process the body message : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to process the body message : " +
                    ex.getMessage(),ex);
        } finally {
            try {
                out.flush();
            } catch (Exception ex) {
                // ignore
            }
        }
    }
    
    
    /**
     * This method processes the body.
     *
     * @param section The section with its parts
     * @exception ServerException
     */
    private void processBody(EmailSection section) throws ServerException {
        try {
            StringBuffer contentBuff = new StringBuffer();
            if (section.parts == null) {
                ImapPart part = new ImapPart(maildirMessage);
                contentBuff.append(part.getContent());
            } else {
                Object content = this.maildirMessage.getContent();
                if (content instanceof String) {
                    ImapPart imapPart = new ImapPart(
                            maildirMessage.getRawInputStream());
                    contentBuff.append(imapPart.getContent());
                } else {
                    javax.mail.internet.MimeBodyPart part = getPart(
                            (Multipart)content,section.getParts());
                    ImapPart imapPart = new ImapPart(part.getRawInputStream());
                    contentBuff.append(imapPart.getContent());
                }
            }
            
            // calculate the number of octets
            String content = retrievePart(contentBuff.toString());
            out.write(String.format(" {%d}\r\n%s",content.length(),
                    content));
        } catch (java.lang.IndexOutOfBoundsException ex) {
            log.error("Invalid part number for message");
            throw new ServerException(
                    "Invalid part number for message");
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to print out the entire body : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to print out the entire body : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method processes the mime information.
     *
     * @param section The section information for this request.
     * @exception ServerException
     */
    private void processMime(EmailSection section) throws ServerException {
        try {
            Object content = this.maildirMessage.getContent();
            if (!(content instanceof Multipart)) {
                log.error("This is not a multi part message");
                throw new ServerException(
                        "This is not a multi part message");
            }
            
            // retrieve the body part
            if (section.getParts() != null) {
                javax.mail.internet.MimeBodyPart part = getPart(
                        (Multipart)content,section.getParts());
                if (!(part.getContent() instanceof
                        javax.mail.internet.MimeMessage)){
                    log.error("The embedded section is not a message cannot get " +
                            "mime information for it");
                    throw new ServerException(
                            "The embedded section is not a message cannot get " +
                            "mime information for it");
                }
                javax.mail.internet.MimeMessage subMessage =
                        (javax.mail.internet.MimeMessage)part.getContent();
                content = this.maildirMessage.getContent();
                if (!(content instanceof Multipart)) {
                    log.error("This is not a multi part message");
                    throw new ServerException(
                            "This is not a multi part message");
                }
            }
            
            Multipart multiPart = (Multipart)content;
            StringBuffer result = new StringBuffer();
            
            for (int index = 0; index < multiPart.getCount(); index++) {
                log.debug("Get Body part : " + index);
                javax.mail.internet.MimeBodyPart part =
                        (javax.mail.internet.MimeBodyPart)multiPart.getBodyPart(
                        index);
                Enumeration enumer = part.getAllHeaderLines();
                while (enumer.hasMoreElements()) {
                    result.append(enumer.nextElement().toString()).
                            append("\r\n");
                }
                result.append("\r\n");
            }
            
            // print out the content
            String contentStr = retrievePart(result.toString());
            out.write(String.format(" {%d}\r\n%s",contentStr.length(),
                    contentStr));
            
        } catch (java.lang.IndexOutOfBoundsException ex) {
            log.error("Invalid part number for message");
            throw new ServerException(
                    "Invalid part number for message");
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to process the MIME request : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to process the MIME request : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method processes the header information.
     *
     * @param section The section information for this request.
     * @exception ServerException
     */
    private void processHeader(EmailSection section) throws ServerException {
        try {
            // retrieve the body part
            javax.mail.internet.MimeMessage message = this.maildirMessage;
            if (section.getParts() != null) {
                Object content = this.maildirMessage.getContent();
                if (!(content instanceof Multipart)) {
                    log.error("This is not a multi part message");
                    throw new ServerException(
                            "This is not a multi part message");
                }
                javax.mail.internet.MimeBodyPart part = getPart(
                        (Multipart)content,section.getParts());
                if (!(part.getContent() instanceof
                        javax.mail.internet.MimeMessage)){
                    log.error("The embedded section is not a message cannot get " +
                            "header information for it");
                    throw new ServerException(
                            "The embedded section is not a message cannot get " +
                            "header information for it");
                }
                message = (javax.mail.internet.MimeMessage)part.getContent();
            }
            
            
            StringBuffer contentBuff = new StringBuffer();
            if (section.getPart().equals("HEADER")) {
                Enumeration enumer = message.getAllHeaderLines();
                for (int index = 0; enumer.hasMoreElements(); index++){
                    String header = (String)enumer.nextElement();
                    contentBuff.append(header).append("\r\n");
                }
            } else if (section.getPart().equals("HEADER.FIELDS")) {
                if (section.getFields() == null) {
                    log.error("Must supply field information for this request.");
                    throw new ServerException(
                            "Must supply field information for this request.");
                }
                int pos = 0;
                for (Iterator iter = section.getFields().iterator();
                iter.hasNext();) {
                    String field = (String)iter.next();
                    String[] headers = this.maildirMessage.getHeader(field);
                    if (headers == null) {
                        // this is not an error there are just no headers
                        continue;
                    }
                    for (int index = 0; index < headers.length; index++) {
                        contentBuff.append(field).append(":").
                                append(headers[index]).append("\r\n");
                    }
                }
            } else if (section.getPart().equals("HEADER.FIELDS.NOT")) {
                if (section.getFields() == null) {
                    log.error("Must supply field information for this request.");
                    throw new ServerException(
                            "Must supply field information for this request.");
                }
                int pos = 0;
                Enumeration enumer = this.maildirMessage.getAllHeaders();
                for (int index = 0; enumer.hasMoreElements(); index++){
                    Header header = (Header)enumer.nextElement();
                    if (!section.getFields().contains(header.getName().
                            toUpperCase())) {
                        contentBuff.append(header.getName()).append(":").
                                append(header.getValue()).append("\r\n");
                    }
                    pos++;
                }
            }
            // print out the content
            String contentStr = retrievePart(contentBuff.toString());
            out.write(String.format(" {%d}\r\n%s",contentStr.length(),
                    contentStr));
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to process the HEADER request : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to process the HEADER request : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method processes the text information.
     *
     * @param section The section information for this request.
     * @exception ServerException
     */
    private void processText(EmailSection section) throws ServerException {
        try {
            // retrieve the body part
            javax.mail.internet.MimeMessage message = this.maildirMessage;
            if (section.getParts() != null) {
                Object content = this.maildirMessage.getContent();
                if (!(content instanceof Multipart)) {
                    log.error("This is not a multi part message");
                    throw new ServerException(
                            "This is not a multi part message");
                }
                javax.mail.internet.MimeBodyPart part = getPart(
                        (Multipart)content,section.getParts());
                if (!(part.getContent() instanceof
                        javax.mail.internet.MimeMessage)){
                    log.error("The embedded section is not a message cannot get " +
                            "header information for it");
                    throw new ServerException(
                            "The embedded section is not a message cannot get " +
                            "header information for it");
                }
                message = (javax.mail.internet.MimeMessage)part.getContent();
            }
            
            Object content = message.getContent();
            StringBuffer result = new StringBuffer();
            
            // retrieve the text
            ImapPart imapPart = new ImapPart(
                    this.maildirMessage.getRawInputStream());
            result.append(imapPart.getContent()).append("\r\n");

            // print out the content
            String contentStr = retrievePart(result.toString());
            out.write(String.format(" {%d}\r\n%s",contentStr.length(),
                    contentStr));
            
        } catch (java.lang.IndexOutOfBoundsException ex) {
            log.error("Invalid part number for message");
            throw new ServerException(
                    "Invalid part number for message");
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to process the MIME request : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to process the MIME request : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method retrieves the part of the message
     */
    private String retrievePart(String content) {
        // calculate the number of octets
        int octets = content.length();
        if (end != -1) {
            octets = end > octets ? octets : end;
            content = content.substring(0,end);
        }
        if (start != 0) {
            content = content.substring(start,content.length());
        }
        if (!content.endsWith("\r\n")) {
            content += "\r\n";
        }
        return content;
    }
    
    
    /**
     * This method returns the part of the message specified in the email
     * section object.
     *
     * @return The mime body part of the message.
     * @param multiPart The part of the message.
     * @param section The section object message.
     */
    private javax.mail.internet.MimeBodyPart getPart(Multipart multiPart,
            List section) throws ServerException {
        try {
            javax.mail.internet.MimeBodyPart part = null;
            for (int pos = 0; pos < section.size(); pos++) {
                Integer index = (Integer)section.get(pos);
                part = (javax.mail.internet.MimeBodyPart)multiPart.getBodyPart(
                        index.intValue());
                Object messageContent = part.getContent();
                if (messageContent instanceof javax.mail.internet.MimeMessage) {
                    javax.mail.internet.MimeMessage mimeMessage =
                            (javax.mail.internet.MimeMessage)messageContent;
                    if (mimeMessage.getContent() instanceof Multipart) {
                        multiPart = (Multipart)mimeMessage.getContent();
                    }
                } else if (part.getContent() instanceof Multipart) {
                    return getPart((Multipart)part.getContent(),
                            section.subList(pos + 1,section.size()));
                }
            }
            return part;
        } catch (Exception ex) {
            log.error("Failed to retrieve the part of the message to process :"
                    + ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to retrieve the part of the message to process :"
                    + ex.getMessage(),ex);
        }
    }
}
