package com.tiketly.tiketly.controller.modalController;

import com.tiketly.tiketly.controller.adminController.JadwalFilm;
import database.Database;
import helper.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import util.DataTravel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class FilterJadwalModal extends JadwalFilm implements Initializable {
    public Button btnResetFilter;
    Helper helper = new Helper();
    DataTravel dataTravel = DataTravel.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (dataTravel.contains("filterBioskop")){
            filterBioskop.setValue((String) dataTravel.getData("filterBioskopRaw"));
        }


        if (dataTravel.contains("hargaMin")){
            String hargaMinStr = Float.toString((float) dataTravel.getData("hargaMin"));
            hargaMin.setText(hargaMinStr);
        }

        if (dataTravel.contains("hargaMax")){
            String hargaMaxStr = Float.toString((float) dataTravel.getData("hargaMax"));
            hargaMax.setText(hargaMaxStr);
        }

        if (dataTravel.contains("filterTanggalAkhir")){
            filterTanggalAkhir.setValue(LocalDate.parse((String) dataTravel.getData("filterTanggalAkhir")));
        }

        if (dataTravel.contains("filterTanggalAwal")){
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
            filterBioskop.getItems().add(eachbioskop.get("nama") + " (" + eachbioskop.get("idbioskop") + ")");
        }
    }

    public void resetFilter(ActionEvent actionEvent) {
        dataTravel.deleteData("filterBioskopRaw");
        dataTravel.deleteData("filterBioskop");
        dataTravel.deleteData("hargaMin");
        dataTravel.deleteData("hargaMax");
        dataTravel.deleteData("filterTanggalAkhir");
        dataTravel.deleteData("filterTanggalAwal");

        filterBioskop.setValue(null);
        hargaMin.setText("0.0");
        hargaMax.clear();
        filterTanggalAkhir.setValue(null);
        filterTanggalAwal.setValue(null);

    }
}
