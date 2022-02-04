package model;

public class TableTeaterItem {
    private int no, id, baris, kolom;
    private String nama;

    public TableTeaterItem(int no, int id, int baris, int kolom, String nama){
        this.no = no;
        this.id = id;
        this.baris = baris;
        this.kolom = kolom;
        this.nama = nama;
    }

    public int getNo() {
        return no;
    }

    public int getId() {
        return id;
    }

    public int getBaris() {
        return baris;
    }

    public int getKolom() {
        return kolom;
    }

    public String getNama() {
        return nama;
    }
}
