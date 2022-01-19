package com.tiketly.tiketly.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Login {
    public Button btnMasuk;
    public Button btnKembali;
    public TextField usernameField;
    public PasswordField passwordField;

    public void masuk(ActionEvent actionEvent) {
        String username = usernameField.getText();
        String pass = passwordField.getText();

        System.out.println("user :"+username);
        System.out.println("pass :"+pass);
    }

    public void kembali(ActionEvent actionEvent) {
    }
}
