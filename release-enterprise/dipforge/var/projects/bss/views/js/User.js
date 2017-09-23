/*
 * bss: Description
 * Copyright (C) Tue Aug 28 06:53:56 SAST 2012 owner
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
 * User.js
 * @author brett chaldecott
 */

var emailRegex = /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;

$(document).ready(function() {
    
    $('#addUserItem').click(function (event) {
        event.preventDefault();
        $('#userForm').submit();
    });
    
    $("#userId").validate({
        expression: "if (VAL) return true; else return false;",
        message: "Must provide ID"
    });
    
    $("#username").validate({
        expression: "if (VAL) return true; else return false;",
        message: "Must provide username"
    });
    
    $("#email").validate({
        expression: "if (emailRegex.test(VAL)) return true; else return false;",
        message: "Must provide valid email"
    });
    
    $("#password").validate({
        expression: "if (VAL) return true; else return false;",
        message: "Must provide password"
    });
    
    $('#userForm').validated(function() {
        addUser();
    });
    
    $('#userModal').on('hidden', function () {
        
        $('#userId').val('');
        $('#username').val('');
        $('#email').val('');
        $('#password').val('');
        
        // setup the buttons
        $('#userCloseButton').show();
        $('#addUserItem').show();
        $('#updateUserItem').hide();
        $('#userForm').show();
        
        
    });
    
});


function addUser() {
    $.ajax({
        url: 'user/CreateUser.groovy',
        data: $('#userForm').serialize(),
        success: function(data) {
            if (data == "success") {
                // setup the message
                $('#modelDataErrorResult').hide();
                $('#modelRuntimeErrorResult').hide();
                
                $('#modelSuccessResult').show();
                $('#modelSuccessResultMsg').text('Added the user [' + 
                    $('#username').val() + '] submitted to system.');
                
                // setup the buttons
                $('#userCloseButton').hide();
                $('#addUserItem').hide();
                $('#updateUserItem').hide();
                $('#userForm').hide();
                
                $('#userRecords').append(generateUserHTML());
                
                // delay and hide the modal
                setTimeout(function () {
                    $('#userModal').modal('hide');    
                }, 1500);
                
            } else {
                // setup the message
                $('#modelSuccessResult').hide();
                $('#modelDataErrorResult').hide();
                
                $('#modelRuntimeErrorResult').show();
                $('#modelRuntimeErrorResultMsg').html(data);
            }
        },
        failure: function(data) {
            // setup the message
            $('#modelSuccessResult').hide();
            $('#modelDataErrorResult').hide();
            
            $('#modelRuntimeErrorResult').show();
            $('#modelRuntimeErrorResultMsg').html("Failed to add the user");
        }
    });
}

function showUpdateUser(userId) {
    
}

function showRemoveUser(userId) {
    
}

function generateUserHTML() {
    // generate a new html section
    var pathValue = [];
    var jsonPath = "[";
    var sep = "";
    
    var tableRow = [];
    tableRow.push('<tr id="tr',$('#userId').val(),'">',
            '<td><a href="javascript:showRemoveUser(\'',$('#userId').val(),'\');"><i class="icon-minus-sign"></i></a></td>',
            '<td><a href="javascript:showUpdateUser(\'',$('#userId').val(),'\');">',$('#username').val(),'</a></td>',
            '<td><a href="javascript:showUpdateUser(\'',$('#userId').val(),'\');">',$('#email').val(),'</a></td>',
            '<td><a href="javascript:showUpdateUser(\'',$('#userId').val(),'\');">',$('#userType').val(),'</a></td>',
            '<form id="userData',$('#userId').val(),'" style="display:none;">',
            '<input type="hidden" id="userData',$('#userId').val(),'userId" name="userData',$('#userId').val(),'userId" value="',$('#userId').val(),'" />',
            '<input type="hidden" id="userData',$('#userId').val(),'username" name="userData',$('#userId').val(),'username" value="',$('#username').val(),'" />',
            '<input type="hidden" id="userData',$('#userId').val(),'email" name="userData',$('#userId').val(),'email" value="',$('#email').val(),'" />',
            '<input type="hidden" id="userData',$('#userId').val(),'userType" name="userData',$('#userId').val(),'userType" value="',$('#userType').val(),'" />',
            '</form>',
            '</tr>');
    
    return tableRow.join('');
}
