package model;

import java.sql.Timestamp;

public class TableJadwalItem {
    private final int no, idjadwal, idbioskop, idteater;
    private final float harga;
    private final String showAt, judul, namaBioskop, namaTeater;
    private final Timestamp showAtRaw;

    public TableJadwalItem(int no, int idjadwal, int idbioskop, int idteater, float harga, String showAt, String judul, String namaBioskop, String namaTeater, Timestamp showAtRaw){
        this.no = no;
        this.idjadwal = idjadwal;
        this.idbioskop = idbioskop;
        this.idteater = idteater;
        this.harga = harga;
        this.showAt = showAt;
        this.judul = judul;
        this.namaBioskop = namaBioskop;
        this.namaTeater = namaTeater;
        this.showAtRaw = showAtRaw;
    }

    public int getNo(){ return this.no; }
    public int getIdjadwal(){ return this.idjadwal; }
    public int getIdbioskop(){ return this.idbioskop; }
    public int getIdteater(){ return this.idteater; }
    public float getHarga(){ return this.harga; }
    public String getShowAt(){ return this.showAt; }
    public String getJudul(){ return this.judul; }
    public String getNamaBioskop(){ return this.namaBioskop; }
    public String getNamaTeater(){ return this.namaTeater; }

    public Timestamp getShowAtRaw() {
        return showAtRaw;
    }
}
