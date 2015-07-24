/*
 * ProjectClient: The project client interface.
 * Copyright (C) 2012  2015 Burntjam
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
 * XMLTypeInfoParser.java
 */
package com.rift.dipforge.project.timer;

import com.rift.dipforge.project.type.TypeException;
import com.rift.dipforge.project.type.XMLTypeInfoParser;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * This is the implementation of the XML timer info parser.
 *
 * @author brett chaldecott
 */
public class XMLTimerInfoParser {

    // class static variables
    private static Logger log = Logger.getLogger(XMLTimerInfoParser.class);

    /**
     * This object represents an action to execute
     */
    public class Action {
        // private member variables

        private String jndi;
        private String script;
        private int month;
        private int day;
        private int hour;
        private int minute;
        private boolean recure;

        /**
         * The default constructor for the action object.
         */
        public Action() {
        }

        /**
         * This constructor sets up the action information.
         *
         * @param jndi The jndi information.
         * @param script The script
         * @param month The month of the year
         * @param day The day of the month
         * @param hour The hour of the day
         * @param minute The minute of the hour
         * @param recure TRUE to recure
         */
        public Action(String jndi, String script, int month, int day, int hour,
                int minute, boolean recure) {
            this.jndi = jndi;
            this.script = script;
            this.month = month;
            this.day = day;
            this.hour = hour;
            this.minute = minute;
            this.recure = recure;
        }

        /**
         * The string containing the JNDI information.
         *
         * @return The string containing the JNDI information.
         */
        public String getJndi() {
            return jndi;
        }

        /**
         * The string containing the jndi value.
         *
         * @param jndi The reference to the JNDI string.
         */
        public void setJndi(String jndi) {
            this.jndi = jndi;
        }

        /**
         * The string containing the script information.
         *
         * @return The script information.
         */
        public String getScript() {
            return script;
        }

        /**
         * The setter for the script information.
         *
         * @param script The string containing the script information.
         */
        public void setScript(String script) {
            this.script = script;
        }

        /**
         * This method returns the integer day
         *
         * @return The day
         */
        public int getDay() {
            return day;
        }

        /**
         * This method sets the day of the month
         *
         * @param day The day of the month
         */
        public void setDay(int day) {
            this.day = day;
        }

        /**
         * This method gets the day of the hour of the day
         *
         * @param hour The hour of the day
         */
        public int getHour() {
            return hour;
        }

        /**
         * This method sets the hour of the day
         *
         * @param hour The hour of the day
         */
        public void setHour(int hour) {
            this.hour = hour;
        }

        /**
         * This method sets the minute of the hour
         *
         * @return The minute of the hour
         */
        public int getMinute() {
            return minute;
        }

        /**
         * This method set the minute of the hour
         *
         * @param minute The minute of the hour
         */
        public void setMinute(int minute) {
            this.minute = minute;
        }

        /**
         * This method gets the month of the
         *
         * @return
         */
        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public boolean isRecure() {
            return recure;
        }

        public void setRecure(boolean recure) {
            this.recure = recure;
        }

        /**
         * This method determines if the objects are equal.
         *
         * @param obj The object to perform the comparison on.
         * @return The reference to the equals object.
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Action other = (Action) obj;
            if ((this.jndi == null) ? (other.jndi != null) : !this.jndi.equals(other.jndi)) {
                return false;
            }
            if ((this.script == null) ? (other.script != null) : !this.script.equals(other.script)) {
                return false;
            }
            return true;
        }

        /**
         * The hash code to perform the equals operation on.
         *
         * @return The integer hash code.
         */
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 47 * hash + (this.jndi != null ? this.jndi.hashCode() : 0);
            hash = 47 * hash + (this.script != null ? this.script.hashCode() : 0);
            return hash;
        }

        /**
         * This method returns the string value.
         *
         * @return The string value.
         */
        @Override
        public String toString() {
            return "Action{" + "jndi=" + jndi + ", script=" + script + '}';
        }
    }

    /**
     * The xml timer handler
     */
    public class XMLTimerInfoHandler extends DefaultHandler {

        // class constants
        private final static String TIMES = "times";
        private final static String PROJECT = "project";
        private final static String TIME = "time";
        private final static String JNDI = "jndi";
        private final static String SCRIPT = "script";
        private final static String MONTH = "month";
        private final static String DAY = "day";
        private final static String HOUR = "hour";
        private final static String MINUTE = "minute";
        private final static String RECURE = "recur";
        // member variables
        private boolean inTimes = false;

        /**
         * The default constructor
         */
        public XMLTimerInfoHandler() {
        }

        /**
         * This method is called at the start of an element.
         *
         * @param uri The uri.
         * @param localName The local name.
         * @param qName The qName
         * @param attributes The list of attributes
         * @throws SAXException
         */
        public void startElement(String uri, String localName, String qName,
                Attributes attributes) throws SAXException {
            try {
                // handle a package and retrieve the value information
                if (qName.compareToIgnoreCase(TIMES) == 0) {
                    inTimes = true;
                    project = (String) attributes.getValue(PROJECT);
                } else if (qName.compareToIgnoreCase(TIME) == 0) {
                    if ((attributes.getValue(MONTH) == null)
                            || (attributes.getValue(DAY) == null)
                            || (attributes.getValue(HOUR) == null)
                            || (attributes.getValue(MINUTE) == null)
                            || (attributes.getValue(RECURE) == null)) {
                        log.error("The [MONTH,DAY,HOUR,MINUTE,RECUR] must be set for a time");
                        throw new SAXException(
                                "The [MONTH,DAY,HOUR,MINUTE,RECUR] must be set for a time");
                    }
                    actions.add(new Action((String) attributes.getValue(JNDI),
                            (String) attributes.getValue(SCRIPT),
                            Integer.parseInt(attributes.getValue(MONTH)),
                            Integer.parseInt(attributes.getValue(DAY)),
                            Integer.parseInt(attributes.getValue(HOUR)),
                            Integer.parseInt(attributes.getValue(MINUTE)),
                            attributes.getValue(RECURE).equalsIgnoreCase("TRUE") ? true : false));
                }
            } catch (SAXException ex) {
                throw ex;
            } catch (Exception ex) {
                log.error("Failed to handle the start element : " + ex.getMessage(), ex);
                throw new SAXException(
                        "Failed to handle the start element : " + ex.getMessage(), ex);
            }
        }

        /**
         * This method sets the end element.
         *
         * @param uri The string uri.
         * @param localName The local name
         * @param qName The qName
         * @throws SAXException
         */
        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            try {
                // handle a package and retrieve the value information
                if (qName.compareToIgnoreCase(TIMES) == 0) {
                    inTimes = false;
                }

            } catch (Exception ex) {
                log.error("Failed to handle the end element : " + ex.getMessage(), ex);
                throw new SAXException(
                        "Failed to handle the end element : " + ex.getMessage(), ex);
            }
        }
    }
    // private member variables.
    private String xml;
    private String project;
    private List<Action> actions = new ArrayList<Action>();

    public XMLTimerInfoParser(String xml) throws TimerException {
        this.xml = xml;
        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            ByteArrayInputStream in = new ByteArrayInputStream(xml.getBytes());
            parser.parse(in, new XMLTimerInfoHandler());
            in.close();
        } catch (Exception ex) {
            log.error("Failed to parser the xml : " + ex.getMessage(), ex);
            throw new TimerException("Failed to parser the xml : " + ex.getMessage(), ex);
        }
    }

    /**
     * The getter for the actions
     *
     * @return The list of actions.
     */
    public List<Action> getActions() {
        return actions;
    }

    /**
     * The getter for the project name.
     *
     * @return The string containing the project name.
     */
    public String getProject() {
        return project;
    }

    /**
     * The string containing the XML.
     *
     * @return The XML information
     */
    public String getXml() {
        return xml;
    }
}
