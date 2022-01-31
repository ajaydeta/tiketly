package com.tiketly.tiketly.controller.adminController;

import helper.Navigation;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class KelolaBioskop extends AdminBase{
    public TextField namaBioskop;
    public ChoiceBox provinsiBioskop;
    public ChoiceBox kotaBioskop;
    public ChoiceBox filterProvinsi;
    public ChoiceBox filterKota;
    public TableView tableBIoskop;
    public TextField kolomField;
    public TextField barisField;
    public TextField namaTeaterField;
    public TableView tableTeater;

    public void simapanBioskop(ActionEvent actionEvent) {
    }

    public void lihatTeater(ActionEvent actionEvent) throws IOException {
        Navigation navigation = new Navigation();
        navigation.showModal(actionEvent, "Data Teater", 988, 650,"teaterModal");
    }

    public void useFilter(ActionEvent actionEvent) {
    }

    public void deleteBioskop(ActionEvent actionEvent) {
    }

    public void simpanTeater(ActionEvent actionEvent) {
    }
}
