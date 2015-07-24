/*
 * CoadunationTypeManagerConsole: The type management console.
 * Copyright (C) 2010  2015 Burntjam
 *
 * ResourceDataHandler.java
 */
package com.rift.coad.catalog.client.entry;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rift.coad.catalog.client.rdf.CatalogEntry;
import com.rift.coad.catalog.client.types.TypeCache;
import com.rift.coad.gwt.lib.client.console.ConsolePanel;
import com.rift.coad.gwt.lib.client.console.PanelFactory;
import com.rift.coad.rdf.objmapping.client.resource.ResourceBase;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
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
 * This object manages the entries in the catalog.
 *
 * @author brett chaldecott
 */
public class EntriesFactory implements PanelFactory {

    public class RemoveEntryHandler implements AsyncCallback {

        private String id;

        /**
         * This method handles the removal of information.
         *
         * @param id The id of the entry to remove.
         */
        public RemoveEntryHandler(String id) {
            this.id = id;
        }


        /**
         * This method is called to deal with failures.
         * 
         * @param caught This method
         */
        public void onFailure(Throwable caught) {
            SC.say("Failed to remove the entry from the catalog : " + caught.getMessage());
        }


        /**
         * This method is called to on success.
         *
         * @param result The result of the call.
         */
        public void onSuccess(Object result) {
            EntriesStore.getInstance().removeEntry(id);
        }

    }

    /**
     * This form provides the ability to manipulate the category entries information.
     */
    public class EntriesForm extends Canvas implements
            com.smartgwt.client.widgets.form.fields.events.ClickHandler, AsyncCallback {

        private CatalogEntry entry;
        private CatalogEntry newEntry;

        private TextItem externalId = null;
        private TextItem name = null;
        private TextItem description = null;
        private ComboBoxItem types = null;
        private ListGrid dependanciesGrid;
        private ButtonItem save;




        /**
         * The default constructor of the entries form.
         */
        public EntriesForm() {
            try {
                this.setWidth100();
                this.setHeight100();

                DynamicForm form = new DynamicForm();

                externalId = new TextItem();
                externalId.setTitle("External ID");
                name = new TextItem();
                name.setTitle("Name");
                description = new TextItem();
                description.setTitle("Description");
                types = new ComboBoxItem();
                types.setTitle("Types");
                List<ResourceBase> resources = TypeCache.getInstance().getResources();
                String[] typeNames = new String[resources.size()];
                try {
                    for (int index = 0; index < resources.size(); index++) {
                        ResourceBase resource = resources.get(index);
                        typeNames[index] = resource.getIdForDataType();
                    }
                } catch (Exception ex) {
                    // ignore the exception
                }
                types.setValueMap(typeNames);

                // parameters
                dependanciesGrid = new ListGrid();
                dependanciesGrid.setTitleField("Dependancies");
                dependanciesGrid.setHeight(100);
                dependanciesGrid.setAlternateRecordStyles(true);
                dependanciesGrid.setSelectionType(SelectionStyle.SIMPLE);
                dependanciesGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);
                dependanciesGrid.setWidth(410);
                ListGridField dependencyId = new ListGridField("ID", "id");
                ListGridField dependencyName = new ListGridField("Name", "name");
                dependanciesGrid.setFields(dependencyId,dependencyName);
                dependanciesGrid.setAutoFetchData(false);
                dependanciesGrid.setShowFilterEditor(false);
                CanvasItem canvasItem = new CanvasItem();
                canvasItem.setCanvas(dependanciesGrid);
                canvasItem.setName("Dependancies");


                // set the button
                save = new ButtonItem("Save");
                save.addClickHandler(this);
                save.setTitle("Save");
                
                // set the fields in this form
                form.setFields(new FormItem[]{
                    externalId,name,description,
                    types,canvasItem,save});

                // add the items to the canvas as a child
                addChild(form);

            } catch (Exception ex) {
                SC.say("Failed to create the entry form : " + ex.getMessage());
            }
        }


        /**
         * This method sets the entry information.
         *
         * @param entry The new entry.
         */
        public void setEntry(CatalogEntry entry) {
            if (entry == null) {
                externalId.clearValue();
                name.clearValue();
                description.clearValue();
                types.clearValue();


            } else {
                externalId.setValue(entry.getExternalId());
                name.setValue(entry.getName());
                description.setValue(entry.getDescription());
                types.setValue(entry.getType().getIdForDataType());
            }
            this.entry = entry;
            setDependancyData(entry);
        }


        /**
         * The event to handle
         *
         * @param event The event.
         */
        public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
            try {
                CatalogEntry entry = this.entry;
                if (this.entry == null) {
                    entry = CatalogEntry.create();

                }
                if (externalId.getValue() != null) {
                    entry.setExternalId(externalId.getValue().toString());
                } else {
                    entry.setExternalId("");
                }
                if (name.getValue() == null) {
                    throw new Exception("The name must be supplied");
                }
                entry.setName(name.getValue().toString());
                if (description.getValue() == null) {
                    throw new Exception("The name must be supplied");
                }
                entry.setDescription(description.getValue().toString());
                entry.setType(TypeCache.getInstance().getResource(types.getValue().toString()));
                ListGridRecord[] records = dependanciesGrid.getSelection();
                List<CatalogEntry> catalogEntries = new ArrayList<CatalogEntry>();
                for (ListGridRecord record : records) {
                    catalogEntries.add(
                            EntriesStore.getInstance().getEntry(
                            record.getAttribute("ID").toString()));
                }
                entry.setDependancies(catalogEntries.toArray(new CatalogEntry[0]));

                if (this.entry == null) {
                    EntriesRPCInterfaceConnector.getService().addEntry(entry, this);
                } else {
                    EntriesRPCInterfaceConnector.getService().updateEntry(entry, this);
                }
                newEntry = entry;
            } catch (Exception ex) {
                SC.say("Failed to save the entry because : " + ex.getMessage());
            }
        }


        /**
         * The failure handler
         *
         * @param caught The exception that was caught.
         */
        public void onFailure(Throwable caught) {
            SC.say("Failed to save the entry because : " + caught.getMessage());
        }


        /**
         * Handle the successfull call.
         * 
         * @param result The result.
         */
        public void onSuccess(Object result) {
            EntriesStore.getInstance().updateEntry(newEntry);
            panel.updateEntry(newEntry);
            this.entry = newEntry;
            newEntry = null;
        }


        /**
         * This method sets the dependancy data
         */
        private void setDependancyData(CatalogEntry entry) {
            List<String> idEntries = EntriesStore.getInstance().getEntryIds();
            List<ListGridRecord> records = new ArrayList<ListGridRecord>();
            List<ListGridRecord> selectedRecords = new ArrayList<ListGridRecord>();
            for (int index = 0; index < idEntries.size(); ++index) {
                CatalogEntry currentEntry =
                        EntriesStore.getInstance().getEntry(idEntries.get(index));
                if ((entry != null) && currentEntry.getId().equals(entry.getId())) {
                    continue;
                }
                ListGridRecord recordGrid = new ListGridRecord();
                recordGrid.setAttribute("ID", currentEntry.getId());
                recordGrid.setAttribute("Name", currentEntry.getName());
                records.add(recordGrid);
                if (entry != null) {
                    for (CatalogEntry depEntry : entry.getDependancies()) {
                        if (depEntry.getId().equals(currentEntry.getId())) {
                            selectedRecords.add(recordGrid);
                            break;
                        }
                    }
                }
            }
            dependanciesGrid.setData(records.toArray(new ListGridRecord[0]));
            for (ListGridRecord selectedRecord : selectedRecords) {
                dependanciesGrid.selectRecord(selectedRecord);
            }
        }
    }

    /**
     * This object manages entries in the catalog.
     */
    public class EntriesPanel extends ConsolePanel implements RecordClickHandler {

        private ListGrid categories;
        private EntriesForm entriesForm;

        /**
         * The default constructor for the entries panel
         */
        public EntriesPanel() {
        }

        /**
         * The panel to view.
         *
         * @return The panel to view.
         */
        @Override
        public Canvas getViewPanel() {
            try {

                HLayout panel = new HLayout();
                panel.setWidth100();
                panel.setHeight100();

                VLayout categoriesPanel = new VLayout();
                categoriesPanel.setWidth(300);
                categoriesPanel.setHeight100();

                categories = new ListGrid();
                categories.setWidth(300);
                categories.setHeight100();
                categories.setSelectionType(SelectionStyle.SIMPLE);
                categories.setSelectionAppearance(SelectionAppearance.CHECKBOX);


                ListGridField idField = new ListGridField("id", "ID");
                ListGridField nameField = new ListGridField("name", "Name");
                ListGridField descField = new ListGridField("description", "Description");

                categories.setFields(new ListGridField[]{
                            idField, nameField, descField});
                categories.setCanResizeFields(true);

                List<String> idEntries = EntriesStore.getInstance().getEntryIds();
                ListGridRecord[] recordGrid = new ListGridRecord[idEntries.size()];
                for (int index = 0; index < idEntries.size(); index++) {
                    CatalogEntry entry = EntriesStore.getInstance().getEntry(idEntries.get(index));
                    if (entry == null) {
                        continue;
                    }
                    recordGrid[index] = new ListGridRecord();
                    recordGrid[index].setAttribute("id", entry.getId());
                    recordGrid[index].setAttribute("name", entry.getName());
                    recordGrid[index].setAttribute("description", entry.getDescription());
                }


                categories.setData(recordGrid);
                categories.addRecordClickHandler(this);

                categoriesPanel.addMember(categories);

                // button panel
                HLayout buttonPanel = new HLayout();
                buttonPanel.setWidth100();

                Button addButton = new Button("Add");
                addButton.addClickHandler(new ClickHandler() {

                    /**
                     * This method is called to handle the click event
                     */
                    public void onClick(ClickEvent event) {
                        entriesForm.setDisabled(false);
                        entriesForm.setEntry(null);
                    }

                });
                buttonPanel.addMember(addButton);

                Button deleteButton = new Button("Delete");
                deleteButton.addClickHandler(new ClickHandler() {

                    /**
                     * This method is called to delete a selected entry.
                     */
                    public void onClick(ClickEvent event) {
                        removeEntry();
                    }

                });
                buttonPanel.addMember(deleteButton);

                categoriesPanel.addMember(buttonPanel);
                
                // add the panel
                panel.addMember(categoriesPanel);


                // the forum that contains all the information
                entriesForm = new EntriesForm();
                entriesForm.setWidth100();
                entriesForm.setHeight100();
                entriesForm.setDisabled(true);
                panel.addMember(entriesForm);

                return panel;
            } catch (Exception ex) {
                SC.say("Failed to create the entries factory : " + ex.getMessage());
                return null;
            }
        }

        /**
         * The name of the panel
         *
         * @return to view.
         */
        @Override
        public String getName() {
            return "Entries Panel";
        }

        
        /**
         * This method handles the record click
         * 
         * @param event The event
         */
        public void onRecordClick(RecordClickEvent event) {
            entriesForm.setDisabled(false);
            entriesForm.setEntry(
                    EntriesStore.getInstance().getEntry(event.getRecord().
                    getAttribute("id")));
        }


        /**
         * This method updates the catalog entry.
         *
         * @param entry The entry to update.
         */
        public void updateEntry(CatalogEntry entry) {
            ListGridRecord[] records = categories.getRecords();
            List<ListGridRecord> recordList = new ArrayList<ListGridRecord>();
            ListGridRecord selectedRecord = null;
            for (ListGridRecord record: records) {
                if (record.getAttribute("id").toString().equals(entry.getId())) {
                    record.setAttribute("name", entry.getName());
                    record.setAttribute("description", entry.getDescription());
                    selectedRecord = record;
                }
                recordList.add(record);
            }
            if (selectedRecord == null) {
                ListGridRecord record = new ListGridRecord();
                record.setAttribute("id", entry.getId());
                record.setAttribute("name", entry.getName());
                record.setAttribute("description", entry.getDescription());
                recordList.add(record);
                selectedRecord = record;
            }
            categories.setRecords(recordList.toArray(new ListGridRecord[0]));
            //categories.selectRecord(selectedRecord);
        }


        /**
         * This method assumes the current selected record is getting deleted.
         */
        public void removeEntry() {
            ListGridRecord[] records = categories.getRecords();
            ListGridRecord[] selectedRecords = categories.getSelection();
            List<ListGridRecord> recordList = new ArrayList<ListGridRecord>();
            for (ListGridRecord record: records) {
                String recordId = record.getAttribute("id").toString();
                boolean found = false;
                for (ListGridRecord selectedRecord : selectedRecords) {
                    String selectedRecordId = selectedRecord.getAttribute("id").toString();
                    if (recordId.equals(selectedRecordId)) {
                        EntriesRPCInterfaceConnector.getService().removeEntry(recordId,
                                new RemoveEntryHandler(recordId));
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    recordList.add(record);
                }
                
            }
            categories.setRecords(recordList.toArray(new ListGridRecord[0]));
        }
    }

    // private member variables
    private String id;
    private EntriesPanel panel;

    /**
     * The default constructor of the entires factory.
     */
    public EntriesFactory() {
    }

    /**
     * This method is called to create a new canvas.
     *
     * @return The reference to the newly created panel
     */
    public Canvas create() {
        panel = new EntriesPanel();
        id = panel.getID();
        return panel;

    }

    /**
     * This method returns the instance if of this object.
     *
     * @return The instance id of the panel after it is created.
     */
    public String getID() {
        return id;
    }

    /**
     * The description of this factory.
     *
     * @return The string containing the description of the factory.
     */
    public String getDescription() {
        return "The Category Entries.";
    }
}
