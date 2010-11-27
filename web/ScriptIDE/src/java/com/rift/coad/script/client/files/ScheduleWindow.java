/*
 * ScriptIDE: The coadunation ide for editing scripts in coadunation.
 * Copyright (C) 2010  Rift IT Contracting
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
 * TimerManager.java
 */


// files
package com.rift.coad.script.client.files;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rift.coad.script.broker.client.rdf.RDFScriptInfo;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VStack;
import java.util.LinkedHashMap;

/**
 * The cron window information.
 *
 * @author brett chaldecott
 */
public class ScheduleWindow {

    /**
     * This object handles the saving of 
     */
    public class SaveHandler implements AsyncCallback {

        /**
         * The default constructor
         */
        public SaveHandler() {
        }



        /**
         * The on failure method.
         *
         * @param caught The exception that was caught.
         */
        public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        /**
         * Deal with the sucessfull events.
         * @param result
         */
        public void onSuccess(Object result) {
            // ignore
        }

    }


    /**
     * This method is called to delete the handler
     */
    public class DeleteHandler implements AsyncCallback {

        private ScheduleCanvas scheduleCanvas;

        /**
         * The constructor for the delete handler
         */
        public DeleteHandler(ScheduleCanvas scheduleCanvas) {
            this.scheduleCanvas = scheduleCanvas;
        }


        /**
         * This method is called to deal with failures.
         *
         * @param caught The exception that was caught.
         */
        public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
        }


        /**
         * This method is called to deal with sucessful events.
         *
         * @param result The result of the call.
         */
        public void onSuccess(Object result) {
            scheduleCanvas.clearFormValues();
        }

    }


    /**
     * This object represents the
     */
    public class ScheduleCanvas extends VStack implements AsyncCallback {

        private DynamicForm form;
        private SelectItem month;
        private SelectItem day;
        private SelectItem hour;
        private SelectItem minute;
        private CheckboxItem recure;
        private ButtonItem save;
        private ButtonItem clear;

        /**
         * The default constructor
         */
        public ScheduleCanvas() {
            try {
                    form = new DynamicForm();

                    month = new SelectItem();
                    month.setName("Month");
                    LinkedHashMap<String,String> months = new LinkedHashMap<String,String>();
                    months.put("-1", "*");
                    months.put("0", "January");
                    months.put("1", "Feburary");
                    months.put("2", "March");
                    months.put("3", "April");
                    months.put("4", "May");
                    months.put("5", "June");
                    months.put("6", "July");
                    months.put("7", "August");
                    months.put("8", "September");
                    months.put("9", "October");
                    months.put("10", "November");
                    months.put("11", "December");
                    month.setValueMap(months);

                    day = new SelectItem();
                    day.setName("Day");
                    LinkedHashMap<String,String> days = new LinkedHashMap<String,String>();
                    days.put("-1", "*");
                    days.put("1", "1");
                    days.put("2", "2");
                    days.put("3", "3");
                    days.put("4", "4");
                    days.put("5", "5");
                    days.put("6", "6");
                    days.put("7", "7");
                    days.put("8", "8");
                    days.put("9", "9");
                    days.put("10", "10");
                    days.put("11", "11");
                    days.put("12", "12");
                    days.put("13", "13");
                    days.put("14", "14");
                    days.put("15", "15");
                    days.put("16", "16");
                    days.put("17", "17");
                    days.put("18", "18");
                    days.put("19", "19");
                    days.put("20", "20");
                    days.put("21", "21");
                    days.put("22", "22");
                    days.put("23", "23");
                    days.put("24", "24");
                    days.put("25", "25");
                    days.put("26", "26");
                    days.put("27", "27");
                    days.put("28", "28");
                    days.put("29", "29");
                    days.put("30", "30");
                    days.put("31", "31");
                    day.setValueMap(days);

                    hour = new SelectItem();
                    hour.setName("Hour");
                    LinkedHashMap<String,String> hours = new LinkedHashMap<String,String>();
                    hours.put("-1", "*");
                    hours.put("1", "1");
                    hours.put("2", "2");
                    hours.put("3", "3");
                    hours.put("4", "4");
                    hours.put("5", "5");
                    hours.put("6", "6");
                    hours.put("7", "7");
                    hours.put("8", "8");
                    hours.put("9", "9");
                    hours.put("10", "10");
                    hours.put("11", "11");
                    hours.put("12", "12");
                    hours.put("13", "13");
                    hours.put("14", "14");
                    hours.put("15", "15");
                    hours.put("16", "16");
                    hours.put("17", "17");
                    hours.put("18", "18");
                    hours.put("19", "19");
                    hours.put("20", "20");
                    hours.put("21", "21");
                    hours.put("22", "22");
                    hours.put("23", "23");
                    hours.put("24", "24");
                    hour.setValueMap(hours);

                    minute = new SelectItem();
                    minute.setName("Minute");
                    LinkedHashMap<String,String> minutes = new LinkedHashMap<String,String>();
                    minutes.put("-1", "*");
                    minutes.put("0", "0");
                    minutes.put("1", "1");
                    minutes.put("2", "2");
                    minutes.put("3", "3");
                    minutes.put("4", "4");
                    minutes.put("5", "5");
                    minutes.put("6", "6");
                    minutes.put("7", "7");
                    minutes.put("8", "8");
                    minutes.put("9", "9");
                    minutes.put("10", "10");
                    minutes.put("11", "11");
                    minutes.put("12", "12");
                    minutes.put("13", "13");
                    minutes.put("14", "14");
                    minutes.put("15", "15");
                    minutes.put("16", "16");
                    minutes.put("17", "17");
                    minutes.put("18", "18");
                    minutes.put("19", "19");
                    minutes.put("20", "20");
                    minutes.put("21", "21");
                    minutes.put("22", "22");
                    minutes.put("23", "23");
                    minutes.put("24", "24");
                    minutes.put("25", "25");
                    minutes.put("26", "26");
                    minutes.put("27", "27");
                    minutes.put("28", "28");
                    minutes.put("29", "29");
                    minutes.put("30", "30");
                    minutes.put("31", "31");
                    minutes.put("32", "32");
                    minutes.put("33", "33");
                    minutes.put("34", "34");
                    minutes.put("35", "35");
                    minutes.put("36", "36");
                    minutes.put("37", "37");
                    minutes.put("38", "38");
                    minutes.put("39", "39");
                    minutes.put("40", "40");
                    minutes.put("41", "41");
                    minutes.put("42", "42");
                    minutes.put("43", "43");
                    minutes.put("44", "44");
                    minutes.put("45", "45");
                    minutes.put("46", "46");
                    minutes.put("47", "47");
                    minutes.put("48", "48");
                    minutes.put("49", "49");
                    minutes.put("50", "50");
                    minutes.put("51", "51");
                    minutes.put("52", "52");
                    minutes.put("53", "53");
                    minutes.put("54", "54");
                    minutes.put("55", "55");
                    minutes.put("56", "56");
                    minutes.put("57", "57");
                    minutes.put("58", "58");
                    minutes.put("59", "59");
                    minute.setValueMap(minutes);

                    recure = new CheckboxItem();
                    recure.setName("Recure");

                    save = new ButtonItem();
                    save.setName("Save");
                    save.addClickHandler(new ClickHandler() {

                        public void onClick(ClickEvent event) {
                            try {
                                if (month.getValue() == null || day.getValue() == null ||
                                        hour.getValue() == null || minute.getValue() == null) {
                                    SC.say("Must select values for month, day, hour and minute");
                                    return;
                                }
                                String jndi = "";
                                if (FileSuffixLookup.getSuffixForName(file).equals(Constants.FILE_SUFFIXES[0])) {
                                    jndi = Constants.GROOVY_TIMER_JNDI;
                                } else if (FileSuffixLookup.getSuffixForName(file).equals(Constants.FILE_SUFFIXES[1])) {
                                    jndi = Constants.JYTHON_TIMER_JNDI;
                                }
                                boolean recuring = false;
                                if (recure.getValue() != null) {
                                    recuring = (Boolean)recure.getValue();
                                }
                                TimerManagerConnector.getService().register(jndi, Integer.parseInt(month.getValue().toString()),
                                        Integer.parseInt(day.getValue().toString()), Integer.parseInt(hour.getValue().toString()),
                                        Integer.parseInt(minute.getValue().toString()), file, recuring, new SaveHandler());
                            } catch (Exception ex) {
                                SC.say("Failed to register : " + ex.getMessage());
                            }
                        }

                    });


                    clear = new ButtonItem();
                    clear.setName("Clear");
                    clear.addClickHandler(new ClickHandler() {

                        public void onClick(ClickEvent event) {

                            handleClear();

                        }

                    });


                    form.setFields(month,day,hour,minute,recure,save,clear);
                    addMember(form);
                    this.setDisabled(true);
            } catch (Exception ex) {
                SC.say("Failed to instantiate the cron window : " + ex.getMessage());
            }
        }


        /**
         * This method deals with the failure.
         *
         * @param caught The exception that was caught.
         */
        public void onFailure(Throwable caught) {
            SC.say("Failed to retrieve the cron information.");
        }


        /**
         * The result of the change.
         *
         * @param result The result.
         */
        public void onSuccess(Object result) {
            TimerEvent[] events = (TimerEvent[])result;
            for (TimerEvent event : events) {
                if (event.getEvent().equals(file)) {
                    month.setValue("" + event.getMonth());
                    day.setValue("" + event.getDay());
                    hour.setValue("" + event.getHour());
                    minute.setValue("" + event.getMinute());
                    recure.setValue(event.getRecure());
                    break;
                }
            }
            this.setDisabled(false);
        }


        /**
         * This method is called to clear the schedule event for this object
         */
        private void handleClear() {
            TimerManagerConnector.getService().deleteEvent(file, new DeleteHandler(this));
        }

        /**
         * This method is called to clear the value
         */
        public void clearFormValues() {
           month.clearValue();
           day.clearValue();
           hour.clearValue();
           minute.clearValue();
           recure.clearValue();
        }

    }


    // private member variabes
    private FileEditorFactory factory;
    private String file;
    private ScheduleCanvas scheduleCanvas;
    private Window win;


    /**
     * The constructor for a new cron window.
     *
     * @param scriptInfo
     * @param factory
     */
    public ScheduleWindow(RDFScriptInfo scriptInfo, FileEditorFactory factory) {
        this.factory = factory;
        if (scriptInfo.getScope().length() > 0) {
            file = scriptInfo.getScope().replaceAll("[.]", "/") + "/" + scriptInfo.getFileName();
        } else {
            file = scriptInfo.getFileName();
        }

        scheduleCanvas = new ScheduleCanvas();
        TimerManagerConnector.getService().listEvents(scheduleCanvas);
    }


    /**
     * This method creates a new window.
     */
    public void createWindow() {
        if (win == null) {
            win = new Window();
            win.setTitle("Cron: " + file);
            win.setKeepInParentRect(true);
            win.setWidth(300);
            win.setHeight(250);
            int windowTop = 40;
            int windowLeft = factory.getPanel().getWidth()- (win.getWidth() + 20);
            win.setLeft(windowLeft);
            win.setTop(windowTop);
            win.setCanDragReposition(true);
            win.setCanDragResize(true);
            win.setMembersMargin(5);

            win.addItem(scheduleCanvas);

            factory.getPanel().addChild(win);
        }
        win.draw();
    }
}
