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
        Scene nodeScene = ((Node) actionEvent.getSource()).getScene();
        nodeScene.setRoot(root);
    }

}
