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

Ext.define('com.dipforge.Editor.App', {
    extend: 'Ext.container.Viewport',
    border: 0,
    padding: 0,
	
	initComponent: function(){
    	
    	Ext.apply(this, {
            layout: {
                        type: 'border'
                    },
            border: 0,
            padding: 0,
            items: [this.createEditorPanel()]
        });
        this.callParent(arguments);
    },    
 	
 	/**
     * Create the editor panel
     * @private
     * @return {com.dipforge.IDE.EditorPanel} feedInfo
     */
    createEditorPanel: function(){
    	var editor = ace.edit("editor");
        var JavaScriptMode = require("ace/mode/groovy").Mode;
        editor.getSession().setMode(new JavaScriptMode());
        this.editorpanel = Ext.create('Ext.panel.Panel', {
                layout: "fit",
                items: editor,
                itemId: 'test',
                id: path,
                title: fileName,
                url: path,
                width: '100%',
                height: '100%'
            });
		editor.resize();
        return this.editorpanel;
    }   
});