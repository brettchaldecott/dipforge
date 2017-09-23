/*
 * bss: Description
 * Copyright (C) Sun Jun 17 19:11:59 SAST 2012 owner
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
 * DataView.js
 * @author brett chaldecott
 */
 
 
 
 Ext.define('com.dipforge.BSS.ManagementTypeView', {
    
    extend: 'Ext.tab.Panel',
    alias: 'widget.managementtypeviewpanel',
    tabPosition: 'bottom',
    
    border: false,
    plugins: [{
                ptype: 'tabscrollermenu',
                maxText  : 15,
                pageSize : 5
            }],

    initComponent: function() {
        this.tabBar = {
            border: false
        };
        this.callParent(arguments);
    },
    
    // template method
    afterRender: function(){
        this.callParent(arguments);
    },
    
    
    /**
     * Add a new type editor
     * 
     * @param {String} project The name of the project
     * @param {String} fileName The file name of the project
     * @param {String} path The path of the file
     * @param {String} editor The type of editor
     * @param {mode} The mode of the editor
     */
    addManagementTypeView: function(type,name,view,mode){
        var id = type
        var active = this.getComponent(id);
        var self = this
        if (!active) {
            if (type == "organisation") {
                active = this.add(Ext.create('Ext.panel.Panel', {
                    layout: "fit",
                    id: type,
                    itemId: type,
                    title: name,
                    closable: true,
                    width: '100%',
                    height: '100%'
                }));
                this.setActiveTab(active);
            } else if (type == "user") {
            	active = this.add(Ext.create('Ext.panel.Panel', {
                    layout: "fit",
                    id: type,
                    itemId: type,
                    title: name,
                    closable: true,
                    width: '100%',
                    height: '100%'
                }));
                this.setActiveTab(active);
            } else if (type == "package") {
                active = this.add(Ext.create('Ext.panel.Panel', {
                    layout: "fit",
                    id: type,
                    itemId: type,
                    title: name,
                    closable: true,
                    width: '100%',
                    height: '100%'
                }));
                this.setActiveTab(active);
            }
        } else {
            this.setActiveTab(active);
        }
    }
});
