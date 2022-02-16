package model;

public class TableTransaksiKasirItem {
    private String idTrx, judulFilm, teater, tanggalPembelian;
    private float total;
    private long jumlahKursi;

    public TableTransaksiKasirItem(String idTrx, String judulFilm, String teater, String tanggalPembelian, float total, long jumlahKursi){
        this.idTrx = idTrx;
        this.judulFilm = judulFilm;
        this.tanggalPembelian = tanggalPembelian;
        this.teater = teater;
        this.total = total;
        this.jumlahKursi = jumlahKursi;
    }

    public String getIdTrx(){ return this.idTrx; }
    public String getJudulFilm(){ return this.judulFilm; }
    public String getTeater(){ return this.teater; }
    public String getTanggalPembelian(){ return this.tanggalPembelian; }
    public float getTotal(){ return this.total; }
    public long getJumlahKursi(){ return this.jumlahKursi; }

}
