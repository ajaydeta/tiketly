package com.tiketly.tiketly.controller;

import helper.DataTravel;
import helper.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class Jadwal implements Initializable {
    public Button btnPengguna;
    public Button btnKeluar;
    public Button btnTIket;
    public Button btnJadwal;
    public TableView tableJadwalFilm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataTravel dataTravel = DataTravel.getInstance();
        Map<String, String> data = dataTravel.getData();
        System.out.println("data");
        System.out.println(data.get("user"));
    }

    public void jadwalOnClick(ActionEvent actionEvent) {
    }

    public void tiketOnClick(ActionEvent actionEvent) {
    }

    public void keluarOnClick(ActionEvent actionEvent) {
    }

    public void penggunaOnClick(ActionEvent actionEvent) {
    }
}
