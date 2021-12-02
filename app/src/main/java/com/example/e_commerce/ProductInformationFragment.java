package com.example.e_commerce;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductInformationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductInformationFragment() {
        // Required empty public constructor
    }

    private RecyclerView productInformationRecyclerView;
    public List<ProductInformationModel> productInformationModelList;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductInformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductInformationFragment newInstance(String param1, String param2) {
        ProductInformationFragment fragment = new ProductInformationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_information, container, false);

        productInformationRecyclerView = view.findViewById(R.id.rcv_product_information);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        productInformationRecyclerView.setLayoutManager(linearLayoutManager);

//        = new ArrayList<>();
//        productInformationModelList.add(new ProductInformationModel("Tên Sản Phẩm","Áo"));
//        productInformationModelList.add(new ProductInformationModel("Thương Hiệu",  "OEM"));
//        productInformationModelList.add(new ProductInformationModel("Xuất Xứ","Việt Nam"));
//        productInformationModelList.add(new ProductInformationModel("Chất Liệu","Vải"));
//        productInformationModelList.add(new ProductInformationModel("Size","M"));




        ProductInformationAdapter productInformationAdapter = new ProductInformationAdapter(productInformationModelList);
        productInformationRecyclerView.setAdapter(productInformationAdapter);
        productInformationAdapter.notifyDataSetChanged();
        return view;
    }
}