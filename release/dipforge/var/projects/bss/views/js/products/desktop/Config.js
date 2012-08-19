/*
 * bss: Description
 * Copyright (C) Sat Aug 18 11:28:45 SAST 2012 owner
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
 * @author brett chaldecott
 */

/* initilize the page */
$(document).ready(function() {
    productMap.desktop = new Desktop();
});


var Desktop = function() {
    if (!this) {
        return new Desktop();
    }
    this.createDiv = function() {
        var html = [];
        html.push("<h3>Desktop</h3>",
            '<div class="control-group">',
            '<label class="control-label" for="desktopName">Name</label>',
            '<div class="controls">',
            '<input type="text" class="input-large" id="desktopName" name="desktopName" value="Dipforge">',
            '<p class="help-block">The name of the desktop.</p>',
            '</div>',
            '</div>');
        return html.join('');
    };
    this.setupHooks = function() {
        $("#desktopName").validate({
            expression: "if (VAL) return true; else return false;",
            message: "Must the name of the desktop"
        });
    };
    this.populateValues = function(pckgId) {
        $('#tlds').val($('#existingPckgData' + pckgId + 'desktop').val());
    };
    this.generateInput = function(pckgId) {
        return '<input type="hidden" name="existingPckgData' + pckgId + 'desktop" id="existingPckgData' + pckgId + 'desktop" value="' + $('#desktopName').val() + '" />';
    };
    
};

