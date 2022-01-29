package com.tiketly.tiketly.controller;

import database.Database;
import helper.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.UserCell;
import routes.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class User implements Initializable {
    public Button btnJadwal;
    public Button btnTIket;
    public Button btnPengguna;
    public Button btnKeluar;
    public TableView<UserCell> tableJadwalFilm;

    //FORM Add/Update user data
    public TextField namaField;
    public TextField noField;
    public PasswordField password;
    public DatePicker dateField;
    public Button btnSimpan;

    Routes routes = new Routes();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<UserCell, String> idCol = new TableColumn<>("ID Pengguna");
        idCol.setCellValueFactory(new PropertyValueFactory<>("idUser"));

        TableColumn<UserCell, String> namaCol = new TableColumn<>("Nama");
        namaCol.setCellValueFactory(new PropertyValueFactory<>("nama"));

        TableColumn<UserCell, String> noCol = new TableColumn<>("No Telpon");
        noCol.setCellValueFactory(new PropertyValueFactory<>("telp"));

        TableColumn<UserCell, String> dateCol = new TableColumn<>("Tanggal lahir");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("tglLahir"));

        tableJadwalFilm.getColumns().add(idCol);
        tableJadwalFilm.getColumns().add(namaCol);
        tableJadwalFilm.getColumns().add(noCol);
        tableJadwalFilm.getColumns().add(dateCol);

        tableJadwalFilm.getItems().add(new UserCell("hahaha", "huhuh", "hehehe", "hohoh"));
        tableJadwalFilm.getItems().add(new UserCell("hahaha", "huhuh", "hehehe", "hohoh"));
        tableJadwalFilm.getItems().add(new UserCell("hahaha", "huhuh", "hehehe", "hohoh"));

        System.out.println(tableJadwalFilm.getItems());
    }

    public void simpanOnClick(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String idUser, nama, passwordStr, telp;
        LocalDate tglLahir;
        Helper helper = new Helper();
        Database database = new Database();

        idUser      = helper.generateIdUser();
        nama        = namaField.getText();
        passwordStr = password.getText();
        telp        = noField.getText();
        tglLahir    = dateField.getValue();

        System.out.println("nama: "+nama);
        System.out.println("passwordStr: "+passwordStr);
        System.out.println("telp: "+telp);
        System.out.println("tglLahir: "+tglLahir);

        String[] colName = {"iduser","nama", "telp", "password", "tanggal_lahir"};
        PreparedStatement insertStm = database.getInstert("user", colName);
        insertStm.setString(1,idUser);
        insertStm.setString(2,nama);
        insertStm.setString(3,telp);
        insertStm.setString(4,passwordStr);
        insertStm.setDate(5, Date.valueOf(tglLahir));
        System.out.println(insertStm);
        if (insertStm.executeUpdate() > 0){
            System.out.println("sukses");
            tableJadwalFilm.getItems().add(new UserCell(idUser, nama, telp, tglLahir.toString()));
        }

    }

    public void jadwalOnClick(ActionEvent actionEvent) throws IOException {
        routes.toJadwal(actionEvent);
    }

    public void tiketOnClick(ActionEvent actionEvent) throws IOException {
        routes.toTiket(actionEvent);
    }

    public void keluarOnClick(ActionEvent actionEvent) {
    }

    public void penggunaOnClick(ActionEvent actionEvent) throws IOException {
        routes.toUser(actionEvent);
    }
}
