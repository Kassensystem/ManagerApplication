package dhbw;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("kassensystem_manager_view/kassensystem_manager.fxml"));
        primaryStage.setTitle("Kassensystem-Manager");
        primaryStage.setScene(new Scene(root, 700, 550));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void toggleFullscreen() {
        primaryStage.setFullScreen(!primaryStage.fullScreenProperty().getValue());
    }

    public static boolean isFullscreen() {
        return primaryStage.fullScreenProperty().getValue();
    }

    public static void closeProgram() {
        primaryStage.close();
    }

}
