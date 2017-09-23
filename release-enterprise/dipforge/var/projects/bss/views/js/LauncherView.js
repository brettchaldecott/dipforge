/*
 * bss: Description
 * Copyright (C) Sun Jun 17 19:11:40 SAST 2012 owner
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
 * LauncherView.js
 * @author brett chaldecott
 */



Ext.define('com.dipforge.BSS.ManagementList', {
    extend: 'Ext.panel.Panel',
    
    alias: 'widget.managementlistpanel',
    
    layout: 'fit',
    title: 'Management',
    preventHeader: true,
    
    
    initComponent: function(){
        Ext.apply(this, {
            items: [
                Ext.create('Ext.container.Container', {
                    layout: {
                       type: 'anchor'},
            	    items: [this.createEntityTypeListView()]
            	    })]
        });
        
        this.addEvents(
            /**
             * @event entitytypeselect Fired when a type is selected
             * @param {FeedPanel} this
             * @param {String} title The title of the feed
             * @param {String} url The url of the feed
             */
            'managementtypeselect'
        );
        this.callParent(arguments);
    },   

    // template method
    afterRender: function(){
        this.callParent(arguments);
    },
    
    /**
     * Create the DataView to be used for the feed list.
     * @private
     * @return {Ext.view.View}
     */
    createEntityTypeListView: function(){
        //Ext.ux.tree.TreeGrid is no longer a Ux. You can simply use a tree.TreePanel
    	this.tree = Ext.create('Ext.tree.Panel', {
            //title: 'Core Team Projects',
            preventHeader: true,
            anchor: '100% 100%',
            collapsible: false,
            useArrows: true,
            rootVisible: false,
            store: Ext.create('Ext.data.TreeStore', {
            	model: 'ManagementType',
            	autoLoad: true,
            	proxy: {
                	type: 'ajax',
                	//the store will get the content from the .json file
                	url: 'entity/EntityTypeList.groovy'
            		}
    			}),
            singleExpand: false,
            //the 'columns' property is now 'headers'
            columns: [{
                xtype: 'treecolumn', //this is so we know which column will show the tree
                text: 'Management',
                flex: 2,
                sortable: true,
                dataIndex: 'name'
            }],
            listeners: {
            	scope: this,
                selectionchange: this.onSelectionChange
                }});
    	return this.tree;
    },
    
    
    /**
     * Used when view selection changes so we can disable toolbar buttons.
     * @private
     */
    onSelectionChange: function(model,records){
    	if (records.length != 0 && records[0].get('leaf')) {
            var record = records[0]
        	this.fireEvent('managementtypeselect', this, record.get('type'), 
        		record.get('name'), record.get('view'), record.get('mode'));
        }
    },
    
    
    /**
     * This method is called when the context menu
     */
    onContainerContextMenu: function(model,record,item,index,eventObj,options) {
        eventObj.stopEvent();
        this.mnuContext.model = this.tree;
        this.mnuContext.record = record;
        this.mnuContext.item = item;
    	this.mnuContext.showAt(eventObj.getXY());    
    }
 });