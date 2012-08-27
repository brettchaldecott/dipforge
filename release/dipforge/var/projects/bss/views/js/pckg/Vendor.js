/*
 * bss: Description
 * Copyright (C) Tue Jul 10 20:59:29 SAST 2012 owner
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
 * Vendor.js
 * @author brett chaldecott
 */

var modalAction = 0;

/* initilize the page */
$(document).ready(function() {
    $('#addVendorItem').click(function(event) {
        event.preventDefault();
        $('#vendorForm').submit();
    });
    
    $('#updateVendorItem').click(function(event) {
        event.preventDefault();
        $('#vendorForm').submit();
    });
    
    $('#removeVendorItem').click(function(event) {
        event.preventDefault();
        $.ajax({
                url: 'vendor/RemoveVendor.groovy?vendorId=' + $('#removeModalFormId').val(),
                success: function(data) {
                    if (data == "success") {
                        // setup the message
                        $('#removeModelMsg').hide();
                        
                        $('#removeModelSuccessMsg').show();
                        $('#removeModelSuccessResultMsg').text('Vendor removal [' + 
                            $('#removeModalFormId').val() + '] submitted to system.');
                        $('#removeModelErrorMsg').hide();
                        
                        // setup the buttons
                        $('#removeVendorCloseButton').hide();
                        $('#removeVendorItem').hide();
                        
                        // remove the thumbnail
                        $('#vendorThumbnailEntry' + $('#removeModalFormId').val()).remove();
                        
                        // delay and hide the modal
                        setTimeout(function () {
                            $('#removeModal').modal('hide');    
                        }, 1500);
                        
                    } else {
                        // setup the message
                        $('#removeModelMsg').hide();
                        
                        $('#removeModelSuccessMsg').hide();
                        $('#removeModelErrorMsg').show();
                        $('#removeModelErrorResultMsg').html('Vendor [' + 
                            $('#removeModalFormId').val() + '] could not be removed : ' + data);
                        
                        // setup the buttons
                        $('#removeVendorCloseButton').show();
                        $('#removeVendorItem').hide();
                        
                    }
                }
        });
    })
    
    $("#vendorId").validate({
        expression: "if (VAL) return true; else return false;",
        message: "Must provide ID"
    });
    
    $("#vendorName").validate({
        expression: "if (VAL) return true; else return false;",
        message: "Must provide name"
    });
    
    $("#vendorDescription").validate({
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
    
    $('#vendorForm').validated(function() {
        
        // hide all error messages
        $('#modelDataErrorResult').hide();
        $('#modelSuccessResult').hide();
        $('#modelRuntimeErrorResult').hide();
        
        if (modalAction == 0) {
            $.ajax({
                url: 'vendor/CreateVendor.groovy?' + $('#vendorForm').serialize(),
                success: function(data) {
                    if (data == 'success') {
                        $('#modelRuntimeErrorResult').hide();
                        $('#updateVendorItem').hide();
                        $('#addVendorItem').hide()
                        $('#vendorCloseButton').hide();
                        $('#modelForm').hide();
                        $('#modelSuccessResult').show();
                        $('#modelSuccessResultMsg').text('Vendor add [' + 
                            $('#vendorId').val() + '] submitted to system.');
                            
                        var html = [];
                        html.push(
                            '<li class="span3" id="vendorThumbnailEntry',$('#vendorId').val(),'">',
                            generateThumbnail(),
                            '</li>');
                        $("#vendorThumbnails").append(html.join(""));
                        
                        
                        $('[id^=hoveroverimage]').popover({"placement":"bottom"});
                        
                        // delay and hide the modal
                        setTimeout(function () {
                            $('#vendorModal').modal('hide');    
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
                url: 'vendor/UpdateVendor.groovy?vendorId=' + $('#vendorId').val() + '&' + $('#vendorForm').serialize(),
                success: function(data) {
                    if (data == 'success') {
                        $('#modelRuntimeErrorResult').hide();
                        $('#addVendorItem').hide();
                        $('#updateVendorItem').hide();
                        $('#vendorCloseButton').hide();
                        $('#modelForm').hide();
                        $('#modelSuccessResult').show();
                        $('#modelSuccessResultMsg').text('Vendor update [' + 
                            $('#vendorId').val() + '] submitted to system.');
                            
                        var html = [];
                        html.push(generateThumbnail());
                        $("#vendorThumbnailEntry" + $('#vendorId').val()).html(html.join(""));
                        
                        $('[id^=hoveroverimage]').popover({"placement":"bottom"});
                        
                        // delay and hide the modal
                        setTimeout(function () {
                            $('#vendorModal').modal('hide');
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
    
    $('#vendorModal').on('hidden', function () {
        
        $('#vendorId').val('');
        $('#vendorName').val('');
        $('#vendorDescription').val('');
        $('#thumbnail').val('');
        $('#icon').val('');
        $('#addVendorItem').show();
        $('#updateVendorItem').hide();
        $('#vendorCloseButton').show();
        $('#modelForm').show();
        $('#modelDataErrorResult').hide();
        $('#modelSuccessResult').hide();
        $('#modelRuntimeErrorResult').hide();
        $('#addVendorTitle').show();
        $('#updateVendorTitle').hide();
        $('#vendorId').removeAttr('disabled');
        modalAction = 0;
    });
    
    
    $('#removeModal').on('hidden', function () {
        $('#removeModelMsg').show();
        $('#removeModelSuccessMsg').hide();
        $('#removeModelErrorMsg').hide();
        $('#removeVendorCloseButton').show();
        $('#removeVendorItem').show();
        
        
    });
    
    
    $('[id^=hoveroverimage]').popover({"placement":"bottom"});
});


/**
 * This method is called to edit a vendor
 */
function updateVendor(vendorId) {
    modalAction = 1;
    
    $('#vendorId').val($('#existingVendorId' + vendorId).val());
    $('#vendorName').val($('#existingVendorName' + vendorId).val());
    $('#vendorDescription').val($('#existingVendorDescription' + vendorId).val());
    $('#thumbnail').val($('#existingVendorThumbnail' + vendorId).val());
    $('#icon').val($('#existingVendorIcon' + vendorId).val());
    
    $('#vendorId').attr('disabled','disabled'); 
    $('#addVendorTitle').hide();
    $('#updateVendorTitle').show();
    $('#addVendorItem').hide();
    $('#updateVendorItem').show();
    $('#vendorModal').modal('show');
}


/**
 * This function is called to remove the vendor
 */
function removeVendor(vendorId) {
    $('#removeModalFormId').val(vendorId);
    
    // setup the message
    $('#removeModelMsg').show();
    $('#removeModelText').html("Are you sure you want to remove the vendor [" + vendorId + "]");
    
    $('#removeModelSuccessMsg').hide();
    $('#removeModelErrorMsg').hide();
    
    // setup the buttons
    $('#removeVendorCloseButton').show();
    $('#removeVendorItem').show();
    
    // show the model
    $('#removeModal').modal('show');
}

/**
 * A function for generating a new thumb nail for the page
 */
function generateThumbnail() {
    var html = [];
    html.push('<div class="thumbnail" rel="popover"',
            ' data-content="ID: ',$('#vendorId').val(),
            '<br/>Name: ',$('#vendorName').val(),
            '<br/>Description: ',$('#vendorDescription').val(),
            '<br/>Thumbnail: ',$('#thumbnail').val(),
            '<br/>Icon: ',$('#icon').val(),'<br/>"',
            ' data-original-title="',$('#vendorName').val(),'" id="hoveroverimage',$('#vendorId').val(),'">',
            '<img src="../',$('#thumbnail').val(),'" alt="',$('#vendorDescription').val(),'">',
            '<div class="caption">',
            '<h5>','<img src="../',$('#icon').val(),'" style="height:16px;width:16px;"/> ',$('#vendorName').val(),'</h5>',
            '<p>',$('#vendorDescription').val(),'</p>',
            '<p><a href="javascript:removeVendor(\'',$('#vendorId').val(),'\');" class="btn btn-primary">Remove</a> <a href="javascript:updateVendor(\'',$('#vendorId').val(),'\');" class="btn">Update</a></p>',
            '<form id="existingVendorForm',$('#vendorId').val(),'">',
            '<input type="hidden" name="existingVendorId',$('#vendorId').val(),'" id="existingVendorId',$('#vendorId').val(),'" value="',$('#vendorId').val(),'" />',
            '<input type="hidden" name="existingVendorName',$('#vendorId').val(),'" id="existingVendorName',$('#vendorId').val(),'" value="',$('#vendorName').val(),'" />',
            '<input type="hidden" name="existingVendorDescription',$('#vendorId').val(),'" id="existingVendorDescription',$('#vendorId').val(),'" value="',$('#vendorDescription').val(),'" />',
            '<input type="hidden" name="existingVendorThumbnail',$('#vendorId').val(),'" id="existingVendorThumbnail',$('#vendorId').val(),'" value="',$('#thumbnail').val(),'" />',
            '<input type="hidden" name="existingVendorIcon',$('#vendorId').val(),'" id="existingVendorIcon',$('#vendorId').val(),'" value="',$('#icon').val(),'" />',
            '</form>',
            '</div>',
            '</div>');
    
    return html.join("");
}

