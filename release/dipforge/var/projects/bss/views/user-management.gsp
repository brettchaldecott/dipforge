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
            <th>First Name</th>
            <th>Last Name</th>
            <th>Username</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td><i class="icon-minus-sign"></i></td>
            <td>Mark</td>
            <td>Otto</td>
            <td>mdo</td>
          </tr>
          <tr>
            <td><i class="icon-minus-sign"></i></td>
            <td>Jacob</td>
            <td>Thornton</td>
            <td>fat</td>
          </tr>
          <tr>
            <td><a data-toggle="modal" href="#userModal"><i class="icon-minus-sign"></i></a></td>
            <td>Larry</td>
            <td>the Bird</td>
            <td>twitter</td>
          </tr>
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
              <label class="control-label" for="userName">User name</label>
              <div class="controls">
                <input type="text" class="input-large" id="userName" name="userName" placeholder="username">
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
              <label class="control-label" for="baseUserType">User Type</label>
              <div class="controls">
                <select class="input-large" id="baseUserType" name="baseUserType">
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

