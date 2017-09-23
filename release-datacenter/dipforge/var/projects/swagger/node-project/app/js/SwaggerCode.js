/*
 * swagger: Description
 * Copyright (C) Thu Apr 13 02:11:01 UTC 2017 owner
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * SwaggerCode.js
 * @author brett chaldecott
 */


$("#project").change(function() {
    var projectName = $( "#project option:selected" ).text();
    $("#swagger-ui").empty()
    // Build a system
    const ui = SwaggerUIBundle({
        url: "/DipforgeWeb/" + projectName + "/com/dipforge/project/Swagger.groovy",
        dom_id: '#swagger-ui',
        presets: [
        SwaggerUIBundle.presets.apis,
        SwaggerUIStandalonePreset
        ],
        plugins: [
        SwaggerUIBundle.plugins.DownloadUrl
        ],
        layout: "StandaloneLayout"
    });
    
    window.ui = ui

})
