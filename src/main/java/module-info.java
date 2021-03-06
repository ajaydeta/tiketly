module com.tiketly.tiketly {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;
    requires jasperreports;

    opens com.tiketly.tiketly to javafx.fxml;
    opens com.tiketly.tiketly.views.admin to javafx.fxml;
//    exports com.tiketly.tiketly.controller;
    exports com.tiketly.tiketly;
    exports util;
    exports com.tiketly.tiketly.controller.adminController;
    exports com.tiketly.tiketly.controller.kasirController;
    exports com.tiketly.tiketly.controller.modalController;
    exports com.tiketly.tiketly.controller.auth;
    exports com.tiketly.tiketly.controller;
    exports helper;
    exports database;
    exports model;
    exports routes;
}