package com.tiketly.tiketly.controller.modalController;

import com.tiketly.tiketly.controller.kasirController.BuatTransaksi;
import database.Database;
import helper.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import util.DataTravel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class PilihKursi extends BuatTransaksi implements Initializable {
    private ArrayList<String> kursiSelected = new ArrayList<>();
    private final DataTravel dataTravel = DataTravel.getInstance();
    public GridPane gridKursi;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("idteater: " + dataTravel.getData("idteater"));
        if (dataTravel.contains("kursiSelected")) {
            this.kursiSelected = (ArrayList<String>) dataTravel.getData("kursiSelected");
        }

        try {
            setGridKursi();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setGridKursi() throws SQLException, ClassNotFoundException {
        Database database = new Database();
        database.table("teater");
        database.where("idteater = ?", dataTravel.getData("idteater"));
        database.where("hapus = ?", 0);
        Map<String, Object> teater = database.getOneMapResult();

        Database database2 = new Database();
        database2.table("jadwal");
        database2.select("kursi_teater.*", "IF(transaksi_kursi.idkursi IS NULL, 1, 0) as status_kursi", "teater.baris", "teater.kolom");
        database2.join("JOIN teater ON jadwal.idteater = teater.idteater");
        database2.join("JOIN kursi_teater ON kursi_teater.idteater = teater.idteater");
        database2.join("LEFT JOIN transaksi_kursi ON jadwal.idjadwal = transaksi_kursi.idjadwal AND kursi_teater.id = transaksi_kursi.idkursi");
        database2.where("jadwal.idjadwal = ?", dataTravel.getData("idjadwal"));
        ArrayList<Map<String, Object>> kursiResult = database2.getArrayMapResult();

        gridKursi.setHgap(10);
        gridKursi.setVgap(10);
        gridKursi.getColumnConstraints().clear();

        final String baseStyle = "-fx-font-size: 20; -fx-font-weight: bold;";
        final String tersedia = baseStyle + " -fx-background-color: #1B607B; -fx-text-fill: #FFC901;";
        final String dipesan = baseStyle + " -fx-background-color: #FFF89A; -fx-text-fill: #1B607B;";
        final String disable = baseStyle + " -fx-background-color: #828282; -fx-text-fill: #ffffff;";
        final String dipilihCustomer = baseStyle + " -fx-background-color: #FFC901; -fx-text-fill: #1B607B;";

        int kursiIndex = 0;
        for (int i = 0; i < (Integer) teater.get("baris"); i++) {
            for (int j = 0; j < (Integer) teater.get("kolom"); j++) {
                Map<String, Object> kursi = kursiResult.get(kursiIndex);
                final String namaKursi = (String) kursi.get("nama");

                Button button = new Button(namaKursi);
                button.setPrefSize(70, 50);

                if (this.kursiSelected.size() > 0 && this.kursiSelected.contains(namaKursi)) {
                    button.setStyle(dipilihCustomer);
                } else {
                    if ((Integer) kursi.get("status_kursi") == 1) {
                        button.setStyle(tersedia);
                    } else {
                        button.setStyle(dipesan);
                    }

                    if ((Boolean) kursi.get("hapus")) {
                        button.setStyle(disable);
                        button.setDisable(true);
                    }
                }

                button.setOnAction(actionEvent -> {
                    if (this.kursiSelected.size() > 0 && this.kursiSelected.contains(namaKursi)) {
                        button.setStyle(tersedia);
                        this.kursiSelected.remove(namaKursi);
                    } else {
                        this.kursiSelected.add(namaKursi);
                        button.setStyle(dipilihCustomer);
                    }
                    System.out.println(this.kursiSelected);
                });

                gridKursi.add(button, j, i);
                kursiIndex++;
            }
        }
    }

    public void konfirmasi(ActionEvent actionEvent) {
        dataTravel.addData("kursiSelected", this.kursiSelected);
        closeModal(actionEvent);
    }

    public void batalPilih(ActionEvent actionEvent) {
        this.kursiSelected.clear();
        closeModal(actionEvent);
    }

    private void closeModal(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}
