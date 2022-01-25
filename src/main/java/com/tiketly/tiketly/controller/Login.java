package com.tiketly.tiketly.controller;

import database.Database;
import helper.DataTravel;
import helper.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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

    public void masuk(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException, InterruptedException {
        String username = usernameField.getText();
        String pass = passwordField.getText();

        Database database = new Database();
//        System.out.println(database.getColumn());
//        String q = "SELECT * FROM `user` WHERE (telp = '" + username + "');";
//        System.out.println(q);
//        Connection db = database.getConnection();
//        PreparedStatement stm = db.prepareStatement(q);
//        stm.setQueryTimeout(0);
//        ResultSet rs = stm.executeQuery();
//        while (rs.next()) {
//            System.out.println(rs.getString(1));
//        }


        database.select();
        database.table("user");
        database.where("telp = ?", username);

        System.out.println("user :" + username);
        System.out.println("pass :" + pass);
        Map<String, String> elements = database.getMapResult();

        if (isValid(elements, pass)){
            DataTravel dataTravel = DataTravel.getInstance();
            dataTravel.setData(elements);

            System.out.println("loginn");

        }

    }

    private void toDashboard(ActionEvent actionEvent) throws IOException {
        Navigation navHelper = new Navigation();
        String viewPath = "./src/main/resources/com/tiketly/tiketly/jadwal.fxml";
        navHelper.navigate(actionEvent, viewPath);
    }

    private boolean isValid(Map<String, String> elements, String pass) throws InterruptedException {
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setTitle("LOGIN ERROR");
        if (elements.isEmpty()){
            a.setContentText("no telpon tidak terdaftar!!");
            a.show();
            Thread.sleep(4000);
            System.out.println("close");
            a.close();
            return false;
        }

        if (!Objects.equals(elements.get("password"), pass)){
            a.setContentText("password salah!!");
            a.show();
            Thread.sleep(4000);
            System.out.println("close");
            a.close();
            return false;
        }
        return true;
    }

    public void kembali(ActionEvent actionEvent) {
    }
}
