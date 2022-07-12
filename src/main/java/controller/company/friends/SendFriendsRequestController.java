package controller.company.friends;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.ce.ap.discord.client.business.CommandParser;

import static org.ce.ap.discord.common.boot.Bootstrapper.LOGGER;

public class SendFriendsRequestController {

    @FXML
    private Text statusText;

    @FXML
    private TextField textField;

    @FXML
    void onButton(ActionEvent event) {
        if(!(textField.getText() == null || textField.getText().trim().isEmpty()));{
            Object response = CommandParser.networkService.sendFriendRequest(CommandParser.loginUser.getId(), textField.getText());
            if (response != null){
                LOGGER.error("Sending Friend Request failed because {}", String.valueOf(response));
                statusText.setText(String.valueOf(response));
            }
            else{
                LOGGER.info("Sent Friend Request To : {}", textField.getText());
                statusText.setStyle("-fx-fill: #40FF96");
                statusText.setText("Friend Request sent Successfully.");
            }
        }
    }

}
