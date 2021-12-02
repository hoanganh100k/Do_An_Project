package com.example.e_commerce;

import static com.example.e_commerce.DeliveryActivity.SELECT_ADDRESS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class OrderDetailsActivity extends AppCompatActivity {
    private Button changeOrAddAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Order details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        changeOrAddAddress = findViewById(R.id.change_or_add_address_btn);
        changeOrAddAddress.setVisibility(View.VISIBLE);
        changeOrAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addressIntent = new Intent(getApplication(), MyAddressActivity.class);
                addressIntent.putExtra("MODE", SELECT_ADDRESS);
                startActivity(addressIntent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}