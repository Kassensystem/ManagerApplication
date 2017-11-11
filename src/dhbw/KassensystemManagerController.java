package dhbw;

import dhbw.datamodel.Item;
import dhbw.datamodel.Order;
import dhbw.datamodel.Table;
import dhbw.sa.databaseApplication.database.DatabaseService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class KassensystemManagerController implements Initializable{

    public MenuItem printOrderMenu;

    private DatabaseService databaseService = new DatabaseService();

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.refreshData();

    }

    public void refreshData() {
        DecimalFormat df = new DecimalFormat("#0.00");
        //Daten holen
        //Items
        ObservableList<Item> itemData = FXCollections.observableArrayList();
        ArrayList<dhbw.sa.databaseApplication.database.entity.Item> allItems = databaseService.getAllItems();
        for(dhbw.sa.databaseApplication.database.entity.Item i: allItems) {
            itemData.add(new Item(i.getItemID(), i.getName(), i.getRetailprice(), i.getQuantity()));
        }
        //Tables
        ObservableList<Table> tableData = FXCollections.observableArrayList();
        ArrayList<dhbw.sa.databaseApplication.database.entity.Table> allTables = databaseService.getAllTables();
        for(dhbw.sa.databaseApplication.database.entity.Table t: allTables) {
            tableData.add(new Table(t.getTableID(), t.getName()));
        }
        //Orders
        ObservableList<Order> orderData = FXCollections.observableArrayList();
        ArrayList<dhbw.sa.databaseApplication.database.entity.Order> allOrders = databaseService.getAllOrders();
        for(dhbw.sa.databaseApplication.database.entity.Order o: allOrders) {
            String items = "";
            for(dhbw.sa.databaseApplication.database.entity.Item i: o.getItems(allItems)) {
                items += i.getName() + "\n";
            }
            String tableName = o.getTable(allTables).getName();

            orderData.add(new Order(o.getOrderID(), items, o.getPrice(), o.getDate().toString("dd.MM.yyyy kk:mm:ss"), tableName));
        }


        //Tabelle Item befüllen
        itemTableIDColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("itemID"));
        itemTableNameColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        itemTableRetailpriceColumn.setCellValueFactory(new PropertyValueFactory<Item, Double>("retailprice"));
        itemTableQuantityColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("quantity"));

        //Tabelle Table befüllen
        tableTableIDColumn.setCellValueFactory(new PropertyValueFactory<Table, Integer>("tableID"));
        tableTableNameColumn.setCellValueFactory(new PropertyValueFactory<Table, String>("name"));

        //Tabelle Order befüllen
        orderTableIDColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderID"));
        orderTableItemsColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("items"));
        orderTablePriceColumn.setCellValueFactory(new PropertyValueFactory<Order, Double>("price"));
        orderTableTableColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("table"));
        orderTableDateColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("date"));

        itemTable.setItems(itemData);
        tableTable.setItems(tableData);
        orderTable.setItems(orderData);
    }

    public void refreshData(ActionEvent actionEvent) {
        this.refreshData();
    }

    public void pushData(ActionEvent actionEvent) {
    }

    public void closeProgram(ActionEvent actionEvent) {
    }

    public void toggleFullscreen(ActionEvent actionEvent) {
    }

    public void openAbout(ActionEvent actionEvent) {
    }

    public void printOrder(ActionEvent actionEvent) {
        /**
         * TODO Wie erkennen, welcher Eintrag ausgewählt wurde???
         */
    }
}
