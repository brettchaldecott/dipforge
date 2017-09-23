<!--
Date: Fri Dec 28 08:51:36 SAST 2012
File: shopping-list.gsp
Author: brett chaldecott
-->

<% import java.text.DecimalFormat %>
<% import groovy.json.* %>

<div class="span9">
    <div class="hero-unit">
    <legend>Shopping List</legend>
        <table class="table table-striped">
        <thead>
          <tr>
            <th>&nbsp;</th>
            <th>Item</th>
            <th>Amount</th>
          </tr>
        </thead>
        <tbody id="offerings">
        <%
        def importScripts = []
        def total = 0
        def format = new DecimalFormat("#,##0.00")
        for (element in params?.cartInfo) {
            def builder = new JsonBuilder()
            def offering = element.value.offering
            element.value.remove("offering")
            builder(element.value)
            def id = element.key
            def setupCost = 0.0
            def monthlyCost = 0.0
            offering.getCosts()?.each { cost ->
                if (cost.getType() != null && cost.getType() != "") {
                    if (cost.getType() == "setup") {
                        setupCost += cost.getAmount()
                    } else if (cost.getType() == "monthly") {
                        monthlyCost += cost.getAmount()
                    }
                }
            }
            total += setupCost
            setupCost = setupCost / 100
            monthlyCost = monthlyCost / 100
            %>
          <tr id="tr${id}">
            <form id="offering${id}">
                <input type="hidden" name="offeringInfo${id}" id="offeringInfo${id}" value='${builder.toString()}'/>
                <td><a href="javascript:removeOffering('${id}');"><i class="icon-minus-sign"></i></a></td>
                <td><a href="javascript:updateOffering('${id}');">${offering.getName()}</a></td>
                <td>${format.format(setupCost)}</td>
                <input type="hidden" name="existingOfferingInstallId${id}" id="existingOfferingInstallId${id}" value="${id}" />
                <input type="hidden" name="existingOfferingId${id}" id="existingOfferingId${id}" value="${offering.getId()}" />
                <input type="hidden" name="existingOfferingName${id}" id="existingOfferingName${id}" value="${offering.getName()}" />
                <input type="hidden" name="existingOfferingDescription${id}" id="existingOfferingDescription${id}" value="${offering.getDescription()}" />
                <input type="hidden" name="existingOfferingThumbnail${id}" id="existingOfferingThumbnail${id}" value="${params.contextBase}${offering.getThumbnail()}" />
                <input type="hidden" name="existingOfferingSetup${id}" id="existingOfferingSetup${id}" value="${format.format(setupCost)}" />
                <input type="hidden" name="existingOfferingMonthly${id}" id="existingOfferingMonthly${id}" value="${format.format(monthlyCost)}" />
                <%
                def products = ""
                def packageSep = ""
                offering.getPckg().getProducts().each { productConfig ->
                    productConfig.getProduct().getConfigurationManager().each { config ->
                        if (config.getName() == "Install") {
                            if (!importScripts.contains("bss/" + config.getUrl())) {
                                importScripts.add("bss/" + config.getUrl())
                            }
                        }
                    }
                    def productId = productConfig.getProduct().getId();
                    products += packageSep + productId;
                    packageSep = ","
                    %>
                    <input type="hidden" name="existingOfferingData${id}${productId}" id="existingOfferingData${id}${productId}" value="${productConfig.getData()}" />
                    <%
                }
                %>
                <input type="hidden" name="existingOfferingProducts${id}" id="existingOfferingProducts${id}" value="${products}" />
            </form>
          </tr>
        <% } 
        total = total / 100
        %>
        <tr id="total" class="success">
            <td>&nbsp;</td>
            <td><b>Total</b></td>
            <td><b>${format.format(total)}</b></td>
          </tr>
        </tbody>
      </table>
    <a href="shopping/BuyBasket.groovy" class="btn btn-primary" id="buyOffering"><i class="icon-shopping-cart"></i>Buy</a>
    </div>
</div>

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
    <a href="#" class="btn btn-primary" id="updateOffering"><i class="icon-shopping-cart"></i>Update</a>
  </div>
</div>


<div class="modal hide" id="msgModal">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <div id="addOfferingTitle">
        <h3>Removed</h3>
    </div>
  </div>
  <div class="modal-body">
    <div id="msgModelSuccessResult" style="display:none;">
        <div class="alert fade in alert-info">
            <span id="msgModelSuccessResultMsg"></span>
        </div>
    </div>
    <div id="msgModelRuntimeErrorResult" style="display:none;">
        <div class="alert fade in alert-error">
            <span id="msgModelRuntimeErrorResultMsg"></span>
        </div>
    </div>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn" data-dismiss="modal" id="purchaseCloseButton">Close</a>
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