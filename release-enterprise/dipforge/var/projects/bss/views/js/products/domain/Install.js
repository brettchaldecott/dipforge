/*
 * bss: Description
 * Copyright (C) Tue Dec 18 18:59:33 SAST 2012 owner
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
            '<label class="control-label" for="domain">Domain</label>',
            '<div class="controls">',
            '<input type="text" class="input-large" id="domain" name="domain" >',
            '<p class="help-block">Enter the domain name.</p>',
            '</div>',
            '</div>',
            '<div class="control-group">',
            '<label class="control-label" for="tld">TLD</label>',
            '<div class="controls">',
            '<select class="input-large" id="tld" name="tld" >',
            '</select>',
            '<p class="help-block">Select the top level domain.</p>',
            '</div>',
            '</div>');
        return html.join('');
    };
    this.setupHooks = function() {
        $("#domain").validate({
            expression: "if (VAL) return true; else return false;",
            message: "Must provide a domain."
        });
        $("#tld").validate({
            expression: "if (VAL) return true; else return false;",
            message: "Must select a top level domain."
        });
    };
    this.populateValues = function(offeringId) {
        var tlds = $('#existingOfferingData' + offeringId + 'domain').val().split(",");
        var html = [];
        $('#tld').empty();
        $.each(tlds,function(i,value) {
            html.push("<option value='", value, "'>",
                value, "</option>");
        });
        $('#tld').append(html.join(''));
    };
    this.populateConfiguredValues = function(offeringId) {
        var jsonData = JSON.parse($('#offeringInfo' + offeringId).val());
        $("select#tld option")
            .each(function() { this.selected = (this.text == jsonData.tld); });
        $("#domain").val(jsonData.domain);
    };
};



