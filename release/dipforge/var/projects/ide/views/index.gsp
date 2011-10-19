<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN">
<html>
<head>
	
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Dipforge</title>
    <link rel="stylesheet" type="text/css" href="ext-js-4.0/resources/css/ext-all.css">
    <link rel="stylesheet" type="text/css" href="css/Feed-Viewer.css">
    <link rel="stylesheet" type="text/css" href="css/TabScrollerMenu.css">
    <link rel="stylesheet" type="text/css" href="css/Editor.css">
    
<style type="text/css">
.x-menu-item img.preview-right, .preview-right {
    background-image: url(images/preview-right.gif);
}
.x-menu-item img.preview-bottom, .preview-bottom {
    background-image: url(images/preview-bottom.gif);
}
.x-menu-item img.preview-hide, .preview-hide {
    background-image: url(images/preview-hide.gif);
}

#reading-menu .x-menu-item-checked {
    border: 1px dotted #a3bae9 !important;
    background: #DFE8F6;
    padding: 0;
    margin: 0;
}
</style>
    <script type="text/javascript" src="ext-js-4.0/bootstrap.js"></script>
    <script type="text/javascript" src="js/States.js"></script>
    <script type="text/javascript" src="js/EditorPanel.js"></script>
    <script type="text/javascript" src="js/ProjectPanel.js"></script>
    <script type="text/javascript" src="js/IDEView.js"></script>
    <script type="text/javascript" src="js/FilePanelDialog.js"></script>
    <script type="text/javascript" src="js/DeleteFileDialog.js"></script>
	<script type="text/javascript" src="js/DeleteProjectDialog.js"></script>
	<script type="text/javascript" src="js/CreateProjectDialog.js"></script>
	<script type="text/javascript" src="ux/TabScrollerMenu.js"></script>
	<script type="text/javascript" src="/ace-0.2.0/ace-uncompressed.js"></script>
    <script type="text/javascript" src="/ace-0.2.0/mode-javascript.js" charset="utf-8"></script>
    <script type="text/javascript" src="/ace-0.2.0/mode-c_cpp.js" charset="utf-8"></script>
    <script type="text/javascript" src="/ace-0.2.0/mode-clojure.js" charset="utf-8"></script>
    <script type="text/javascript" src="/ace-0.2.0/mode-coffee.js" charset="utf-8"></script>
    <script type="text/javascript" src="/ace-0.2.0/mode-coffee.js" charset="utf-8"></script>
    <script type="text/javascript" src="/ace-0.2.0/mode-csharp.js" charset="utf-8"></script>
    <script type="text/javascript" src="/ace-0.2.0/mode-css.js" charset="utf-8"></script>    
    <script type="text/javascript" src="/ace-0.2.0/mode-groovy.js" charset="utf-8"></script>
    <script type="text/javascript" src="/ace-0.2.0/mode-html.js" charset="utf-8"></script>
    <script type="text/javascript" src="/ace-0.2.0/mode-java.js" charset="utf-8"></script>
    <script type="text/javascript" src="/ace-0.2.0/mode-javascript.js" charset="utf-8"></script>
    <script type="text/javascript" src="/ace-0.2.0/mode-json.js" charset="utf-8"></script>
    <script type="text/javascript" src="/ace-0.2.0/mode-php.js" charset="utf-8"></script>
    <script type="text/javascript" src="/ace-0.2.0/mode-python.js" charset="utf-8"></script>
    <script type="text/javascript" src="/ace-0.2.0/mode-textile.js" charset="utf-8"></script>
    <script type="text/javascript" src="/ace-0.2.0/mode-xml.js" charset="utf-8"></script>
    
    <script type="text/javascript">
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
            var app = new com.dipforge.IDE.App();
        });
    </script>
    
</head>
<body> 
</body>
</html>