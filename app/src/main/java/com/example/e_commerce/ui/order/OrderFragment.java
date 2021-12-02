package com.example.e_commerce.ui.order;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.DBqueries;
import com.example.e_commerce.R;

public class OrderFragment extends Fragment {


    public OrderFragment() {
        // Required empty public constructor
    }
    private RecyclerView myOrdersRecyclerView;
    private Dialog loadingDialog;
    public static MyOrderAdapter myOrderAdapter;


    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_order, container, false);
        myOrdersRecyclerView = view.findViewById(R.id.my_orders_recyclerview);


        //////////loading dialog

        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        //////////loading dialog
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myOrdersRecyclerView.setLayoutManager(layoutManager);

        myOrderAdapter = new MyOrderAdapter(DBqueries.myOrderItemModelList,loadingDialog);
        myOrdersRecyclerView.setAdapter(myOrderAdapter);

        DBqueries.loadOrders(getContext(),myOrderAdapter,loadingDialog);
//        myOrderAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        myOrderAdapter.notifyDataSetChanged();
    }
}