package com.tiketly.tiketly;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        URL url = Paths.get("src/main/resources/com/tiketly/tiketly/play.fxml").toUri().toURL();
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(".src/main/resources/com/tiketly/tiketly/views/admin/kelolaBioskop.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Scene scene = new Scene(fxmlLoader.load(), 658, 569);
//        stage.setFullScreen(true);
        stage.setTitle("Tiketly.id");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}