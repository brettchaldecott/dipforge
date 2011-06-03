<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Feed Viewer</title>
    <link rel="stylesheet" type="text/css" href="ext-js-4.0/resources/css/ext-all.css">
    <link rel="stylesheet" type="text/css" href="css/Desktop.css">
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
    <script type="text/javascript" src="js/bootstrap.js"></script>
    <script type="text/javascript" src="viewer/FeedPost.js"></script>
    <script type="text/javascript" src="viewer/FeedDetail.js"></script>
    <script type="text/javascript" src="viewer/FeedGrid.js"></script>
    <script type="text/javascript" src="viewer/FeedInfo.js"></script>
    <script type="text/javascript" src="viewer/FeedPanel.js"></script>
    <script type="text/javascript" src="viewer/FeedViewer.js"></script>
    <script type="text/javascript" src="viewer/FeedWindow.js"></script>
    <script type="text/javascript">
        Ext.Loader.setConfig({enabled: true});
        Ext.Loader.setPath('Ext.ux', '../ux/');
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
            'Ext.ux.PreviewPlugin'
        ]);
        Ext.onReady(function(){
            var app = new FeedViewer.App();
        });
    </script>
</head>
<body>
</body>
</html>
