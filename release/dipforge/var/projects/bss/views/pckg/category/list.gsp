<!--
Date: Wed Jun 28 05:43:54 SAST 2012
File: list.gsp
Author: brett chaldecott
-->

    <div class="span9">
      <ul class="thumbnails">
        <%
        params.categories.each { categories ->
            def category = categories[0]
            %>
            <li class="span3">
                <div class="thumbnail" rel="popover" data-content="And here's some amazing content. It's very engaging. right?" data-original-title="test" id="hoveroverbuttona">
                    <img src="http://placehold.it/260x180" alt="">
                    <div class="caption">
                        <h5>Thumbnail label</h5>
                        <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
                        <p><a href="#" class="btn btn-primary">Uninstall</a> <a href="#" class="btn">Info</a></p>
                    </div>
                </div>
            </li>
            <script type="text/javascript">
            \${document}.ready(function() {
                \$('#hoveroverbutton').popover({});
                \$('#hoveroverbuttona').popover({});
            });
            </script>
                    
        <% } %>

        <!--
        <li class="span3">
            <div class="thumbnail" rel="popover" data-content="And here's some amazing content. It's very engaging. right?" data-original-title="test" id="hoveroverbuttona">
                <img src="http://placehold.it/260x180" alt="">
                <div class="caption">
                    <h5>Thumbnail label</h5>
                    <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
                    <p><a href="#" class="btn btn-primary">Uninstall</a> <a href="#" class="btn">Info</a></p>
                </div>
            </div>
        </li>
        <li class="span3">
            <div class="thumbnail">
                <img src="http://placehold.it/260x180" alt="">
                <div class="caption">
                    <h5>Thumbnail label</h5>
                    <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
                    <p><a href="#" class="btn btn-primary">Uninstall</a> <a href="#" class="btn">Info</a></p>
                </div>
            </div>
        </li>
        <li class="span3">
            <div class="thumbnail">
                <img src="http://placehold.it/260x180" alt="">
                <div class="caption">
                    <h5>Thumbnail label</h5>
                    <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
                    <p><a href="#" class="btn btn-primary">Uninstall</a> <a href="#" class="btn">Info</a></p>
                </div>
            </div>
        </li>
        <li class="span3">
              <div class="thumbnail">
                <img src="http://placehold.it/260x180" alt="">
                <div class="caption">
                    <h5>Thumbnail label</h5>
                    <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
                    <p><a href="#" class="btn btn-primary">Uninstall</a> <a href="#" class="btn">Info</a></p>
                </div>
            </div>
        </li>
        <li class="span3">
              <div class="thumbnail">
                <img src="http://placehold.it/260x180" alt="">
                <div class="caption">
                    <h5>Thumbnail label</h5>
                    <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
                    <p><a href="#" class="btn btn-primary">Uninstall</a> <a href="#" class="btn">Info</a></p>
                </div>
            </div>
        </li>
        <li class="span3">
            <div class="thumbnail">
                <img src="http://placehold.it/260x180" alt="">
                <div class="caption">
                    <h5>Thumbnail label</h5>
                    <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
                    <p><a href="#" class="btn btn-primary">Uninstall</a> <a href="#" class="btn">Info</a></p>
                </div>
            </div>
        </li> /span-->
      </ul><!--/row-->
    </div><!--/span-->
    
<div class="modal hide" id="categoryModal">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <h3>Add Category</h3>
  </div>
  <div class="modal-body">
    <div id="modelForm">
        <form class="form-horizontal" id="createCategoryForm">
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
            <strong>Failed to create category!</strong> <span id="modelDataErrorResultMsg"></span>
          </div>
        </div>
    </div>
    <div id="modelSuccessResult" style="display:none;">
        <div class="alert fade in alert-info">
            <strong>Category created!</strong> <span id="modelSuccessResultMsg"></span>
          </div>
    </div>
    <div id="modelRuntimeErrorResult" style="display:none;">
        <div class="alert fade in alert-error">
            <strong>Runtime Error!</strong> <span id="modelRuntimeErrorResultMsg"></span>
          </div>
    </div>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn" data-dismiss="modal">Close</a>
    <a href="#" class="btn btn-primary" id="addCategoryItem">Save changes</a>
  </div>
</div>
