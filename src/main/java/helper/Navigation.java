package helper;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.SQLException;

public class Navigation extends Helper{
    private static final String baseViewUrl = "src/main/resources/com/tiketly/tiketly/views/";
    private static final String baseModalUrl = baseViewUrl+"modal/";


    public void navigate(ActionEvent actionEvent, String path) throws IOException {
        if (!path.endsWith(".fxml")){
            path += ".fxml";
        }
        URL url = Paths.get(baseViewUrl+path).toUri().toURL();
        Parent root = FXMLLoader.load(url); // loads scene
        Scene nodeScene = ((Node) actionEvent.getSource()).getScene();
        nodeScene.setRoot(root);
    }

    public void showModal(ActionEvent actionEvent, String modalTitle, int width, int height, String path, EventHandler<WindowEvent> onHide, EventHandler<WindowEvent> onClose) throws IOException {
        if (!path.endsWith(".fxml")){
            path += ".fxml";
        }

        final Stage stage = new Stage();
        URL url = Paths.get(baseModalUrl+path).toUri().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(url);

        stage.setResizable(false);
        stage.setScene(new Scene(fxmlLoader.load(), width, height));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow() );
        stage.setTitle(modalTitle);

        if (onClose != null){
            System.out.println("onClose: "+onClose);
            stage.setOnCloseRequest(onClose);
        }

        if (onHide != null) {
            stage.setOnHidden(onHide);
        }

        stage.show();
    }

    public void showDialog(String title, String body){
        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle(title);
        dialog.setContentText(body);
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.showAndWait();
    }

}

