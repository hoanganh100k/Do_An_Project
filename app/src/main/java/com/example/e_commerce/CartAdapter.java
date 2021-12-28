package com.example.e_commerce;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter {
    //Dữ liệu hiện thị là danh sách cart
    private List mCart;
    // Lưu Context để dễ dàng truy cập
    private Context mContext;

    public CartAdapter(List _cart, Context mContext) {
        this.mCart = _cart;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Nạp layout cho View
        View cartView =
                inflater.inflate(R.layout.cart1_item_layout, parent, false);

        ViewHolder viewHolder1 = new ViewHolder(cartView);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return mCart.size();
    }

    /**
     * Lớp nắm giữ cấu trúc view
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private View itemview;
        public TextView txtTitle;
        public TextView txtPrice;
        public TextView txtQuantily;
        public LinearLayout btnNext;

        public ViewHolder(View itemView) {
            super(itemView);
            itemview = itemView;
            txtTitle = itemView.findViewById(R.id.product_title1);
            txtPrice = itemView.findViewById(R.id.product_price1);
            txtQuantily = itemView.findViewById(R.id.product_quantity1);
            btnNext = itemView.findViewById(R.id.remove_item_btn1);

            //Xử lý khi nút xoá được bấm
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
