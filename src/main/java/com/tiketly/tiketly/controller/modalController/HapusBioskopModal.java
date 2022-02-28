package com.tiketly.tiketly.controller.modalController;

import database.Database;
import database.QueryBuilder;
import helper.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import routes.Routes;
import util.DataTravel;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class HapusBioskopModal implements Initializable {
    public ComboBox<String> mutasiBioskop;
    public Label labelDisclaimer;
    DataTravel dataTravel = DataTravel.getInstance();
    Helper helper = new Helper();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelDisclaimer.setText("Jika anda menghapus\n" +
                "bioskop berarti anda\n" +
                "juga akan menghapus\n" +
                "jadwal, teater dan data karyawan\n" +
                "atau memutasi karyawan.\n" +
                "hapus bioskop secara permanen\n" +
                "dan mutasi karyawan.\n" +
                "*kosongi jika tidak ingin mutasi.");

        try {
            setBioskopChoice();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void hapusBioskop(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Database db = new Database();
        Connection conn = db.getConnection();

        try {
            conn.setAutoCommit(false);
            QueryBuilder qb = new QueryBuilder();
            qb.table("bioskop");
            qb.where("idbioskop = ?", dataTravel.getData("idBioskop"));
            db.execute(conn, qb.getQueryUpdate("hapus", 1));

            QueryBuilder qb2 = new QueryBuilder();
            qb2.table("teater");
            qb2.where("idbioskop = ?", dataTravel.getData("idBioskop"));
            db.execute(conn, qb2.getQueryUpdate("hapus", 1));

            QueryBuilder qb3 = new QueryBuilder();
            qb3.table("jadwal");
            qb3.where("idbioskop = ?", dataTravel.getData("idBioskop"));
            db.execute(conn, qb3.getQueryUpdate("hapus", 1));

            QueryBuilder qbK = new QueryBuilder();
            qbK.table("user");
            qbK.where("idbioskop = ?", dataTravel.getData("idBioskop"));
            qbK.where("role = ?", 2);
            if (mutasiBioskop.getValue() != null) {
                db.execute(conn, qbK.getQueryUpdate("idBioskop", Integer.parseInt(helper.getIdDalamKurung(mutasiBioskop.getValue()))));
            } else {
                db.execute(conn, qbK.getQueryUpdate("hapus", 1));
            }

            conn.commit();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();
            Routes routes = new Routes();
            routes.toKelolaBioskop(actionEvent);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            try {
                System.err.print("Transaction is  being rolled back");
                conn.rollback();
            } catch (SQLException excep) {
                e.printStackTrace();
            }
        }
    }

    private void setBioskopChoice() throws SQLException, ClassNotFoundException {
        Database database = new Database();
        database.table("bioskop");
        database.select();
        database.where("hapus = 0");
        ArrayList<Map<String, Object>> bioskopResult = database.getArrayMapResult();

        for (Map<String, Object> eachbioskop : bioskopResult) {
            mutasiBioskop.getItems().add(helper.setIdDalamKurung(eachbioskop.get("nama"), eachbioskop.get("idbioskop")));
        }
    }
}
