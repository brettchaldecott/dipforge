/*
 * ScriptIDE: The coadunation ide for editing scripts in coadunation.
 * Copyright (C) 2010  2015 Burntjam
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
 * TimerManager.java
 */


package com.rift.coad.script.client.files;

/**
 * The timer event object
 *
 * @author brett chaldecott
 */
public class TimerEvent implements java.io.Serializable {
      // private member variables
    private int id = 0;
    private String jndi = null;
    private String event = null;
    private int month = -1;
    private int day = -1;
    private int hour = -1;
    private int minute = -1;
    private boolean recure = false;


    /**
     * Creates a new instance of TimerEvent
     */
    public TimerEvent() {
    }


    /**
     * The contructor responsible for setting the private member variables.
     *
     * @param id The id of the timer.
     * @param jndi The jndi url for the event.
     * @param event The serializable event identifier.
     * @param month The month the event occurs in.
     * @param day The day of the month that the event occurs on.
     * @param hour The hour the event occurs on.
     * @param minute The minute the event occurs on.
     * @param recure If true the event will recure and is not just a once off.
     */
    public TimerEvent(int id, String jndi, String event, int month,
            int day, int hour, int minute, boolean recure) {
        this.id = id;
        this.jndi = jndi;
        this.event = event;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.recure = recure;
    }


    /**
     * The getter method for the id.
     *
     * @return Returns the id of this event.
     */
    public int getId() {
        return id;
    }


    /**
     * The setter method for the event id.
     *
     * @param id The id of the
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * The getter method for the JNDI url.
     *
     * @return The string containing the JNDI url.
     */
    public String getJndi() {
        return jndi;
    }


    /**
     * The setter method for the JNDI url.
     *
     * @param jndi The jndi url.
     */
    public void setJndi(String jndi) {
        this.jndi = jndi;
    }


    /**
     * The getter method for the event.
     *
     * @return The object that indicates the event that this object was created
     *          for.
     */
    public String getEvent() {
        return event;
    }


    /**
     * Set the event.
     *
     * @param event The object used to identify this event.
     */
    public void setEvent(String event) {
        this.event = event;
    }


    /**
     * The get method for month.
     *
     * @return Returns the value for month.
     */
    public int getMonth() {
        return month;
    }

    /**
     * The set method for month.
     *
     * @param month The new value for month.
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * The get method for day.
     *
     * @return Returns the value for day.
     */
    public int getDay() {
        return day;
    }

    /**
     * The set method for day.
     *
     * @param day The new value for day.
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * The get method for hour.
     *
     * @return Returns the value for hour.
     */
    public int getHour() {
        return hour;
    }

    /**
     * The set method for hour.
     *
     * @param hour The new value for hour.
     */
    public void setHour(int hour) {
        this.hour = hour;
    }

    /**
     * The get method for minute.
     *
     * @return Returns the value for minute.
     */
    public int getMinute() {
        return minute;
    }

    /**
     * The set method for minute.
     *
     * @param minute The new value for minute.
     */
    public void setMinute(int minute) {
        this.minute = minute;
    }


    /**
     * This getter for the recure flag.
     *
     * @return The recure flag.
     */
    public boolean getRecure() {
        return recure;
    }


    /**
     * The setter for the recure method.
     *
     * @param recure The new recure flag value.
     */
    public void setRecure(boolean recure) {
        this.recure = recure;
    }


    /**
     * This method returns the string version of the timer event.
     */
    public String toString() {
        return "[" + id + "|" + jndi + "|" + month + "|" + day
                + "|" + hour + "|" + minute + "|" + event.toString() + "|" + recure + "]";
    }
}
