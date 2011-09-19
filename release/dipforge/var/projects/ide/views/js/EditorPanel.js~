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
Ext.define('com.dipforge.IDE.EditorPanel', {
    
    extend: 'Ext.tab.Panel',
    alias: 'widget.editorpanel',
    tabPosition: 'bottom',
    
    maxTabWidth: 230,
    border: false,
    plugins: [{
                ptype: 'tabscrollermenu',
                maxText  : 15,
                pageSize : 5
            }],
    
    initComponent: function() {
        this.tabBar = {
            border: true
        };
        //this.addFeed("cnn","http://www.cnn.com");
        this.callParent();
    },
    
    // template method
    afterRender: function(){
        this.callParent(arguments);
        this.addFeed("Editor","editor.gsp");
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
               layout: "fit",
                items: this.addContainer(url),
                itemId: title,
                title: title,
                url: url,
                closable: true,
                width: '100%',
               height: '100%'
            }));
        }
        this.setActiveTab(active);
    },
    
    
    addContainer: function(url) {
       var editor = new Ext.ux.AceEditor();
       editor.setValue("int bob = 0;",  {
            mode: "groovy",
            callback: function(doc)
            {
            }
       });
       return editor;
    }    
});