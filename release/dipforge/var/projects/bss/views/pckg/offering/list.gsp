<!--
Date: Wed Jun 27 05:43:54 SAST 2012
File: list.gsp
Author: brett chaldecott
-->

    <div class="span9">
      <ul class="thumbnails" id="offeringThumbnails">
        <%
        params.offerings.each { offerings ->
            def offering = offerings[0]
            %>
            <li class="span3" id="offeringThumbnailEntry${offering.getId()}">
                <div class="thumbnail" rel="popover" 
                    data-content="ID: ${offering.getId()}<br/>Name: ${offering.getName()}<br/>Description: ${offering.getDescription()}<br/>Thumbnail: ${offering.getThumbnail()}<br/>Icon: ${offering.getIcon()}<br/>"
                    data-original-title="${offering.getName()}" id="hoveroverimage${offering.getId()}">
                    <img src="${params.contextBase}${offering.getThumbnail()}" alt="${offering.getDescription()}">
                    <div class="caption">
                        <h5><img src="${params.contextBase}${offering.getIcon()}" style="height:16px;width:16px;"/> ${offering.getName()}</h5>
                        <p>${offering.getDescription()}</p>
                        <p><a href="javascript:removeOffering('${offering.getId()}');" class="btn btn-primary">Remove</a> <a href="javascript:updateOffering('${offering.getId()}');" class="btn">Update</a></p>
                        <form id="existingOfferingForm${offering.getId()}" name="existingOfferingForm${offering.getId()}">
                            <input type="hidden" name="existingOfferingId${offering.getId()}" id="existingOfferingId${offering.getId()}" value="${offering.getId()}" />
                            <input type="hidden" name="existingOfferingName${offering.getId()}" id="existingOfferingName${offering.getId()}" value="${offering.getName()}" />
                            <input type="hidden" name="existingOfferingDescription${offering.getId()}" id="existingOfferingDescription${offering.getId()}" value="${offering.getDescription()}" />
                            <input type="hidden" name="existingOfferingThumbnail${offering.getId()}" id="existingOfferingThumbnail${offering.getId()}" value="${offering.getThumbnail()}" />
                            <input type="hidden" name="existingOfferingIcon${offering.getId()}" id="existingOfferingIcon${offering.getId()}" value="${offering.getThumbnail()}" />
                            <input type="hidden" name="existingofferingPackage${offering.getId()}" id="existingofferingPackage${offering.getId()}" value="${offering.getPckg().getId()}" />
                            <input type="hidden" name="existingofferingCatalog${offering.getId()}" id="existingofferingCatalog${offering.getId()}" value="${offering.getCatalog()?.getId()}" />
                            <input type="hidden" name="existingCreated${offering.getId()}" id="existingCreated${offering.getId()}" value="${offering.getCreated()}" />
                            <%
                            def costs = ""
                            def sep = ""
                            offering.getCosts()?.each { cost ->
                                if (cost.getType() != null && cost.getType() != "") {
                                    costs += sep + cost.getId() + ",," + cost.getLineItem() + ",," + cost.getType() + ",," + cost.getAmount()
                                    sep = "||"
                                }
                            }
                            %>
                            <input type="hidden" name="existingOfferingCosts${offering.getId()}" id="existingOfferingCosts${offering.getId()}" value="${costs}" />
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
        <h3>Add Offering</h3>
    </div>
    <div id="updateOfferingTitle" style="display:none;">
        <h3>Update Offering</h3>
    </div>
  </div>
  <div class="modal-body">
    <div id="modelForm">
        <form class="form-horizontal" id="offeringForm">
          <fieldset>
            <div class="control-group">
              <label class="control-label" for="offeringId">ID</label>
              <div class="controls">
                <input type="text" class="input-large" id="offeringId" name="offeringId">
                <p class="help-block">The id of the new offering</p>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label" for="offeringName">Name</label>
              <div class="controls">
                <input type="text" class="input-large" id="offeringName" name="offeringName">
                <p class="help-block">The id of the offering.</p>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label" for="offeringDescription">Description</label>
              <div class="controls">
                <input type="text" class="input-large" id="offeringDescription" name="offeringDescription">
                <p class="help-block">The id of the offering.</p>
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
                <label class="control-label" for="offeringPackage">Package</label>
                <div class="controls">
                <select class="input-large" id="offeringPackage" name="offeringPackage">
                    <%
                    params.pckgs.each { pckgs ->
                        def pckg = pckgs[0]
                        %>
                        <option value="${pckg.getId()}">${pckg.getName()}</option>
                        <%
                    }
                    %>
                </select>
                <p class="help-block">The offering package.</p>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="offeringCatalog">Catalog</label>
                <div class="controls">
                <select class="input-large" id="offeringCatalog" name="offeringCatalog">
                    <%
                    params.catalogEntries.each { entry ->
                        walkCatalog(entry.getName(), entry);
                    }
                    %>
                </select>
                <p class="help-block">The offering package.</p>
                </div>
            </div>
            <table class="table">
                <thead>
                  <tr>
                    <th></th>
                    <th>Line Item</th>
                    <th>Type</th>
                    <th>cents</th>
                  </tr>
                </thead>
                <tbody id="costEntry">
                  <tr>
                    <td><i class="icon-minus-sign"></i></td>
                    <td><input type="text" style="width:100%;" id="costLineItem" name="costLineItem"></td>
                    <td>
                        <select style="width:100%;" id="costType" name="costType">
                            <option value="setup">Setup</option>
                            <option value="monthly">Monthly</option>
                            <option value="quartly">Quartly</option>
                            <option value="annually">Annually</option>
                        </select>
                    </td>
                    <td><input type="text" class="input-small" id="costValue" name="costValue"></td>
                  </tr>
                  <tr>
                    <td><i class="icon-plus-sign"></i></td>
                    <td><input type="text" style="width:100%;" id="costLineItem" name="costLineItem"></td>
                    <td>
                        <select style="width:100%;" id="costType" name="costType">
                            <option value="setup">Setup</option>
                            <option value="monthly">Monthly</option>
                            <option value="quartly">Quartly</option>
                            <option value="annually">Annually</option>
                        </select>
                    </td>
                    <td><input type="text" class="input-small" id="costValue" name="costValue"></td>
                  </tr>
                </tbody>
            </table>
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
    <a href="#" class="btn" data-dismiss="modal" id="offeringCloseButton">Close</a>
    <a href="#" class="btn btn-primary" id="addOfferingItem">Add Offering</a>
    <a href="#" class="btn btn-primary" id="updateOfferingItem" style="display:none;">Update Offering</a>
  </div>
</div>


<div class="modal hide" id="removeModal">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>Remove Offering</h3>
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
        <a href="#" class="btn" data-dismiss="modal" id="removeOfferingCloseButton">Close</a>
        <a href="#" class="btn btn-primary" id="removeOfferingItem">Remove Offering</a>
    </div>
</div>

<%
def walkCatalog(def path, def entry) {
    // TODO: this is very lazy. It is done so that when a catalog entry is deleted
    // a black node does not appear in the list. Should perform a search during deletion
    // and updated the referencing entries.
    if (entry.getName() == null || entry.getName() == "") {
        return
    }
    %>
    <option value="${entry.getId()}">${path}</option>
    <%
    entry.getChildren()?.each { child ->
        walkCatalog(path + "/" + child.getName(), child)
    }
}
%>    
    
