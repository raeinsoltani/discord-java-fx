package controller.company.groupChat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.ce.ap.discord.client.business.CommandParser;
import org.ce.ap.discord.common.entity.api.dto.server.PersonAdditionResponseDto;
import org.ce.ap.discord.common.entity.business.discord.DiscordServer;
import org.ce.ap.discord.common.entity.business.discord.Role;
import org.ce.ap.discord.common.entity.business.discord.channel.DiscordChannel;
import org.ce.ap.discord.common.entity.business.enumeration.Ability;
import org.ce.ap.discord.common.entity.business.enumeration.ChannelType;
import org.ce.ap.discord.common.entity.business.enumeration.Reaction;
import org.ce.ap.discord.server.business.discord.exceptions.InvalidIdException;

import java.util.ArrayList;

import static org.ce.ap.discord.common.boot.Bootstrapper.LOGGER;

public class ServerSettingsController {

    @FXML
    private TextField extra;

    @FXML
    private TextField serverCategory;

    @FXML
    private TextField serverID;

    @FXML
    private TextField userID;

    @FXML
    private TextField channelID;

    @FXML
    private Text statusText;

    @FXML
    void addUser(ActionEvent event) {
        String serverID = this.serverID.getText();
        String userID = this.userID.getText();
        if(!serverID.isEmpty() && !userID.isEmpty()){
            if (CommandParser.loginUser == null) {
                LOGGER.error("You are not logged in");
                return;
            }
            Object response = CommandParser.networkService.addServerMember(serverID, CommandParser.loginUser.getId(), userID);
            if (response == null){
                LOGGER.info("User added successfully");
                statusText.setStyle("-fx-background-color: #52FF4D");
                statusText.setText("User added successfully");
            }

            else{
                LOGGER.error("Couldn't add user, because {}", ((PersonAdditionResponseDto) response).getFailureCause());
                statusText.setStyle("-fx-background-color: #FF2035");
                statusText.setText("Couldn't add user!");
            }
        }
        else{
            statusText.setStyle("-fx-background-color: #FF2035");
            statusText.setText("Please fill out ServerID, channelID and then try again!");
        }
    }

    @FXML
    void React(ActionEvent event) {
        String serverID = this.serverID.getText();
        String categoryID = serverCategory.getText();
        String channelID = this.channelID.getText();
        String extra = this.extra.getText();
        String userID = this.userID.getText();
        if (!(serverID.isEmpty() || categoryID.isEmpty() || channelID.isEmpty() || extra.isEmpty() || userID.isEmpty())){
            DiscordChannel channel = CommandParser.networkService.selectChannel(serverID, channelID, channelID);
            try {
                Reaction reaction = Reaction.getByIndex(Integer.parseInt(extra));
                CommandParser.networkService.react(channel, userID, reaction);
            } catch (InvalidIdException e) {
                System.out.println(e.getMessage());
            }
            statusText.setStyle("-fx-background-color: #52FF4D");
            statusText.setText("reaction added successfully");
            this.serverCategory.clear();
            this.channelID.clear();
            this.extra.clear();
            this.userID.clear();
        }
        else{
            statusText.setStyle("-fx-background-color: #FF2035");
            statusText.setText("Please fill out ServerID,  serverCat, channelID, userID (messageID) and extra (Reaction index) and then try again!");
        }
    }

    @FXML
    void addTextChannel(ActionEvent event) {
        String serverID = this.serverID.getText();
        String categoryID = this.serverCategory.getText();
        String channelID = this.channelID.getText();
        if(!(serverID.isEmpty() || categoryID.isEmpty() || channelID.isEmpty())){
            CommandParser.networkService.addChannel(CommandParser.loginUser.getId(), serverID, categoryID, channelID, ChannelType.TEXT);
            statusText.setStyle("-fx-background-color: #52FF4D");
            statusText.setText("text channel added successfully");
            this.serverCategory.clear();
            this.channelID.clear();
        }
        else{
            statusText.setStyle("-fx-background-color: #FF2035");
            statusText.setText("Please fill out ServerID,  serverCat and channelID and then try again!");
        }

    }

    @FXML
    void addVoiceChannel(ActionEvent event) {
        String serverID = this.serverID.getText();
        String categoryID = this.serverCategory.getText();
        String channelID = this.channelID.getText();
        if(!(serverID.isEmpty() || categoryID.isEmpty() || channelID.isEmpty())){
            CommandParser.networkService.addChannel(CommandParser.loginUser.getId(), serverID, categoryID, channelID, ChannelType.VOICE);
            statusText.setStyle("-fx-background-color: #52FF4D");
            statusText.setText("voice channel added successfully");
            this.serverCategory.clear();
            this.channelID.clear();
        }
        else{
            statusText.setStyle("-fx-background-color: #FF2035");
            statusText.setText("Please fill out ServerID,  serverCat and channelID and then try again!");
        }
    }

    @FXML
    void renameServer(ActionEvent event) {
        String serverID = this.serverID.getText();
        String name = extra.getText();
        if(!(serverID.isEmpty() || name.isEmpty())){
            CommandParser.networkService.renameServer(serverID, name);
            LOGGER.info("server renamed successfully");
            statusText.setStyle("-fx-background-color: #52FF4D");
            statusText.setText("server renamed successfully");
            extra.clear();
        }
        else{
            statusText.setStyle("-fx-background-color: #FF2035");
            statusText.setText("Please fill out ServerID and extra and then try again!");
        }
    }

    @FXML
    void setRole(ActionEvent event) {
        String serverID = this.serverID.getText();
        String userID = this.userID.getText();
        if(!serverID.isEmpty() && !userID.isEmpty()){
            DiscordServer server = CommandParser.networkService.selectServer(serverID);
            Role role = new Role();
            role.setAbilities(Ability.getAllAbilities());
            System.out.println("please enter abilities indexes in one line by space");
            String[] indexArr = extra.getText().split(" ");
            ArrayList<Ability> newAbilities = new ArrayList<>();

            for (String s : indexArr) {
                try {
                    newAbilities.add(Ability.getByIndex(Integer.parseInt(s)));
                } catch (InvalidIdException e) {
                    System.out.println(e.getMessage());
                }
            }
            Role newRole = new Role();
            role.setAbilities(newAbilities);
            server.getPersonRoleMap().replace(userID, newRole);
            LOGGER.info("added roles successfully");
            statusText.setStyle("-fx-background-color: #52FF4D");
            statusText.setText("added roles successfully");
            this.extra.clear();
            this.userID.clear();

        }
    }

}
