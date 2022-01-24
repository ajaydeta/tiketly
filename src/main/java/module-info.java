module com.tiketly.tiketly {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;
    requires mysql.connector;

    opens com.tiketly.tiketly to javafx.fxml;
    exports com.tiketly.tiketly;
    exports com.tiketly.tiketly.controller;
    exports helper;
    exports database;
    exports model;
}