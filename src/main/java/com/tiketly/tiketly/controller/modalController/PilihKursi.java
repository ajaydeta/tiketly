package com.tiketly.tiketly.controller.modalController;

import com.tiketly.tiketly.controller.kasirController.BuatTransaksi;
import database.Database;
import helper.Helper;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import util.DataTravel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class PilihKursi extends BuatTransaksi implements Initializable {
    DataTravel dataTravel = DataTravel.getInstance();
    Helper helper = new Helper();
    public GridPane gridKursi;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("idteater: "+dataTravel.getData("idteater"));

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
        database2.table("kursi_teater");
        database2.where("idteater = ?",dataTravel.getData("idteater"));
        ArrayList<Map<String, Object>> kursiResult = database2.getArrayMapResult();

        gridKursi.setHgap(10);
        gridKursi.setVgap(10);

        gridKursi.getColumnConstraints().clear();

        String baseStyle = "-fx-font-size: 20; -fx-font-weight: bold;";
        int kursiIndex = 0;
        for (int i = 0; i < (Integer) teater.get("baris"); i++) {
            for (int j = 0; j < (Integer) teater.get("kolom"); j++) {
                Map<String, Object> kursi = kursiResult.get(kursiIndex);
                Button button = new Button((String) kursi.get("nama"));

                button.setOnAction(actionEvent -> {
                    button.setStyle(baseStyle+" -fx-background-color: #FFC901; -fx-text-fill: #1B607B;");
                });

                button.setPrefSize(70, 50);

                //1 tersedia, 2 dipesan, 3 rusak
                switch ((Integer) kursi.get("status")){
                    case 1:
                        button.setStyle(baseStyle+" -fx-background-color: #1B607B; -fx-text-fill: #FFC901;");
                        break;
                    case 2:
                        button.setStyle(baseStyle+" -fx-background-color: #FFF89A; -fx-text-fill: #1B607B;");
                        break;
                    case 3:
                        button.setStyle(baseStyle+" -fx-background-color: #828282; -fx-text-fill: #ffffff;");
                        break;
                }

                if ((Boolean) kursi.get("hapus")){
                    button.setStyle(baseStyle+" -fx-background-color: #828282; -fx-text-fill: #ffffff;");
                }

                if ((Boolean) kursi.get("hapus") || (Integer) kursi.get("status") != 1){
                    button.setDisable(true);
                }

                gridKursi.add(button, j, i);
                kursiIndex++;
            }
        }
    }
}
