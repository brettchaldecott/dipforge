/*
 * bss: Description
 * Copyright (C) Mon Dec 03 04:55:29 SAST 2012 owner
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
 * Shopping.js
 * @author brett chaldecott
 */

var emailRegex = /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;

$(document).ready(function() {
    
    $('#offeringModal').on('hidden', function () {
        
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
    
    $('[id^=hoveroverimage]').popover({"placement":"bottom","trigger":"hover","html":"true"});
    
});


function addOffering(offeringId) {
    var guid = new GUID();
    
    $('#offeringId').val($('#existingOfferingId' + offeringId).val());
    $('#installedOfferingId').val(guid.getValue());
    
    
    // setup up model display values
    $('#offeringName').empty();
    $('#offeringName').append($('#existingOfferingName' + offeringId).val());
    $('#offeringDescription').empty();
    $('#offeringDescription').append($('#existingOfferingDescription' + offeringId).val());
    $('#offeringImage').empty();
    $('#offeringImage').append('<img src="'+ $('#existingOfferingThumbnail' + offeringId).val() + '"/>');
    $('#offeringSetupCost').empty();
    $('#offeringSetupCost').append($('#existingOfferingSetup' + offeringId).val());
    $('#offeringMonthlyCost').empty();
    $('#offeringMonthlyCost').append($('#existingOfferingMonthly' + offeringId).val());
    
    var pckgProducts = $('#existingOfferingProducts' + offeringId).val().split(",");
    $('#offeringConfig').empty();
    $.each(pckgProducts, function(i, productId) {
        var divId = productId + "div";
        var configObj = productMap[productId];
        var divHtml = '<div id="' + divId + '">' + configObj.createDiv() + "</div>";
        $('#offeringConfig').append(divHtml);
        configObj.setupHooks();
        configObj.populateValues(offeringId);
    });
    
    $('#purchaseOffering').click(function (event) {
        event.preventDefault();
        $('#configurationForm').submit();
    });
    
    $('#configurationForm').validated(function() {
        addToCart();
    });
    
    // setup the buttons
    $('#purchaseOffering').show();
    $('#configurationForm').show();
    
    $('#modelDataErrorResult').hide();
    $('#modelSuccessResult').hide();
    $('#modelRuntimeErrorResult').hide();
    
    $('#offeringModal').modal('show');
    
}

function addToCart() {
    $.ajax({
        url: 'shopping/AddToBasket.groovy',
        data: $('#configurationForm').serialize(),
        success: function(data) {
            if (data == "success") {
                // setup the message
                $('#modelDataErrorResult').hide();
                $('#modelRuntimeErrorResult').hide();
                
                $('#modelSuccessResult').show();
                $('#modelSuccessResultMsg').text('Added to basket.');
                
                // setup the buttons
                $('#purchaseOffering').hide();
                $('#configurationForm').hide();
                
                // delay and hide the modal
                setTimeout(function () {
                    $('#offeringModal').modal('hide');    
                }, 1000);
                
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
            $('#modelRuntimeErrorResultMsg').html("Failed to add to the basket.");
        }
    });
}

