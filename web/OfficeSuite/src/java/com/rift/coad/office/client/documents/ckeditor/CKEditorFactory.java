/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.office.client.documents.ckeditor;

import com.rift.coad.gwt.lib.client.console.ConsolePanel;
import com.rift.coad.gwt.lib.client.console.PanelFactory;
import com.rift.coad.office.client.documents.Constants;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.VLayout;
import java.util.HashMap;
import java.util.Map;

/**
 * This object manages the CKEditor.
 *
 * @author brett chaldecott
 */
public class CKEditorFactory implements PanelFactory {

    public class CKEditorPanel extends ConsolePanel {


        private Window messageWindow = null;

        /**
         * The default constructor
         */
        public CKEditorPanel() {
            //setID("php." + scriptInfo.getScope() + "." + scriptInfo.getFileName());
        }


        /**
         * This method returns the panel view.
         *
         * @return The reference to the panel.
         */
        @Override
        public Canvas getViewPanel() {
            VLayout layout = new VLayout();
            layout.setWidth100();
            layout.setHeight100();

            Window window = new Window();
            window.setTitle(scope + "/" + filename);
            window.setWidth100();
            window.setHeight100();
            window.setCanDragReposition(false);
            window.setCanDragResize(true);
            window.setHeaderControls();

            window.setSrc("/OfficeSuite/CKFileEditor?scope=" +
                    scope + "&fileName=" + filename);

            layout.addMember(window);

            return layout;
        }


        /**
         * This method returns the name of this panel
         *
         * @return The string containing the name of this panel
         */
        @Override
        public String getName() {
            return filename;
        }


        /**
         * This method returns the icon path for this console
         *
         * @return The icon path.
         */
        @Override
        public String getIcon() {
            return Constants.HTML_DOCUMENT_ICON;
        }


        /**
         * This method is called to set the message window value on the canvas.
         *
         * @param value The value to set in the window.
         */
        public void setMessageCanvas(String message) {
            if (messageWindow != null) {
                try {
                    Canvas[] items = messageWindow.getItems();
                    if (items != null) {
                        for (Canvas item : items) {
                            messageWindow.removeItem(item);
                        }
                    }
                    Label label = new Label("<b>Result:</b><br>" + message.replaceAll("\n", "<br>"));
                    label.setWidth100();
                    label.setHeight100();
                    label.setPadding(5);
                    label.setValign(VerticalAlignment.TOP);
                    messageWindow.addItem(label);
                } catch (Exception ex) {
                    SC.say("Failed to set the values : " + ex.getMessage());
                }
            }
        }

    }

    // class singletons
    private static Map<String, CKEditorFactory> mapSingleton =
            new HashMap<String, CKEditorFactory>();

    // private member variables
    private String scope;
    private String filename;
    private String id;
    private CKEditorPanel panel;


    /**
     * This constructor is responsible for
     * @param scope
     */
    private CKEditorFactory(String scope, String filename) {
        this.scope = scope;
        this.filename = filename;
    }


    /**
     * The reference to the instance singleton.
     * @param scriptInfo The script informaton.
     * @return The reference to the file editor
     */
    public synchronized static CKEditorFactory getInstance(String scope, String filename) {
        String id = scope + "/" + filename;
        if (!mapSingleton.containsKey(id)) {
            mapSingleton.put(id, new CKEditorFactory(scope,filename));
        }
        return mapSingleton.get(id);
    }


    /**
     * This method is responsible for creating a new panel.
     *
     * @return The reference to the newly created canvas.
     */
    public Canvas create() {
        if (panel == null) {
            panel = new CKEditorPanel();
            id = panel.getID();
        } else if (!panel.isDrawn()) {
            panel = new CKEditorPanel();
            panel.draw();
            id = panel.getID();
        }

        return panel;
    }


    /**
     * This method returns the reference to the panel.
     *
     * @return The reference to the panel.
     */
    public CKEditorPanel getPanel() {
       return panel;
    }


    /**
     * This method returns the id of the panel instance.
     *
     * @return The reference to the panel id.
     */
    public String getID() {
        return id;
    }


    /**
     * The description of this factory.
     *
     * @return The string containing the description.
     */
    public String getDescription() {
        return "File Editor Factory";
    }
}
