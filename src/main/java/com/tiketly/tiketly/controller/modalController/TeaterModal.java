package com.tiketly.tiketly.controller.modalController;

import com.tiketly.tiketly.controller.adminController.KelolaBioskop;
import database.Database;
import database.QueryBuilder;
import helper.Helper;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import routes.Routes;
import util.DataTravel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.TableTeaterItem;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class TeaterModal extends KelolaBioskop implements Initializable {
    public Button btnHapusTeater;
    public ChoiceBox<String> namaKursi;
    public ChoiceBox<String> statusKursi;
    public HBox inputBarisKolom;
    public HBox updateKursi;
    DataTravel dataTravel = DataTravel.getInstance();
    Helper helper = new Helper();

    public TableView<TableTeaterItem> tableTeater;
    public TextField namaTeaterField;
    public TextField kolomField;
    public TextField barisField;
    public TextField idTeater;
    private int idTeaterInt;
    private int statusKursiInt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("idBioskop: " + dataTravel.getData("idBioskop"));
        try {
            setValueTableTeater();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        statusKursi.getItems().add("Tersedia");
        statusKursi.getItems().add("Rusak");
        statusKursi.setOnAction((event) -> {
            this.statusKursiInt = helper.getStatusKursiInt(statusKursi.getValue());
        });

        btnHapusTeater.setVisible(false);
        inputBarisKolom.setVisible(true);
        updateKursi.setVisible(false);

    }

    public void deleteBioskop(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        Database db = new Database();
        db.table("bioskop");
        db.where("idbioskop = ?", dataTravel.getData("idBioskop"));
        if (db.update("hapus", 1) > 0) {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();
            Routes routes = new Routes();
            routes.toKelolaBioskop(actionEvent);
        }
    }

    public void simpanTeater(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        insertTeater();
    }

    public void hapusTeater(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Database database = new Database();
        Connection conn = database.getConnection();
        try {
            conn.setAutoCommit(false);
            Database db1 = new Database();
            db1.table("teater");
            db1.where("idteater = ?", Integer.parseInt(idTeater.getText()));
            if ((Integer) database.execute(conn, db1.getQueryUpdate("hapus", 1)) > 0) {
                Database db2 = new Database();
                db2.table("kursi_teater");
                db2.where("idteater = ?", Integer.parseInt(idTeater.getText()));
                if ((Integer)database.execute(conn, db1.getQueryUpdate("hapus", 1)) > 0){
                    btnHapusTeater.setVisible(false);
                    clearField();
                    setValueTableTeater();
                }
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                System.err.print("Transaction is being rolled back");
                conn.rollback();
            } catch (SQLException excep) {
                e.printStackTrace();
            }
        }
    }

    private void insertTeater() throws SQLException, ClassNotFoundException {
        Map<String, Object> insertTeaterData = new HashMap<>();
        int kolomInt = Integer.parseInt(kolomField.getText());
        int barisInt = Integer.parseInt(barisField.getText());
        insertTeaterData.put("idbioskop", dataTravel.getData("idBioskop"));
        insertTeaterData.put("nama", namaTeaterField.getText());
        insertTeaterData.put("kolom", kolomInt);
        insertTeaterData.put("baris", barisInt);

        Database db = new Database();
        Connection conn = db.getConnection();

        try {
            conn.setAutoCommit(false);
            QueryBuilder qb = new QueryBuilder();
            db.execute(conn, qb.getQueryInsert("teater", insertTeaterData));

            qb.table("teater");
            qb.order("created_at DESC");
            qb.limit(1);
            Map<String, Object> teaterResult = (Map<String, Object>) db.execute(conn, qb.getQuerySelect());

            ArrayList<Map<String, Object>> kursiTeaterInsertData = new ArrayList<>();
            for (int i = 1; i <= barisInt; i++) {
                for (int j = 1; j <= kolomInt; j++) {
                    Map<String, Object> kursiTeater = new HashMap<>();
                    kursiTeater.put("idteater", teaterResult.get("idteater"));
                    kursiTeater.put("idbioskop", teaterResult.get("idbioskop"));
                    kursiTeater.put("nama", helper.getNamaKursiTeater(i, j));
                    kursiTeaterInsertData.add(kursiTeater);
                }
            }
            if ((int) db.execute(conn, qb.geQuerytBulkInsert("kursi_teater", kursiTeaterInsertData)) > 0) {
                btnHapusTeater.setVisible(false);
                clearField();
                setValueTableTeater();
            }
            conn.commit();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            try {
                System.err.print("Transaction is being rolled back");
                conn.rollback();
            } catch (SQLException excep) {
                e.printStackTrace();
            }
        }
    }

    private void setValueTableTeater() throws SQLException, ClassNotFoundException {
        TableColumn<TableTeaterItem, String> noCol = new TableColumn<>("No");
        noCol.setCellValueFactory(new PropertyValueFactory<>("no"));

        TableColumn<TableTeaterItem, String> namaCol = new TableColumn<>("Nama");
        namaCol.setCellValueFactory(new PropertyValueFactory<>("nama"));

        TableColumn<TableTeaterItem, String> barisCol = new TableColumn<>("Jumlah Baris");
        barisCol.setCellValueFactory(new PropertyValueFactory<>("baris"));

        TableColumn<TableTeaterItem, String> kolomCol = new TableColumn<>("Jumlah Kolom");
        kolomCol.setCellValueFactory(new PropertyValueFactory<>("kolom"));

        tableTeater.getColumns().clear();
        tableTeater.getColumns().add(noCol);
        tableTeater.getColumns().add(namaCol);
        tableTeater.getColumns().add(barisCol);
        tableTeater.getColumns().add(kolomCol);

        tableTeater.getItems().clear();
        Database database = new Database();
        database.table("teater");
        database.where("idbioskop = ?", dataTravel.getData("idBioskop"));
        database.where("hapus = 0");
        ArrayList<Map<String, Object>> teaterResult = database.getArrayMapResult();

        for (int i = 0; i < teaterResult.size(); i++) {
            Map<String, Object> teater = teaterResult.get(i);
            int idteaterInt = (int) teater.get("idteater");
            int baris = (int) teater.get("baris");
            int kolom = (int) teater.get("kolom");

            tableTeater.getItems().add(new TableTeaterItem(i + 1, idteaterInt, baris, kolom, (String) teater.get("nama")));
        }
    }

    public void selectItemTable(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        if (mouseEvent.getClickCount() == 2) {
            TableTeaterItem tableItem = tableTeater.getSelectionModel().getSelectedItem();

            idTeater.setText(Integer.toString(tableItem.getId()));
            namaTeaterField.setText(tableItem.getNama());
            barisField.setText(Integer.toString(tableItem.getBaris()));
            kolomField.setText(Integer.toString(tableItem.getKolom()));

            this.idTeaterInt = tableItem.getId();
            btnHapusTeater.setVisible(true);
            inputBarisKolom.setVisible(false);
            updateKursi.setVisible(true);

            setKursiChoiceBox(idTeaterInt);

        } else if (mouseEvent.getClickCount() == 1) {
            btnHapusTeater.setVisible(false);
            clearField();
        }
    }

    private void setKursiChoiceBox(int idTeater) throws SQLException, ClassNotFoundException {
        Map<String, Boolean> kursiStatus = new HashMap<>();

        Database database = new Database();
        database.table("kursi_teater");
        database.where("idteater = ?", idTeater);
        database.where("hapus = 0");
        database.where("hapus = 0");
        ArrayList<Map<String, Object>> kursiTeaterResult = database.getArrayMapResult();
        namaKursi.getItems().clear();
        for (Map<String, Object> kursiTeater : kursiTeaterResult) {
            namaKursi.getItems().add((String) kursiTeater.get("nama"));
            kursiStatus.put((String) kursiTeater.get("nama"), (boolean) kursiTeater.get("hapus"));
        }

        namaKursi.setOnAction((event) -> {
            statusKursi.setValue(kursiStatus.get(namaKursi.getValue()) ? "Rusak" : "Tersedia");
        });

    }

    private void clearField() {
        idTeater.clear();
        namaTeaterField.clear();
        barisField.clear();
        kolomField.clear();
        idTeaterInt = 0;
        inputBarisKolom.setVisible(true);
        updateKursi.setVisible(false);
    }
}
