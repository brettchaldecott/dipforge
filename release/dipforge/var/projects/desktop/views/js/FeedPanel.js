/**
 * @class FeedViewer.FeedPanel
 * @extends Ext.panel.Panel
 *
 * Shows a list of available feeds. Also has the ability to add/remove and load feeds.
 *
 * @constructor
 * Create a new Feed Panel
 * @param {Object} config The config object
 */

Ext.define('FeedViewer.FeedPanel', {
    extend: 'Ext.panel.Panel',

    alias: 'widget.feedpanel',

    //animCollapse: true,
    layout: 'fit',
    //title: 'Dipforge',
    titleCollapse: true,

    initComponent: function(){
        Ext.apply(this, {
            items: [
            	Ext.create('Ext.container.Container', {
                   layout: {
                       type: 'anchor'
                   },
            	    items: [this.createView(),/*this.createLHSContainer(), */this.createShortFeedContainer()]
            	})]
        });
        
        
        this.addEvents(
            /**
             * @event feedselect Fired when a feed is selected
             * @param {FeedPanel} this
             * @param {String} title The title of the feed
             * @param {String} url The url of the feed
             */
            'feedselect',
            /**
             * @event feedentryshortcutselect Fired when a feed entry short is selected
             * @param {FeedPanel} this
             * @param {String} title The title of the feed
             * @param {String} url The url of the feed
             */
            'feedentryshortcutselect'
        );
        this.setIconCls("dipforge-icon");
        this.callParent(arguments);
        //var task = Ext.TaskManager.start({
        //     run: this.reloadFeeds,
        //     interval: 1000
        //});
        var runner = new Ext.util.TaskRunner();
         var task = runner.start({
            scope: this,
             run: this.reloadFeeds,
             interval: 1000 * 60
         });
        
    },   

    // template method
    afterRender: function(){
        this.callParent(arguments);
        var view = this.view;
        view.getStore().load({
            scope   : this.view.getStore(),
            callback: function(records, operation, success) {
                	view.getSelectionModel().select(view.store.first());
            }
        });
        var feeds = this.feeds;
        feeds.getStore().load({
            scope   : this.feeds.getStore(),
            callback: function(records, operation, success) {
                    //feeds.getSelectionModel().select(feeds.store.first());
            }
        });
        
        
    },
    
    reloadFeeds: function() {
        var feeds = this.feeds;
        feeds.getStore().load({
            scope   : this.feeds.getStore(),
            callback: function(records, operation, success) {
                    //feeds.getSelectionModel().select(feeds.store.first());
            }
        });
    },

    /**
     * This method creates the master container
     */
    createLHSContainer: function() {
        this.lhsContainer = Ext.create('widget.desktopsearch', {
            minWidth: 250,
            height:50,
            anchor: '100% 5%',
            listeners: {
                scope: this,
                desktopsearch: this.onDesktopSearch
            }
        });
		  return this.lhsContainer;
    },
    
    /**
     * Create the DataView to be used for the feed list.
     * @private
     * @return {Ext.view.View}
     */
    createShortFeedContainer: function(){
        this.feeds = Ext.create('widget.dataview', {
            store: Ext.create('Ext.data.Store', {
                model: 'FeedShortCut',
                proxy: {
                    type: 'ajax',
                    url : 'feeds/FeedShortCut.groovy',
                    reader: {
                        type: 'json',
                        root: 'feeds'
                    }
                }
            }),
            selModel: {
                mode: 'SINGLE',
                listeners: {
                    scope: this,
                    selectionchange: this.onFeedsSelectionChange
                }
            },
            listeners: {
                scope: this,
                contextmenu: this.onContextMenu
            },
            //height: 250,
            width: 200,
            anchor: '100% 45%',
            trackOver: true,
            autoScroll: true,
            style: "padding-top:5px",
            cls: 'feed-list',
            itemSelector: '.feed-list-item',
            overItemCls: 'feed-list-item-hover',
            tpl: '<tpl for="."><div class="feed-list-item"><table border=0  cellpadding=0 cellspacing=0 valign="middle"><tr><td><b>{author}</b><br><b><i>{title}</i></b></td></tr><tr><td>{msg}</td></tr></table></div></tpl>'
        });
        return this.feeds;
    },
    

    /**
     * Create the DataView to be used for the feed list.
     * @private
     * @return {Ext.view.View}
     */
    createView: function(){
        this.view = Ext.create('widget.dataview', {
            store: Ext.create('Ext.data.Store', {
                model: 'Feed',
                proxy: {
                    type: 'ajax',
                    url : 'applications/ApplicationInfo.groovy',
                    reader: {
                        type: 'json',
                        root: 'applications'
                    }
                }
            }),
            selModel: {
                mode: 'SINGLE',
                listeners: {
                    scope: this,
                    selectionchange: this.onApplicationSelectionChange
                }
            },
            listeners: {
                scope: this,
                contextmenu: this.onContextMenu,
                itemclick: this.onApplicationSelectionChange
            },
            //height: 250,
            width: 200,
            anchor: '100% 55%',
            trackOver: true,
            autoScroll: true,
            style: "padding-top:5px",
            cls: 'feed-list',
            itemSelector: '.feed-list-item',
            overItemCls: 'feed-list-item-hover',
            tpl: '<tpl for="."><div class="feed-list-item"><table border=0 cellpadding=0 cellspacing=0><tr><td style="height:20px; vertical-align:middle; padding-top:3px; padding-left:3px;"><image src="{image}" height=20 width=20/>&nbsp;</td><td style="vertical-align:middle">{title}</td></tr></table></div></tpl>'
        });
        return this.view;
    },


    /**
     * Used when view selection changes so we can disable toolbar buttons.
     * @private
     */
    onApplicationSelectionChange: function(){
        var selected = this.getApplicationSelectedItem();
        this.loadFeed(selected);
    },
    
    /**
     * Used when view selection changes so we can disable toolbar buttons.
     * @private
     */
    onFeedsSelectionChange: function(){
        var selected = this.getFeedsSelectedItem();
        this.loadFeed(selected);
    },
    
    /**
     *
     */
    onDesktopSearch: function() {
    },

    /**
     * React to the load feed menu click.
     * @private
     */
    /*onLoadClick: function(){
        this.loadFeed(this.menu.activeFeed);
    },*/

    /**
     * Loads a feed.
     * @private
     * @param {Ext.data.Model} rec The feed
     */
    loadFeed: function(rec){
        // Load a feed
        if (rec) {
            // Check if the author is the Social feed
            // If So launch the Feed application
            if (rec.get('author') !== null && rec.get('author') == 'Social Feed') {
                this.fireEvent('feedselect', this, 'Feed', rec.get('url'));
            } else {
                this.fireEvent('feedselect', this, rec.get('title'), rec.get('url'));
            }
            
        }
    },

    /**
     * Gets the currently selected record in the view.
     * @private
     * @return {Ext.data.Model} Returns the selected model. false if nothing is selected.
     */
    getApplicationSelectedItem: function(){
        return this.view.getSelectionModel().getSelection()[0] || false;
    },

    /**
     * Gets the currently selected record in the view.
     * @private
     * @return {Ext.data.Model} Returns the selected model. false if nothing is selected.
     */
    getFeedsSelectedItem: function(){
        return this.feeds.getSelectionModel().getSelection()[0] || false;
    },

    /**
     * Listens for the context menu event on the view
     * @private
     */
    onContextMenu: function(view, index, el, event){
        var menu = this.menu;

        event.stopEvent();
        menu.activeFeed = view.store.getAt(index);
        menu.showAt(event.getXY());
    },

    /**
     * React to a feed being removed
     * @private
     */
    onRemoveFeedClick: function(){
        var active = this.menu.activeFeed || this.getSelectedItem();


        this.animateNode(this.view.getNode(active), 1, 0, {
            scope: this,
            afteranimate: function(){
                this.view.store.remove(active);
            }
        });
        this.fireEvent('feedremove', this, active.get('title'), active.get('url'));

    },

    /**
     * React to a feed attempting to be added
     * @private
     */
    onAddFeedClick: function(){
        var win = Ext.create('widget.feedwindow', {
            listeners: {
                scope: this,
                feedvalid: this.onFeedValid
            }
        });
        win.show();
    },

    /**
     * React to a validation on a feed passing
     * @private
     * @param {FeedViewer.FeedWindow} win
     * @param {String} title The title of the feed
     * @param {String} url The url of the feed
     */
    onFeedValid: function(win, title, url){
        var view = this.view,
            store = view.store,
            rec;

        rec = store.add({
            url: url,
            title: title
        })[0];
        this.animateNode(view.getNode(rec), 0, 1);
    },

    /**
     * Animate a node in the view when it is added/removed
     * @private
     * @param {Mixed} el The element to animate
     * @param {Number} start The start opacity
     * @param {Number} end The end opacity
     * @param {Object} listeners (optional) Any listeners
     */
    animateNode: function(el, start, end, listeners){
        Ext.create('Ext.fx.Anim', {
            target: Ext.get(el),
            duration: 500,
            from: {
                opacity: start
            },
            to: {
                opacity: end
            },
            listeners: listeners
         });
    },

    // Inherit docs
    onDestroy: function(){
        this.callParent(arguments);
        this.menu.destroy();
    }
});