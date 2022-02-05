package com.tiketly.tiketly;

import database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        URL url = Paths.get("src/main/resources/com/tiketly/tiketly/views/admin/kelolaKasir.fxml").toUri().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
//        stage.setFullScreen(true);
//        stage.setMaximized(true);
        stage.setTitle("Tiketly.id");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}