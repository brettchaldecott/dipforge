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
            border: false
        };
        this.callParent(arguments);
    },
    
    // template method
    afterRender: function(){
        this.callParent(arguments);
    },
    
    /**
     * Add a new editor
     * @param {String} project The name of the project
     * @param {String} fileName The file name of the project
     * @param {String} path The path of the file
     * @param {String} editor The type of editor
     * @param {mode} The mode of the editor
     */
    addEditor: function(project, fileName, path, editor, mode){
        var id = project + ":" + path
        var active = this.getComponent(id);
        var self = this
        if (!active) {
        	if (editor == "ace-project-types" || editor == "ace-project-methods" || 
                editor == "ace-project-services" || editor == "ace") {
                active = this.add(Ext.create('Ext.panel.Panel', {
                    layout: "fit",
                    html: '<iframe src="/ide/editor.gsp?project=' + project + '&path=' + path + '&fileName=' + fileName + '&editor=' + editor + '&mode=' + mode + '" width="100%" height="100%" frameborder=\"0\" scrolling="no" marginheight="0" marginwidth="0"/>',
                    itemId: id,
                    id: id,
                    title: fileName,
                    url: path,
                    closable: true,
                    width: '100%',
                    height: '100%'
                }));
                this.setActiveTab(active);
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
    },
    
    
    /**
     * Remove an editor
     * @param {String} project The name of the project
     * @param {String} path The path of the file
     */
    removeEditor: function(project, path){
        var id = project + ":" + path
        var component = this.getComponent(id)
        if (component) {
        	this.remove(this.getComponent(id));
        }
    }
});