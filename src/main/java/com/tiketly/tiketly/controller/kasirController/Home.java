package com.tiketly.tiketly.controller.kasirController;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import routes.Routes;
import util.DataTravel;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Home extends KasirBase implements Initializable {
    public Label headerHome;
    Routes routes = new Routes();
    DataTravel dataTravel = DataTravel.getInstance();
    Map<String, Object> session = new HashMap<>();

    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.session = (Map<String, Object>) dataTravel.getData("SESSION");

//        Selamat pagi, kasir Ahmad
        String sapaan = "";
        LocalTime localTime = LocalTime.now();
        int jam = localTime.getHour();
        if (jam > 3 && jam < 11) {
            sapaan = "pagi";
        } else if (jam >= 11 && jam < 14) {
            sapaan = "siang";
        } else if (jam >= 14 && jam < 18) {
            sapaan = "sore";
        } else {
            sapaan = "malam";
        }

        headerHome.setText("Selamat " + sapaan + ", kasir " + this.session.get("nama").toString());
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        dataTravel.clear();
        System.out.println(this.dataTravel.getData());
        routes.toLogin(actionEvent);
    }

    public void riwayatTransaksi(ActionEvent actionEvent) throws IOException {
        routes.toRiwayatTransaksi(actionEvent);
    }

    public void buatTransaksi(ActionEvent actionEvent) throws IOException {
        routes.toBuatTransaksi(actionEvent);
    }
}
