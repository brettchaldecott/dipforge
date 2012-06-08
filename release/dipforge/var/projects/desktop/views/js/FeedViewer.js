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
            items: [this.createFeedPanel(), this.createFeedInfo()]
        });
        this.callParent(arguments);
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
            minWidth: 250,
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
