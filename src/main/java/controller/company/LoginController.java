package controller.company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.ce.ap.discord.client.business.CommandParser;
import org.ce.ap.discord.common.entity.business.Person;

import static org.ce.ap.discord.common.boot.Bootstrapper.LOGGER;

public class LoginController {

    @FXML
    private Text errorText;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    void Login(ActionEvent event) {
        Object response = CommandParser.networkService.login(usernameTextField.getText(), passwordTextField.getText());
        if (response instanceof Person) {
            LOGGER.info("Logged in :{}", response.toString());
            CommandParser.loginUser = (Person) response;

        } else {
            LOGGER.error("Logging failed because {}", String.valueOf(response));
            errorText.setText(String.valueOf(response));
        }
    }

}
