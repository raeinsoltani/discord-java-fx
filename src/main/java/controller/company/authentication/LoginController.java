package controller.company.authentication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.ce.ap.discord.client.business.CommandParser;
import org.ce.ap.discord.common.entity.business.Person;

import java.io.IOException;

import static org.ce.ap.discord.common.boot.Bootstrapper.LOGGER;

public class LoginController {

    private Stage stage;

    private Scene scene;

    private Parent root;

    @FXML
    private Text errorText;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    void Login(ActionEvent event) throws IOException {
        Object response = CommandParser.networkService.login(usernameTextField.getText(), passwordTextField.getText());
        if (response instanceof Person) {
            LOGGER.info("Logged in :{}", response.toString());
            CommandParser.loginUser = (Person) response;
            root = FXMLLoader.load(getClass().getClassLoader().getResource("views/PrivateChatView.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setTitle("Private Chat");
            stage.setScene(scene);
            stage.show();
        } else {
            LOGGER.error("Logging failed because {}", String.valueOf(response));
            errorText.setText(String.valueOf(response));
        }
    }

    @FXML
    void Back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/LoginOrSignup.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Welcome to our Application!");
        stage.setScene(scene);
        stage.show();
    }


}
