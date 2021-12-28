package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    ArrayList<CartModel> cartModels;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cartList);

        cartModels = new ArrayList<CartModel>();
        //Tự phát sinh 10 dữ liệu mẫu
        for (int i = 1; i <= 10; i++) {
            cartModels.add(new CartModel("Sp1"+i , "200.000", 2 + (i % 2)));
        }

        cartAdapter = new CartAdapter(cartModels, this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(cartAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, CheckOutActivity.class);
                startActivity(intent);
            }
        });
    }
}