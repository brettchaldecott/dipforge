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

var modalAction = 0;

/* initilize the page */
$(document).ready(function() {
    $('#addCategoryItem').click(function(event) {
        event.preventDefault();
        $('#categoryForm').submit();
    });
    
    $('#updateCategoryItem').click(function(event) {
        event.preventDefault();
        $('#categoryForm').submit();
    });
    
    $('#removeCategoryItem').click(function(event) {
        event.preventDefault();
        $.ajax({
                url: 'category/RemoveCategory.groovy?categoryId=' + $('#removeModalFormId').val(),
                success: function(data) {
                    if (data == "success") {
                        // setup the message
                        $('#removeModelMsg').hide();
                        
                        $('#removeModelSuccessMsg').show();
                        $('#removeModelSuccessResultMsg').text('Category removal [' + 
                            $('#removeModalFormId').val() + '] submitted to system.');
                        $('#removeModelErrorMsg').hide();
                        
                        // setup the buttons
                        $('#removeCategoryCloseButton').hide();
                        $('#removeCategoryItem').hide();
                        
                        // remove the thumbnail
                        $('#categoryThumbnailEntry' + $('#removeModalFormId').val()).remove();
                        
                        // delay and hide the modal
                        setTimeout(function () {
                            $('#removeModal').modal('hide');    
                        }, 3000);
                        
                    } else {
                        // setup the message
                        $('#removeModelMsg').hide();
                        
                        $('#removeModelSuccessMsg').hide();
                        $('#removeModelErrorMsg').show();
                        $('#removeModelErrorResultMsg').html('Category [' + 
                            $('#removeModalFormId').val() + '] could not be removed : ' + data);
                        
                        // setup the buttons
                        $('#removeCategoryCloseButton').show();
                        $('#removeCategoryItem').hide();
                        
                    }
                }
        });
    })
    
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
    
    $('#categoryForm').validated(function() {
        
        // hide all error messages
        $('#modelDataErrorResult').hide();
        $('#modelSuccessResult').hide();
        $('#modelRuntimeErrorResult').hide();
        
        if (modalAction == 0) {
            $.ajax({
                url: 'category/CreateCategory.groovy?' + $('#categoryForm').serialize(),
                success: function(data) {
                    if (data == 'success') {
                        $('#modelRuntimeErrorResult').hide();
                        $('#updateCategoryItem').hide();
                        $('#addCategoryItem').hide()
                        $('#categoryCloseButton').hide();
                        $('#modelForm').hide();
                        $('#modelSuccessResult').show();
                        $('#modelSuccessResultMsg').text('Category add [' + 
                            $('#categoryId').val() + '] submitted to system.');
                            
                        var html = [];
                        html.push(
                            '<li class="span3" id="categoryThumbnailEntry',$('#categoryId').val(),'">',
                            generateThumbnail(),
                            '</li>');
                        $("#categoryThumbnails").append(html.join(""));
                        
                        
                        $('[id^=hoveroverimage]').popover({"placement":"bottom"});
                        
                        // delay and hide the modal
                        setTimeout(function () {
                            $('#categoryModal').modal('hide');    
                        }, 3000);
                        
                    } else {
                        $('#modelDataErrorResult').show();
                        $('#modelDataErrorResultMsg').text(data);
                    }
                },
                error: function(data) {
                    $('#modelSuccessResult').hide();
                    $('#modelForm').hide();
                    $('#modelRuntimeErrorResult').show();
                    $('#modelRuntimeErrorResultMsg').text(data);
                    
                }
            });
        } else {
            $.ajax({
                url: 'category/UpdateCategory.groovy?categoryId=' + $('#categoryId').val() + '&' + $('#categoryForm').serialize(),
                success: function(data) {
                    if (data == 'success') {
                        $('#modelRuntimeErrorResult').hide();
                        $('#addCategoryItem').hide();
                        $('#updateCategoryItem').hide();
                        $('#categoryCloseButton').hide();
                        $('#modelForm').hide();
                        $('#modelSuccessResult').show();
                        $('#modelSuccessResultMsg').text('Category update [' + 
                            $('#categoryId').val() + '] submitted to system.');
                            
                        var html = [];
                        html.push(generateThumbnail());
                        $("#categoryThumbnailEntry" + $('#categoryId').val()).html(html.join(""));
                        
                        $('[id^=hoveroverimage]').popover({"placement":"bottom"});
                        
                        // delay and hide the modal
                        setTimeout(function () {
                            $('#categoryModal').modal('hide');    
                        }, 3000);
                        
                    } else {
                        $('#modelDataErrorResult').show();
                        $('#modelDataErrorResultMsg').text(data);
                    }
                },
                error: function(data) {
                    $('#modelSuccessResult').hide();
                    $('#modelForm').hide();
                    $('#modelRuntimeErrorResult').show();
                    $('#modelRuntimeErrorResultMsg').text(data);
                    
                }
            });
        }
    });
    
    $('#categoryModal').on('hidden', function () {
        
        $('#categoryId').val('');
        $('#categoryName').val('');
        $('#categoryDescription').val('');
        $('#thumbnail').val('');
        $('#icon').val('');
        $('#addCategoryItem').show();
        $('#updateCategoryItem').hide();
        $('#categoryCloseButton').show();
        $('#modelForm').show();
        $('#modelDataErrorResult').hide();
        $('#modelSuccessResult').hide();
        $('#modelRuntimeErrorResult').hide();
        $('#addCategoryTitle').show();
        $('#updateCategoryTitle').hide();
        $('#categoryId').removeAttr('disabled');
        modalAction = 0;
    });
    
    
    $('#removeModal').on('hidden', function () {
        $('#removeModelMsg').show();
        $('#removeModelSuccessMsg').hide();
        $('#removeModelErrorMsg').hide();
        $('#removeCategoryCloseButton').show();
        $('#removeCategoryItem').show();
        
        
    });
    
    
    $('[id^=hoveroverimage]').popover({"placement":"bottom"});
});


/**
 * This method is called to edit a category
 */
function updateCategory(categoryId) {
    modalAction = 1;
    
    $('#categoryId').val($('#existingCategoryId' + categoryId).val());
    $('#categoryName').val($('#existingCategoryName' + categoryId).val());
    $('#categoryDescription').val($('#existingCategoryDescription' + categoryId).val());
    $('#thumbnail').val($('#existingCategoryThumbnail' + categoryId).val());
    $('#icon').val($('#existingCategoryIcon' + categoryId).val());
    
    $('#categoryId').attr('disabled','disabled'); 
    $('#addCategoryTitle').hide();
    $('#updateCategoryTitle').show();
    $('#addCategoryItem').hide();
    $('#updateCategoryItem').show();
    $('#categoryModal').modal('show');
}


/**
 * This function is called to remove the category
 */
function removeCategory(categoryId) {
    $('#removeModalFormId').val(categoryId);
    
    // setup the message
    $('#removeModelMsg').show();
    $('#removeModelText').html("Are you sure you want to remove the category [" + categoryId + "]");
    
    $('#removeModelSuccessMsg').hide();
    $('#removeModelErrorMsg').hide();
    
    // setup the buttons
    $('#removeCategoryCloseButton').show();
    $('#removeCategoryItem').show();
    
    // show the model
    $('#removeModal').modal('show');
}

/**
 * A function for generating a new thumb nail for the page
 */
function generateThumbnail() {
    var html = [];
    html.push('<div class="thumbnail" rel="popover"',
            ' data-content="ID: ',$('#categoryId').val(),
            '<br/>Name: ',$('#categoryName').val(),
            '<br/>Description: ',$('#categoryDescription').val(),
            '<br/>Thumbnail: ',$('#thumbnail').val(),
            '<br/>Icon: ',$('#icon').val(),'<br/>"',
            ' data-original-title="',$('#categoryName').val(),'" id="hoveroverimage',$('#categoryId').val(),'">',
            '<img src="',$('#thumbnail').val(),'" alt="',$('#categoryDescription').val(),'">',
            '<div class="caption">',
            '<h5>','<img src="',$('#icon').val(),'" style="height:16px;width:16px;"/> ',$('#categoryName').val(),'</h5>',
            '<p>',$('#categoryDescription').val(),'</p>',
            '<p><a href="javascript:removeCategory(\'',$('#categoryId').val(),'\');" class="btn btn-primary">Remove</a> <a href="javascript:updateCategory(\'',$('#categoryId').val(),'\');" class="btn">Update</a></p>',
            '<form id="existingCategoryForm',$('#categoryId').val(),'">',
            '<input type="hidden" name="existingCategoryId',$('#categoryId').val(),'" id="existingCategoryId',$('#categoryId').val(),'" value="',$('#categoryId').val(),'" />',
            '<input type="hidden" name="existingCategoryName',$('#categoryId').val(),'" id="existingCategoryName',$('#categoryId').val(),'" value="',$('#categoryName').val(),'" />',
            '<input type="hidden" name="existingCategoryDescription',$('#categoryId').val(),'" id="existingCategoryDescription',$('#categoryId').val(),'" value="',$('#categoryDescription').val(),'" />',
            '<input type="hidden" name="existingCategoryThumbnail',$('#categoryId').val(),'" id="existingCategoryThumbnail',$('#categoryId').val(),'" value="',$('#thumbnail').val(),'" />',
            '<input type="hidden" name="existingCategoryIcon',$('#categoryId').val(),'" id="existingCategoryIcon',$('#categoryId').val(),'" value="',$('#icon').val(),'" />',
            '</form>',
            '</div>',
            '</div>');
    
    return html.join("");
}