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


public class CartFragment extends Fragment {

    public CartFragment(){}

    private RecyclerView cartItemRecyclerView;
    private Button btnContinue;
    private Dialog loadingDialog;
    public static CartAdapter cartAdapter;
    private TextView totalAmount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        totalAmount=view.findViewById(R.id.total_cart_amount);

        //////////loading dialog
        loadingDialog=new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        //////////loading dialog

        cartItemRecyclerView= view.findViewById(R.id.cart_items_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemRecyclerView.setLayoutManager(layoutManager);

//          List<CartItemModel> cartItemModelList = new ArrayList<>();
//        cartItemModelList.add(new CartItemModel(0,R.drawable.hoddie1,"Hoddie",2,"180.000 VND","190.000 VND",1,0,0));
//        cartItemModelList.add(new CartItemModel(0,R.drawable.hoddie1,"Hoddie",0,"180.000 VND","190.000 VND",1,1,0));
//        cartItemModelList.add(new CartItemModel(0,R.drawable.hoddie1,"Hoddie",2,"180.000 VND","190.000 VND",1,2,0));
//        cartItemModelList.add(new CartItemModel(1,"Giá (3 sản phẩm)","520.000 VND","Free","520.00 VND"," Giảm 5000 VND"));

        cartAdapter = new CartAdapter(DBqueries.cartItemModelList,totalAmount,true);
        cartItemRecyclerView.setAdapter(cartAdapter);
//        cartAdapter.notifyDataSetChanged();

        btnContinue = view.findViewById(R.id.cart_continue_btn);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeliveryActivity.cartItemModelList = new ArrayList<>();
                DeliveryActivity.fromCart = true;

                for (int x = 0; x < DBqueries.cartItemModelList.size(); x++) {
                    CartItemModel cartItemModel = DBqueries.cartItemModelList.get(x);
                    if (cartItemModel.isInStock()) {
                        DeliveryActivity.cartItemModelList.add(cartItemModel);
                    }
                }
                DeliveryActivity.cartItemModelList.add(new CartItemModel(CartItemModel.TOTAL_AMOUNT));
                loadingDialog.show();
                if (DBqueries.addressesModelList.size() == 0) {
                    DBqueries.loadAddresses(getContext(), loadingDialog, true);
                } else {
                    loadingDialog.dismiss();
                    startActivity(new Intent(getContext(), DeliveryActivity.class));
                }
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        cartAdapter.notifyDataSetChanged();

        if(DBqueries.rewardModelList.size() == 0){
            loadingDialog.show();
            DBqueries.loadRewards(getContext(),loadingDialog,false);
        }

        if(DBqueries.cartItemModelList.size() == 0){
            DBqueries.cartList.clear();
            DBqueries.loadCartList(getContext(),loadingDialog,true,new TextView(getContext()),totalAmount);
        }else {
            if(DBqueries.cartItemModelList.get(DBqueries.cartItemModelList.size()-1).getType() == CartItemModel.TOTAL_AMOUNT){
                LinearLayout parent=(LinearLayout)totalAmount.getParent().getParent();
                parent.setVisibility(View.VISIBLE);
            }
            loadingDialog.dismiss();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        for(CartItemModel cartItemModel:DBqueries.cartItemModelList){
            if(!TextUtils.isEmpty(cartItemModel.getSelectedCoupanId())){
                for(RewardModel rewardModel: DBqueries.rewardModelList){
                    if(rewardModel.getCoupanId().equals(cartItemModel.getSelectedCoupanId())){
                        rewardModel.setAlreadyUsed(false);

                    }
                }
                cartItemModel.setSelectedCoupanId(null);
//                if(RewardFragment.rewardsAdapter != null){
//                    RewardFragment.rewardsAdapter.notifyDataSetChanged();
//                }
            }
        }
    }
}