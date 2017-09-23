<!--
Date: Sun Jul 12 09:49:21 SAST 2015
File: list.gsp
Author: admin
-->

<div id="categoriesSetupMsg" class="span9">

<%
if (params.categories.size() == 0) {
    %>
        <div id="categoriesSetupMsg" class="span8" style="display:none;">
            <div class="alert fade in alert-info">
                The categories have been setup
            </div>
        </div>
        <div id="categoriesNotSetupMsg" class="span8">
            <div class="alert fade in alert-error">
                The categories have not been setup
            </div>
        </div>
    <%
} else {
    %>
        <div id="categoriesSetupMsg" class="span8">
            <div class="alert fade in alert-info">
                The categories have been setup
            </div>
        </div>
        <div id="categoriesNotSetupMsg" class="span8" style="display:none;">
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
        <div id="vendorsSetupMsg" class="span8" style="display:none;">
            <div class="alert fade in alert-info">
                The vendors have been setup
            </div>
        </div>
        <div id="vendorsNotSetupMsg" class="span8">
            <div class="alert fade in alert-error">
                The vendors have not been setup
            </div>
        </div>
    <%
} else {
    %>
        <div id="vendorsSetupMsg" class="span8">
            <div class="alert fade in alert-info">
                The vendors have been setup
            </div>
        </div>
        <div id="vendorsNotSetupMsg" class="span8" style="display:none;">
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
        <div id="productsSetupMsg" class="span8" style="display:none;">
            <div class="alert fade in alert-info">
                The products have been setup
            </div>
        </div>
        <div id="productsNotSetupMsg" class="span8">
            <div class="alert fade in alert-error">
                The products have not been setup
            </div>
        </div>
    <%
} else {
    %>
        <div id="productsSetupMsg" class="span8">
            <div class="alert fade in alert-info">
                The products have been setup
            </div>
        </div>
        <div id="productsNotSetupMsg" class="span8" style="display:none;">
            <div class="alert fade in alert-error">
                The products not have been setup
            </div>
        </div>
    <%
}
%>

<%
if (params.packages.size() == 0) {
    %>
        <div id="packagesSetupMsg" class="span8" style="display:none;">
            <div class="alert fade in alert-info">
                The packages have been setup
            </div>
        </div>
        <div id="packagesNotSetupMsg" class="span8">
            <div class="alert fade in alert-error">
                The packages have not been setup
            </div>
        </div>
    <%
} else {
    %>
        <div id="packagesSetupMsg" class="span8">
            <div class="alert fade in alert-info">
                The packages have been setup
            </div>
        </div>
        <div id="packagesNotSetupMsg" class="span8" style="display:none;">
            <div class="alert fade in alert-error">
                The packages not have been setup
            </div>
        </div>
    <%
}
%>

<%
if (params?.catalog == null || params?.catalog.getEntries() == null) {
    %>
        <div id="catalogSetupMsg" class="span8" style="display:none;">
            <div class="alert fade in alert-info">
                The catalog has been setup
            </div>
        </div>
        <div id="catalogNotSetupMsg" class="span8">
            <div class="alert fade in alert-error">
                The catalog has not been setup
            </div>
        </div>
    <%
} else {
    %>
        <div id="catalogSetupMsg" class="span8">
            <div class="alert fade in alert-info">
                The catalog has been setup
            </div>
        </div>
        <div id="productsNotSetupMsg" class="span8" style="display:none;">
            <div class="alert fade in alert-error">
                The catalog has not been setup
            </div>
        </div>
    <%
}
%>

<%
if (params.offerings.size() == 0) {
    %>
        <div id="offeringsSetupMsg" class="span8" style="display:none;">
            <div class="alert fade in alert-info">
                The offerings have been setup
            </div>
        </div>
        <div id="offeringsNotSetupMsg" class="span8">
            <div class="alert fade in alert-error">
                The offerings have not been setup
            </div>
        </div>
    <%
} else {
    %>
        <div id="offeringsSetupMsg" class="span8">
            <div class="alert fade in alert-info">
                The offerings have been setup
            </div>
        </div>
        <div id="offeringsNotSetupMsg" class="span8" style="display:none;">
            <div class="alert fade in alert-error">
                The offerings not have been setup
            </div>
        </div>
    <%
}
%>

</div>

<div class="modal hide" id="setupModal">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>Setup</h3>
    </div>
    <div class="modal-body">
        <div id="setupModelMsg">
            <p id="setupModelText">
                Do you wish to setup the default portfolio. This contains all the basic packages for administration
            </p>
        </div>
        <div id="setupModelSuccessMsg" style="display:none;">
            <div class="alert fade in alert-info">
                <span id="setupModelSuccessResultMsg">
                    A request to setup the default portfolio was submitted. Will finish synchronizing shortly.
                </span>
            </div>
        </div>
        <div id="setupModelErrorMsg" style="display:none;">
            <div class="alert fade in alert-error">
                <span id="setupModelErrorResultMsg">
                    Failed to submit a request setup the default portfolio configuration
                </span>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <a href="#" class="btn" data-dismiss="modal" id="setupCloseButton">Close</a>
        <a href="#" class="btn btn-primary" id="setupSubmit">Setup</a>
    </div>
</div>


