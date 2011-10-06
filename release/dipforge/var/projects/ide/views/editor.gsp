<html>
<head>
   <link rel="stylesheet" type="text/css" href="css/Editor.css">
</head>
<body>
<div id="editor" style="height: 100%; width: 100%">var test = 1</div>
<script type="text/javascript" src="ext-js-4.0/bootstrap.js"></script>
<script src="/ace-0.2.0/ace.js" type="text/javascript" charset="utf-8"></script>
<script src="/ace-0.2.0/mode-javascript.js" type="text/javascript" charset="utf-8"></script>
<script src="/ace-0.2.0/mode-groovy.js" type="text/javascript" charset="utf-8"></script>
<script src="/ace-0.2.0/mode-java.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="js/EditorView.js"></script>
<script>
        Ext.Loader.setConfig({enabled: true});
        Ext.Loader.setPath('Ext.ux', 'ux/');
        Ext.require([
            'Ext.grid.*',
            'Ext.data.*',
            'Ext.util.*',
            'Ext.Action',
            'Ext.tab.*',
            'Ext.button.*',
            'Ext.form.*',
            'Ext.layout.container.Card',
            'Ext.layout.container.Border',
            'Ext.ux.PreviewPlugin',
            'Ext.ux.TabScrollerMenu'
        ]);
        Ext.onReady(function(){
            var app = new com.dipforge.Editor.App();
        });
//window.onload = function() {
//    var editor = ace.edit("editor");
//    var JavaScriptMode = require("ace/mode/groovy").Mode;
//    editor.getSession().setMode(new JavaScriptMode());
//    editor.resize();
//};
</script>
</body>
</html>