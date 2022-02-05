package com.tiketly.tiketly.controller.adminController;

import database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import model.TableBioskopItem;
import model.TableKasirItem;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class KelolaKasir extends AdminBase implements Initializable {

    public TextField idKasir;
    public TextField telponKasir;
    public TextField namaKasir;
    public PasswordField passwordKasir;
    public ChoiceBox<String> bioskopKasir;

    public TableView<TableKasirItem> tableKaryawan;

    public TextField search;
    public ChoiceBox<String> filterBioskop;
    public Label labelBioskopForm;
    public Button btnHapusKasir;

    private Map<String, Integer> bioskopIdMap = new HashMap<>();
    private boolean isUpdate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startStageView();

        try {
            setBioskopChoice();
            setValueTableKasir();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void simapanKasir(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (this.isUpdate){
            updateKasir();
        } else{
            insertKasir();
        }
    }

    public void useFilter(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        setValueTableKasir();
    }

    public void resetFilter(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        search.clear();
        filterBioskop.setValue(null);
        setValueTableKasir();
    }

    public void hapusKasir(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Database database = new Database();
        database.table("user");
        database.where("iduser = ?", idKasir.getText());
        if (database.update("hapus", 1) > 0){
            setValueTableKasir();
            clearField();
            startStageView();
        }
    }

    private void setBioskopChoice() throws SQLException, ClassNotFoundException {
        Database database = new Database();
        database.table("bioskop");
        database.select();
        database.where("hapus = 0");
        ArrayList<Map<String, Object>> bioskopResult =  database.getArrayMapResult();

        System.out.println(bioskopIdMap);

        for(Map<String, Object> bioskop : bioskopResult){
            bioskopIdMap.put((String) bioskop.get("nama"), (int) bioskop.get("idbioskop"));
            bioskopKasir.getItems().add((String) bioskop.get("nama"));
            filterBioskop.getItems().add((String) bioskop.get("nama"));
        }
    }

    private void setValueTableKasir() throws SQLException, ClassNotFoundException {
        TableColumn<TableKasirItem, String> noCol = new TableColumn<>("No");
        noCol.setCellValueFactory(new PropertyValueFactory<>("no"));

        TableColumn<TableKasirItem, String> idKasirCol = new TableColumn<>("ID Kasir");
        idKasirCol.setCellValueFactory(new PropertyValueFactory<>("idKasir"));

        TableColumn<TableKasirItem, String> namaCol = new TableColumn<>("Nama");
        namaCol.setCellValueFactory(new PropertyValueFactory<>("nama"));

        TableColumn<TableKasirItem, String> telpCol = new TableColumn<>("No Telpon");
        telpCol.setCellValueFactory(new PropertyValueFactory<>("telp"));

        TableColumn<TableKasirItem, String> namaBioskopCol = new TableColumn<>("Nama Bioskop");
        namaBioskopCol.setCellValueFactory(new PropertyValueFactory<>("namaBioskop"));

        TableColumn<TableKasirItem, String> createdAtCol = new TableColumn<>("Tanggal Terdaftar");
        createdAtCol.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        TableColumn<TableKasirItem, String> blokirCol = new TableColumn<>("Blokir");
        blokirCol.setCellValueFactory(new PropertyValueFactory<>("blokir"));

        tableKaryawan.getColumns().clear();
        tableKaryawan.getColumns().add(noCol);
        tableKaryawan.getColumns().add(idKasirCol);
        tableKaryawan.getColumns().add(namaCol);
        tableKaryawan.getColumns().add(telpCol);
        tableKaryawan.getColumns().add(namaBioskopCol);
        tableKaryawan.getColumns().add(blokirCol);
        tableKaryawan.getColumns().add(createdAtCol);

        tableKaryawan.getItems().clear();
        Database database = new Database();

        if (!search.getText().equals("")){
            database.where("`user`.nama LIKE ? OR `user`.telp LIKE ?", "%"+search.getText()+"%", "%"+search.getText()+"%");
        }

        if (filterBioskop.getValue() != null){
            database.where("`user`.idbioskop = ?", bioskopIdMap.get(filterBioskop.getValue()));
        }

        database.table("user");
        database.select("`user`.*", "bioskop.nama nama_bioskop");
        database.join("JOIN bioskop ON `user`.idbioskop = bioskop.idbioskop");
        database.where("`user`.role = ?", 2);
        database.where("`user`.hapus = ?", 0);
        ArrayList<Map<String, Object>> kasirResult = database.getArrayMapResult();

        for (int i = 0; i < kasirResult.size(); i++) {
            Map<String, Object> kasir = kasirResult.get(i);
            int idbioskopInt = (int) kasir.get("idbioskop");

            tableKaryawan.getItems().add(
              new TableKasirItem(
                      i+1,
                      idbioskopInt,
                      (boolean) kasir.get("blokir"),
                      (String) kasir.get("iduser"),
                      (String) kasir.get("password"),
                      (String) kasir.get("nama"),
                      (String) kasir.get("telp"),
                      (String) kasir.get("nama_bioskop"),
                      helper.formatDateTimeFull((Timestamp) kasir.get("created_at"))
              )
            );
        }
    }

    private void insertKasir() throws SQLException, ClassNotFoundException {
        String idKasirStr = idKasir.getText();
        String telponKasirStr = telponKasir.getText();
        String namaKasirStr = namaKasir.getText();
        String passwordKasirStr = passwordKasir.getText();
        String bioskopKasirStr = bioskopKasir.getValue();

        Database database = new Database();
        Map<String, Object> insertData = new HashMap<>();
        insertData.put("iduser" ,idKasirStr);
        insertData.put("nama" ,namaKasirStr);
        insertData.put("telp" ,telponKasirStr);
        insertData.put("password" ,passwordKasirStr);
        insertData.put("idbioskop", bioskopIdMap.get(bioskopKasirStr));

        if (database.insert("user", insertData) > 0){
            clearField();
            setValueTableKasir();
        }
    }

    private void updateKasir() throws SQLException, ClassNotFoundException {
        String idKasirStr = idKasir.getText();
        String telponKasirStr = telponKasir.getText();
        String namaKasirStr = namaKasir.getText();
        String passwordKasirStr = passwordKasir.getText();
        String bioskopKasirStr = bioskopKasir.getValue();

        Database database = new Database();
        database.table("user");
        database.where("iduser = ?", idKasirStr);
        Map<String, Object> insertData = new HashMap<>();
        insertData.put("nama" ,namaKasirStr);
        insertData.put("telp" ,telponKasirStr);
        insertData.put("password" ,passwordKasirStr);
        insertData.put("idbioskop", bioskopIdMap.get(bioskopKasirStr));
        if (database.updates(insertData) > 0){
            clearField();
            setValueTableKasir();
            startStageView();
        }
    }

    public void selectItemTable(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2)
        {
            labelBioskopForm.setText("Mutasi Ke Bioskop *");
            TableKasirItem tableItem = tableKaryawan.getSelectionModel().getSelectedItem();

            idKasir.setText(tableItem.getIdKasir());
            telponKasir.setText(tableItem.getTelp());
            namaKasir.setText(tableItem.getNama());
            passwordKasir.setText(tableItem.getPassword());
            bioskopKasir.setValue(tableItem.getNamaBioskop());

            this.isUpdate = true;
            btnHapusKasir.setVisible(true);

        } else if (mouseEvent.getClickCount() == 1){
            startStageView();
            clearField();
        }
    }

    private void clearField(){
        idKasir.setText(helper.generateIdUser());
        telponKasir.clear();
        namaKasir.clear();
        passwordKasir.clear();
        bioskopKasir.setValue(null);
    }

    private void startStageView(){
        this.isUpdate = false;
        btnHapusKasir.setVisible(false);
        idKasir.setText(helper.generateIdUser());
        labelBioskopForm.setText("Bekerja Di Bioskop *");
    }
}


