<%-- 
    Document   : spreadsheeteditor
    Created on : 01 Mar 2010, 3:40:16 AM
    Author     : brett
--%>

<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>Spreed Sheet Editor</title>
  <link type="text/css" rel="stylesheet" href="resource/css/layout.css"/>
  <script language="Javascript" type="text/javascript" src="prototype-1.6.0.3.js"></script>
  <!-- Use settings to configure application -->
  <script type="text/javascript">
    qxsettings = { "qx.application" : "com.logisphere.x4view.Application" };
  </script>

  <script type="text/javascript" src="script/x4view.js"></script>

  <script type="text/javascript">






qx.log.Logger.ROOT_LOGGER.removeAllAppenders();

//qx.log.Logger.ROOT_LOGGER.addAppender(new qx.log.appender.Window());






qx.Class.define("com.logisphere.x4view.Application",
{
  extend : qx.application.Gui,

  members :
  {
    main : function()
    {
      // Call super class
      this.base(arguments);


      qx.Class.include(qx.ui.core.Widget, qx.ui.animation.MAnimation)

      qx.theme.manager.Meta.getInstance().setTheme(qx.theme.Ext);

      qx.theme.manager.Icon.getInstance().setIconTheme(qx.theme.icon.VistaInspirate);
      //qx.theme.manager.Icon.getInstance().setIconTheme(qx.theme.icon.Nuvola);



      var d = qx.ui.core.ClientDocument.getInstance();

      var w0 = new qx.ui.layout.CanvasLayout;
      w0.set({left:0, top:0, right:0, bottom:0});
      w0.setOverflow("hidden");
      d.add(w0);

      //var w1 = new qx.ui.window.Window("Simple Spreadsheet Component");
      //w1.setSpace(20, 800, 20, 600);
      //w0.add(w1);

      //var tf1 = new qx.ui.pageview.tabview.TabView;
      //tf1.set({ left: 10, top: 10, right: 10, bottom: 10 });

      //var t1_1 = new qx.ui.pageview.tabview.Button("Spreadsheet demo");

      //t1_1.setChecked(true);

      //tf1.getBar().add(t1_1);

      //var p1_1 = new qx.ui.pageview.tabview.Page(t1_1);

      //tf1.getPane().add(p1_1);




      var spreadData = "";

      var spread = new qx.ui.spread.Spread();
    <%
    String fileContents = (String)request.getAttribute("fileContents");
    if (fileContents.length() > 0) {
        %>
        spread.openSpread("${fileContents}", "Sheet");
        <%
    } else {
        %>
        spread.createSheet("Sheet 1");
        <%
    }
    %>
      spread.addEventListener("save", function(e) {
         spreadData = e.getData();
         try {
            var success       = function(t){
                $('ddiv').innerHTML = t.responseText + 'supposed to be here';
            }
            var failure         = function(t){ alert('update failed'); }
            var url = '/OfficeSuite/spreadsheet/SpreadSheetFileEditor?action=save';

            var pars = new Hash();
            pars.set('fileName','${fileName}');
            pars.set('fileContents',spreadData);

            var myAjax = new Ajax.Request(
            url,
            {
                method: 'post',
                parameters: pars
            }          );
        } catch (error) {
            alert('Failed to save : ' + error)
        }
         this.debug("********* Document was saved with data : " + e.getData());
      });
      spread.addEventListener("closesheet", function(e) {
         this.debug("********* Sheet " + e.getData() + "was closed");
      });

      //p1_1.add(spread);
      //w1.add(spread);
      w0.add(spread);

      //w1.open();

  }
 }
});

  </script>


</head>
</html>
