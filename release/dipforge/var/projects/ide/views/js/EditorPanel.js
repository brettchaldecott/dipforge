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
        	if (editor == "ace-project-types") {
        		Ext.Ajax.request({
                    url: 'files/FileRetriever.groovy',
                    params: {
                        project: project,
                        path: path
                    },
                    success: function(response){
                        var fileInfo = Ext.decode(response.responseText);
                  		active = self.add(Ext.create('Ext.panel.Panel', {
                  			tbar: [
                                { xtype: 'button', text: 'Save',
                                    handler: function() {
                                        Ext.Ajax.request({
                                            url: 'files/FileSave.groovy',
                                            params: {
                                                project: project,
                                                path: path,
                                                content: editor.getSession().getValue()
                                        }})
                                    }},
                                { xtype: 'button', text: 'Publish',
                                    handler: function() {
                                        Ext.Ajax.request({
                                            url: 'types/PublishTypes.groovy',
                                            params: {
                                                project: project
	                                        },
	                                        failure: function(response) {
	 	                                    	Ext.Msg.show({
												     title:"Failed to create the project [" + project + "]",
												     msg: response.responseText,
												     autoScroll: true,
												     buttons: Ext.Msg.CANCEL
												});
	 	                                    }
                                        })
                                    }},
                                { xtype: 'button', text: 'Find',
                                    handler: function() {
                                        editor.find(Ext.getCmp("search:"+id).value,{
                                            backwards: false,
                                            wrap: true,
                                            caseSensitive: false,
                                            wholeWord: false,
                                            regExp: false
                                            });
                                    }},
                                { xtype: 'textfield', id: "search:"+id, 
                                    itemId: "search:"+id, name: "search",
                                    emptyText: 'enter search term' },
                                { xtype: 'tbtext', text: 'and' },
                                { xtype: 'textfield', id: "replace:"+id, 
                                    itemId: "replace:"+id, name: "replace",
                                    emptyText: 'enter replace term' },
                                { xtype: 'button', text: 'Replace',
                                    handler: function() {
                                        editor.find(Ext.getCmp("search:"+id).value);
                                        editor.replace(Ext.getCmp("replace:"+id).value);
                                    }}],
                            layout: "fit",
                            html: '<div id="id|' + id + '" style="height: 100%; width: 100%">' + fileInfo.contents + '</div>',
                            itemId: id,
                            id: id,
                            title: fileName,
                            url: path,
                            closable: true,
                            width: '100%',
                            height: '100%',
                            editor: null,
                            project: project,
                            path: path
                        }));
                        self.setActiveTab(active);
                        
                        var canon = require("pilot/canon");
                        
                        // Fake-Save, works from the editor and the command line.
                        canon.addCommand({
                            name: "save",
                            bindKey: {
                                win: "Ctrl-S",
                                mac: "Command-S",
                                sender: "editor"
                            },
                            exec: function() {
                                Ext.Ajax.request({
                                    url: 'files/FileSave.groovy',
                                    params: {
                                        project: project,
                                        path: path,
                                        content: editor.getSession().getValue()
                                }})
                            }
                        });
                        
                
                        var el = Ext.get("id|'" + id)
                        var editor = ace.edit(el.dom);
                        var JavaScriptMode = require(mode).Mode;
                        editor.getSession().setMode(new JavaScriptMode());
                        editor.resize();
                        editor.getSession().setUseSoftTabs(true);
                        editor.getSession().setTabSize(4);
                        
                        fileInfo.editor = editor;
                   }
                });
        		
            } else if (editor == "ace") {
        		Ext.Ajax.request({
                    url: 'files/FileRetriever.groovy',
                    params: {
                        project: project,
                        path: path
                    },
                    success: function(response){
                        var fileInfo = Ext.decode(response.responseText);
                  		active = self.add(Ext.create('Ext.panel.Panel', {
                  			tbar: [
                                { xtype: 'button', text: 'Save',
                                    handler: function() {
                                        Ext.Ajax.request({
                                            url: 'files/FileSave.groovy',
                                            params: {
                                                project: project,
                                                path: path,
                                                content: editor.getSession().getValue()
                                        }})
                                    }},
                                { xtype: 'button', text: 'Find',
                                    handler: function() {
                                        editor.find(Ext.getCmp("search:"+id).value,{
                                            backwards: false,
                                            wrap: true,
                                            caseSensitive: false,
                                            wholeWord: false,
                                            regExp: false
                                            });
                                    }},
                                { xtype: 'textfield', id: "search:"+id, 
                                    itemId: "search:"+id, name: "search",
                                    emptyText: 'enter search term' },
                                { xtype: 'tbtext', text: 'and' },
                                { xtype: 'textfield', id: "replace:"+id, 
                                    itemId: "replace:"+id, name: "replace",
                                    emptyText: 'enter replace term' },
                                { xtype: 'button', text: 'Replace',
                                    handler: function() {
                                        editor.find(Ext.getCmp("search:"+id).value);
                                        editor.replace(Ext.getCmp("replace:"+id).value);
                                    }}],
                            layout: "fit",
                            html: '<div id="id|' + id + '" style="height: 100%; width: 100%">' + fileInfo.contents + '</div>',
                            itemId: id,
                            id: id,
                            title: fileName,
                            url: path,
                            closable: true,
                            width: '100%',
                            height: '100%',
                            editor: null,
                            project: project,
                            path: path
                        }));
                        self.setActiveTab(active);
                        
                        var canon = require("pilot/canon");
                        
                        // Fake-Save, works from the editor and the command line.
                        canon.addCommand({
                            name: "save",
                            bindKey: {
                                win: "Ctrl-S",
                                mac: "Command-S",
                                sender: "editor"
                            },
                            exec: function() {
                                Ext.Ajax.request({
                                    url: 'files/FileSave.groovy',
                                    params: {
                                        project: project,
                                        path: path,
                                        content: editor.getSession().getValue()
                                }})
                            }
                        });
                
                        var el = Ext.get("id|" + id)
                        var editor = ace.edit(el.dom);
                        var JavaScriptMode = require(mode).Mode;
                        editor.getSession().setMode(new JavaScriptMode());
                        editor.resize();
                        editor.getSession().setUseSoftTabs(true);
                        editor.getSession().setTabSize(4);
                        
                        fileInfo.editor = editor;
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