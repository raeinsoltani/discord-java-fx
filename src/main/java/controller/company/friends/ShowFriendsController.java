package controller.company.friends;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.ce.ap.discord.client.business.CommandParser;
import org.ce.ap.discord.common.entity.business.Person;

import static org.ce.ap.discord.common.boot.Bootstrapper.LOGGER;

public class ShowFriendsController {

    @FXML
    private Text emailText;

    @FXML
    private Text phoneNumberText;

    @FXML
    private ImageView profilePicture;

    @FXML
    private Text statusText;

    @FXML
    private Text usernameText;

    public void setContactInfo(Person person){
        usernameText.setText(person.getId());
//        statusText.setText();
        phoneNumberText.setText(person.getPhoneNumber());
        emailText.setText(person.getEmail());
    }

    @FXML
    void block(ActionEvent event) {
        LOGGER.info("Blocking");
        Object response = CommandParser.networkService.block(CommandParser.loginUser.getId(), usernameText.getText());
        if (response != null)
            LOGGER.error("Blocking failed because {}", String.valueOf(response));
        else
            LOGGER.info("Blocked : {}", usernameText.getText());

    }

}
