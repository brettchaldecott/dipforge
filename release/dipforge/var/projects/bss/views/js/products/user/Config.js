/*
 * bss: Description
 * Copyright (C) Sat Aug 18 11:28:34 SAST 2012 owner
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
    productMap.user = new User();
});


var User = function() {
    if (!this) {
        return new User();
    }
    this.createDiv = function() {
        var html = [];
        html.push("<h3>User</h3>",
            '<div class="control-group">',
            '<label class="control-label" for="principles">Principles</label>',
            '<div class="controls">',
            '<input type="text" class="input-large" id="principles" name="principles" value="" placeholder="value,value,value,....">',
            '<p class="help-block">A comma seperated list of user principles.</p>',
            '</div>',
            '</div>');
        return html.join('');
    };
    this.setupHooks = function() {
        $("#principles").validate({
            expression: "if (VAL) return true; else return false;",
            message: "Must provide list of principles"
        });
    };
    this.populateValues = function(pckgId) {
        $('#principles').val($('#existingPckgData' + pckgId + 'user').val());
    };
    this.generateInput = function(pckgId) {
        return '<input type="hidden" name="existingPckgData' + pckgId + 'user" id="existingPckgData' + pckgId + 'user" value="' + $('#principles').val() + '" />';
    };
    
};

