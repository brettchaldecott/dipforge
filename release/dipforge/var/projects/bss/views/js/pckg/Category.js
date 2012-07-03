/*
 * bss: Description
 * Copyright (C) Thu Jun 28 07:01:03 SAST 2012 owner
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
 * Category.js
 * @author brett chaldecott
 */

/* initilize the page */
$(document).ready(function() {
    $('#addCategoryItem').click(function() {
            $('#createCategoryForm').submit();
    });
    
    $("#categoryId").validate({
        expression: "if (VAL) return true; else return false;",
        message: "Must provide ID"
    });
    
    $("#categoryName").validate({
        expression: "if (VAL) return true; else return false;",
        message: "Must provide name"
    });
    
    $("#categoryDescription").validate({
        expression: "if (VAL) return true; else return false;",
        message: "Must provide description"
    });
    
    $("#thumbnail").validate({
        expression: "if (VAL) return true; else return false;",
        message: "Must provide thumbnail"
    });
    
    $("#icon").validate({
        expression: "if (VAL) return true; else return false;",
        message: "Must provide icon"
    });
    
    $('#createCategoryForm').validated(function() {
        $.ajax({
            url: 'category/CreateCategory.groovy?' + $('#createCategoryForm').serialize(),
            success: function(data) {
                if (data == 'success') {
                    $('#addCategoryItem').hide()
                    $('#modelForm').hide();
                    $('#modelSuccessResult').show();
                    $('#modelSuccessResultMsg').text('Request to create category [' + 
                        $('#categoryId').val() + '] has been submitted.');
                } else {
                    $('#modelDataErrorResult').show();
                    $('#modelDataErrorResultMsg').text(data);
                }
            },
            error: function(data) {
                $('#modelForm').hide();
                $('#modelRuntimeErrorResult').show();
                $('#modelRuntimeErrorResultMsg').text(data);
            }
        });
    });
    
    $('#categoryModal').on('hidden', function () {
        $('#categoryId').val('');
        $('#categoryName').val('');
        $('#categoryDescription').val('');
        $('#thumbnail').val('');
        $('#icon').val('');
        $('#addCategoryItem').show();
        $('#modelForm').show();
        $('#modelDataErrorResult').hide();
        $('#modelSuccessResult').hide();
    });
    
});



