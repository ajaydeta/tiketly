package com.tiketly.tiketly;

import database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.SQLException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException, ClassNotFoundException {
        System.out.println("Tiketly running...");
        System.out.println("java version: " + System.getProperty("java.version"));
        System.out.println("javafx.version: " + System.getProperty("javafx.version"));

        URL url;
        Database db = new Database();
        if (db.ping()) {
//            url = Paths.get("src/main/resources/com/tiketly/tiketly/views/kasir/riwayatTransaksi.fxml").toUri().toURL();
//            url = Paths.get("src/main/resources/com/tiketly/tiketly/views/admin/pembelian.fxml").toUri().toURL();
            url = Paths.get("src/main/resources/com/tiketly/tiketly/views/login.fxml").toUri().toURL();

//            url = Paths.get("src/main/resources/com/tiketly/tiketly/play.fxml").toUri().toURL();
        } else {
            url = Paths.get("src/main/resources/com/tiketly/tiketly/views/alerterror.fxml").toUri().toURL();
        }

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