package com.example.e_commerce.ui.cart;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.DBqueries;
import com.example.e_commerce.DeliveryActivity;
import com.example.e_commerce.R;
import com.example.lib.Model.CartItemModel;
import com.example.lib.Model.RewardModel;

import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment {

    public CartFragment(){}

    private RecyclerView cartItemRecyclerView;
    private Button btnContinue;
    private Dialog loadingDialog;
    public static CartAdapter cartAdapter;
    private TextView totalAmount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        //Gắn layout vào fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        totalAmount=view.findViewById(R.id.total_cart_amount);

        //////////loading dialog
        loadingDialog=new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        loadingDialog.show();
        //////////loading dialog

        cartItemRecyclerView= view.findViewById(R.id.cart_items_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemRecyclerView.setLayoutManager(layoutManager);

//        Đổ dữ liệu
          List<CartItemModel> cartItemModelList = new ArrayList<>();
        cartItemModelList.add(new CartItemModel( (long) 2,"1","https://haycafe.vn/wp-content/uploads/2021/11/Anh-avatar-dep-chat-lam-hinh-dai-dien.jpg","Hoddie","100000"));
        cartItemModelList.add(new CartItemModel( (long) 20,"2","","Hoddie1","110000"));
        cartItemModelList.add(new CartItemModel( (long) 21,"3","","Hoddie2","150000"));
        cartItemModelList.add(new CartItemModel( (long) 25,"4","","Hoddie3","120000"));
        DBqueries.cartItemModelList = cartItemModelList;

        for (int i = 0; i < DBqueries.cartItemModelList.size(); i++) {
            DBqueries.tong += Integer.parseInt(DBqueries.cartItemModelList.get(i).getProductPrice());
        }
        totalAmount.setText(DBqueries.tong + "VND");
//        cartItemModelList.add(new CartItemModel(1,"Giá (3 sản phẩm)","520.000 VND","Free","520.00 VND"," Giảm 5000 VND"))

        cartAdapter = new CartAdapter(DBqueries.cartItemModelList,totalAmount,true, 0);
        cartItemRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
        btnContinue = view.findViewById(R.id.cart_continue_btn);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), DeliveryActivity.class));
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
//        cartAdapter.notifyDataSetChanged();


    }
    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}