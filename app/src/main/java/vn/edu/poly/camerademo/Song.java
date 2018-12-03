package vn.edu.poly.camerademo;

public class Song {
    private String ten;
    private int file;

    public Song() {
    }

    public Song(String ten, int file) {
        this.ten = ten;
        this.file = file;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getFile() {
        return file;
    }

    public void setFile(int file) {
        this.file = file;
    }
}
