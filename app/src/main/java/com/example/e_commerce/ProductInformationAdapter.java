package com.example.e_commerce;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductInformationAdapter extends RecyclerView.Adapter<ProductInformationAdapter.ViewHolder> {


    private List<ProductInformationModel> productInformationModelList;

    public ProductInformationAdapter(List<ProductInformationModel> productInformationModelList) {
        this.productInformationModelList = productInformationModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType){
            case ProductInformationModel.SPECIFICATION_TITLE:
                TextView title = new TextView(parent.getContext());
                title.setTypeface(null, Typeface.BOLD);
                title.setTextColor(Color.parseColor("#000000"));
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
                layoutParams.setMargins(setDp(16,parent.getContext()),setDp(16,parent.getContext()),setDp(16,parent.getContext()),setDp(8,parent.getContext()));
                title.setLayoutParams(layoutParams);
                return new ViewHolder(title);
            case ProductInformationModel.SPECIFICATION_BODY:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_information_item_layout, parent, false);
                return new ViewHolder(view);
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        switch (productInformationModelList.get(position).getType()){
            case ProductInformationModel.SPECIFICATION_TITLE:
                holder.setTitle(productInformationModelList.get(position).getTitle());
                break;
            case ProductInformationModel.SPECIFICATION_BODY:
                String featuretitle=productInformationModelList.get(position).getFeatureName();
                String featuredetail=productInformationModelList.get(position).getFeatureValue();
                holder.setFeatures(featuretitle,featuredetail);
                break;
            default:
                return;
        }

    }

    @Override
    public int getItemViewType(int position) {
        switch (productInformationModelList.get(position).getType()){
            case 0:
                return ProductInformationModel.SPECIFICATION_TITLE;
            case 1:
                return ProductInformationModel.SPECIFICATION_BODY;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return productInformationModelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView featureName,featurevalue,title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        private void setFeatures(String featuretitle,String featuredetail){
            featureName=itemView.findViewById(R.id.feature_name);
            featurevalue=itemView.findViewById(R.id.feature_value);
            featurevalue.setText(featuredetail);
            featureName.setText(featuretitle);

        }
        private void setTitle(String titleText){
            title= (TextView) itemView;
            title.setText(titleText);
        }
    }

    private int setDp(int dp, Context context){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.getResources().getDisplayMetrics());
    }
}





//package com.example.e_commerce;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//public class ProductInformationAdapter extends RecyclerView.Adapter<ProductInformationAdapter.ViewHolder> {
//
//    private List<ProductInformationModel> productInformationModelList;
//
//    public ProductInformationAdapter(List<ProductInformationModel> productInformationModelList) {
//        this.productInformationModelList = productInformationModelList;
//    }
//
//
//
//    @NonNull
//    @Override
//    public ProductInformationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_information_item_layout,viewGroup,false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ProductInformationAdapter.ViewHolder viewHolder, int position) {
//        String featureTitle = productInformationModelList.get(position).getFeatureName();
//        String featureDetail = productInformationModelList.get(position).getFeatureValue();
//        viewHolder.setFeatures(featureTitle,featureDetail);
//    }
//
//    @Override
//    public int getItemCount() {
//        return productInformationModelList.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        private TextView featureName;
//        private TextView featureValue;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            featureName = itemView.findViewById(R.id.feature_name);
//            featureValue = itemView.findViewById(R.id.feature_value);
//        }
//        private void setFeatures(String featureTitle, String featureDetail){
//            featureName.setText(featureTitle);
//            featureValue.setText(featureDetail);
//        }
//
//
//    }
//}
