/*
 * CoadunationDesktop: The desktop interface to the Coadunation Server.
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
 * Launcher.java
 */

// package path
package com.rift.coad.desktop.client.top;

// gwt imports
import com.google.gwt.user.client.Command;

// coadunation imports
import com.google.gwt.user.client.Window;
import com.rift.coad.desktop.client.desk.DesktopTabManager;
import com.rift.coad.desktop.client.top.LaunchInfo;
import org.gwm.client.GInternalFrame;
import org.gwm.client.impl.CoadInternalFrame;

/**
 * This object launches the specified application.
 * 
 * @author brett chaldecott
 */
public class Launcher implements Command {
    
    // private member variables
    private LaunchInfo info = null;
    /**
     * The constructor of the launcher
     */
    public Launcher(LaunchInfo info) {
        this.info = info;
    }
    
    /**
     * This method acts to handle the specified command
     */
    public void execute() {
        GInternalFrame window = new CoadInternalFrame(info.getName(),info.getTitle());
        window.setUrl(info.getUrl());
        DesktopTabManager.getInstance().getCurrentDesktop().addWindow(window);
        window.setVisible(true);
        window.setSize(info.getWidth(),info.getHeight() );
    }

}
