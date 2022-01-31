package com.tiketly.tiketly.controller.auth;

import helper.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {
    public Button masukBtn;
    Helper helper = new Helper();

    public TextField idKasir;
    public PasswordField password;
    public ImageView capchaImage;
    public TextField capchaText;
    private String capchaImageNameOld;
    private String capchaImageName = "";
    private int loginAttempt = 1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCapchaImage();
    }

    public void btnMasuk(ActionEvent actionEvent) {
        System.out.println("loginAttempt: "+loginAttempt);
        if (capchaText.getText().equals(capchaImageName)) {
            System.out.println(capchaImageName);
        } else {
            loginAttempt++;
            capchaImageNameOld = capchaImageName;
            setCapchaImage();
        }
    }

    private void setCapchaImage(){
        capchaImageName = helper.getCapcha(capchaImageNameOld);

        Image image = null;
        String full_path = "src/main/resources/com/tiketly/tiketly/assets/capcha/"+capchaImageName+".jpg";
        System.out.println(full_path);
        try {
            image = new Image(new FileInputStream(full_path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        capchaImage.setImage(image);
    }
}
