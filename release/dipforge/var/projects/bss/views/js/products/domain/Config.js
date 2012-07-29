/*
 * bss: Description
 * Copyright (C) Thu Jul 26 06:18:46 SAST 2012 owner
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
    productMap.domain = new Domain();
});


var Domain = function() {
    if (!this) {
        return new Domain();
    }
    this.createDiv = function() {
        var html = [];
        html.push("<h3>Domain</h3>",
            '<div class="control-group">',
            '<label class="control-label" for="tlds">Tlds</label>',
            '<div class="controls">',
            '<input type="text" class="input-large" id="tlds" name="tlds" value="com,org,net,co.za">',
            '<p class="help-block">The comma seperated list of tlds.</p>',
            '</div>',
            '</div>');
        return html.join('');
    };
    this.setupHooks = function() {
        $("#tlds").validate({
            expression: "if (VAL) return true; else return false;",
            message: "Must provide list of tlds"
        });
    };
    this.populateValues = function(pckgId) {
        $('tlds').val($('#existingPckgData' + pckgId + 'domain').val());
    };
};
