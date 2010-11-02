/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2009  Rift IT Contracting
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
 * WorkflowCanvas.java
 */

// package path
package com.rift.coad.change.client.action.workflow;

// smart gwt imports
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rift.coad.change.client.action.ActionManagerConnector;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.events.ClickHandler;

// change manage imports
import com.rift.coad.change.client.action.workflow.piece.block.BinWorkflowBlock;
import com.rift.coad.change.rdf.objmapping.client.change.ActionDefinition;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.layout.VLayout;


/**
 * This object represents a workflow canvas
 *
 * @author brett
 */
public class WorkflowCanvas extends Canvas implements ClickHandler {


    // the actions
    private String action;
    private String type;
    private VStack toolsStack = new VStack(10);
    private BinWorkflowBlock workflow;
    private Canvas propertyPanel;
    private VLayout propertyLayout;
    private boolean membersSet = false;
    private String stack = "";
    private String[] dependancies;
    

    /**
     * The constructor of the workflow canvas
     *
     * @param action The action that is defined by the workflow.
     * @param type The type that is defined by the workflow.
     */
    public WorkflowCanvas(String type, String action) {
        this.type = type;
        this.action = action;
        
        // setup this layout
        this.setWidth100();
        this.setHeight100();

        // setup the master layout
        HLayout masterLayout = new HLayout();
        masterLayout.setWidth100();
        masterLayout.setHeight100();

        // setup the tool stack
        toolsStack.setLayoutMargin(20);
        toolsStack.setMembersMargin(20);
        toolsStack.setLayoutAlign(Alignment.CENTER);
        toolsStack.setHeight100();
        toolsStack.setWidth(100);
        toolsStack.setOverflow(Overflow.SCROLL);
        masterLayout.addMember(toolsStack);
        addTools(toolsStack);


        // setup the flow stack
        workflow = new BinWorkflowBlock(this,type,action);
        workflow.addClickHandler(this);
        Canvas binCanvas = new Canvas();
        binCanvas.setOverflow(Overflow.SCROLL);
        binCanvas.addChild(workflow);
        binCanvas.setWidth100();
        binCanvas.setHeight100();
        masterLayout.addMember(binCanvas);

        // setup the property panel
        propertyPanel = new Canvas();
        propertyPanel.setOverflow(Overflow.SCROLL);
        propertyPanel.setHeight100();
        propertyPanel.setWidth(610);

        propertyLayout = new VLayout();
        propertyLayout.setWidth100();
        propertyLayout.setHeight100();
        propertyPanel.addChild(propertyLayout);

        masterLayout.addMember(propertyPanel);

        this.addChild(masterLayout);
        membersSet = false;

        // load the action definition
        loadActionDefinition();
    }


    /**
     * The getter for the action.
     * 
     * @return The string containing the action.
     */
    public String getAction() {
        return action;
    }


    /**
     * The setter for the action.
     *
     * @param action The string containing the action name.
     */
    public void setAction(String action) {
        this.action = action;
    }


    /**
     * This method returns the type information.
     *
     * @return The string containing the type information.
     */
    public String getType() {
        return type;
    }


    /**
     * This method sets the type information.
     *
     * @param type The string containing the type information.
     */
    public void setType(String type) {
        this.type = type;
    }

    
    /**
     * This method retrieves the current dependancy data.
     * 
     * @return The list of dependancies.
     */
    public String[] getDependancies() {
        return dependancies;
    }


    /**
     * This method sets the dependancies.
     *
     * @param dependancies
     */
    public void setDependancies(String[] dependancies) {
        this.dependancies = dependancies;
    }





    
    /**
     * This method adds the tools to the stack.
     *
     * @param toolsStack The stack to add the tools to.
     */
    private void addTools(VStack toolsStack) {
        // the tools
        DragPiece piece = new DragPiece(Icons.CALL,Icons.CALL);
        piece.setPrompt("Call Data Mapper");
        toolsStack.addMember(piece);

        piece = new DragPiece(Icons.ASSIGN,Icons.ASSIGN);
        piece.setPrompt("Assign value between Variables");
        toolsStack.addMember(piece);
        
        // TODO: add embedded panel in at the appropriate time.
        //toolsStack.addMember(new DragPiece(Icons.EMBEDDED,Icons.EMBEDDED));

        // TODO: Will ad back at later stage
        //toolsStack.addMember(new DragPiece(Icons.RETURN,Icons.RETURN));
        piece = new DragPiece(Icons.BLOCK,Icons.BLOCK);
        piece.setPrompt("Sub-Block Scope");
        toolsStack.addMember(piece);
        
        piece = new DragPiece(Icons.IF,Icons.IF);
        piece.setPrompt("IF Statement");
        toolsStack.addMember(piece);

        piece = new DragPiece(Icons.ELSE_IF,Icons.ELSE_IF);
        piece.setPrompt("ELSE IF Statement");
        toolsStack.addMember(piece);

        piece = new DragPiece(Icons.ELSE,Icons.ELSE);
        piece.setPrompt("ELSE Statement");
        toolsStack.addMember(piece);


        //piece = new DragPiece(Icons.FOR_EACH,Icons.FOR_EACH);
        //piece.setPrompt("FOR Each Loop");
        //toolsStack.addMember(piece);


        piece = new DragPiece(Icons.FOR,Icons.FOR);
        piece.setPrompt("FOR Loop");
        toolsStack.addMember(piece);


        piece = new DragPiece(Icons.WHILE,Icons.WHILE);
        piece.setPrompt("While Loop");
        toolsStack.addMember(piece);
    }


    /**
     * This method is called to handle the onclick events.
     *
     * @param event The event that spawned this action.
     */
    public void onClick(ClickEvent event) {
        try {
            // delete all the children from the property panel
            if (membersSet == true) {
                propertyLayout.removeMembers(propertyLayout.getMembers());
                membersSet = false;
            }
            
            // property panel
            PropertyFactory property = (PropertyFactory)event.getSource();
            Canvas propertyPanel = property.getPropertyPanel();
            if (propertyPanel != null) {
                this.propertyLayout.addMember(propertyPanel);
                membersSet = true;
            }
            event.cancel();
        } catch (UnsupportedOperationException ex) {
            // ignore this
        } catch (Exception ex) {
            SC.say("Failed to get the create the canvas : " + ex.getMessage());
        }
    }


    /**
     * This method is called to load the action definition
     */
    private void loadActionDefinition() {
        ActionManagerConnector.getService().getActionDefinition(type, action, 
            new AsyncCallback() {

            /**
             * This method deals with successfull calls
             */
            public void onFailure(Throwable caught) {
                SC.say("Failed to retrieve the workflow : " + caught.getMessage());
            }


            /**
             * This method deals with failure calls.
             */
            public void onSuccess(Object result) {
                if (result == null) {
                    return;
                }
                displayActionDefinition((ActionDefinition)result);
            }

        });
    }


    /**
     * This method displays the action definition.
     *
     * @param definition This method is called to display the actions.
     */
    private void displayActionDefinition(ActionDefinition definition) {
        this.dependancies = definition.getDependancyData();
        this.workflow.setDefinition(definition);
    }


    /**
     * This method is called to save the action definition.
     */
    public void saveActionDefinition() {
        try {
            ActionDefinition definition = this.workflow.getDefinition();
            definition.setDependancyData(dependancies);
            //SC.say("Make the call to save the definition");
            ActionManagerConnector.getService().saveActionDefinition(definition,
                new AsyncCallback() {

                /**
                 * Display any errors that occur during the save process
                 */
                public void onFailure(Throwable caught) {
                    SC.say("Failed to save the action definition : " +
                            caught.getMessage());
                }

                /**
                 * There is no result.
                 */
                public void onSuccess(Object result) {
                    // ignore
                }

            });
        } catch (Exception ex) {
            SC.say("Failed to save the changes : " + ex.getMessage());
        }
    }



}
