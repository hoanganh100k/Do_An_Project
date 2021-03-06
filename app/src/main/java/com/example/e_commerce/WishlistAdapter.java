package com.example.e_commerce;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.lib.Model.WishlistModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {

    private List<WishlistModel> wishlistModelList;
    private Boolean wishlist, fromSearch = false;
    private int lastpos = -1;

    public WishlistAdapter(List<WishlistModel> wishlistModelList) {
        this.wishlistModelList = wishlistModelList;
    }
    public Boolean getFromSearch() {
        return fromSearch;
    }

    public List<WishlistModel> getWishlistModelList() {
        return wishlistModelList;
    }

    public void setWishlistModelList(List<WishlistModel> wishlistModelList) {
        this.wishlistModelList = wishlistModelList;
    }
    public void setFromSearch(Boolean fromSearch) {
        this.fromSearch = fromSearch;
    }

    public WishlistAdapter(List<WishlistModel> wishlistModelList, Boolean wishlist) {
        this.wishlistModelList = wishlistModelList;
        this.wishlist = wishlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item_layout, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public int getItemCount(){
        return wishlistModelList.size();
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (lastpos < position) {
            String productId = wishlistModelList.get(position).getProductId();
            String title = wishlistModelList.get(position).getProductTitle();
            String resource = wishlistModelList.get(position).getProductImage();
            long freeCoupans = wishlistModelList.get(position).getFreeCoupens();
            String rating = wishlistModelList.get(position).getRating();
            long totalRatings = wishlistModelList.get(position).getTotalRatings();
            String productPrice = wishlistModelList.get(position).getProductPrice();
            String cuttedPrice = wishlistModelList.get(position).getCuttedPrice();
            Boolean paymentMethod = wishlistModelList.get(position).getCOD();
            boolean inStock = wishlistModelList.get(position).isInStock();
            holder.setData(productId, resource, title, freeCoupans, rating, totalRatings, productPrice, cuttedPrice, paymentMethod, position, inStock);

            if (lastpos < position) {
                Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_in);
                holder.itemView.setAnimation(animation);
                lastpos = position;
            }
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView productTitle;
        private TextView freeCoupens;
        private ImageView coupenIcon;
        private TextView rating;
        private TextView totalRatings;
        private View priceCut;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView paymentMethod;
        private ImageButton deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            freeCoupens = itemView.findViewById(R.id.free_coupan);
            coupenIcon = itemView.findViewById(R.id.coupan_icon);
            rating = itemView.findViewById(R.id.tv_product_rating_miniview);
            totalRatings = itemView.findViewById(R.id.total_ratings);
            priceCut = itemView.findViewById(R.id.price_cut);
            productPrice = itemView.findViewById(R.id.product_price);
            cuttedPrice = itemView.findViewById(R.id.cutted_price);
            paymentMethod = itemView.findViewById(R.id.payment_method);
            deleteBtn = itemView.findViewById(R.id.delete_btn);
        }

        private void setData(final String productId, String resource, String title, long freeCoupansNo, String averageRate, long totalRatingsNo, String price, String cuttedPriceValue, Boolean payMethod, final int index, boolean inStock) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.mipmap.pic_foreground)).into(productImage);
            productTitle.setText(title);
            paymentMethod.setText(averageRate);
            if (freeCoupansNo != 0 && inStock) {
                coupenIcon.setVisibility(View.VISIBLE);
                if (freeCoupansNo == 1) {
                    freeCoupens.setText("free " + freeCoupansNo + " coupan");
                } else {
                    freeCoupens.setText("free " + freeCoupansNo + " coupans");
                }
            } else {
                coupenIcon.setVisibility(View.INVISIBLE);
                freeCoupens.setVisibility(View.INVISIBLE);
            }
            LinearLayout linearLayout = (LinearLayout) rating.getParent();
            if (inStock) {
                rating.setVisibility(View.VISIBLE);
                totalRatings.setVisibility(View.VISIBLE);
                cuttedPrice.setVisibility(View.VISIBLE);
                productPrice.setTextColor(Color.parseColor("#000000"));

                rating.setText(averageRate);
                totalRatings.setText("(" + totalRatingsNo + ")ratings");
                NumberFormat formatter = new DecimalFormat("#,###");
                productPrice.setText(price+" VN??");
                cuttedPrice.setText(cuttedPriceValue + "VND");

                if (payMethod) {
                    paymentMethod.setVisibility(View.VISIBLE);
                } else {
                    paymentMethod.setVisibility(View.INVISIBLE);
                }
                linearLayout.setVisibility(View.VISIBLE);
            } else {
                linearLayout.setVisibility(View.INVISIBLE);
                paymentMethod.setVisibility(View.INVISIBLE);
                rating.setVisibility(View.INVISIBLE);
                totalRatings.setVisibility(View.INVISIBLE);
                cuttedPrice.setVisibility(View.INVISIBLE);
                productPrice.setText("H???t h??ng");
                productPrice.setTextColor(itemView.getContext().getResources().getColor(R.color.colorPrimary));
            }

            if (wishlist) {
                deleteBtn.setVisibility(View.VISIBLE);
            } else {
                deleteBtn.setVisibility(View.GONE);
            }

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!ProductDetailsActivity.running_wishlist_querry) {
                        ProductDetailsActivity.running_wishlist_querry = true;
                        DBqueries.removeFromWishlist(index, itemView.getContext());
                        Toast.makeText(itemView.getContext(), "X??a th??nh c??ng!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (fromSearch) {
                        ProductDetailsActivity.fromSearch = true;
                    }
                    itemView.getContext().startActivity(new Intent(itemView.getContext(), ProductDetailsActivity.class).putExtra("PRODUCT_ID", productId));
                }
            });
        }
    }
}