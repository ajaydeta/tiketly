package com.tiketly.tiketly.controller.kasirController;

import javafx.event.ActionEvent;
import routes.Routes;

import java.io.IOException;

public class KasirBase {
    public void backHome(ActionEvent actionEvent) throws IOException {
        Routes routes = new Routes();
        routes.toHome(actionEvent);
    }
}
