package com.tiketly.tiketly.controller.adminController;

import database.Database;
import helper.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.WindowEvent;
import model.ReportModel;
import model.TablePembelianItem;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

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

    public void exportLaporan(ActionEvent actionEvent) throws JRException, SQLException, ClassNotFoundException {
        Database database = new Database();
        database.table("transaksi");
        database.select("transaksi.id AS noNota","`user`.nama AS namaKasir","film.judul","bioskop.nama AS bioskop","jadwal.harga","transaksi.total_bayar AS total","COUNT( transaksi_kursi.id ) AS jumlahKursi","transaksi.created_at AS createdAt");
        database.join("JOIN `user` ON `user`.iduser = transaksi.idkasir");
        database.join("JOIN jadwal ON jadwal.idjadwal = transaksi.idjadwal");
        database.join("JOIN film ON jadwal.idfilm = film.id");
        database.join("JOIN bioskop ON bioskop.idbioskop = transaksi.idbioskop");
        database.join("JOIN transaksi_kursi ON transaksi_kursi.idtransaksi = transaksi.id");
        database.groupBy("transaksi.id");
        ArrayList<Map<String, Object>> reportResult = database.getArrayMapResult();
        ArrayList<ReportModel> dataSource = new ArrayList<>();

        for (Map<String, Object> report : reportResult){
            dataSource.add(new ReportModel(
                    (String) report.get("noNota"),
                    (String) report.get("namaKasir"),
                    (String) report.get("judul"),
                    (String) report.get("bioskop"),
                    helper.formatDateTimeFull((LocalDateTime) report.get("createdAt")),
                    (long) report.get("jumlahKursi"),
                    (float) report.get("total"),
                    (float) report.get("harga")
            ));
        }


        String template = "src/main/resources/com/tiketly/tiketly/reportTemplate.jrxml";
        Date d = new Date();
        LocalDateTime l = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        String fileName = "ReportTransaksiTiketly_" + helper.formatDateTimeFullNoSpace(l) + ".pdf";
//        String fileName = "ReportTransaksiTiketly.pdf";
        String dest = "src/main/resources/com/tiketly/tiketly/"+fileName;

        File file = new File(template);
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("tanggalExport", helper.formatDateTimeFull(l));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(dataSource));
        JasperExportManager.exportReportToPdfFile(jasperPrint, dest);

        File source = new File(dest);
        File destination = new File(System.getProperty("user.home") + "/Desktop/"+fileName);

        if (!destination.exists()) {
            source.renameTo(destination);
        }
        navigation.showDialog("Sukses", "Export sukses, sistem akan secara default menempatkan hasil di desktop anda.");
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
                            helper.formatDateTimeFull((LocalDateTime) transaksi.get("created_at")),
                            (String) transaksi.get("kasir"),
                            (float) transaksi.get("total_bayar"),
                            (long) transaksi.get("jumlah_kursi")
                    )
            );
        }
    }
}
