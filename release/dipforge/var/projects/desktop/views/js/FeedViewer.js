/**
 * @class FeedViewer.FeedViewer
 * @extends Ext.container.Viewport
 *
 * The main FeedViewer application
 * 
 * @constructor
 * Create a new Feed Viewer app
 * @param {Object} config The config object
 */

Ext.define('FeedViewer.App', {
    extend: 'Ext.container.Viewport',
    
    initComponent: function(){
        
        Ext.define('Feed', {
            extend: 'Ext.data.Model',
            fields: ['image','title', 'url']
        });
        
        Ext.define('FeedShortCut', {
            extend: 'Ext.data.Model',
            fields: ['image','title','author','msg','url']
        });

        Ext.define('FeedItem', {
            extend: 'Ext.data.Model',
            fields: ['title', 'author', {
                name: 'pubDate',
                type: 'date'
            }, 'link', 'description', 'content']
        });
        
        Ext.apply(this, {
            layout: 'border',
            padding: 5,
            items: [this.createToolBar(), this.createFeedPanel(), this.createFeedInfo()]
        });
        this.callParent(arguments);
    },
    
    
    /**
     * This method is called to create a new tool bar
     */
    createToolBar: function() {
        this.toolbar = Ext.create('Ext.toolbar.Toolbar', {
            region: 'north',
            width   : '100%',
            height: 45,
            //style: 'background-color: black !important;',
            margin: '0 0 5px 0',
            items: [
                {
                    // xtype: 'button', // default for Toolbars
                    iconCls: 'dipforge-logo',
                    scale: 'large',
                    height: 40,
                    width: 140,
                    //padding: '0 0 0 0',
                    url: 'http://dipforge.sourceforge.net/',
                    text: 'dip<span style="color:#b4281a;">forge</span>'
                },
                // begin using the right-justified button container
                '->'//, // same as { xtype: 'tbfill' }
                //{
                //    xtype    : 'textfield',
                //    name     : 'field1',
                //    emptyText: 'enter search term'
                //},
                // add a vertical separator bar between toolbar items
                //'-', // same as {xtype: 'tbseparator'} to create Ext.toolbar.Separator
                //'text 1', // same as {xtype: 'tbtext', text: 'text1'} to create Ext.toolbar.TextItem
                //{ xtype: 'tbspacer' },// same as ' ' to create Ext.toolbar.Spacer
                //'text 2',
                //{ xtype: 'tbspacer', width: 50 }, // add a 50px space
                //'text 3'
            ]
        });
        
        
        return this.toolbar;
    },
    
    
    
    /**
     * Create the list of fields to be shown on the left
     * @private
     * @return {FeedViewer.FeedPanel} feedPanel
     */
    createFeedPanel: function(){
        this.feedPanel = Ext.create('widget.feedpanel', {
            region: 'west',
            collapsible: false,
            width: 250,
            floatable: false,
            split: true,
            width: 250,
            listeners: {
                scope: this,
                feedselect: this.onFeedSelect
            }
        });
        return this.feedPanel;
    },
    
    /**
     * Create the feed info container
     * @private
     * @return {FeedViewer.FeedInfo} feedInfo
     */
    createFeedInfo: function(){
        this.feedInfo = Ext.create('widget.feedinfo', {
            region: 'center',
            minWidth: 300
        });
        return this.feedInfo;
    },
    
    /**
     * Reacts to a feed being selected
     * @private
     */
    onFeedSelect: function(feed, title, url){
        this.feedInfo.addFeed(title, url);
    }
});
