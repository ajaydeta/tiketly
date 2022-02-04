package model;

public class TableFilmItem {
    int id, no;
    String judul, sinopsis, sensor;
    public TableFilmItem(int no, int id, String judul, String sinopsis, String sensor){
        this.no = no;
        this.id = id;
        this.judul = judul;
        this.sinopsis = sinopsis;
        this.sensor = sensor;
    }

    public int getNo() {
        return no;
    }

    public int getId() {
        return id;
    }

    public String getJudul() {
        return judul;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public String getSensor() {
        return sensor;
    }
}
