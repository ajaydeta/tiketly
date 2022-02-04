package com.tiketly.tiketly.controller.adminController;

import database.Database;
import helper.Helper;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import routes.Routes;

import java.io.IOException;

public class AdminBase {
    public Button btnKelolaKasir;
    public Button btnKelolaBioskop;
    public Button btnKelolaFilm;
    public Button btnJadwalFilm;
    public Button btnPembelian;
    public Button btnKeluar;

    Routes routes = new Routes();
    Helper helper = new Helper();

    public void kelolaKasir(ActionEvent actionEvent) throws IOException {
        routes.toKelolaKasir(actionEvent);
    }

    public void kelolaBioskop(ActionEvent actionEvent) throws IOException {
        routes.toKelolaBioskop(actionEvent);
    }

    public void kelolaFilm(ActionEvent actionEvent) throws IOException {
        routes.toKelolaFilm(actionEvent);
    }

    public void jadwalFilm(ActionEvent actionEvent) throws IOException {
        routes.toKelolaJadwal(actionEvent);
    }

    public void pembelian(ActionEvent actionEvent) throws IOException {
        routes.toPembelian(actionEvent);
    }

    public void keluar(ActionEvent actionEvent) throws IOException {
    }
}
