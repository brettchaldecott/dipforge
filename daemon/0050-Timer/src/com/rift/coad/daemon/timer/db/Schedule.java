/*
 * Timer: The timer class
 * Copyright (C) 2006-2007  2015 Burntjam
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
 * Schedule.java
 */

package com.rift.coad.daemon.timer.db;

/**
 * Schedule is a hibernate database object.
 */
public class Schedule  implements java.io.Serializable {

    // Fields    

     /**
      * 
      * auto_increment
      * 		    
      */
     private Integer id;
     private String jndi;
     private Integer month;
     private Integer day;
     private Integer hour;
     private Integer minute;
     private byte[] event;
     private byte recure;

     // Constructors

    /** default constructor */
    public Schedule() {
    }

    /** full constructor
     *
     * @param jndi The JNDI of the daemon to be called.
     * @param month The month on which the event will be called.
     * @param day The day on which the event will be called.
     * @param hour The hour on which the event will be called.
     * @param minute The minute on which the event will be called.
     * @param event An object to identify the event.
     * @param recure A byte which indicates whether the event recures or not.
     */
    public Schedule(String jndi, Integer month, Integer day, Integer hour, 
            Integer minute, byte[] event, byte recure) {
       this.jndi = jndi;
       this.month = month;
       this.day = day;
       this.hour = hour;
       this.minute = minute;
       this.event = event;
       this.recure = recure;
    }
   
    // Property accessors
    
    public Integer getId() {
        return this.id;
    }
    
    /**       
     * auto_increment	    
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * @return Returns the JNDI of the daemon to be called.
     */
    public String getJndi() {
        return this.jndi;
    }
    
    /**
     * @param jndi The JNDI of the daemon to be called.
     */
    public void setJndi(String jndi) {
        this.jndi = jndi;
    }
    
    /**
     * @return Returns the month on which the event will occur.
     */
    public Integer getMonth() {
        return this.month;
    }
    
    /**
     * @param month Sets the month on which the event is to occur.
     */
    public void setMonth(Integer month) {
        this.month = month;
    }
    
    /**
     * @return Returns the day on which the event is to occur.
     */
    public Integer getDay() {
        return this.day;
    }
    
    /**
     * @param day Sets the day on which the event is to occur.
     */
    public void setDay(Integer day) {
        this.day = day;
    }
    
    /**
     * @return Returns the hour on which the event is to occur.
     */
    public Integer getHour() {
        return this.hour;
    }
    
    /**
     * @param hour Sets the hour on which an event is to occur.
     */
    public void setHour(Integer hour) {
        this.hour = hour;
    }
    
    /**
     * @return Returns the minute on which an event is to occur.
     */
    public Integer getMinute() {
        return this.minute;
    }
    
    /**
     * @param minute Sets the minutes on which an event is to occur.
     */
    public void setMinute(Integer minute) {
        this.minute = minute;
    }
    
    /**
     * @return Returns the identifying object.
     */
    public byte[] getEvent() {
        return this.event;
    }
    
    /**
     * @param event Sets the identifying object.
     */
    public void setEvent(byte[] event) {
        this.event = event;
    }
    
    /**
     * @return Returns the recure value.
     */
    public byte getRecure() {
        return this.recure;
    }
    
    /**
     * @param recure Sets the recure value.
     */
    public void setRecure(byte recure) {
        this.recure = recure;
    }
    
}


