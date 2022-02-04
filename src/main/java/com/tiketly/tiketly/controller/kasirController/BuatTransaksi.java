package com.tiketly.tiketly.controller.kasirController;

import helper.Helper;
import helper.Navigation;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.io.IOException;

public class BuatTransaksi extends KasirBase {
    Navigation navigationHelper = new Navigation();

    public Label judulFilm;
    public Label teater;
    public Label noKursi;
    public Label totalBayar;
    public TableView tableJadwal;

    public void checkKonfirm(ActionEvent actionEvent) {
    }

    public void batal(ActionEvent actionEvent) {
    }

    public void modalKonfirm(ActionEvent actionEvent) {
    }

    public void pilihKursi(ActionEvent actionEvent) throws IOException {
        navigationHelper.showModal(actionEvent, "Pilih Kursi", 1067, 548, "pilihKursi");
    }
}
