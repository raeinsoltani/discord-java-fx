package org.ce.ap.discord.client.business;

import org.ce.ap.discord.client.business.display.DisplayService;
import org.ce.ap.discord.client.business.display.entity.PrivateChatMenu;
import org.ce.ap.discord.client.business.display.entity.ServerMenu;
import org.ce.ap.discord.client.business.display.impl.DisplayServiceImpl;
import org.ce.ap.discord.client.business.network.ClientNetworkServiceManagement;
import org.ce.ap.discord.client.business.network.impl.ClientNetworkServiceManagementImpl;
import org.ce.ap.discord.common.boot.InitializingBean;
import org.ce.ap.discord.common.entity.api.dto.server.PersonAdditionResponseDto;
import org.ce.ap.discord.common.entity.boot.ApplicationContext;
import org.ce.ap.discord.common.entity.business.Person;
import org.ce.ap.discord.common.entity.business.discord.*;
import org.ce.ap.discord.common.entity.business.discord.channel.DiscordChannel;
import org.ce.ap.discord.common.entity.business.discord.channel.TextChannel;
import org.ce.ap.discord.common.entity.business.enumeration.Ability;
import org.ce.ap.discord.common.entity.business.enumeration.ChannelType;
import org.ce.ap.discord.common.entity.business.enumeration.Reaction;
import org.ce.ap.discord.common.util.logger.Logger;
import org.ce.ap.discord.server.business.discord.exceptions.InvalidIdException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Parham Ahmady, Farhad Aman, Arian Qazvini, Negar Anabestani
 * @since 6/27/2022
 */
@SuppressWarnings("unchecked")
public class CommandParser implements InitializingBean {

    private static final Logger LOGGER = Logger.getLogger(CommandParser.class.getName());

    public static ClientNetworkServiceManagement networkService;
    public static DisplayService displayService;
    public static Person loginUser;

    @Override
    public void afterPropertiesSet(ApplicationContext context) {
        networkService = (ClientNetworkServiceManagement) context.getApplicationBeans().get(ClientNetworkServiceManagementImpl.class);
        displayService = (DisplayService) context.getApplicationBeans().get(DisplayServiceImpl.class);
    }

    public void start() throws IOException {
        LOGGER.info("Command Parser Starting");
        networkService.initialize();
        displayService.printMainMenu();
        parseCommand();
    }

    private void parseCommand() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            displayService.printCurrentMenu();
            System.out.println(">>>");
            String command = sc.nextLine();
            String[] commands = command.split(" ");
            if (!commands[0].startsWith("--")) {
                System.out.println("Invalid Command");
                continue;
            }

            switch (commands[0]) {
                case "--selserver":
                    selectServer(commands[1]);
                    break;
                case "--sndchtxtMssg":
                    sendChannelTXTMessage(commands[1], commands[2], commands[3], sc);
                    break;
                case "--addserver":
                    addServer(sc);
                    break;
                case "--shservers":
                    shServers();
                    break;
                case "--shchannels":
                    showServerChannels(sc);
                    break;
                case "--addvoisch":
                    addVoiceChannel(sc);
                    break;
                case "--addmember":
                    addServerMember(commands[1], commands[2]);
                    break;
                case "--rmvmember":
                    removeServerMember(sc);
                    break;
                case "--addtxtchannel":
                    addTextChannel(sc);
                    break;
                case "--shmembers":
                    showServerMember(sc);
                    break;
                case "--rmchannel":
                    removeChannel(sc);
                    break;
                case "--selchannel":
                    selectChannel(commands[1], commands[2], commands[3]);
                    break;
                case "--exit":
                    exit();
                    break;
                case "--help":
                    break;
                case "--register":
                    register(sc);
                    break;
                case "--login":
                    login(sc);
                    break;
                case "--chpwd":
                    changePassword(sc);
                    break;
                case "--shfriends":
                    showFriendsList();
                    break;
                case "--shblocks":
                    getBlockedList();
                    break;
                case "--chimg":
                    changeImage(sc);
                    break;
                case "--shfriendreq":
                    getFriendsRequests();
                    break;
                case "--shchat":
                    getPrivateChats();
                    break;
                case "--selchat":
                    selectChat(commands[1]);
                    break;
                case "-shservers":
                    getServersList();
                    break;
                case "--logOut":
                    logOut();
                    break;
                case "--shmessages":
                    showMessages(commands[1]);
                    break;
                case "--sndtxtmessage":
                    sendTextMessage(sc, commands[1]);
                    break;
                case "--sndfilemessage":
                    sendFileMessage(sc, commands[1]);
                    break;
                case "--sndfirendreq":
                    sendFriendRequest(commands[1]);
                    break;
                case "--acpfriendreq":
                    acceptFriendRequest(commands[1]);
                    break;
                case "--rejfriendreq":
                    rejectFriendRequest(commands[1]);
                    break;
                case "--block":
                    block(commands[1]);
                    break;
                case "--unblock":
                    unblock(commands[1]);
                    break;
                case "--renameServer":
                    renameServer(commands[1], sc);
                    break;
                case "--setrole":
                    setRole(commands[1], commands[2], sc);
                    break;
                case "--sndchfileMssg":
                    sendChannelFileMessage(commands[1], commands[2], commands[3], sc);
                    break;
                case "--react":
                    react(commands[1], commands[2], commands[3], sc);
                default:
                    System.out.println("Not Supported Command");
            }
        }
    }

    private void selectChat(String chatId) {
        if (loginUser == null) {
            LOGGER.error("Login first");
            return;
        }
        List<PrivateChat> response = (List<PrivateChat>) networkService.getPrivateChats(loginUser.getId());
        if (response == null) {
            LOGGER.error("Error while selecting Chat");
            return;
        }
        PrivateChat chat = null;
        for (PrivateChat privateChat : response) {
            if (privateChat.getId().equals(chatId))
                chat = privateChat;
        }
        if (chat == null) {
            LOGGER.warn("No such that Chat Founded");
            return;
        }
        displayService.printPrivateChatMenu(new PrivateChatMenu(chatId));
    }

    private void react(String serverId, String categoryId, String channelId, Scanner sc) {
        DiscordChannel channel = selectChannel(serverId, categoryId, channelId);
        System.out.println("please enter messageId:");
        String messageId = sc.nextLine();
        System.out.println("please enter index if reaction: like(1), dislike(2), laugh(3)");
        String index = sc.nextLine();
        try {
            Reaction reaction = Reaction.getByIndex(Integer.parseInt(index));
            networkService.react(channel, messageId, reaction);
        } catch (InvalidIdException e) {
            System.out.println(e.getMessage());
        }
    }

    private void sendChannelTXTMessage(String serverId, String categoryId, String channelId, Scanner sc) {
        System.out.println("please write your message:");
        String text = sc.nextLine();
        DiscordMessage<String> message = new DiscordMessage(loginUser, text);
        networkService.sendChannelMessages(serverId, categoryId, channelId, message);
    }

    private void sendChannelFileMessage(String serverId, String categoryId, String channelId, Scanner sc) {
        if (!isLoggedIn()) {
            LOGGER.error("You are not logged in");
            return;
        }
        LOGGER.info("Sending File Message");
        System.out.println("Please Enter the file format");
        String fileFormat = sc.next();
        System.out.println("Please Enter filePath");
        String fileAddress = sc.nextLine();
        File file = new File(fileAddress);
        byte[] bytes;
        if (!file.exists() || fileFormat == null || fileFormat.isBlank()) {
            LOGGER.error("File Not Exist");
            return;
        }
        try {
            bytes = new FileInputStream(file).readAllBytes();
        } catch (IOException e) {
            LOGGER.error("Sending File Message failed because {}", e.getMessage());
            return;
        }
        FileMessage message = new FileMessage(bytes, fileFormat);

        Object response = networkService.sendChannelMessages(serverId, categoryId, channelId, new DiscordMessage<>(loginUser, message));
        if (response instanceof String)
            LOGGER.error("Sending File Message failed because {}", String.valueOf(response));
        else
            LOGGER.info("Sent File Message : {}", response.toString());
    }

    private void setRole(String serverId, String personId, Scanner sc) {
        DiscordServer server = networkService.selectServer(serverId);
        System.out.println("the person abilities:");
        printRole(server.getPersonRoleMap().get(personId));
        System.out.println("all existing abilities:");
        Role role = new Role();
        role.setAbilities(Ability.getAllAbilities());
        printRole(role);
        System.out.println("please enter abilities indexes in one line by space");
        String indexes = sc.nextLine();
        String[] indexArr = indexes.split(" ");
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
        server.getPersonRoleMap().replace(personId, newRole);


    }

    private void printRole(Role role) {
        for (Ability ability : role.getAbilities()) {
            System.out.println(ability.name() + ":" + ability.getIndex());
        }
    }

    private void renameServer(String serverId, Scanner sc) {
        System.out.println("please enter new name");
        String name = sc.nextLine();
        networkService.renameServer(serverId, name);
    }

    private void selectServer(String serverId) {
        if (loginUser == null) {
            LOGGER.error("Login first");
            return;
        }
        if (!loginUser.getServerIdList().contains(serverId)) {
            LOGGER.warn("No such that server Founded");
            return;
        }
        displayService.printServerMenu(new ServerMenu(serverId));
    }

    private void shServers() {
        if (loginUser == null) {
            LOGGER.error("Login first");
            return;
        }
        Person updatePerson = networkService.updatePerson(loginUser.getId());
        if (updatePerson != null) {
            loginUser = updatePerson;
            System.out.println("Servers are :");
            loginUser.getServerIdList().forEach(System.out::println);
        } else {
            LOGGER.error("couldn't get server list");
        }
    }

    private void addServer(Scanner sc) {
        if (loginUser == null) {
            LOGGER.error("Login first");
            return;
        }
        System.out.println("Please Enter Server Name");
        String name = sc.next();
        if (networkService.addServer(loginUser.getId(), name)) {
            LOGGER.info("Server Added Successfully");
        } else {
            LOGGER.error("Couldn't make Server");
        }
    }

    private void logOut() {
        if (loginUser != null) {
            networkService.logOut(loginUser);
            displayService.printMainMenu();
        }
    }

    private DiscordChannel selectChannel(String serverId, String categoryId, String channelId) {
        DiscordChannel channel = networkService.selectChannel(serverId, categoryId, channelId);
        System.out.println(channel.getName() + ":" + channel.getId());
        if (channel instanceof TextChannel)
            printChannelInfo((TextChannel) channel);
        return channel;
    }

    private void printChannelInfo(TextChannel channel) {
        System.out.println("messages:" + (channel).getDiscordMessages().size());
        for (DiscordMessage message : channel.getDiscordMessages().values()) {
            if (message.getBody() instanceof FileMessage)
                System.out.println(((FileMessage) message.getBody()).getFormat() + ":" + message.getId());
            else
                System.out.println(message.getBody() + ":" + message.getId());
        }
    }

    private void removeChannel(Scanner sc) {
        System.out.println("please enter serverId");
        String serverId = sc.nextLine();
        System.out.println("please enter categoryId");
        String categoryId = sc.nextLine();
        System.out.println("please enter channelId");
        String channelId = sc.nextLine();
        networkService.removeChannel(serverId, categoryId, channelId, loginUser.getId());

    }

    private void addVoiceChannel(Scanner sc) {
        System.out.println("please enter serverId");
        String serverId = sc.nextLine();
        System.out.println("please enter categoryId");
        String categoryId = sc.nextLine();
        System.out.println("please enter name");
        String name = sc.nextLine();
        networkService.addChannel(loginUser.getId(), serverId, categoryId, name, ChannelType.VOICE);

    }

    private void addTextChannel(Scanner sc) {
        System.out.println("please enter serverId");
        String serverId = sc.nextLine();
        System.out.println("please enter categoryId");
        String categoryId = sc.nextLine();
        System.out.println("please enter name");
        String name = sc.nextLine();
        networkService.addChannel(loginUser.getId(), serverId, categoryId, name, ChannelType.TEXT);
    }

    private void showServerChannels(Scanner sc) {
        System.out.println("please enter the serverId:");
        String serverId = sc.nextLine();
        List<Category> channels = networkService.showServerChannels(serverId);
        for (Category category : channels) {
            printChannelsInfo(category);
        }
    }

    private void printChannelsInfo(Category category) {
        System.out.println(category.getName() + " : " + category.getId());
        for (DiscordChannel channel : category.getChannels().values()) {
            System.out.println("  " + channel.getName() + " : " + channel.getId());
        }
    }

    private void showServerMember(Scanner sc) {
        System.out.println("please enter the serverId:");
        String serverId = sc.nextLine();
        List<Person> persons = networkService.showServerMembers(serverId);
        int index = 0;
        for (Person p : persons) {
            printUserInfo(p, index);
            index++;
        }

    }

    private void printUserInfo(Person person, int index) {
        System.out.println(index + "- id: " + person.getId());
        System.out.println("  name: " + person.getName() + "id : " + person.getId());
        System.out.println("  status: " + person.getUserStatus());
    }

    private void removeServerMember(Scanner sc) {
        if (!isLoggedIn()) {
            LOGGER.error("You are not logged in");
            return;
        }
        System.out.println("please enter the serverId:");
        String serverId = sc.nextLine();
        System.out.println("please enter the personId");
        String personId = sc.nextLine();
        Object response = networkService.removeServerMember(serverId, loginUser.getId(), personId);
    }

    private void addServerMember(String serverId, String personId) {
        if (!isLoggedIn()) {
            LOGGER.error("You are not logged in");
            return;
        }
        Object response = networkService.addServerMember(serverId, loginUser.getId(), personId);
        if (response == null)
            LOGGER.info("User added successfully");
        else
            LOGGER.error("Couldn't add user, because {}", ((PersonAdditionResponseDto) response).getFailureCause());
    }

    private void changeImage(Scanner sc) {
        if (loginUser != null) {
            LOGGER.info("Changing profile photo");
            //pass
        }
    }

    private void getPrivateChats() {
        if (loginUser != null) {
            LOGGER.info("Getting private chats:");
            List<PrivateChat> response = (List<PrivateChat>) networkService.getPrivateChats(loginUser.getId());
            if (response != null) {
                for (int i = 0; i < response.size(); i++) {
                    System.out.println(response.get(i).getId());
                }
            } else {
                LOGGER.error("Showing private chats failed");
            }
        } else {
            LOGGER.error("You are not logged in");
        }
    }

    private void getServersList() {
        if (loginUser != null) {
            LOGGER.info("Getting servers list:");
            List<String> response = (List<String>) networkService.getServersList(loginUser.getId());
            if (response != null) {
                for (int i = 0; i < response.size(); i++) {
                    System.out.println(response.get(i));
                }
            } else {
                LOGGER.error("Showing servers list failed");
            }
        } else {
            LOGGER.error("You are not logged in");
        }
    }

    private void getFriendsRequests() {
        if (loginUser != null) {
            LOGGER.info("Getting friends requests list");
            List<String> response = (List<String>) networkService.getFriendsRequestsList(loginUser.getId());
            if (response != null) {
                for (int i = 0; i < response.size(); i++) {
                    System.out.println(response.get(i));
                }
            } else {
                LOGGER.error("Showing friends requests failed");
            }
        } else {
            LOGGER.error("You are not logged in");
        }
    }

    private void exit() {
        LOGGER.warn("Shutting Down ...");
        if (loginUser == null) {
            networkService.exit();
            System.exit(9);
        }
        if (!networkService.logOut(loginUser))
            LOGGER.warn("Error occurred while logging out");
        networkService.exit();
        System.exit(9);
    }

    private void register(Scanner scanner) {
        String name, pwd, email, phoneNumber, id;
        LOGGER.info("Registering");
        System.out.println("Please Enter your name");
        name = scanner.next();
        System.out.println("Please Enter your id");
        id = scanner.next();
        System.out.println("Please Enter your email");
        email = scanner.next();
        System.out.println("Please Enter your phoneNumber");
        phoneNumber = scanner.next();
        System.out.println("Please Enter your password");
        pwd = scanner.next();

        Object response = networkService.register(name, id, pwd, email, phoneNumber);
        if (response instanceof String)
            LOGGER.error("Registering failed because {}", String.valueOf(response));
        else
            LOGGER.info("Registered : {}", response.toString());
    }

    private void login(Scanner scanner) {
        String password, id;
        LOGGER.info("Login");
        System.out.println("Please Enter your id");
        id = scanner.next();
        System.out.println("Please Enter your password");
        password = scanner.next();
        Object response = networkService.login(id, password);
        if (response instanceof Person) {
            LOGGER.info("Logged in :{}", response.toString());
            loginUser = (Person) response;
            displayService.printDiscordMenu();
        } else {
            LOGGER.error("Logging failed because {}", String.valueOf(response));
        }
    }

    private void changePassword(Scanner scanner) {
        if (loginUser != null) {
            String newPwd;
            LOGGER.info("Change Password");
            System.out.println("Enter new password:");
            newPwd = scanner.next();
            boolean response = networkService.changePassword(loginUser.getId(), newPwd);
            if (response) {
                LOGGER.info("Password changed successfully");
            } else {
                LOGGER.error("Password changing failed");
            }
        } else {
            LOGGER.error("You are not logged in");
        }
    }

    private void showFriendsList() {
        if (loginUser != null) {
            LOGGER.info("Show friends list");
            List<Person> response = (List<Person>) networkService.getFriendList(loginUser.getId());
            if (response != null) {
                for (int i = 0; i < response.size(); i++) {
                    System.out.println(response.get(i).getName());
                }
            } else {
                LOGGER.error("Showing friends failed");
            }
        } else {
            LOGGER.error("You are not logged in");
        }
    }

    private void getBlockedList() {
        if (loginUser != null) {
            LOGGER.info("Show blocked list");
            List<String> response = (List<String>) networkService.getBlockList(loginUser.getId());
            if (response != null) {
                for (int i = 0; i < response.size(); i++) {
                    System.out.println(response.get(i));
                }
            } else {
                LOGGER.error("Showing blocked list failed");
            }
        } else {
            LOGGER.error("You are not logged in");
        }
    }

    private boolean isLoggedIn() {
        return loginUser != null;
    }

    private void showMessages(String personId) {
        if (!isLoggedIn()) {
            LOGGER.error("You are not logged in");
            return;
        }
        List<DiscordMessage> messages = networkService.showMessages(loginUser.getId(), personId);
        for (DiscordMessage message : messages) {
            System.out.println(message);
        }
    }

    private void sendTextMessage(Scanner scanner, String message) {
        if (!isLoggedIn()) {
            LOGGER.error("You are not logged in");
            return;
        }
        LOGGER.info("Sending Text Message");
        System.out.println("Please Enter your receiverId");
        String receiverId = scanner.next();
        Object response = networkService.sendTextMessage(loginUser, loginUser.getId(), receiverId, message);
        if (response != null)
            LOGGER.error("Sending Text Message failed because {}", String.valueOf(response));
        else
            LOGGER.info("Sent Text Message to : {}", receiverId);
    }

    private void sendFileMessage(Scanner scanner, String fileAddress) {
        if (!isLoggedIn()) {
            LOGGER.error("You are not logged in");
            return;
        }
        LOGGER.info("Sending File Message");
        System.out.println("Please Enter the file format");
        String fileFormat = scanner.next();
        System.out.println("Please Enter your receiverId");
        String receiverId = scanner.next();
        File file = new File(fileAddress);
        byte[] bytes;
        if (!file.exists() || fileFormat == null || fileFormat.isBlank()) {
            LOGGER.error("File Not Exist");
            return;
        }
        try {
            bytes = new FileInputStream(file).readAllBytes();
        } catch (IOException e) {
            LOGGER.error("Sending File Message failed because {}", e.getMessage());
            return;
        }
        Object response = networkService.sendFileMessage(loginUser, loginUser.getId(), receiverId, bytes, fileFormat);
        if (response != null)
            LOGGER.error("Sending File Message failed because {}", String.valueOf(response));
        else
            LOGGER.info("Sent File Message  to : {}", receiverId);
    }

    private void sendFriendRequest(String receiverId) {
        if (!isLoggedIn()) {
            LOGGER.error("You are not logged in");
            return;
        }
        LOGGER.info("Sending Friend Request");
        Object response = networkService.sendFriendRequest(loginUser.getId(), receiverId);
        if (response != null)
            LOGGER.error("Sending Friend Request failed because {}", String.valueOf(response));
        else
            LOGGER.info("Sent Friend Request To : {}", receiverId);

    }

    private void acceptFriendRequest(String senderId) {
        if (!isLoggedIn()) {
            LOGGER.error("You are not logged in");
            return;
        }
        LOGGER.info("Accepting Friend Request");
        Object response = networkService.acceptFriendRequest(senderId, loginUser.getId());
        if (response != null)
            LOGGER.error("Accepting Friend Request failed because {}", String.valueOf(response));
        else
            LOGGER.info("Accepted Friend Request : {} sent", senderId);
    }

    private void rejectFriendRequest(String senderId) {
        if (!isLoggedIn()) {
            LOGGER.error("You are not logged in");
            return;
        }
        LOGGER.info("Rejecting Friend Request");
        Object response = networkService.rejectFriendRequest(senderId, loginUser.getId());
        if (response != null)
            LOGGER.error("Rejecting Friend Request failed because {}", String.valueOf(response));
        else
            LOGGER.info("Rejected Friend Request : {} sent", senderId);
    }

    private void block(String receiverId) {
        if (!isLoggedIn()) {
            LOGGER.error("You are not logged in");
            return;
        }
        LOGGER.info("Blocking");
        Object response = networkService.block(loginUser.getId(), receiverId);
        if (response != null)
            LOGGER.error("Blocking failed because {}", String.valueOf(response));
        else
            LOGGER.info("Blocked : {}", receiverId);
    }

    private void unblock(String receiverId) {
        if (!isLoggedIn()) {
            LOGGER.error("You are not logged in");
            return;
        }
        LOGGER.info("Unblocking");
        Object response = networkService.unblock(loginUser.getId(), receiverId);
        if (response != null)
            LOGGER.error("Unblocking failed because {}", String.valueOf(response));
        else
            LOGGER.info("Unblocked : {}", receiverId);
    }
}
