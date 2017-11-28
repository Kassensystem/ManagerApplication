package dhbw;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
	
	public static void display(String title, String message) {
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);	//Block userinteraction, until alertbox is closed
		window.setTitle(title);
		window.setMinWidth(270);
		
		Label label = new Label();
		label.setText(message);
		
		Button closeButton = new Button("OK");
		closeButton.setOnAction(e -> window.close());
		
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(10, 10, 10, 10));
		layout.getChildren().addAll(label, closeButton);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();		//don't go back to caller until window is closed
		
	}
}
