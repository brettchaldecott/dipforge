/*
 * social: The social stream
 * Copyright (C) Thu Jan 24 05:23:23 SAST 2013 dipforge
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
 * Messages.js
 * @author brett chaldecott
 */


$(document).ready(function() {
    $('#messageList').empty();
    loadMsgs();
    $('#createMessage').click(function(event) {
        event.preventDefault();
        createMsg();
    });
});

function createMsg() {
    $('#createMessage').toggleClass('btn-primary');
    $.ajax({
        url: 'messages/CreateMessage.groovy',
        data: "message=" + $('#messageText').val(),
        success: function(data) {
            if (data == "success") {
                setTimeout(function () {
                    $('#createMessage').toggleClass('btn-primary');
                    loadMsgs();
                }, 1000 * 4);
                
            } else {
                
                setTimeout(function () {
                    $('#createMessage').toggleClass('btn-primary');
                    loadMsgs();    
                }, 1000 * 4);
            }
        },
        failure: function(data) {
            setTimeout(function () {
                $('#createMessage').toggleClass('btn-primary');
                loadMsgs();    
            }, 1000 * 4);
        }
    });
}

function loadMsgs() {
    $.ajax({
        url: 'messages/List.groovy',
        success: function(data) {
            $('#messageList').empty();
            var html = [];
            $.each($.parseJSON(data),function(index,message) {
                html.push(
                    '<li class="span9" style="margin-bottom:10px;">',
                    '    <div class="thumbnail">',
                    '        <p><b>',message.username,'</b> ',message.created,'</p>',
                    '        <p>',message.message,'</p>',
                    '    </div>',
                    '</li>\n');
            });
            $('#messageList').append(html.join(''));
            // delay and hide the modal
            setTimeout(function () {
                loadMsgs();
            }, 1000 * 1 * 60);  
        },
        failure: function(data) {
            // delay and hide the modal
            setTimeout(function () {
                loadMsgs();    
            }, 1000 * 1 * 60);
        }
    });
}
