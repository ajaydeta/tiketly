package com.tiketly.tiketly.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Play implements Initializable {
    public Label textLabel;

    public void combo(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        textLabel.setText("a d, m, i.n");
//        System.out.println(textLabel.getText());
//        System.out.println(Arrays.toString(textLabel.getText().split("\\d")));
//        textLabel.setText(String.join(":", textLabel.getText().split("\\d{1,2}")));

        String str = "15";
        String delimiters = "";

        // analyzing the string
        String[] tokensVal = str.split(delimiters);
        System.out.println(Arrays.toString(tokensVal));
        // prints the number of tokens
        System.out.println("Count of tokens = " + tokensVal.length);

            StringBuilder c= new StringBuilder();
        for (int i = 0; i < tokensVal.length; i++) {
            System.out.println(i + ": " + (i%2 == 0));
            c.append(tokensVal[i]).append(i - 1 % 2 == 0 ? ":" : "");
        }
        System.out.println(c);

        for(String token : tokensVal) {
            System.out.print(token);
        }
    }
}
