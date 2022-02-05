package com.tiketly.tiketly.controller.adminController;

import database.Database;
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
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class KelolaBioskop extends AdminBase implements Initializable {
    DataTravel dataTravel = DataTravel.getInstance();

    public TableView<TableBioskopItem> tableBioskop;
    public ChoiceBox<String> filterProvinsi;
    public ChoiceBox<String> filterKota;

    //form add bioskop
    public TextField idBioskop;
    public TextField namaBioskop;
    public ChoiceBox<String> provinsiBioskop;
    public ChoiceBox<String> kotaBioskop;
    public Button btnLihatTeater;

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
                    if (!getKotaId(kotaBioskop.getValue()).equals("")){
                        idKota = Integer.parseInt(getKotaId(kotaBioskop.getValue()));
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
                    if (!getKotaId(filterKota.getValue()).equals("")){
                        idKotaFilter = Integer.parseInt(getKotaId(filterKota.getValue()));
                    }
                });
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
//            provinsiBioskop.setValue("Sumatera Utara");
    }

    public void simapanBioskop(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (idBioskop.getText().equals("")){
            insertBioskop();
        } else {
            System.out.println("updateeeee");
        }
    }

    private void insertBioskop() throws SQLException, ClassNotFoundException {
        if(namaBioskop.getText().equals("") || idProvinsi == 0 || idKota == 0){
            System.out.println("idprovinsi: "+ idProvinsi);
            System.out.println("idkota: "+ idKota);
            System.out.println("nama: "+ namaBioskop.getText());
            return;
        }

        Database database = new Database();
        Map<String, Object> data = new HashMap<>();
        data.put("idprovinsi", idProvinsi);
        data.put("idkota", idKota);
        data.put("nama", namaBioskop.getText());

        if (database.insert("bioskop", data) > 0) {
            database.table("bioskop");
            database.where("hapus = 0");
            database.order("created_at DESC");
            Map<String, Object> bioskopData = database.getOneMapResult();

            int tableItemsSize = tableBioskop.getItems().size();
            tableBioskop.getItems().add(
                    new TableBioskopItem(
                            (String) bioskopData.get("nama"),
                            provinsiBioskop.getValue(),
                            kotaBioskop.getValue(),
                            (int) bioskopData.get("idbioskop"),
                            0,
                            tableItemsSize+1
                    )
            );

            clearField();
            System.out.println("sukses");
        }

    }

    public void lihatTeater(ActionEvent actionEvent) throws IOException {
        dataTravel.addData("idBioskop", idBioskopInt);

        Navigation navigation = new Navigation();
        navigation.showModal(actionEvent, "Data Teater", 988, 650,"teaterModal");
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

    private String getKotaId(String rawVal){
        if (rawVal != null){
            return rawVal.substring(rawVal.indexOf("(") + 1, rawVal.indexOf(")"));
        }
        return "";
    }

    private void clearField(){
        namaBioskop.clear();
        idBioskop.clear();
        provinsiBioskop.setValue(null);
        filterProvinsi.setValue(null);
        idProvinsi = 0;
        idKota = 0;
    }

    private void setValueTableBioskop() throws SQLException, ClassNotFoundException {
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
        database.select("`bioskop`.*", "provinsi.nama as nama_provinsi", "kota.nama as nama_kota", "SUM(teater.idteater) as jumlah_teater");
        database.join("LEFT JOIN provinsi ON `bioskop`.idprovinsi = provinsi.idprovinsi");
        database.join("LEFT JOIN kota ON `kota`.idprovinsi = `bioskop`.idprovinsi AND kota.idkota = `bioskop`.idkota");
        database.join("LEFT JOIN teater ON teater.idbioskop = `bioskop`.idbioskop AND teater.hapus = 0");
        database.where("bioskop.hapus = 0");
        database.groupBy("bioskop.idbioskop");
        ArrayList<Map<String, Object>> bioskopResult = database.getArrayMapResult();

        for (int i = 0; i < bioskopResult.size(); i++) {
            Map<String, Object> bioskop = bioskopResult.get(i);
            int idbioskopInt = (int) bioskop.get("idbioskop");
            int jumlahTeater = 0;
            if (bioskop.get("jumlah_teater") != null){
                jumlahTeater = (int) bioskop.get("jumlah_teater");
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

    public void selectItemTable(MouseEvent mouseEvent) {
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

        } else if (mouseEvent.getClickCount() == 1){
            btnLihatTeater.setVisible(false);
            clearField();
        }
    }
}
