/*
 * bss: The BSS functionality
 * Copyright (C) Tue Dec 18 18:59:49 SAST 2012 owner
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
 * Install.js
 * @author brett chaldecott
 */

/* initilize the page */
$(document).ready(function() {
    productMap.mail = new Mail();
});


var Mail = function() {
    if (!this) {
        return new Mail();
    }
    this.createDiv = function() {
        var html = [];
        html.push("<h3>Mail</h3>",
            '<div class="control-group">',
            '<label class="control-label" for="mailQuota">Mail quota</label>',
            '<div class="controls">',
            '<input type="text" class="input-large" id="mailQuota" name="mailQuota" disabled="true">',
            '<p class="help-block">The size of the mail quota in GIGS.</p>',
            '</div>',
            '</div>');
        return html.join('');
    };
    this.setupHooks = function() {
        $("#mailQuota").validate({
            expression: "if (VAL) return true; else return false;",
            message: "Must provide quota"
        });
    };
    this.populateValues = function(offeringId) {
        $('#mailQuota').val($('#existingOfferingData' + offeringId + 'mail').val());
    };
};

