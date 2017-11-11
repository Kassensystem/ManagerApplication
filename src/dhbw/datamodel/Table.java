package dhbw.datamodel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Table {
    private final SimpleIntegerProperty tableID;
    private final SimpleStringProperty name;

    public Table(int tableID, String name) {
        this.tableID = new SimpleIntegerProperty(tableID);
        this.name = new SimpleStringProperty(name);
    }

    public int getTableID() {
        return tableID.get();
    }

    public SimpleIntegerProperty tableIDProperty() {
        return tableID;
    }

    public void setTableID(int tableID) {
        this.tableID.set(tableID);
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
}
