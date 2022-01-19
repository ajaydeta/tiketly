module com.tiketly.tiketly {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.tiketly.tiketly to javafx.fxml;
    exports com.tiketly.tiketly;
    exports com.tiketly.tiketly.controller;
    exports helper;
}