package dhbw.view_data_conflict;

import dhbw.AlertBox;
import dhbw.datamodel.DataConflictModel;
import dhbw.datamodel.TableModel;
import dhbw.kassensystem_manager_view.KassensystemManagerController;
import dhbw.sa.kassensystem_rest.database.databaseservice.DatabaseService;
import dhbw.sa.kassensystem_rest.database.entity.OrderedItem;
import dhbw.sa.kassensystem_rest.database.entity.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.util.ArrayList;

public class ViewDataConflictController
{
	private DatabaseService databaseService;
	private KassensystemManagerController kmc;
	private int itemID;
	private Stage window;

	private ArrayList<OrderedItem> removedOrderedItems = new ArrayList<>();

	public Label textLabel;
	public Label quantityLabel;

	public TableView table;
	public TableColumn orderedItemIdColumn;
	public TableColumn itemIDColumn;
	public TableColumn itemNameColumn;
	public TableColumn orderIDColumn;
	public TableColumn tableNameColumn;
	public TableColumn timestampColumn;

	public CheckBox printCheckbox;
	public Button approveButton;

	public void initialize(DatabaseService databaseService, KassensystemManagerController kmc, int itemID, Stage window)
	{
		this.databaseService = databaseService;
		this.kmc = kmc;
		this.itemID = itemID;
		this.window = window;

		refreshDisplayedData();

		String text = "Für den Artikel " + databaseService.getItemById(itemID).getName() + " wurde ein negativer " +
				"Warenbestand registriert. Im Folgenden werden alle bestellten Aritkel aufgelistet, die noch nicht " +
				"produziert wurden. Bitte löschen Sie so viele Artikel, bis der negative Warenbestand behoben wurde.";

		textLabel.setText(text);

		window.setOnCloseRequest(e -> {
			e.consume();	//Verhindert Close-Request
		});

	}

	public void deleteOrderedItem(ActionEvent actionEvent)
	{
		DataConflictModel conflictModel = (DataConflictModel) table.getSelectionModel().getSelectedItem();
		int orderedItemID = conflictModel.getOrderedItemID();
		removedOrderedItems.add(databaseService.getOrderedItemById(orderedItemID));
		databaseService.deleteOrderedItem(orderedItemID);
		refreshDisplayedData();
	}

	public void approveButtonPressed(ActionEvent actionEvent)
	{
		if(printCheckbox.isSelected())
		{
			System.out.println("--------------PRINT");
			databaseService.printDataConflict(this.removedOrderedItems);
		}
		window.close();
		kmc.refreshData();
	}

	private void refreshDisplayedData()
	{
		// Angezeigten Warenbestand aktualisieren
		int newQuantity = databaseService.getItemById(itemID).getQuantity();
		quantityLabel.setText(String.valueOf(newQuantity));
		if(newQuantity >= 0)
		{
			approveButton.setDisable(false);
			AlertBox.display("Datenkonflikt behoben", "Der Datenkonflikt wurde behoben. Sie können den " +
					"Dialog nun bestätigen.");
		}
		//Sortierung sichern
		TableColumn sortColumnTable = null;
		TableColumn.SortType sortTypeTable = null;
		if(!table.getSortOrder().isEmpty()) {
			sortColumnTable = (TableColumn) table.getSortOrder().get(0);
			sortTypeTable = sortColumnTable.getSortType();
		}
		// Alle unproduzierten Items abrufen mit der übergebenen itemID
		ObservableList<DataConflictModel> conflictModels = FXCollections.observableArrayList();

		for(OrderedItem o: databaseService.getAllUnproducedOrderedItemsByItemId(itemID)) {
			int orderedItemID = o.getOrderedItemID();
			int itemID = o.getItemID();
			String itemName = databaseService.getItemById(o.getItemID()).getName();
			int orderID = o.getOrderID();
				int tableID = databaseService.getOrderById(o.getOrderID()).getTable();
			String tableName = databaseService.getTableById(tableID).getName();
			String date = databaseService.getOrderById(o.getOrderID()).getDate().toString("dd.MM.yyyy kk:mm:ss");

			conflictModels.add(new DataConflictModel(orderedItemID, itemID, itemName, orderID, tableName, date));
		}
		//Tabelle befüllen
		orderedItemIdColumn.setCellValueFactory(new PropertyValueFactory<DataConflictModel, Integer>("orderedItemID"));
		itemIDColumn.setCellValueFactory(new PropertyValueFactory<DataConflictModel, Integer>("itemID"));
		itemNameColumn.setCellValueFactory(new PropertyValueFactory<DataConflictModel, String>("itemName"));
		orderIDColumn.setCellValueFactory(new PropertyValueFactory<DataConflictModel, Integer>("orderID"));
		tableNameColumn.setCellValueFactory(new PropertyValueFactory<DataConflictModel, String>("tableName"));
		timestampColumn.setCellValueFactory(new PropertyValueFactory<DataConflictModel, String>("date"));

		table.setItems(conflictModels);
		//Gesicherte Sortierung wieder anwenden
		if(sortColumnTable != null) {
			table.getSortOrder().add(sortColumnTable);
			sortColumnTable.setSortType(sortTypeTable);
			sortColumnTable.setSortable(true);
		}
	}
}
