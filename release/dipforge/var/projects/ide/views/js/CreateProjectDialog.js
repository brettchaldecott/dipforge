/**
 * @class com.dipforge.IDE.CreateProjectDialog
 * @extends Ext.tab.Panel
 *
 * A container class for showing the create project dialog
 * 
 * @constructor
 * Create a new Feed Info
 * @param {Object} config The config object
 */
Ext.define('com.dipforge.IDE.CreateProjectDialog', {
    
    extend: 'Ext.window.Window',
    title: 'Create Project',
    height: 200,
    width: 400,
    layout: 'fit',
    id: "createprojectdialog",
    itemId: "createprojectdialog",
    
    
    initComponent: function(){
        Ext.apply(this, {
            items: [
            	Ext.create('Ext.container.Container', {
                    layout: {
                       type: 'fit'},
            	    items: [this.createForm(this.projectPanel)]
            	    })]
        });
        this.callParent(arguments);
    },
    
    
    /**
     * This method creates the form.
     */
    createForm : function(projectPanel) {
    	var formPanel = Ext.create('Ext.form.Panel', {
	        title: 'Project Form',
	        alias: 'createprojectform',
	        bodyPadding: 5,
	        width: 350,
	        preventHeader: true,
	        
	        // Fields will be arranged vertically, stretched to full width
	        layout: 'anchor',
	        defaults: {
	            anchor: '100%'
	        },
	        
	        // The fields
	        defaultType: 'textfield',
	        items: [{
	            fieldLabel: 'Project',
	            name: 'project',
	            id: 'project',
	            allowBlank: false,
	            disabled: false
	        },{
	            fieldLabel: 'Description',
	            name: 'description',
	            id: 'description',
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
	            text: 'Create',
	            formBind: true, //only enabled once the form is valid
	            disabled: true,
	            fileId: this.fileId,
	            handler: function() {
	                var form = this.up('form').getForm();
	                var projectName = form.getValues().project
	                var projectDescription = form.getValues().description 
	                if (form.isValid()) {
	                	var result = ""
	                    for (var key in form.items) {
	                    	result += "[" + key + "]";
	                    }
						Ext.Ajax.request({
                                            url: 'projects/ProjectCreator.groovy',
                                            params: {
                                                project: projectName,
                                                description: projectDescription
	 	                                       },
	 	                                    success : function() {
	 	                                    	var treeNode = Ext.data.NodeInterface.create({
    												id: "P:" + projectName,
										            project: projectName,
										            file: projectName,
										            user: projectName,
										            leaf: false,
										            project_dir: true,
										  			iconCls: 'project'
												});
	 	                                    	projectPanel.getRootNode().appendChild(treeNode)
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
    }
});