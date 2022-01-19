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

public class Navigation extends Helper{
    public void navigate(ActionEvent actionEvent, String path) throws IOException {
        URL url = Paths.get(path).toUri().toURL();
        Parent root = FXMLLoader.load(url); // loads scene
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow(); // Gets event source(button) then gets the stage(window)

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(false);
        stage.setMaximized(true);
        stage.setMinWidth(750);
        stage.setMinHeight(540);

        stage.show();
    }

}
