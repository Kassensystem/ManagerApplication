package dhbw.view_add_waiter;

import dhbw.AlertBox;
import dhbw.kassensystem_manager_view.KassensystemManagerController;
import dhbw.sa.kassensystem_rest.database.databaseservice.DatabaseService;
import dhbw.sa.kassensystem_rest.database.entity.Waiter;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class AddWaiterController
{
	public DatabaseService databaseService;
	public KassensystemManagerController kmController;
	public boolean update;
	public Stage window;
	public Waiter waiter;

	public TextField addWaiterPrenameField;
	public TextField addWaiterLastnameField;
	public Button waiterBtn;

	public void initialize(DatabaseService databaseService, KassensystemManagerController kmController,
						   boolean update, Waiter waiter, Stage window)
	{
		this.databaseService = databaseService;
		this.kmController = kmController;
		this.update = update;
		if(waiter != null)
			this.waiter = waiter;
		this.window = window;

		addWaiterPrenameField.requestFocus();
		if(update)
		{
			window.setTitle("Bedienung bearbeiten");
			addWaiterPrenameField.setText(this.waiter.getPrename());
			addWaiterLastnameField.setText(this.waiter.getLastname());
			waiterBtn.setText("Bearbeiten");
		}
		else
			window.setTitle("Neue Bedienung anlegen");
	}

	public void waiterButtonPressed(ActionEvent actionEvent)
	{
		String prename = addWaiterPrenameField.getText();
		String lastname = addWaiterLastnameField.getText();


		if (!prename.isEmpty() && !lastname.isEmpty())
		{
			if (update)
			{
				this.waiter.setPrename(prename);
				this.waiter.setLastname(lastname);
				databaseService.updateWaiter(this.waiter.getWaiterID(), waiter);
			}
			else
			{
				databaseService.addWaiter(new Waiter(lastname, prename));
			}

			kmController.refreshData();
			window.close();
		}
		else
		{
			String error = null;

			if(prename.isEmpty())
			{
				error = "Bitte einen Vornamen eingeben.";
				addWaiterPrenameField.requestFocus();
			}
			else if(lastname.isEmpty())
			{
				error = "Bitte einen Nachnamen eingeben.";
				addWaiterLastnameField.requestFocus();
			}

			AlertBox.display("Error", error);
		}
	}

	public void addWaiterKeyPressed(KeyEvent keyEvent)
	{
		if(keyEvent.getCode() == KeyCode.ENTER)
			waiterBtn.fire();
	}
}
