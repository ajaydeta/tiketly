package com.tiketly.tiketly.controller.adminController;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class KelolaKasir extends AdminBase implements Initializable {

    public ChoiceBox bioskopKasir;
    public TextField telponKasir;
    public PasswordField passwordKasir;
    public TextField namaKasir;
    public TextField idKasir;
    public ChoiceBox filterBioskop;
    public TextField search;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("kelolaKasirrrrrrr");
    }

    public void simapanKasir(ActionEvent actionEvent) {
    }

    public void validateTelp(InputMethodEvent inputMethodEvent) {
        System.out.println("getCommitted "+inputMethodEvent.getCommitted());
        System.out.println("getCaretPosition "+inputMethodEvent.getCaretPosition());
        System.out.println("getEventType "+inputMethodEvent.getEventType().getName());
        System.out.println("getComposed "+inputMethodEvent.getComposed().toString());

    }

    public void validatePassword(InputMethodEvent inputMethodEvent) {
    }

    public void useFilter(ActionEvent actionEvent) {
    }
}
