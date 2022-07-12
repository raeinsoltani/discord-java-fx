package controller.company.privateChat;

import controller.company.authentication.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.text.Text;
import org.ce.ap.discord.client.business.CommandParser;

import java.io.IOException;
import static org.ce.ap.discord.common.boot.Bootstrapper.LOGGER;


public class ChatSelectBoxController {

    Parent root;

    @FXML
    private Text ChatID;

    @FXML
    private Text Person1;

    @FXML
    private Text Person2;

    @FXML
    void onClick(ActionEvent event) throws IOException {
        if (Person1.getText().equals(CommandParser.loginUser.getId())){
            PrivateChatController.currentContactPersonID = Person2.getText();
        }
        else{
            PrivateChatController.currentContactPersonID = Person1.getText();
        }
        LOGGER.info("Changed messing person.");
    }

    public void addValue(String ChatID, String Person1, String Person2){
        this.ChatID.setText(ChatID);
        this.Person1.setText(Person1);
        this.Person2.setText(Person2);
    }

}
