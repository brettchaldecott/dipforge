/*
 * MessageTest: This is a test message service library.
 * Copyright (C) 2007 2015 Burntjam
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
 * NamedServer1.java
 */

// package path
package test.server.named;

// imports
import com.rift.coad.lib.bean.BeanRunnable;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.daemon.messageservice.Message;
import com.rift.coad.daemon.messageservice.TextMessage;
import com.rift.coad.daemon.messageservice.named.NamedQueueClient;


/**
 * The named server implementation of the named server.
 *
 * @author Brett Chaldecott
 */
public class NamedServerImpl implements NamedServer, BeanRunnable {

    // private member variables
    private ThreadStateMonitor state = new ThreadStateMonitor();
    
    /**
     * Creates a new instance of NamedServer1
     */
    public NamedServerImpl() {
    }
    
    
    /**
     * This method is called to perform the processing.
     */
    public void process() {
        NamedQueueClient client = null;
        try {
            client = NamedQueueClient.create("test");
        } catch (Exception ex) {
            System.out.println("Failed to create named queue");
            return;
        }
        while(!state.isTerminated()) {
            try {
                Message message = client.receive(1000);
                if (message == null) {
                    continue;
                }
                TextMessage textMessage = (TextMessage)message;
            } catch (Exception ex) {
                System.out.println("Failed to retrieve text message:" 
                        + ex.toString());
            }
        }
    }
    
    
    /**
     * This method is called to terminate the processing.
     */
    public void terminate() {
        state.terminate(true);
    }
}
