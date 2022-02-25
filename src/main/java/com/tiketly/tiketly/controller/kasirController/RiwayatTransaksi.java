package com.tiketly.tiketly.controller.kasirController;

import database.Database;
import helper.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;
import model.TableJadwalItem;
import model.TableTransaksiKasirItem;
import util.DataTravel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class RiwayatTransaksi extends KasirBase implements Initializable {
    public Label judulFilm;
    public Label teater;
    public Label noKursi;
    public Label totalBayar;
    public TableView<TableTransaksiKasirItem> tableRiwayat;
    DataTravel dataTravel = DataTravel.getInstance();

    private Map<String, Object> session = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        judulFilm.setText("Belum dipilih");
        teater.setText("Belum dipilih");
        noKursi.setText("Belum dipilih");
        totalBayar.setText("Belum dipilih");

        this.session = (Map<String, Object>) dataTravel.getData("SESSION");
        try {
            setItemTable();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void openFilter(ActionEvent actionEvent) throws IOException {
        Navigation navigation = new Navigation();
        navigation.showModal(actionEvent, "Filter Transaksi", 322, 380, "filterTransaksiKasir", this::onHideHandler, null);
    }

    private void onHideHandler(WindowEvent we) {
        System.out.println("modal closedd");
        try {
            setItemTable();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setItemTable() throws SQLException, ClassNotFoundException {
        TableColumn<TableTransaksiKasirItem, String> idTrxCol = new TableColumn<>("No Nota");
        idTrxCol.setCellValueFactory(new PropertyValueFactory<>("idTrx"));

        TableColumn<TableTransaksiKasirItem, String> judulFilmCol = new TableColumn<>("Judul Film");
        judulFilmCol.setCellValueFactory(new PropertyValueFactory<>("judulFilm"));

        TableColumn<TableTransaksiKasirItem, String> tanggalPembelianCol = new TableColumn<>("Tanggal Pembelian");
        tanggalPembelianCol.setCellValueFactory(new PropertyValueFactory<>("tanggalPembelian"));

        TableColumn<TableTransaksiKasirItem, String> teaterCol = new TableColumn<>("Teater");
        teaterCol.setCellValueFactory(new PropertyValueFactory<>("teater"));

        TableColumn<TableTransaksiKasirItem, String> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));

        TableColumn<TableTransaksiKasirItem, String> jumlahKursiCol = new TableColumn<>("Jumlah Kursi");
        jumlahKursiCol.setCellValueFactory(new PropertyValueFactory<>("jumlahKursi"));

        tableRiwayat.getColumns().clear();
        tableRiwayat.getColumns().add(idTrxCol);
        tableRiwayat.getColumns().add(judulFilmCol);
        tableRiwayat.getColumns().add(teaterCol);
        tableRiwayat.getColumns().add(totalCol);
        tableRiwayat.getColumns().add(jumlahKursiCol);
        tableRiwayat.getColumns().add(tanggalPembelianCol);

        tableRiwayat.getItems().clear();
        Database database = new Database();

        if (dataTravel.contains("filterTeater")) {
            database.where("jadwal.idteater = ?", dataTravel.getData("filterTeater"));
        }

        if (dataTravel.contains("filterTanggalAkhir")) {
            database.where("transaksi.created_at <= ?", dataTravel.getData("filterTanggalAkhir") + " 23:59:59");
        }

        if (dataTravel.contains("filterTanggalAwal")) {
            database.where("transaksi.created_at >= ?", dataTravel.getData("filterTanggalAwal") + " 00:00:00");
        }

        database.table("transaksi");
        database.select("`transaksi`.*", "film.judul", "teater.nama as teater", "COUNT(transaksi_kursi.id) as jumlah_kursi");
        database.join("JOIN jadwal ON jadwal.idjadwal = transaksi.idjadwal");
        database.join("JOIN film ON film.id = jadwal.idfilm");
        database.join("JOIN transaksi_kursi ON transaksi_kursi.idtransaksi = transaksi.id");
        database.join("JOIN teater ON jadwal.idteater = teater.idteater");
        database.where("transaksi.idkasir = ?", this.session.get("iduser"));
        database.where("transaksi.hapus = 0");
        database.groupBy("transaksi.id");
        database.order("transaksi.created_at DESC");
        ArrayList<Map<String, Object>> transaksiResult = database.getArrayMapResult();

        for (Map<String, Object> transaksi : transaksiResult) {
            tableRiwayat.getItems().add(
                    new TableTransaksiKasirItem(
                            (String) transaksi.get("id"),
                            (String) transaksi.get("judul"),
                            (String) transaksi.get("teater"),
                            helper.formatDateTimeFull((LocalDateTime) transaksi.get("created_at")),
                            (float) transaksi.get("total_bayar"),
                            (long) transaksi.get("jumlah_kursi")
                    )
            );
        }
    }

    public void selectItemTable(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        if (mouseEvent.getClickCount() == 2) {
            TableTransaksiKasirItem tableItem = tableRiwayat.getSelectionModel().getSelectedItem();

            judulFilm.setText(tableItem.getJudulFilm());
            teater.setText(tableItem.getTeater());
            noKursi.setText(tableItem.getJudulFilm());
            totalBayar.setText(Float.toString(tableItem.getTotal()));

        } else if (mouseEvent.getClickCount() == 1) {
            judulFilm.setText("Belum dipilih");
            teater.setText("Belum dipilih");
            noKursi.setText("Belum dipilih");
            totalBayar.setText("Belum dipilih");
        }
    }
}
