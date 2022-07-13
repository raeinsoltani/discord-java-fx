package controller.company.profile;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.ce.ap.discord.client.business.CommandParser;

import java.net.URL;
import java.util.ResourceBundle;

import static org.ce.ap.discord.common.boot.Bootstrapper.LOGGER;

public class ProfileController implements Initializable {

    @FXML
    private Text usernameText;

    @FXML
    private Text nameText;

    @FXML
    private Text phoneNumberText;

    @FXML
    private Text emailText;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Text statusText;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameText.setText(CommandParser.loginUser.getId());
        nameText.setText(CommandParser.loginUser.getName());
        phoneNumberText.setText(CommandParser.loginUser.getPhoneNumber());
        emailText.setText(CommandParser.loginUser.getEmail());
    }

    @FXML
    void changePassword(ActionEvent event) {
        String newPwd = passwordTextField.getText();
        if (!newPwd.isEmpty()){
            if (CommandParser.loginUser != null) {
                LOGGER.info("Change Password");
                boolean response = CommandParser.networkService.changePassword(CommandParser.loginUser.getId(), newPwd);
                if (response) {
                    LOGGER.info("Password changed successfully");
                    statusText.setStyle("-fx-background-color: #52FF4D");
                    statusText.setText("Password changed successfully");
                } else {
                    LOGGER.error("Password changing failed");
                    statusText.setText("Password changing failed");
                }
            } else {
                LOGGER.error("You are not logged in");
            }
        }
        else{
            statusText.setText("please enter a valid password then press the button");
        }
    }
}
