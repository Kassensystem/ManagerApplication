package dhbw;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox
{
	
	private static boolean answer;
	
	
	public static boolean display(String title, String message) {
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);
		Label label = new Label();
		label.setText(message);
		
		//Create two buttons
		Button yesButton = new Button("Ja");
		Button noButton = new Button("Nein");
		
		yesButton.setOnAction(e -> {
			answer = true;
			window.close();
		});
		yesButton.setOnKeyPressed(e ->
		{
			if(e.getCode() == KeyCode.ENTER)
				yesButton.fire();
		});

		noButton.setOnAction(e -> {
			answer = false;
			window.close();
		});
		noButton.setOnKeyPressed(e ->
		{
			if(e.getCode() == KeyCode.ENTER)
				noButton.fire();
		});
		
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(10,10,10,10));
		HBox buttons = new HBox(10);
		buttons.setAlignment(Pos.CENTER);
		buttons.getChildren().addAll(yesButton, noButton);
		layout.getChildren().addAll(label, buttons);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		
		return answer;
	}
}
