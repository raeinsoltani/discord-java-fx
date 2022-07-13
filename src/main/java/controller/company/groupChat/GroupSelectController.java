package controller.company.groupChat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class GroupSelectController {

    @FXML
    private Text serverID;

    @FXML
    void onClick(ActionEvent event) {
        GroupChatController.currentServerID = serverID.getText();
    }

    public void setServerID(String serverID){
        this.serverID.setText(serverID);
    }
}
