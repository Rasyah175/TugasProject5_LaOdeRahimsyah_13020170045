package id.mobileprogramming.tugasproject5.models;

public class Pariwisata {
    private String nama_wisata;
    private String lokasi;
    private String detail;
    private String deskripsi;
    private String gambar;

    public Pariwisata(String nama_wisata, String lokasi, String detail, String deskripsi, String gambar) {
        this.nama_wisata = nama_wisata;
        this.lokasi = lokasi;
        this.detail = detail;
        this.deskripsi = deskripsi;
        this.gambar = gambar;
    }

    public String getNama_wisata() {
        return nama_wisata;
    }

    public String getLokasi() {
        return lokasi;
    }

    public String getDetail() {
        return detail;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getGambar() {
        return gambar;
    }
}
