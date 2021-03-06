package com.tiketly.tiketly.controller.adminController;

import database.Database;
import helper.Helper;
import util.DataTravel;
import helper.Navigation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.TableBioskopItem;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class KelolaBioskop extends AdminBase implements Initializable {
    //    DataTravel dataTravel = DataTravel.getInstance();
    Helper helper = new Helper();

    public TableView<TableBioskopItem> tableBioskop;
    public ChoiceBox<String> filterProvinsi;
    public ChoiceBox<String> filterKota;

    //form add bioskop
    public TextField idBioskop;
    public TextField namaBioskop;
    public ChoiceBox<String> provinsiBioskop;
    public ChoiceBox<String> kotaBioskop;
    public Button btnLihatTeater;
    public Button btnHapusBioskop;

    protected int idProvinsi;
    protected int idKota;
    protected int idProvinsiFilter;
    protected int idKotaFilter;
    protected int idBioskopInt;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Database database = new Database();
        ArrayList<Map<String, Object>> provinsiResult = null;

        btnLihatTeater.setVisible(false);
        btnHapusBioskop.setVisible(false);

        database.select();
        database.table("provinsi");
        try {
            provinsiResult = database.getArrayMapResult();
            setValueTableBioskop();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < Objects.requireNonNull(provinsiResult).toArray().length; i++) {
            Map<String, Object> provinsi = provinsiResult.get(i);
            provinsiBioskop.getItems().add((String) provinsi.get("nama"));
            filterProvinsi.getItems().add((String) provinsi.get("nama"));
        }

        provinsiBioskop.setOnAction((event) -> {
            int selectedIndex = provinsiBioskop.getSelectionModel().getSelectedIndex() + 1;
            idProvinsi = selectedIndex;
            try {
                setKota(selectedIndex, kotaBioskop, (eventChild) -> {
                    if (!helper.getIdDalamKurung(kotaBioskop.getValue()).equals("")){
                        idKota = Integer.parseInt(helper.getIdDalamKurung(kotaBioskop.getValue()));
                    }
                });
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        filterProvinsi.setOnAction((event) -> {
            int selectedIndex = filterProvinsi.getSelectionModel().getSelectedIndex() + 1;
            idProvinsiFilter = selectedIndex;
            try {
                setKota(selectedIndex, filterKota, (eventChild) -> {
                    if (!helper.getIdDalamKurung(filterKota.getValue()).equals("")){
                        idKotaFilter = Integer.parseInt(helper.getIdDalamKurung(filterKota.getValue()));
                    }
                });
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
//            provinsiBioskop.setValue("Sumatera Utara");

        namaBioskop.textProperty().addListener((observable, oldValue, newValue) -> {
            namaBioskop.setText(this.inputUtil.inputNamaBioskop(newValue));
        });
    }

    public void simapanBioskop(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (idBioskop.getText().equals("")){
            insertBioskop();
        } else {
            updateBioskop();
        }
    }

    private void insertBioskop() throws SQLException, ClassNotFoundException {
        if (validateInputEmpty()){
            navigation.showDialog("Gagal", "Harap lengkapi form yang dibutuhkan!");
            return;
        }

        Database database = new Database();
        Map<String, Object> data = new HashMap<>();
        data.put("idprovinsi", idProvinsi);
        data.put("idkota", idKota);
        data.put("nama", namaBioskop.getText());

        if (database.insert("bioskop", data) > 0) {
            setValueTableBioskop();
            clearField();
            navigation.showDialog("Sukses", "Penambahan data bioskop telah berhasil");
        }

    }

    private void updateBioskop() throws SQLException, ClassNotFoundException {
        if (validateInputEmpty()){
            navigation.showDialog("Gagal", "Harap lengkapi form yang dibutuhkan!");
            return;
        }

        Database database = new Database();
        database.table("bioskop");
        database.where("idbioskop = ?", idBioskop.getText());

        Map<String, Object> data = new HashMap<>();
        data.put("idprovinsi", idProvinsi);
        data.put("idkota", idKota);
        data.put("nama", namaBioskop.getText());

        if (database.updates(data) > 0) {
            setValueTableBioskop();
            clearField();
            btnLihatTeater.setVisible(false);
            btnHapusBioskop.setVisible(false);
            navigation.showDialog("Sukses", "Pembaharuan data bioskop telah berhasil");
        }

    }

    public void lihatTeater(ActionEvent actionEvent) throws IOException {
        dataTravel.addData("idBioskop", idBioskopInt);
        navigation.showModal(actionEvent, "Data Teater", 988, 650,"teaterModal", null, null);
    }

    public void hapusBioskop(ActionEvent actionEvent) throws IOException {
        dataTravel.addData("idBioskop", idBioskopInt);
        navigation.showModal(actionEvent, "Hapus Bioskop", 322, 450,"hapusBioskopModal", null, null);
    }

    public void useFilter(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        setValueTableBioskop();
    }

    public void resetFilter(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        filterProvinsi.setValue(null);
        filterKota.setValue(null);

        this.idProvinsiFilter = 0;
        this.idKotaFilter = 0;

        setValueTableBioskop();
    }

    private void setKota(int idprovinsi, ChoiceBox<String> child, EventHandler<ActionEvent> eventHandler) throws SQLException, ClassNotFoundException {
        Database database = new Database();
        database.select();
        database.table("kota");
        database.where("idprovinsi = ?", idprovinsi);
        ArrayList<Map<String, Object>> kotaResult = database.getArrayMapResult();

        child.getItems().clear();
        for (Map<String, Object> kota : kotaResult) {
            child.getItems().add(kota.get("nama") + " (" + kota.get("idkota") + ")");
        }
        child.setOnAction(eventHandler);

    }

    private void clearField(){
        namaBioskop.clear();
        idBioskop.clear();
        provinsiBioskop.setValue(null);
        filterProvinsi.setValue(null);
        idProvinsi = 0;
        idKota = 0;
    }

    protected void setValueTableBioskop() throws SQLException, ClassNotFoundException {
        TableColumn<TableBioskopItem, String> noCol = new TableColumn<>("No");
        noCol.setCellValueFactory(new PropertyValueFactory<>("no"));

        TableColumn<TableBioskopItem, String> namaCol = new TableColumn<>("Nama");
        namaCol.setCellValueFactory(new PropertyValueFactory<>("nama"));

        TableColumn<TableBioskopItem, String> provinsiCol = new TableColumn<>("Provinsi");
        provinsiCol.setCellValueFactory(new PropertyValueFactory<>("provinsi"));

        TableColumn<TableBioskopItem, String> kotaCol = new TableColumn<>("Kota");
        kotaCol.setCellValueFactory(new PropertyValueFactory<>("kota"));

        TableColumn<TableBioskopItem, String> jumlahTeaterCol = new TableColumn<>("Jumlah Teater");
        jumlahTeaterCol.setCellValueFactory(new PropertyValueFactory<>("jumlahTeater"));

        tableBioskop.getColumns().clear();
        tableBioskop.getColumns().add(noCol);
        tableBioskop.getColumns().add(namaCol);
        tableBioskop.getColumns().add(provinsiCol);
        tableBioskop.getColumns().add(kotaCol);
        tableBioskop.getColumns().add(jumlahTeaterCol);

        tableBioskop.getItems().clear();
        Database database = new Database();

        if (this.idProvinsiFilter != 0){
            database.where("bioskop.idprovinsi = ?", this.idProvinsiFilter);
        }

        if (this.idKotaFilter != 0){
            database.where("bioskop.idkota = ?", this.idKotaFilter);
        }

        database.table("bioskop");
        database.select("`bioskop`.*", "provinsi.nama as nama_provinsi", "kota.nama as nama_kota", "COUNT(teater.idteater) as jumlah_teater");
        database.join("LEFT JOIN provinsi ON `bioskop`.idprovinsi = provinsi.idprovinsi");
        database.join("LEFT JOIN kota ON `kota`.idprovinsi = `bioskop`.idprovinsi AND kota.idkota = `bioskop`.idkota");
        database.join("LEFT JOIN teater ON teater.idbioskop = `bioskop`.idbioskop AND teater.hapus = 0");
        database.where("bioskop.hapus = 0");
        database.groupBy("bioskop.idbioskop");
        ArrayList<Map<String, Object>> bioskopResult = database.getArrayMapResult();

        for (int i = 0; i < bioskopResult.size(); i++) {
            Map<String, Object> bioskop = bioskopResult.get(i);
            int idbioskopInt = (int) bioskop.get("idbioskop");
            long jumlahTeater = 0;
            if (bioskop.get("jumlah_teater") != null){
                jumlahTeater = (long) bioskop.get("jumlah_teater");
            }
            tableBioskop.getItems().add(
                    new TableBioskopItem(
                            (String) bioskop.get("nama"),
                            (String) bioskop.get("nama_provinsi"),
                            bioskop.get("nama_kota")+" ("+bioskop.get("idkota")+")",
                            idbioskopInt,
                            jumlahTeater,
                            i+1
                    )
            );
        }
    }

    public void selectItemTable(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        if (mouseEvent.getClickCount() == 2)
        {
            TableBioskopItem tableItem = tableBioskop.getSelectionModel().getSelectedItem();
            System.out.println(tableItem.getIdBioskop());
            System.out.println(tableItem.getNama());
            System.out.println(tableItem.getKota());

            idBioskop.setText(Integer.toString(tableItem.getIdBioskop()));
            namaBioskop.setText(tableItem.getNama());
            provinsiBioskop.setValue(tableItem.getProvinsi());
            kotaBioskop.setValue(tableItem.getKota());

            this.idBioskopInt = tableItem.getIdBioskop();
            btnLihatTeater.setVisible(true);
            btnHapusBioskop.setVisible(true);

        } else if (mouseEvent.getClickCount() == 1){
            btnLihatTeater.setVisible(false);
            btnHapusBioskop.setVisible(false);
            clearField();
        }
    }

    private boolean validateInputEmpty(){
        return namaBioskop.getText() == null ||
                namaBioskop.getText().trim().equals("") ||
                provinsiBioskop.getValue() == null ||
                kotaBioskop.getValue() == null;
    }
}
