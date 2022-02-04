package com.tiketly.tiketly.controller.modalController;

import com.tiketly.tiketly.controller.adminController.KelolaBioskop;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TeaterModal extends KelolaBioskop implements Initializable {
    public TableView tableTeater;
    public TextField namaTeaterField;
    public TextField kolomField;
    public TextField barisField;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("idBioskop: "+super.idBioskop.getText());
    }

}
