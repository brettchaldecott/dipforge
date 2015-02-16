/*
 * ide: Description
 * Copyright (C) Sun Feb 15 06:30:43 SAST 2015 owner
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
 * LogPanel.js
 * @author admin
 */



Ext.require(['*']);

var logpanel = null;

Ext.onReady(function() {
    
    Ext.define('LogFile', {
        extend: 'Ext.data.Model',
        fields: [
            {name: 'file', type: 'string'}]
    });
    
    var tb = Ext.create('Ext.toolbar.Toolbar');
    tb.suspendLayout = true;
    tb.render('log-toolbar');
    
    
    var combo = Ext.create('Ext.form.field.ComboBox', {
        hideLabel: true,
        store: Ext.create('Ext.data.Store', {
                model: 'LogFile',
                proxy: {
                    type: 'ajax',
                    url : 'logs/ListLogs.groovy',
                    reader: {
                        type: 'json'
                    }
                }
            }),
        displayField: 'file',
        emptyText: 'Select a log file...',
        selectOnFocus: true,
        width: 135,
        iconCls: 'no-icon',
        // all of your config options
        listeners:{
             scope: this,
             'select': selectLog
        }
    });
    
    tb.add(combo);
    tb.add({
        text: 'Clear',
        baseParams: {
            q: 'html+anchor+tag'
        },
        tooltip: 'Click this button to clear the log',
        handler: function(){
            if (logpanel !== null) {
                logpanel.clear();
            }
        }
    });
    tb.suspendLayout = false;
    tb.doLayout();
    
    

});


function selectLog(combo,records,source) {
    
    var innerHeight = window.innerHeight - 27;
    var innerWidth = window.innerWidth;
    
    $('#log-contents').css({height : innerHeight, width:innerWidth });
    
    // clear the existing log panel
    if (logpanel !== null) {
        logpanel.stop();
        logpanel.clear();
    }
    logpanel = new LogTailer();
    logpanel.setLogFile(combo.getValue());
    logpanel.poll();
}

var LogTailer = function() {
    if (!this) {
        return new LogTailer();
    }
    
    this.clear = function() {
        $('#log-contents').empty();
    };
    
    this.setLogFile = function(file) {
        this.file = file;
    };
    
    this.poll = function() {
        this.finish = false;
        pollLog(this.file,0);
        
    };
    this.stop = function() {
        this.finish = true;
    };
    this.getFinish = function() {
        return this.finish;
    };
};


function pollLog(file,endLine) {
    
    $.ajax({
            url: 'logs/TailLog.groovy?logFile=' + file + '&endLine=' + endLine,
            success: function(data) {
                var logResult = JSON.parse(data);
                
                $('#log-contents').append(logResult[0].lines.replace("\n", "\r\n"));
                // delay and hide the modal
                if (logpanel !== null && !logpanel.getFinish()) {
                    setTimeout(function () {
                        pollLog(file,logResult[0].endLine);
                    }, 1000 * 3);
                }
            },
            failure: function(data) {
                // delay and hide the modal
                setTimeout(function () {
                    pollLog(file,endLine);    
                }, 1000 * 3);
            }
        });
}