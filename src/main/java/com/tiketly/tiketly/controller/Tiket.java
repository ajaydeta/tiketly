package com.tiketly.tiketly.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class Tiket implements Initializable {
    public Button btnPesanTiket;
    public Button btnJadwal;
    public Button btnTIket;
    public Button btnKeluar;
    public Button btnPengguna;
    public Label pesanDisclaimer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("FUCKKKK");
        pesanDisclaimer.setText(
                "*Setelah melakukan pemesanan, sistem akan\n" +
                "menampilkan modal. Anda harus menyimpan \n" +
                "tangkap layar atau screenshot modal tersebut\n" +
                "dan tunjukan ke kasir dibioskop untuk mekakukan\n" +
                        "pembayaran."
        );
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
