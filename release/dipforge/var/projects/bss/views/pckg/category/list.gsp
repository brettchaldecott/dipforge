<!--
Date: Wed Jun 28 05:43:54 SAST 2012
File: list.gsp
Author: brett chaldecott
-->

    <div class="span9">
      <ul class="thumbnails" id="categoryThumbnails">
        <%
        params.categories.each { categories ->
            def category = categories[0]
            %>
            <li class="span3" id="categoryThumbnailEntry${category.getId()}">
                <div class="thumbnail" rel="popover" 
                    data-content="ID: ${category.getId()}<br/>Name: ${category.getName()}<br/>Description: ${category.getDescription()}<br/>Thumbnail: ${category.getThumbnail()}<br/>Icon: ${category.getIcon()}<br/>"
                    data-original-title="${category.getName()}" id="hoveroverimage${category.getId()}">
                    <img src="${params.contextBase}${category.getThumbnail()}" alt="${category.getDescription()}">
                    <div class="caption">
                        <h5><img src="${params.contextBase}${category.getIcon()}" style="height:16px;width:16px;"/> ${category.getName()}</h5>
                        <!--
                        <div class="span3" style="margin-left:0px;">
                            <div style="float: left;">
                            </div>
                            <div style="width:83%; margin-left: 20px;">
                                <div class="progress progress-success" style="margin-bottom: 9px;">
                                    <div class="bar" style="width: 100%"></div>
                                </div>
                            </div>
                        </div>
                        -->
                        <p>${category.getDescription()}</p>
                        <p><a href="javascript:removeCategory('${category.getId()}');" class="btn btn-primary">Remove</a> <a href="javascript:updateCategory('${category.getId()}');" class="btn">Update</a></p>
                        <form id="existingCategoryForm${category.getId()}" name="existingCategoryForm${category.getId()}">
                            <input type="hidden" name="existingCategoryId${category.getId()}" id="existingCategoryId${category.getId()}" value="${category.getId()}" />
                            <input type="hidden" name="existingCategoryName${category.getId()}" id="existingCategoryName${category.getId()}" value="${category.getName()}" />
                            <input type="hidden" name="existingCategoryDescription${category.getId()}" id="existingCategoryDescription${category.getId()}" value="${category.getDescription()}" />
                            <input type="hidden" name="existingCategoryThumbnail${category.getId()}" id="existingCategoryThumbnail${category.getId()}" value="${category.getThumbnail()}" />
                            <input type="hidden" name="existingCategoryIcon${category.getId()}" id="existingCategoryIcon${category.getId()}" value="${category.getIcon()}" />
                        </form>
                    </div>
                </div>
            </li>
            
        
        <% } %>
      </ul><!--/row-->
    </div><!--/span-->
    
<div class="modal hide" id="categoryModal">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <div id="addCategoryTitle">
        <h3>Add Category</h3>
    </div>
    <div id="updateCategoryTitle" style="display:none;">
        <h3>Update Category</h3>
    </div>
  </div>
  <div class="modal-body">
    <div id="modelForm">
        <form class="form-horizontal" id="categoryForm">
          <fieldset>
            <div class="control-group">
              <label class="control-label" for="categoryId">Category ID</label>
              <div class="controls">
                <input type="text" class="input-large" id="categoryId" name="categoryId">
                <p class="help-block">The id of the new category</p>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label" for="categoryName">Category name</label>
              <div class="controls">
                <input type="text" class="input-large" id="categoryName" name="categoryName">
                <p class="help-block">The id of the category.</p>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label" for="categoryDescription">Category description</label>
              <div class="controls">
                <input type="text" class="input-large" id="categoryDescription" name="categoryDescription">
                <p class="help-block">The id of the category.</p>
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
    <a href="#" class="btn" data-dismiss="modal" id="categoryCloseButton">Close</a>
    <a href="#" class="btn btn-primary" id="addCategoryItem">Add Category</a>
    <a href="#" class="btn btn-primary" id="updateCategoryItem" style="display:none;">Update Category</a>
  </div>
</div>


<div class="modal hide" id="removeModal">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>Remove Category</h3>
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
        <a href="#" class="btn" data-dismiss="modal" id="removeCategoryCloseButton">Close</a>
        <a href="#" class="btn btn-primary" id="removeCategoryItem">Remove Category</a>
    </div>
</div>