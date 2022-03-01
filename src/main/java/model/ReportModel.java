package model;

public class ReportModel {

    private String noNota;
    private String namaKasir;
    private String judul;
    private String bioskop;
    private String createdAt;
    private long jumlahKursi;
    private float total;
    private float harga;
//    private

    public ReportModel(String noNota, String namaKasir, String judul, String bioskop, String createdAt, long jumlahKursi, float total, float harga){
        this.noNota = noNota;
        this.namaKasir = namaKasir;
        this.judul = judul;
        this.bioskop = bioskop;
        this.createdAt = createdAt;
        this.jumlahKursi = jumlahKursi;
        this.total = total;
        this.harga = harga;
    }

    public String getNoNota(){ return this.noNota; }
    public String getNamaKasir(){ return this.namaKasir; }
    public String getBioskop(){ return this.bioskop; }
    public String getJudul(){ return this.judul; }
    public String getCreatedAt(){ return this.createdAt; }
    public long getJumlahKursi(){ return this.jumlahKursi; }
    public float getTotal(){ return this.total; }
    public float getHarga(){ return this.harga; }

    @Override
    public String toString(){
        return "ReportModel{" +
                "noNota='" + noNota +"',"+
                "namaKasir='" + namaKasir +"',"+
                "judul='" + judul +"',"+
                "bioskop='" + bioskop +"',"+
                "createdAt='" + createdAt +"',"+
                "jumlahKursi='" + jumlahKursi +"',"+
                "total='" + total +"',"+
                "harga='" + harga +"'"+
                "}";

    }
}
