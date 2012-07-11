/*
 * bss: Description
 * Copyright (C) Sat Jul 07 07:50:18 SAST 2012 owner
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
 * Product.js
 * @author admin
 */

var modalAction = 0;

/* initilize the page */
$(document).ready(function() {
    $('#addProductItem').click(function(event) {
        event.preventDefault();
        $('#productForm').submit();
    });
    
    $('#updateProductItem').click(function(event) {
        event.preventDefault();
        $('#productForm').submit();
    });
    
    $('#removeProductItem').click(function(event) {
        event.preventDefault();
        $.ajax({
                url: 'product/RemoveProduct.groovy?productId=' + $('#removeModalFormId').val(),
                success: function(data) {
                    if (data == "success") {
                        // setup the message
                        $('#removeModelMsg').hide();
                        
                        $('#removeModelSuccessMsg').show();
                        $('#removeModelSuccessResultMsg').text('Product removal [' + 
                            $('#removeModalFormId').val() + '] submitted to system.');
                        $('#removeModelErrorMsg').hide();
                        
                        // setup the buttons
                        $('#removeProductCloseButton').hide();
                        $('#removeProductItem').hide();
                        
                        // remove the thumbnail
                        $('#productThumbnailEntry' + $('#removeModalFormId').val()).remove();
                        
                        // delay and hide the modal
                        setTimeout(function () {
                            $('#removeModal').modal('hide');    
                        }, 1500);
                        
                    } else {
                        // setup the message
                        $('#removeModelMsg').hide();
                        
                        $('#removeModelSuccessMsg').hide();
                        $('#removeModelErrorMsg').show();
                        $('#removeModelErrorResultMsg').html('Product [' + 
                            $('#removeModalFormId').val() + '] could not be removed : ' + data);
                        
                        // setup the buttons
                        $('#removeProductCloseButton').show();
                        $('#removeProductItem').hide();
                        
                    }
                }
        });
    })
    
    $("#productId").validate({
        expression: "if (VAL) return true; else return false;",
        message: "Must provide ID"
    });
    
    $("#productName").validate({
        expression: "if (VAL) return true; else return false;",
        message: "Must provide name"
    });
    
    $("#productDescription").validate({
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
    
    $('#productForm').validated(function() {
        
        // hide all error messages
        $('#modelDataErrorResult').hide();
        $('#modelSuccessResult').hide();
        $('#modelRuntimeErrorResult').hide();
        
        if (modalAction == 0) {
            $.ajax({
                url: 'product/CreateProduct.groovy?' + $('#productForm').serialize(),
                success: function(data) {
                    if (data == 'success') {
                        $('#modelRuntimeErrorResult').hide();
                        $('#updateProductItem').hide();
                        $('#addProductItem').hide()
                        $('#productCloseButton').hide();
                        $('#modelForm').hide();
                        $('#modelSuccessResult').show();
                        $('#modelSuccessResultMsg').text('Product add [' + 
                            $('#productId').val() + '] submitted to system.');
                            
                        var html = [];
                        html.push(
                            '<li class="span3" id="productThumbnailEntry',$('#productId').val(),'">',
                            generateThumbnail(),
                            '</li>');
                        $("#productThumbnails").append(html.join(""));
                        
                        
                        $('[id^=hoveroverimage]').popover({"placement":"bottom"});
                        
                        // delay and hide the modal
                        setTimeout(function () {
                            $('#productModal').modal('hide');    
                        }, 1500);
                        
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
                url: 'product/UpdateProduct.groovy?productId=' + $('#productId').val() + '&' + $('#productForm').serialize(),
                success: function(data) {
                    if (data == 'success') {
                        $('#modelRuntimeErrorResult').hide();
                        $('#addProductItem').hide();
                        $('#updateProductItem').hide();
                        $('#productCloseButton').hide();
                        $('#modelForm').hide();
                        $('#modelSuccessResult').show();
                        $('#modelSuccessResultMsg').text('Product update [' + 
                            $('#productId').val() + '] submitted to system.');
                            
                        var html = [];
                        html.push(generateThumbnail());
                        $("#productThumbnailEntry" + $('#productId').val()).html(html.join(""));
                        
                        $('[id^=hoveroverimage]').popover({"placement":"bottom"});
                        
                        // delay and hide the modal
                        setTimeout(function () {
                            $('#productModal').modal('hide');    
                        }, 1500);
                        
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
    
    $('#productModal').on('hidden', function () {
        
        $('#productId').val('');
        $('#productName').val('');
        $('#productDescription').val('');
        $('#thumbnail').val('');
        $('#icon').val('');
        $('#addProductItem').show();
        $('#updateProductItem').hide();
        $('#productCloseButton').show();
        $('#modelForm').show();
        $('#modelDataErrorResult').hide();
        $('#modelSuccessResult').hide();
        $('#modelRuntimeErrorResult').hide();
        $('#addProductTitle').show();
        $('#updateProductTitle').hide();
        $('#productId').removeAttr('disabled');
        modalAction = 0;
    });
    
    
    $('#removeModal').on('hidden', function () {
        $('#removeModelMsg').show();
        $('#removeModelSuccessMsg').hide();
        $('#removeModelErrorMsg').hide();
        $('#removeProductCloseButton').show();
        $('#removeProductItem').show();
        
        
    });
    
    
    $('[id^=hoveroverimage]').popover({"placement":"bottom"});
});


/**
 * This method is called to edit a product
 */
function updateProduct(productId) {
    modalAction = 1;
    
    $('#productId').val($('#existingProductId' + productId).val());
    $('#productName').val($('#existingProductName' + productId).val());
    $('#productDescription').val($('#existingProductDescription' + productId).val());
    $('#thumbnail').val($('#existingProductThumbnail' + productId).val());
    $('#icon').val($('#existingProductIcon' + productId).val());
    
    $('#productId').attr('disabled','disabled'); 
    $('#addProductTitle').hide();
    $('#updateProductTitle').show();
    $('#addProductItem').hide();
    $('#updateProductItem').show();
    $('#productModal').modal('show');
}


/**
 * This function is called to remove the product
 */
function removeProduct(productId) {
    $('#removeModalFormId').val(productId);
    
    // setup the message
    $('#removeModelMsg').show();
    $('#removeModelText').html("Are you sure you want to remove the product [" + productId + "]");
    
    $('#removeModelSuccessMsg').hide();
    $('#removeModelErrorMsg').hide();
    
    // setup the buttons
    $('#removeProductCloseButton').show();
    $('#removeProductItem').show();
    
    // show the model
    $('#removeModal').modal('show');
}

/**
 * A function for generating a new thumb nail for the page
 */
function generateThumbnail() {
    var html = [];
    html.push('<div class="thumbnail" rel="popover"',
            ' data-content="ID: ',$('#productId').val(),
            '<br/>Name: ',$('#productName').val(),
            '<br/>Description: ',$('#productDescription').val(),
            '<br/>Thumbnail: ',$('#thumbnail').val(),
            '<br/>Icon: ',$('#icon').val(),'<br/>"',
            ' data-original-title="',$('#productName').val(),'" id="hoveroverimage',$('#productId').val(),'">',
            '<img src="',$('#thumbnail').val(),'" alt="',$('#productDescription').val(),'">',
            '<div class="caption">',
            '<h5>','<img src="',$('#icon').val(),'" style="height:16px;width:16px;"/> ',$('#productName').val(),'</h5>',
            '<p>',$('#productDescription').val(),'</p>',
            '<p><a href="javascript:removeProduct(\'',$('#productId').val(),'\');" class="btn btn-primary">Remove</a> <a href="javascript:updateProduct(\'',$('#productId').val(),'\');" class="btn">Update</a></p>',
            '<form id="existingProductForm',$('#productId').val(),'">',
            '<input type="hidden" name="existingProductId',$('#productId').val(),'" id="existingProductId',$('#productId').val(),'" value="',$('#productId').val(),'" />',
            '<input type="hidden" name="existingProductName',$('#productId').val(),'" id="existingProductName',$('#productId').val(),'" value="',$('#productName').val(),'" />',
            '<input type="hidden" name="existingProductDescription',$('#productId').val(),'" id="existingProductDescription',$('#productId').val(),'" value="',$('#productDescription').val(),'" />',
            '<input type="hidden" name="existingProductThumbnail',$('#productId').val(),'" id="existingProductThumbnail',$('#productId').val(),'" value="',$('#thumbnail').val(),'" />',
            '<input type="hidden" name="existingProductIcon',$('#productId').val(),'" id="existingProductIcon',$('#productId').val(),'" value="',$('#icon').val(),'" />',
            '</form>',
            '</div>',
            '</div>');
    
    return html.join("");
}

