package com.tiketly.tiketly.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;

public class StartScreen {
    public Button btnMasuk;
    public Button btnBuatAkun;

    public void masuk(ActionEvent actionEvent) throws IOException {
        URL url = Paths.get("./src/main/resources/com/tiketly/tiketly/login.fxml").toUri().toURL();
        Parent root = FXMLLoader.load(url); // loads scene
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow(); // Gets event source(button) then gets the stage(window)

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(false);
        stage.setMaximized(true);
        stage.setMinWidth(750);
        stage.setMinHeight(540);

        stage.show();
        System.out.println("masukkkkkk");
    }

    public void buatAkun(ActionEvent actionEvent) throws IOException {
        System.out.println("buattt");
    }
}
