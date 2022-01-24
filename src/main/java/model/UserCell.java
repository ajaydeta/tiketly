package model;

public class UserCell {
    private String idUser, nama, telp, tglLahir;

    public UserCell(String idUser, String nama, String telp, String tglLahir){
        this.idUser = idUser;
        this.nama = nama;
        this.telp = telp;
        this.tglLahir = tglLahir;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getNama() {
        return nama;
    }

    public String getTelp() {
        return telp;
    }

    public String getTglLahir() {
        return tglLahir;
    }
}
