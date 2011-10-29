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
Ext.define('com.dipforge.IDE.RenameFileDialog', {
    
    extend: 'Ext.window.Window',
    title: 'Rename File',
    height: 200,
    width: 400,
    layout: 'fit',
    id: "renamedialog",
    itemId: "renamedialog",
    
    
    initComponent: function(){
        Ext.apply(this, {
            items: [
            	Ext.create('Ext.container.Container', {
                    layout: {
                       type: 'fit'},
            	    items: [this.createForm(this.record,this.menu)]
            	    })]
        });
        this.callParent(arguments);
    },
    
    
    /**
     * This method creates the form.
     */
    createForm : function(record, dialog) {
    	var oldFileName = this.fileName.substring(this.fileName.lastIndexOf("/") + 1,this.fileName.lastIndexOf("."))
    	var fileType = this.fileName.substring(this.fileName.lastIndexOf("."))
    	var formPanel = Ext.create('Ext.form.Panel', {
	        title: 'Simple Form',
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
	            value: this.project,
	            allowBlank: false,
	            disabled: true
	        },{
	            fieldLabel: 'Directory',
	            name: 'directory',
	            id: 'directory',
	            value: record.parentNode.get("path"),
	            allowBlank: false,
	            disabled: true
	        },{
	            fieldLabel: 'Old File',
	            name: 'oldFileName',
	            id: 'oldFileName',
	            value: oldFileName,
	            allowBlank: false,
	            disabled: true
	        },{
	            fieldLabel: 'New File',
	            name: 'newFileName',
	            id: 'newFileName',
	            allowBlank: false
	        }],
	        
	        // Reset and Submit buttons
	        buttons: [{
	            text: 'Cancel',
	            handler: function() {
	                Ext.WindowMgr.getActive().close();
	            }
	        }, {
	            text: 'Rename',
	            formBind: true, //only enabled once the form is valid
	            disabled: true,
	            fileId: this.fileId,
	            handler: function() {
	                var form = this.up('form').getForm();
	                var newPath = record.parentNode.get("path") + "/" + form.getValues().newFileName + fileType
	                if (form.isValid()) {
	                	Ext.Ajax.request({
                                            url: 'files/FileRename.groovy',
                                            params: {
                                                project: form.items[0].value,
												source: record.get("path"),
                                                target: newPath
	 	                                       },
	 	                                    success : function() {
												var newNode = record.copy()
												var parentNode = record.parentNode 	                                    	
	 	                                    	parentNode.removeChild(record)
	 	                                    	newNode.set("id",'P:' + form.items[0].value + ":" + newPath)
	 	                                    	newNode.set("file",form.getValues().newFileName + fileType)
	 	                                    	newNode.set("path", newPath)
	 	                                    	newNode.set("newFile", true)
												newNode.set("text", form.getValues().newFileName + fileType)
	 	                                    	parentNode.appendChild(newNode)
	 	                                    },
	 	                                    failure: function() {
	 	                                    	Ext.Msg.show({
												     title:"Failed to rename the file [" + form.items[0].value + "]",
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