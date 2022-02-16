package com.tiketly.tiketly.controller.modalController;

import com.tiketly.tiketly.controller.adminController.Pembelian;
import database.Database;
import helper.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import util.DataTravel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class FilterPembelian implements Initializable {
    public ComboBox<String> filterKasir;
    public ComboBox<String> filterBioskop;
    public DatePicker filterTanggalAwal;
    public DatePicker filterTanggalAkhir;

    DataTravel dataTravel = DataTravel.getInstance();
    Helper helper = new Helper();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (dataTravel.contains("filterBioskopRaw")) {
            filterBioskop.setValue((String) dataTravel.getData("filterBioskopRaw"));
        }

        if (dataTravel.contains("filterKasirRaw")) {
            filterKasir.setValue((String) dataTravel.getData("filterKasirRaw"));
        }

        if (dataTravel.contains("filterTanggalAkhir")) {
            filterTanggalAkhir.setValue(LocalDate.parse((String) dataTravel.getData("filterTanggalAkhir")));
        }

        if (dataTravel.contains("filterTanggalAwal")) {
            filterTanggalAwal.setValue(LocalDate.parse((String) dataTravel.getData("filterTanggalAwal")));
        }

        filterTanggalAwal.setConverter(
                new StringConverter<>() {
                    final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyy");

                    @Override
                    public String toString(LocalDate date) {
                        return (date != null) ? dateFormatter.format(date) : "";
                    }

                    @Override
                    public LocalDate fromString(String string) {
                        return (string != null && !string.isEmpty())
                                ? LocalDate.parse(string, dateFormatter)
                                : null;
                    }
                });

        filterTanggalAkhir.setConverter(
                new StringConverter<>() {
                    final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyy");

                    @Override
                    public String toString(LocalDate date) {
                        return (date != null) ? dateFormatter.format(date) : "";
                    }

                    @Override
                    public LocalDate fromString(String string) {
                        return (string != null && !string.isEmpty())
                                ? LocalDate.parse(string, dateFormatter)
                                : null;
                    }
                });

        try {
            setBioskopChoice();
            setKasirChoice();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setBioskopChoice() throws SQLException, ClassNotFoundException {
        Database database = new Database();
        database.table("bioskop");
        database.where("hapus = 0");
        ArrayList<Map<String, Object>> bioskopResult = database.getArrayMapResult();
        filterBioskop.getItems().clear();
        for(Map<String, Object> eachbioskop : bioskopResult){
            filterBioskop.getItems().add(helper.setIdDalamKurung(eachbioskop.get("nama"), eachbioskop.get("idbioskop")));
        }
    }

    private void setKasirChoice() throws SQLException, ClassNotFoundException {
        Database database = new Database();
        database.table("user");
        database.where("hapus = 0");
        database.where("role = 2");
        ArrayList<Map<String, Object>> bioskopResult = database.getArrayMapResult();
        filterKasir.getItems().clear();
        for(Map<String, Object> eachbioskop : bioskopResult){
            filterKasir.getItems().add(helper.setIdDalamKurung(eachbioskop.get("nama"), eachbioskop.get("iduser")));
        }
    }

    public void useFilter(ActionEvent actionEvent) {
        if (filterBioskop.getValue() != null){
            dataTravel.addData("filterBioskop", Integer.parseInt(helper.getIdDalamKurung(filterBioskop.getValue())));
            dataTravel.addData("filterBioskopRaw", filterBioskop.getValue());
        }

        if (filterKasir.getValue() != null){
            dataTravel.addData("filterKasir", helper.getIdDalamKurung(filterKasir.getValue()));
            dataTravel.addData("filterKasirRaw", filterKasir.getValue());
        }

        if (filterTanggalAkhir.getValue() != null) {
            dataTravel.addData("filterTanggalAkhir", filterTanggalAkhir.getValue().toString());
        }

        if (filterTanggalAwal.getValue() != null) {
            dataTravel.addData("filterTanggalAwal", filterTanggalAwal.getValue().toString());
        }

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();

    }

}
