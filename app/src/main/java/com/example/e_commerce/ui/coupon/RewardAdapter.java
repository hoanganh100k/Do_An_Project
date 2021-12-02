package com.example.e_commerce.ui.coupon;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.R;
import com.example.lib.Model.CartItemModel;
import com.example.lib.Model.RewardModel;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.List;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.Viewholder> {

    private List<RewardModel> rewardModelList;
    private List<CartItemModel> cartItemModelList;
    private Boolean useMinilayout=false;
    private RecyclerView coupanRecyclerview;
    private LinearLayout selectedCoupan;
    private Long productOriginalPrice;
    private TextView selectedcoupanTitle;
    private TextView selectedcoupanExpiryDate;
    private TextView selectedcoupanBody,discountedPrice;
    private int position=-1;

    public RewardAdapter(List<RewardModel> rewardModelList) {
        this.rewardModelList = rewardModelList;

    }
    public RewardAdapter(int position,List<RewardModel> rewardModelList, Boolean useMinilayout, RecyclerView coupanRecyclerview, LinearLayout selectedCoupan, Long productOriginalPrice, TextView coupanTitle, TextView coupanExpiryDate, TextView coupanBody,TextView discountedPrice,List<CartItemModel> cartItemModelList) {
        this.position=position;
        this.rewardModelList = rewardModelList;
        this.useMinilayout = useMinilayout;
        this.coupanRecyclerview = coupanRecyclerview;
        this.selectedCoupan = selectedCoupan;
        this.productOriginalPrice = productOriginalPrice;
        this.selectedcoupanTitle = coupanTitle;
        this.selectedcoupanExpiryDate = coupanExpiryDate;
        this.selectedcoupanBody = coupanBody;
        this.discountedPrice=discountedPrice;
        this.cartItemModelList=cartItemModelList;
    }
    public RewardAdapter(List<RewardModel> rewardModelList, Boolean useMinilayout, RecyclerView coupanRecyclerview, LinearLayout selectedCoupan, Long productOriginalPrice, TextView coupanTitle, TextView coupanExpiryDate, TextView coupanBody,TextView discountedPrice) {
        this.rewardModelList = rewardModelList;
        this.useMinilayout = useMinilayout;
        this.coupanRecyclerview = coupanRecyclerview;
        this.selectedCoupan = selectedCoupan;
        this.productOriginalPrice = productOriginalPrice;
        this.selectedcoupanTitle = coupanTitle;
        this.selectedcoupanExpiryDate = coupanExpiryDate;
        this.selectedcoupanBody = coupanBody;
        this.discountedPrice=discountedPrice;
    }
    public RewardAdapter(List<RewardModel> rewardModelList, boolean userMiniLayout) {
        this.rewardModelList = rewardModelList;
        this.useMinilayout = userMiniLayout;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view;
        if(useMinilayout){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mini_rewards_item_layout,parent,false);

        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rewards_item_layout,parent,false);

        }
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        String type=rewardModelList.get(position).getType();
        String upperLimit=rewardModelList.get(position).getUpperLimit();
        String lowerLimit=rewardModelList.get(position).getLowerLimit();
        String discORamt=rewardModelList.get(position).getDiscORamt();
        String body=rewardModelList.get(position).getCoupanBody();
        Timestamp validity=rewardModelList.get(position).getTimestamp();
        boolean alreadyUsed=rewardModelList.get(position).isAlreadyUsed();
        String coupanId=rewardModelList.get(position).getCoupanId();
        holder.setData(type,upperLimit,lowerLimit,discORamt,body,validity,alreadyUsed,coupanId);
    }

    @Override
    public int getItemCount() {
        return rewardModelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        private TextView coupanTitle;
        private TextView coupanExpiryDate;
        private TextView coupanBody;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            coupanTitle = itemView.findViewById(R.id.coupan_title);
            coupanExpiryDate = itemView.findViewById(R.id.coupan_validity);
            coupanBody = itemView.findViewById(R.id.coupan_body);
        }
        private void setData(final String type, final String upperLimit, final String lowerLimit, final String discORamt, final String body, final Timestamp validity, final boolean alreadyUsed, final String coupanId){
            if(type.equals("Discount")){
                coupanTitle.setText(type);
            }else {
                coupanTitle.setText("Giảm "+discORamt+" VND");
            }
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");

            if(alreadyUsed){
                coupanExpiryDate.setText("5DSHOP Discount");
                coupanExpiryDate.setTextColor(itemView.getContext().getResources().getColor(R.color.colorPrimary));
                coupanBody.setTextColor(Color.parseColor("#50ffffff"));
                coupanTitle.setTextColor(Color.parseColor("#50ffffff"));
            }else {
                coupanBody.setTextColor(Color.parseColor("#ffffff"));
                coupanTitle.setTextColor(Color.parseColor("#ffffff"));
                coupanExpiryDate.setTextColor(itemView.getContext().getResources().getColor(R.color.blue));
      //          coupanExpiryDate.setText(simpleDateFormat.format(validity));
            }
            coupanBody.setText(body);


            if(useMinilayout){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!alreadyUsed) {

                            if (type.equals("Discount")) {
                                selectedcoupanTitle.setText(type);
                            } else {
                                selectedcoupanTitle.setText("Giảm" + discORamt + " VND");
                            }
                            selectedcoupanExpiryDate.setText("Đến hết ngày " + simpleDateFormat.format(validity));
                            selectedcoupanBody.setText(body);

                            if (productOriginalPrice > Long.valueOf(lowerLimit) && productOriginalPrice < Long.valueOf(upperLimit)) {
                                if (type.equals("Discount")) {
                                    Long discountAmount = productOriginalPrice * Long.valueOf(discORamt) / 100;
                                    discountedPrice.setText(String.valueOf(productOriginalPrice - discountAmount) + "VND");
                                } else {
                                    discountedPrice.setText(String.valueOf(productOriginalPrice - Long.valueOf(discORamt)) + "VND");

                                }
                                if(position != -1) {
                                    cartItemModelList.get(position).setSelectedCoupanId(coupanId);
                                }
                            } else {
                                if(position != -1) {
                                    cartItemModelList.get(position).setSelectedCoupanId(null);
                                }
                                Toast.makeText(itemView.getContext(), "Sản phẩm không có trong chương trình giảm giá", Toast.LENGTH_SHORT).show();
                            }

                            if (coupanRecyclerview.getVisibility() == View.GONE) {
                                coupanRecyclerview.setVisibility(View.VISIBLE);
                                selectedCoupan.setVisibility(View.GONE);
                            } else {
                                coupanRecyclerview.setVisibility(View.GONE);
                                selectedCoupan.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
            }


        }
    }
}
