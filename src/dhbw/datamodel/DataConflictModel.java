package dhbw.datamodel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DataConflictModel
{
	private final SimpleIntegerProperty orderedItemID;
	private final SimpleIntegerProperty itemID;
	private final SimpleStringProperty itemName;
	private final SimpleIntegerProperty orderID;
	private final SimpleStringProperty tableName;
	private final SimpleStringProperty date;

	public DataConflictModel(int orderedItemID, int itemID, String itemName, int orderID, String tableName, String date)
	{
		this.orderedItemID = new SimpleIntegerProperty(orderedItemID);
		this.itemID = new SimpleIntegerProperty(itemID);
		this.itemName = new SimpleStringProperty(itemName);
		this.orderID = new SimpleIntegerProperty(orderID);
		this.tableName = new SimpleStringProperty(tableName);
		this.date = new SimpleStringProperty(date);
	}

	public int getItemID()
	{
		return itemID.get();
	}

	public SimpleIntegerProperty itemIDProperty()
	{
		return itemID;
	}

	public void setItemID(int itemID)
	{
		this.itemID.set(itemID);
	}

	public String getItemName()
	{
		return itemName.get();
	}

	public SimpleStringProperty itemNameProperty()
	{
		return itemName;
	}

	public void setItemName(String itemName)
	{
		this.itemName.set(itemName);
	}

	public int getOrderID()
	{
		return orderID.get();
	}

	public SimpleIntegerProperty orderIDProperty()
	{
		return orderID;
	}

	public void setOrderID(int orderID)
	{
		this.orderID.set(orderID);
	}

	public String getTableName()
	{
		return tableName.get();
	}

	public SimpleStringProperty tableNameProperty()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName.set(tableName);
	}

	public String getDate()
	{
		return date.get();
	}

	public SimpleStringProperty dateProperty()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date.set(date);
	}

	public int getOrderedItemID()
	{
		return orderedItemID.get();
	}

	public SimpleIntegerProperty orderedItemIDProperty()
	{
		return orderedItemID;
	}

	public void setOrderedItemID(int orderedItemID)
	{
		this.orderedItemID.set(orderedItemID);
	}
}
