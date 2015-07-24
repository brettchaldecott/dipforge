/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  2015 Burntjam
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
 * BeanThread.java
 *
 * This object is responsible for calling the run and terminate methods on the
 * BeanRunnable object.
 */

// package path
package com.rift.coad.lib.bean;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.thread.BasicThread;

/**
 * This object is responsible for calling the run and terminate methods on the
 * BeanRunnable object.
 *
 * @author Brett Chaldecott
 */
public class BeanThread extends BasicThread {
    
    // the class log variable
    protected Logger log =
        Logger.getLogger(BasicThread.class.getName());
    
    // the classes member variables
    private BeanRunnable bean = null;
    
    /** 
     * Creates a new instance of BeanThread 
     *
     * @param bean The reference to the 
     */
    public BeanThread(BeanRunnable bean) throws Exception
    {
        this.bean = bean;
    }
    
    
    /**
     * This method replaces the run method in the BasicThread.
     *
     * @exception Exception
     */
    public void process() throws Exception {
        try {
            bean.process();
        } catch (Exception ex) {
            log.error("Failed to process with this thread : " + ex.getMessage(),
                    ex);
        }
    }
    
    
    /**
     * This method will be implemented by child objects to terminate the
     * processing of this thread.
     */
    public void terminate() {
        try {
            bean.terminate();
        } catch (Exception ex) {
            log.error("Failed to terminate this thread : " + ex.getMessage(),
                    ex);
        }
    }
}
