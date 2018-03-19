package dhbw.datamodel;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class OrderedItemModel
{
	private final SimpleStringProperty name;
	private final SimpleDoubleProperty price;
	private final SimpleBooleanProperty paid;
	private final SimpleBooleanProperty produced;
	private final SimpleStringProperty comment;

	public OrderedItemModel(String name, double price, boolean paid, boolean produced, String comment)
	{
		this.name = new SimpleStringProperty(name);
		this.price = new SimpleDoubleProperty(price);
		this.paid = new SimpleBooleanProperty(paid);
		this.produced = new SimpleBooleanProperty(produced);
		this.comment = new SimpleStringProperty(comment);
	}

	public String getName()
	{
		return name.get();
	}

	public SimpleStringProperty nameProperty()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name.set(name);
	}

	public double getPrice()
	{
		return price.get();
	}

	public SimpleDoubleProperty priceProperty()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price.set(price);
	}

	public boolean isPaid()
	{
		return paid.get();
	}

	public SimpleBooleanProperty paidProperty()
	{
		return paid;
	}

	public void setPaid(boolean paid)
	{
		this.paid.set(paid);
	}

	public boolean isProduced()
	{
		return produced.get();
	}

	public SimpleBooleanProperty producedProperty()
	{
		return produced;
	}

	public void setProduced(boolean produced)
	{
		this.produced.set(produced);
	}

	public String getComment()
	{
		return comment.get();
	}

	public SimpleStringProperty commentProperty()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment.set(comment);
	}
}
