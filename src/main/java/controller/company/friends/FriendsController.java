package controller.company.friends;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.ce.ap.discord.client.business.CommandParser;
import org.ce.ap.discord.common.entity.business.Person;

import java.io.IOException;
import java.util.List;

import static org.ce.ap.discord.common.boot.Bootstrapper.LOGGER;

public class FriendsController {

    Parent root;

    @FXML
    private Button GroupChat;

    @FXML
    private Button friends;

    @FXML
    private ImageView settings;

    @FXML
    private ImageView userProfileImage;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox vBoxFriends;

//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        vBoxFriends.getChildren().clear();
//        vBoxFriends.heightProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//                scrollPane.setVvalue((double) newValue);
//            }
//        });
//
//        if (CommandParser.loginUser != null) {
//            LOGGER.info("Show friends list");
//            List<Person> response = (List<Person>) CommandParser.networkService.getFriendList(CommandParser.loginUser.getId());
//            if (response != null) {
//                for (int i = 0; i < response.size(); i++) {
//                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/ShowFriendsController.fxml"));
//                    AnchorPane anchorPane = null;
//                    try {
//                        anchorPane = fxmlLoader.load();
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                    ShowFriendsController reviewFriendRequestsController = fxmlLoader.getController();
//                    reviewFriendRequestsController.setContactInfo(response.get(i));
//                    vBoxFriends.getChildren().add(anchorPane);
//                }
//            } else {
//                LOGGER.error("Showing friends failed");
//            }
//        } else {
//            LOGGER.error("You are not logged in");
//        }
//    }


    @FXML
    void sendFriendRequest(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getClassLoader().getResource("views/SendFriendRequestView.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Send Friend Request");
//        stage.getIcons().add(new Image("resources/sendMessage@"));
        stage.setScene(new Scene(root, 407, 76));
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void ReviewFriendRequest(ActionEvent event) throws IOException {
        vBoxFriends.getChildren().clear();
        vBoxFriends.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                scrollPane.setVvalue((double) newValue);
            }
        });

        if (CommandParser.loginUser != null) {
            LOGGER.info("Getting friends requests list");
            List<String> response = (List<String>) CommandParser.networkService.getFriendsRequestsList(CommandParser.loginUser.getId());
            if (response != null) {
                for (int i = 0; i < response.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/ReviewFriendRequestsView.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();
                    ReviewRequestsController reviewFriendRequestsController = fxmlLoader.getController();
                    reviewFriendRequestsController.setUsernameText(response.get(i));
                    vBoxFriends.getChildren().add(anchorPane);
                }
            } else {
                LOGGER.error("Showing friends requests failed");
            }
        } else {
            LOGGER.error("You are not logged in");
        }
    }


    @FXML
    void showFriends(ActionEvent event) throws IOException {
        vBoxFriends.getChildren().clear();
        vBoxFriends.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                scrollPane.setVvalue((double) newValue);
            }
        });

        if (CommandParser.loginUser != null) {
            LOGGER.info("Show friends list");
            List<Person> response = (List<Person>) CommandParser.networkService.getFriendList(CommandParser.loginUser.getId());
            if (response != null) {
                for (int i = 0; i < response.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/ShowFriendsView.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();
                    ShowFriendsController reviewFriendRequestsController = fxmlLoader.getController();
                    reviewFriendRequestsController.setContactInfo(response.get(i));
                    vBoxFriends.getChildren().add(anchorPane);
                }
            } else {
                LOGGER.error("Showing friends failed");
            }
        } else {
            LOGGER.error("You are not logged in");
        }


    }

    @FXML
    void blockedContacts(ActionEvent event) throws IOException {
        vBoxFriends.getChildren().clear();
        vBoxFriends.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                scrollPane.setVvalue((double) newValue);
            }
        });

        if (CommandParser .loginUser != null) {
            LOGGER.info("Show blocked list");
            List<String> response = (List<String>) CommandParser.networkService.getBlockList(CommandParser.loginUser.getId());
            if (response != null) {
                for (int i = 0; i < response.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/blockedContactsView.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();
                    BlockedContactsController reviewFriendRequestsController = fxmlLoader.getController();
                    reviewFriendRequestsController.setUsernameText(response.get(i));
                    vBoxFriends.getChildren().add(anchorPane);
                }
            } else {
                LOGGER.error("Showing blocked list failed");
            }
        } else {
            LOGGER.error("You are not logged in");
        }
    }

    @FXML
    void privateChat(ActionEvent event) {

    }
}
