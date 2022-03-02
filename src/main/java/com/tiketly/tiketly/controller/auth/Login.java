package com.tiketly.tiketly.controller.auth;

import database.Database;
import helper.Helper;
import helper.Navigation;
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
    private String idOld = "";
    private String capchaImageName = "";
    private int loginAttempt = 1;
    private Map<String, Object> userData = new HashMap<>();
    private boolean idNotEmpty = false;
    private boolean passNotEmpty = false;
    private boolean capchaNotEmpty = false;

    Routes routes = new Routes();
    DataTravel dataTravel = DataTravel.getInstance();
    Navigation navigation = new Navigation();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        masukBtn.setDisable(true);

        idKasir.textProperty().addListener((observable, oldValue, newValue) -> {
            idKasir.setText(newValue.toUpperCase());
            idNotEmpty = !newValue.equals("");
            masukBtn.setDisable(!(idNotEmpty && passNotEmpty && capchaNotEmpty));
        });

        password.textProperty().addListener((observable, oldValue, newValue) -> {
            passNotEmpty = !newValue.equals("");
            masukBtn.setDisable(!(idNotEmpty && passNotEmpty && capchaNotEmpty));
        });

        capchaText.textProperty().addListener((observable, oldValue, newValue) -> {
            capchaNotEmpty = !newValue.equals("");
            masukBtn.setDisable(!(idNotEmpty && passNotEmpty && capchaNotEmpty));
        });

        setCapchaImage();
    }

    public void btnMasuk(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        String id = idKasir.getText();
        String pass = password.getText();
        String capcha = capchaText.getText();

        Database database = new Database();
        database.select();
        database.table("user");
        database.where("iduser = ?", id);
        database.where("hapus = ?", 0);
        this.userData = database.getOneMapResult();

        if (this.userData.isEmpty()) {
            navigation.showDialog("Gagal", "ID tidak terdaftar, periksa kembali.");
            return;
        }

        if ((boolean) this.userData.get("blokir")) {
            navigation.showDialog("Gagal", "Akun anda telah diblockir, silahkan hubungi admin.");
            return;
        }

        if (validateInput(pass, capcha)) {
            dataTravel.addData("SESSION", this.userData);
            if ((Integer) this.userData.get("role") == 1) {
                routes.toKelolaKasir(actionEvent);
            } else {
                routes.toHome(actionEvent);
            }
        } else {
            if (idOld.equals(id) && (Integer) this.userData.get("role") == 2) {
                loginAttempt++;
                if (loginAttempt > 2) {
                    Database database2 = new Database();
                    database2.table("user");
                    database2.where("iduser = ?", id);
                    database2.update("blokir", 1);

                    idKasir.clear();
                    navigation.showDialog("Gagal", "Akun anda telah diblockir, silahkan hubungi admin.");
                }
            } else {
                idOld = id;
                loginAttempt = 0;
            }
            password.clear();
            capchaText.clear();
        }


        System.out.println("loginAttempt: " + loginAttempt);
        if (capchaText.getText().equals(capchaImageName)) {
            System.out.println(capchaImageName);
        } else {
            capchaImageNameOld = capchaImageName;
            setCapchaImage();
        }
    }

    private boolean validateInput(String pass, String capcha) throws SQLException, ClassNotFoundException {
        if (!capcha.toLowerCase().equals(capchaImageName)) {
            navigation.showDialog("Gagal", "Capcha tidak sesuai");
            return false;
        }

        if (!pass.equals(this.userData.get("password"))) {
            navigation.showDialog("Gagal", "Password tidak sesuai.");
            return false;
        }
        return true;
    }

    private void setCapchaImage() {
        capchaImageName = helper.getCapcha(capchaImageNameOld);

        Image image = null;
        String full_path = "src/main/resources/com/tiketly/tiketly/assets/capcha/" + capchaImageName + ".jpg";
        System.out.println(full_path);
        try {
            image = new Image(new FileInputStream(full_path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        capchaImage.setImage(image);
    }
}
