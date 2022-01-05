package com.example.e_commerce;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.lib.Model.HoaDonChiTietModel;

public class HistoryIntermationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infomation_hoa_don);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        HoaDonChiTietModel ThongTinChiTiet =(HoaDonChiTietModel) intent.getSerializableExtra("getThongTinChiTiet");
        String MaHD = ThongTinChiTiet.getMaHoaDon();
        ((TextView) findViewById(R.id.textView_ma_hoa_don)).setText(MaHD);
        ((TextView) findViewById(R.id.textView_ho_ten)).setText(ThongTinChiTiet.getHoTen());
        ((TextView) findViewById(R.id.textView_sdt)).setText(ThongTinChiTiet.getSDT());
        ((TextView) findViewById(R.id.textView_gioi_tinh)).setText(ThongTinChiTiet.getGioiTinh());
        ((TextView) findViewById(R.id.textView_ngay_sinh)).setText(ThongTinChiTiet.getNgaySinh());
        ((TextView) findViewById(R.id.textView_dia_chi)).setText(ThongTinChiTiet.getDiaChi());
        ((TextView) findViewById(R.id.textView_email)).setText(ThongTinChiTiet.getEmail());
        ((TextView) findViewById(R.id.textView_ngay_tao)).setText(ThongTinChiTiet.getNgayTao());
        ((TextView) findViewById(R.id.textView_ngay_giao)).setText((ThongTinChiTiet.getNgayGiao().equals("null"))?"Chưa giao":ThongTinChiTiet.getNgayGiao());
        ((TextView) findViewById(R.id.textView_trang_thai)).setText(ThongTinChiTiet.getTrangThai());
        ((TextView) findViewById(R.id.textView_thanh_toan)).setText(ThongTinChiTiet.getThanhToan());
        ((TextView) findViewById(R.id.textViewma_thanhtoan)).setText(ThongTinChiTiet.getMaThanhToan());
        String phuongThuc = "";
        switch (ThongTinChiTiet.getLoaiThanhToan()){
            case "ck":
                phuongThuc = "Chuyển khoản";
                break;
            case "nd":
                phuongThuc = "Nội địa";
                break;
            case "off":
                phuongThuc = "Thanh toán khi giao hàng";
                break;
            case "qt":
                phuongThuc = "Quốc tế";
                break;
        }
        ((TextView) findViewById(R.id.textView_loai_thanh_toan)).setText(phuongThuc);


    }
}
