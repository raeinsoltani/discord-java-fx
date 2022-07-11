package controller.company;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.ce.ap.discord.client.boot.ClientBootStrapper;
import org.ce.ap.discord.client.business.CommandParser;
import org.ce.ap.discord.client.business.display.DisplayService;
import org.ce.ap.discord.client.business.network.ClientNetworkServiceManagement;
import org.ce.ap.discord.common.boot.Bootstrapper;
import org.ce.ap.discord.common.entity.boot.ApplicationContext;
import org.ce.ap.discord.common.entity.boot.BootstrapProperties;
import org.ce.ap.discord.common.entity.business.Person;

import java.util.List;

public class MainLauncher extends Application {

    private static Stage primaryStageObj;

    @Override
    public void start(Stage stage) throws Exception {
        Bootstrapper bootstrapper = new ClientBootStrapper("DiscordClient");
        BootstrapProperties bootstrapProperties = new BootstrapProperties("DiscordClient", List.of("client.properties"));
        bootstrapper.initialize(bootstrapProperties);
        bootstrapper.initializingPostProcessor();
        ApplicationContext applicationContext = ApplicationContext.getInstance();
        CommandParser commandParser = (CommandParser) applicationContext.getApplicationBeans().get(CommandParser.class);
        commandParser.start();

        primaryStageObj = stage;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/LoginOrSignup.fxml"));
        stage.setTitle("Welcome to our Application!");
        stage.setResizable(false);
//        stage.getIcons().add(new Image("main/java/resources/images/Discord-logo.png"));
        Scene scene = new Scene(root, 600, 400);
        scene.setRoot(root);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> Platform.exit());
    }

    public static Stage getPrimaryStageObj() {
        return primaryStageObj;
    }

    public static void setPrimaryStageObj(Stage primaryStageObj) {
        MainLauncher.primaryStageObj = primaryStageObj;
    }
}
