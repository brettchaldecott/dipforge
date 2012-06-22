/**
 * @class com.dipforge.IDE.App
 * @extends Ext.container.Viewport
 *
 * The IDE application
 * 
 * @constructor
 * Create a new IDE application
 * @param {Object} config The config object
 */

Ext.define('com.dipforge.BSS.App', {
    extend: 'Ext.container.Viewport',
    border: 0,
    padding: 0,
     
    initComponent: function(){
        
    	//we want to setup a model and store instead of using dataUrl
    	Ext.define('ManagementType', {
            extend: 'Ext.data.Model',
            fields: [
                {name: 'type',  type: 'string'},
                {name: 'name',     type: 'string'},
                {name: 'view',   type: 'string'},
                {name: 'mode',     type: 'string'}
            ]
    	});
    	
    	Ext.define('ManagementTypeView', {
            extend: 'Ext.data.Model',
            fields: [
            	{name: 'type', type: 'string'},
                {name: 'name', type: 'string'},
            	{name: 'view', type: 'string'},
            	{name: 'mode', type: 'string'}]
        });

    	Ext.apply(this, {
            layout: {
                        type: 'border'
                    },
            border: 0,
            padding: 0,
            items: [this.createManagementListPanel(),this.createManagementTypeViewPanel()]
        });
        this.callParent(arguments);
    },
    
    
    /**
     * This method returns the project panel
     */
    createManagementListPanel: function() {
    	this.managementListPanel = Ext.create('widget.managementlistpanel', {
            region: 'west',
            collapsible: false,
            width: 250,
            preventHeader: true,
            floatable: false,
            split: false,
            minWidth: 250,
            listeners: {
                scope: this,
                managementtypeselect: this.onManagementTypeSelect
            }
        });
        return this.managementListPanel;
    },
    
    
    /**
     * Create the management panel
     * @private
     * @return {com.dipforge.IDE.EditorPanel} feedInfo
     */
    createManagementTypeViewPanel: function(){
        this.managementtypeviewpanel = Ext.create('widget.managementtypeviewpanel', {
            region: 'center',
            minWidth: 300
        });
        return this.managementtypeviewpanel;
    },
	
	
	/**
	 * This method is called to handle the on select event
	 */
	onManagementTypeSelect: function(panel,type,name,view,mode) {
		this.managementtypeviewpanel.addManagementTypeView(type,name,view,mode);
	},
	
});

