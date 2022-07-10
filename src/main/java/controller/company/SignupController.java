package controller.company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.ce.ap.discord.client.business.network.ClientNetworkServiceManagement;

import java.io.IOException;

import static org.ce.ap.discord.common.boot.Bootstrapper.LOGGER;

public class SignupController {

    private ClientNetworkServiceManagement networkService;

    private Stage stage;

    private Scene scene;

    private Parent root;

    @FXML
    private Text errorText;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    void RegisterButton(ActionEvent event) throws IOException {
        Object response = networkService.register(nameTextField.getText(), usernameTextField.getText(), passwordTextField.getText(),
                emailTextField.getText(), phoneNumberTextField.getText());
        if (response instanceof String) {
            LOGGER.error("Registering failed because {}", String.valueOf(response));
            errorText.setText(String.valueOf(response));
        }
        else{
            LOGGER.info("Registered : {}", response.toString());
            errorText.setStyle("-fx-text-fill: green; -fx-font-size: 12px;");
            errorText.setText("Registered, now please login!");
            try
            {
                Thread.sleep(2000);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
            root = FXMLLoader.load(getClass().getClassLoader().getResource("views/LoginView.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setTitle("User Login");
            stage.setScene(scene);
            stage.show();
        }
    }

}
