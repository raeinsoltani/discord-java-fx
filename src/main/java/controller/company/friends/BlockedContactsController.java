package controller.company.friends;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.ce.ap.discord.client.business.CommandParser;

import static org.ce.ap.discord.common.boot.Bootstrapper.LOGGER;

public class BlockedContactsController {

    @FXML
    private ImageView profilePicture;

    @FXML
    private Text usernameText;

    @FXML
    void removeBlockButton(ActionEvent event) {
        LOGGER.info("Unblocking");
        Object response = CommandParser.networkService.unblock(CommandParser.loginUser.getId(), usernameText.getText());
        if (response != null)
            LOGGER.error("Unblocking failed because {}", String.valueOf(response));
        else
            LOGGER.info("Unblocked : {}", usernameText.getText());
    }

    public void setUsernameText(String username) {
        usernameText.setText(username);
    }
}
