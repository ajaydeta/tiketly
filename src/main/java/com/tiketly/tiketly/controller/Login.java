package com.tiketly.tiketly.controller;

import helper.DataTravel;
import helper.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Login implements Initializable {
    public Button btnMasuk;
    public Button btnKembali;
    public TextField usernameField;
    public PasswordField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("FUCKKKK");
    }

    public void masuk(ActionEvent actionEvent) throws IOException {
        String username = usernameField.getText();
        String pass = passwordField.getText();

        Map<String, String> elements = new HashMap<>();
        elements.put("user", username);
        elements.put("pass", pass);

        DataTravel dataTravel = DataTravel.getInstance();
        dataTravel.setData(elements);

        toDashboard(actionEvent);

        System.out.println("user :"+username);
        System.out.println("pass :"+pass);
    }

    private void toDashboard(ActionEvent actionEvent) throws IOException {
        Navigation navHelper = new Navigation();
        String viewPath = "./src/main/resources/com/tiketly/tiketly/jadwal.fxml";
        navHelper.navigate(actionEvent, viewPath);
    }

    public void kembali(ActionEvent actionEvent) {
    }
}
