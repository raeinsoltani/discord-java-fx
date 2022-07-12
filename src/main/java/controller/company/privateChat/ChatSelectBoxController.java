package controller.company.privateChat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ChatSelectBoxController {

    @FXML
    private Text ChatID;

    @FXML
    private Text Person1;

    @FXML
    private Text Person2;

    @FXML
    void onClick(ActionEvent event) {

    }

    public void addValue(String ChatID, String Person1, String Person2){
        this.ChatID.setText(ChatID);
        this.Person1.setText(Person1);
        this.Person2.setText(Person2);
    }

}
