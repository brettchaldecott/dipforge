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
    
        // sample static data for the store
        var myData = [
            ['3m Co',                               71.72, 0.02,  0.03,  '9/1 12:00am'],
            ['Alcoa Inc',                           29.01, 0.42,  1.47,  '9/1 12:00am'],
            ['Altria Group Inc',                    83.81, 0.28,  0.34,  '9/1 12:00am'],
            ['American Express Company',            52.55, 0.01,  0.02,  '9/1 12:00am'],
            ['American International Group, Inc.',  64.13, 0.31,  0.49,  '9/1 12:00am'],
            ['AT&T Inc.',                           31.61, -0.48, -1.54, '9/1 12:00am'],
            ['Boeing Co.',                          75.43, 0.53,  0.71,  '9/1 12:00am'],
            ['Caterpillar Inc.',                    67.27, 0.92,  1.39,  '9/1 12:00am'],
            ['Citigroup, Inc.',                     49.37, 0.02,  0.04,  '9/1 12:00am'],
            ['E.I. du Pont de Nemours and Company', 40.48, 0.51,  1.28,  '9/1 12:00am'],
            ['Exxon Mobil Corp',                    68.1,  -0.43, -0.64, '9/1 12:00am'],
            ['General Electric Company',            34.14, -0.08, -0.23, '9/1 12:00am'],
            ['General Motors Corporation',          30.27, 1.09,  3.74,  '9/1 12:00am'],
            ['Hewlett-Packard Co.',                 36.53, -0.03, -0.08, '9/1 12:00am'],
            ['Honeywell Intl Inc',                  38.77, 0.05,  0.13,  '9/1 12:00am'],
            ['Intel Corporation',                   19.88, 0.31,  1.58,  '9/1 12:00am'],
            ['International Business Machines',     81.41, 0.44,  0.54,  '9/1 12:00am'],
            ['Johnson & Johnson',                   64.72, 0.06,  0.09,  '9/1 12:00am'],
            ['JP Morgan & Chase & Co',              45.73, 0.07,  0.15,  '9/1 12:00am'],
            ['McDonald\'s Corporation',             36.76, 0.86,  2.40,  '9/1 12:00am'],
            ['Merck & Co., Inc.',                   40.96, 0.41,  1.01,  '9/1 12:00am'],
            ['Microsoft Corporation',               25.84, 0.14,  0.54,  '9/1 12:00am'],
            ['Pfizer Inc',                          27.96, 0.4,   1.45,  '9/1 12:00am'],
            ['The Coca-Cola Company',               45.07, 0.26,  0.58,  '9/1 12:00am'],
            ['The Home Depot, Inc.',                34.64, 0.35,  1.02,  '9/1 12:00am'],
            ['The Procter & Gamble Company',        61.91, 0.01,  0.02,  '9/1 12:00am'],
            ['United Technologies Corporation',     63.26, 0.55,  0.88,  '9/1 12:00am'],
            ['Verizon Communications',              35.57, 0.39,  1.11,  '9/1 12:00am'],
            ['Wal-Mart Stores, Inc.',               45.45, 0.73,  1.63,  '9/1 12:00am']
        ];
    
        
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


