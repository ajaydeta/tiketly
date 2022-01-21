package com.tiketly.tiketly;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("startScreen.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("tiket.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1248, 720);
        stage.setTitle("Tiketly.id");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}