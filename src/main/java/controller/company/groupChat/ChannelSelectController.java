package controller.company.groupChat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ChannelSelectController {
    @FXML
    private Text ChannelID;

    @FXML
    void onClick(ActionEvent event) {
        GroupChatController.currentChannelID = ChannelID.getText();
    }

    public void setChannelID(String channelID){
        this.ChannelID.setText(channelID);
    }

}
