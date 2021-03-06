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
 * Service.java
 */

package com.rift.coad.daemon.servicebroker.db;

/**
 * Service generated by hbm2java
 */
public class Service  implements java.io.Serializable {

    // Fields    

     private ServicePK comp_id;
     private Integer counter;

     // Constructors

    /** default constructor */
    public Service() {
    }

    /** full constructor */
    public Service(ServicePK comp_id, Integer counter) {
       this.comp_id = comp_id;
       this.counter = counter;
    }
    
   
    // Property accessors
    public ServicePK getComp_id() {
        return this.comp_id;
    }
    
    public void setComp_id(ServicePK comp_id) {
        this.comp_id = comp_id;
    }
    public Integer getCounter() {
        return this.counter;
    }
    
    public void setCounter(Integer counter) {
        this.counter = counter;
    }



}


