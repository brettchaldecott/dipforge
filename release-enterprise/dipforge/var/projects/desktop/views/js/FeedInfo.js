/**
 * @class FeedViewer.FeedInfo
 * @extends Ext.tab.Panel
 *
 * A container class for showing a series of feed details
 * 
 * @constructor
 * Create a new Feed Info
 * @param {Object} config The config object
 */
Ext.define('FeedViewer.FeedInfo', {
    
    extend: 'Ext.tab.Panel',
    alias: 'widget.feedinfo',
    
    //maxTabWidth: 230,
    border: 1,
    
    initComponent: function() {
        this.tabBar = {
            border: 0
        };
        
        this.callParent();
    },   
    
    /**
     * Add a new feed
     * @param {String} title The title of the feed
     * @param {String} url The url of the feed
     */
    addFeed: function(title, url){
        var active = this.getComponent(title);
        if (!active) {
            active = this.add(Ext.create('Ext.panel.Panel', {
                html: "<iframe style='border-radius:5px' src=\"" + url + "\" width=\"100%\" height=\"100%\" frameborder=\"0\" border=\"0\" marginheight=\"0\" marginwidth=\"0\"/>",
                itemId: title,
                title: title,
                url: url,
                border: 0,
                closable: true
            }));
        }
        this.setActiveTab(active);
    },
    
    /**
     * Listens for a new tab request
     * @private
     * @param {FeedViewer.FeedPost} The post
     * @param {Ext.data.Model} model The model
     */
    onTabOpen: function(post, rec) {
        var items = [],
            item;
        if (Ext.isArray(rec)) {
            Ext.each(rec, function(rec) {
                items.push({
                    inTab: true,
                    xtype: 'feedpost',
                    title: rec.get('title'),
                    closable: true,
                    data: rec.data,
                    active: rec
                });
            });
            this.add(items);
        }
        else {
            item = this.add({
                inTab: true,
                xtype: 'feedpost',
                title: rec.get('title'),
                closable: true,
                data: rec.data,
                active: rec
            });
            this.setActiveTab(item);
        }
    },
    
    /**
     * Listens for a row dblclick
     * @private
     * @param {FeedViewer.Detail} detail The detail
     * @param {Ext.data.Model} model The model
     */
    onRowDblClick: function(info, rec){
        this.onTabOpen(null, rec);
    },
    
    /**
     * Listens for the open all click
     * @private
     * @param {FeedViewer.FeedDetail}
     */
    onOpenAll: function(detail) {
        this.onTabOpen(null, detail.getFeedData());
    }
});