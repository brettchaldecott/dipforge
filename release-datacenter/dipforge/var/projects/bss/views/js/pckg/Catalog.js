/*
 * bss: Description
 * Copyright (C) Mon Aug 06 06:48:38 SAST 2012 owner
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
 * Catalog.js
 * @author brett chaldecott
 */

var modalAction = 0;
var currentPath = [];

$(document).ready(function() {
    $('#addCatalogItem').click(function(event) {
        event.preventDefault();
        $('#catalogForm').submit();
    });
    
    $('#updateCatalogItem').click(function(event) {
        event.preventDefault();
        $('#catalogForm').submit();
    });
    
    $('#removeCatalogItem').click(function(event) {
        event.preventDefault();
        $.ajax({
                url: 'catalog/RemoveCatalogEntry.groovy?catalogEntryId=' + $('#removeModalFormId').val(),
                success: function(data) {
                    if (data == "success") {
                        // setup the message
                        $('#removeModelMsg').hide();
                        
                        $('#removeModelSuccessMsg').show();
                        $('#removeModelSuccessResultMsg').text('Catalog removal [' + 
                            $('#removeModalFormId').val() + '] submitted to system.');
                        $('#removeModelErrorMsg').hide();
                        
                        // setup the buttons
                        $('#removeCatalogCloseButton').hide();
                        $('#removeCatalogItem').hide();
                        
                        // remove the thumbnail
                        $('#catId' + $('#removeModalFormId').val()).remove();
                        
                        // delay and hide the modal
                        setTimeout(function () {
                            $('#removeModal').modal('hide');    
                        }, 1500);
                        
                    } else {
                        // setup the message
                        $('#removeModelMsg').hide();
                        
                        $('#removeModelSuccessMsg').hide();
                        $('#removeModelErrorMsg').show();
                        $('#removeModelErrorResultMsg').html('Catalog [' + 
                            $('#removeModalFormId').val() + '] could not be removed : ' + data);
                        
                        // setup the buttons
                        $('#removeCategoryCloseButton').show();
                        $('#removeCategoryItem').hide();
                        
                    }
                }
        });
    });
    
    $("#catalogEntryId").validate({
        expression: "if (VAL) return true; else return false;",
        message: "Must provide ID"
    });
    
    $("#catalogEntryName").validate({
        expression: "if (VAL) return true; else return false;",
        message: "Must provide name"
    });
    
    $("#catalogEntryDescription").validate({
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
    
    $('#catalogForm').validated(function() {
        
        if (modalAction === 0) {
            $.ajax({
                url: 'catalog/AddCatalogEntry.groovy?' + $('#catalogForm').serialize(),
                success: function(data) {
                    if (data == 'success') {
                        $('#modelRuntimeErrorResult').hide();
                        $('#updateCatalogItem').hide();
                        $('#addCatalogItem').hide();
                        $('#catalogCloseButton').hide();
                        $('#modelForm').hide();
                        $('#modelSuccessResult').show();
                        $('#modelSuccessResultMsg').text('Catalog add [' + 
                            $('#pckgId').val() + '] submitted to system.');
                        
                        $("#catId" + $('#parentId').val()).after(generateHTMLPath().join(""));
                        
                        // delay and hide the modal
                        setTimeout(function () {
                            $('#catalogModal').modal('hide');    
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
                url: 'catalog/UpdateCatalogEntry.groovy?' + $('#catalogForm').serialize(),
                success: function(data) {
                    if (data == 'success') {
                        $('#modelRuntimeErrorResult').hide();
                        $('#updateCatalogItem').hide();
                        $('#addCatalogItem').hide();
                        $('#catalogCloseButton').hide();
                        $('#modelForm').hide();
                        $('#modelSuccessResult').show();
                        $('#modelSuccessResultMsg').text('Catalog add [' + 
                            $('#pckgId').val() + '] submitted to system.');
                        
                        currentPath = currentPath.slice(0,currentPath.length -1);
                        
                        $("#catId" + $('#catalogEntryId').val()).replaceWith(generateHTMLPath().join(""));
                        
                        // delay and hide the modal
                        setTimeout(function () {
                            // this is a a hack to work around not re-working the page contents using
                            // javascript which would be a lot more effecient.
                            window.location.reload();
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
    
    $('#catalogModal').on('hidden', function () {
        
        $('#catalogId').val('');
        $('#catalogEntryId').val('');
        $('#catalogEntryName').val('');
        $('#catalogEntryDescription').val('');
        $('#thumbnail').val('');
        $('#icon').val('');
        $('#addCatalogItem').show();
        $('#updateCatalogItem').hide();
        $('#catalogCloseButton').show();
        $('#modelForm').show();
        $('#modelDataErrorResult').hide();
        $('#modelSuccessResult').hide();
        $('#modelRuntimeErrorResult').hide();
        $('#addCatalogTitle').show();
        $('#updateCatalogTitle').hide();
        $('#offeringId').removeAttr('disabled');
        modalAction = 0;
    });
    
    $('#removeModal').on('show', function () {
        $('#removeModelMsg').show();
        $('#removeModelSuccessMsg').hide();
        $('#removeModelErrorMsg').hide();
        $('#removeCatalogCloseButton').show();
        $('#removeCatalogItem').show();
    });
});


function addEntry(path,parentId) {
    var guid = new GUID();
    var pathValue = [];
    currentPath = path;
    pathValue.push('<li>');
    $.each(path, function(index, section) {
        pathValue.push(section,' <span class="divider">/</span> ');
    });
    pathValue.push('</li>');
    $('#catalogModalPath').empty();
    $('#catalogModalPath').append(pathValue.join(''));
    
    $('#parentId').val(parentId);
    $('#catalogEntryId').val(guid.getValue());
    $('#catalogModal').modal('show');
}


function updateEntry(path,entryId) {
    modalAction = 1;
    var pathValue = [];
    currentPath = path;
    pathValue.push('<li>');
    $.each(path, function(index, section) {
        pathValue.push(section,' <span class="divider">/</span> ');
    });
    pathValue.push('</li>');
    $('#catalogModalPath').empty();
    $('#catalogModalPath').append(pathValue.join(''));
    
    $('#parentId').val(parentId);
    $('#catalogEntryId').val($('#existingCatalogEntryId' + entryId).val());
    $('#catalogEntryName').val($('#existingCatalogEntryName' + entryId).val());
    $('#catalogEntryDescription').val($('#existingCatalogEntryDescription' + entryId).val());
    $('#thumbnail').val($('#existingCatalogEntryThumbnail' + entryId).val());
    $('#icon').val($('#existingCatalogEntryIcon' + entryId).val());
    
    $('#addCatalogItem').hide();
    $('#updateCatalogItem').show();
    $('#catalogCloseButton').show();
        
    $('#catalogModal').modal('show');
}


function removeEntry(path,entryId) {
    var pathValue = [];
    pathValue.push('<li>');
    $.each(path, function(index, section) {
        pathValue.push(section,' <span class="divider">/</span> ');
    });
    pathValue.push('</li>');
    $('#catalogRemoveModalPath').empty();
    $('#catalogRemoveModalPath').append(pathValue.join(''));
    
    $('#removeModalFormId').val(entryId);
    
    // setup the message
    $('#removeModelMsg').show();
    $('#removeModelText').html("Are you sure you want to remove the catalog entry [" + entryId + "]");
    
    $('#removeModelSuccessMsg').hide();
    $('#removeModelErrorMsg').hide();
    
    $('#removeModal').modal('show');
}


function generateHTMLPath() {
    // generate a new html section
    var pathValue = [];
    var jsonPath = "[";
    var sep = "";
    $.each(currentPath, function(index, section) {
            pathValue.push(section,' <span class="divider">/</span> ');
            jsonPath += sep + "\'" + section + "\'";
            sep = ",";
        });
    pathValue.push($('#catalogEntryName').val());
    jsonPath += sep + "\'" + $('#catalogEntryName').val() + "\']";
    var html = [];
    html.push(
        '<div id="catId',$('#catalogEntryId').val(),'">',
        '<li>',
        '<a href="javascript:addEntry(',jsonPath,',\'',$('#catalogEntryId').val(),'\');"><i class="icon-plus"></i></a><a href="javascript:removeEntry(',jsonPath,',\'',$('#catalogEntryId').val(),'\');"><i class="icon-minus"></i></a> <a href="javascript:updateEntry(',jsonPath,',\'',$('#catalogEntryId').val(),'\');">');
    html.push(pathValue.join(''),' </li></a></div>');
    html.push('<form style="display:none;" id="existingCatalogForm',$('#catalogEntryId').val(),'" name="existingCatalogForm',$('#catalogEntryId').val(),'">',
        '<input type="hidden" name="existingCatalogEntryId',$('#catalogEntryId').val(),'" id="existingCatalogEntryId',$('#catalogEntryId').val(),'" value="',$('#catalogEntryId').val(),'" />',
        '<input type="hidden" name="existingCatalogEntryName',$('#catalogEntryId').val(),'" id="existingCatalogEntryName',$('#catalogEntryId').val(),'" value="',$('#catalogEntryName').val(),'" />',
        '<input type="hidden" name="existingCatalogEntryDescription',$('#catalogEntryId').val(),'" id="existingCatalogEntryDescription',$('#catalogEntryId').val(),'" value="',$('#catalogEntryDescription').val(),'" />',
        '<input type="hidden" name="existingCatalogEntryThumbnail',$('#catalogEntryId').val(),'" id="existingCatalogEntryThumbnail',$('#catalogEntryId').val(),'" value="',$('#thumbnail').val(),'" />',
        '<input type="hidden" name="existingCatalogEntryIcon',$('#catalogEntryId').val(),'" id="existingCatalogEntryIcon',$('#catalogEntryId').val(),'" value="',$('#icon').val(),'" />',
        '</form>');
    return html;
}

