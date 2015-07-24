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
 * TypeLookupFactory.java
 */

// package path
package com.rift.coad.change.client.action.workflow;

// smart gwt import
import com.rift.coad.change.client.action.workflow.piece.BinBlock;
import com.rift.coad.change.client.action.workflow.piece.PieceAssign;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;

// piece call
import com.rift.coad.change.client.action.workflow.piece.PieceCall;
import com.rift.coad.change.client.action.workflow.piece.PieceEmbedded;
import com.rift.coad.change.client.action.workflow.piece.PieceReturn;
import com.rift.coad.change.client.action.workflow.piece.block.BinElseBlock;
import com.rift.coad.change.client.action.workflow.piece.block.BinElseIfBlock;
import com.rift.coad.change.client.action.workflow.piece.block.BinForBlock;
import com.rift.coad.change.client.action.workflow.piece.block.BinForEachBlock;
import com.rift.coad.change.client.action.workflow.piece.block.BinIfBlock;
import com.rift.coad.change.client.action.workflow.piece.block.BinWhileBlock;
import com.rift.coad.change.rdf.objmapping.client.change.ActionTaskDefinition;
import com.rift.coad.change.rdf.objmapping.client.change.Constants;
import com.rift.coad.change.rdf.objmapping.client.change.Return;
import com.rift.coad.change.rdf.objmapping.client.change.task.Assign;
import com.rift.coad.change.rdf.objmapping.client.change.task.Block;
import com.rift.coad.change.rdf.objmapping.client.change.task.Call;
import com.rift.coad.change.rdf.objmapping.client.change.task.EmbeddedBlock;
import com.rift.coad.change.rdf.objmapping.client.change.task.logic.Else;
import com.rift.coad.change.rdf.objmapping.client.change.task.logic.ElseIf;
import com.rift.coad.change.rdf.objmapping.client.change.task.logic.If;
import com.rift.coad.change.rdf.objmapping.client.change.task.loop.ForEach;
import com.rift.coad.change.rdf.objmapping.client.change.task.loop.ForLoop;
import com.rift.coad.change.rdf.objmapping.client.change.task.loop.WhileLoop;


/**
 * The implementation of the type lookup factory.
 *
 * @author brett chaldecott
 */
public class TypeLookupFactory {
    
    /**
     * This method returns the type identified by the name
     * 
     * @param typeName The name of the type.
     * @param canvas The reference to the canvas.
     * @return parent The parent piece bin.
     * @return The reference to the canvas.
     */
    public static Canvas getType(String typeName, WorkflowCanvas canvas, PieceBin parent) throws Exception {
        try {
            Canvas result = null;
            if (typeName.equals(Icons.CALL)) {
                DroppedPiece drop = new PieceCall();
                drop.setBin(parent);
                result = drop;
            } else if (typeName.equals(Icons.ASSIGN)) {
                DroppedPiece drop = new PieceAssign();
                drop.setBin(parent);
                result = drop;
            } else if (typeName.equals(Icons.EMBEDDED)) {
                DroppedPiece drop = new PieceEmbedded();
                drop.setBin(parent);
                result = drop;
            } else if (typeName.equals(Icons.RETURN)) {
                DroppedPiece drop = new PieceReturn();
                drop.setBin(parent);
                result = drop;
            } else if (typeName.equals(Icons.BLOCK)) {
                PieceBin bin = new BinBlock(canvas);
                bin.setBin(parent);
                result = bin;
            } else if (typeName.equals(Icons.IF)) {
                PieceBin bin = new BinIfBlock(canvas);
                bin.setBin(parent);
                result = bin;
            } else if (typeName.equals(Icons.ELSE_IF)) {
                PieceBin bin = new BinElseIfBlock(canvas);
                bin.setBin(parent);
                result = bin;
            } else if (typeName.equals(Icons.ELSE)) {
                PieceBin bin = new BinElseBlock(canvas);
                bin.setBin(parent);
                result = bin;
            } else if (typeName.equals(Icons.FOR_EACH)) {
                PieceBin bin = new BinForEachBlock(canvas);
                bin.setBin(parent);
                result = bin;
            } else if (typeName.equals(Icons.FOR)) {
                PieceBin bin = new BinForBlock(canvas);
                bin.setBin(parent);
                result = bin;
            } else if (typeName.equals(Icons.WHILE)) {
                PieceBin bin = new BinWhileBlock(canvas);
                bin.setBin(parent);
                result = bin;
            }
            if (result != null) {
                result.addClickHandler(canvas);
                result.addShowContextMenuHandler(parent);
                return result;
            }
            SC.say("Unknown type : " + typeName);
            return null;
        } catch (Exception ex) {
            throw new Exception("Failed to instanciate a type : " + ex.getMessage());
        }
    }


    /**
     * This method returns the type information.
     *
     * @param definition The definition of this task.
     * @param canvas The that this task is attached to.
     * @return parent The parent piece bin.
     * @return The reference to the newly created canvas or null.
     */
    public static Canvas getType(ActionTaskDefinition definition, WorkflowCanvas canvas,
            PieceBin parent) throws Exception {
        Canvas result = null;

        if (definition instanceof Call) {
            DroppedPiece drop = new PieceCall((Call)definition);
            drop.setBin(parent);
            result = drop;
        } else if (definition instanceof Assign) {
            DroppedPiece drop = new PieceAssign((Assign)definition);
            drop.setBin(parent);
            result = drop;
        } else if (definition instanceof EmbeddedBlock) {
            DroppedPiece drop = new PieceEmbedded((EmbeddedBlock)definition);
            drop.setBin(parent);
            result = drop;
        } else if (definition instanceof Return) {
            DroppedPiece drop = new PieceReturn((Return)definition);
            drop.setBin(parent);
            result = drop;
        } else if (definition instanceof If) {
            PieceBin bin = new BinIfBlock(canvas,(If)definition,parent);
            bin.setBin(parent);
            result = bin;
        } else if (definition instanceof ElseIf) {
            PieceBin bin = new BinElseIfBlock(canvas,(ElseIf)definition);
            bin.setBin(parent);
            result = bin;
        } else if (definition instanceof Else) {
            PieceBin bin = new BinElseBlock(canvas,(Else)definition);
            bin.setBin(parent);
            result = bin;
        } else if (definition instanceof ForEach) {
            PieceBin bin = new BinForEachBlock(canvas,(ForEach)definition);
            bin.setBin(parent);
            result = bin;
        } else if (definition instanceof ForLoop) {
            PieceBin bin = new BinForBlock(canvas,(ForLoop)definition);
            bin.setBin(parent);
            result = bin;
        } else if (definition instanceof WhileLoop) {
            PieceBin bin = new BinWhileBlock(canvas,(WhileLoop)definition);
            bin.setBin(parent);
            result = bin;
        } else if (definition instanceof Block) {
            PieceBin bin = new BinBlock(canvas,(Block)definition);
            bin.setBin(parent);
            result = bin;
        }
        if (result != null) {
            result.addClickHandler(canvas);
            result.addShowContextMenuHandler(parent);
            return result;
        }

        throw new Exception("The type is not recognise.");
    }
}
