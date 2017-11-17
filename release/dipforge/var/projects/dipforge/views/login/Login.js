/*
 * dipforge: Description
 * Copyright (C) Thu Nov 16 07:09:08 UTC 2017 owner
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
 * Login.js
 * @author admin
 */


// A $( document ).ready() block.
$( document ).ready(function() {
    console.log( "ready!" );
    
    var path = window.location.pathname
    console.log("The path for us all is %s",path)
    
    // this is the id of the form
    $("#login_form").submit(function(e) {
    
        var url = "path/to/your/script.php"; // the script where you handle the form input.
    
        $.ajax({
               type: "POST",
               url: path + 'j_security_check',
               data: $("#login_form").serialize(), // serializes the form's elements.
               success: function(data,status,jqXHR) {
                   console.log("The result of the call is %o %o %o %o",data,status,jqXHR,jqXHR.status)
                   if (jqXHR.status == 200) {
                        $.get( path + "/login/check.json",{
                            success: function(data,status,jqXHR) {
                                let jsonData = JSON.parse(data);
                                if (jqXHR.status == 200 && jsonData.status == "private vissabe") {
                                    
                                } else {
                                    
                                }
                            }
                        });
                   }
                   
               },
               error: function(data) {
                   console.log("The data for stufff %o",data)
               }
             });
    
        e.preventDefault(); // avoid to execute the actual submit of the form.
    });
    
});
