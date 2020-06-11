package id.mobileprogramming.tugasproject5.models;

public class Account {
    private String username;
    private String password;
    private String nama_lengkap;
    private String kontak;
    private String alamat;
    private String photo;

    public Account(String username, String password, String nama_lengkap, String kontak, String alamat, String photo) {
        this.username = username;
        this.password = password;
        this.nama_lengkap = nama_lengkap;
        this.kontak = kontak;
        this.alamat = alamat;
        this.photo = photo;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public String getKontak() {
        return kontak;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getPhoto() {
        return photo;
    }
}
