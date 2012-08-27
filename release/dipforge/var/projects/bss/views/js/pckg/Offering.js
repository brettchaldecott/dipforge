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
 * Offering.js
 * @author brett chaldecott
 */

var modalAction = 0;

/* initilize the page */
$(document).ready(function() {
    $('#addOfferingItem').click(function(event) {
        event.preventDefault();
        $('#offeringForm').submit();
    });
    
    $('#updateOfferingItem').click(function(event) {
        event.preventDefault();
        $('#offeringForm').submit();
    });
    
    $('#removeOfferingItem').click(function(event) {
        event.preventDefault();
        $.ajax({
                url: 'offering/RemoveOffering.groovy?offeringId=' + $('#removeModalFormId').val(),
                success: function(data) {
                    if (data == "success") {
                        // setup the message
                        $('#removeModelMsg').hide();
                        
                        $('#removeModelSuccessMsg').show();
                        $('#removeModelSuccessResultMsg').text('Offering removal [' + 
                            $('#removeModalFormId').val() + '] submitted to system.');
                        $('#removeModelErrorMsg').hide();
                        
                        // setup the buttons
                        $('#removeOfferingCloseButton').hide();
                        $('#removeOfferingItem').hide();
                        
                        // remove the thumbnail
                        $('#offeringThumbnailEntry' + $('#removeModalFormId').val()).remove();
                        
                        // delay and hide the modal
                        setTimeout(function () {
                            $('#removeModal').modal('hide');    
                        }, 1500);
                        
                    } else {
                        // setup the message
                        $('#removeModelMsg').hide();
                        
                        $('#removeModelSuccessMsg').hide();
                        $('#removeModelErrorMsg').show();
                        $('#removeModelErrorResultMsg').html('Offering [' + 
                            $('#removeModalFormId').val() + '] could not be removed : ' + data);
                        
                        // setup the buttons
                        $('#removeOfferingCloseButton').show();
                        $('#removeOfferingItem').hide();
                        
                    }
                }
        });
    });
    
    $("#offeringId").validate({
        expression: "if (VAL) return true; else return false;",
        message: "Must provide ID"
    });
    
    $("#offeringName").validate({
        expression: "if (VAL) return true; else return false;",
        message: "Must provide name"
    });
    
    $("#offeringDescription").validate({
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
    
    
    $('#offeringForm').validated(function() {
        
        var valid = true;
        var number = $('tr[id^="costId"]').size();
        $('tr[id^="costId"]').each(function(index,element) {
            $.each(element.cells,function(columnIndex,cell) {
                if (columnIndex !== 0) {
                    $.each(cell.children,function(childIndex,child) {
                        if ((!child.value || child.value === "") && index < (number - 1)) {
                            $(child).addClass("error-block");
                            valid = false;
                        } else if (child.id === "costValue" && isNaN(child.value)) {
                            $(child).addClass("error-block");
                            valid = false;
                        } else {
                            $(child).removeClass("error-block");
                        }
                    });
                }
            });
        });
        
        if (valid) {
        
            // hide all error messages
            $('#modelDataErrorResult').hide();
            $('#modelSuccessResult').hide();
            $('#modelRuntimeErrorResult').hide();
            
            if (modalAction === 0) {
                $.ajax({
                    url: 'offering/CreateOffering.groovy?' + $('#offeringForm').serialize(),
                    success: function(data) {
                        if (data == 'success') {
                            $('#modelRuntimeErrorResult').hide();
                            $('#updateOfferingItem').hide();
                            $('#addOfferingItem').hide();
                            $('#offeringCloseButton').hide();
                            $('#modelForm').hide();
                            $('#modelSuccessResult').show();
                            $('#modelSuccessResultMsg').text('Offering add [' + 
                                $('#offeringId').val() + '] submitted to system.');
                                
                            var html = [];
                            html.push(
                                '<li class="span3" id="offeringThumbnailEntry',$('#offeringId').val(),'">',
                                generateThumbnail(),
                                '</li>');
                            $("#offeringThumbnails").append(html.join(""));
                            
                            $('#offeringDependency').append('<option value="' + $('#offeringId').val() + '">' + $('#offeringName').val() + '</option>');
                            
                            $('[id^=hoveroverimage]').popover({"placement":"bottom"});
                            
                            // delay and hide the modal
                            setTimeout(function () {
                                $('#offeringModal').modal('hide');    
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
                    url: 'offering/UpdateOffering.groovy?offeringId=' + $('#offeringId').val() + '&' + $('#offeringForm').serialize(),
                    success: function(data) {
                        if (data == 'success') {
                            $('#modelRuntimeErrorResult').hide();
                            $('#addOfferingItem').hide();
                            $('#updateOfferingItem').hide();
                            $('#offeringCloseButton').hide();
                            $('#modelForm').hide();
                            $('#modelSuccessResult').show();
                            $('#modelSuccessResultMsg').text('Offering update [' + 
                                $('#offeringId').val() + '] submitted to system.');
                            
                            var html = [];
                            html.push(generateThumbnail());
                            $("#offeringThumbnailEntry" + $('#offeringId').val()).html(html.join(""));
                            
                            $('[id^=hoveroverimage]').popover({"placement":"bottom"});
                            
                            // delay and hide the modal
                            setTimeout(function () {
                                $('#offeringModal').modal('hide');    
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
        }
    });
    
    $('#offeringModal').on('hidden', function () {
        
        $('#offeringId').val('');
        $('#offeringName').val('');
        $('#offeringDescription').val('');
        $('#thumbnail').val('');
        $('#icon').val('');
        $('#offeringDataType').val('');
        $('#offeringJavascriptConfigUrl').val('');
        $('#offeringGroovyConfigUrl').val('');
        $('#addOfferingItem').show();
        $('#updateOfferingItem').hide();
        $('#offeringCloseButton').show();
        $('#modelForm').show();
        $('#modelDataErrorResult').hide();
        $('#modelSuccessResult').hide();
        $('#modelRuntimeErrorResult').hide();
        $('#addOfferingTitle').show();
        $('#updateOfferingTitle').hide();
        $('#offeringId').removeAttr('disabled');
        modalAction = 0;
    });
    
    $('#offeringModal').on('show', function() {
        if ($('#offeringId').val() === null || $('#offeringId').val() === '') {
            var guid = new GUID();
            $('#offeringId').val(guid.getValue());
        }
        if (modalAction !== 1) {
            $('#costEntry').empty();
            $('#costEntry').append(generateNewRecord());
        }
    });
    
    
    $('#removeModal').on('hidden', function () {
        $('#removeModelMsg').show();
        $('#removeModelSuccessMsg').hide();
        $('#removeModelErrorMsg').hide();
        $('#removeOfferingCloseButton').show();
        $('#removeOfferingItem').show();
        
        
    });
    
    
    $('[id^=hoveroverimage]').popover({"placement":"bottom"});
});


/**
 * This method is called to edit a offering
 */
function updateOffering(offeringId) {
    modalAction = 1;
    
    $('#offeringId').val($('#existingOfferingId' + offeringId).val());
    $('#offeringName').val($('#existingOfferingName' + offeringId).val());
    $('#offeringDescription').val($('#existingOfferingDescription' + offeringId).val());
    $('#thumbnail').val($('#existingOfferingThumbnail' + offeringId).val());
    $('#icon').val($('#existingOfferingIcon' + offeringId).val());
    $('select#offeringPackage option')
        .each(function() { this.selected = (this.value == $('#existingofferingPackage' + offeringId).val()); });
    $('select#offeringCatalog option')
        .each(function() { this.selected = (this.value == $('#existingofferingCatalog' + offeringId).val()); });
    var costTable = [];
    if ($('#existingOfferingCosts' + offeringId).val() !== null && $('#existingOfferingCosts' + offeringId).val() !== "") {
        var costConfigList = $('#existingOfferingCosts' + offeringId).val().split("||");
        $.each(costConfigList,function(index,config) {
            var configRecord = config.split(",,");
            costTable.push(
                generateUpdateRecord(configRecord[0],configRecord[1],configRecord[2],configRecord[3]));
        });
    }
    costTable.push(generateNewRecord());
    $('#costEntry').empty();
    $('#costEntry').append(costTable.join(''));
    
    $('#offeringId').attr('disabled','disabled'); 
    $('#addOfferingTitle').hide();
    $('#updateOfferingTitle').show();
    $('#addOfferingItem').hide();
    $('#updateOfferingItem').show();
    $('#offeringModal').modal('show');
}


/**
 * This function is called to remove the offering
 */
function removeOffering(offeringId) {
    $('#removeModalFormId').val(offeringId);
    
    // setup the message
    $('#removeModelMsg').show();
    $('#removeModelText').html("Are you sure you want to remove the offering [" + offeringId + "]");
    
    $('#removeModelSuccessMsg').hide();
    $('#removeModelErrorMsg').hide();
    
    // setup the buttons
    $('#removeOfferingCloseButton').show();
    $('#removeOfferingItem').show();
    
    // show the model
    $('#removeModal').modal('show');
}

/**
 * A function for generating a new thumb nail for the page
 */
function generateThumbnail() {
    var html = [];
    
    var configValues = "Javascript," + $('#offeringJavascriptConfigUrl').val() + '|Groovy,' + $('#offeringGroovyConfigUrl').val();
    
    var costValues = "";
    var valueSep = "";
    var valid = true;
    $('tr[id^="costId"]').each(function(index,element) {
        var sep = "";
        var elementValue = "";
        $.each(element.cells,function(index,cell) {
            if (index !== 0) {
                $.each(cell.children,function(childIndex,child) {
                    if (child.value === null || child.value === "") {
                        valid = false;
                    }
                    elementValue += sep + child.value;
                    sep = ",,";
                });
            }
        });
        costValues += valueSep + elementValue;
        valueSep = "||";
    });
    var size = $('tr[id^="costId"]').size();
    if (size == 1 && !valid) {
        costValues = "";
    }
    
    html.push('<div class="thumbnail" rel="popover"',
            ' data-content="ID: ',$('#offeringId').val(),
            '<br/>Name: ',$('#offeringName').val(),
            '<br/>Description: ',$('#offeringDescription').val(),
            '<br/>Thumbnail: ',$('#thumbnail').val(),
            '<br/>Icon: ',$('#icon').val(),'<br/>"',
            ' data-original-title="',$('#offeringName').val(),'" id="hoveroverimage',$('#offeringId').val(),'">',
            '<img src="../',$('#thumbnail').val(),'" alt="',$('#offeringDescription').val(),'">',
            '<div class="caption">',
            '<h5>','<img src="../',$('#icon').val(),'" style="height:16px;width:16px;"/> ',$('#offeringName').val(),'</h5>',
            '<p>',$('#offeringDescription').val(),'</p>',
            '<p><a href="javascript:removeOffering(\'',$('#offeringId').val(),'\');" class="btn btn-primary">Remove</a> <a href="javascript:updateOffering(\'',$('#offeringId').val(),'\');" class="btn">Update</a></p>',
            '<form id="existingOfferingForm',$('#offeringId').val(),'">',
            '<input type="hidden" name="existingOfferingId',$('#offeringId').val(),'" id="existingOfferingId',$('#offeringId').val(),'" value="',$('#offeringId').val(),'" />',
            '<input type="hidden" name="existingOfferingName',$('#offeringId').val(),'" id="existingOfferingName',$('#offeringId').val(),'" value="',$('#offeringName').val(),'" />',
            '<input type="hidden" name="existingOfferingDescription',$('#offeringId').val(),'" id="existingOfferingDescription',$('#offeringId').val(),'" value="',$('#offeringDescription').val(),'" />',
            '<input type="hidden" name="existingOfferingThumbnail',$('#offeringId').val(),'" id="existingOfferingThumbnail',$('#offeringId').val(),'" value="',$('#thumbnail').val(),'" />',
            '<input type="hidden" name="existingOfferingIcon',$('#offeringId').val(),'" id="existingOfferingIcon',$('#offeringId').val(),'" value="',$('#icon').val(),'" />',
            '<input type="hidden" name="existingofferingPackage',$('#offeringId').val(),'" id="existingofferingPackage',$('#offeringId').val(),'" value="',$('#offeringPackage').val(),'" />',
            '<input type="hidden" name="existingofferingCatalog',$('#offeringId').val(),'" id="existingofferingCatalog',$('#offeringId').val(),'" value="',$('#offeringCatalog').val(),'" />',
            '<input type="hidden" name="existingOfferingCosts',$('#offeringId').val(),'" id="existingOfferingCosts',$('#offeringId').val(),'" value="',costValues,'" />',
            '</form>',
            '</div>',
            '</div>');
    
    return html.join("");
}


function generateNewRecord() {
    var guid = new GUID();
    var rowEntry = [];
    rowEntry.push(
        '<tr id="costId',guid.getValue(),'">',
        '<td>',
        '<div id="addDiv', guid.getValue(),'">',
        '<a href="javascript:addRecord(\'',guid.getValue(),'\');"><i class="icon-plus-sign"></i></a>',
        '</div>',
        '<div id="removeDiv', guid.getValue(),'" style="display:none">',
        '<a href="javascript:removeRecord(\'',guid.getValue(),'\');"><i class="icon-minus-sign"></i></a>',
        '</div>',
        '</td>',
        '<td>',
        '<input type="hidden" name="costId" value="',guid.getValue(),'"/>',
        '<input type="text" style="width:100%;" id="costLineItem" name="costLineItem" placeholder="Line Item"></td>',
        '<td>',
        '<select style="width:100%;" id="costType" name="costType">',
        '<option value="setup">Setup</option>',
        '<option value="monthly">Monthly</option>',
        '<option value="quartly">Quartly</option>',
        '<option value="annually">Annually</option>',
        '</select>',
        '</td>',
        '<td><input type="text" class="input-small" id="costValue" name="costValue" placeholder="cents"></td>',
        '</tr>');
    return rowEntry.join('');
}


function generateUpdateRecord(
        guid, lineItem, type, value) {
    var rowEntry = [];
    rowEntry.push(
        '<tr id="costId',guid,'">',
        '<td>',
        '<div id="addDiv', guid,'" style="display:none">',
        '<a href="javascript:addRecord(\'',guid,'\');"><i class="icon-plus-sign"></i></a>',
        '</div>',
        '<div id="removeDiv', guid,'">',
        '<a href="javascript:removeRecord(\'',guid,'\');"><i class="icon-minus-sign"></i></a>',
        '</div>',
        '</td>',
        '<td>',
        '<input type="hidden" name="costId" value="',guid,'"/>',
        '<input type="text" style="width:100%;" id="costLineItem" name="costLineItem" value="',lineItem,'" placeholder="Line Item"></td>',
        '<td>',
        '<select style="width:100%;" id="costType" name="costType">',
        '<option value="setup" ',(type == "setup"? "selected=true" : ""),'>Setup</option>',
        '<option value="monthly" ',(type == "monthly"? "selected=true" : ""),'>Monthly</option>',
        '<option value="quartly" ',(type == "quartly"? "selected=true" : ""),'>Quartly</option>',
        '<option value="annually" ',(type == "annually"? "selected=true" : ""),'>Annually</option>',
        '</select>',
        '</td>',
        '<td><input type="text" class="input-small" id="costValue" name="costValue" value="',value,'" placeholder="0,000.00"></td>',
        '</tr>');
    return rowEntry.join('');
}

function addRecord(recordId) {
    $('#addDiv' + recordId).hide();
    $('#removeDiv' + recordId).show();
    $('#costEntry').append(generateNewRecord());
}


function removeRecord(recordId) {
    $('#' + recordId).remove();
}


