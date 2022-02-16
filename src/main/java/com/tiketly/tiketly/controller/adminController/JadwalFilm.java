package com.tiketly.tiketly.controller.adminController;

import database.Database;
import helper.Navigation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.TableBioskopItem;
import model.TableJadwalItem;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class JadwalFilm extends AdminBase implements Initializable {
    public TableView<TableJadwalItem> tableJadwal;

    //    FORM ADD
    public ChoiceBox<String> judul;
    public ChoiceBox<String> bioskop;
    public ChoiceBox<String> teater;
    public DatePicker tanggalTayang;
    public TextField jamTayang;
    public TextField hargaField;

    public VBox filterJadwalModal;
    public ComboBox<String> filterBioskop;
    public DatePicker filterTanggalAkhir;
    public DatePicker filterTanggalAwal;
    public TextField hargaMin;
    public TextField hargaMax;
    public TextField idjadwal;

    private Map<String, Integer> filmIdMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataTravel.deleteData("filterBioskop");
        dataTravel.deleteData("filterBioskopRaw");
        dataTravel.deleteData("hargaMin");
        dataTravel.deleteData("hargaMax");
        dataTravel.deleteData("filterTanggalAkhir");
        dataTravel.deleteData("filterTanggalAwal");

        try {
            setFilmChoice();
            setBioskopChoice();
            setTableJadwal();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void simapanJadwal(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (!idjadwal.getText().equals("")){
            updateJadwal();
        } else {
            insertJadwal();
        }
    }

    private void insertJadwal() throws SQLException, ClassNotFoundException {
        String judulStr = judul.getValue();
        int idbioskop = Integer.parseInt(helper.getIdDalamKurung(bioskop.getValue()));
        int idteater = Integer.parseInt(helper.getIdDalamKurung(teater.getValue()));
        String tanggalTayangStr = tanggalTayang.getValue().toString();
        String jamTayangStr = jamTayang.getText();
        float harga = Float.parseFloat(hargaField.getText());

        Map<String, Object> data = new HashMap<>();
        data.put("idbioskop", idbioskop);
        data.put("idteater", idteater);
        data.put("idfilm", filmIdMap.get(judulStr));
        data.put("harga", harga);
        data.put("show_at", tanggalTayangStr + "T" + jamTayangStr);

        Database database = new Database();
        if (database.insert("jadwal", data) > 0) {
            clearField();
            setTableJadwal();
        }
    }

    private void updateJadwal() throws SQLException, ClassNotFoundException {
        String judulStr = judul.getValue();
        int idbioskop = Integer.parseInt(helper.getIdDalamKurung(bioskop.getValue()));
        int idteater = Integer.parseInt(helper.getIdDalamKurung(teater.getValue()));
        String tanggalTayangStr = tanggalTayang.getValue().toString();
        String jamTayangStr = jamTayang.getText();
        float harga = Float.parseFloat(hargaField.getText());

        Map<String, Object> data = new HashMap<>();
        data.put("idbioskop", idbioskop);
        data.put("idteater", idteater);
        data.put("idfilm", filmIdMap.get(judulStr));
        data.put("harga", harga);
        data.put("show_at", tanggalTayangStr + "T" + jamTayangStr);

        Database database = new Database();
        database.table("jadwal");
        database.where("idjadwal = ?",  Integer.parseInt(idjadwal.getText()));
        if (database.updates(data) > 0){
            clearField();
            setTableJadwal();
        }
    }

    public void openFilterJadwal(ActionEvent actionEvent) throws IOException {
        Navigation navigation = new Navigation();
        navigation.showModal(actionEvent, "Filter Jadwal Film", 322, 470, "filterJadwalFilm", this::onHideHandler, null);
    }

    private void onHideHandler(WindowEvent we){
        System.out.println("modal closedd");
        try {
            setTableJadwal();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void useFilter(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (filterBioskop.getValue() != null){
            dataTravel.addData("filterBioskop", Integer.parseInt(helper.getIdDalamKurung(filterBioskop.getValue())));
            dataTravel.addData("filterBioskopRaw", filterBioskop.getValue());
        }

        if (filterTanggalAkhir.getValue() != null) {
            dataTravel.addData("filterTanggalAkhir", filterTanggalAkhir.getValue().toString());
        }

        if (filterTanggalAwal.getValue() != null) {
            dataTravel.addData("filterTanggalAwal", filterTanggalAwal.getValue().toString());
        }

        if (!hargaMin.getText().equals("")) {
            dataTravel.addData("hargaMin", Float.parseFloat(hargaMin.getText()));
        }

        if (!hargaMax.getText().equals("")) {
            dataTravel.addData("hargaMax", Float.parseFloat(hargaMax.getText()));
        }

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
//        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    private void setFilmChoice() throws SQLException, ClassNotFoundException {
        Database database = new Database();
        database.table("film");
        database.where("hapus = 0");
        ArrayList<Map<String, Object>> filmResult = database.getArrayMapResult();
        judul.getItems().clear();
        for (Map<String, Object> film : filmResult) {
            filmIdMap.put((String) film.get("judul"), (int) film.get("id"));
            judul.getItems().add((String) film.get("judul"));
        }
    }

    private void setBioskopChoice() throws SQLException, ClassNotFoundException {
        Database database = new Database();
        database.table("bioskop");
        database.where("hapus = 0");
        ArrayList<Map<String, Object>> bioskopResult = database.getArrayMapResult();
        bioskop.getItems().clear();
        for (Map<String, Object> eachbioskop : bioskopResult) {
            bioskop.getItems().add(eachbioskop.get("nama") + " (" + eachbioskop.get("idbioskop") + ")");
        }
        bioskop.setOnAction(event -> {
            if (bioskop.getValue() != null) {
                int idbioskopInt = Integer.parseInt(helper.getIdDalamKurung(bioskop.getValue()));
                try {
                    setTeaterChoice(idbioskopInt);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setTeaterChoice(int idbioskop) throws SQLException, ClassNotFoundException {
        Database database = new Database();
        database.table("teater");
        database.where("hapus = 0");
        database.where("idbioskop = ?", idbioskop);
        ArrayList<Map<String, Object>> teaterResult = database.getArrayMapResult();
        teater.getItems().clear();
        for (Map<String, Object> eachTeater : teaterResult) {
            teater.getItems().add(eachTeater.get("nama") + " (" + eachTeater.get("idteater") + ")");
        }
    }

    private void setTableJadwal() throws SQLException, ClassNotFoundException {
        TableColumn<TableJadwalItem, String> noCol = new TableColumn<>("No");
        noCol.setCellValueFactory(new PropertyValueFactory<>("no"));

        TableColumn<TableJadwalItem, String> judulCol = new TableColumn<>("Judul Film");
        judulCol.setCellValueFactory(new PropertyValueFactory<>("judul"));

        TableColumn<TableJadwalItem, String> bioskopCol = new TableColumn<>("Bioskop");
        bioskopCol.setCellValueFactory(new PropertyValueFactory<>("namaBioskop"));

        TableColumn<TableJadwalItem, String> teaterCol = new TableColumn<>("Teater");
        teaterCol.setCellValueFactory(new PropertyValueFactory<>("namaTeater"));

        TableColumn<TableJadwalItem, String> jadwalCol = new TableColumn<>("Jadwal Tayang");
        jadwalCol.setCellValueFactory(new PropertyValueFactory<>("showAt"));

        TableColumn<TableJadwalItem, String> hargaCol = new TableColumn<>("Harga Tiket");
        hargaCol.setCellValueFactory(new PropertyValueFactory<>("harga"));

        tableJadwal.getColumns().clear();
        tableJadwal.getColumns().add(noCol);
        tableJadwal.getColumns().add(judulCol);
        tableJadwal.getColumns().add(bioskopCol);
        tableJadwal.getColumns().add(teaterCol);
        tableJadwal.getColumns().add(jadwalCol);
        tableJadwal.getColumns().add(hargaCol);

        tableJadwal.getItems().clear();
        Database database = new Database();

        if (dataTravel.contains("filterBioskop")){
            database.where("jadwal.idbioskop = ?", this.dataTravel.getData("filterBioskop"));
        }


        if (dataTravel.contains("hargaMin")){
            database.where("jadwal.harga >= ?", this.dataTravel.getData("hargaMin"));
        }

        if (dataTravel.contains("hargaMax")){
            database.where("jadwal.harga <= ?", this.dataTravel.getData("hargaMax"));
        }

        if (dataTravel.contains("filterTanggalAkhir")){
            database.where("jadwal.show_at <= ?", this.dataTravel.getData("filterTanggalAkhir") + " 23:59:59");
        }

        if (dataTravel.contains("filterTanggalAwal")){
            database.where("jadwal.show_at >= ?", this.dataTravel.getData("filterTanggalAwal") + " 00:00:00");
        }

        database.table("jadwal");
        database.select("jadwal.*", "film.judul as judul_film", "bioskop.nama as nama_bioskop", "teater.nama as nama_teater");
        database.join("JOIN film ON film.id = jadwal.idfilm ");
        database.join("JOIN bioskop ON bioskop.idbioskop = jadwal.idbioskop");
        database.join("JOIN teater ON teater.idteater = jadwal.idteater");
        database.where("jadwal.hapus = ?", 0);
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
                            helper.formatDateTimeFull((Timestamp) jadwal.get("show_at")),
                            (String) jadwal.get("judul_film"),
                            (String) jadwal.get("nama_bioskop"),
                            (String) jadwal.get("nama_teater"),
                            (Timestamp) jadwal.get("show_at")
                    )
            );
        }

    }

    private void clearField() {
        judul.setValue(null);
        bioskop.setValue(null);
        teater.setValue(null);
//        teater.getItems().clear();
        tanggalTayang.setValue(null);
        jamTayang.clear();
        hargaField.clear();
        idjadwal.clear();
    }

    public void selectItemTable(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        if (mouseEvent.getClickCount() == 2)
        {
//            teater.getItems().clear();
            TableJadwalItem tableItem = tableJadwal.getSelectionModel().getSelectedItem();

            bioskop.setValue(helper.setIdDalamKurung(tableItem.getNamaBioskop(), tableItem.getIdbioskop()));
            judul.setValue(tableItem.getJudul());
            teater.setValue(helper.setIdDalamKurung(tableItem.getNamaTeater(), tableItem.getIdteater()));
            tanggalTayang.setValue(tableItem.getShowAtRaw().toLocalDateTime().toLocalDate());
            jamTayang.setText(tableItem.getShowAtRaw().toLocalDateTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            hargaField.setText(Float.toString(tableItem.getHarga()));
            idjadwal.setText(Integer.toString(tableItem.getIdjadwal()));

        } else if (mouseEvent.getClickCount() == 1){
//            btnLihatTeater.setVisible(false);
            clearField();
        }
    }
}
