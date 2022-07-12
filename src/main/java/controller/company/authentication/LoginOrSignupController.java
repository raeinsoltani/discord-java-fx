package controller.company.authentication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginOrSignupController {

    private Stage stage;

    private Scene scene;

    private Parent root;

    @FXML
    void SignupButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getClassLoader().getResource("views/SignupView.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("User Signup");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void loginButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getClassLoader().getResource("views/LoginView.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("User Login");
        stage.setScene(scene);
        stage.show();
    }

}
