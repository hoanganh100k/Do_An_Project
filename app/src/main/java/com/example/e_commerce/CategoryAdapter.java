package com.example.e_commerce;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.lib.Model.CategoryModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<CategoryModel> categoryModelList;          //list cac category

    public CategoryAdapter(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent,false); // Lấy view của các cái item
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        String icon = categoryModelList.get(position).getCategoryLink();
        String name = categoryModelList.get(position).getCategoryName();
        holder.setCategory(name,position);
        holder.setCategoryIcon(icon);
    }

    //đếm item có trong recycler view
    @Override
    public int getItemCount() {
        if(categoryModelList != null)
            return categoryModelList.size();
        return 0;
    }

//để khai báo các phần tử UI và thao tác trên nó
    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        private ImageView categoryIcon;
        private TextView categoryName;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryIcon = itemView.findViewById(R.id.category_icon);
            categoryName = itemView.findViewById(R.id.category_name);
        }
        private void setCategoryIcon(String iconLink){
            if(!iconLink.equals("null"))
            {
                Glide.with(itemView.getContext()).load(iconLink).apply(new RequestOptions().placeholder(R.drawable.ic_menu_home)).into(categoryIcon);       //Set ảnh bằng Glide
            }
        }
        private void setCategory(String name,int position){
            categoryName.setText(name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position != 0){
                        Intent  categoryIntent = new Intent(itemView.getContext(),CategoryActivity.class);
                        categoryIntent.putExtra("CategoryName", name);
                        itemView.getContext().startActivity(categoryIntent);
                    }
                }
            });
        }
    }

}
