package controller.company.friends;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.ce.ap.discord.client.business.CommandParser;

import java.io.IOException;

import static org.ce.ap.discord.common.boot.Bootstrapper.LOGGER;

public class ReviewRequestsController {

    @FXML
    private ImageView profilePicture;

    @FXML
    private Text usernameText;

    @FXML
    void acceptButton(ActionEvent event) throws IOException {
        LOGGER.info("Accepting Friend Request");
        Object response = CommandParser.networkService.acceptFriendRequest(usernameText.getText(), CommandParser.loginUser.getId());
        if (response != null)
            LOGGER.error("Accepting Friend Request failed because {}", String.valueOf(response));
        else
            LOGGER.info("Accepted Friend Request : {} sent", usernameText.getText());
    }

    @FXML
    void declineButton(ActionEvent event) {
        LOGGER.info("Rejecting Friend Request");
        Object response = CommandParser.networkService.rejectFriendRequest(usernameText.getText(), CommandParser.loginUser.getId());
        if (response != null)
            LOGGER.error("Rejecting Friend Request failed because {}", String.valueOf(response));
        else
            LOGGER.info("Rejected Friend Request : {} sent", usernameText.getText());
    }

    public void setUsernameText(String username) {
        usernameText.setText(username);
    }
}