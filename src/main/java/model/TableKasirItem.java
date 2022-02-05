package model;

public class TableKasirItem {
    private final String idKasir, password, nama, telp, createdAt, namaBioskop;
    private final int no, idbioskop;
    private final boolean blokir;

    public TableKasirItem(int no, int idbioskop, boolean blokir, String idKasir, String password, String nama, String telp, String namaBioskop, String createdAt ){
        this.no = no;
        this.idKasir = idKasir;
        this.nama = nama;
        this.telp = telp;
        this.namaBioskop = namaBioskop;
        this.createdAt = createdAt;
        this.password = password;
        this.idbioskop = idbioskop;
        this.blokir = blokir;
    }

    public String getIdKasir() { return this.idKasir; }

    public String getPassword() { return this.password; }

    public String getNama() { return this.nama; }

    public String getTelp() { return this.telp; }

    public String getCreatedAt() { return this.createdAt; }

    public int getNo() { return this.no; }

    public int getIdbioskop() { return this.idbioskop; }

    public boolean getBlokir() { return blokir; }

    public String getNamaBioskop() {
        return namaBioskop;
    }
}
