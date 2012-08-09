<!--
Date: Mon Aug 06 06:20:34 SAST 2012
File: list.gsp
Author: brett chaldecott
-->

<!--

-->

<div class="span9">
    <ul class="breadcrumb" id="catalog">
    <div>
      <li>
        <a href="javascript:addEntry(['Home'],'home');"><i class="icon-plus"></i></a><i class="icon-minus"></i> Home <span class="divider">/</span>
      </li>
    </div>
<%
if (params.catalog != null) {
    params.catalog.getEntries()?.each { entry ->
        buildCatalog(["Home"],entry)
    }
}
%>
    </ul>
</div>

<%
def buildCatalog(def path, def entry) {
    def jsonArray = "["
    def sep = ""
    path.each { pathEntry ->
        jsonArray += sep + "'" + pathEntry + "'";
        sep = ",";
    }
    
    jsonArray += sep + "'" + entry.getName() + "']"
    %>
    <div id="catId${entry.getId()}">
      <li>
         <a href="javascript:addEntry(${jsonArray},'${entry.getId()}');"><i class="icon-plus"></i></a><a href="javascript:removeEntry(${jsonArray},'${entry.getId()}');"><i class="icon-minus"></i></a> <% 
    path.each { val ->
         %> ${val} <span class="divider">/</span> <%
    } %> ${entry.getName()} </li>
    </div>
    <%
    def subPath = []
    subPath.addAll(path);
    subPath.add(entry.getName())
    entry.getChildren()?.each { child ->
        buildCatalog(subPath,child)
    }
}
%>

<div class="modal hide" id="catalogModal">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <div id="addCatalogTitle">
        <h3>Add Catalog</h3>
    </div>
    <div id="updateCatalogTitle" style="display:none;">
        <h3>Update Catalog</h3>
    </div>
  </div>
  <div class="modal-body">
    <div id="modelForm">
        <ul class="breadcrumb" id="catalogModalPath">
        </ul>
        <form class="form-horizontal" id="catalogForm">
        <input type="hidden" id="parentId" name="parentId">
          <fieldset>
            <div class="control-group">
              <label class="control-label" for="catalogEntryId">Catalog ID</label>
              <div class="controls">
                <input type="text" class="input-large" id="catalogEntryId" name="catalogEntryId">
                <p class="help-block">The id of the new catalog</p>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label" for="catalogEntryName">Catalog name</label>
              <div class="controls">
                <input type="text" class="input-large" id="catalogEntryName" name="catalogEntryName">
                <p class="help-block">The name of the catalog.</p>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label" for="catalogEntryDescription">Catalog description</label>
              <div class="controls">
                <input type="text" class="input-large" id="catalogEntryDescription" name="catalogEntryDescription">
                <p class="help-block">The description of the catalog.</p>
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
    <a href="#" class="btn" data-dismiss="modal" id="catalogCloseButton">Close</a>
    <a href="#" class="btn btn-primary" id="addCatalogItem">Add Catalog</a>
    <a href="#" class="btn btn-primary" id="updateCatalogItem" style="display:none;">Update Catalog</a>
  </div>
</div>


<div class="modal hide" id="removeModal">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>Remove Catalog</h3>
    </div>
    <div class="modal-body">
        <div id="removeModelMsg">
            <ul class="breadcrumb" id="catalogRemoveModalPath">
                <li>
                    Catalog <span class="divider">/</span>
                </li>
            </ul>
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
        <a href="#" class="btn" data-dismiss="modal" id="removeCatalogCloseButton">Close</a>
        <a href="#" class="btn btn-primary" id="removeCatalogItem">Remove Catalog</a>
    </div>
</div>
