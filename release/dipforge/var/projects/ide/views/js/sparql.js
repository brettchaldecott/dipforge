/*
 * ide: Description
 * Copyright (C) Sat Nov 24 13:55:26 SAST 2012 owner
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
 * sparql.js
 * @author admin
 */

Ext.require([
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.util.*',
    'Ext.state.*'
]);

var sparqlpanel = null;
var resultContainer = null;

Ext.onReady(function() {
    
    sparqlpanel = Ext.create('Ext.Viewport', {
        height: '100%',
        width: "100%",
        //title: 'Array Grid',
        renderTo: 'grid-example',
        layout:'border',
        items: [createResultContainer(),createQueryForm()]
    });
    
    function createResultContainer() {
        resultContainer = Ext.create('Ext.container.Container', {
            border: 0,
            region: 'center',
            layout: 'fit',
            items: [createGrid()]
        });
        
        return resultContainer;
    }
    
    function setupResultContainer(data) {
        resultContainer.removeAll();
        resultContainer.add(createGridWithData(data));
    }
    
    function createGridWithData(data) {
        // Define Company entity
        // Null out built in convert functions for performance *because the raw data is known to be valid*
        // Specifying defaultValue as undefined will also save code. *As long as there will always be values in the data, or the app tolerates undefined field values*
        Ext.define('Company', {
            extend: 'Ext.data.Model',
            fields: data.fields,
            idProperty: 'company'
        });
        
        Ext.QuickTips.init();
        
        // setup the state provider, all state information will be saved to a cookie
        Ext.state.Manager.setProvider(Ext.create('Ext.state.CookieProvider'));
    
        // create the data store
        var store = Ext.create('Ext.data.ArrayStore', {
            model: 'Company',
            data: data.data
        });
        
        // create the Grid
        var grid = Ext.create('Ext.grid.Panel', {
            store: store,
            stateful: true,
            collapsible: true,
            multiSelect: true,
            region:'center',
            stateId: 'stateGrid',
            itemId:'dataGrid',
            columns: data.columns,
            viewConfig: {
                stripeRows: true,
                enableTextSelection: true
            }
        });
        
        
        
        return grid;
    }
    
    function createGrid() {
        // create the Grid
        var grid = Ext.create('Ext.panel.Panel', {
            stateful: true,
            collapsible: true,
            multiSelect: true,
            region:'center',
            stateId: 'stateGrid',
            itemId:'dataGrid'
        });
        
        
        
        return grid;
    }
    
    
    function createQueryForm() {
        var form = Ext.create('Ext.form.Panel', {
            layout: 'fit',
            defaultType: 'textfield',
            border: false,
            region:'south',
            height: 200,
            url: 'sparql/Execute.groovy',
            items: [ {
                region: 'center',
                xtype: 'textarea',
                style: 'margin:1',
                hideLabel: true,
                name: 'query'
            }]
        });
        
        var panel = Ext.create('Ext.panel.Panel', {
            layout: 'fit',
            defaultType: 'textfield',
            border: false,
            region:'south',
            items: [form],
            buttons: [{
                text: 'Execute',
                handler: function() {
                    Ext.Ajax.request({
                        url: 'sparql/Execute.groovy',
                        reader: 'json',
                        params: form.getValues(),
                        failure: function(response) {
                                Ext.Msg.show({
                                title:"Failed to execute the query",
                                msg: response.responseText,
                                autoScroll: true,
                                buttons: Ext.Msg.CANCEL
							});
                        },
                        success: function(data) {
                            setupResultContainer(Ext.decode(data.responseText));
                        } 
                    });
                }
            }]
        });
        
        return panel;
    }
});


