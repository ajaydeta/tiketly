package com.tiketly.tiketly.controller;

import helper.Helper;
import helper.Navigation;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.io.IOException;

public class StartScreen {
    public Button btnMasuk;
    public Button btnBuatAkun;

    public void masuk(ActionEvent actionEvent) throws IOException {
        Navigation navHelper = new Navigation();
        String viewPath = "./src/main/resources/com/tiketly/tiketly/login.fxml";
        navHelper.navigate(actionEvent, viewPath);
        System.out.println("masukkkkkk");
    }

    public void buatAkun(ActionEvent actionEvent) throws IOException {
        System.out.println("buattt");
    }
}
