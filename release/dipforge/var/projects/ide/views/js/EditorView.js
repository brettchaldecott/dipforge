/*
 * ide: Description
 * Copyright (C) Thu Dec 01 13:05:38 SAST 2011 owner
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * EditorView.js.js
 * @author admin
 */


Ext.define('com.dipforge.IDE.Editor', {
    extend: 'Ext.container.Viewport',
    alias: 'widget.EditorView',
    border: 0,
    padding: 0,
    
    initComponent: function(){
        
    	//we want to setup a model and store instead of using dataUrl
    	Ext.define('File', {
            extend: 'Ext.data.Model',
            fields: [
                {name: 'project',  type: 'string'},
                {name: 'file',     type: 'string'},
                {name: 'path',     type: 'string'},
                {name: 'user',     type: 'string'},
                {name: 'editor',   type: 'string'},
                {name: 'mode',     type: 'string'},
                {name: 'project_dir', type: 'boolean'}
            ]
    	});
    	
    	Ext.define('FileType', {
            extend: 'Ext.data.Model',
            fields: [
            	{name: 'name', type: 'string'},
            	{name: 'suffix', type: 'string'},
            	{name: 'editor', type: 'string'},
            	{name: 'mode', type: 'string'}]
        });

    	Ext.apply(this, {
            layout: {
                        type: 'fit'
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
        var editorpanel = Ext.create('Ext.panel.Panel', {
            layout: {
                        type: 'fit'
                    },
            preventHeader: true,
            region: 'center',
            height: '100%',
            width: '100%',
            autoScroll: false,
            border: '0'
        });
        editorpanel.add(
                Ext.create('Ext.panel.Panel', {
                preventHeader: true,
                region: 'center',
                height: '100%',
                width: '100%',
                border: '0',
                html: '<p>Loading ...... </p>'
            }));
        
		var project = this.project
		var path = this.path
		var fileName = this.fileName
		var mode = this.mode
        var id = this.entryName
		
		if (this.editor == "ace-project-types") {
    		Ext.Ajax.request({
                url: 'files/FileRetriever.groovy',
                params: {
                    project: project,
                    path: path
                },
                success: function(response){
                    var fileInfo = Ext.decode(response.responseText);
              		var editorPanelContent = Ext.create('Ext.panel.Panel', {
                        border: '0',
                        autoScroll: false,
              			layout: {
                            type: 'fit'
                        },
                        preventHeader: true,
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
                                            project: project,
                                            path: path,
                                            content: editor.getSession().getValue()
                                        },
                                        failure: function(response) {
 	                                    	Ext.Msg.show({
											     title:"Failed to publish the project [" + project + "] types",
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
                                    editor.focus();
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
                                    editor.focus();
                                }}],
                        layout: "fit",
                        html: '<div id="id|' + id + '" style="height:100%;width:100%;">' + fileInfo.contents + '</div>',
                        itemId: id,
                        id: id,
                        //title: fileName,
                        url: path,
                        //closable: true,
                        width: '100%',
                        height: '100%',
                        autoScroll: false,
                        editor: null,
                        project: project,
                        path: path
                    });
                    
                    editorpanel.removeAll()
                    editorpanel.add(editorPanelContent)
                    
                    var el = Ext.get("id|" + id)
                    var editor = ace.edit("id|" + id);
                    var JavaScriptMode = require(mode).Mode;
                    editor.getSession().setMode(new JavaScriptMode());
                    editor.resize();
                    
                    editor.getSession().setUseSoftTabs(true);
                    editor.getSession().setTabSize(4);
                    
                    // Fake-Save, works from the editor and the command line.
                    editor.commands.addCommand({
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
                    
               }
            });
    		
        } else if (this.editor == "ace-project-actions") {
        	Ext.Ajax.request({
                url: 'files/FileRetriever.groovy',
                params: {
                    project: project,
                    path: path
                },
                success: function(response){
                    var fileInfo = Ext.decode(response.responseText);
              		var editorPanelContent = Ext.create('Ext.panel.Panel', {
                        border: '0',
                  		layout: {
                            type: 'fit'
                        },
              			preventHeader: true,
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
                                        url: 'actions/PublishActions.groovy',
                                        params: {
                                            project: project,
                                            path: path,
                                            content: editor.getSession().getValue()
                                        },
                                        failure: function(response) {
 	                                    	Ext.Msg.show({
											     title:"Failed to publish the project [" + project + "] types",
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
                                    editor.focus();
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
                                    editor.focus();
                                }}],
                        layout: "fit",
                        html: '<div id="id|' + id + '" style="height:100%;width:100%;">' + fileInfo.contents + '</div>',
                        itemId: id,
                        id: id,
                        //title: fileName,
                        url: path,
                        //closable: true,
                        width: '100%',
                        height: '100%',
                        autoScroll: false,
                        editor: null,
                        project: project,
                        path: path
                    });
                    
                    editorpanel.removeAll()
                    editorpanel.add(editorPanelContent)
                    
                    var el = Ext.get("id|" + id)
                    var editor = ace.edit("id|" + id);
                    var JavaScriptMode = require(mode).Mode;
                    editor.getSession().setMode(new JavaScriptMode());
                    editor.resize();
                    editor.getSession().setUseSoftTabs(true);
                    editor.getSession().setTabSize(4);
                    
                    // Fake-Save, works from the editor and the command line.
                    editor.commands.addCommand({
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
                    
               }
            });
    		
        } else if (this.editor == "ace-project-methods") {
        	Ext.Ajax.request({
                url: 'files/FileRetriever.groovy',
                params: {
                    project: project,
                    path: path
                },
                success: function(response){
                    var fileInfo = Ext.decode(response.responseText);
              		var editorPanelContent = Ext.create('Ext.panel.Panel', {
              			border: '0',
                  		layout: {
                            type: 'fit'
                        },
                  		preventHeader: true,
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
                                        url: 'methods/PublishMethods.groovy',
                                        params: {
                                            project: project,
                                            path: path,
                                            content: editor.getSession().getValue()
                                        },
                                        failure: function(response) {
 	                                    	Ext.Msg.show({
											     title:"Failed to publish the project [" + project + "] methods",
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
                                    editor.focus();
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
                                    editor.focus();
                                }}],
                        layout: "fit",
                        html: '<div id="id|' + id + '" style="height:100%;width:100%;">' + fileInfo.contents + '</div>',
                        itemId: id,
                        id: id,
                        //title: fileName,
                        url: path,
                        //closable: true,
                        width: '100%',
                        height: '100%',
                        autoScroll: false,
                        editor: null,
                        project: project,
                        path: path
                    });
					editorpanel.removeAll()
                    editorpanel.add(editorPanelContent)                    
                    
                    var el = Ext.get("id|" + id)
                    var editor = ace.edit("id|" + id);
                    var JavaScriptMode = require(mode).Mode;
                    editor.getSession().setMode(new JavaScriptMode());
                    editor.resize();
                    editor.getSession().setUseSoftTabs(true);
                    editor.getSession().setTabSize(4);
                    
                    // Fake-Save, works from the editor and the command line.
                    edidor.commands.addCommand({
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
                    
            
                    
               }
            });
    		
        }  else if (this.editor == "ace-project-times") {
            Ext.Ajax.request({
                url: 'files/FileRetriever.groovy',
                params: {
                    project: project,
                    path: path
                },
                success: function(response){
                    var fileInfo = Ext.decode(response.responseText);
              		var editorPanelContent = Ext.create('Ext.panel.Panel', {
              			border: '0',
                  		layout: {
                            type: 'fit'
                        },
                  		preventHeader: true,
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
                                        url: 'timer/PublishTimes.groovy',
                                        params: {
                                            project: project,
                                            path: path,
                                            content: editor.getSession().getValue()
                                        },
                                        failure: function(response) {
 	                                    	Ext.Msg.show({
											     title:"Failed to publish the project [" + project + "] methods",
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
                                    editor.focus();
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
                                    editor.focus();
                                }}],
                        layout: "fit",
                        html: '<div id="id|' + id + '" style="height:100%;width:100%;">' + fileInfo.contents + '</div>',
                        itemId: id,
                        id: id,
                        //title: fileName,
                        url: path,
                        //closable: true,
                        width: '100%',
                        height: '100%',
                        autoScroll: false,
                        editor: null,
                        project: project,
                        path: path
                    });
					editorpanel.removeAll()
                    editorpanel.add(editorPanelContent)                    
                    
                    var el = Ext.get("id|" + id)
                    var editor = ace.edit("id|" + id);
                    var JavaScriptMode = require(mode).Mode;
                    editor.getSession().setMode(new JavaScriptMode());
                    editor.resize();
                    editor.getSession().setUseSoftTabs(true);
                    editor.getSession().setTabSize(4);
                    
                    // Fake-Save, works from the editor and the command line.
                    editor.commands.addCommand({
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
                    
            
                    
               }
            });
    		
        } else if (this.editor == "ace-project-services") {
            Ext.Ajax.request({
                url: 'files/FileRetriever.groovy',
                params: {
                    project: project,
                    path: path
                },
                success: function(response){
                    var fileInfo = Ext.decode(response.responseText);
              		var editorPanelContent = Ext.create('Ext.panel.Panel', {
              			border: '0',
                  		layout: {
                            type: 'fit'
                        },
                  		preventHeader: true,
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
                            { xtype: 'button', text: 'Execute',
                                handler: function() {
                                    Ext.Ajax.request({
                                        url: 'scripts/ExecuteScript.groovy',
                                        params: {
                                            project: project,
                                            path: path
                                        },
                                        success: function(response) {
                                         	Ext.Msg.show({
											     title:"Execute the script [" + path + "]",
											     msg: response.responseText,
											     autoScroll: true,
											     buttons: Ext.Msg.CANCEL
											});
 	                                    },
                                        failure: function(response) {
 	                                    	Ext.Msg.show({
											     title:"Failed to execute the script [" + path + "] methods",
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
                                    editor.focus();
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
                                    editor.focus();
                                }}],
                        layout: "fit",
                        html: '<div id="id|' + id + '" style="height:100%;width:100%;">' + fileInfo.contents + '</div>',
                        itemId: id,
                        id: id,
                        //title: fileName,
                        url: path,
                        //closable: true,
                        width: '100%',
                        height: '100%',
                        autoScroll: false,
                        editor: null,
                        project: project,
                        path: path
                    });
					editorpanel.removeAll()
                    editorpanel.add(editorPanelContent)                    
                    
                    var el = Ext.get("id|" + id)
                    var editor = ace.edit("id|" + id);
                    var JavaScriptMode = require(mode).Mode;
                    editor.getSession().setMode(new JavaScriptMode());
                    editor.resize();
                    editor.getSession().setUseSoftTabs(true);
                    editor.getSession().setTabSize(4);
                    // Fake-Save, works from the editor and the command line.
                    editor.commands.addCommand({
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
                    
            
                    
               }
            });
    		
        } else if (this.editor == "ace") {
    		Ext.Ajax.request({
                url: 'files/FileRetriever.groovy',
                params: {
                    project: project,
                    path: path
                },
                success: function(response){
                    var fileInfo = Ext.decode(response.responseText);
              		var editorPanelContent = Ext.create('Ext.panel.Panel', {
              			border: '0',
                  		layout: {
                            type: 'fit'
                        },
                  		preventHeader: true,
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
                                    editor.focus();
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
                                    editor.focus();
                                }}],
                        layout: "fit",
                        html: '<div id="id|' + id + '" style="height:100%;width:100%;">' + fileInfo.contents + '</div>',
                        itemId: id,
                        id: id,
                        //title: fileName,
                        url: path,
                        //closable: true,
                        width: '100%',
                        height: '100%',
                        autoScroll: false,
                        editor: null,
                        project: project,
                        path: path
                    });
                    
                    editorpanel.removeAll()
                    editorpanel.add(editorPanelContent)
                    
                    var canon = require("pilot/canon");
                    
                    //var el = Ext.get("id|" + id)
                    var editor = ace.edit("id|" + id);
                    var JavaScriptMode = require(mode).Mode;
                    editor.getSession().setMode(new JavaScriptMode());
                    // Fake-Save, works from the editor and the command line.
                    editor.commands.addCommand({
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
            
                    editor.resize();
                    //editor.getSession().setUseSoftTabs(true);
                    //editor.getSession().setTabSize(4);
					                    
               }
            });
        }
        this.editorpanel = editorpanel;
        return this.editorpanel;
    }
});

