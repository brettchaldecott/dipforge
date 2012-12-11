<!--
Date: Wed Jun 27 05:43:54 SAST 2012
File: list.gsp
Author: brett chaldecott
-->
<% import java.text.DecimalFormat %>

    <div class="span9">
      <ul class="thumbnails" id="offeringThumbnails">
        <%
        params.offerings.each { offerings ->
            def offering = offerings[0]
            offering.getPckg().getProducts().each { productConfig ->
                productConfig.getProduct().getConfigurationManager().each { config ->
                    
                }
            }
            %>
            <li class="span3" id="offeringThumbnailEntry${offering.getId()}">
                <div class="thumbnail" rel="popover" 
                    data-content="Name: ${offering.getName()}<br/>Description: ${offering.getDescription()}"
                    data-original-title="${offering.getName()}" id="hoveroverimage${offering.getId()}">
                    <img src="${params.contextBase}${offering.getThumbnail()}" alt="${offering.getDescription()}">
                    <div class="caption">
                        <h5><img src="${params.contextBase}${offering.getIcon()}" style="height:16px;width:16px;"/> ${offering.getName()}</h5>
                        <p>${offering.getDescription()} - ${offering.getPckg().getDescription()}</p>
                        <%
                            def costs = ""
                            def sep = ""
                            def setupCost = 0.0
                            def monthlyCost = 0.0
                            def format = new DecimalFormat("#,##0.00")
                            offering.getCosts()?.each { cost ->
                                if (cost.getType() != null && cost.getType() != "") {
                                    costs += sep + cost.getId() + ",," + cost.getLineItem() + ",," + cost.getType() + ",," + cost.getAmount()
                                    sep = "||"
                                    if (cost.getType() == "setup") {
                                        setupCost += cost.getAmount()
                                    } else if (cost.getType() == "monthly") {
                                        monthlyCost += cost.getAmount()
                                    } 
                                    
                                }
                            }
                            setupCost = setupCost / 100
                            monthlyCost = monthlyCost / 100
                            
                        %>
                        <p><b>Setup</b> ${format.format(setupCost)}</p>
                        <p><b>Monthly</b> ${format.format(monthlyCost)}</p>
                        <p><a href="javascript:addOffering('${offering.getId()}');" class="btn btn-primary"><i class="icon-shopping-cart"></i>Add</a></p>
                        <form id="offeringForm${offering.getId()}" name="offeringForm${offering.getId()}">
                            <input type="hidden" name="offeringId${offering.getId()}" id="offeringId${offering.getId()}" value="${offering.getId()}" />
                            <input type="hidden" name="offeringName${offering.getId()}" id="offeringName${offering.getId()}" value="${offering.getName()}" />
                            <input type="hidden" name="offeringDescription${offering.getId()}" id="offeringDescription${offering.getId()}" value="${offering.getDescription()}" />
                            <input type="hidden" name="offeringCosts${offering.getId()}" id="offeringCosts${offering.getId()}" value="${costs}" />
                        </form>
                    </div>
                </div>
            </li>
            
        
        <% } %>
      </ul><!--/row-->
    </div><!--/span-->
    
<div class="modal hide" id="offeringModal">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <div id="addOfferingTitle">
        <h3>Purchase</h3>
        
    </div>
  </div>
  <div class="modal-body">
        <p id="offeringName">Name</p>
        <p id="offeringDescription">Description</p>
        <p id="offeringSetupCost">0.00</p>
        <p id="offeringMonthlyCost">0.00</p>        
  </div>
  <div class="modal-body">
        <p><h4>Configuration</h4></p>
    <div id="modelForm">
        <form class="form-horizontal" id="configurationForm">
          <fieldset>
            <input type="hidden" id="installedOfferingId" name="installedOfferingId">
            <input type="hidden" id="offeringId" name="offeringId">
            <div class="control-group">
              <label class="control-label" for="domain">Domain</label>
              <div class="controls">
                <input type="text" class="input-large" id="domain" name="offeringId">
                <p class="help-block">Domain</p>
              </div>
            </div>
          </fieldset>
        </form>
        
        
        <div id="modelDataErrorResult" style="display:none;">
            <div class="alert fade in alert-error">
                <span id="modelDataErrorResultMsg"></span>
            </div>
        </div>
    </div>
    <div id="modelSuccessResult" style="display:none;">
        <div class="alert fade in alert-info">
            <span id="modelSuccessResultMsg"></span>
        </div>
    </div>
    <div id="modelRuntimeErrorResult" style="display:none;">
        <div class="alert fade in alert-error">
            <span id="modelRuntimeErrorResultMsg"></span>
        </div>
    </div>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn" data-dismiss="modal" id="purchaseCloseButton">Close</a>
    <a href="#" class="btn btn-primary" id="purchaseOffering"><i class="icon-shopping-cart"></i>Purchase</a>
  </div>
</div>
