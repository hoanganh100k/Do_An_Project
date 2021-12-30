package com.example.e_commerce;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.ui.cart.CartAdapter;

public class HoaDonActivity extends AppCompatActivity {
    private RecyclerView cartItemRecyclerView;
    private CartAdapter cartAdapter;
    private TextView total;
    private TextView SDT;
    private TextView Hoten;
    private TextView GioiTinh;
    private TextView NgaySinh;
    private TextView DiaChi;
    private TextView Email;
    private TextView TongTien;
    private TextView ThanhTien;
    private Button DatHang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);

        cartItemRecyclerView = findViewById(R.id.cart_hoadon);
        SDT = findViewById(R.id.textEmailSDT);
        Hoten = findViewById(R.id.textNameTT);
        GioiTinh = findViewById(R.id.textChiTietGT);
        NgaySinh = findViewById(R.id.textBirtdayTT);
        DiaChi = findViewById(R.id.textDiaChiTT);
        Email = findViewById(R.id.textEmailTT);
        DatHang = findViewById(R.id.dathang);
        SDT.setText(DBqueries.checkOutModel.getSDT());
        Hoten.setText(DBqueries.checkOutModel.getName());
        GioiTinh.setText(DBqueries.checkOutModel.getGioiTinh());
        NgaySinh.setText(DBqueries.checkOutModel.getNgaySinh());
        DiaChi.setText(DBqueries.checkOutModel.getDiaChi());
        Email.setText(DBqueries.checkOutModel.getEmail());

        TongTien = findViewById(R.id.tongTien);
        ThanhTien= findViewById(R.id.thanhTien);

        TongTien.setText(DBqueries.tong+"VND");
        ThanhTien.setText(DBqueries.tong+"VND");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemRecyclerView.setLayoutManager(layoutManager);
        cartAdapter = new CartAdapter(DBqueries.cartItemModelList,total,true, 1);
        System.out.println(cartAdapter.getItemCount()+"cc");
        cartItemRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
        DatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


}