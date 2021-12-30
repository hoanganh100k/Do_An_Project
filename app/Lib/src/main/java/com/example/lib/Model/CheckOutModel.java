package com.example.lib.Model;

public class CheckOutModel {
    private String SDT;
    private String Name;
    private String GioiTinh;
    private String NgaySinh;
    private String DiaChi;
    private String email;

    public CheckOutModel(String SDT, String name, String gioiTinh, String ngaySinh, String diaChi, String email) {
        this.SDT = SDT;
        Name = name;
        GioiTinh = gioiTinh;
        NgaySinh = ngaySinh;
        DiaChi = diaChi;
        this.email = email;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
    }

    public void setNgaySinh(String ngaySinh) {
        NgaySinh = ngaySinh;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSDT() {
        return SDT;
    }

    public String getName() {
        return Name;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public String getEmail() {
        return email;
    }
}
