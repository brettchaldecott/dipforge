/*
 * CoadunationAdmin: The admin frontend for coadunation.
 * Copyright (C) 2007 - 2008  Rift IT Contracting
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
 * ContentPanel.java
 */

// package path
package com.rift.coad.web.admin.client;

// java imports
import java.util.HashMap;

// gwt imports
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.DeckPanel;


/**
 * This is the content panel for the admin console.
 *
 * @author brett chaldecott
 */
public class ContentPanel extends Composite {
    
    // singleton member variables
    private static ContentPanel singleton = null;
    
    // private member variables
    private DeckPanel deckPanel = new DeckPanel();
    private HashMap entries = new HashMap();
    
    /**
     * Creates a new instance of ContentPanel
     */
    private ContentPanel() {
        
        // set the values of the deck panel
        deckPanel.setHeight("100%");
        deckPanel.setWidth("100%");
        
        // init the widget
        initWidget(deckPanel);
    }
    
    
    /**
     * This method returns an instance of the content panel.
     *
     * @return A reference to the content panel.
     */
    public static synchronized ContentPanel getInstance() {
        if (singleton == null) {
            singleton = new ContentPanel();
        }
        return singleton;
    }
    
    
    /**
     * This method is responsible setting a panel entry.
     *
     * @param name The name of the panel to add.
     * @param widget The reference to the panel.
     */
    public void setPanel(
            String name,
            Widget widget)
    {
        if (entries.containsKey(name))
        {
            deckPanel.showWidget(((Integer)entries.get(name)).intValue());
        }
        else
        {
            Integer index = new Integer(entries.size());
            entries.put(name,index);
            deckPanel.add(widget);
            deckPanel.showWidget(index.intValue());
        }
    }
}
