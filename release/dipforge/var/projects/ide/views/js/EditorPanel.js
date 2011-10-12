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
        this.callParent(arguments);
    },
    
    // template method
    afterRender: function(){
        this.callParent(arguments);
    },
    
    /**
     * Add a new feed
     * @param {String} title The title of the feed
     * @param {String} url The url of the feed
     */
    addEditor: function(project, fileName, path, editor, mode){
        var id = project + ":" + path
        var active = this.getComponent(id);
        var self = this
        if (!active) {
        	if (editor == "ace") {
        		Ext.Ajax.request({
                    url: 'files/FileRetriever.groovy',
                    params: {
                        project: project,
                        path: path
                    },
                    success: function(response){
                        var fileInfo = Ext.decode(response.responseText);
                  		active = self.add(Ext.create('Ext.panel.Panel', {
                            layout: "fit",
                            html: '<div id="id|' + id + '" style="height: 100%; width: 100%">' + fileInfo.contents + '</div>',
                            itemId: id,
                            id: id,
                            title: fileName,
                            url: path,
                            closable: true,
                            width: '100%',
                            height: '100%'
                        }));
                        self.setActiveTab(active);
                
                        var el = Ext.get("id|" + id)
                        var editor = ace.edit(el.dom);
                        var JavaScriptMode = require(mode).Mode;
                        editor.getSession().setMode(new JavaScriptMode());
                        editor.resize();
                   }
                });
        		
            } else if (editor == "image") {
            	active = this.add(Ext.create('Ext.panel.Panel', {
                    layout: "fit",
                    html: '<img src="/DipforgeWeb/' + project + '/' + path.substring(7) + '"/>',
                    itemId: id,
                    id: id,
                    title: fileName,
                    url: path,
                    closable: true,
                    width: '100%',
                    height: '100%'
                }));
                this.setActiveTab(active);
            }
        } else {
            this.setActiveTab(active);
        }
    }
});