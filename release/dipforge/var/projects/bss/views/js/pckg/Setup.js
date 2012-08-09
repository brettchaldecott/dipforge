/*
 * bss: Description
 * Copyright (C) Wed Jul 25 14:17:33 SAST 2012 owner
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
 * Setup.js
 * @author brett chaldecott
 */

/* initilize the page */
$(document).ready(function() {
    
    $('#setupSubmit').click(function(event) {
        event.preventDefault();
        $('#setupModelSuccessMsg').hide();
        $('#setupModelErrorMsg').hide();
        
        $.ajax({
            url: 'setup/Setup.groovy',
            success: function(data) {
                $('#setupModelMsg').hide();
                $('#setupModelSuccessMsg').show();
                $('#setupModelErrorMsg').hide();    
                $('#setupSubmit').hide();
                $('#setupCloseButton').hide();
                
                
                
                // delay and hide the modal
                setTimeout(function () {
                    $('#setupModal').modal('hide');    
                }, 1500);
            },
            error: function(data) {
                $('#setupModelMsg').hide();
                $('#setupModelSuccessMsg').hide();
                $('#setupModelErrorMsg').show();
            }
        })
    });
    
    $('#setupModal').on('hidden', function () {
        $('#setupSubmit').show();
        $('#setupCloseButton').show();
    });
});


