<!--
Date: Sat Aug 18 08:16:00 SAST 2012
File: user-management.gsp
Author: admin
-->

<div class="span9">
    <div class="hero-unit">
    <legend>Users</legend>
        <table class="table">
        <thead>
          <tr>
            <th>&nbsp;</th>
            <th>Username</th>
            <th>Email</th>
            <th>User Type</th>
          </tr>
        </thead>
        <tbody id="userRecords">
        <%
        params.users.each { users ->
            def user = users[0]
            %>
          <tr id="tr${user.getId()}">
            <td><a href="javascript:showRemoveUser('${user.getId()}');"><i class="icon-minus-sign"></i></a></td>
            <td><a href="javascript:showUpdateUser('${user.getId()}');">${user.getUsername()}</a></td>
            <td><a href="javascript:showUpdateUser('${user.getId()}');">${user.getEmail()}</a></td>
            <td><a href="javascript:showUpdateUser('${user.getId()}');">${user.getUserType()}</a></td>
            <form id="userData${user.getId()}" style="display:none;">
                <input type="hidden" id="userData${user.getId()}userId" name="userData${user.getId()}userId" value="${user.getId()}" />
                <input type="hidden" id="userData${user.getId()}username" name="userData${user.getId()}username" value="${user.getUsername()}" />
                <input type="hidden" id="userData${user.getId()}email" name="userData${user.getId()}email" value="${user.getEmail()}" />
                <input type="hidden" id="userData${user.getId()}userType" name="userData${user.getId()}userType" value="${user.getUserType()}" />
            </form>
          </tr>
        <% } %>
        </tbody>
        <tbody>
          <tr>
            <td><a data-toggle="modal" href="#userModal"><i class="icon-plus-sign"></a></i></td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
        </tbody>
      </table>
    </div>
</div>


<div class="modal hide" id="userModal">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <div id="addUserTitle">
        <h3>Add User</h3>
    </div>
    <div id="updateUserTitle" style="display:none;">
        <h3>Update User</h3>
    </div>
  </div>
  <div class="modal-body">
    <div id="modelForm">
        <form class="form-horizontal" id="userForm">
          <fieldset>
            <div class="control-group">
              <label class="control-label" for="userId">User ID</label>
              <div class="controls">
                <input type="text" class="input-large" id="userId" name="userId" placeholder="userid">
                <p class="help-block">The id of the user</p>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label" for="username">User name</label>
              <div class="controls">
                <input type="text" class="input-large" id="username" name="username" placeholder="username">
                <p class="help-block">The username.</p>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label" for="email">Email</label>
              <div class="controls">
                <input type="text" class="input-large" id="email" name="email" placeholder="email@email.com">
                <p class="help-block">The user email address.</p>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label" for="password">Password</label>
              <div class="controls">
                <input type="password" class="input-large" id="password" name="password" placeholder="password">
                <p class="help-block">Thumbnail URL or server path.</p>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label" for="userType">User Type</label>
              <div class="controls">
                <select class="input-large" id="userType" name="userType">
                    <option value="base-user">Base</option>
                </select>
                <p class="help-block">The base user type.</p>
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
    <a href="#" class="btn" data-dismiss="modal" id="userCloseButton">Close</a>
    <a href="#" class="btn btn-primary" id="addUserItem">Add User</a>
    <a href="#" class="btn btn-primary" id="updateUserItem" style="display:none;">Update User</a>
  </div>
</div>

