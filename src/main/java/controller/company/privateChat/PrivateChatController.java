package controller.company.privateChat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class PrivateChatController {

    private Stage stage;

    private Scene scene;

    private Parent root;


    @FXML
    private Button friends;

    @FXML
    private ImageView settings;

    @FXML
    private ImageView userProfileImage;

    @FXML
    void GroupChat(ActionEvent event) {

    }

    @FXML
    void friendsList(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getClassLoader().getResource("views/FriendsView.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Friends List");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void privateChat(ActionEvent event) {

    }

    @FXML
    void settings(ActionEvent event) {

    }

}
