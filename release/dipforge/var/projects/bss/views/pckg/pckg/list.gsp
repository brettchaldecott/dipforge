<!--
Date: Wed Jun 27 05:43:54 SAST 2012
File: list.gsp
Author: brett chaldecott
-->

    <div class="span9">
      <ul class="thumbnails" id="pckgThumbnails">
        <%
        params.pckgs.each { pckgs ->
            def pckg = pckgs[0]
            %>
            <li class="span3" id="pckgThumbnailEntry${pckg.getId()}">
                <div class="thumbnail" rel="popover" 
                    data-content="ID: ${pckg.getId()}<br/>Name: ${pckg.getName()}<br/>Description: ${pckg.getDescription()}<br/>Thumbnail: ${pckg.getThumbnail()}<br/>Icon: ${pckg.getIcon()}<br/>"
                    data-original-title="${pckg.getName()}" id="hoveroverimage${pckg.getId()}">
                    <img src="${params.contextBase}${pckg.getThumbnail()}" alt="${pckg.getDescription()}">
                    <div class="caption">
                        <h5><img src="${params.contextBase}${pckg.getIcon()}" style="height:16px;width:16px;"/> ${pckg.getName()}</h5>
                        <p>${pckg.getDescription()}</p>
                        <p><a href="javascript:removePckg('${pckg.getId()}');" class="btn btn-primary">Remove</a> <a href="javascript:updatePckg('${pckg.getId()}');" class="btn">Update</a></p>
                        <form id="existingPckgForm${pckg.getId()}" name="existingPckgForm${pckg.getId()}">
                            <input type="hidden" name="existingPckgId${pckg.getId()}" id="existingPckgId${pckg.getId()}" value="${pckg.getId()}" />
                            <input type="hidden" name="existingPckgName${pckg.getId()}" id="existingPckgName${pckg.getId()}" value="${pckg.getName()}" />
                            <input type="hidden" name="existingPckgDescription${pckg.getId()}" id="existingPckgDescription${pckg.getId()}" value="${pckg.getDescription()}" />
                            <input type="hidden" name="existingPckgThumbnail${pckg.getId()}" id="existingPckgThumbnail${pckg.getId()}" value="${pckg.getThumbnail()}" />
                            <input type="hidden" name="existingPckgIcon${pckg.getId()}" id="existingPckgIcon${pckg.getId()}" value="${pckg.getThumbnail()}" />
                            <input type="hidden" name="existingPckgTarget${pckg.getId()}" id="existingPckgTarget${pckg.getId()}" value="${pckg.getTarget()?.getId()}" />
                            <input type="hidden" name="existingPckgPckgTarget${pckg.getId()}" id="existingPckgPckgTarget${pckg.getId()}" value="${pckg.getPckgTarget()?.getId()}" />
                            <%
                            def products = ""
                            def sep = ""
                            pckg.getProducts()?.each {product ->
                                products += sep + product.getId();
                                sep = ","
                            }
                            %>
                            <input type="hidden" name="existingPckgProducts${pckg.getId()}" id="existingPckgProducts${pckg.getId()}" value="${products}" />
                            
                        </form>
                    </div>
                </div>
            </li>
            
        
        <% } %>
      </ul><!--/row-->
    </div><!--/span-->
    
<div class="modal hide" id="pckgModal">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <div id="addPckgTitle">
        <h3>Add Package</h3>
    </div>
    <div id="updatePckgTitle" style="display:none;">
        <h3>Update Package</h3>
    </div>
  </div>
  <div class="modal-body">
    <div id="modelForm">
        <form class="form-horizontal" id="pckgForm">
        <fieldset>
            <div class="control-group">
              <label class="control-label" for="pckgId">Package ID</label>
              <div class="controls">
                <input type="text" class="input-large" id="pckgId" name="pckgId">
                <p class="help-block">The id of the new package</p>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label" for="pckgName">Package name</label>
              <div class="controls">
                <input type="text" class="input-large" id="pckgName" name="pckgName">
                <p class="help-block">The id of the package.</p>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label" for="pckgDescription">Package description</label>
              <div class="controls">
                <input type="text" class="input-large" id="pckgDescription" name="pckgDescription">
                <p class="help-block">The id of the package.</p>
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
            <div class="control-group">
              <label class="control-label" for="pckgTarget">Target</label>
              <div class="controls">
                <select class="input-large" id="pckgTarget" name="pckgTarget">
                    <%
                    params.products.each { products ->
                        def product = products[0]
                        %>
                        <option value="${product.getId()}">${product.getName()}</option>
                        <%
                    }
                    %>
                </select>
                <p class="help-block">The product target.</p>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label" for="pckgPckgTarget">Package Target</label>
              <div class="controls">
                <select class="input-large" id="pckgPckgTarget" name="pckgPckgTarget">
                    <%
                    params.pckgs.each { pckgs ->
                        def pckg = pckgs[0]
                        %>
                        <option value="${pckg.getId()}">${pckg.getName()}</option>
                        <%
                    }
                    %>
                </select>
                <p class="help-block">The package target.</p>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label" for="pckgProducts">Products</label>
              <div class="controls">
                <select class="input-large" id="pckgProducts" name="pckgProducts" multiple="multiple">
                    <%
                    params.products.each { products ->
                        def product = products[0]
                        %>
                        <option value="${product.getId()}">${product.getName()}</option>
                        <%
                    }
                    %>
                </select>
                <p class="help-block">The products.</p>
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
    <a href="#" class="btn" data-dismiss="modal" id="pckgCloseButton">Close</a>
    <a href="#" class="btn btn-primary" id="addPckgItem">Add Package</a>
    <a href="#" class="btn btn-primary" id="updatePckgItem" style="display:none;">Update Package</a>
  </div>
</div>


<div class="modal hide" id="removeModal">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>Remove Package</h3>
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
        <a href="#" class="btn" data-dismiss="modal" id="removePckgCloseButton">Close</a>
        <a href="#" class="btn btn-primary" id="removePckgItem">Remove Package</a>
    </div>
</div>

    
    
