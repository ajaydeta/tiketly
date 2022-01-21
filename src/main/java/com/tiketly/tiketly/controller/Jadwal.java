package com.tiketly.tiketly.controller;

import helper.DataTravel;
import helper.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.text.AbstractDocument;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
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
    public TextField btnStok;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataTravel dataTravel = DataTravel.getInstance();
        Map<String, String> data = dataTravel.getData();
        System.out.println("data");
    }

    public void jancok(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        URL url = Paths.get("./src/main/resources/com/tiketly/tiketly/jadwal.fxml").toUri().toURL();
        Parent root = FXMLLoader.load(url);
        stage.setScene(new Scene(root));
        stage.setTitle("My modal window");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                ((Node)event.getSource()).getScene().getWindow() );
        stage.show();
    }

    public void jadwalOnClick(ActionEvent actionEvent) {
    }

    public void tiketOnClick(ActionEvent actionEvent) {
    }

    public void keluarOnClick(ActionEvent actionEvent) {
    }

    public void penggunaOnClick(ActionEvent actionEvent) {
    }

    public void simpanOnClick(ActionEvent actionEvent) throws IOException {
        jancok(actionEvent);
    }
}
