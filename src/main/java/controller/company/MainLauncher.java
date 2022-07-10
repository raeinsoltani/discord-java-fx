package controller.company;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainLauncher extends Application {

    private static Stage primaryStageObj;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStageObj = stage;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/LoginOrSignup.fxml"));
        stage.setTitle("Welcome to our Application!");
        stage.setResizable(false);
        Scene scene = new Scene(root, 600, 400);
        scene.setRoot(root);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> Platform.exit());
    }

    public static Stage getPrimaryStageObj() {
        return primaryStageObj;
    }

    public static void setPrimaryStageObj(Stage primaryStageObj) {
        MainLauncher.primaryStageObj = primaryStageObj;
    }
}
