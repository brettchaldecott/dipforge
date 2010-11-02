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
 * RPCServerTestImpl.java
 */

package test.server;

import java.util.Date;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class RPCServerTestImpl implements RPCServerTest {

    public RPCServerTestImpl() {
    }

    public int testMethod(String msg) throws RemoteException {
        return msg.length();
    }

}