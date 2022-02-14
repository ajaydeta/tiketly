package com.tiketly.tiketly.controller.kasirController;

import database.Database;
import helper.Helper;
import helper.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.TableJadwalItem;
import util.DataTravel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class BuatTransaksi extends KasirBase implements Initializable {
    public Button btnPilihKursi;
    Navigation navigationHelper = new Navigation();
    DataTravel dataTravel = DataTravel.getInstance();

    public Label judulFilm;
    public Label teater;
    public Label noKursi;
    public Label totalBayar;
    public TableView<TableJadwalItem> tableJadwal;
    private Map<String, Object> session = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startStage();

        try {
//            FOR DEVELOPMENT PURPOSE
            Database database = new Database();
            database.select();
            database.table("user");
            database.where("iduser = ?", "USR16440334169808891");
            database.where("hapus = ?", 0);

            this.session = database.getOneMapResult();
            dataTravel.addData("SESSION", this.session);
//            this.session = (Map<String, Object>) dataTravel.getData("SESSION");

            setTableJadwal();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void checkKonfirm(ActionEvent actionEvent) {
    }

    public void batal(ActionEvent actionEvent) {
    }

    public void modalKonfirm(ActionEvent actionEvent) {
    }

    public void pilihKursi(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        Database database = new Database();
        database.table("teater");
        database.where("idteater = ?", dataTravel.getData("idteater"));
        Map<String, Object> teaterResult = database.getOneMapResult();
        int jumlahKolom = (Integer) teaterResult.get("kolom");
        int jumlahBaris = (Integer) teaterResult.get("baris");

        int width = jumlahKolom * 70 + (jumlahKolom * 10);
        int height = jumlahBaris * 70 + (jumlahBaris * 10);

        System.out.println("width "+width);
        System.out.println("height "+height);

        navigationHelper.showModal(actionEvent, "Pilih Kursi", width, height, "pilihKursi");
    }

    private void setTableJadwal() throws SQLException, ClassNotFoundException {
        TableColumn<TableJadwalItem, String> noCol = new TableColumn<>("No");
        noCol.setCellValueFactory(new PropertyValueFactory<>("no"));

        TableColumn<TableJadwalItem, String> judulCol = new TableColumn<>("Judul Film");
        judulCol.setCellValueFactory(new PropertyValueFactory<>("judul"));

        TableColumn<TableJadwalItem, String> teaterCol = new TableColumn<>("Teater");
        teaterCol.setCellValueFactory(new PropertyValueFactory<>("namaTeater"));

        TableColumn<TableJadwalItem, String> jadwalCol = new TableColumn<>("Jadwal Tayang");
        jadwalCol.setCellValueFactory(new PropertyValueFactory<>("showAt"));

        TableColumn<TableJadwalItem, String> hargaCol = new TableColumn<>("Harga Tiket");
        hargaCol.setCellValueFactory(new PropertyValueFactory<>("harga"));

        tableJadwal.getColumns().clear();
        tableJadwal.getColumns().add(noCol);
        tableJadwal.getColumns().add(judulCol);
        tableJadwal.getColumns().add(teaterCol);
        tableJadwal.getColumns().add(jadwalCol);
        tableJadwal.getColumns().add(hargaCol);

        tableJadwal.getItems().clear();
        Database database = new Database();

        database.table("jadwal");
        database.select("jadwal.*", "film.judul as judul_film", "bioskop.nama as nama_bioskop", "teater.nama as nama_teater");
        database.join("JOIN film ON film.id = jadwal.idfilm ");
        database.join("JOIN bioskop ON bioskop.idbioskop = jadwal.idbioskop");
        database.join("JOIN teater ON teater.idteater = jadwal.idteater");
        database.where("jadwal.hapus = ?", 0);
        database.where("jadwal.idbioskop = ?", session.get("idbioskop"));
        database.order("jadwal.show_at DESC");
        ArrayList<Map<String, Object>> jadwalResult = database.getArrayMapResult();

        for (int i = 0; i < jadwalResult.size(); i++) {
            Map<String, Object> jadwal = jadwalResult.get(i);
            tableJadwal.getItems().add(
                    new TableJadwalItem(
                            i + 1,
                            (int) jadwal.get("idjadwal"),
                            (int) jadwal.get("idbioskop"),
                            (int) jadwal.get("idteater"),
                            (float) jadwal.get("harga"),
                            this.helper.formatDateTimeFull((Timestamp) jadwal.get("show_at")),
                            (String) jadwal.get("judul_film"),
                            (String) jadwal.get("nama_bioskop"),
                            (String) jadwal.get("nama_teater"),
                            (Timestamp) jadwal.get("show_at")
                    )
            );
        }

    }

    private void startStage(){
        judulFilm.setText("Belum dipilih");
        teater.setText("Belum dipilih");
        noKursi.setText("");
        totalBayar.setText("Belum dipilih");
        btnPilihKursi.setDisable(true);
        dataTravel.deleteData("idteater");
    }

    public void selectItemTable(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        if (mouseEvent.getClickCount() == 2)
        {
            TableJadwalItem tableItem = tableJadwal.getSelectionModel().getSelectedItem();

            judulFilm.setText(tableItem.getJudul());
            teater.setText(tableItem.getNamaTeater());
            totalBayar.setText("0.0");
            btnPilihKursi.setDisable(false);

            dataTravel.addData("idteater", tableItem.getIdteater());
        } else if (mouseEvent.getClickCount() == 1){
            startStage();
        }
    }
}
