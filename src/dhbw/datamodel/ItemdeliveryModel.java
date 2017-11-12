package dhbw.datamodel;

import javafx.beans.property.SimpleIntegerProperty;

public class ItemdeliveryModel {
    private final SimpleIntegerProperty itemdeliveryID;
    private final SimpleIntegerProperty itemID;
    private final SimpleIntegerProperty quantity;


    public ItemdeliveryModel(int itemdeliveryID, int itemID, int quantity) {
        this.itemdeliveryID = new SimpleIntegerProperty(itemdeliveryID);
        this.itemID = new SimpleIntegerProperty(itemID);
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
