package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;

import static models.BBDD.closeConection;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        primaryStage.setTitle("AdDateManager");
        Scene scene = new Scene(root,1000,600);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/resources/style.css").toExternalForm());
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws InterruptedException {
        closeConection();
        Alert alert = new Alert(Alert.AlertType.NONE, "Closing application.... Bye bye");
        alert.show();
        TimeUnit.SECONDS.sleep(1);
        alert.close();
    }


}
