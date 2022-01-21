package com.tiketly.tiketly.controller;

import helper.DataTravel;
import helper.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class Jadwal implements Initializable {
    public Button btnPengguna;
    public Button btnKeluar;
    public Button btnTIket;
    public Button btnJadwal;
    public TableView tableJadwalFilm;
    public TableColumn filmCol;
    public TableColumn bioskopCol;
    public TableColumn jadwalCol;
    public TableColumn noCol;
    public TableColumn pukulCol;
    public TextField namaFilmField;
    public TextField bioskopField;
    public DatePicker dateField;
    public TextField timeField;
    public Button btnSimpan;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataTravel dataTravel = DataTravel.getInstance();
        Map<String, String> data = dataTravel.getData();
        System.out.println("data");
    }

    public void jadwalOnClick(ActionEvent actionEvent) {
    }

    public void tiketOnClick(ActionEvent actionEvent) {
    }

    public void keluarOnClick(ActionEvent actionEvent) {
    }

    public void penggunaOnClick(ActionEvent actionEvent) {
    }

    public void simpanOnClick(ActionEvent actionEvent) {
    }
}
