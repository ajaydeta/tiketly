package helper;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Date;

public class Helper {
    public String generateIdUser(){
        Date date = new Date();
        int floor = (int) Math.floor(Math.random() * (9999 - 1000 + 1) + 1000);
        return "USR"+ date.getTime() + floor;
    }
}
