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
 * LogicCanvas.java
 */


package com.rift.coad.change.client.action.workflow.piece.block.logical;

// import
import com.rift.coad.change.client.action.workflow.ParamInfo;
import com.rift.coad.change.client.action.workflow.ParamInfoFilter;
import com.rift.coad.change.client.action.workflow.PieceBin;
import com.rift.coad.change.rdf.objmapping.client.change.task.logic.LogicalExpression;
import com.rift.coad.change.rdf.objmapping.client.change.task.operator.Operator;
import com.rift.coad.change.rdf.objmapping.client.change.task.operator.OperatorConstants;
import com.rift.coad.change.rdf.objmapping.client.change.task.operator.OperatorLookup;
import com.rift.coad.rdf.objmapping.client.base.DataType;
import com.rift.coad.rdf.objmapping.client.base.VariableNameHolder;
import com.rift.coad.rdf.objmapping.util.client.type.TypeHelper;
import com.rift.coad.rdf.objmapping.util.client.type.TypeManager;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SelectOtherItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import java.util.ArrayList;
import java.util.List;



/**
 * The logic canvas.
 *
 * @author brett chaldecott
 */
public class LogicCanvas extends VLayout {


    /**
     * This object handles the clicks on the expression.
     */
    public class AddExpressionHandler implements ClickHandler {

        public void onClick(ClickEvent event) {
            changed = true;
            try {
                Object value = logicalOperator.getValue();
                String logicalOperatorStr = null;
                if (value != null && !value.toString().equals(NO_OPERATOR_VALUE)) {
                    logicalOperatorStr = value.toString();
                }
                value = variable.getValue();
                if (value == null) {
                    SC.say("The [variable] of the variable must be set");
                    return;
                }
                String variableStr = value.toString();
                value = comparitiveOperator.getValue();
                if (value == null) {
                    SC.say("The [operator] of the variable must be set");
                    return;
                }
                String comparitiveOperatorStr = value.toString();
                value = comparisonValue.getValue();
                if (value == null) {
                    SC.say("The [value] of the variable must be set");
                    return;
                }
                String comparisonValueStr = value.toString();

                if (expressionGrid.getRecords().length > 0 && logicalOperatorStr == null) {
                    SC.say("Must supply a logic operator when not the first argument to an expresssion");
                    return;
                }

                // add the record
                ListGridRecord record = new ListGridRecord();
                if (logicalOperator != null) {
                    record.setAttribute("Logic", logicalOperatorStr);
                }
                record.setAttribute("Variable", variableStr);
                record.setAttribute("Operator", comparitiveOperatorStr);
                record.setAttribute("Value", comparisonValueStr);
                expressionGrid.addData(record);
                logicalOperator.clearValue();
                variable.clearValue();
                comparitiveOperator.clearValue();
                comparitiveOperator.setDisabled(true);
                comparisonValue.clearValue();
                comparisonValue.setDisabled(true);

                update.setDisabled(true);
                delete.setDisabled(true);
            } catch (Exception ex) {
                SC.say("Failed add the expression : " + ex.getMessage());
            }
        }

    }


    /**
     * This object handles the updating of the expression.
     */
    public class UpdateExpressionHandler implements ClickHandler {

        
        /**
         * The default constructor.
         */
        public UpdateExpressionHandler() {
        }
        

        /**
         * The event to handle
         * @param event
         */
        public void onClick(ClickEvent event) {
            changed = true;
            try {
                Object value = logicalOperator.getValue();
                String logicalOperatorStr = null;
                if (value != null && !value.toString().equals(NO_OPERATOR_VALUE)) {
                    logicalOperatorStr = value.toString();
                }
                value = variable.getValue();
                if (value == null) {
                    SC.say("The [variable] of the variable must be set");
                    return;
                }
                String variableStr = value.toString();
                value = comparitiveOperator.getValue();
                if (value == null) {
                    SC.say("The [operator] of the variable must be set");
                    return;
                }
                String comparitiveOperatorStr = value.toString();
                value = comparisonValue.getValue();
                if (value == null) {
                    SC.say("The [value] of the variable must be set");
                    return;
                }
                String comparisonValueStr = value.toString();

                if ((recordIndex != 0) && (logicalOperator == null)) {
                    SC.say("Must supply a logic operator when not the first argument to an expresssion");
                    return;
                }

                ListGridRecord[] records = expressionGrid.getRecords();
                if (logicalOperator != null) {
                    records[recordIndex].setAttribute("Logic", logicalOperatorStr);
                }
                records[recordIndex].setAttribute("Variable", variableStr);
                records[recordIndex].setAttribute("Operator", comparitiveOperatorStr);
                records[recordIndex].setAttribute("Value", comparisonValueStr);
                expressionGrid.setRecords(records);

                logicalOperator.clearValue();
                variable.clearValue();
                comparitiveOperator.clearValue();
                comparitiveOperator.setDisabled(true);
                comparisonValue.clearValue();
                comparisonValue.setDisabled(true);

                update.setDisabled(true);
                delete.setDisabled(true);
            } catch (Exception ex) {
                SC.say("");
            }
        }

    }


    /**
     * This method is called to handle the deleting of a record.
     */
    public class DeleteExpressionHandler implements ClickHandler {


        /**
         * This method is called to delete an expression.
         *
         * @param event The event to handle
         */
        public void onClick(ClickEvent event) {
            changed = true;
            ListGridRecord[] records = expressionGrid.getRecords();
            ListGridRecord[] updatedRecords = new ListGridRecord[records.length - 1];
            int pos = 0;
            for (int index = 0; index < records.length; index++) {
                if (index == recordIndex) {
                    continue;
                } else {
                    updatedRecords[pos] = records[index];
                    pos++;
                }
            }
            expressionGrid.setRecords(updatedRecords);

            logicalOperator.clearValue();
            variable.clearValue();
            comparitiveOperator.clearValue();
            comparitiveOperator.setDisabled(true);
            comparisonValue.clearValue();
            comparisonValue.setDisabled(true);

            update.setDisabled(true);
            delete.setDisabled(true);
        }

    }


    /**
     * This object is called to deal with the change events.
     */
    public class VariableChangedHandler implements ChangedHandler {

        /**
         * The default constructor
         */
        public VariableChangedHandler() {
        }


        /**
         * This method deals with the on change event.
         *
         * @param event The event that caused this change.
         */
        public void onChanged(ChangedEvent event) {
            try {
                if (event.getValue() == null) {
                    comparisonValue.clearValue();
                    comparisonValue.setDisabled(true);

                } else {
                    List<ParamInfo> parameters = parent.listParameters();
                    ParamInfo param = ParamInfoFilter.getParameter(parameters,
                            event.getValue().toString());
                    if (param == null) {
                        comparitiveOperator.clearValue();
                        comparitiveOperator.setDisabled(true);
                        comparisonValue.clearValue();
                        comparisonValue.setDisabled(true);
                    } else {
                        if (param.getType().equals(
                                TypeManager.getTypesForGroup(TypeManager.NONE_SCOPE_GROUPING[0])[4])) {
                            comparitiveOperator.setValueMap(OperatorConstants.EQUAL,
                                OperatorConstants.NOT_EQUAL);
                        } else {
                            comparitiveOperator.setValueMap(OperatorConstants.EQUAL,
                                OperatorConstants.GREATER,OperatorConstants.LESS,
                                OperatorConstants.NOT_EQUAL);
                        }
                        comparitiveOperator.setDisabled(false);

                        comparisonValue.setDisabled(false);
                        comparisonValue.setValueMap(ParamInfoFilter.convertParameterToArray(
                                ParamInfoFilter.filterParameters(parameters, param.getType())));
                    }
                }
            } catch (Exception ex) {
                SC.say("Failed to handle the change event : " + ex.getMessage());
            }
        }

    }

    /**
     * This object is responsible for handling the row click handler
     */
    public class RowClickHandler implements RecordClickHandler {

        
        /**
         * The default constructor
         */
        public RowClickHandler() {
        }


        /**
         * This method deals with the on record click operation.
         *
         * @param event The on record click operation.
         */
        public void onRecordClick(RecordClickEvent event) {
            try {
                Record record = event.getRecord();
                recordIndex = event.getRecordNum();
                logicalOperator.setValue(record.getAttributeAsString("Logic"));
                List<ParamInfo> parameters = parent.listParameters();
                ParamInfo param = ParamInfoFilter.getParameter(parameters,
                                record.getAttributeAsString("Variable"));
                variable.setValue(param.getName());
                if (param.getType().equals(
                        TypeManager.getTypesForGroup(TypeManager.NONE_SCOPE_GROUPING[0])[4])) {
                    comparitiveOperator.setValueMap(OperatorConstants.EQUAL,
                        OperatorConstants.NOT_EQUAL);
                } else {
                    comparitiveOperator.setValueMap(OperatorConstants.EQUAL,
                        OperatorConstants.GREATER,OperatorConstants.LESS,
                        OperatorConstants.NOT_EQUAL);
                }
                comparitiveOperator.setDisabled(false);
                comparitiveOperator.setValue(record.getAttributeAsString("Operator"));

                comparisonValue.setDisabled(false);
                comparisonValue.setValueMap(ParamInfoFilter.convertParameterToArray(
                        ParamInfoFilter.filterParameters(parameters, param.getType())));
                comparisonValue.setValue(record.getAttributeAsString("Value"));

                update.setDisabled(false);
                delete.setDisabled(false);
            } catch (Exception ex) {
                SC.say("Failed to set the record information : " + ex.getMessage());
            }
        }

    }

    private final static String NO_OPERATOR_VALUE = "---No Operator---";

    // private member variables
    private LogicalExpression expression;
    private boolean changed = false;
    private PieceBin parent;
    private DynamicForm form;
    private ListGrid expressionGrid;

    // fields
    private SelectItem logicalOperator;
    private SelectItem variable;
    private SelectItem comparitiveOperator;
    private SelectOtherItem comparisonValue;

    // buttons
    private Button add;
    private Button update;
    private Button delete;

    // none display variables
    private int recordIndex;

    /**
     * The logic
     */
    public LogicCanvas(LogicalExpression expression,PieceBin parent) {
        try {

            setIsGroup(true);
            setGroupTitle("Expression");

            this.expression = expression;
            this.parent = parent;

            form = new DynamicForm();
            
            // parameters
            expressionGrid = new ListGrid();
            expressionGrid.setTitleField("Parameter List");
            expressionGrid.setHeight(100);
            expressionGrid.setAlternateRecordStyles(true);
            expressionGrid.setWidth(410);
            ListGridField logic = new ListGridField("Logic", "logic");
            ListGridField variable = new ListGridField("Variable", "variable");
            ListGridField operator = new ListGridField("Operator", "operator");
            ListGridField value = new ListGridField("Value", "value");
            expressionGrid.setFields(logic,variable,operator,value);
            expressionGrid.setAutoFetchData(false);
            expressionGrid.setShowFilterEditor(false);
            //expressionGrid.setCanDrag(true);
            //expressionGrid.setCanDragRecordsOut(false);
            //expressionGrid.setCanDragReposition(true);
            expressionGrid.setCanDragSelect(true);
            expressionGrid.addRecordClickHandler(new RowClickHandler());


            CanvasItem item = new CanvasItem();
            item.setCanvas(expressionGrid);
            item.setName("Expression");

            logicalOperator = new SelectItem();
            logicalOperator.setName("Logic");
            logicalOperator.setValueMap("---No Operator---",
                    OperatorConstants.AND,OperatorConstants.OR,
                    OperatorConstants.NOT);

            this.variable = new SelectItem();
            this.variable.setName("Variable");
            this.variable.setValueMap(ParamInfoFilter.convertParameterToArray(
                parent.listParameters()));
            this.variable.addChangedHandler(new VariableChangedHandler());

            comparitiveOperator = new SelectItem();
            comparitiveOperator.setName("Comparison");
            comparitiveOperator.setDisabled(true);

            comparisonValue = new SelectOtherItem();
            comparisonValue.setName("Value");
            comparisonValue.setDisabled(true);

            form.setFields(item,logicalOperator,this.variable,comparitiveOperator,
                    comparisonValue);


            addMember(form);


            HLayout buttonLayout = new HLayout();

            add = new Button("Add");
            add.addClickHandler(new AddExpressionHandler());
            buttonLayout.addMember(add);
            update = new Button("Update");
            update.addClickHandler(new UpdateExpressionHandler());
            update.setDisabled(true);
            buttonLayout.addMember(update);
            delete = new Button("Delete");
            delete.setDisabled(true);
            delete.addClickHandler(new DeleteExpressionHandler());
            buttonLayout.addMember(delete);

            addMember(buttonLayout);

            setUpGrid(expression);
        } catch (Exception ex) {
            SC.say("Failed to create the logic canvas : " + ex.getMessage());
        }
    }



    /**
     * The logic expression.
     * 
     * @return
     */
    public LogicalExpression getExpression() throws Exception {
        if (!changed) {
            if (this.expression == null) {
                throw new Exception("The logical expression has not been configured");
            }
            return this.expression;
        }

        List<ParamInfo> parameters = parent.listParameters();
        LogicalExpression result = LogicalExpression.createInstance();
        if (this.expression != null) {
            result.setId(this.expression.getId());
        }
        ListGridRecord[] records = expressionGrid.getRecords();
        for (ListGridRecord record : records) {
            Operator logicOperator = null;
            if (record.getAttribute("Logic") != null) {
                logicOperator = OperatorLookup.get(record.getAttribute("Logic").toString());
                result.addExpression(logicOperator);
            }
            LogicalExpression expression = LogicalExpression.createInstance();
            ParamInfo variableParam = ParamInfoFilter.getParameter(parameters,
                        record.getAttributeAsString("Variable"));
            expression.addExpression(new VariableNameHolder(variableParam.getName()));
            expression.addExpression(
                    OperatorLookup.get(record.getAttributeAsString("Operator")));
            ParamInfo comparisonParam = ParamInfoFilter.getParameter(parameters,
                        record.getAttributeAsString("Value"));
            DataType valueData;
            if (comparisonParam != null) {
                expression.addExpression(
                        new VariableNameHolder(comparisonParam.getName()));
            } else {
                expression.addExpression(
                        TypeHelper.getTypeFromString(variableParam.getType(),
                        record.getAttributeAsString("Value")));
            }
            result.addExpression(expression);
        }

        this.expression = result;
        return result;
    }


    /**
     * The expression
     *
     * @param expression
     */
    private void setUpGrid(LogicalExpression expression) {
        if (expression == null) {
            return;
        }
        DataType[] types = expression.getExpressions();
        try {
            for (int index = 0; index < types.length;index++) {
                Operator operator = null;
                DataType value = expression.getExpressions()[index];
                if (value instanceof Operator) {
                    operator = (Operator)value;
                    value = expression.getExpressions()[++index];
                }
                if (!(value instanceof LogicalExpression)) {
                    SC.say("This expression is incorrectly formatted for the " +
                            "simple expression builder.");
                    return;
                }
                LogicalExpression entry = (LogicalExpression)value;
                 ListGridRecord record = new ListGridRecord();
                if (operator != null) {
                    record.setAttribute("Logic", operator.getName());
                }
                record.setAttribute("Variable", entry.getExpressions()[0].getDataName());
                Operator comparisonOperator = (Operator)entry.getExpressions()[1];
                record.setAttribute("Operator", comparisonOperator.getName());
                value = entry.getExpressions()[2];
                if (value instanceof VariableNameHolder) {
                    record.setAttribute("Value", value.getDataName());
                } else {
                    record.setAttribute("Value", TypeHelper.getValueAsString(value));
                }

                expressionGrid.addData(record);
            }
        } catch (Exception ex) {
            SC.say("The logical expression was incorrectly formated might be to " +
                    "complex for the simple expression builder : " + ex.getMessage());
        }
    }
}
