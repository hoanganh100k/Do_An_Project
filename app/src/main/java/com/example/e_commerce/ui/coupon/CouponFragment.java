package com.example.e_commerce.ui.coupon;

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

public class CouponFragment extends Fragment {

    public CouponFragment(){}
    public static RewardAdapter adapter;
    private Dialog loadingDialog;
    private RecyclerView rewardRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coupon, container, false);
        rewardRecyclerView = view.findViewById(R.id.my_rewards_recyclerview);

        //////////loading dialog
        loadingDialog=new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        //////////loading dialog

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rewardRecyclerView.setLayoutManager(layoutManager);

        adapter = new RewardAdapter(DBqueries.rewardModelList,false);
        rewardRecyclerView.setAdapter(adapter);

        if(DBqueries.rewardModelList.size() == 0){
            DBqueries.loadRewards(getContext(),loadingDialog,true);
        }else {
            loadingDialog.dismiss();
        }

        adapter.notifyDataSetChanged();
        return view;
    }

//    private CouponViewModel couponViewModel;
//private FragmentCouponBinding binding;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//            ViewGroup container, Bundle savedInstanceState) {
//        couponViewModel =
//                new ViewModelProvider(this).get(CouponViewModel.class);
//
//    binding = FragmentCouponBinding.inflate(inflater, container, false);
//    View root = binding.getRoot();
//
//        final TextView textView = binding.textCoupon;
//        couponViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        return root;
//    }
//
//@Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
}