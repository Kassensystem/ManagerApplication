package dhbw;

import dhbw.datamodel.*;
import dhbw.sa.kassensystem_rest.database.DatabaseService;
import dhbw.sa.kassensystem_rest.database.entity.Item;
import dhbw.sa.kassensystem_rest.database.entity.Itemdelivery;
import dhbw.sa.kassensystem_rest.database.entity.Order;
import dhbw.sa.kassensystem_rest.database.entity.Table;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class KassensystemManagerController implements Initializable{

    private String version = "1.0";

    public Tab itemTab;
    public Tab orderTab;
    public Tab tableTab;
    public Tab itemdeliveryTab;

    private DatabaseService databaseService = new DatabaseService();


    /****Order-Tab****/
    public TableView orderTable;
    public TableColumn orderTableIDColumn;
    public TableColumn orderTableItemsColumn;
    public TableColumn orderTablePriceColumn;
    public TableColumn orderTableTableColumn;
    public TableColumn orderTableDateColumn;


    /****Item-Tab****/
    public SplitPane itemSplitpane;

    public TableView itemTable;
    public TableColumn itemTableIDColumn;
    public TableColumn itemTableNameColumn;
    public TableColumn itemTableRetailpriceColumn;
    public TableColumn itemTableQuantityColumn;

    public TextField itemNameLabel;
    public TextField itemRetailpriceLabel;
    public TextField itemQuantityLabel;

    public Label itemIDLabel;
    public TextField editItemNameLabel;
    public TextField editItemRetailpriceLabel;

    /****Itemdelivery-Tab****/
    public TableView itemdeliveryTable;
    public TableColumn itemdeliveryTableIDColumn;
    public TableColumn itemdeliveryTableItemIDColumn;
    public TableColumn itemdeliveryTableItemNameColumn;
    public TableColumn itemdeliveryTableQuantityColumn;

    public Label addItemdeliveryItemIDLabel;
    public Label addItemdeliveryItemNameLabel;
    public TextField addItemdeliveryQuantityField;

    /****Table-Tab****/
    public SplitPane tableSplitPane;

    public TableView tableTable;
    public TableColumn tableTableIDColumn;
    public TableColumn tableTableNameColumn;

    public TextField editTableNameField;
    public Label editTableDLabel;

    public TextField addTableNameField;

    private ArrayList<Order> allOrders;
    private ArrayList<Item> allItems;
    private ArrayList<Table> allTables;
    private ArrayList<Itemdelivery> allItemdeliveries;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler());

        System.out.println("----------------------\nINITIALIZE");
        this.refreshData();
        System.out.println("----------------------");
        /**
         * Todo Einbinden des Threads zum Aktualisieren der Daten
         * Methode: startRefreshThread()
         */
    }

    public void refreshData() {
        DecimalFormat df = new DecimalFormat("#0.00");

        refreshOrderData();
        refreshItemData();
        refreshTableData();
        refreshItemdeliveryData();

    }

    private void refreshOrderData() {
        //Sortierung sichern
        TableColumn sortColumnOrderTable = null;
        TableColumn.SortType sortTypeOrderTable = null;
        if (!orderTable.getSortOrder().isEmpty()) {
            sortColumnOrderTable = (TableColumn) orderTable.getSortOrder().get(0);
            sortTypeOrderTable = sortColumnOrderTable.getSortType();
        }
        //Daten abrufen
        ObservableList<OrderModel> orderData = FXCollections.observableArrayList();
        allOrders = databaseService.getAllOrders();
        allItems = databaseService.getAllItems();
        allTables = databaseService.getAllTables();
        for (Order o : allOrders) {
            String items = "";
            for (Item i : o.getItems(allItems)) {
                items += i.getName() + "\n";
            }
            String tableName = o.getTable(allTables).getName();

            orderData.add(new OrderModel(o.getOrderID(), items, o.getPrice(), o.getDate().toString("dd.MM.yyyy kk:mm:ss"), tableName));
        }
        //Tabelle befüllen
        orderTableIDColumn.setCellValueFactory(new PropertyValueFactory<OrderModel, Integer>("orderID"));
        orderTableItemsColumn.setCellValueFactory(new PropertyValueFactory<OrderModel, String>("items"));
        orderTablePriceColumn.setCellValueFactory(new PropertyValueFactory<OrderModel, Double>("price"));
        orderTableTableColumn.setCellValueFactory(new PropertyValueFactory<OrderModel, String>("table"));
        orderTableDateColumn.setCellValueFactory(new PropertyValueFactory<OrderModel, String>("date"));

        orderTable.setItems(orderData);
        //Gesicherte Sortierung wieder anwenden
        if (sortColumnOrderTable != null) {
            orderTable.getSortOrder().add(sortColumnOrderTable);
            sortColumnOrderTable.setSortType(sortTypeOrderTable);
            sortColumnOrderTable.setSortable(true);
        }
    }

    private void refreshItemData() {
        //Sortierung sichern
        TableColumn sortColumnItemTable = null;
        TableColumn.SortType sortTypeItemTable = null;
        if(!itemTable.getSortOrder().isEmpty()) {
            sortColumnItemTable = (TableColumn) itemTable.getSortOrder().get(0);
            sortTypeItemTable = sortColumnItemTable.getSortType();
        }
        //Daten abrufen
        ObservableList<ItemModel> itemData = FXCollections.observableArrayList();
        allItems = databaseService.getAllItems();
        for(Item i: allItems) {
            if(i.isAvailable())
                itemData.add(new ItemModel(i.getItemID(), i.getName(), i.getRetailprice(), i.getQuantity()));
        }
        //Tabelle befüllen
        itemTableIDColumn.setCellValueFactory(new PropertyValueFactory<ItemModel, Integer>("itemID"));
        itemTableNameColumn.setCellValueFactory(new PropertyValueFactory<ItemModel, String>("name"));
        itemTableRetailpriceColumn.setCellValueFactory(new PropertyValueFactory<ItemModel, Double>("retailprice"));
        itemTableQuantityColumn.setCellValueFactory(new PropertyValueFactory<ItemModel, Integer>("quantity"));

        itemTable.setItems(itemData);
        //Gesicherte Sortierung wieder anwenden
        if(sortColumnItemTable != null) {
            itemTable.getSortOrder().add(sortColumnItemTable);
            sortColumnItemTable.setSortType(sortTypeItemTable);
            sortColumnItemTable.setSortable(true);
        }

    }

    private void refreshTableData() {
        //Sortierung sichern
        TableColumn sortColumnTableTable = null;
        TableColumn.SortType sortTypeTableTable = null;
        if(!tableTable.getSortOrder().isEmpty()) {
            sortColumnTableTable = (TableColumn) tableTable.getSortOrder().get(0);
            sortTypeTableTable = sortColumnTableTable.getSortType();
        }
        //Daten abrufen
        ObservableList<TableModel> tableData = FXCollections.observableArrayList();
        allTables = databaseService.getAllTables();
        for(Table t: allTables) {
            if(t.isAvailable())
                tableData.add(new TableModel(t.getTableID(), t.getName()));
        }
        //Tabelle befüllen
        tableTableIDColumn.setCellValueFactory(new PropertyValueFactory<TableModel, Integer>("tableID"));
        tableTableNameColumn.setCellValueFactory(new PropertyValueFactory<TableModel, String>("name"));

        tableTable.setItems(tableData);
        //Gesicherte Sortierung wieder anwenden
        if(sortColumnTableTable != null) {
            tableTable.getSortOrder().add(sortColumnTableTable);
            sortColumnTableTable.setSortType(sortTypeTableTable);
            sortColumnTableTable.setSortable(true);
        }
    }

    private void refreshItemdeliveryData() {
        //Sortierung sichern
        TableColumn sortColumnItemdeliveryTable = null;
        TableColumn.SortType sortTypeItemdeliveryTable = null;
        if(!itemdeliveryTable.getSortOrder().isEmpty()) {
            sortColumnItemdeliveryTable = (TableColumn) itemdeliveryTable.getSortOrder().get(0);
            sortTypeItemdeliveryTable = sortColumnItemdeliveryTable.getSortType();
        }
        //Daten abrufen
        ObservableList<ItemdeliveryModel> itemdeliveryData = FXCollections.observableArrayList();
        allItemdeliveries = databaseService.getAllItemdeliveries();
        allItems = databaseService.getAllItems();
        Itemdelivery ide;
        Item item = new Item();
        for(int i = 0; i < allItemdeliveries.size(); i++) {
            ide = allItemdeliveries.get(i);
            int itemdeliveryID = ide.getItemID();
            for(Item it: allItems) {
                if(it.getItemID() == itemdeliveryID)
                    item = it;
            }
            if (item.isAvailable()) {
                String itemName = item.getName();
                itemdeliveryData.add(new ItemdeliveryModel(ide.getItemdeliveryID(), ide.getItemID(), itemName, ide.getQuantity()));
            }
        }
        //Tabelle befüllen
        itemdeliveryTableIDColumn.setCellValueFactory(new PropertyValueFactory<ItemdeliveryModel, Integer>("itemdeliveryID"));
        itemdeliveryTableItemIDColumn.setCellValueFactory(new PropertyValueFactory<ItemdeliveryModel, Integer>("itemID"));
        itemdeliveryTableItemNameColumn.setCellValueFactory(new PropertyValueFactory<ItemdeliveryModel, String>("itemName"));
        itemdeliveryTableQuantityColumn.setCellValueFactory(new PropertyValueFactory<ItemdeliveryModel, Integer>("quantity"));

        itemdeliveryTable.setItems(itemdeliveryData);
        //Gesicherte Sortierung wieder anwenden
        if(sortColumnItemdeliveryTable != null) {
            itemdeliveryTable.getSortOrder().add(sortColumnItemdeliveryTable);
            sortColumnItemdeliveryTable.setSortType(sortTypeItemdeliveryTable);
            sortColumnItemdeliveryTable.setSortable(true);
        }

    }


    public void closeProgram(ActionEvent actionEvent) {
        Main.closeProgram();
    }

    public void toggleFullscreen(ActionEvent actionEvent) {
        Main.toggleFullscreen();

        if(Main.isFullscreen()) {
            setMaximizedLayout();
        }
        else {
            setSmallLayout();
        }
    }

    private void setSmallLayout() {
        if(itemSplitpane != null && tableSplitPane != null) {
            itemSplitpane.setDividerPositions(0.65);
            tableSplitPane.setDividerPositions(0.65);
        }
    }

    private void setMaximizedLayout() {
        itemSplitpane.setDividerPositions(0.8);
        tableSplitPane.setDividerPositions(0.8);
    }

    private void setSplitPaneLayout() {
        boolean maximized = Main.getPrimaryStage().isMaximized();
        if(maximized)
            setMaximizedLayout();
        else
            setSmallLayout();
    }

    public void openAbout(ActionEvent actionEvent) {
        AlertBox.display("About",
                "Kassensystem-Manager Verwaltungstool\n" +
                        "Version " + version +"\n" +
                        "by Daniel Schifano und Marvin Mai\n" +
                        "DHBW-Stuttgart Jahrgang 2015\n");
    }


    /*******Order********/
    public void onOrderTabSelection(Event event) {
        if (orderTab.isSelected()) {
            System.out.println("LOG: Order-Tab selected");
            this.refreshOrderData();
            setSplitPaneLayout();
        }
    }

    public void printOrder(ActionEvent actionEvent) {
        Object item = orderTable.getSelectionModel().getSelectedItem();
        int orderID = ((OrderModel) item).getOrderID();
        databaseService.printOrderById(orderID);
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
    public void onItemTabSelection(Event event) {
        if (itemTab.isSelected()) {
            System.out.println("LOG: Item-Tab selected");
            this.refreshItemData();
            setSplitPaneLayout();
            itemIDLabel.setText("");
            editItemNameLabel.clear();
            editItemRetailpriceLabel.clear();

            addItemdeliveryItemIDLabel.setText("");
            addItemdeliveryItemNameLabel.setText("");
            addItemdeliveryQuantityField.clear();
        }
    }

    public void selectItem(MouseEvent mouseEvent) {
        Object i = itemTable.getSelectionModel().getSelectedItem();
        if (i != null) {
            int itemID = ((ItemModel) i).getItemID();
            Item item = databaseService.getItemById(itemID);
            //Felder für Bearbeiten eines Items
            double retailprice = item.getRetailprice();
            itemIDLabel.setText("" + item.getItemID());
            editItemNameLabel.setText(item.getName());
            editItemRetailpriceLabel.setText("" + retailprice);

            //Felder für hinzufügen einer Itemdelivery
            addItemdeliveryItemIDLabel.setText("" + item.getItemID());
            addItemdeliveryItemNameLabel.setText(item.getName());
            addItemdeliveryQuantityField.clear();
        }
    }

    public void deleteItem(ActionEvent actionEvent) {
        Object item = itemTable.getSelectionModel().getSelectedItem();
        int itemID = ((ItemModel) item).getItemID();
        databaseService.deleteItem(itemID);

        this.refreshItemData();
    }

    public void editItem(ActionEvent actionEvent) {
        String name = editItemNameLabel.getText();
        String retailprice = editItemRetailpriceLabel.getText();
        String itemIDtext = itemIDLabel.getText();

        if (!retailprice.isEmpty() && !itemIDtext.isEmpty()) {
            int itemID = Integer.parseInt(itemIDtext);
            Item oldItem = databaseService.getItemById(itemID);
            oldItem.setAvailable(false);

            double price = Double.parseDouble(retailprice);
            int quantity = oldItem.getQuantity();
            Item newItem = new Item(name, Double.parseDouble(retailprice), quantity, true);
            databaseService.addItem(newItem);

            databaseService.updateItem(itemID, oldItem);

            this.refreshItemData();
            editItemNameLabel.clear();
            editItemRetailpriceLabel.clear();
            itemIDLabel.setText("");
        }
        else if(itemIDtext.isEmpty())
            AlertBox.display("Error", "Bitte zum Bearbeiten einen Artikel auswählen.");
        else
            AlertBox.display("Error", "Bitte einen Preis eingeben.");

    }

    public void addItem(ActionEvent actionEvent) {
        String name = itemNameLabel.getCharacters().toString();
        String retailprice = itemRetailpriceLabel.getCharacters().toString();
        String quantity = itemQuantityLabel.getCharacters().toString();

        if(!retailprice.isEmpty() && !quantity.isEmpty()) {
            Item newItem = new Item(name, Double.parseDouble(retailprice), Integer.parseInt(quantity), true);
            databaseService.addItem(newItem);

            this.refreshData();
            itemRetailpriceLabel.clear();
            itemNameLabel.clear();
            itemQuantityLabel.clear();
        }
        else if(retailprice.isEmpty())
            AlertBox.display("Error", "Bitte einen Preis eingeben.");
        else
            AlertBox.display("Error", "Bitte eine Anzahl für den neuen Wareneingang eingeben.");
    }

    /*******Table*******/
    public void onTableTabSelection(Event event) {
        if(tableTab.isSelected()) {
            System.out.println("LOG: Table-Tab selected");
            addTableNameField.clear();
            editTableNameField.clear();
            editTableDLabel.setText("");
            this.refreshTableData();
        }
    }

    public void selectTable(MouseEvent mouseEvent) {
        Object i = tableTable.getSelectionModel().getSelectedItem();
        if (i != null) {
            int tableID = ((TableModel) i).getTableID();
            Table table = databaseService.getTableById(tableID);
            //Felder für bearbeiten eines Tisches
            editTableDLabel.setText("" + table.getTableID());
            editTableNameField.setText(table.getName());
        }
    }

    public void deleteTable(ActionEvent actionEvent) {
        Object item = tableTable.getSelectionModel().getSelectedItem();
        int tableID = ((TableModel) item).getTableID();
        databaseService.deleteTable(tableID);
        this.refreshTableData();
    }

    public void editTable(ActionEvent actionEvent) {
        String name = editTableNameField.getText();
        String tableIDText = editTableDLabel.getText();


        if (!tableIDText.isEmpty()) {
            int tableID = Integer.parseInt(tableIDText);
            Table oldTable = databaseService.getTableById(tableID);
            oldTable.setAvailable(false);

            Table newTable = new Table(name, true);
            databaseService.addTable(newTable);

            databaseService.updateTable(tableID, oldTable);

            this.refreshTableData();
            editTableNameField.clear();
            editTableDLabel.setText("");
        }
        else
            AlertBox.display("Error", "Bitte einen Tisch zum Bearbeiten auswählen.");
    }

    public void addTable(ActionEvent actionEvent) {
        String name = addTableNameField.getCharacters().toString();

        Table newTable = new Table(name, true);
        databaseService.addTable(newTable);

        this.refreshTableData();
        addTableNameField.clear();
    }

    /*******Itemdelivery********/
    public void onItemdeliveryTabSelection(Event event) {
        if (itemdeliveryTab.isSelected()) {
            System.out.println("LOG: Itemdelivery-Tab selected");
            this.refreshItemdeliveryData();
        }
    }

    public void addItemdelivery(ActionEvent actionEvent) {
        String itemIdText = addItemdeliveryItemIDLabel.getText();
        String quantityText = addItemdeliveryQuantityField.getText();


        if(!quantityText.isEmpty()) {
            int itemID = Integer.parseInt(itemIdText);
            int quantity = Integer.parseInt(quantityText);
            Itemdelivery itemdelivery = new Itemdelivery(itemID, quantity);
            databaseService.addItemdelivery(itemdelivery);
            this.refreshData();
            addItemdeliveryQuantityField.clear();
        }
        else
            AlertBox.display("Error", "Bitte eine Anzahl für den neuen Wareneingang eingeben.");
    }

    public void deleteItemdelivery(ActionEvent actionEvent) {
        Object item = itemdeliveryTable.getSelectionModel().getSelectedItem();
        int itemdeliveryID = ((ItemdeliveryModel) item).getItemdeliveryID();
        databaseService.deleteItemdelivery(itemdeliveryID);
        this.refreshItemdeliveryData();
    }



    /*
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
