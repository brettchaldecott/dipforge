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
    layout: 'auto',
    title: '<image src="favicon.ico" style="vertical-align:middle horizontal-align:center" height=20 width=20/>&nbsp;Dipforge',
    //titleCollapse: true,

    initComponent: function(){
        Ext.apply(this, {
            items: [this.createView(),this.createLHSContainer(),this.createShortFeedContainer()]
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
        this.callParent(arguments);
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
    },


    /**
     * This method creates the master container
     */
    createLHSContainer: function() {
        this.lhsContainer = Ext.create('widget.desktopsearch', {
            minWidth: 250,
            listeners: {
                scope: this,
                desktopsearch: this.onDesktopSearch
            }
        });
		  return this.lhsContainer;
    },
    
    /**
     * This function creates the feed short cut bar
     */
    createShortFeedContainer: function() {
        this.shortCutFeedsContainer = Ext.create('widget.desktopfeedshortcutbar', {
            minWidth: 250
        });
		  return this.shortCutFeedsContainer;
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
                    selectionchange: this.onSelectionChange
                }
            },
            listeners: {
                scope: this,
                contextmenu: this.onContextMenu
            },
            trackOver: true,
            style: "padding-top:5px",
            cls: 'feed-list',
            itemSelector: '.feed-list-item',
            overItemCls: 'feed-list-item-hover',
            tpl: '<tpl for="."><div class="feed-list-item"><table border=0 cellpadding=0 cellspacing=0 valign="middle"><tr><td><image src="{image}" height=20 width=20/>&nbsp;</td><td>{title}</td></tr></table></div></tpl>'
        });
        return this.view;
    },

    /**
     * Used when view selection changes so we can disable toolbar buttons.
     * @private
     */
    onSelectionChange: function(){
        var selected = this.getSelectedItem();
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
        if (rec) {
            this.fireEvent('feedselect', this, rec.get('title'), rec.get('url'));
        }
    },

    /**
     * Gets the currently selected record in the view.
     * @private
     * @return {Ext.data.Model} Returns the selected model. false if nothing is selected.
     */
    getSelectedItem: function(){
        return this.view.getSelectionModel().getSelection()[0] || false;
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
