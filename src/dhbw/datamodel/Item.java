package dhbw.datamodel;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Item {
    private final SimpleIntegerProperty itemID;
    private final SimpleStringProperty name;
    private final SimpleDoubleProperty retailprice;
    private final SimpleIntegerProperty quantity;

    public Item(int itemID, String name, Double retailprice, int quantity) {
        this.itemID = new SimpleIntegerProperty(itemID);
        this.name = new SimpleStringProperty(name);
        this.retailprice = new SimpleDoubleProperty(retailprice);
        this.quantity = new SimpleIntegerProperty(quantity);
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

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public double getRetailprice() {
        return retailprice.get();
    }

    public SimpleDoubleProperty retailpriceProperty() {
        return retailprice;
    }

    public void setRetailprice(double retailprice) {
        this.retailprice.set(retailprice);
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
