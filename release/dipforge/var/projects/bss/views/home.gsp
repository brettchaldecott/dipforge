<!--
Date: Fri Aug 17 07:28:44 SAST 2012
File: home.gsp
Author: brett chaldecott
-->

<%
def username = request.getRemoteUser();
def disabled = "false"
if (username.equals("admin")) {
    disabled = "true"
}

%>

<div class="span9">
    <form class="well form-horizontal" name="myProfile" id="myProfile">
        <fieldset>
            <legend>Profile</legend>
            <div class="control-group">
                <label class="control-label" for="username">Username</label>
                <div class="controls">
                    <input type="text" class="input-xlarge" id="username" disabled=true value="${request.getRemoteUser()}">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="firstname">Firstname</label>
                <div class="controls">
                    <input type="text" class="input-xlarge" id="firstname" name="firstname" disabled=${disabled}>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="lastname">Lastname</label>
                <div class="controls">
                    <input type="text" class="input-xlarge" id="lastname" name="lastname" disabled=${disabled}>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="email">Email</label>
                <div class="controls">
                    <input type="text" class="input-xlarge" id="email" name="email" disabled=${disabled}>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="password">Password</label>
                <div class="controls">
                    <input type="password" class="input-xlarge" id="password" name="password"  disabled=${disabled}>
                </div>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Save changes</button>
                <button class="btn">Cancel</button>
            </div>
        </fieldset>
    </form>
</div>