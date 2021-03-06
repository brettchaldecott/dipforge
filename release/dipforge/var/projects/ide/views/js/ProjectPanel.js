/**
 * @class com.dipforge.IDE.ProjectPanel
 * @extends Ext.panel.Panel
 *
 * Shows a list of available feeds. Also has the ability to add/remove and load feeds.
 *
 * @constructor
 * Create a new Feed Panel
 * @param {Object} config The config object
 */

Ext.define('com.dipforge.IDE.ProjectPanel', {
    extend: 'Ext.panel.Panel',
    
    alias: 'widget.projectpanel',
    
    layout: 'fit',
    title: 'project',
    preventHeader: true,
    
    
    initComponent: function(){
        Ext.apply(this, {
            items: [
            	Ext.create('Ext.container.Container', {
                    layout: {
                       type: 'fit'},
            	    items: [this.createListTabs()]
            	    })]
        });
        
        this.addEvents(
            /**
             * @event fileselect Fired when a feed is selected
             * @param {FeedPanel} this
             * @param {String} title The title of the feed
             * @param {String} url The url of the feed
             */
            'fileselect',
            /**
             * @event deletefile Fired when a node is deleted
             * @param {ProjectPanell} this
             * @param {String} id The id of the node to remove
             * @param {String} url The url of the feed
             */
            'deletefile',
            /**
             * @event toolselect Fired when a tool is selected
             * @param {toolselect} this
             * @param {String} tool The tool of the feed
             * @param {String} url The url of the tool
             */
            'toolselect'
            
        );
        this.createContextMenu();
        this.callParent(arguments);
    },   

    // template method
    afterRender: function(){
        this.callParent(arguments);
    },
    
    createListTabs: function() {
        this.tabPanel = Ext.create('Ext.tab.Panel', {
            enableTabScroll: true,
            tabPosition: 'top',
            border: false,
            layout: "fit",
            items: [this.createProjectView(),this.createToolView()]
        });
        return this.tabPanel;
    },
    
    /**
     * Create the DataView to be used for the feed list.
     * @private
     * @return {Ext.view.View}
     */
    createProjectView: function(){
        //Ext.ux.tree.TreeGrid is no longer a Ux. You can simply use a tree.TreePanel
    	this.tree = Ext.create('Ext.tree.Panel', {
            //title: 'Core Team Projects',
            preventHeader: true,
            title: "Projects",
            //anchor: '100% 100%',
            collapsible: false,
            useArrows: true,
            rootVisible: false,
            store: Ext.create('Ext.data.TreeStore', {
            	model: 'File',
            	autoLoad: true,
            	proxy: {
                	type: 'ajax',
                	//the store will get the content from the .json file
                	url: 'files/FileList.groovy'
            		}
    			}),
            singleExpand: false,
            //the 'columns' property is now 'headers'
            columns: [{
                xtype: 'treecolumn', //this is so we know which column will show the tree
                hideTitle: true,
                text: 'Projects',
                flex: 2,
                sortable: true,
                dataIndex: 'file'
            }],
            listeners: {
            	scope: this,
                selectionchange: this.onSelectionChange,
                itemcontextmenu: this.onContainerContextMenu,
                itemremove: this.onItemRemove,
                itemappend: this.onItemAppend
                },
            tbar: [
		        { xtype: 'button', text: 'Create Project',
		          handler: function() {
		                var projectCreatorDialog = Ext.create('com.dipforge.IDE.CreateProjectDialog', {
			    						toolBar:this
			    			});
			    		projectCreatorDialog.show();
		            } }
		        ]});
    	return this.tree;
    },
    
    
    /**
     * Create the DataView to be used for the feed list.
     * @private
     * @return {Ext.view.View}
     */
    createToolView: function(){
        //Ext.ux.tree.TreeGrid is no longer a Ux. You can simply use a tree.TreePanel
        this.tools = Ext.create('Ext.tree.Panel', {
            preventHeader: true,
            title: "Tools",
            //anchor: '100% 100%',
            collapsible: false,
            useArrows: true,
            rootVisible: false,
            store: Ext.create('Ext.data.TreeStore', {
            	model: 'File',
            	autoLoad: true,
            	proxy: {
                	type: 'ajax',
                	//the store will get the content from the .json file
                	url: 'tools/ToolList.groovy'
            		}
    			}),
            singleExpand: false,
            //the 'columns' property is now 'headers'
            columns: [{
                xtype: 'treecolumn', //this is so we know which column will show the tree
                hideTitle: true,
                text: 'Tools',
                flex: 2,
                sortable: true,
                dataIndex: 'file'
            }],
            listeners: {
            	scope: this,
                selectionchange: this.onToolSelectionChange
                }});
    	return this.tools;
    },
    
    createContextMenu: function() {
    	this.mnuContext = new Ext.menu.Menu({
    		items: [{
        		id: 'create-file',
        		text: 'Create'
    		},{
        		id: 'delete-file',
        		text: 'Delete'
    		},{
        		id: 'rename-file',
        		text: 'Rename'
    		}],
    		bubbleEvents: ['deletefile'],
    		listeners: {
        		click: function(menu,item,objEvent,options) {
        			switch (item.id) {
                		case 'create-file':
                			if ((this.record.data.project_dir == false) &&
                				!this.record.get('leaf') &&
	    						(this.record.data.project != "documentation")) {      			
		                		var fileDialog = Ext.create('com.dipforge.IDE.FilePanelDialog', {
		                			record: this.record});
	    						fileDialog.show();
    						}
                			break;
    					case 'delete-file':
                			var result = ""
							if ((this.record.data.project_dir == false) &&
	    						(this.record.data.project != "documentation")&&
	    						(this.record.data.path != "/project.properties")) {      			
	                			var fileDialog = Ext.create('com.dipforge.IDE.DeleteFileDialog', {
	    							fileName: this.record.data.path,
	    							project: this.record.data.project,
	    							fileId: this.record.id,
	    							record: this.record,
									menu: menu});
	    						fileDialog.show();
	    					} else if ((this.record.data.project_dir) &&
	    						(this.record.data.project != "public") &&
	    						(this.record.data.project != "desktop") &&
	    						(this.record.data.project != "documentation") &&
	    						(this.record.data.project != "ide") &&
	    						(this.record.data.project != "test") &&
	    						(this.record.data.project != "dipforge")) { 
                    			var projectDialog = Ext.create('com.dipforge.IDE.DeleteProjectDialog', {
	    							project: this.record.data.project,
	    							fileId: this.record.id,
	    							record: this.record});
	    						projectDialog.show();
                    		}
                    		break;
                    	case 'rename-file':
                			var result = ""
							if ((this.record.data.project_dir == false) &&
	    						(this.record.data.project != "documentation")&&
	    						(this.record.data.path != "/project.properties")) {      			
	                			var renameFileDialog = Ext.create('com.dipforge.IDE.RenameFileDialog', {
	    							fileName: this.record.data.path,
	    							project: this.record.data.project,
	    							fileId: this.record.id,
	    							record: this.record,
									menu: menu});
	    						renameFileDialog.show();
	    					}
            		}
        		}
    		}
		});
    },
    
    /**
     * Used when view selection changes so we can disable toolbar buttons.
     * @private
     */
    onSelectionChange: function(model,records){
    	if (records.length != 0 && records[0].get('leaf')) {
        	var record = records[0]
        	this.fireEvent('fileselect', this, record.get('project'), 
        		record.get('file'), record.get('path'), record.get('editor'), record.get('mode'));
        }
    },
    
    
    /**
     * Used when view selection changes so we can disable toolbar buttons.
     * @private
     */
    onToolSelectionChange: function(model,records){
    	var record = records[0]
        if (record.get('leaf')) {
            this.fireEvent('toolselect', this, record.get('project'), 
    		    record.get('file'), record.get('path'), record.get('editor'), record.get('mode'));
    	}
    },
    
    
    /**
     * Used when an item is removed.
     * @private
     */
    onItemRemove: function(model,record){
        if (record.get('leaf')) {
        	this.fireEvent('deletefile', this, record.get('project'),record.get('path'));
        }
    },
    
    /**
     * Used when
     */
    onItemAppend: function(model,treeNode,number,options) {
    	if (treeNode.get('leaf') && treeNode.get("newFile")) {
        	this.fireEvent('fileselect', this, treeNode.get('project'), 
        		treeNode.get('file'), treeNode.get('path'), 
        		treeNode.get('editor'), treeNode.get('mode'));
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