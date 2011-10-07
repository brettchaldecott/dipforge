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
                       type: 'anchor'},
            	    items: [this.createProjectView()]
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
             * @event feedentryshortcutselect Fired when a feed entry short is selected
             * @param {FeedPanel} this
             * @param {String} title The title of the feed
             * @param {String} url The url of the feed
             */
            'feedentryshortcutselect'
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
    createProjectView: function(){
        //Ext.ux.tree.TreeGrid is no longer a Ux. You can simply use a tree.TreePanel
    	this.tree = Ext.create('Ext.tree.Panel', {
            //title: 'Core Team Projects',
            preventHeader: true,
            anchor: '100% 100%',
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
                text: 'Project',
                flex: 2,
                sortable: true,
                dataIndex: 'file'
            }],
            selModel: {
                mode: 'SINGLE',
                    listeners: {
                        selectionchange: {
                        	scope: this,
                            fn: this.onSelectionChange
                        }
                    }}});
    	return this.tree;
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
    }
    
 });