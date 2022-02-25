package com.tiketly.tiketly.controller.kasirController;

import database.Database;
import database.QueryBuilder;
import helper.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.WindowEvent;
import model.TableJadwalItem;
import util.DataTravel;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class BuatTransaksi extends KasirBase implements Initializable {
    public Button btnPilihKursi;
    public Button btnConfirm;
    public CheckBox checkKonfirmBox;
    public Button pilihKursiSml;
    public Label keteranganHarga;

    public Label judulFilm;
    public Label teater;
    public Label noKursi;
    public Label totalBayar;
    public TableView<TableJadwalItem> tableJadwal;
    public Pane transaksiControl;

    private Map<String, Object> session = new HashMap<>();
    private Boolean btnConfirmIsDisable = true;
    private float hargaTiket = 0;
    Navigation navigationHelper = new Navigation();
    DataTravel dataTravel = DataTravel.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startStage();
        this.session = (Map<String, Object>) dataTravel.getData("SESSION");

        try {
            setTableJadwal();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void checkKonfirm(ActionEvent actionEvent) {
        this.btnConfirmIsDisable = !this.btnConfirmIsDisable;
        btnConfirm.setDisable(this.btnConfirmIsDisable);
    }

    public void batal(ActionEvent actionEvent) {
        startStage();
        dataTravel.deleteData("kursiSelected");
    }

    public void buatTransaksi(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        final String idTrx = helper.generateIdTransaksi();
        Map<String, Object> transaksi = new HashMap<>();
        transaksi.put("id", idTrx);
        transaksi.put("idjadwal", dataTravel.getData("idjadwal"));
        transaksi.put("idbioskop", session.get("idbioskop"));
        transaksi.put("idkasir", session.get("iduser"));
        transaksi.put("total_bayar", Float.parseFloat(totalBayar.getText()));

        Database db = new Database();
        Connection conn = db.getConnection();

        try {
            conn.setAutoCommit(false);

            QueryBuilder qb = new QueryBuilder();
            db.execute(conn, qb.getQueryInsert("transaksi", transaksi));

            ArrayList<String> kursiSelected = (ArrayList<String>) dataTravel.getData("kursiSelected");
            qb.table("kursi_teater");
            qb.where("idteater = ?", dataTravel.getData("idteater"));
            qb.where("nama IN (?)", kursiSelected);
            ArrayList<Map<String, Object>> kursiResult = (ArrayList<Map<String, Object>>) db.execute(conn, qb.getQuerySelect());

            Map<String, Integer> kursiId = new HashMap<>();
            for (Map<String, Object> kursi : kursiResult) {
                kursiId.put((String) kursi.get("nama"), (Integer) kursi.get("id"));
            }

            ArrayList<Map<String, Object>> kursiTransaksi = new ArrayList<>();
            for (String kursi : kursiSelected) {
                Map<String, Object> insertData = new HashMap<>();
                insertData.put("idtransaksi", idTrx);
                insertData.put("idjadwal", dataTravel.getData("idjadwal"));
                insertData.put("idkursi", kursiId.get(kursi));
                kursiTransaksi.add(insertData);
            }

            if ((int) db.execute(conn, qb.geQuerytBulkInsert("transaksi_kursi", kursiTransaksi)) > 0) {
                btnPilihKursi.setVisible(true);
                startStage();
//                setValueTableTeater();
                System.out.println("berhasill");
            }

            conn.commit();
//            conn.rollback();
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

    public void pilihKursi(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        Database database = new Database();
        database.table("teater");
        database.where("idteater = ?", dataTravel.getData("idteater"));
        Map<String, Object> teaterResult = database.getOneMapResult();
        int jumlahKolom = (Integer) teaterResult.get("kolom");
        int jumlahBaris = (Integer) teaterResult.get("baris");

        int width = jumlahKolom * 70 + (jumlahKolom * 10);
        int height = jumlahBaris * 60 + (jumlahBaris * 10) + 110;

        System.out.println("width " + width);
        System.out.println("height " + height);

        navigationHelper.showModal(actionEvent, "Pilih Kursi", width, height, "pilihKursi", this::modalOnHideHandler, null);
    }

    private void modalOnHideHandler(WindowEvent we) {
        ArrayList<String> kursiSelected = (ArrayList<String>) dataTravel.getData("kursiSelected");
        if (kursiSelected.size() > 0) {
            noKursi.setText(String.join(", ", kursiSelected));
            pilihKursiSml.setVisible(true);
            btnPilihKursi.setVisible(false);
            totalBayar.setText(String.valueOf(hargaTiket * kursiSelected.size()));

            keteranganHarga.setVisible(true);
            keteranganHarga.setText("* Harga untuk " + kursiSelected.size() + " kursi terpilih");
        } else {
            pilihKursiSml.setVisible(false);
            btnPilihKursi.setVisible(true);
            keteranganHarga.setVisible(false);
        }
        System.out.println("kursi selected: " + dataTravel.getData("kursiSelected"));
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
                            this.helper.formatDateTimeFull((LocalDateTime) jadwal.get("show_at")),
                            (String) jadwal.get("judul_film"),
                            (String) jadwal.get("nama_bioskop"),
                            (String) jadwal.get("nama_teater"),
                            (LocalDateTime) jadwal.get("show_at")
                    )
            );
        }

    }

    private void startStage() {
        judulFilm.setText("Belum dipilih");
        teater.setText("Belum dipilih");
        noKursi.setText("");
        totalBayar.setText("Belum dipilih");
        btnPilihKursi.setDisable(true);
        dataTravel.deleteData("idteater");
        btnConfirm.setDisable(this.btnConfirmIsDisable);
        checkKonfirmBox.setSelected(false);
        keteranganHarga.setVisible(false);
        pilihKursiSml.setVisible(false);
        transaksiControl.setVisible(false);
    }

    public void selectItemTable(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        if (mouseEvent.getClickCount() == 2) {
            TableJadwalItem tableItem = tableJadwal.getSelectionModel().getSelectedItem();

            judulFilm.setText(tableItem.getJudul());
            teater.setText(tableItem.getNamaTeater());
            totalBayar.setText("0.0");
            btnPilihKursi.setDisable(false);

            dataTravel.addData("idjadwal", tableItem.getIdjadwal());
            dataTravel.addData("idteater", tableItem.getIdteater());

            hargaTiket = tableItem.getHarga();
            transaksiControl.setVisible(true);
        } else if (mouseEvent.getClickCount() == 1) {
            startStage();
            dataTravel.deleteData("idjadwal");
            dataTravel.deleteData("idteater");
            dataTravel.deleteData("kursiSelected");
            btnPilihKursi.setVisible(true);
            keteranganHarga.setVisible(false);
        }
    }
}
