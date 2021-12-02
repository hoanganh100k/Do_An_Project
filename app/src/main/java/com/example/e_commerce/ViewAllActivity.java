package com.example.e_commerce;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lib.Model.HorizontalProductScrollModel;
import com.example.lib.Model.WishlistModel;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GridView gridView;
    public static List<HorizontalProductScrollModel> horizontalProductScrollModelList;
    public static List<WishlistModel> wishlistModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);                          // hiên dấu <-

        recyclerView=findViewById(R.id.recycler_view);
        gridView=findViewById(R.id.grid_view);

        int layout = getIntent().getIntExtra("layout",-1);

        if(layout==0) {

            recyclerView.setVisibility(View.VISIBLE);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);

            //Gọi WishlistAdapter
            WishlistAdapter wishlistAdapter = new WishlistAdapter(wishlistModelList, false);
            recyclerView.setAdapter(wishlistAdapter);
            wishlistAdapter.notifyDataSetChanged();

        }else if(layout==1) {

            gridView.setVisibility(View.VISIBLE);

            //gọi GridProductLayoutAdapter
            GridProductLayoutAdapter gridProductLayoutAdapter = new GridProductLayoutAdapter(horizontalProductScrollModelList);
            gridView.setAdapter(gridProductLayoutAdapter);
            gridProductLayoutAdapter.notifyDataSetChanged();
        }
    }

    //Trở về home
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}