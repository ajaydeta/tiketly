package com.tiketly.tiketly.controller.adminController;

import database.Database;
import helper.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.WindowEvent;
import model.TablePembelianItem;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class Pembelian extends AdminBase implements Initializable {
    public TableView<TablePembelianItem> tablePembelian;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataTravel.deleteData("filterBioskop");
        dataTravel.deleteData("filterKasir");
        dataTravel.deleteData("filterTanggalAkhir");
        dataTravel.deleteData("filterTanggalAwal");
        dataTravel.deleteData("filterBioskopRaw");
        dataTravel.deleteData("filterKasirRaw");

        try {
            setItemTable();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void openFilterPembelian(ActionEvent actionEvent) throws IOException {
        Navigation navigation = new Navigation();
        navigation.showModal(actionEvent, "Filter Pembelian", 322, 470, "filterPembelian", this::onHideHandler, null);
    }

    private void onHideHandler(WindowEvent we){
        System.out.println("modal closedd");
        try {
            setItemTable();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setItemTable() throws SQLException, ClassNotFoundException {
        TableColumn<TablePembelianItem, String> idTrxCol = new TableColumn<>("No Nota");
        idTrxCol.setCellValueFactory(new PropertyValueFactory<>("idTrx"));

        TableColumn<TablePembelianItem, String> judulFilmCol = new TableColumn<>("Judul Film");
        judulFilmCol.setCellValueFactory(new PropertyValueFactory<>("judulFilm"));

        TableColumn<TablePembelianItem, String> bioskopCol = new TableColumn<>("Bioskop");
        bioskopCol.setCellValueFactory(new PropertyValueFactory<>("bioskop"));

        TableColumn<TablePembelianItem, String> kasirCol = new TableColumn<>("Oleh Kasir");
        kasirCol.setCellValueFactory(new PropertyValueFactory<>("kasir"));

        TableColumn<TablePembelianItem, String> tanggalPembelianCol = new TableColumn<>("Tanggal Pembelian");
        tanggalPembelianCol.setCellValueFactory(new PropertyValueFactory<>("tanggalPembelian"));

        TableColumn<TablePembelianItem, String> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));

        TableColumn<TablePembelianItem, String> jumlahKursiCol = new TableColumn<>("Jumlah Kursi");
        jumlahKursiCol.setCellValueFactory(new PropertyValueFactory<>("jumlahKursi"));

        tablePembelian.getColumns().clear();
        tablePembelian.getColumns().add(idTrxCol);
        tablePembelian.getColumns().add(judulFilmCol);
        tablePembelian.getColumns().add(bioskopCol);
        tablePembelian.getColumns().add(kasirCol);
        tablePembelian.getColumns().add(totalCol);
        tablePembelian.getColumns().add(jumlahKursiCol);
        tablePembelian.getColumns().add(tanggalPembelianCol);

        tablePembelian.getItems().clear();
        Database database = new Database();

        if (dataTravel.contains("filterBioskop")) {
            database.where("transaksi.idbioskop = ?", dataTravel.getData("filterBioskop"));
        }

        if (dataTravel.contains("filterKasir")) {
            database.where("transaksi.idkasir = ?", dataTravel.getData("filterKasir"));
        }

        if (dataTravel.contains("filterTanggalAkhir")) {
            database.where("transaksi.created_at <= ?", dataTravel.getData("filterTanggalAkhir") + " 23:59:59");
        }

        if (dataTravel.contains("filterTanggalAwal")) {
            database.where("transaksi.created_at >= ?", dataTravel.getData("filterTanggalAwal") + " 00:00:00");
        }

        database.table("transaksi");
        database.select("`transaksi`.*", "film.judul",  "bioskop.nama as bioskop", "`user`.nama as kasir", "COUNT(transaksi_kursi.id) as jumlah_kursi");
        database.join("JOIN jadwal ON jadwal.idjadwal = transaksi.idjadwal");
        database.join("JOIN film ON film.id = jadwal.idfilm");
        database.join("JOIN bioskop ON jadwal.idbioskop = bioskop.idbioskop");
        database.join("JOIN `user` ON `user`.iduser = transaksi.idkasir");
        database.join("JOIN transaksi_kursi ON transaksi_kursi.idtransaksi = transaksi.id");
        database.where("transaksi.hapus = 0");
        database.groupBy("transaksi.id");
        database.order("transaksi.created_at DESC");
        ArrayList<Map<String, Object>> transaksiResult = database.getArrayMapResult();

        for (Map<String, Object> transaksi : transaksiResult) {
            tablePembelian.getItems().add(
                    new TablePembelianItem(
                            (String) transaksi.get("id"),
                            (String) transaksi.get("judul"),
                            (String) transaksi.get("bioskop"),
                            helper.formatDateTimeFull((Timestamp) transaksi.get("created_at")),
                            (String) transaksi.get("kasir"),
                            (float) transaksi.get("total_bayar"),
                            (long) transaksi.get("jumlah_kursi")
                    )
            );
        }
    }
}
