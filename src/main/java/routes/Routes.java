package routes;

import helper.Navigation;
import javafx.event.ActionEvent;

import java.io.IOException;

public class Routes {
    Navigation navHelper = new Navigation();

//    ROUTE V2
//    private static final String baseViewUrl = "src/main/resources/com/tiketly/tiketly/views/";

//    Admin
    public void toKelolaBioskop(ActionEvent actionEvent) throws IOException {
         String viewPath = "admin/kelolaBioskop";
        navHelper.navigate(actionEvent, viewPath);
    }

    public void toKelolaFilm(ActionEvent actionEvent) throws IOException {
        navHelper.navigate(actionEvent, "admin/kelolaFilm");
    }

    public void toKelolaJadwal(ActionEvent actionEvent) throws IOException {
        navHelper.navigate(actionEvent, "admin/jadwalFilm");
    }

    public void toKelolaKasir(ActionEvent actionEvent) throws IOException {
        navHelper.navigate(actionEvent, "admin/kelolaKasir");
    }

    public void toPembelian(ActionEvent actionEvent) throws IOException {
        navHelper.navigate(actionEvent, "admin/pembelian");
    }

//    Kasir
    public void toHome(ActionEvent actionEvent) throws IOException {
        navHelper.navigate(actionEvent, "kasir/home");
    }

    public void toBuatTransaksi(ActionEvent actionEvent) throws IOException {
        navHelper.navigate(actionEvent, "kasir/buatTransaksi");
    }

    public void toRiwayatTransaksi(ActionEvent actionEvent) throws IOException {
        navHelper.navigate(actionEvent, "kasir/riwayatTransaksi");
    }


//    ROUTE V1
    public void toJadwal(ActionEvent actionEvent) throws IOException {
        String viewPath = "./src/main/resources/com/tiketly/tiketly/kelolaJadwal.fxml";
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
