/*
 * public: Description
 * Copyright (C) Fri May 25 13:16:02 SAST 2012 owner
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
 * DataGrid.js
 * @author brett chaldecott
 */


Ext.require([
    'Ext.data.*',
    'Ext.grid.*'
]);

Ext.onReady(function(){
    Ext.define('NewsFeed',{
        extend: 'Ext.data.Model',
        fields: ['image','title','author','msg','url']
    });

    // create the Data Store
    var store = Ext.create('Ext.data.Store', {
        model: 'NewsFeed',
        autoLoad: true,
        proxy: {
            // load using HTTP
            type: 'ajax',
            url: 'feeds/NewsFeeds.groovy',
            // the return will be XML, so lets set up a reader
            reader: {
                type: 'json',
                root: 'feeds'
            }
        }
    });

    // create the grid
    var grid = Ext.create('widget.dataview', {
        store: store,
        //columns: [
        //    {text: "Author", flex: 1, dataIndex: 'author', sortable: true},
        //    {text: "Title", width: 180, dataIndex: 'title', sortable: true},
        //    {text: "Msg", width: 115, dataIndex: 'msg', sortable: true}
        //],
        trackOver: true,
        autoScroll: true,
        style: "padding-top:5px",
        cls: 'feed-list',
        itemSelector: '.feed-list-item',
        overItemCls: 'feed-list-item-hover',
        tpl: '<tpl for="."><div class="feed-list-item"><a href="{url}" target="_blank"><table border=0  cellpadding=0 cellspacing=0 valign="middle"><tr><td><b>{author}</b><br><b><i>{title}</i></b></td></tr><tr><td><p>{msg}</p></td></tr></table></a></div></tpl>',
        renderTo:'data-grid',
        width: 255,
        height: 405
    });
});
