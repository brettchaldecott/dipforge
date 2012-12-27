<!--
Date: Wed Jun 27 05:43:54 SAST 2012
File: list.gsp
Author: brett chaldecott
-->
<% import java.text.DecimalFormat %>

    <div class="span9">
      <ul class="thumbnails" id="offeringThumbnails">
        <%
        def importScripts = []
        params.offerings.each { offerings ->
            def offering = offerings[0]
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
                        <form id="offeringForm${offering.getId()}" name="existingOfferingForm${offering.getId()}">
                            <input type="hidden" name="existingOfferingId${offering.getId()}" id="existingOfferingId${offering.getId()}" value="${offering.getId()}" />
                            <input type="hidden" name="existingOfferingName${offering.getId()}" id="existingOfferingName${offering.getId()}" value="${offering.getName()}" />
                            <input type="hidden" name="existingOfferingDescription${offering.getId()}" id="existingOfferingDescription${offering.getId()}" value="${offering.getDescription()}" />
                            <input type="hidden" name="existingOfferingCosts${offering.getId()}" id="existingOfferingCosts${offering.getId()}" value="${costs}" />
                            <input type="hidden" name="existingOfferingThumbnail${offering.getId()}" id="existingOfferingThumbnail${offering.getId()}" value="${params.contextBase}${offering.getThumbnail()}" />
                            <input type="hidden" name="existingOfferingSetup${offering.getId()}" id="existingOfferingSetup${offering.getId()}" value="${format.format(setupCost)}" />
                            <input type="hidden" name="existingOfferingMonthly${offering.getId()}" id="existingOfferingMonthly${offering.getId()}" value="${format.format(monthlyCost)}" />
                            <%
                            def products = ""
                            def packageSep = ""
                            offering.getPckg().getProducts().each { productConfig ->
                                productConfig.getProduct().getConfigurationManager().each { config ->
                                    if (config.getName() == "Install") {
                                        if (!importScripts.contains(config.getUrl())) {
                                            importScripts.add("bss/" + config.getUrl())
                                        }
                                    }
                                }
                                def productId = productConfig.getProduct().getId();
                                products += packageSep + productId;
                                packageSep = ","
                                %>
                                <input type="hidden" name="existingOfferingData${offering.getId()}${productId}" id="existingOfferingData${offering.getId()}${productId}" value="${productConfig.getData()}" />
                                <%
                            }
                            %>
                            <input type="hidden" name="existingOfferingProducts${offering.getId()}" id="existingOfferingProducts${offering.getId()}" value="${products}" />
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
        <p id="offeringImage"></p>
        <p id="offeringName">Name</p>
        <p id="offeringDescription">Description</p>
        <p id="offeringSetupCost">0.00</p>
        <p id="offeringMonthlyCost">0.00</p>        
        <p><h4>Configuration</h4></p>
    <div id="modelForm">
        <form class="form-horizontal" id="configurationForm">
          <fieldset>
            <input type="hidden" id="offeringId" name="offeringId">
            <input type="hidden" id="installedOfferingId" name="installedOfferingId">
            <div id="offeringConfig">
                <div class="control-group">
                  <label class="control-label" for="domain">Domain</label>
                  <div class="controls">
                    <input type="text" class="input-large" id="domain" name="offeringId">
                    <p class="help-block">Domain</p>
                  </div>
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

<script type="text/javascript">
var productMap = {};
</script>

<!-- Le javascript
================================================== -->
<!-- Due to include structure Javascript placed at top of document -->
<script src="jquery/jquery-1.7.2.min.js"></script>
<script src="jquery/jquery.validate.js"></script>
<script src="bootstrap/js/bootstrap.js"></script>


<!-- This is the package configuration utilities -->
<%
importScripts.each { importScript ->
    %><script src="../${importScript}"></script>
<%
}%>