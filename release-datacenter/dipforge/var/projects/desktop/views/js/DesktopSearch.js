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

Ext.define('Desktop.DesktopSearch', {
    extend: 'Ext.container.Container',
    alias: 'widget.desktopsearch',
    layout: 'fit',
    border: 1,
    style: "padding-top:5px",
    /**
     * The function to init the component
     */
    initComponent: function(){
        Ext.apply(this, {
            items: [this.createSearchField()]
        });
        this.addEvents(
            /**
             * @event feedselect Fired when a feed is selected
             * @param {FeedPanel} this
             * @param {String} title The title of the feed
             * @param {String} url The url of the feed
             */
            'desktopsearch'
        );
        this.callParent(arguments);
    },
    /**
     * This method is called to create the search field
     */
    createSearchField : function() {
    	this.searchfield = Ext.create('Ext.form.field.Text', {
        name: 'search',
        value: 'search'
      });
      return this.searchfield;
    }
});