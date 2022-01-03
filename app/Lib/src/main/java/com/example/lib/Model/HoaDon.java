package com.example.lib.Model;

public class HoaDon {
    private String MaHD;
    private String TenKH;
    private String GioiTinh;
    private String trangThai;
    private String TongTien;
    private String MaTT;
    private String ThanhTien;

    public HoaDon(String maHD, String tenKH, String gioiTinh, String trangThai, String tongTien, String maTT, String thanhTien) {
        MaHD = maHD;
        TenKH = tenKH;
        GioiTinh = gioiTinh;
        this.trangThai = trangThai;
        TongTien = tongTien;
        MaTT = maTT;
        ThanhTien = thanhTien;
    }

    public String getMaHD() {
        return MaHD;
    }

    public String getTenKH() {
        return TenKH;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public String getTongTien() {
        return TongTien;
    }

    public String getMaTT() {
        return MaTT;
    }

    public String getThanhTien() {
        return ThanhTien;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
