package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.e_commerce.ui.cart.CartAdapter;

public class HoaDonActivity extends AppCompatActivity {
    private RecyclerView cartItemRecyclerView;
    private CartAdapter cartAdapter;
    private TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        cartItemRecyclerView = findViewById(R.id.cart_hoadon);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemRecyclerView.setLayoutManager(layoutManager);
        cartAdapter = new CartAdapter(DBqueries.cartItemModelList,total,true, 1);
        System.out.println(cartAdapter.getItemCount()+"cc");
        cartItemRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

    }


}