/*
 * CoadunationAdmin: The admin frontend for coadunation.
 * Copyright (C) 2007 - 2008  Rift IT Contracting
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
 * Method.java
 */

// imports
package com.rift.coad.web.admin.client;

// imports
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;

/**
 * This panel is responsible for displaying a single methods information and
 * providing a means to invoke it.
 *
 * @author brett chaldecott
 */
public class MethodPanel extends Composite implements ClickListener {
    
    // class constants
    private final static int MAX_DEFAULT_ROWS = 5;
    
    // scroll panel
    private MethodListener listener = null;
    private HTML methodName = new HTML();
    private HTML methodResult = new HTML();
    private HTML description = new HTML();
    private FlexTable parameters = new FlexTable();
    private Button invoke = new Button("Invoke");
    private MethodDef method = null;
    private TextArea[] textAreas = null;
    
    /**
     * Creates a new instance of Method
     */
    public MethodPanel(MethodListener listener) {
        this.listener = listener;
        VerticalPanel panel = new VerticalPanel();
        panel.setWidth("100%");
        
        // setup the label
        Label objectLabel = new Label("Method");
        objectLabel.setStyleName("header-Label");
        objectLabel.setWidth("100%");
        panel.add(objectLabel);
        
        // add the description
        VerticalPanel descVerticalPanel = new VerticalPanel();
        descVerticalPanel.setWidth("100%");
        methodName.setWidth("100%");
        methodName.setStyleName("method-NameRow");
        descVerticalPanel.add(methodName);
        methodResult.setWidth("100%");
        methodResult.setStyleName("method-ResultRow");
        descVerticalPanel.add(methodResult);
        description.setWordWrap(true);
        descVerticalPanel.add(description);
        ScrollPanel descScrollPanel = new ScrollPanel(descVerticalPanel);
        descScrollPanel.setStyleName("method-Desc-Scroller");
        descScrollPanel.setWidth("100%");
        panel.add(descScrollPanel);
        
        // parameter panel
        parameters.setCellSpacing(0);
        parameters.setCellPadding(0);
        parameters.setWidth("100%");
        ScrollPanel scrollPanel = new ScrollPanel(parameters);
        scrollPanel.setWidth("100%");
        scrollPanel.setStyleName("method-Scroller");
        scrollPanel.setAlwaysShowScrollBars(true);
        panel.add(scrollPanel);
        resetMethod();
        
        // button panel
        VerticalPanel buttonPanel = new VerticalPanel();
        buttonPanel.setWidth("100%");
        buttonPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        buttonPanel.add(invoke);
        buttonPanel.setStyleName("button-Panel");
        panel.add(buttonPanel);
        invoke.addClickListener(this);
        
        // border
        panel.setStyleName("panel-Border");
        
        initWidget(panel);
    }
    
    
    /**
     * Initializes the table so that it contains enough rows for a full page of
     * emails. Also creates the images that will be used as 'read' flags.
     */
    private void initTable() {
        // Create the header row.
        parameters.clear();
        parameters.setText(0, 0, "Name");
        parameters.setText(0, 1, "Value");
        parameters.setText(0, 2, "Type");
        parameters.setText(0, 3, "Description");
        parameters.getRowFormatter().setStyleName(0, "parameter-ListHeader");
        
        // Initialize the rest of the rows.
        for (int i = 0; i < MAX_DEFAULT_ROWS; ++i) {
            parameters.setText(i + 1, 0, "");
            TextArea text = new TextArea();
            text.setCharacterWidth(40);
            text.setVisibleLines(2);
            text.setText("");
            text.setEnabled(false);
            parameters.setWidget(i + 1, 1, text);
            parameters.setText(i + 1, 2, "");
            parameters.setText(i + 1, 3, "");
            parameters.getCellFormatter().setWordWrap(i + 1, 0, false);
            parameters.getCellFormatter().setWordWrap(i + 1, 1, false);
            parameters.getCellFormatter().setWordWrap(i + 1, 2, false);
            parameters.getCellFormatter().setWordWrap(i + 1, 2, false);
            styleRow(i + 1);
        }
    }
    
    
    /**
     * Initializes the table so that it contains enough rows for a full page of
     * emails. Also creates the images that will be used as 'read' flags.
     */
    private void setParameters(VariableDef[] parameterArray) {
        // Create the header row.
        parameters.clear();
        parameters.setText(0, 0, "Name");
        parameters.setText(0, 1, "Value");
        parameters.setText(0, 2, "Type");
        parameters.setText(0, 3, "Description");
        parameters.getRowFormatter().setStyleName(0, "parameter-ListHeader");
        
        // loop through the parameter arrauy
        int pos = 1;
        textAreas = new TextArea[parameterArray.length];
        for (int index = 0; index < parameterArray.length; index++) {
            
            parameters.setText(pos, 0, parameterArray[index].getName());
            textAreas[index] = new TextArea();
            textAreas[index].setCharacterWidth(40);
            textAreas[index].setVisibleLines(2);
            textAreas[index].setText("");
            textAreas[index].setEnabled(true);
            parameters.setWidget(pos, 1, textAreas[index]);
            parameters.setText(pos, 2, parameterArray[index].getType());
            parameters.setText(pos, 3, parameterArray[index].getDescription());
            parameters.getCellFormatter().setWordWrap(pos, 0, false);
            parameters.getCellFormatter().setWordWrap(pos, 1, false);
            parameters.getCellFormatter().setWordWrap(pos, 2, false);
            parameters.getCellFormatter().setWordWrap(pos, 2, false);
            styleRow(pos);
            pos++;
            
        }
        
        // Initialize the rest of the rows.
        for (int i = 0; i < (MAX_DEFAULT_ROWS - parameterArray.length); ++i) {
            parameters.setText(pos, 0, "");
            TextArea text = new TextArea();
            text.setCharacterWidth(40);
            text.setVisibleLines(2);
            text.setText("");
            text.setEnabled(false);
            parameters.setWidget(pos, 1, text);
            parameters.setText(pos, 2, "");
            parameters.setText(pos, 3, "");
            parameters.getCellFormatter().setWordWrap(pos, 0, false);
            parameters.getCellFormatter().setWordWrap(pos, 1, false);
            parameters.getCellFormatter().setWordWrap(pos, 2, false);
            parameters.getCellFormatter().setWordWrap(pos, 2, false);
            styleRow(pos);
            pos++;
        }
    }
    
    /**
     * This method sets the style on a row
     */
    private void styleRow(int row) {
        if ((row % 2) == 0) {
            parameters.getRowFormatter().addStyleName(row, "parameter-LightRow");
        } else {
            parameters.getRowFormatter().removeStyleName(row, "parameter-DarkRow");
        }

    }
    
    
    /**
     * This method displays the method information
     */
    public void setMethod(MethodDef method) {
        this.method = method;
        methodName.setHTML(method.getName());
        methodResult.setHTML(method.getResult().getType() + "&nbsp;" + 
                method.getResult().getDescription());
        description.setHTML(method.getDescription().replaceAll("<","&lt;").
                replaceAll(">","&gt;").replaceAll("\n","<br>").
                replaceAll("\t","&nbsp;&nbsp;&nbsp;&nbsp;"));
        setParameters(method.getParameters());
    }
    
    /**
     * This method resets the method
     */
    public void resetMethod() {
        methodName.setHTML("&nbsp;");
        methodResult.setHTML("&nbsp;");
        description.setHTML("&nbsp;");
        initTable();
        method = null;
        textAreas = null;
    }

    public void onClick(Widget sender) {
        if (this.invoke == sender) {
            VariableDef[] variables = method.getParameters();
            for (int index = 0; index < textAreas.length; index++) {
                variables[index].setValue(textAreas[index].getText());
            }
            listener.methodInvoked(method);
        }
    }
}
