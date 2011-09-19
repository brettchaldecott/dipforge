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

Ext.define('com.dipforge.IDE.App', {
    extend: 'Ext.container.Viewport',
    
    initComponent: function(){
        
        Ext.apply(this, {
            layout: 'fit',
            padding: 0,
            items: [this.createMasterPanel()]
        });
        this.callParent(arguments);
    },
    
    
    
    
    /**
     * This method creats the tool bar
     */
    createMasterPanel: function() {
      this.masterpanel = Ext.create('widget.idepanel', {
            floatable: false
        });
        return this.masterpanel;  
    }
});