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
 * Pckg.js
 * @author brett chaldecott
 */

var modalAction = 0;

/* initilize the page */
$(document).ready(function() {
    $('#addPckgItem').click(function(event) {
        event.preventDefault();
        $('#pckgForm').submit();
    });
    
    $('#updatePckgItem').click(function(event) {
        event.preventDefault();
        $('#pckgForm').submit();
    });
    
    $('#removePckgItem').click(function(event) {
        event.preventDefault();
        $.ajax({
                url: 'pckg/RemovePckg.groovy?pckgId=' + $('#removeModalFormId').val(),
                success: function(data) {
                    if (data == "success") {
                        // setup the message
                        $('#removeModelMsg').hide();
                        
                        $('#removeModelSuccessMsg').show();
                        $('#removeModelSuccessResultMsg').text('Pckg removal [' + 
                            $('#removeModalFormId').val() + '] submitted to system.');
                        $('#removeModelErrorMsg').hide();
                        
                        // setup the buttons
                        $('#removePckgCloseButton').hide();
                        $('#removePckgItem').hide();
                        
                        // remove the thumbnail
                        $('#pckgThumbnailEntry' + $('#removeModalFormId').val()).remove();
                        
                        // delay and hide the modal
                        setTimeout(function () {
                            $('#removeModal').modal('hide');    
                        }, 1500);
                        
                    } else {
                        // setup the message
                        $('#removeModelMsg').hide();
                        
                        $('#removeModelSuccessMsg').hide();
                        $('#removeModelErrorMsg').show();
                        $('#removeModelErrorResultMsg').html('Pckg [' + 
                            $('#removeModalFormId').val() + '] could not be removed : ' + data);
                        
                        // setup the buttons
                        $('#removePckgCloseButton').show();
                        $('#removePckgItem').hide();
                        
                    }
                }
        });
    })
    
    $("#pckgId").validate({
        expression: "if (VAL) return true; else return false;",
        message: "Must provide ID"
    });
    
    $("#pckgName").validate({
        expression: "if (VAL) return true; else return false;",
        message: "Must provide name"
    });
    
    $("#pckgDescription").validate({
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
    
    $("#pckgDataType").validate({
        expression: "if (VAL) return true; else return false;",
        message: "Must provide type"
    });
    
    $("#pckgCategory").validate({
        expression: "if (VAL) return true; else return false;",
        message: "Must select category"
    });
    
    $("#pckgVendor").validate({
        expression: "if (VAL) return true; else return false;",
        message: "Must provide vendor"
    });
    
    $('#pckgForm').validated(function() {
        
        // hide all error messages
        $('#modelDataErrorResult').hide();
        $('#modelSuccessResult').hide();
        $('#modelRuntimeErrorResult').hide();
        
        if (modalAction == 0) {
            $.ajax({
                url: 'pckg/CreatePckg.groovy?' + $('#pckgForm').serialize(),
                success: function(data) {
                    if (data == 'success') {
                        $('#modelRuntimeErrorResult').hide();
                        $('#updatePckgItem').hide();
                        $('#addPckgItem').hide()
                        $('#pckgCloseButton').hide();
                        $('#modelForm').hide();
                        $('#modelSuccessResult').show();
                        $('#modelSuccessResultMsg').text('Pckg add [' + 
                            $('#pckgId').val() + '] submitted to system.');
                            
                        var html = [];
                        html.push(
                            '<li class="span3" id="pckgThumbnailEntry',$('#pckgId').val(),'">',
                            generateThumbnail(),
                            '</li>');
                        $("#pckgThumbnails").append(html.join(""));
                        
                        $('#pckgDependency').append('<option value="' + $('#pckgId').val() + '">'
                            + $('#pckgName').val() + '</option>');
                        
                        $('[id^=hoveroverimage]').popover({"placement":"bottom"});
                        
                        // delay and hide the modal
                        setTimeout(function () {
                            $('#pckgModal').modal('hide');    
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
                url: 'pckg/UpdatePckg.groovy?pckgId=' + $('#pckgId').val() + '&' + $('#pckgForm').serialize(),
                success: function(data) {
                    if (data == 'success') {
                        $('#modelRuntimeErrorResult').hide();
                        $('#addPckgItem').hide();
                        $('#updatePckgItem').hide();
                        $('#pckgCloseButton').hide();
                        $('#modelForm').hide();
                        $('#modelSuccessResult').show();
                        $('#modelSuccessResultMsg').text('Pckg update [' + 
                            $('#pckgId').val() + '] submitted to system.');
                        
                        var html = [];
                        html.push(generateThumbnail());
                        $("#pckgThumbnailEntry" + $('#pckgId').val()).html(html.join(""));
                        
                        $('[id^=hoveroverimage]').popover({"placement":"bottom"});
                        
                        // delay and hide the modal
                        setTimeout(function () {
                            $('#pckgModal').modal('hide');    
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
    
    $('#pckgModal').on('hidden', function () {
        
        $('#pckgId').val('');
        $('#pckgName').val('');
        $('#pckgDescription').val('');
        $('#thumbnail').val('');
        $('#icon').val('');
        $('#pckgDataType').val('');
        $('#addPckgItem').show();
        $('#updatePckgItem').hide();
        $('#pckgCloseButton').show();
        $('#modelForm').show();
        $('#modelDataErrorResult').hide();
        $('#modelSuccessResult').hide();
        $('#modelRuntimeErrorResult').hide();
        $('#addPckgTitle').show();
        $('#updatePckgTitle').hide();
        $('#pckgId').removeAttr('disabled');
        modalAction = 0;
    });
    
    
    $('#removeModal').on('hidden', function () {
        $('#removeModelMsg').show();
        $('#removeModelSuccessMsg').hide();
        $('#removeModelErrorMsg').hide();
        $('#removePckgCloseButton').show();
        $('#removePckgItem').show();
        
        
    });
    
    
    $('[id^=hoveroverimage]').popover({"placement":"bottom"});
});


/**
 * This method is called to edit a pckg
 */
function updatePckg(pckgId) {
    modalAction = 1;
    
    $('#pckgId').val($('#existingPckgId' + pckgId).val());
    $('#pckgName').val($('#existingPckgName' + pckgId).val());
    $('#pckgDescription').val($('#existingPckgDescription' + pckgId).val());
    $('#pckgDataType').val($('#existingPckgDataType' + pckgId).val());
    $('select#pckgCategory option')
        .each(function() { this.selected = (this.val == $('#existingPckgCategory' + pckgId).val()); });
    $('select#pckgVendor option')
        .each(function() { this.selected = (this.val == $('#existingPckgVendor' + pckgId).val()); });
    var dependency = $('#existingDependency' + pckgId).val();
    if (dependency !== null && dependency !== '') {
        alert("The dependency is " + dependency);
        $('select#pckgDependency option')
            .each(function() { this.selected = (this.val == dependency); });
    } else {
        $('select#pckgDependency option')
            .each(function() { this.selected = false; });
    }
    $('#thumbnail').val($('#existingPckgThumbnail' + pckgId).val());
    $('#icon').val($('#existingPckgIcon' + pckgId).val());
    
    $('#pckgId').attr('disabled','disabled'); 
    $('#addPckgTitle').hide();
    $('#updatePckgTitle').show();
    $('#addPckgItem').hide();
    $('#updatePckgItem').show();
    $('#pckgModal').modal('show');
}


/**
 * This function is called to remove the pckg
 */
function removePckg(pckgId) {
    $('#removeModalFormId').val(pckgId);
    
    // setup the message
    $('#removeModelMsg').show();
    $('#removeModelText').html("Are you sure you want to remove the pckg [" + pckgId + "]");
    
    $('#removeModelSuccessMsg').hide();
    $('#removeModelErrorMsg').hide();
    
    // setup the buttons
    $('#removePckgCloseButton').show();
    $('#removePckgItem').show();
    
    // show the model
    $('#removeModal').modal('show');
}

/**
 * A function for generating a new thumb nail for the page
 */
function generateThumbnail() {
    var html = [];
    html.push('<div class="thumbnail" rel="popover"',
            ' data-content="ID: ',$('#pckgId').val(),
            '<br/>Name: ',$('#pckgName').val(),
            '<br/>Description: ',$('#pckgDescription').val(),
            '<br/>Thumbnail: ',$('#thumbnail').val(),
            '<br/>Icon: ',$('#icon').val(),'<br/>"',
            ' data-original-title="',$('#pckgName').val(),'" id="hoveroverimage',$('#pckgId').val(),'">',
            '<img src="',$('#thumbnail').val(),'" alt="',$('#pckgDescription').val(),'">',
            '<div class="caption">',
            '<h5>','<img src="',$('#icon').val(),'" style="height:16px;width:16px;"/> ',$('#pckgName').val(),'</h5>',
            '<p>',$('#pckgDescription').val(),'</p>',
            '<p><a href="javascript:removePckg(\'',$('#pckgId').val(),'\');" class="btn btn-primary">Remove</a> <a href="javascript:updatePckg(\'',$('#pckgId').val(),'\');" class="btn">Update</a></p>',
            '<form id="existingPckgForm',$('#pckgId').val(),'">',
            '<input type="hidden" name="existingPckgId',$('#pckgId').val(),'" id="existingPckgId',$('#pckgId').val(),'" value="',$('#pckgId').val(),'" />',
            '<input type="hidden" name="existingPckgName',$('#pckgId').val(),'" id="existingPckgName',$('#pckgId').val(),'" value="',$('#pckgName').val(),'" />',
            '<input type="hidden" name="existingPckgDescription',$('#pckgId').val(),'" id="existingPckgDescription',$('#pckgId').val(),'" value="',$('#pckgDescription').val(),'" />',
            '<input type="hidden" name="existingPckgThumbnail',$('#pckgId').val(),'" id="existingPckgThumbnail',$('#pckgId').val(),'" value="',$('#thumbnail').val(),'" />',
            '<input type="hidden" name="existingPckgIcon',$('#pckgId').val(),'" id="existingPckgIcon',$('#pckgId').val(),'" value="',$('#icon').val(),'" />',
            '<input type="hidden" name="existingPckgDataType',$('#pckgId').val(),'" id="existingPckgDataType',$('#pckgId').val(),'" value="',$('#pckgDataType').val(),'" />',
            '<input type="hidden" name="existingPckgCategory',$('#pckgId').val(),'" id="existingPckgCategory',$('#pckgId').val(),'" value="',$('#pckgCategory').val(),'" />',
            '<input type="hidden" name="existingPckgVendor',$('#pckgId').val(),'" id="existingPckgVendor',$('#pckgId').val(),'" value="',$('#pckgVendor').val(),'" />',
            '<input type="hidden" name="existingDependency',$('#pckgId').val(),'" id="existingDependency',$('#pckgId').val(),'" value="',$('#pckgDependency').val(),'" />',
            '</form>',
            '</div>',
            '</div>');
    
    return html.join("");
}

