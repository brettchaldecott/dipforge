<!--
Date: Wed Jun 27 05:43:54 SAST 2012
File: list.gsp
Author: brett chaldecott
-->

    <div class="span9">
      <ul class="thumbnails">
        <%
        params.products.each { products ->
            def product = products[0]
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
            
            \$('#hoveroverbutton').popover({});
            \$('#hoveroverbuttona').popover({});
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
    
<div class="modal hide" id="productModal">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <h3>Add Product</h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal">
      <fieldset>
        <div class="control-group">
          <label class="control-label" for="productId">Product ID</label>
          <div class="controls">
            <input type="text" class="input-large" id="productId" name="productId">
            <p class="help-block">The id of the new product</p>
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="productDataType">Product data type</label>
          <div class="controls">
            <input type="text" class="input-large" id="productDataType" name="productDataType">
            <p class="help-block">The url of the data type.</p>
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="categorySelect">Category Select</label>
          <div class="controls">
            <select class="input-large" id="categorySelect" name="categorySelect">
                <option value="1">test</option>
            </select>
            <p class="help-block">Select the category to attach this product to.</p>
          </div>
        </div>
      </fieldset>
    </form>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn" data-dismiss="modal">Close</a>
    <a href="#" class="btn btn-primary">Save changes</a>
  </div>
</div>

    
    