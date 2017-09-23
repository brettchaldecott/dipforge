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
Ext.define('com.dipforge.IDE.DeleteProjectDialog', {
    
    extend: 'Ext.window.Window',
    title: 'Delete Project',
    height: 200,
    width: 400,
    layout: 'fit',
    id: "deleteprojectdialog",
    itemId: "deleteprojectdialog",
    
    
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
	        }],
	        
	        // Reset and Submit buttons
	        buttons: [{
	            text: 'Cancel',
	            handler: function() {
	                Ext.WindowMgr.getActive().close();
	            }
	        }, {
	            text: 'Delete',
	            formBind: true, //only enabled once the form is valid
	            disabled: true,
	            fileId: this.fileId,
	            handler: function() {
	                var form = this.up('form').getForm();
	                if (form.isValid()) {
	                	Ext.Ajax.request({
                                            url: 'projects/ProjectRemover.groovy',
                                            params: {
                                                project: form.items[0].value
	 	                                       },
	 	                                    success : function() {
	 	                                    	record.parentNode.removeChild(record)
	 	                                    },
	 	                                    failure: function(response) {
	 	                                    	Ext.Msg.show({
												     title:"Failed to create the project [" + form.items[0].value + "]",
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