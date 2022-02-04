package com.tiketly.tiketly.controller;

import database.Database;
import helper.DataTravel;
import helper.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import routes.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class Login implements Initializable {
    public Button btnMasuk;
    public TextField usernameField;
    public PasswordField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("APP start");
    }

    public void masuk(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException, InterruptedException {
        String username = usernameField.getText();
        String pass = passwordField.getText();
        Helper helper = new Helper();
        Database database = new Database();

        database.select();
        database.table("user");
        database.where("telp = ?", username);
        database.where("hapus = ?", 0);

        System.out.println("user :" + username);
        Map<String, Object> userData = database.getOneMapResult();
        System.out.println(userData);


        Map<String, String> elements = new HashMap<>();
        elements.put("session", helper.mapToJson(userData));

        String invalidMessage = isValid(userData, pass);
        if (invalidMessage.equals("")){
            DataTravel dataTravel = DataTravel.getInstance();
            dataTravel.setData(elements);
            toDashboard(actionEvent);
            System.out.println("loginn");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login error");
            alert.setHeaderText(null);
            alert.setContentText(invalidMessage);
            alert.show();
        }

    }

    private void toDashboard(ActionEvent actionEvent) throws IOException {
        Routes routes = new Routes();
        routes.toJadwal(actionEvent);
    }

    private String isValid(Map<String, Object> elements, String pass) throws InterruptedException {
        if (elements.isEmpty()){
            return "no telpon tidak terdaftar!!";
        }

        if (!Objects.equals(elements.get("password"), pass)){
            return "password salah!!";
        }
        return "";
    }

}
