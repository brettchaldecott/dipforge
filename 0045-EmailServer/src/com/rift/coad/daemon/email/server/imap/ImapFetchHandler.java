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
 * ImapUtilsTest.java
 */

// package path
package com.rift.coad.daemon.email.server.imap;

// java imports
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Enumeration;
import java.util.Iterator;
import java.io.Writer;
import java.io.BufferedReader;
import java.io.InputStreamReader;

// java mail api
import javax.mail.*;
import javax.mail.internet.*;
import com.sun.mail.imap.protocol.FLAGS;
import javax.mail.search.*;

// log4j imports
import org.apache.log4j.Logger;

// mail dir
import net.ukrpost.storage.maildir.MaildirMessage;

// email imports
import com.rift.coad.daemon.email.smtp.Header;
import com.rift.coad.daemon.email.server.ServerException;


/**
 * This object is responsible for handling the fetch command request.
 *
 * @author brett chaldecott
 */
public class ImapFetchHandler {
    
    /**
     * This object stores the body request information.
     */
    public class Body {
        private String bodyType = null;
        private String section = null;
        private Integer start = new Integer(0);
        private Integer end = new Integer(-1);
        
        /**
         * The section that states
         */
        public Body(String body, StringTokenizer token) {
            String fullBody = processBrackets(body, token);
            fetchSection(fullBody);
            setPartial(fullBody);
        }
        
        
        /**
         * This method retrieves the section name
         */
        private void fetchSection(String body) {
            this.bodyType = body;
            int begin = body.indexOf("[");
            int end = body.indexOf("]");
            section = null;
            if (begin != -1 && end != -1) {
                this.bodyType = body.substring(0,begin);
                section = body.substring(begin +1, end);
            }
        }
        
        
        /**
         * This method will set the partial values.
         *
         * @param body The body to set.
         */
        private void setPartial(String body) {
            int begin = body.indexOf("<");
            int end = body.indexOf(">");
            if (begin != -1 && end != -1) {
                String partial = body.substring(begin + 1, end);
                String[] parts = partial.split("[.]");
                if (parts.length >= 1) {
                    start = new Integer(Integer.parseInt(parts[0]));
                }
                if (parts.length >= 2) {
                    // calculate the end position as from the start pos
                    this.end = new Integer(start.intValue() +
                            Integer.parseInt(parts[1]));
                }
            }
            log.debug("Start [" + start + "] and end [" + this.end + "]");
        }
        
        /**
         * process the brackets on a request
         */
        private String processBrackets(String body, StringTokenizer token) {
            if (!body.contains("[") || (body.contains("[") && body.contains("]"))) {
                return body;
            }
            while (token.hasMoreTokens()) {
                body += " " + token.nextToken();
                if (body.contains("[") && body.contains("]")) {
                    return body;
                }
            }
            return body;
        }
        
        
        /**
         * This method returns the body type.
         */
        public String getBodyType() {
            return bodyType;
        }
        
        /**
         * This method returns the section.
         */
        public String getSection() {
            return section;
        }
        
        
        /**
         * This method returns the starting octet.
         */
        public Integer getStart() {
            return start;
        }
        
        
        /**
         * This method returns the ending octet.
         */
        public Integer getEnd() {
            return end;
        }
        
        
    }
    
    // log reference
    private static Logger log = Logger.getLogger(ImapFetchHandler.class);
    
    // private member variables
    private boolean select = true;
    private Writer out = null;
    private List commands = new ArrayList();
    
    
    /**
     * Creates a new instance of ImapMessageHandler
     */
    public ImapFetchHandler(boolean select, Writer out, StringTokenizer token,
            boolean uid) {
        this.select = select;
        this.out = out;
        while (token.hasMoreTokens()) {
            String tokenValue = token.nextToken();
            // remove any brackets from the commands
            if (tokenValue.indexOf("(") == 0) {
                tokenValue = tokenValue.replaceFirst("[(]","");
            }
            if (tokenValue.endsWith(")")) {
                tokenValue = tokenValue.substring(0,tokenValue.length() -1);
            }
            
            
            if (tokenValue.equalsIgnoreCase("ALL")) {
                this.commands.add("FLAGS");
                this.commands.add("INTERNALDATE");
                this.commands.add("ENVELOPE");
                this.commands.add("RFC822.SIZE");
            } else if (tokenValue.equals("BODY")) {
                this.commands.add("BODY");
            } else if (tokenValue.matches(
                    "BODY[\\[]+[\\p{Punct}\\p{Space}\\p{Alnum}]*")) {
                log.debug("This is a body request");
                this.commands.add(new Body(tokenValue,token));
            } else if (tokenValue.matches(
                    "BODY[.]+PEEK[\\[]+[\\p{Punct}\\p{Space}\\p{Alnum}]*")) {
                log.debug("This is a peek");
                this.commands.add(new Body(tokenValue,token));
            } else if (tokenValue.equalsIgnoreCase("BODYSTRUCTURE")) {
                // body and body structure result in the same response on the
                this.commands.add("BODYSTRUCTURE");
            } else if (tokenValue.equalsIgnoreCase("ENVELOPE")) {
                this.commands.add("ENVELOPE");
            } else if (tokenValue.equalsIgnoreCase("FAST")) {
                this.commands.add("FLAGS");
                this.commands.add("INTERNALDATE");
                this.commands.add("RFC822.SIZE");
            } else if (tokenValue.equalsIgnoreCase("FLAGS")) {
                this.commands.add("FLAGS");
            } else if (tokenValue.equalsIgnoreCase("FULL")) {
                this.commands.add("FLAGS");
                this.commands.add("INTERNALDATE");
                this.commands.add("ENVELOPE");
                this.commands.add("BODY");
                this.commands.add("RFC822.SIZE");
            } else if (tokenValue.equalsIgnoreCase("INTERNALDATE")) {
                this.commands.add("INTERNALDATE");
            } else if (tokenValue.equalsIgnoreCase("RFC822")) {
                this.commands.add(new Body("RFC822",token));
            } else if (tokenValue.equalsIgnoreCase("RFC822.HEADER")) {
                this.commands.add(new Body("RFC822.HEADER[HEADER]",token));
            } else if (tokenValue.equalsIgnoreCase("RFC822.SIZE")) {
                this.commands.add("RFC822.SIZE");
            } else if (tokenValue.equalsIgnoreCase("RFC822.TEXT")) {
                this.commands.add(new Body("RFC822.TEXT[TEXT]",token));
            } else if (tokenValue.equalsIgnoreCase("UID")) {
                this.commands.add("UID");
            }
        }
        
        // if uid add to end of commands list
        if (uid && (!this.commands.contains("UID"))) {
            List commands = new ArrayList();
            boolean found = false;
            for (Iterator iter = this.commands.iterator(); iter.hasNext();) {
                Object entry = iter.next();
                if (!found && (entry instanceof Body)) {
                    commands.add("UID");
                    found = true;
                }
                commands.add(entry);
            }
            this.commands = commands;
            if (!found) {
                this.commands.add("UID");
            }
        }
        
    }
    
    
    /**
     * This method process the message request.
     */
    public void processMessage(
            net.ukrpost.storage.maildir.MaildirFolder maildirFolder,
            Message message) throws ServerException {
        try {
            String sep = "";
            out.write("* " + message.getMessageNumber() + " FETCH (");
            
            MaildirMessage maildirMessage = (MaildirMessage)message;
            Object content = message.getContent();
            maildirMessage.getMessageID();
            
            for (Iterator iter = this.commands.iterator(); iter.hasNext();) {
                Object command = iter.next();
                if (command instanceof Body) {
                    Body body = (Body)command;
                    if (body.getBodyType().equals("BODY") ||
                            body.getBodyType().equals("RFC822") ||
                            body.getBodyType().equals("RFC822.HEADER") ||
                            body.getBodyType().equals("RFC822.TEXT")) {
                        log.debug("Call process body");
                        out.write(sep);
                        this.processBody(body,maildirFolder,maildirMessage);
                        // the seen flag is only implicidly set if the
                        // box is selected
                        if (select) {
                            maildirMessage.setFlag(Flags.Flag.SEEN,true);
                        }
                    } else if (body.getBodyType().equals("BODY.PEEK")) {
                        log.debug("Call process body");
                        out.write(sep);
                        this.processBodyPeek(body,maildirFolder,maildirMessage);
                    }
                    sep = " ";
                    continue;
                }
                String commandStr = (String)command;
                if (commandStr.equals("FLAGS")) {
                    Flags flags = message.getFlags();
                    Flags.Flag[] flagList = flags.getSystemFlags();
                    out.write(sep + "FLAGS (");
                    for (int index = 0; index < flagList.length; index++) {
                        out.write(ImapUtils.getFlag(flagList[index]));
                    }
                    out.write(")");
                } else if (commandStr.equals("INTERNALDATE")) {
                    out.write(String.format("%sINTERNALDATE \"%s\"",sep,
                            ImapUtils.getDateString(
                            maildirMessage.getReceivedDate())));
                } else  if (commandStr.equals("RFC822.SIZE")) {
                    // ImapPart imapPart = new ImapPart(maildirMessage);
                    // out.write(String.format("%sRFC822.SIZE %d",
                    //         sep, imapPart.getSize()));
                    // as this takes to long to return as it is loading
                    // the entire message into memory this will not result
                    // in an accurate size as the maildirMessage.getSize method
                    // used instead does not include "\n\r" on unix but only
                    // "\n" therefore one character less per line.
                    log.debug("Size : " + maildirMessage.getSize());
                    out.write(String.format("%sRFC822.SIZE %d",
                            sep, maildirMessage.getSize()));
                } else if (commandStr.equals("UID")) {
                    out.write(String.format(
                            "%sUID %d",sep,
                            maildirFolder.getUID(message)));
                } else if (commandStr.equals("ENVELOPE")) {
                    String from = "unknown@unknown.com";
                    try {
                        from = processAddresses(maildirMessage.getFrom());
                    } catch (Exception ex) {
                        // ignore
                        log.warn("The from address is invalid defaulting to " +
                                "'unknown@unknown.com' : " + ex.getMessage(),ex);
                    }
                    String sender = from;
                    if (maildirMessage.getSender() != null) {
                        sender = "(" +
                                processAddress(maildirMessage.getSender()) + ")";
                    }
                    String replyTo = processAddresses(maildirMessage.getReplyTo());
                    if (replyTo.equals("NIL")) {
                        replyTo = from;
                    }
                    String to = processAddresses(maildirMessage.getRecipients(
                            Message.RecipientType.TO));
                    String cc = processAddresses(maildirMessage.getRecipients(
                            Message.RecipientType.CC));
                    String bcc = processAddresses(maildirMessage.getRecipients(
                            Message.RecipientType.BCC));
                    out.write(String.format(
                            "%sENVELOPE (\"%s\" \"%s\" %s %s %s %s %s %s NIL \"%s\")",
                            sep,
                            ImapUtils.getDateString(
                            maildirMessage.getReceivedDate()),
                            escapeString(maildirMessage.getSubject()),
                            from,sender,replyTo,to, cc, bcc,
                            maildirMessage.getMessageID()));
                    
                } else if (commandStr.equals("BODY") ||
                        commandStr.equals("BODYSTRUCTURE")) {
                    
                    // ass the rfc states that the server must not give the extension
                    /// data for a body structure call until the standard has been
                    // re-defined body and bodystructure result in exactly the same call.
                    out.write(sep + commandStr + " ");
                    processBodyStructure(commandStr,maildirMessage);
                    // process the body request
                }
                sep = " ";
                
            }
            out.write(")\r\n");
        } catch (Exception ex) {
            log.error("Failed to process the message : " + ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to process the message : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * Process the body structure
     */
    private void processBodyStructure(String commandStr,
            javax.mail.internet.MimeMessage mimeMessage) throws ServerException {
        try {
            Object content = mimeMessage.getContent();
            if (content instanceof String) {
                log.debug("Content : " + content.getClass().getName());
                ImapPart imapPart = new ImapPart(
                        mimeMessage.getRawInputStream());
                printBodyStructure("text", "plain", "\"CHARSET\" " +
                        "\"us-ascii\"", "NIL", "NIL","7BIT",
                        imapPart.getSize(),
                        imapPart.getLines());
            } else if (content instanceof Multipart) {
                Multipart multipart = (Multipart)content;
                out.write("(");
                processMultiPart(commandStr,multipart);
                // add the extension data
                out.write(String.format(
                        " %s)", parseContentType(multipart)));
            }
        } catch (Exception ex) {
            log.error("Failed to process the body structure : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to process the body structure : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method processes a multi part messsage
     */
    public void processMultiPart(String commandStr,
            Multipart multipart) throws ServerException {
        try {
            for (int index = 0; index < multipart.getCount(); index++) {
                javax.mail.internet.MimeBodyPart part =
                        (javax.mail.internet.MimeBodyPart)multipart.getBodyPart(index);
                // loop through the content information.
                log.debug("Content is : " + part.getContent().getClass().getName());
                if (part.getContent() instanceof javax.mail.internet.MimeMessage) {
                    javax.mail.internet.MimeMessage mimeMessage =
                            (javax.mail.internet.MimeMessage)part.getContent();
                    processBodyStructure(commandStr,mimeMessage);
                    continue;
                } else if (part.getContent() instanceof Multipart) {
                    Multipart contentMultipart = (Multipart)part.getContent();
                    out.write("(");
                    processMultiPart(commandStr,contentMultipart);
                    // add the extension data
                    out.write(String.format(
                            " %s)", parseContentType(contentMultipart)));
                    continue;
                }
                
                log.debug("Part Content Type : " + part.getContentType());
                log.debug("File name : " + part.getFileName());
                String[] contentEncoding = part.getContentType().split("[;]");
                String[] contentPart = contentEncoding[0].split("[/]");
                StringBuffer parameters = new StringBuffer();
                String sep = "";
                for (int contentIndex = 1;
                contentIndex < contentEncoding.length; contentIndex++) {
                    String[] bodyParameter =
                            contentEncoding[contentIndex].trim().split("[=]");
                    parameters.append(sep).append("\"").append(
                            bodyParameter[0].trim()).
                            append("\" \"").append(bodyParameter[1].
                            replaceAll("\"","").trim()).append("\"");
                    sep = " ";
                }
                
                // get the content id and description
                String contentId = "NIL";
                if (part.getContentID() != null) {
                    contentId = "\"" + part.getContentID() + "\"";
                }
                String contentDescription = "NIL";
                if (part.getDescription() != null) {
                    contentDescription = "\"" + part.getDescription() + "\"";
                }
                
                
                // get encoding
                String[] encoding = part.getHeader("Content-Transfer-Encoding");
                if (encoding == null) {
                    log.debug("The encoding is equal to null");
                    encoding = new String[] {"7bit"};
                }
                log.debug("Part type = " + part.getClass().getName());
                ImapPart imapPart = new ImapPart(part.getRawInputStream());
                if (commandStr.equals("BODYSTRUCTURE")) {
                    
                    printBodyStructure(contentPart[0], contentPart[1], parameters.toString(),
                            contentId, contentDescription,encoding[0],
                            imapPart.getSize(),imapPart.getLines(),
                            part.getContentMD5(),part,
                            part.getContentLanguage());
                } else {
                    printBodyStructure(contentPart[0], contentPart[1], parameters.toString(),
                            contentId, contentDescription,encoding[0],
                            imapPart.getSize(),imapPart.getLines());
                }
            }
        } catch (Exception ex) {
            log.error("Failed to process the multipart message : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to process the multipart message : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to process the body request.
     */
    public void processBody(Body body,
            net.ukrpost.storage.maildir.MaildirFolder maildirFolder,
            MaildirMessage maildirMessage) throws ServerException {
        ImapFetchBodyHandler handler = new ImapFetchBodyHandler(out, body,
                maildirMessage);
        log.debug("Print out the requested body information");
        handler.printBody();
    }
    
    
    /**
     * This method is called to process the body request.
     */
    public void processBodyPeek(Body body,
            net.ukrpost.storage.maildir.MaildirFolder maildirFolder,
            MaildirMessage maildirMessage) throws ServerException {
        ImapFetchBodyHandler handler = new ImapFetchBodyHandler(out, body,
                maildirMessage);
        log.debug("Print out the requested body information");
        handler.printBody();
    }
    
    
    /**
     * This method prints out the body information
     */
    public void printBodyStructure(String type, String subType, String parameters,
            String id, String description, String encoding, int size, int lines)
            throws ServerException {
        try {
            this.out.write(String.format("(\"%s\" \"%s\" (%s) %s %s \"%s\" %d %d)",
                    type.toUpperCase(), subType.toUpperCase(),
                    parameters, id, description, encoding, size,
                    lines));
        } catch (Exception ex) {
            log.error("Failed to print out the body because : " +
                    ex.getMessage(),ex);
            throw new ServerException("Failed to print out the body because : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method prints out the body information
     */
    public void printBodyStructure(String type, String subType, String parameters,
            String id, String description, String encoding, int size, int lines,
            String md5, javax.mail.internet.MimeBodyPart part, String[] language)
            throws ServerException {
        try {
            /*
            Ignore this code for now, clients can handle a NIL response.
             if (md5 == null) {
                md5 = "NIL";
            } else {
                md5 = "\"" + md5 + "\"";
            }*/
            String disposition = "NIL";
            log.debug("Disposition : " + part.getDisposition());
            if ((part.getDisposition() != null) &&
                    part.getDisposition().equalsIgnoreCase("attachment") &&
                    (part.getFileName() != null)) {
                disposition = "(\"attachment\" (\"filename\" \"" +
                        part.getFileName() + "\"))";
            }
            /*String languageStr = "NIL";
            if (language != null) {
                languageStr = "(";
                String sep = "";
                for (int index = 0; index < language.length; index++) {
                    languageStr += sep + "\"" + language[index] + "\"";
                    sep = " ";
                }
                languageStr += ")";
            }*/
            // set the parameters for the message
            String paramStr = "(\"CHARSET\" \"us-ascii\")";
            if (parameters.length() > 0) {
                paramStr = "(" + parameters + ")";
                // handle 8bit encoding
            } else if (encoding.equals("8bit")) {
                paramStr = "(\"CHARSET\" \"UTF-8\")";
            }
            
            if (encoding.equals("7bit") || encoding.equals("8bit") ||
                    type.equalsIgnoreCase("TEXT")) {
                this.out.write(String.format(
                        "(\"%s\" \"%s\" %s %s %s \"%s\" %d %d NIL %s NIL)",
                        type.toUpperCase(), subType.toUpperCase(),
                        paramStr, id, description, encoding, size,
                        lines,disposition));
            } else {
                this.out.write(String.format(
                        "(\"%s\" \"%s\" %s %s %s \"%s\" %d NIL %s NIL)",
                        type.toUpperCase(), subType.toUpperCase(),
                        paramStr, id, description, encoding, size,
                        disposition));
            }
        } catch (Exception ex) {
            log.error("Failed to print out the body because : " +
                    ex.getMessage(),ex);
            throw new ServerException("Failed to print out the body because : " +
                    ex.getMessage(),ex);
        }
    }
    
    /**
     * This method returns a list of addresses or nill if the address list
     * is invalid.
     */
    private String processAddresses(Address[] addresses) throws ServerException {
        if (addresses == null) {
            return "NIL";
        }
        StringBuffer result = new StringBuffer().append("(");
        for (int index = 0; index < addresses.length; index++) {
            result.append(processAddress(addresses[index]));
        }
        result.append(")");
        if (result.toString().equals("()")) {
            return "NIL";
        }
        return result.toString();
    }
    
    
    /**
     * This method processes the address string
     */
    private String processAddress(Address address) throws ServerException {
        int index = address.toString().lastIndexOf(" ");
        if (index == -1) {
            return "(NIL NIL " + parseAddress(address.toString()) + ")";
        }
        String name = address.toString().substring(0,index).trim().
                replaceAll("\"","");
        String addStr = address.toString().substring(index + 1,
                address.toString().length()).trim();
        return "(\""+ name+ "\" NIL " + parseAddress(addStr) + ")";
    }
    
    
    /**
     * This method parses the address.
     */
    private String parseAddress(String address) throws ServerException {
        address = address.replaceAll("<","");
        address = address.replaceAll(">","");
        String[] addrSeg = address.split("[@]");
        if (addrSeg.length != 2) {
            log.error("Invalid address : " + address);
            throw new ServerException("Invalid address : " + address);
        }
        return "\"" + addrSeg[0] + "\" \"" + addrSeg[1] + "\"";
    }
    
    
    /**
     * This method parses the content
     */
    private String parseContentType(Multipart multipart) {
        String[] segments = multipart.getContentType().split("[;]");
        log.debug("Content type : " + multipart.getContentType());
        if (segments.length == 0) {
            return "\"MIXED\" NIL NIL NIL";
        }
        String[] types = segments[0].split("/");
        String subType = "MIXED";
        if (types.length == 2) {
            subType = types[1];
        }
        
        StringBuffer buffer = new StringBuffer();
        String sep = "";
        for (int index = 1; index < segments.length; index++) {
            String[] dispos = segments[index].split("[=]",2);
            log.debug("Dispos : " + segments[index]);
            if (dispos.length != 2) {
                log.debug("There are [" + dispos.length + "]");
                continue;
            }
            String value = dispos[1].replaceAll("\"","").trim();
            String key = dispos[0].trim();
            buffer.append(sep).append("\"").append(key).append("\" \"").
                    append(value).append("\"");
            sep = " ";
        }
        return String.format("\"%s\" (%s) NIL NIL",subType,buffer.toString());
    }
    
    
    /**
     * This method is called to escape the string being returned.
     *
     * @return The escaped string.
     * @param The value to escape.
     */
    private String escapeString(String value) {
        return value.replaceAll("\"","'");
    }
}
