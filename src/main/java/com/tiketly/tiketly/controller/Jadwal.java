package com.tiketly.tiketly.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import helper.DataTravel;
import helper.Helper;
import helper.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import routes.Routes;

import javax.swing.text.AbstractDocument;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public ChoiceBox namaFilm;
    public TextField bioskopField;
    public DatePicker dateField;
    public TextField timeField;
    public Button btnSimpan;
    public TextField btnStok;
    public TableColumn stokCol;
    public Button btnAddFilm;
    public Button btnHapus;
    public VBox formKelolaJadwal;
    public HBox rootPane;

    Routes routes = new Routes();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Helper helper = new Helper();
        DataTravel dataTravel = DataTravel.getInstance();
        Map<String, Object> data = dataTravel.getData();
        Map<String, Object> session = null;
        try {
            session = helper.jsonStringToMap((String) data.get("session"));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        assert session != null;
        System.out.println(session.get("role"));

        if (session.get("role").equals("2")){
            btnPengguna.setVisible(false);
            rootPane.getChildren().remove(formKelolaJadwal);
        }
    }

    public void jadwalOnClick(ActionEvent actionEvent) {
    }

    public void tiketOnClick(ActionEvent actionEvent) throws IOException {
        routes.toTiket(actionEvent);
    }

    public void keluarOnClick(ActionEvent actionEvent) {
    }

    public void penggunaOnClick(ActionEvent actionEvent) throws IOException {
        routes.toUser(actionEvent);
    }

    public void simpanOnClick(ActionEvent actionEvent) throws IOException {
        LocalDateTime xx = LocalDateTime.parse(dateField.getValue().toString()+" 15:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        System.out.println(xx);
//        jancok(actionEvent);
    }

    public void addFilm(ActionEvent actionEvent) {
    }

    public void hapusOnClick(ActionEvent actionEvent) {
    }
}
