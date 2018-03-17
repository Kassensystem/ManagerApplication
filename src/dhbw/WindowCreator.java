package dhbw;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class WindowCreator<T>
{
	private Stage window;
	private String fxmlPath;
	private T controller;

	public <T> WindowCreator(String fxmlPath)
	{
		this.fxmlPath = fxmlPath;

		try
		{
			FXMLLoader loader = new FXMLLoader( getClass().getResource(fxmlPath) );

			this.window = new Stage(StageStyle.DECORATED);
			window.initModality(Modality.APPLICATION_MODAL);
			window.setScene(new Scene((Pane) loader.load()));
			window.show();

			controller = loader.getController();

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public <T> T getController()
	{
		return (T) controller;
	}

	public Stage getWindow()
	{
		return window;
	}

	public void setTitle(String title)
	{
		this.window.setTitle(title);
	}
}
