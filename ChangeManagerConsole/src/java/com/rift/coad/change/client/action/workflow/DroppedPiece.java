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
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.widgets.Img;

/**
 * This object represents a dropped piece.
 *
 * @author brett chaldecott
 */
public class DroppedPiece extends Img {

    // private member variables.
    private PieceBin bin;


    /**
     * The default constructor of the dropped piece.
     */
    public DroppedPiece() {
        setWidth(32);
        setHeight(32);
        setLayoutAlign(Alignment.CENTER);
        setCanDragReposition(true);
        setCanDrop(true);
        setDragAppearance(DragAppearance.TARGET);
        setAppImgDir("workflow/");
    }

    public DroppedPiece(String src) {
        this();
        setSrc(src);
    }


    /**
     * This method returns the piece bin that this object is attached to.
     *
     * @return The reference to the piece bin.
     */
    public PieceBin getBin() {
        return bin;
    }


    /**
     * This method sets the bin.
     *
     * @param bin The bin to set.
     */
    public void setBin(PieceBin bin) {
        this.bin = bin;
    }



}
