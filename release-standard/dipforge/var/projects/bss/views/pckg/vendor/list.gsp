<!--
Date: Tue Jul 10 20:44:36 SAST 2012
File: list.gsp
Author: brett chaldecott
-->


    <div class="span9">
      <ul class="thumbnails" id="vendorThumbnails">
        <%
        params.vendors.each { vendors ->
            def vendor = vendors[0]
            %>
            <li class="span3" id="vendorThumbnailEntry${vendor.getId()}">
                <div class="thumbnail" rel="popover" 
                    data-content="ID: ${vendor.getId()}<br/>Name: ${vendor.getName()}<br/>Description: ${vendor.getDescription()}<br/>Thumbnail: ${vendor.getThumbnail()}<br/>Icon: ${vendor.getIcon()}<br/>"
                    data-original-title="${vendor.getName()}" id="hoveroverimage${vendor.getId()}">
                    <img src="${params.contextBase}${vendor.getThumbnail()}" alt="${vendor.getDescription()}">
                    <div class="caption">
                        <h5><img src="${params.contextBase}${vendor.getIcon()}" style="height:16px;width:16px;"/> ${vendor.getName()}</h5>
                        <p>${vendor.getDescription()}</p>
                        <p><a href="javascript:removeVendor('${vendor.getId()}');" class="btn btn-primary">Remove</a> <a href="javascript:updateVendor('${vendor.getId()}');" class="btn">Update</a></p>
                        <form id="existingVendorForm${vendor.getId()}" name="existingVendorForm${vendor.getId()}">
                            <input type="hidden" name="existingVendorId${vendor.getId()}" id="existingVendorId${vendor.getId()}" value="${vendor.getId()}" />
                            <input type="hidden" name="existingVendorName${vendor.getId()}" id="existingVendorName${vendor.getId()}" value="${vendor.getName()}" />
                            <input type="hidden" name="existingVendorDescription${vendor.getId()}" id="existingVendorDescription${vendor.getId()}" value="${vendor.getDescription()}" />
                            <input type="hidden" name="existingVendorThumbnail${vendor.getId()}" id="existingVendorThumbnail${vendor.getId()}" value="${vendor.getThumbnail()}" />
                            <input type="hidden" name="existingVendorIcon${vendor.getId()}" id="existingVendorIcon${vendor.getId()}" value="${vendor.getIcon()}" />
                        </form>
                    </div>
                </div>
            </li>
            
        
        <% } %>
      </ul><!--/row-->
    </div><!--/span-->
    
<div class="modal hide" id="vendorModal">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <div id="addVendorTitle">
        <h3>Add Vendor</h3>
    </div>
    <div id="updateVendorTitle" style="display:none;">
        <h3>Update Vendor</h3>
    </div>
  </div>
  <div class="modal-body">
    <div id="modelForm">
        <form class="form-horizontal" id="vendorForm">
          <fieldset>
            <div class="control-group">
              <label class="control-label" for="vendorId">Vendor ID</label>
              <div class="controls">
                <input type="text" class="input-large" id="vendorId" name="vendorId">
                <p class="help-block">The id of the new vendor</p>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label" for="vendorName">Vendor name</label>
              <div class="controls">
                <input type="text" class="input-large" id="vendorName" name="vendorName">
                <p class="help-block">The id of the vendor.</p>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label" for="vendorDescription">Vendor description</label>
              <div class="controls">
                <input type="text" class="input-large" id="vendorDescription" name="vendorDescription">
                <p class="help-block">The id of the vendor.</p>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label" for="thumbnail">Thumbnail</label>
              <div class="controls">
                <input type="text" class="input-large" id="thumbnail" name="thumbnail">
                <p class="help-block">Thumbnail URL or server path.</p>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label" for="icon">Icon</label>
              <div class="controls">
                <input type="text" class="input-large" id="icon" name="icon">
                <p class="help-block">Icon URL or server path.</p>
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
    <a href="#" class="btn" data-dismiss="modal" id="vendorCloseButton">Close</a>
    <a href="#" class="btn btn-primary" id="addVendorItem">Add Vendor</a>
    <a href="#" class="btn btn-primary" id="updateVendorItem" style="display:none;">Update Vendor</a>
  </div>
</div>


<div class="modal hide" id="removeModal">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>Remove Vendor</h3>
    </div>
    <div class="modal-body">
        <div id="removeModelMsg">
            <p id="removeModelText"></p>
            <form name="removeModalForm" id="removeModalForm">
                <input type="hidden" name="removeModalFormId" id="removeModalFormId"/>
            </form>
        </div>
        <div id="removeModelSuccessMsg">
            <div class="alert fade in alert-info">
                <span id="removeModelSuccessResultMsg"></span>
            </div>
        </div>
        <div id="removeModelErrorMsg">
            <div class="alert fade in alert-info">
                <span id="removeModelErrorResultMsg"></span>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <a href="#" class="btn" data-dismiss="modal" id="removeVendorCloseButton">Close</a>
        <a href="#" class="btn btn-primary" id="removeVendorItem">Remove Vendor</a>
    </div>
</div>