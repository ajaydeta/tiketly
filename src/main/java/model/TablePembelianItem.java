package model;

public class TablePembelianItem {
    private String idTrx, judulFilm, bioskop, tanggalPembelian, kasir;
    private float total;
    private long jumlahKursi;

    public TablePembelianItem(String idTrx, String judulFilm, String bioskop, String tanggalPembelian, String kasir, float total, long jumlahKursi){
        this.idTrx = idTrx;
        this.judulFilm = judulFilm;
        this.bioskop = bioskop;
        this.tanggalPembelian = tanggalPembelian;
        this.kasir = kasir;
        this.total = total;
        this.jumlahKursi = jumlahKursi;
    }

    public String getIdTrx(){ return this.idTrx; }
    public String getJudulFilm(){ return this.judulFilm; }
    public String getBioskop(){ return this.bioskop; }
    public String getTanggalPembelian(){ return this.tanggalPembelian; }
    public String getKasir(){ return this.kasir; }
    public float getTotal(){ return this.total; }
    public long getJumlahKursi(){ return this.jumlahKursi; }

}
