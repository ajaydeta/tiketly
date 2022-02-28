package com.tiketly.tiketly.controller.adminController;

import database.Database;
import database.QueryBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.TableBioskopItem;
import model.TableFilmItem;
import routes.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class KelolaFilm extends AdminBase implements Initializable {
    public TableView<TableFilmItem> tableFilm;
    public TextField search;
    public ChoiceBox<String> filterSensor;

    public TextField idfilm;
    public TextField judul;
    public TextArea sinopsis;
    public ChoiceBox<String> sensor;
    public Button btnHapusFilm;
    private int idfilmInt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        System.out.println("idBioskop: "+dataTravel.getData("idBioskop"));
        try {
            setValueTableFilm();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        btnHapusFilm.setVisible(false);
        setChoiceBox();
    }

    public void useFilter(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        setValueTableFilm();
    }

    public void resetFilter(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        filterSensor.setValue(null);
        search.clear();
        setValueTableFilm();
    }

    public void simapanFilm(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (idfilm.getText().equals("")) {
            insertFilm();
        } else {
            updateFilm();
        }
    }

    public void selectItemTable(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            TableFilmItem tableItem = tableFilm.getSelectionModel().getSelectedItem();

            idfilm.setText(Integer.toString(tableItem.getId()));
            judul.setText(tableItem.getJudul());
            sinopsis.setText(tableItem.getSinopsis());
            sensor.setValue(tableItem.getSensor());

            this.idfilmInt = tableItem.getId();
            btnHapusFilm.setVisible(true);

        } else if (mouseEvent.getClickCount() == 1) {
            btnHapusFilm.setVisible(false);
            clearField();
        }
    }

    public void hapusFilm(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Database db = new Database();
        Connection conn = db.getConnection();

        try {
            conn.setAutoCommit(false);
            QueryBuilder qb = new QueryBuilder();
            qb.table("film");
            qb.where("id = ?", this.idfilmInt);
            db.execute(conn, qb.getQueryUpdate("hapus", 1));

            QueryBuilder qb2 = new QueryBuilder();
            qb2.table("jadwal");
            qb2.where("idfilm = ?", this.idfilmInt);
            qb2.where("hapus = ?", 0);
            db.execute(conn, qb2.getQueryUpdate("hapus", 1));

            conn.commit();

            setValueTableFilm();
            btnHapusFilm.setVisible(false);
            clearField();
            navigation.showDialog("Sukses", "Sukses menghapus film");
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                System.err.print("Transaction is  being rolled back");
                conn.rollback();
            } catch (SQLException excep) {
                e.printStackTrace();
            }
        }
    }

    private void setValueTableFilm() throws SQLException, ClassNotFoundException {
        TableColumn<TableFilmItem, String> noCol = new TableColumn<>("No");
        noCol.setCellValueFactory(new PropertyValueFactory<>("no"));

        TableColumn<TableFilmItem, String> judulCol = new TableColumn<>("Judul");
        judulCol.setCellValueFactory(new PropertyValueFactory<>("judul"));

        TableColumn<TableFilmItem, String> sensorCol = new TableColumn<>("Sensor");
        sensorCol.setCellValueFactory(new PropertyValueFactory<>("sensor"));

        TableColumn<TableFilmItem, String> sinopsisCol = new TableColumn<>("Sinopsis");
        sinopsisCol.setCellValueFactory(new PropertyValueFactory<>("sinopsis"));

        tableFilm.getColumns().clear();
        tableFilm.getColumns().add(noCol);
        tableFilm.getColumns().add(judulCol);
        tableFilm.getColumns().add(sensorCol);
        tableFilm.getColumns().add(sinopsisCol);

        tableFilm.getItems().clear();
        Database database = new Database();

        if (!search.getText().equals("")){
            database.where("judul LIKE ?", "%"+search.getText()+"%");
        }

        if (filterSensor.getValue() != null){
            database.where("sensor = ?", filterSensor.getValue());
        }

        database.table("film");
        database.where("hapus = 0");

        ArrayList<Map<String, Object>> filmResult = database.getArrayMapResult();
        for (int i = 0; i < filmResult.size(); i++) {
            Map<String, Object> bioskop = filmResult.get(i);
            int idfilmInt = (int) bioskop.get("id");
            tableFilm.getItems().add(
                    new TableFilmItem(
                            i + 1,
                            idfilmInt,
                            (String) bioskop.get("judul"),
                            (String) bioskop.get("sinopsis"),
                            (String) bioskop.get("sensor")
                    )
            );
        }
    }

    private void clearField() {
        idfilm.clear();
        judul.clear();
        sinopsis.clear();
        sensor.setValue(null);
    }

    private void insertFilm() throws SQLException, ClassNotFoundException {
        String judulStr = judul.getText();
        String sinopsisStr = sinopsis.getText();
        String sensorStr = sensor.getValue();

        Database database = new Database();
        Map<String, Object> data = new HashMap<>();
        data.put("judul", judulStr);
        data.put("sinopsis", sinopsisStr);
        data.put("sensor", sensorStr);

        if (database.insert("film", data) > 0) {
            database.table("film");
            database.where("hapus = 0");
            database.order("created_at DESC");
            Map<String, Object> filmData = database.getOneMapResult();

            int tableItemsSize = tableFilm.getItems().size();
            tableFilm.getItems().add(
                    new TableFilmItem(
                            tableItemsSize + 1,
                            (int) filmData.get("id"),
                            (String) filmData.get("judul"),
                            (String) filmData.get("sinopsis"),
                            (String) filmData.get("sensor")
                    )
            );
            clearField();
            navigation.showDialog("Sukses", "Berhasil menambahkan film.");
        }
    }

    private void updateFilm() throws SQLException, ClassNotFoundException {
        String judulStr = judul.getText();
        String sinopsisStr = sinopsis.getText();
        String sensorStr = sensor.getValue();
        Map<String, Object> data = new HashMap<>();
        data.put("judul", judulStr);
        data.put("sinopsis", sinopsisStr);
        data.put("sensor", sensorStr);

        Database database = new Database();
        database.table("film");
        database.where("id = ?", this.idfilmInt);
        if (database.updates( data) > 0) {
            setValueTableFilm();
            btnHapusFilm.setVisible(false);
            clearField();
            navigation.showDialog("Sukses", "Berhasil mengupdate film.");
        }

    }

    private void setChoiceBox() {
        filterSensor.getItems().add("SU");
        filterSensor.getItems().add("13+");
        filterSensor.getItems().add("17+");
        filterSensor.getItems().add("21+");

        sensor.getItems().add("SU");
        sensor.getItems().add("13+");
        sensor.getItems().add("17+");
        sensor.getItems().add("21+");
    }

}
