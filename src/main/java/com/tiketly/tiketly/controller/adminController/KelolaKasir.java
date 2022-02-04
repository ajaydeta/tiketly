package com.tiketly.tiketly.controller.adminController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class KelolaKasir extends AdminBase implements Initializable {

    public TextField idKasir;
    public ChoiceBox bioskopKasir;
    public TextField telponKasir;
    public TextField namaKasir;
    public PasswordField passwordKasir;

    public TableView tableKaryawan;

    public TextField search;
    public ChoiceBox filterBioskop;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idKasir.setText(helper.generateIdUser());
//        telponKasir.setOnKeyPressed(new EventHandler<KeyEvent>()
//        {
//            public void handle(final KeyEvent keyEvent)
//            {
//                handleEvent(keyEvent);
//            }
//        });

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

    public void handleEvent(KeyEvent e)
    {
//        String raw = telponKasir.getText();
//        String minVal = raw.substring(0, raw.length() - 1);
//        if (telponKasir.getText().length() == 13){
//            telponKasir.setText(minVal);
//        }
    }

    public void validatePassword(InputMethodEvent inputMethodEvent) {
    }

    public void useFilter(ActionEvent actionEvent) {
    }
}
