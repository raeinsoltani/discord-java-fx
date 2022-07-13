package controller.company.groupChat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.ce.ap.discord.client.business.CommandParser;
import org.ce.ap.discord.common.entity.business.discord.Category;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static org.ce.ap.discord.common.boot.Bootstrapper.LOGGER;

public class GroupChatController implements Initializable {

    private Stage stage;

    private Scene scene;

    private Parent root;

    @FXML
    private ScrollPane ScrollPaneServers;

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
    private VBox vBoxServers;

    @FXML
    private ScrollPane channelScrollPane;

    @FXML
    private VBox channelVBox;


    public static String currentServerID;

    public static String currentChannelID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (CommandParser.loginUser != null) {
            LOGGER.info("Getting servers list:");
            List<String> response = (List<String>) CommandParser.networkService.getServersList(CommandParser.loginUser.getId());
            if (response != null) {
                for (int i = 0; i < response.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/GroupSelectBox.fxml"));
                    try {
                        AnchorPane anchorPane = fxmlLoader.load();
                        GroupSelectController groupSelectController = fxmlLoader.getController();
                        groupSelectController.setServerID(response.get(i));
                        vBoxServers.getChildren().add(anchorPane);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                LOGGER.error("Showing servers list failed");
            }
        } else {
            LOGGER.error("You are not logged in");
        }

        vBoxServers.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                ScrollPaneServers.setVvalue((double) newValue);
            }
        });

        chatVBox.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                chatScrollPane.setVvalue((double) newValue);
            }
        });

        channelVBox.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                channelScrollPane.setVvalue((double) newValue);
            }
        });
    }

    @FXML
    void GroupChat(ActionEvent event) {

    }

    @FXML
    void addServer(ActionEvent event) {
        String serverName = textField.getText();
        if(!serverName.isEmpty()){
            if (CommandParser.networkService.addServer(CommandParser.loginUser.getId(), serverName)) {
                LOGGER.info("Server Added Successfully");
            } else {
                LOGGER.error("Couldn't make Server");
            }
        }
    }

    @FXML
    void clock(ActionEvent event) {
        channelVBox.getChildren().clear();
        List<Category> channels = CommandParser.networkService.showServerChannels(currentServerID);
        for (Category category : channels) {
            for(String channelID :category.getChannels().keySet()){
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/ChannelSelectBox.fxml"));
                try {
                    AnchorPane anchorPane = fxmlLoader.load();
                    ChannelSelectController channelSelectController = fxmlLoader.getController();
                    channelSelectController.setChannelID(channelID);
                    channelVBox.getChildren().add(anchorPane);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }

        chatVBox.getChildren().clear();
//        if(currentChannelID != null){
//            LOGGER.info("Showing Old messages");
//            List<DiscordMessage> messages = CommandParser.networkService.showMessages(CommandParser.loginUser.getId(), currentContactPersonID);
//            for (int i = 0; i < messages.size(); i++) {
//                if(messages.get(i).getSender().getId().equals(CommandParser.loginUser.getId())){
//                    HBox hBox = new HBox();
//                    hBox.setAlignment(Pos.CENTER_RIGHT);
//                    hBox.setPadding(new Insets(5, 5, 5, 5));
//
//                    Text text = new Text((String) messages.get(i).getBody());
//                    TextFlow textFlow = new TextFlow(text);
//
//                    textFlow.setStyle("-fx-color: rgb(239,242,255);" +
//                            "-fx-background-color: rgb(15,125,242);" +
//                            "-fx-background-radius: 20px;");
//
//                    textFlow.setPadding(new Insets(5,10,5,10));
//                    text.setFill(Color.color(0.934, 0.945, 0.996));
//
//                    hBox.getChildren().add(textFlow);
//                    chatVBox.getChildren().add(hBox);
//                }
//                else {
//                    HBox hBox = new HBox();
//                    hBox.setAlignment(Pos.CENTER_LEFT);
//                    hBox.setPadding(new Insets(5, 5, 5, 5));
//
//                    Text text = new Text((String) messages.get(i).getBody());
//                    TextFlow textFlow = new TextFlow(text);
//
//                    textFlow.setStyle("-fx-background-color: rgb(233,233,235);" +
//                            "-fx-background-radius: 20px;");
//
//                    textFlow.setPadding(new Insets(5,10,5,10));
//
//                    hBox.getChildren().add(textFlow);
//                    chatVBox.getChildren().add(hBox);
//                }
//            }
//        }
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
    void groupChatSettings(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/ServerSettingsView.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Server Settings Management");
        stage.getIcons().add(new Image("/images/Settings.png"));
        stage.setScene(new Scene(root, 479, 475));
        stage.setResizable(false);
        stage.show();
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
    void sendButton(ActionEvent event) {

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
