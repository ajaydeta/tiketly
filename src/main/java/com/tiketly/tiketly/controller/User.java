package com.tiketly.tiketly.controller;

import database.Database;
import helper.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class User {
    public Button btnJadwal;
    public Button btnTIket;
    public Button btnPengguna;
    public Button btnKeluar;
    public TableView tableJadwalFilm;
    public TableColumn idCol;
    public TableColumn namaCol;
    public TableColumn noCol;
    public TableColumn dateCol;

    //FORM Add/Update user data
    public TextField namaField;
    public TextField noField;
    public PasswordField password;
    public DatePicker dateField;
    public Button btnSimpan;


    public void simpanOnClick(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String nama, passwordStr, telp;
        LocalDate tglLahir;

        nama        = namaField.getText();
        passwordStr =  password.getText();
        telp        = noField.getText();
        tglLahir    = dateField.getValue();

        System.out.println("nama: "+nama);
        System.out.println("passwordStr: "+passwordStr);
        System.out.println("telp: "+telp);
        System.out.println("tglLahir: "+tglLahir);

        Helper helper = new Helper();
        Database database = new Database();
//        Connection db = database.getConnection();
        String[] colName = {"iduser","nama", "telp", "password", "tanggal_lahir"};
        PreparedStatement insertStm = database.getInstert("user", colName);
        insertStm.setString(1,helper.generateIdUser());
        insertStm.setString(2,nama);
        insertStm.setString(3,telp);
        insertStm.setString(4,passwordStr);
        insertStm.setDate(5, Date.valueOf(tglLahir));
        System.out.println(insertStm);
        if (insertStm.executeUpdate() > 0){
            System.out.println("sukses");
//            tableJadwalFilm.setItems();
        }

    }

    public void jadwalOnClick(ActionEvent actionEvent) {
    }

    public void penggunaOnClick(ActionEvent actionEvent) {
    }

    public void keluarOnClick(ActionEvent actionEvent) {
    }

    public void tiketOnClick(ActionEvent actionEvent) {
    }
}
