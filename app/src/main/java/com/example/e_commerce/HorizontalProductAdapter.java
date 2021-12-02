package com.example.e_commerce;


import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lib.Model.HorizontalProductModel;

import java.util.List;

public class HorizontalProductAdapter extends RecyclerView.Adapter<HorizontalProductAdapter.ViewHolder> {

    private List<HorizontalProductModel> horizontalProductModelsList;

    public HorizontalProductAdapter() {
    }

    public HorizontalProductAdapter(List<HorizontalProductModel> horizontalProductModelsList) {
        this.horizontalProductModelsList = horizontalProductModelsList;
    }

    @NonNull
    @Override
    public HorizontalProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalProductAdapter.ViewHolder holder, int position) {
        int resource = horizontalProductModelsList.get(position).getProductImage();
        String name = horizontalProductModelsList.get(position).getProductName();
        String price = horizontalProductModelsList.get(position).getProductPrice();

        holder.setProductImage(resource);
        holder.setProductName(name);
        holder.setProductPrice(price);
    }

    @Override
    public int getItemCount() {
        if(horizontalProductModelsList != null)
            return horizontalProductModelsList.size();
        return 0;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView productImage;
        private TextView productName;
        private TextView productPrice;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.horizontal_product_img);
            productName = itemView.findViewById(R.id.horizontal_product_name);
            productPrice = itemView.findViewById(R.id.horizontal_product_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailsIntent = new Intent(itemView.getContext(),ProductDetailsActivity.class);
                    itemView.getContext().startActivity(productDetailsIntent);
                }
            });
        }




        private void setProductImage(int resource){
            productImage.setImageResource(resource);
        }
        private void setProductName(String name){
            productName.setText(name);
        }
        private void setProductPrice(String price){
            productPrice.setText(price);
        }
    }
}
