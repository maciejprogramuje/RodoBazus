package pl.maciejprogramuje.rodobazus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class Main extends Application {
    public static final String fileName = "dir_listing_";
    public static final String fileNameDenied = "dir_access_denied.txt";

    public static ResourceBundle bundles;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        bundles = ResourceBundle.getBundle("bundles.messages");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Board.fxml"));

        loader.setResources(bundles);

        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        primaryStage.setTitle(bundles.getString("application.title"));
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("images/icon.png"));
        primaryStage.show();
    }
}