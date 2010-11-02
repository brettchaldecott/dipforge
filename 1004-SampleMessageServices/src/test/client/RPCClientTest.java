/*
 * Timer: The timer class
 * Copyright (C) 2006  Rift IT Contracting
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
 * RPCClientTest.java
 */

package test.client;

import com.rift.coad.daemon.messageservice.MessageHandler;
import java.rmi.Remote;
import java.rmi.RemoteException;
import com.rift.coad.daemon.messageservice.AsyncCallbackHandler;

public interface RPCClientTest extends AsyncCallbackHandler, Remote, 
        MessageHandler {

    public void runBasicTest(String testString) throws RemoteException,
            MessageTestException;
    
    public void runBasicMessageTest(String testString) throws RemoteException,
            MessageTestException;

}