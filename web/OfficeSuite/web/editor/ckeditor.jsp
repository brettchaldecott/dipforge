<%-- 
    Document   : editor
    Created on : 24 Feb 2010, 7:41:55 PM
    Author     : brett
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script language="Javascript" type="text/javascript" src="ckeditor/prototype-1.6.0.3.js"></script>
        <script language="Javascript" type="text/javascript" src="ckeditor/ckeditor.js"></script>
        <script src="editor-init.js" type="text/javascript"></script>
        <link href="editor-style.css" rel="stylesheet" type="text/css" />
        <link href="style/extra-editor-style.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <form method="post">
		    <input type="hidden" value="${fileName}" name="filename"/>
			<textarea id="editor" name="editor" style="height:100%; width:100%;">${fileContents}</textarea>
			<script type="text/javascript">
			//<![CDATA[
				CKEDITOR.replace( 'editor',
					{
						fullPage : true,
                        skin : 'v2',
                        on :
   {
      // Maximize the editor on start-up.
      'instanceReady' : function( evt )
      {
         evt.editor.execCommand( 'maximize' );
      }
   } 
					});
			//]]>
			</script>
	</form>
    </body>
</html>
