/*
 * CoadunationAdmin: The admin frontend for coadunation.
 * Copyright (C) 2007 - 2008  2015 Burntjam
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
 * TreePanel.java
 */

// package path
package com.rift.coad.web.admin.client;

// imports
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeImages;
import com.google.gwt.user.client.ui.TreeItem;


/**
 * This is the tree panel.
 *
 * @author brett chaldecott
 */
public class TreePanel extends Composite {
    
    private Tree tree;
    
    /**
     * Creates a new instance of MXPanel
     */
    public TreePanel() {
        tree = new Tree();
        
        initWidget(tree);
    }
    
    /**
     * This method adds the 
     */
    public void addTreeItem(String value)
    {
        tree.addItem(new TreeItem(value));
    }
}
