/*
 * dipforge: Common libraries to the dipforge system
 * Copyright (C) Sat Jul 28 08:06:26 SAST 2012 owner
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
 * GUID.js
 * @author brett chaldecott
 */



/**
 * This is a GUID generator. This code was taken from
 * 
 * http://blog.shkedy.com/2007/01/createing-guids-with-client-side.html
 */
var GUID = function() {
    if (!this) {
        return new GUID();
    }
    var guidValue, i, j;
    guidValue = '';
    for (j=0; j<32; j++) {
        if ( j == 8 || j == 12|| j == 16|| j == 20) {
            guidValue = guidValue + '-';
        }
        i = Math.floor(Math.random()*16).toString(16).toUpperCase();
        guidValue = guidValue + i;
    }
    this.value = guidValue;
    this.getValue = function() {
        return this.value;
    };
};
