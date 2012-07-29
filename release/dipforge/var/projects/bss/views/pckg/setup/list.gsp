<!--
Date: Wed Jul 25 06:16:41 SAST 2012
File: list.gsp
Author: admin
-->

<%
if (params.categories.size() == 0) {
    %>
        <div id="categoriesSetupMsg" class="span9" style="display:none;">
            <div class="alert fade in alert-info">
                The categories have been setup
            </div>
        </div>
        <div id="categoriesNotSetupMsg" class="span9">
            <div class="alert fade in alert-error">
                The categories have not been setup
            </div>
        </div>
    <%
} else {
    %>
        <div id="categoriesSetupMsg" class="span9">
            <div class="alert fade in alert-info">
                The categories have been setup
            </div>
        </div>
        <div id="categoriesNotSetupMsg" class="span9" style="display:none;">
            <div class="alert fade in alert-error">
                The categories not have been setup
            </div>
        </div>
    <%
}
%>

<%
if (params.vendors.size() == 0) {
    %>
        <div id="vendorsSetupMsg" class="span9" style="display:none;">
            <div class="alert fade in alert-info">
                The vendors have been setup
            </div>
        </div>
        <div id="vendorsNotSetupMsg" class="span9">
            <div class="alert fade in alert-error">
                The vendors have not been setup
            </div>
        </div>
    <%
} else {
    %>
        <div id="vendorsSetupMsg" class="span9">
            <div class="alert fade in alert-info">
                The vendors have been setup
            </div>
        </div>
        <div id="vendorsNotSetupMsg" class="span9" style="display:none;">
            <div class="alert fade in alert-error">
                The vendors not have been setup
            </div>
        </div>
    <%
}
%>

<%
if (params.products.size() == 0) {
    %>
        <div id="productsSetupMsg" class="span9" style="display:none;">
            <div class="alert fade in alert-info">
                The products have been setup
            </div>
        </div>
        <div id="productsNotSetupMsg" class="span9">
            <div class="alert fade in alert-error">
                The products have not been setup
            </div>
        </div>
    <%
} else {
    %>
        <div id="productsSetupMsg" class="span9">
            <div class="alert fade in alert-info">
                The products have been setup
            </div>
        </div>
        <div id="productsNotSetupMsg" class="span9" style="display:none;">
            <div class="alert fade in alert-error">
                The products not have been setup
            </div>
        </div>
    <%
}
%>

<div class="modal hide" id="setupModal">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>Setup</h3>
    </div>
    <div class="modal-body">
        <div id="setupModelMsg">
            <p id="setupModelText">
                Do you wish to setup the default ISP [Categories][Vendors][Products]
            </p>
        </div>
        <div id="setupModelSuccessMsg" style="display:none;">
            <div class="alert fade in alert-info">
                <span id="setupModelSuccessResultMsg">
                    A request to setup the default ISP [Categories][Vendors][Products] was submitted
                </span>
            </div>
        </div>
        <div id="setupModelErrorMsg" style="display:none;">
            <div class="alert fade in alert-info">
                <span id="setupModelErrorResultMsg">
                    Failed to submit a request setup the default ISP [Categories][Vendors][Products] configuration
                </span>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <a href="#" class="btn" data-dismiss="modal" id="setupCloseButton">Close</a>
        <a href="#" class="btn btn-primary" id="setupSubmit">Setup</a>
    </div>
</div>