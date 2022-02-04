package model;

public class TableBioskopItem {
    private String nama, provinsi, kota;
    private int no, idBioskop, jumlahTeater;

    public TableBioskopItem(String nama, String provinsi, String kota, int idBioskop, int jumlahTeater, int no) {
        this.nama = nama;
        this.provinsi = provinsi;
        this.kota = kota;
        this.idBioskop = idBioskop;
        this.jumlahTeater = jumlahTeater;
        this.no = no;
    }


    public int getIdBioskop() {
        return idBioskop;
    }

    public int getJumlahTeater() {
        return jumlahTeater;
    }

    public String getNama() {
        return nama;
    }

    public String getKota() {
        return kota;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public int getNo() {
        return no;
    }
}
