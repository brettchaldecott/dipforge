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
 * PieceBin.java
 */


// package path
package com.rift.coad.change.client.action.workflow;

// smart gwt imports
import com.rift.coad.change.client.action.workflow.piece.PieceCall;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.DropEvent;
import com.smartgwt.client.widgets.events.DropHandler;
import com.smartgwt.client.widgets.events.DropOverEvent;
import com.smartgwt.client.widgets.events.DropOverHandler;
import com.smartgwt.client.widgets.events.ShowContextMenuEvent;
import com.smartgwt.client.widgets.events.ShowContextMenuHandler;
import com.smartgwt.client.widgets.layout.VStack;
import java.util.ArrayList;
import java.util.List;

/**
 * The piece bin base class.
 *
 * @author brett chaldecott
 */
public abstract class PieceBin extends VStack implements DropHandler, ShowContextMenuHandler {

    // class singletons
    private static boolean handled = false;

    // private member variables
    private PieceBin bin;
    private VStack flowBar = null;
    private WorkflowCanvas canvas;

    /**
     * This constructor sets up the edge image.
     *
     * @param edgeImage The edge image.
     * @param drag The image to drag
     */
    public PieceBin(String edgeImage, boolean drag, WorkflowCanvas canvas) {
        this(edgeImage, null, drag, canvas);
    }

    /**
     * This constructor sets up all the relevant information for the piece bin.
     *
     * @param edgeImage The image to set on the side of the bar.
     * @param icon The icon to display at the top.
     * @param drag
     */
    public PieceBin(String edgeImage, String icon, boolean drag, WorkflowCanvas canvas) {

        try {

            this.setAlign(Alignment.CENTER);
            //this.setShowEdges(true);
            this.canvas = canvas;
            // this extra check is here to see if an icon will be displayed with
            // the
            if (icon != null) {
                Canvas holder = new Canvas();
                holder.setAlign(Alignment.CENTER);
                holder.setHeight(16);
                holder.setWidth100();
                Img imageObj = new Img();
                imageObj.setSrc(icon);
                imageObj.setAppImgDir("workflow/");
                imageObj.setAlign(Alignment.CENTER);
                imageObj.setWidth(16);
                imageObj.setHeight(16);
                holder.addChild(imageObj);
                this.addMember(holder);
            }
            // set this pane to drag able.
            if (drag) {
                setCanDrag(true);
                setCanDrop(true);
            }

            // instanciate the flow control bar
            flowBar = new VStack();
            if (edgeImage != null) {
                flowBar.setEdgeImage(edgeImage);
            }
            flowBar = new VStack();
            flowBar.setWidth(100);
            flowBar.setHeight(100);
            flowBar.setShowEdges(true);
            flowBar.setEdgeSize(6);
            flowBar.setLayoutAlign(Alignment.CENTER);
            flowBar.setCanAcceptDrop(true);
            flowBar.setAnimateMembers(true);
            flowBar.setDropLineThickness(4);


            flowBar.addDropOverHandler(new DropOverHandler() {

                public void onDropOver(DropOverEvent event) {
                    handled = false;
                }
            });

            flowBar.addDropHandler(this);

            this.addMember(flowBar);

        //SC.say("Flow bar has been created");
        } catch (Exception ex) {
            SC.say("Failed to create piece bin [" + ex.getMessage() + "]");
        }
    }

    
    /**
     * The piece bin constructor that sets the icon.
     * 
     * @param icon The path to the icon
     */
    public PieceBin(String icon, WorkflowCanvas canvas) {
        this(null, icon, true, canvas);
    }


    /**
     * The implementation of the drop method.
     *
     * @param event The event containining the drop information.
     */
    public void onDrop(DropEvent event) {
        if (EventHandler.getDragTarget() instanceof DragPiece) {
            try {
                if (!handled) {
                    DragPiece piece = (DragPiece) EventHandler.getDragTarget();
                    flowBar.addMember(TypeLookupFactory.getType(piece.getType(), canvas,this),
                            flowBar.getDropPosition());
                    handled = true;
                }
                event.cancel();

            } catch (Exception ex) {
                SC.say("Failed [" + ex.getMessage() + "] [" + EventHandler.getTarget() + "][" +
                        EventHandler.getDragTarget() + "]");
                event.cancel();
            }

        }
    }


    /**
     * The flow bar.
     * 
     * @return The reference to the vertical stack information.
     */
    public VStack getFlowBar() {
        return flowBar;
    }


    /**
     * This method is called to set the piece bin.
     *
     * @return The piece bin.
     */
    public PieceBin getBin() {
        return bin;
    }

    /**
     * This method is called to set the piece bin.
     *
     * @param bin The piece bin.
     */
    public void setBin(PieceBin bin) {
        this.bin = bin;
    }



    /**
     * This method retrieves the list of parameters.
     *
     * @return The list of parameters.
     */
    public List<ParamInfo> listParameters() {
        List<ParamInfo> list = new ArrayList<ParamInfo>();
        list.addAll(getParameters());
        if (bin != null) {
            list.addAll(bin.listParameters());
        }
        return list;
    }


    /**
     * This method returns the list of parameters.
     *
     * @return The list of parameters
     */
    public abstract List<ParamInfo> getParameters();


    /**
     * The event to call.
     *
     * @param event The event to handle.
     */
    public void onShowContextMenu(ShowContextMenuEvent event) {
        try {
            // TODO: Fix the context code
            PieceMenu pieceMenu = new PieceMenu();
            //getFlowBar().addChild(pieceMenu);
            //pieceMenu.setVisibility(Visibility.HIDDEN);
            pieceMenu.setBin(this);
            pieceMenu.setTargetCanvas((Canvas)event.getSource());
            //pieceMenu.setTarget((Canvas)event.getSource());
            //pieceMenu.setLeft(event.getX());
            //pieceMenu.setTop(event.getY());
            pieceMenu.showContextMenu();

            
            // TODO: this is a nasty hack I need to fix the menu context
            // so that deleting is cleaner rather than just a right click.
//            getFlowBar().removeChild((Canvas)event.getSource());
            //getFlowBar().getChildren();
            event.cancel();
        } catch (Exception ex) {
            SC.say("Failed to handle the right mouse click event : " + ex.getMessage());
        }
    }
}
