package controller.company.privateChat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.ce.ap.discord.client.business.CommandParser;
import org.ce.ap.discord.common.entity.business.discord.DiscordMessage;
import org.ce.ap.discord.common.entity.business.discord.PrivateChat;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static org.ce.ap.discord.common.boot.Bootstrapper.LOGGER;

public class PrivateChatController implements Initializable {

    private Stage stage;

    private Scene scene;

    private Parent root;

    @FXML
    private ScrollPane ScrollPaneFriends;

    @FXML
    private ScrollPane chatScrollPane;

    @FXML
    private VBox chatVBox;

    @FXML
    private Button friends;

    @FXML
    private ImageView settings;

    @FXML
    private TextField textField;

    @FXML
    private ImageView userProfileImage;

    @FXML
    private VBox vBoxFriends;

    public static String currentContactPersonID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (CommandParser.loginUser == null) {
            LOGGER.error("Login first");
            return;
        }
        List<PrivateChat> response = (List<PrivateChat>) CommandParser.networkService.getPrivateChats(CommandParser.loginUser.getId());
        if(response != null){
            for (int i = 0; i < response.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/ChatSelectBox.fxml"));
                try {
                    AnchorPane anchorPane = fxmlLoader.load();
                    ChatSelectBoxController chatSelectBoxController = fxmlLoader.getController();
                    chatSelectBoxController.addValue(response.get(i).getId(), response.get(i).getPerson1().getId(), response.get(i).getPerson2().getId());
                    vBoxFriends.getChildren().add(anchorPane);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }

        vBoxFriends.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                ScrollPaneFriends.setVvalue((double) newValue);
            }
        });

        chatVBox.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                chatScrollPane.setVvalue((double) newValue);
            }
        });

    }

    @FXML
    void sendButton(ActionEvent event) {
        String messageToSend = textField.getText();
        if(!messageToSend.isEmpty() && currentContactPersonID != null){
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5, 5, 5, 5));

            Text text = new Text(messageToSend);
            TextFlow textFlow = new TextFlow(text);

            textFlow.setStyle("-fx-color: rgb(239,242,255);" +
                    "-fx-background-color: rgb(15,125,242);" +
                    "-fx-background-radius: 20px;");

            textFlow.setPadding(new Insets(5,10,5,10));
            text.setFill(Color.color(0.934, 0.945, 0.996));

            hBox.getChildren().add(textFlow);
            chatVBox.getChildren().add(hBox);

            CommandParser.networkService.sendTextMessage(CommandParser.loginUser, CommandParser.loginUser.getId(), currentContactPersonID, messageToSend);
            textField.clear();
        }
    }

    @FXML
    void clock(ActionEvent event) {
        showOldMessages();
    }

    public void showOldMessages(){
        chatVBox.getChildren().clear();
        LOGGER.info("Showing Old messages");
        List<DiscordMessage> messages = CommandParser.networkService.showMessages(CommandParser.loginUser.getId(), currentContactPersonID);
        for (int i = 0; i < messages.size(); i++) {
            if(messages.get(i).getSender().getId().equals(CommandParser.loginUser.getId())){
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_RIGHT);
                hBox.setPadding(new Insets(5, 5, 5, 5));

                Text text = new Text((String) messages.get(i).getBody());
                TextFlow textFlow = new TextFlow(text);

                textFlow.setStyle("-fx-color: rgb(239,242,255);" +
                        "-fx-background-color: rgb(15,125,242);" +
                        "-fx-background-radius: 20px;");

                textFlow.setPadding(new Insets(5,10,5,10));
                text.setFill(Color.color(0.934, 0.945, 0.996));

                hBox.getChildren().add(textFlow);
                chatVBox.getChildren().add(hBox);
            }
            else {
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_LEFT);
                hBox.setPadding(new Insets(5, 5, 5, 5));

                Text text = new Text((String) messages.get(i).getBody());
                TextFlow textFlow = new TextFlow(text);

                textFlow.setStyle("-fx-background-color: rgb(233,233,235);" +
                        "-fx-background-radius: 20px;");

                textFlow.setPadding(new Insets(5,10,5,10));

                hBox.getChildren().add(textFlow);
                chatVBox.getChildren().add(hBox);
            }
        }
    }


    @FXML
    void privateChat(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getClassLoader().getResource("views/PrivateChatView.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Private Chat");

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void GroupChat(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getClassLoader().getResource("views/GroupChatView.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Group Chat");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void friendsList(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getClassLoader().getResource("views/FriendsView.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Friends List");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void settings(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/ServerSettingsView.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Server Settings Management");
        stage.getIcons().add(new Image("/images/Settings.png"));
        stage.setScene(new Scene(root, 479, 475));
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void profilePicture(ActionEvent event) {

    }
}
