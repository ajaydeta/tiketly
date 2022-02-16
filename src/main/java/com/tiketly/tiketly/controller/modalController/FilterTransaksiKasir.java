package com.tiketly.tiketly.controller.modalController;

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
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class FilterTransaksiKasir implements Initializable {
    public ComboBox<String> filterTeater;
    public DatePicker filterTanggalAwal;
    public DatePicker filterTanggalAkhir;

    private Map<String, Object> session = new HashMap<>();
    DataTravel dataTravel = DataTravel.getInstance();
    Helper helper = new Helper();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (dataTravel.contains("filterTeaterRaw")) {
            filterTeater.setValue((String) dataTravel.getData("filterTeaterRaw"));
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

        this.session = (Map<String, Object>) dataTravel.getData("SESSION");
        try {
            setTeaterChoice();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setTeaterChoice() throws SQLException, ClassNotFoundException {
        Database database = new Database();
        database.table("teater");
        database.where("idbioskop = ?", session.get("idbioskop"));
        database.where("hapus = 0");
        ArrayList<Map<String, Object>> teaterResult = database.getArrayMapResult();
        filterTeater.getItems().clear();
        for(Map<String, Object> teater : teaterResult){
            filterTeater.getItems().add(helper.setIdDalamKurung(teater.get("nama"), teater.get("idteater")));
        }
    }

    public void useFilter(ActionEvent actionEvent) {
        if (filterTeater.getValue() != null){
            dataTravel.addData("filterTeater", Integer.parseInt(helper.getIdDalamKurung(filterTeater.getValue())));
            dataTravel.addData("filterTeaterRaw", filterTeater.getValue());
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

    public void reset(ActionEvent actionEvent) {
        filterTeater.setValue(null);
        filterTanggalAwal.setValue(null);
        filterTanggalAkhir.setValue(null);

        dataTravel.deleteData("filterTanggalAkhir");
        dataTravel.deleteData("filterTanggalAwal");
        dataTravel.deleteData("filterTeater");
        dataTravel.deleteData("filterTeaterRaw");
    }
}
