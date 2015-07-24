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
 * TextServer1Impl.java
 */

package test.server;

import java.util.Date;
import com.rift.coad.daemon.messageservice.Message;
import com.rift.coad.daemon.messageservice.MessageHandler;
import com.rift.coad.daemon.messageservice.MessageServiceException;
import com.rift.coad.daemon.messageservice.TextMessage;

public class TextServerTextImpl implements MessageHandler {
    
    public TextServerTextImpl() {
    }
    
    public Message processMessage(Message msg) throws MessageServiceException {
        TextMessage textMessage = (TextMessage)msg;
        textMessage.setTextBody("Yes... yes i do.");
        textMessage.acknowledge();
        return textMessage;
    }
}
