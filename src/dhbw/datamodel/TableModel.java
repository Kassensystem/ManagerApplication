package dhbw.datamodel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableModel {
    private final SimpleIntegerProperty tableID;
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty seats;

    public TableModel(int tableID, String name, int seats) {
        this.tableID = new SimpleIntegerProperty(tableID);
        this.name = new SimpleStringProperty(name);
        this.seats = new SimpleIntegerProperty(seats);
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

	public int getSeats()
	{
		return seats.get();
	}

	public SimpleIntegerProperty seatsProperty()
	{
		return seats;
	}

	public void setSeats(int seats)
	{
		this.seats.set(seats);
	}
}
