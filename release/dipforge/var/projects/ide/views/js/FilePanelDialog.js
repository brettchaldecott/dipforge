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
Ext.define('com.dipforge.IDE.FilePanelDialog', {
    
    extend: 'Ext.window.Window',
    title: 'Create File',
    height: 270,
    width: 400,
    layout: 'fit',
    
    
    initComponent: function(){
        Ext.apply(this, {
            items: [
            	Ext.create('Ext.container.Container', {
                    layout: {
                       type: 'fit'},
            	    items: [this.createForm(this.record)]
            	    })]
        });
        this.callParent(arguments);
    },
    
    
    /**
     * This method creates the form.
     */
    createForm : function(record) {
    	var formPanel = Ext.create('Ext.form.Panel', {
	        title: 'Simple Form',
	        bodyPadding: 5,
	        width: 350,
	        preventHeader: true,

	        
	        // The form will submit an AJAX request to this URL when submitted
	        url: 'save-form.php',
	        
	        // Fields will be arranged vertically, stretched to full width
	        layout: 'anchor',
	        defaults: {
	            anchor: '100%'
	        },
	        
	        // The fields
	        defaultType: 'textfield',
	        items: [
	        	{
					id: 'project',
			        fieldLabel: 'Project',
			        name: 'project',
			        value: record.get('project'),
			        allowBlank: false,
	            	disabled: true
			    },{
					id: 'path',
			        fieldLabel: 'Path',
			        name: 'path',
			        value: record.get('path'),
			        allowBlank: false,
	            	disabled: true
			    },// Create the combo box, attached to the states data store
				Ext.create('Ext.form.ComboBox', {
				    fieldLabel: 'File Type',
				    store: Ext.create('Ext.data.Store', {
			                model: 'FileType',
			                proxy: {
			                    type: 'ajax',
			                    url : 'files/FileTypes.groovy',
			                    reader: {
			                        type: 'json',
			                        root: 'types'
			                    }
			                }
			            }),
			        id: 'fileType',
				    displayField: 'name',
				    name: 'fileType',
	            	disabled: false
				}),{
					id: 'fileName',
			        fieldLabel: 'Name',
			        name: 'fileName',
			        allowBlank: false,
	            	disabled: false
			    }],
	        
	        // Reset and Submit buttons
	        buttons: [{
		            text: 'Cancel',
		            handler: function() {
		                
		                Ext.WindowMgr.getActive().close();
		            }
	        	}, {
	            text: 'Submit',
	            id: 'submit',
	            formBind: true, //only enabled once the form is valid
	            disabled: true,
	            handler: function() {
	                var form = this.up('form').getForm();
	                var projectName = form.items[0].value
	                var path = form.items[1].value
					var fileName = form.getValues().fileName
					var fileType = form.getValues().fileType
					var typeField = form.getFields().get("fileType")
					var typeFieldRecord = typeField.findRecordByDisplay(fileType)
	                if (form.isValid()) {
						Ext.Ajax.request({
                                            url: 'files/FileCreator.groovy',
                                            params: {
                                                project: projectName,
                                                path: path,
                                                fileName: fileName,
                                                fileType: fileType
	 	                                       },
	 	                                    success : function() {
	 	                                    	var treeNode = null;
	 	                                    	if (fileType == "folder") {
	 	                                    		var completePath = path + "/" + fileName
	 	                                    		treeNode = Ext.create('File',{
		    												id: 'P:' + projectName + ":" + completePath,
												            project: projectName,
												            file: fileName,
												            user: projectName,
												            leaf: false,
												            path: completePath,
												            project_dir: false,
												  			iconCls: 'directory',
												  			text: fileName
														});
	 	                                    	} else {
	 	                                    		var completePath = path + "/" + fileName + "." + fileType
	 	                                    		treeNode = Ext.create('File',{
		    												id: 'P:' + projectName + ":" + completePath,
												            project: projectName,
												            file: fileName + "." + fileType,
												            user: projectName,
												            leaf: true,
												            path: completePath,
												            project_dir: false,
												            editor: typeFieldRecord.get('editor'), 
												            mode: typeFieldRecord.get('mode'),
												  			iconCls: 'file',
												  			newFile: true,
												  			text: fileName + "." + fileType
														});
	 	                                    	}
	 	                                    	
												Ext.data.NodeInterface.decorate(treeNode);
												record.appendChild(treeNode)
	 	                                    },
	 	                                    failure: function(response) {
	 	                                    	Ext.Msg.show({
												     title:"Failed to create the project [" + projectName + "]",
												     msg: response.responseText,
												     autoScroll: true,
												     buttons: Ext.Msg.CANCEL
												});
	 	                                    }
                                        });	                    
	                                        
	                    Ext.WindowMgr.getActive().close();
	                }
	            }
	        }],
	    });
    	return formPanel;
    },
    
    
    /**
     * Create the DataView to be used for the feed list.
     * @private
     * @return {Ext.view.View}
     */
    createProjectView: function(){
        //Ext.ux.tree.TreeGrid is no longer a Ux. You can simply use a tree.TreePanel
    	this.tree = Ext.create('Ext.tree.Panel', {
            //title: 'Core Team Projects',
            preventHeader: true,
            anchor: '100% 100%',
            collapsible: false,
            useArrows: true,
            rootVisible: false,
            store: Ext.create('Ext.data.TreeStore', {
            	model: 'File',
            	autoLoad: true,
            	proxy: {
                	type: 'ajax',
                	//the store will get the content from the .json file
                	url: 'files/FileList.groovy'
            		}
    			}),
            singleExpand: false,
            //the 'columns' property is now 'headers'
            columns: [{
                xtype: 'treecolumn', //this is so we know which column will show the tree
                text: 'Project',
                flex: 2,
                sortable: true,
                dataIndex: 'file'
            }]});
    	return this.tree;
    }

});