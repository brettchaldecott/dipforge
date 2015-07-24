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
 * ImapSearchFilter.java
 */

// package path
package com.rift.coad.daemon.email.server.imap;

// java imports
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Enumeration;
import java.util.Calendar;

// log4j imports
import org.apache.log4j.Logger;

// java mail api
import javax.mail.*;
import javax.mail.internet.*;
import com.sun.mail.imap.protocol.FLAGS;
import javax.mail.search.*;

// email imports
import com.rift.coad.daemon.email.smtp.Header;
import com.rift.coad.daemon.email.server.ServerException;


/**
 * The imap search filter
 *
 * @author brett chaldecott
 */
public class ImapSearchFilter {
    
    // log reference
    private static Logger log = Logger.getLogger(ImapSearchFilter.class);
    
    // search criteria
    private ImapSequenceSet messageSet = null;
    private boolean all = false;
    private Flags flags = new Flags();
    private Flags notFlags = new Flags();
    private List searchTerms = new ArrayList();
    private ImapSequenceSet uid = null;
    private boolean uidBased = false;
    
    /**
     * Creates a new instance of ImapSearchFilter
     * 
     * @param uid Results must use uid.
     * @param token The token to evaluate
     */
    public ImapSearchFilter(boolean uid, StringTokenizer token) 
    throws ServerException {
        try {
            this.uidBased = uid;
            while(token.hasMoreTokens()) {
                String tokenValue = token.nextToken();
                SearchTerm term = null;
                if (tokenValue.equalsIgnoreCase("ALL")) {
                    all = true;
                } else if (tokenValue.equalsIgnoreCase("ANSWERED")) {
                    flags.add(Flags.Flag.ANSWERED);
                } else if (tokenValue.equalsIgnoreCase("DELETED")) {
                    flags.add(Flags.Flag.ANSWERED);
                } else if (tokenValue.equalsIgnoreCase("DRAFT")) {
                    flags.add(Flags.Flag.DRAFT);
                } else if (tokenValue.equalsIgnoreCase("FLAGGED")) {
                    flags.add(Flags.Flag.FLAGGED);
                } else if (tokenValue.equalsIgnoreCase("NEW")) {
                    flags.add(Flags.Flag.RECENT);
                } else if (tokenValue.equalsIgnoreCase("RECENT")) {
                    flags.add(Flags.Flag.RECENT);
                } else if (tokenValue.equalsIgnoreCase("SEEN")) {
                    flags.add(Flags.Flag.SEEN);
                } else if (tokenValue.equalsIgnoreCase("UNANSWERED")) {
                    notFlags.add(Flags.Flag.ANSWERED);
                } else if (tokenValue.equalsIgnoreCase("UNDELETED")) {
                    notFlags.add(Flags.Flag.DELETED);
                } else if (tokenValue.equalsIgnoreCase("UNDRAFT")) {
                    notFlags.add(Flags.Flag.DRAFT);
                } else if (tokenValue.equalsIgnoreCase("UNFLAGGED")) {
                    notFlags.add(Flags.Flag.FLAGGED);
                } else if (tokenValue.equalsIgnoreCase("UNSEEN")) {
                    notFlags.add(Flags.Flag.SEEN);
                } else if (tokenValue.equalsIgnoreCase("UID")) {
                    this.uid = new ImapSequenceSet(token.nextToken());
                } else if ((term = getSearchTerm(token,tokenValue)) != null) {
                    searchTerms.add(term);
                } else {
                    messageSet = new ImapSequenceSet(tokenValue);
                }
            }
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed create the search filter : " + ex.getMessage(),ex);
            throw new ServerException(
                    "Failed create the search filter : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * filter the message and return false if not valid else true.
     *
     * @return TRUE if the filter matches.
     * @param message The message to filter.
     */
    public boolean filter(
            net.ukrpost.storage.maildir.MaildirFolder maildirFolder,
            Message message) throws ServerException {
        try {
            
            if (!all && messageSet != null && !messageSet.inSequenceSet(
                    message.getMessageNumber())) {
                log.debug("Message not in message set");
                return false;
            }
            if ((flags.getSystemFlags().length > 0) &&
                    !message.getFlags().contains(flags)) {
                log.debug("Message flags not found");
                return false;
            }
            if ((notFlags.getSystemFlags().length > 0) &&
                    message.getFlags().contains(notFlags)) {
                log.debug("Not flags found");
                return false;
            }
            
            for (int index = 0; index < this.searchTerms.size(); index++) {
                SearchTerm term = (SearchTerm)searchTerms.get(index);
                if (!message.match(term)) {
                    return false;
                }
            }
            
            if (!all && uid != null && ! uid.inSequenceSet(
                maildirFolder.getUID(message))) {
                    log.debug("Not in uid range");
                    return false;
            }
            
            log.debug("Return true");
            return true;
        } catch (Exception ex) {
            log.error("Failed to filter the message : " + ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to filter the message : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method process the search terms
     */
    private SearchTerm getSearchTerm(StringTokenizer token, String tokenValue)
    throws ServerException {
        if (tokenValue.equalsIgnoreCase("BCC")) {
            return new RecipientStringTerm(Message.RecipientType.BCC,
                    ImapUtils.parseQuotedString(token));
        } else if (tokenValue.equalsIgnoreCase("BEFORE")) {
            return new ReceivedDateTerm(ComparisonTerm.LE,
                    ImapUtils.parseDate(
                    ImapUtils.parseQuotedString(token)));
        } else if (tokenValue.equalsIgnoreCase("BODY")) {
            return new BodyTerm(ImapUtils.parseQuotedString(token));
        } else if (tokenValue.equalsIgnoreCase("CC")) {
            return new RecipientStringTerm(Message.RecipientType.CC,
                    ImapUtils.parseQuotedString(token));
        } else if (tokenValue.equalsIgnoreCase("FROM")) {
            return new FromStringTerm(
                    ImapUtils.parseQuotedString(token));
        } else if (tokenValue.equalsIgnoreCase("HEADER")) {
            return new HeaderTerm(ImapUtils.parseQuotedString(token),
                    ImapUtils.parseQuotedString(token));
        } else if (tokenValue.equalsIgnoreCase("KEYWORD")) {
            //keyword = new BodyTerm(ImapUtils.parseQuotedString(token));
            return null;
        } else if (tokenValue.equalsIgnoreCase("LARGER")) {
            return new SizeTerm(ComparisonTerm.GE,
                    Integer.parseInt(token.nextToken()));
        } else if (tokenValue.equalsIgnoreCase("NOT")) {
            return new NotTerm(getSearchTerm(token,token.nextToken()));
        } else if (tokenValue.equalsIgnoreCase("ON")) {
            return new ReceivedDateTerm(ComparisonTerm.EQ,
                    ImapUtils.parseDate(
                    ImapUtils.parseQuotedString(token)));
        } else if (tokenValue.equalsIgnoreCase("OR")) {
            SearchTerm term1 = this.getSearchTerm(token,token.nextToken());
            SearchTerm term2 = this.getSearchTerm(token,token.nextToken());
            return new OrTerm(term1,term2);
        } else if (tokenValue.equalsIgnoreCase("SENTBEFORE")) {
            return new SentDateTerm(ComparisonTerm.LE,
                    ImapUtils.parseDate(
                    ImapUtils.parseQuotedString(token)));
        } else if (tokenValue.equalsIgnoreCase("SENTON")) {
            return new SentDateTerm(ComparisonTerm.EQ,
                    ImapUtils.parseDate(
                    ImapUtils.parseQuotedString(token)));
        } else if (tokenValue.equalsIgnoreCase("SENTSINCE")) {
            return new SentDateTerm(ComparisonTerm.GE,
                    ImapUtils.parseDate(
                    ImapUtils.parseQuotedString(token)));
        } else if (tokenValue.equalsIgnoreCase("SINCE")) {
            return new ReceivedDateTerm(ComparisonTerm.GE,
                    ImapUtils.parseDate(
                    ImapUtils.parseQuotedString(token)));
        } else if (tokenValue.equalsIgnoreCase("SMALLER")) {
            return new SizeTerm(ComparisonTerm.LE,
                    Integer.parseInt(token.nextToken()));
        } else if (tokenValue.equalsIgnoreCase("SUBJECT")) {
            return new SubjectTerm(
                    ImapUtils.parseQuotedString(token));
        } else if (tokenValue.equalsIgnoreCase("TEXT")) {
            return new BodyTerm(ImapUtils.parseQuotedString(token));
        } else if (tokenValue.equalsIgnoreCase("TO")) {
            return new RecipientStringTerm(Message.RecipientType.TO,
                    ImapUtils.parseQuotedString(token));
        }
        return null;
    }
    
}
