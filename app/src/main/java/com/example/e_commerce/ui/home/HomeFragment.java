package com.example.e_commerce.ui.home;

import static com.example.e_commerce.DBqueries.categoryModelList;
import static com.example.e_commerce.DBqueries.categoryModelList1;
import static com.example.e_commerce.DBqueries.lists;
import static com.example.e_commerce.DBqueries.loadCategories;
import static com.example.e_commerce.DBqueries.loadedCategoriesName;
import static com.example.e_commerce.DBqueries.setFragmentData;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_commerce.CategoryAdapter;
import com.example.e_commerce.R;
import com.example.lib.Model.HomePageModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    public HomeFragment() {
    }
    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private RecyclerView homePageRecyclerView;
    private ImageView noInternetConnect;
    private RecyclerView horizontalRecyclerView;
    private TextView noInternet;
    private HomePageAdapter adapter;
    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        noInternetConnect = view.findViewById(R.id.no_internet_connection);
        noInternet = view.findViewById(R.id.txt_no_internet);
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected() == true){
            noInternetConnect.setVisibility(View.GONE);
            noInternet.setVisibility(View.GONE);
            categoryRecyclerView = view.findViewById(R.id.category_recyclerView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            categoryRecyclerView.setLayoutManager(layoutManager);

            categoryAdapter = new CategoryAdapter(categoryModelList);
            categoryRecyclerView.setAdapter(categoryAdapter);
            categoryModelList1.clear();
            if(categoryModelList.size() == 0){
                loadCategories(getContext(), categoryAdapter);
            }else{
                categoryAdapter.notifyDataSetChanged();
            }

//========================Horizontal product layout=====================
//            horizontalRecyclerView = view.findViewById(R.id.recycler_horizontal_scroll_layout);
//            horizontalRecyclerView.setVisibility(View.VISIBLE);
//            horizontalRecyclerView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent viewProductDetailsIntent = new Intent(getActivity(), ProductDetailsActivity.class);
//                    getActivity().startActivity(viewProductDetailsIntent);
//                }
//            });
//========================Horizontal product layout=====================

            homePageRecyclerView = view.findViewById(R.id.testing);
            LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
            testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            homePageRecyclerView.setLayoutManager(testingLayoutManager);


            if(lists.size() == 0){
                loadedCategoriesName.add("HOME");
                lists.add(new ArrayList<HomePageModel>());
                adapter = new HomePageAdapter(lists.get(0));
                setFragmentData(getContext(),adapter,0,"HOME");
            }else{
                adapter = new HomePageAdapter(lists.get(0));
                adapter.notifyDataSetChanged();
            }
            homePageRecyclerView.setAdapter(adapter);
        }else{
            Glide.with(this).load(R.mipmap.ic_no_connection).into(noInternetConnect);
            noInternetConnect.setVisibility(View.VISIBLE);
            noInternet.setVisibility(View.VISIBLE);
            noInternet.setText(R.string.oops_connection);
        }
        return view;
    }
}

