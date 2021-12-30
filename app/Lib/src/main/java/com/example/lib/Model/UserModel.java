package com.example.lib.Model;

public class UserModel {
    private String Email;
    private String GioiTinh;
    private String UserFullName;
    private String DiaChi;
    private String NgaySinh;
    private String SDT;

    public UserModel(String email, String gioiTinh, String userFullName, String diaChi, String ngaySinh, String SDT) {
        Email = email;
        GioiTinh = gioiTinh;
        UserFullName = userFullName;
        DiaChi = diaChi;
        NgaySinh = ngaySinh;
        this.SDT = SDT;
    }

    public String getEmail() {
        return Email;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public String getUserFullName() {
        return UserFullName;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public String getSDT() {
        return SDT;
    }
}
