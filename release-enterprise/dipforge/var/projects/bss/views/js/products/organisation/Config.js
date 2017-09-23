/*
 * bss: Description
 * Copyright (C) Thu Dec 13 05:29:22 SAST 2012 owner
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
    productMap.organisation = new Organisation();
});


var Organisation = function() {
    if (!this) {
        return new User();
    }
    this.createDiv = function() {
        var html = [];
        html.push("<h3>User</h3>");
        return html.join('');
    };
    this.setupHooks = function() {
        $("#principals").validate({
            expression: "if (VAL) return true; else return false;",
            message: "Must provide list of principals"
        });
    };
    this.populateValues = function(pckgId) {
        
    };
    this.generateInput = function(pckgId) {
        return '';
    };
};


