package com.example.e_commerce;

import static com.example.e_commerce.DBqueries.categoryModelList1;
import static com.example.e_commerce.DBqueries.lists;
import static com.example.e_commerce.DBqueries.loadCategories1;
import static com.example.e_commerce.DBqueries.loadedCategoriesName;
import static com.example.e_commerce.DBqueries.setCategoryData;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.ui.home.HomePageAdapter;
import com.example.lib.Model.HomePageModel;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    private RecyclerView categoryRecyclerView1;
    private RecyclerView categoryRecyclerView2;
    private HomePageAdapter adapter;
    private CategoryAdapter categoryAdapter1;
    private Dialog loadingDialog;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadingDialog = new Dialog(CategoryActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(CategoryActivity.this.getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        categoryRecyclerView2 = findViewById(R.id.category_recyclerview);
        categoryRecyclerView1 = findViewById(R.id.category_1);

        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayout.VERTICAL);
        LinearLayoutManager testingLayoutManager1 = new LinearLayoutManager(this);
        testingLayoutManager1.setOrientation(LinearLayout.HORIZONTAL);
        categoryRecyclerView2.setLayoutManager(testingLayoutManager);
        categoryRecyclerView1.setLayoutManager(testingLayoutManager1);

        //Đổ vào category con
        categoryAdapter1 = new CategoryAdapter(categoryModelList1);
        categoryRecyclerView1.setAdapter(categoryAdapter1);
        int positionOfList = 0;
        for(int i = 0;i<loadedCategoriesName.size();i++){
            if(loadedCategoriesName.get(i).equals(title.toUpperCase())){
                positionOfList = i;
            }
        }

        if(categoryModelList1.size() == 0){
            loadCategories1(getBaseContext(), categoryAdapter1,title);
        }else{
            categoryAdapter1.notifyDataSetChanged();
        }

        //In sản phẩm của category đó

        if(positionOfList == 0){
            loadedCategoriesName.add(title.toUpperCase());
            lists.add(new ArrayList<HomePageModel>());
            adapter = new HomePageAdapter(lists.get(loadedCategoriesName.size()-1));
            setCategoryData(this,adapter,loadedCategoriesName.size()-1,title,loadingDialog);

        }else{
            adapter = new HomePageAdapter(lists.get(positionOfList));
        }

        categoryRecyclerView2.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; thêm các item vào action bar nếu như nó hiển thị
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.main_search_icon){
            //To do search here
            categoryModelList1.clear();
            startActivity(new Intent(this,SearchActivity.class));
            return true;
        }else if(id == android.R.id.home){      //
            categoryModelList1.clear();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        categoryModelList1.clear();
        finish();
        return;
    }
}