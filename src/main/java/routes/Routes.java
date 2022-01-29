package routes;

import helper.Navigation;
import javafx.event.ActionEvent;

import java.io.IOException;

public class Routes {
    Navigation navHelper = new Navigation();

    public void toJadwal(ActionEvent actionEvent) throws IOException {
        String viewPath = "./src/main/resources/com/tiketly/tiketly/jadwal.fxml";
        navHelper.navigate(actionEvent, viewPath);
    }

    public void toTiket(ActionEvent actionEvent) throws IOException {
        String viewPath = "./src/main/resources/com/tiketly/tiketly/tiket.fxml";
        navHelper.navigate(actionEvent, viewPath);
    }

    public void toUser(ActionEvent actionEvent) throws IOException {
        String viewPath = "./src/main/resources/com/tiketly/tiketly/user.fxml";
        navHelper.navigate(actionEvent, viewPath);
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        String viewPath = "./src/main/resources/com/tiketly/tiketly/login.fxml";
        navHelper.navigate(actionEvent, viewPath);
    }

}
