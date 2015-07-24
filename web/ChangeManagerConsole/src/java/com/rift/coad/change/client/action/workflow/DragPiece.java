/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2009  2015 Burntjam
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
 * DragPiece.java
 */

// package path
package com.rift.coad.change.client.action.workflow;

// smart gwt imports
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;

/**
 * This object represents a drag type
 *
 * @author Brett Chaldecott
 */
public class DragPiece extends Img {

    private String type;

    /**
     * The constructor of the drag piece.
     *
     * @param src The source image.
     * @param type The type of drag piece
     */
    public DragPiece(String src, String type) {
        setSrc(src);
        setWidth(32);
        setHeight(32);
        setCursor(Cursor.MOVE);
        setAppImgDir("workflow/");
        setCanDrag(true);
        setCanDrop(true);
        setDragAppearance(DragAppearance.TRACKER);
        this.type = type;

    }

    /**
     * This method sets the drag tracker.
     *
     * @return TRUE or FALSE.
     */
    protected boolean setDragTracker() {
        EventHandler.setDragTracker(Canvas.imgHTML("workflow/" + getSrc(), 24,
                24), 24, 24, 15, 15);
        return false;
    }


    /**
     * This method returns the type information.
     *
     * @return The type of drag piece this is.
     */
    public String getType() {
        return type;
    }
}
