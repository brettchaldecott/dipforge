<html>
<head>
    <script src="UmlCanvas/UmlCanvas.standalone.min.js"></script>
</head>
<body>
    <!-- 
        a basic UmlCanvas with in-document source: we don't specify any initial
        width nor height, we're going to set it dynamically later on.
     -->
    <canvas id="test" class="UmlCanvas"></canvas>
    <pre id="testSource" style="display:none">
        +dynamic diagram myDiagram {
            [@10,10]
            class myclass {
                attribute myAttribute:String;
                attribute myAtt:String;
            }
        }
    </pre>
    <script>
    // wait until UmlCanvas is ready before accessing the actual UmlCanvas
    // instance.
    UmlCanvas.on( "ready", function wireAutoSizing() {
        // use the standard UmlCanvas getModel() method to get a reference to
        // the UmlCanvas
        var model = UmlCanvas.getModel( "test" );

        // we need a little trick here to avoid the height never to be updated
        // before "measuring" the height, we need to resize the canvas as to not
        // have it force the window to be larger
        function maximizeCanvas() {
            model.setSize( 1,1 );
            model.setSize( document.body.offsetWidth, document.body.offsetHeight );
        }

        // call it everytime the window resizes
        window.onresize = maximizeCanvas;
        // and call it once to initially grow to the (so far) not-resized widow
        maximizeCanvas();
    } );
    </script>
</body>
</html>