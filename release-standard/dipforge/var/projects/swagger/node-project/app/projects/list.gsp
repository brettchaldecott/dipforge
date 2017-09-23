<!--
Date: Wed Apr 12 11:44:49 UTC 2017
File: list.gsp
Author: admin
-->

<form>
    <select id="project" name="project"><% params.projects.each{ project -> %>
    <option value="<%= project.name %>"><%= project.name %></option>
    <% } %></select>
</form>

<div id="swagger-ui"></div>

<script src="./bower_components/jquery/dist/jquery.min.js"> </script>
<script src="./bower_components/swagger-ui/dist/swagger-ui-bundle.js"> </script>
<script src="./bower_components/swagger-ui/dist/swagger-ui-standalone-preset.js"> </script>
<script src="./js/SwaggerCode.js"> </script>
