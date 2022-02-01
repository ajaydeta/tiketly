package com.tiketly.tiketly.controller.kasirController;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import routes.Routes;

import java.io.IOException;

public class Home extends KasirBase{
    public Label headerHome;
    Routes routes = new Routes();

    public void logout(ActionEvent actionEvent) {
    }

    public void riwayatTransaksi(ActionEvent actionEvent) throws IOException {
        routes.toRiwayatTransaksi(actionEvent);
    }

    public void buatTransaksi(ActionEvent actionEvent) throws IOException {
        routes.toBuatTransaksi(actionEvent);
    }
}
