package dhbw.datamodel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ItemdeliveryModel {
    private final SimpleIntegerProperty itemdeliveryID;
    private final SimpleIntegerProperty itemID;
    private final SimpleStringProperty itemName;
    private final SimpleIntegerProperty quantity;


    public ItemdeliveryModel(int itemdeliveryID, int itemID, String itemName, int quantity) {
        this.itemdeliveryID = new SimpleIntegerProperty(itemdeliveryID);
        this.itemID = new SimpleIntegerProperty(itemID);
        this.itemName = new SimpleStringProperty(itemName);

        this.quantity = new SimpleIntegerProperty(quantity);
    }

    public int getItemdeliveryID() {
        return itemdeliveryID.get();
    }

    public SimpleIntegerProperty itemdeliveryIDProperty() {
        return itemdeliveryID;
    }

    public void setItemdeliveryID(int itemdeliveryID) {
        this.itemdeliveryID.set(itemdeliveryID);
    }

    public int getItemID() {
        return itemID.get();
    }

    public SimpleIntegerProperty itemIDProperty() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID.set(itemID);
    }

    public String getItemName() {
        return itemName.get();
    }

    public SimpleStringProperty itemNameProperty() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName.set(itemName);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }
}
