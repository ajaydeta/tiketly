package com.tiketly.tiketly.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class Play implements Initializable {
    public HBox hbox;
    public TilePane tile;
    public GridPane grid;
    public TableView table;
    public ComboBox<Object> combox;
    public AreaChart<String, Number> chart;
    public ImageView imageVIewId;

//    public static void setConstraints(Node var0, int var1, int var2, int var3, int var4) {
//        setRowIndex(var0, var2);
//        setColumnIndex(var0, var1);
//        setRowSpan(var0, var4);
//        setColumnSpan(var0, var3);
//    }

    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("APP start");

        try {
            Image img = new Image(new FileInputStream("src/main/resources/com/tiketly/tiketly/assets/jadwal-kasir.png"));
            imageVIewId.setImage(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Defining the X axis
//        CategoryAxis xAxis = new CategoryAxis();
//
////Defining the y Axis
//        NumberAxis yAxis = new NumberAxis(0, 15, 2.5);
//        yAxis.setLabel("Fruit units");
//
//        chart.getXAxis().setAutoRanging(true);
//        chart.getYAxis().setAutoRanging(true);
//
//        XYChart.Series series1 = new XYChart.Series();
//        series1.setName("John");
//
////        Node fill = series1.getNode().lookup(".chart-series-area-fill"); // only for AreaChart
////        Node line = series1.getNode().lookup(".chart-series-area-line");
//
//        Color color = Color.rgb(27,96,123); // or any other color
//
//        String rgb = String.format("%d, %d, %d",
//                (int) (color.getRed()),
//                (int) (color.getGreen()),
//                (int) (color.getBlue()));
//
//        series1.getNode().lookup(".chart-series-area-fill").setStyle("-fx-fill: rgba(" + rgb + ", 0.15);");
//        series1.getNode().lookup(".chart-series-area-line").setStyle("-fx-stroke: rgba(" + rgb + ", 1.0);");
//
//
//        series1.getData().add(new XYChart.Data("Monday", 3));
//        series1.getData().add(new XYChart.Data("Tuesday", 4));
//        series1.getData().add(new XYChart.Data("Wednesday", 3));
//        series1.getData().add(new XYChart.Data("Thursday", 5));
//        series1.getData().add(new XYChart.Data("Friday", 4));
//        series1.getData().add(new XYChart.Data("Saturday", 10));
//        series1.getData().add(new XYChart.Data("Sunday", 12));
//
//        XYChart.Series series2 = new XYChart.Series();
//        series2.setName("Jane");
//        series2.getData().add(new XYChart.Data("Monday", 1));
//        series2.getData().add(new XYChart.Data("Tuesday", 3));
//        series2.getData().add(new XYChart.Data("Wednesday", 4));
//        series2.getData().add(new XYChart.Data("Thursday", 3));
//        series2.getData().add(new XYChart.Data("Friday", 3));
//        series2.getData().add(new XYChart.Data("Saturday", 5));
//        series2.getData().add(new XYChart.Data("Sunday", 4));
//
//        if(chart.getData().size() > 0)//check to see if the .size() part is correct.
//        {
//            chart.getData().remove(0);
//        }
//
//        chart.getData().addAll(series1,series2);

//        ObservableList<Object> x =  FXCollections.observableArrayList();
//
//        for (int i = 0; i < 100; i++) {
//            x.add(new UserCell("hahaha" + i, "huhuh", "hehehe", "hohoh"));
//        }
//
//        combox.valueProperty().addListener((obs, oldVal, newVal) -> {
//            String selectionText = "Price of the " + newVal.toString();
//
//            System.out.println(selectionText);
//        });
//
//        combox.setItems(x);

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
