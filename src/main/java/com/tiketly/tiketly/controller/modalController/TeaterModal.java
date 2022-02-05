package com.tiketly.tiketly.controller.modalController;

import com.tiketly.tiketly.controller.adminController.KelolaBioskop;
import database.Database;
import util.DataTravel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.TableTeaterItem;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class TeaterModal extends KelolaBioskop implements Initializable {
    public Button btnHapusTeater;
    DataTravel dataTravel = DataTravel.getInstance();

    public TableView<TableTeaterItem> tableTeater;
    public TextField namaTeaterField;
    public TextField kolomField;
    public TextField barisField;
    public TextField idTeater;
    private int idTeaterInt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("idBioskop: "+dataTravel.getData("idBioskop"));
        try {
            setValueTableTeater();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        btnHapusTeater.setVisible(false);
    }

    public void deleteBioskop(ActionEvent actionEvent) {
    }

    public void simpanTeater(ActionEvent actionEvent) {
        System.out.println("namaTeaterField: "+namaTeaterField.getText());
        System.out.println("kolomField: "+kolomField.getText());
        System.out.println("barisField: "+barisField.getText());
    }

    private void setValueTableTeater() throws SQLException, ClassNotFoundException {
        TableColumn<TableTeaterItem, String> noCol = new TableColumn<>("No");
        noCol.setCellValueFactory(new PropertyValueFactory<>("no"));

        TableColumn<TableTeaterItem, String> namaCol = new TableColumn<>("Nama");
        namaCol.setCellValueFactory(new PropertyValueFactory<>("nama"));

        TableColumn<TableTeaterItem, String> barisCol = new TableColumn<>("Jumlah Baris");
        barisCol.setCellValueFactory(new PropertyValueFactory<>("baris"));

        TableColumn<TableTeaterItem, String> kolomCol = new TableColumn<>("Jumlah Kolom");
        kolomCol.setCellValueFactory(new PropertyValueFactory<>("kolom"));

        tableTeater.getColumns().clear();
        tableTeater.getColumns().add(noCol);
        tableTeater.getColumns().add(namaCol);
        tableTeater.getColumns().add(barisCol);
        tableTeater.getColumns().add(kolomCol);

        tableTeater.getItems().clear();
        Database database = new Database();
        database.table("teater");
        database.where("idbioskop = ?", dataTravel.getData("idBioskop"));
        database.where("hapus = 0");
        ArrayList<Map<String, Object>> teaterResult = database.getArrayMapResult();

        for (int i = 0; i < teaterResult.size(); i++) {
            Map<String, Object> teater = teaterResult.get(i);
            int idteaterInt = (int) teater.get("idteater");
            int baris = (int) teater.get("baris");
            int kolom = (int) teater.get("kolom");

            tableTeater.getItems().add(
                    new TableTeaterItem(
                     i+1,
                            idteaterInt,
                            baris,
                            kolom,
                            (String) teater.get("nama")
                    )
            );
        }
    }

    public void selectItemTable(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2)
        {
            TableTeaterItem tableItem = tableTeater.getSelectionModel().getSelectedItem();
            System.out.println(tableItem.getId());
            System.out.println(tableItem.getNama());
            System.out.println(tableItem.getBaris());
            System.out.println(tableItem.getKolom());

            idTeater.setText(Integer.toString(tableItem.getId()));
            namaTeaterField.setText(tableItem.getNama());
            barisField.setText(Integer.toString(tableItem.getBaris()));
            kolomField.setText(Integer.toString(tableItem.getKolom()));

            this.idTeaterInt = tableItem.getId();
            btnHapusTeater.setVisible(true);

        } else if (mouseEvent.getClickCount() == 1){
            btnLihatTeater.setVisible(false);
            clearField();
        }
    }

    public void hapusTeater(ActionEvent actionEvent) {
    }

    private void clearField(){
        idTeater.clear();;
        namaTeaterField.clear();;
        barisField.clear();;
        kolomField.clear();;
        idTeaterInt = 0;
    }
}
