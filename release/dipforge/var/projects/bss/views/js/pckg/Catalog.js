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
                        '<a href="javascript:addEntry(',jsonPath,',\'',$('#catalogEntryId').val(),'\');"><i class="icon-plus"></i></a><a href="javascript:removeEntry(',jsonPath,',\'',$('#catalogEntryId').val(),'\');"><i class="icon-minus"></i></a> ');
                    html.push(pathValue.join(''),' </li></div>');
                    $("#catId" + $('#parentId').val()).after(html.join(""));
                    
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


function removeEntry(path,entryId) {
    
    
    $('#removeModal').modal('show');
}


