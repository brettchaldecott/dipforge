/*
 * bss: Description
 * Copyright (C) Sat Aug 18 12:19:54 SAST 2012 owner
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
 * Config.js
 * @author admin
 */


/* initilize the page */
$(document).ready(function() {
    productMap.application = new Application();
});


var Application = function() {
    if (!this) {
        return new Application();
    }
    this.createDiv = function() {
        var html = [];
        html.push("<h3>Application</h3>",
            '<div class="control-group">',
            '<label class="control-label" for="applicationInfo">Applications</label>',
            '<div class="controls">',
            '<textarea class="input-xlarge" id="applicationInfo" name="applicationInfo">',
            '[name=ide,principle=ide,url=/desktop/ide/,thumbnail=images/apps/ide.png,icon=images/apps/ide.png]',
            '</textarea>',
            '<p class="help-block">The configuration information for the application.</p>',
            '</div>',
            '</div>');
        return html.join('');
    };
    this.setupHooks = function() {
        $("#applicationInfo").validate({
            expression: "if (VAL) return true; else return false;",
            message: "Must the name of the desktop"
        });
    };
    this.populateValues = function(pckgId) {
        $('#applicationInfo').val($('#existingPckgData' + pckgId + 'application').val());
    };
    this.generateInput = function(pckgId) {
        return '<input type="hidden" name="existingPckgData' + pckgId + 'application" id="existingPckgData' + pckgId + 'application" value="' + $('#applicationInfo').val() + '" />';
    };
    
};


