package helper;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
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

    public void showModal(ActionEvent actionEvent, String modalTitle, int width, int height, String path) throws IOException {
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

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.out.println("closeee");
            }
        });


        stage.showAndWait();
    }

    public Stage showModalGetStage(ActionEvent actionEvent, String modalTitle, int width, int height, String path) throws IOException {
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


//        stage.show();
        return stage;
    }

}

