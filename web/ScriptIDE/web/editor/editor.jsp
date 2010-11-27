<%-- 
    Document   : editor
    Created on : 14 Dec 2009, 5:23:37 AM
    Author     : brett
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editor</title>

        <link rel="stylesheet" type="text/css" href="/ScriptIDE/css/extra-editor-style.css"/>

        <script language="Javascript" type="text/javascript" src="/ScriptIDE/editor/edit_area/edit_area_loader.js"></script>
        <script language="Javascript" type="text/javascript" src="/ScriptIDE/editor/prototype-1.6.0.3.js"></script>
        <script language="Javascript" type="text/javascript">
            editAreaLoader.init({
                id: "editor_${number}"	// id of the textarea to transform
                ,start_highlight: true
                ,font_size: "8"
                ,font_family: "verdana, monospace"
                ,allow_resize: "no"
                ,allow_toggle: false
                ,language: "en"
                ,syntax: "${type}"
                ,toolbar: "save, |, search, go_to_line, |, undo, redo, |, select_font, |, change_smooth_selection, highlight, reset_highlight, |, help"
                ,save_callback: "my_save"
                ,charmap_default: "arrows"
            });

            // callback functions
            function my_save(id, content){
                var new_content           =  $F(id);
                var success       = function(t){
                    $('ddiv').innerHTML = t.responseText + 'supposed to be here';
                }
                var failure         = function(t){ alert('update failed'); }
                var url = '/ScriptIDE/FileEditor?action=save';

                var pars = new Hash();
                pars.set('scope','${scope}');
                pars.set('fileName','${fileName}');
                pars.set('fileContents',content);

                var myAjax = new Ajax.Request(
                url,
                {
                    method: 'post',
                    parameters: pars
                }          );
            }

        </script>
        
    </head>
    <body>
        <form action='' method='post' id="textareaHolder">
            <textarea id="editor_${number}" style="height:100%; width:100%;" name="editor_area${number}">${fileContents}</textarea>
            <input type="hidden" name="scope" value="${scope}"/>
            <input type="hidden" name="fileName" value="${fileName}"/>
            <input type="hidden" name="action" value="save"/>
        </form>
    </body>
</html>
