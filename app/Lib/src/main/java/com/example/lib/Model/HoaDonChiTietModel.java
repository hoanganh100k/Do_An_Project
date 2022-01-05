package com.example.lib.Model;

import java.io.Serializable;

public class HoaDonChiTietModel implements Serializable {
    private String maHoaDon;
    private String hoTen;
    private String SDT;
    private String gioiTinh;
    private String ngaySinh;
    private String diaChi;
    private String email;
    private String ngayTao;
    private String ngayGiao;
    private String trangThai;
    private String thanhToan;
    private String maThanhToan;
    private String loaiThanhToan;
    private String tongTien;
    private String thanhTien;
    private String ghiChu;

    public HoaDonChiTietModel(String maHoaDon, String hoTen, String SDT, String gioiTinh, String ngaySinh, String diaChi, String email, String ngayTao, String ngayGiao, String trangThai, String thanhToan, String maThanhToan, String loaiThanhToan, String tongTien, String thanhTien,String _ghiChu) {
        this.maHoaDon = maHoaDon;
        this.hoTen = hoTen;
        this.SDT = SDT;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.email = email;
        this.ngayTao = ngayTao;
        this.ngayGiao = ngayGiao;
        this.trangThai = trangThai;
        this.thanhToan = thanhToan;
        this.maThanhToan = maThanhToan;
        this.loaiThanhToan = loaiThanhToan;
        this.tongTien = tongTien;
        this.thanhTien = thanhTien;
        this.ghiChu = _ghiChu;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public String getTongTien() {
        return tongTien;
    }

    public String getThanhTien() {
        return thanhTien;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getSDT() {
        return SDT;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public String getEmail() {
        return email;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public String getNgayGiao() {
        return ngayGiao;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public String getThanhToan() {
        return thanhToan;
    }

    public String getMaThanhToan() {
        return maThanhToan;
    }

    public String getLoaiThanhToan() {
        return loaiThanhToan;
    }
}
