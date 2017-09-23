/*
 * bss: Description
 * Copyright (C) Sat Dec 29 08:58:56 SAST 2012 owner
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
 * Checkout.js
 * @author brett chaldecott
 */


$(document).ready(function() {
    
    $('#offeringModal').on('hidden', function () {
        
        
        
    });
    
    $('[id^=hoveroverimage]').popover({"placement":"bottom","trigger":"hover","html":"true"});
    
});


function removeOffering(offeringId) {
    $.ajax({
        url: 'shopping/RemoveFromBasket.groovy',
        data: "offeringId=" + offeringId,
        success: function(data) {
            if (data == "success") {
                // setup the message
                $('#msgModelRuntimeErrorResult').hide();
                
                $('#msgModelSuccessResult').show();
                $('#msgModelSuccessResultMsg').text('Removed offering.');
                
                $('#msgModal').modal('show');
                
                // delay and hide the modal
                setTimeout(function () {
                    // this is a a hack to work around not re-working the page contents using
                    // javascript which would be a lot more effecient.
                    window.location.reload();  
                }, 1000);
                
            } else {
                // setup the message
                $('#msgModelSuccessResult').hide();
                
                $('#msgModelRuntimeErrorResult').show();
                $('#msgModelRuntimeErrorResultMsg').text('Failed to remove the offering.');
                
                $('#msgMModal').modal('show');
                
                // delay and hide the modal
                setTimeout(function () {
                    $('#msgMModal').modal('hide');    
                }, 1000);
            }
        },
        failure: function(data) {
            // setup the message
                $('#msgModelSuccessResult').hide();
                
                $('#msgModelRuntimeErrorResult').hide();
                $('#msgModelRuntimeErrorResultMsg').text('Failed to remove the offering.');
                
                $('#msgModal').modal('show');
                
                // delay and hide the modal
                setTimeout(function () {
                    $('#msgModal').modal('hide');    
                }, 1000);
        }
    });
}

function updateOffering(offeringId) {
    
    // setup up model display values
    $('#offeringId').val($('#existingOfferingId' + offeringId).val());
    $('#installedOfferingId').val($('#existingOfferingInstallId' + offeringId).val());
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
        configObj.populateConfiguredValues(offeringId);
    });
    
    $('#updateOffering').click(function (event) {
        event.preventDefault();
        $('#configurationForm').submit();
    });
    
    $('#configurationForm').validated(function() {
        updateTheCart();
    });
    
    // setup the buttons
    $('#purchaseOffering').show();
    $('#configurationForm').show();
    
    $('#modelDataErrorResult').hide();
    $('#modelSuccessResult').hide();
    $('#modelRuntimeErrorResult').hide();
    
    $('#offeringModal').modal('show');
    
}

function updateTheCart() {
    $.ajax({
        url: 'shopping/UpdateBasket.groovy',
        data: $('#configurationForm').serialize(),
        success: function(data) {
            if (data == "success") {
                // setup the message
                $('#modelDataErrorResult').hide();
                $('#modelRuntimeErrorResult').hide();
                
                $('#modelSuccessResult').show();
                $('#modelSuccessResultMsg').text('Updated the basket.');
                
                // setup the buttons
                $('#purchaseOffering').hide();
                $('#configurationForm').hide();
                
                // delay and hide the modal
                setTimeout(function () {
                    // this is a a hack to work around not re-working the page contents using
                    // javascript which would be a lot more effecient.
                    window.location.reload();    
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
            $('#modelRuntimeErrorResultMsg').html("Failed to update the basket.");
        }
    });
}