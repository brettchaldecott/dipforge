/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2010  Rift IT Contracting
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
 * PieceMenu.java
 */

package com.rift.coad.change.client.action.workflow;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ItemClickEvent;
import com.smartgwt.client.widgets.menu.events.ItemClickHandler;
import java.util.ArrayList;
import java.util.List;

/**
 * This method handles the piece menu.
 *
 * @author brett chaldecott
 */
public class PieceMenu extends Menu implements ItemClickHandler {

    // class singlton
    private static PieceMenu singleton = null;
    
    // private member variables
    private PieceBin bin;
    private Canvas targetCanvas;


    /**
     * The default constuctor
     */
    public PieceMenu() {
        List<MenuItem> items = new ArrayList<MenuItem>();

        MenuItem dependanciesEntry = new MenuItem("Delete");
        items.add(dependanciesEntry);

        setItems(items.toArray(new MenuItem[0]));

        addItemClickHandler(this);
    }


    /**
     * This method returns an instance of the menu.
     *
     * @return The reference to the menu.
     */
    public synchronized static PieceMenu getInstance() {
        if (singleton == null) {
            singleton = new PieceMenu();
        }
        return singleton;
    }

    
    /**
     * This method sets the piece bin
     * 
     * @param bin The bin to delete.
     */
    public void setBin(PieceBin bin) {
        this.bin = bin;
    }


    /**
     * This method sets the target of the piece menu.
     *
     * @param target The target of the piece menu.
     */
    public void setTargetCanvas(Canvas targetCanvas) {
        this.targetCanvas = targetCanvas;
    }

    /**
     * This method is called to deal with the on item click operation.
     *
     * @param event The event.
     */
    public void onItemClick(ItemClickEvent event) {
        if (event.getItem().getTitle().equalsIgnoreCase("Delete")) {
            this.bin.getFlowBar().removeChild(this.targetCanvas);
        }
    }

}
