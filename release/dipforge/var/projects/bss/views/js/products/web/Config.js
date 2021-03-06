/*
 * bss: Description
 * Copyright (C) Thu Jul 26 06:19:07 SAST 2012 owner
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
 * @author brett
 */


/* initilize the page */
$(document).ready(function() {
    productMap.web = new Web();
});


var Web = function() {
    if (!this) {
        return new Web();
    }
    this.createDiv = function() {
        var html = [];
        html.push("<h3>Web</h3>",
            '<div class="control-group">',
            '<label class="control-label" for="webQuota">Web quota</label>',
            '<div class="controls">',
            '<input type="text" class="input-large" id="webQuota" name="webQuota" value="3">',
            '<p class="help-block">The size of the web quota in GIGS.</p>',
            '</div>',
            '</div>');
        return html.join('');
    };
    this.setupHooks = function() {
        $("#webQuota").validate({
            expression: "if (VAL) return true; else return false;",
            message: "Must provide the web quota"
        });
    };
    this.populateValues = function(pckgId) {
        $('#webQuota').val($('#existingPckgData' + pckgId + 'web').val());
    };
    this.generateInput = function(pckgId) {
        return '<input type="hidden" name="existingPckgData' + pckgId + 'web" id="existingPckgData' + pckgId + 'web" value="' + $('#webQuota').val() + '" />';
    };
};

