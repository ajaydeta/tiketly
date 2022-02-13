package com.tiketly.tiketly.controller.auth;

import database.Database;
import helper.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import routes.Routes;
import util.DataTravel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Login implements Initializable {
    Helper helper = new Helper();

    public Button masukBtn;
    public TextField idKasir;
    public PasswordField password;
    public ImageView capchaImage;
    public TextField capchaText;
    private String capchaImageNameOld;
    private String capchaImageName = "";
    private int loginAttempt = 1;
    private Map<String, Object> userData = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCapchaImage();
    }

    public void btnMasuk(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        String id = idKasir.getText();
        String pass = password.getText();
        String capcha = capchaText.getText();

        if (validateInput(id, pass, capcha)){
            Routes routes = new Routes();
            DataTravel dataTravel = DataTravel.getInstance();
            dataTravel.addData("SESSION", this.userData);
            if ((Integer) this.userData.get("role") == 1){
                routes.toKelolaKasir(actionEvent);
            } else{
                routes.toHome(actionEvent);
            }
        } else {
            loginAttempt++;
            if (loginAttempt > 3){
                Database database = new Database();
                database.table("user");
                database.where("iduser = ?", id);
                database.update("blokir", 1);
            }
        }


        System.out.println("loginAttempt: "+loginAttempt);
        if (capchaText.getText().equals(capchaImageName)) {
            System.out.println(capchaImageName);
        } else {
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

    private boolean validateInput(String id, String pass, String capcha) throws SQLException, ClassNotFoundException {
        if (id.equals("")){
            return false;
        }

        if (pass.equals("")){
            return false;
        }

        if (capcha.equals("")){
            return false;
        }

        if (!capcha.toLowerCase().equals(capchaImageName)){
            return false;
        }

        Database database = new Database();
        database.select();
        database.table("user");
        database.where("iduser = ?", id);
        database.where("hapus = ?", 0);
        this.userData = database.getOneMapResult();

        if ((boolean) this.userData.get("blokir")){
            return false;
        }

        return pass.equals(this.userData.get("password"));
    }
}
