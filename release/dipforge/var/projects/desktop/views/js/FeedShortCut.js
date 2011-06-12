/**
 * @class Desktop.DesktopSearch
 * @extends Ext.container.Viewport
 *
 * The main FeedViewer application
 * 
 * @constructor
 * Create a new Feed Viewer app
 * @param {Object} config The config object
 */

Ext.define('Desktop.DesktopFeedShortCutBar', {
    extend: 'Ext.container.Container',
    alias: 'widget.desktopfeedshortcutbar',
    layout: 'fit',
    width: 250,
    height: 400,
    //autoScroll: true, 
    //style: "padding-top:5px",
    /**
     * The function to init the component
     */
    initComponent: function(){
    	  this.template = '<tpl for="."><div class="feed-list-item"><table border=0  cellpadding=0 cellspacing=0 valign="middle"><tr><td rowspan=2><image src="{image}" height=20 width=20/>&nbsp;</td><td><b>{author}</b></td></tr><tr><td>{msg}</td></tr></table></div></tpl>';
    	  Ext.apply(this, {
    	  		items: this.createMasterContainer()});
        this.addEvents(
            /**
             * @event feedselect Fired when a feed is selected
             * @param {FeedPanel} this
             * @param {String} title The title of the feed
             * @param {String} url The url of the feed
             */
            'shortcutdisplay'
        );
        this.callParent(arguments);
    },
    
    
    createMasterContainer: function() {
    	var masterContainer = Ext.create('Ext.container.Container', {
    		 layout: 'anchor',
    		 items: [this.createFeedShortCut('feeds/UserFeedShortCut.groovy','100% 40%'),
              //this.createSeperator(),
              this.createFeedShortCut('feeds/FeedShortCut.groovy','100% 60%')]});
       return masterContainer;
    	},
    
    
    
    /**
     * Create the DataView to be used for the feed list.
     * @private
     * @return {Ext.view.View}
     */
    createFeedShortCut: function(feedUrl,anchorInfo){
        var feed = Ext.create('widget.dataview', {
            store: Ext.create('Ext.data.Store', {
                model: 'FeedShortCut',
                proxy: {
                    type: 'ajax',
                    url : feedUrl,
                    reader: {
                        type: 'json',
                        root: 'feeds'
                    }
                },
                autoLoad: true
            }),
            selModel: {
                mode: 'SINGLE',
                listeners: {
                    scope: this,
                    selectionchange: this.onSelectionChange
                }
            },
            listeners: {
                scope: this,
                contextmenu: this.onContextMenu
            },
            trackOver: true,
            //style: "padding-top:5px",
            cls: 'feed-list',
            itemSelector: '.feed-list-item',
            overItemCls: 'feed-list-item-hover',
            tpl: this.template,
            autoScroll: true,
            anchor: anchorInfo
            //height: storeHeight
        });
        return feed;
    },
    
    createSeperator: function() {
    	// explicitly create a Container
		var linesperator = Ext.create('Ext.container.Container', {
		    width: 230,
		    border: 1,
		    cls: 'horizontal-seperator'
		});
		return linesperator;
    },
    
    /**
     * Used when view selection changes so we can disable toolbar buttons.
     * @private
     */
    onSelectionChange: function(){
        // not implemented
    },
    
    /**
     * Listens for the context menu event on the view
     * @private
     */
    onContextMenu: function(view, index, el, event){
        // ignore for the time being.
    }
});

