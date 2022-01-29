package com.tiketly.tiketly.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import model.UserCell;

import java.net.URL;
import java.util.ResourceBundle;

public class Play implements Initializable {
    public HBox hbox;
    public TilePane tile;
    public GridPane grid;
    public TableView table;
    public ComboBox<Object> combox;

//    public static void setConstraints(Node var0, int var1, int var2, int var3, int var4) {
//        setRowIndex(var0, var2);
//        setColumnIndex(var0, var1);
//        setRowSpan(var0, var4);
//        setColumnSpan(var0, var3);
//    }

    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("APP start");

        ObservableList<Object> x =  FXCollections.observableArrayList();

        for (int i = 0; i < 100; i++) {
            x.add(new UserCell("hahaha" + i, "huhuh", "hehehe", "hohoh"));
        }

        combox.valueProperty().addListener((obs, oldVal, newVal) -> {
            String selectionText = "Price of the " + newVal.toString();

            System.out.println(selectionText);
        });

        combox.setItems(x);

//        TableColumn<UserCell, String> idCol = new TableColumn<>("ID Pengguna");
//        idCol.setCellValueFactory(new PropertyValueFactory<>("idUser"));
//
//        TableColumn<UserCell, String> namaCol = new TableColumn<>("Nama");
//        namaCol.setCellValueFactory(new PropertyValueFactory<>("nama"));
//
//        TableColumn<UserCell, String> noCol = new TableColumn<>("No Telpon");
//        noCol.setCellValueFactory(new PropertyValueFactory<>("telp"));
//
//        TableColumn<UserCell, String> dateCol = new TableColumn<>("Tanggal lahir");
//        dateCol.setCellValueFactory(new PropertyValueFactory<>("tglLahir"));
//
//        table.getColumns().add(idCol);
//        table.getColumns().add(namaCol);
//        table.getColumns().add(noCol);
//        table.getColumns().add(dateCol);
//
//        for (int i = 0; i < 100; i++) {
//            table.getItems().add(new UserCell("hahaha" + i, "huhuh", "hehehe", "hohoh"));
//        }
//

//        List<String> indexBtn = new ArrayList<>();

//        int baris = 0;
//        for (int i = 0; i < 25; i++) {
//            Button button = new Button(Integer.toString(i));
//            int finalI = i;
//            button.setOnAction(event -> {
//                indexBtn.add("x" + finalI);
//                System.out.println(indexBtn);
//            });
//            button.setPrefWidth(100);
//            if (i % 5 == 0) {
//                baris++;
//            }
//            if (i %4 == 0){
//                button.setDisable(true);
//            }
//            System.out.printf("%d > kolom %d : baris %d (i mod 5 = %d)\n", i, i % 5, baris, i % 5 );
//            grid.add(button, i % 5, baris);
//
//        }
//        grid.setHgap(10);
//        grid.setVgap(10);
//        System.out.println(indexBtn);

    }

    public void combo(ActionEvent actionEvent) {

    }
}
