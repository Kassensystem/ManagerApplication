package dhbw;

import dhbw.datamodel.ItemModel;
import dhbw.datamodel.OrderModel;
import dhbw.datamodel.TableModel;
import dhbw.sa.databaseApplication.database.DatabaseService;
import dhbw.sa.databaseApplication.database.entity.Item;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class KassensystemManagerController implements Initializable{


    private DatabaseService databaseService = new DatabaseService();

    public MenuItem printOrderMenu;

    public TableView itemTable;
    public TableColumn itemTableIDColumn;
    public TableColumn itemTableNameColumn;
    public TableColumn itemTableRetailpriceColumn;
    public TableColumn itemTableQuantityColumn;

    public TableView tableTable;
    public TableColumn tableTableIDColumn;
    public TableColumn tableTableNameColumn;

    public TableView orderTable;
    public TableColumn orderTableIDColumn;
    public TableColumn orderTableItemsColumn;
    public TableColumn orderTablePriceColumn;
    public TableColumn orderTableTableColumn;
    public TableColumn orderTableDateColumn;

    public TextField itemNameLabel;
    public TextField itemRetailpriceLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.refreshData();

        /**
         * Todo Einbinden des Threads zum Aktualisieren der Daten
         * Methode: startRefreshThread()
         */

    }

    public void refreshData() {
        DecimalFormat df = new DecimalFormat("#0.00");

        //region Sortierung sichern
        //Items
        TableColumn sortColumnItemTable = null;
        TableColumn.SortType sortTypeItemTable = null;
        if(!itemTable.getSortOrder().isEmpty()) {
            sortColumnItemTable = (TableColumn) itemTable.getSortOrder().get(0);
            sortTypeItemTable = sortColumnItemTable.getSortType();
        }
        //Tables
        TableColumn sortColumnTableTable = null;
        TableColumn.SortType sortTypeTableTable = null;
        if(!tableTable.getSortOrder().isEmpty()) {
            sortColumnTableTable = (TableColumn) tableTable.getSortOrder().get(0);
            sortTypeTableTable = sortColumnTableTable.getSortType();
        }
        //Orders
        TableColumn sortColumnOrderTable = null;
        TableColumn.SortType sortTypeOrderTable = null;
        if(!orderTable.getSortOrder().isEmpty()) {
            sortColumnOrderTable = (TableColumn) orderTable.getSortOrder().get(0);
            sortTypeOrderTable = sortColumnOrderTable.getSortType();
        }
        //endregion

        //region Daten abrufen
        //Items
        ObservableList<ItemModel> itemData = FXCollections.observableArrayList();
        ArrayList<dhbw.sa.databaseApplication.database.entity.Item> allItems = databaseService.getAllItems();
        for(dhbw.sa.databaseApplication.database.entity.Item i: allItems) {
            if(i.isAvailable())
                itemData.add(new ItemModel(i.getItemID(), i.getName(), i.getRetailprice(), i.getQuantity()));
        }
        //Tables
        ObservableList<TableModel> tableData = FXCollections.observableArrayList();
        ArrayList<dhbw.sa.databaseApplication.database.entity.Table> allTables = databaseService.getAllTables();
        for(dhbw.sa.databaseApplication.database.entity.Table t: allTables) {
            if(t.isAvailable())
                tableData.add(new TableModel(t.getTableID(), t.getName()));
        }
        //Orders
        ObservableList<OrderModel> orderData = FXCollections.observableArrayList();
        ArrayList<dhbw.sa.databaseApplication.database.entity.Order> allOrders = databaseService.getAllOrders();
        for(dhbw.sa.databaseApplication.database.entity.Order o: allOrders) {
            String items = "";
            for(dhbw.sa.databaseApplication.database.entity.Item i: o.getItems(allItems)) {
                items += i.getName() + "\n";
            }
            String tableName = o.getTable(allTables).getName();

            orderData.add(new OrderModel(o.getOrderID(), items, o.getPrice(), o.getDate().toString("dd.MM.yyyy kk:mm:ss"), tableName));
        }
        //endregion

        //region Tabellen befüllen
        //Tabelle ItemModel befüllen
        itemTableIDColumn.setCellValueFactory(new PropertyValueFactory<ItemModel, Integer>("itemID"));
        itemTableNameColumn.setCellValueFactory(new PropertyValueFactory<ItemModel, String>("name"));
        itemTableRetailpriceColumn.setCellValueFactory(new PropertyValueFactory<ItemModel, Double>("retailprice"));
        itemTableQuantityColumn.setCellValueFactory(new PropertyValueFactory<ItemModel, Integer>("quantity"));

        //Tabelle TableModel befüllen
        tableTableIDColumn.setCellValueFactory(new PropertyValueFactory<TableModel, Integer>("tableID"));
        tableTableNameColumn.setCellValueFactory(new PropertyValueFactory<TableModel, String>("name"));

        //Tabelle OrderModel befüllen
        orderTableIDColumn.setCellValueFactory(new PropertyValueFactory<OrderModel, Integer>("orderID"));
        orderTableItemsColumn.setCellValueFactory(new PropertyValueFactory<OrderModel, String>("items"));
        orderTablePriceColumn.setCellValueFactory(new PropertyValueFactory<OrderModel, Double>("price"));
        orderTableTableColumn.setCellValueFactory(new PropertyValueFactory<OrderModel, String>("table"));
        orderTableDateColumn.setCellValueFactory(new PropertyValueFactory<OrderModel, String>("date"));

        itemTable.setItems(itemData);
        tableTable.setItems(tableData);
        orderTable.setItems(orderData);
        //endregion

        //region Gesicherte Sortierung wieder anwenden
        //Items
        if(sortColumnItemTable != null) {
            itemTable.getSortOrder().add(sortColumnItemTable);
            sortColumnItemTable.setSortType(sortTypeItemTable);
            sortColumnItemTable.setSortable(true);
        }
        //Tables
        if(sortColumnTableTable != null) {
            tableTable.getSortOrder().add(sortColumnTableTable);
            sortColumnTableTable.setSortType(sortTypeTableTable);
            sortColumnTableTable.setSortable(true);
        }
        //Orders
        if(sortColumnOrderTable != null) {
            orderTable.getSortOrder().add(sortColumnOrderTable);
            sortColumnOrderTable.setSortType(sortTypeOrderTable);
            sortColumnOrderTable.setSortable(true);
        }
        //endregion

    }

    public void closeProgram(ActionEvent actionEvent) {
    }

    public void toggleFullscreen(ActionEvent actionEvent) {
    }

    public void openAbout(ActionEvent actionEvent) {
    }


    //region Tabellen bearbeiten/hinzufügen/ausdrucken
    /*******Order********/
    public void printOrder(ActionEvent actionEvent) {
        Object item = orderTable.getSelectionModel().getSelectedItem();
        int orderID = ((OrderModel) item).getOrderID();
        databaseService.printOrder(orderID);
    }

    public void deleteOrder(ActionEvent actionEvent) {
        Object item = orderTable.getSelectionModel().getSelectedItem();
        int orderID = ((OrderModel) item).getOrderID();
        databaseService.deleteOrder(orderID);
        this.refreshData();
    }

    public void editOrder(ActionEvent actionEvent) {

    }


    /*******Item******/
    public void deleteItem(ActionEvent actionEvent) {
        Object item = itemTable.getSelectionModel().getSelectedItem();
        int itemID = ((ItemModel) item).getItemID();
        databaseService.deleteItem(itemID);
        this.refreshData();
    }

    public void editItem(ActionEvent actionEvent) {

    }

    public void addItem(ActionEvent actionEvent) {
        if(itemRetailpriceLabel.getCharacters() != null && itemNameLabel.getCharacters() != null) {
            String name = itemNameLabel.getCharacters().toString();
            Double retailprice = Double.parseDouble(itemRetailpriceLabel.getCharacters().toString());
            Item newItem = new Item(name, retailprice, 30, true);
        }
    }

    /*******Table*******/
    public void deleteTable(ActionEvent actionEvent) {
        Object item = tableTable.getSelectionModel().getSelectedItem();
        int tableID = ((TableModel) item).getTableID();
        databaseService.deleteTable(tableID);
        this.refreshData();
    }

    public void editTable(ActionEvent actionEvent) {

    }
    //endregion


    public void onItemTabSelection(Event event) {
    }

    public void onOrderTabSelection(Event event) {
    }

    public void onTableTabSelection(Event event) {
    }


    /**
     * Methoden für späteres Einbinden des Refresh-Threads
     */
    public Thread refreshThread;

    private static final class Lock { }
    private final Object lock = new Lock();
    private void startRefreshThread() {
        //Tabelleninhalt alle Sekunde aktualisieren
        if (refreshThread == null) {
            refreshThread = new Thread(() -> {
                synchronized (lock) {
                    while (true) {
                        try {
                            Thread.sleep(1000); // sleep 0.5 secs
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Platform.runLater(() -> {   // Ensure data is updated on JavaFX thread
                            this.refreshData();
                        });
                    }
                }
            });
            refreshThread.setDaemon(true);
            refreshThread.start();
        }
    }

    public void continueRefreshing(WindowEvent windowEvent) {
        System.out.println("rechtsklick aus");
        //continueRefreshing();
    }
    private void continueRefreshing() {
        synchronized (lock) {
            lock.notify();
        }
    }

    public void pauseRefreshing(WindowEvent windowEvent) {
        System.out.println("rechtsklick an");
        //pauseRefreshing();
    }
    private void pauseRefreshing() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
