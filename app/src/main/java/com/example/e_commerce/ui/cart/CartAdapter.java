package com.example.e_commerce.ui.cart;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.e_commerce.Config;
import com.example.e_commerce.DBqueries;
import com.example.e_commerce.DeliveryActivity;
import com.example.e_commerce.R;
import com.example.lib.Model.CartItemModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CartAdapter extends RecyclerView.Adapter {

    /////coupan dialog
    private TextView coupanTitle, discountedPrice, originalPrice;
    private TextView coupanExpiryDate;
    private TextView coupanBody;
    private RecyclerView coupanRecyclerview;
    private LinearLayout selectedCoupan, applyORremoveBtnContainer;
    private TextView footerText;
    private LinearLayout coupanRedemLayout;
    private Button applyCoupanBtn, removeCoupanBtn;
    private Long productPriceValue;
    private int statusPos = -1;

    /////coupan dialog

    private List<CartItemModel> cartItemModelList;
    private boolean showDeleteBtn;
    private int lastpos = -1;
    private TextView cartTotalAmount;

    public CartAdapter(List<CartItemModel> cartItemModelList, TextView cartTotalAmount, boolean showDeleteBtn, int _statusPos) {
        this.cartItemModelList = cartItemModelList;
        this.cartTotalAmount = cartTotalAmount;
        this.showDeleteBtn = showDeleteBtn;
        this.statusPos = _statusPos;
    }

    @Override
    public int getItemViewType(int position) {

        switch (cartItemModelList.get(position).getType()) {
            case 0:
                return CartItemModel.CART_ITEM;
            case 1:
                return CartItemModel.TOTAL_AMOUNT;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case CartItemModel.CART_ITEM:
                View cartItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
                return new cartItemViewHolder(cartItemView);
            case CartItemModel.TOTAL_AMOUNT:
                View cartTotalView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_total_amount_layout, parent, false);
                return new cartTotalAmountViewHolder(cartTotalView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        switch (cartItemModelList.get(position).getType()) {
            case CartItemModel.CART_ITEM:
                String productID = cartItemModelList.get(position).getProductID();
                String resource = cartItemModelList.get(position).getProductImage();
                String title = cartItemModelList.get(position).getProductTitle();
//                Long freecoupans=cartItemModelList.get(position).getFreeCoupans();
                String productprice = cartItemModelList.get(position).getProductPrice();
//                String cuttedPrice=cartItemModelList.get(position).getCuttedPrice();
//                Long offersApplied=cartItemModelList.get(position).getOffersApplied();
                Long productQty = cartItemModelList.get(position).getProductQuantity();
//                Long maxQty=cartItemModelList.get(position).getMaxQuantity();
//                Long stockQty=cartItemModelList.get(position).getStockQuantity();
//                boolean inStock=cartItemModelList.get(position).isInStock();
//                boolean codAvailable=cartItemModelList.get(position).isCOD();
//                boolean qtyError=cartItemModelList.get(position).isQtyError();
//                List<String> qtyIDs=cartItemModelList.get(position).getQtyIDs();
//,offersApplied,
                ((cartItemViewHolder) holder).setItemDetails(productID, resource, title, productprice, position, productQty, statusPos);
                break;
            case CartItemModel.TOTAL_AMOUNT:

                int totalItems = 0, totalAmount, savedAmount = 0;
                int totalItemsPrice = 0;
                String deliveryPrice;

                for (int x = 0; x < cartItemModelList.size(); x++) {
                    if (cartItemModelList.get(x).getType() == CartItemModel.CART_ITEM && cartItemModelList.get(x).isInStock()) {
                        int qty = Integer.parseInt(String.valueOf(cartItemModelList.get(x).getProductQuantity()));
                        totalItems = totalItems + qty;
                        if (TextUtils.isEmpty(cartItemModelList.get(x).getSelectedCoupanId())) {
                            totalItemsPrice = totalItemsPrice + Integer.parseInt(cartItemModelList.get(x).getProductPrice()) * qty;
                        } else {
                            totalItemsPrice = totalItemsPrice + Integer.parseInt(cartItemModelList.get(x).getDiscountedPrice()) * qty;
                        }

                        if (!TextUtils.isEmpty(cartItemModelList.get(x).getCuttedPrice())) {
                            savedAmount = savedAmount + (Integer.parseInt(cartItemModelList.get(x).getCuttedPrice()) - Integer.parseInt(cartItemModelList.get(x).getProductPrice())) * qty;
                            if (!TextUtils.isEmpty(cartItemModelList.get(x).getSelectedCoupanId())) {
                                savedAmount = savedAmount + (Integer.parseInt(cartItemModelList.get(x).getProductPrice()) - Integer.parseInt(cartItemModelList.get(x).getDiscountedPrice())) * qty;
                            }
                        } else {
                            if (!TextUtils.isEmpty(cartItemModelList.get(x).getSelectedCoupanId())) {
                                savedAmount = savedAmount + (Integer.parseInt(cartItemModelList.get(x).getProductPrice()) - Integer.parseInt(cartItemModelList.get(x).getDiscountedPrice())) * qty;
                            }
                        }
                    }
                }
                if (totalItemsPrice > 500) {
                    deliveryPrice = "FREE";
                    totalAmount = totalItemsPrice;
                } else {
                    deliveryPrice = "60";
                    totalAmount = totalItemsPrice + 60;
                }
                cartItemModelList.get(position).setTotalItems(totalItems);
                cartItemModelList.get(position).setTotalItemsPrice(totalItemsPrice);
                cartItemModelList.get(position).setDeliveryPrice(deliveryPrice);
                cartItemModelList.get(position).setTotalAmount(totalAmount);
                cartItemModelList.get(position).setSavedAmount(savedAmount);
                ((cartTotalAmountViewHolder) holder).setTotalAmount(totalItems, totalItemsPrice, deliveryPrice, totalAmount, savedAmount);
                break;
            default:
                return;
        }
        if (lastpos < position) {
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_in);
            holder.itemView.setAnimation(animation);
            lastpos = position;
        }
    }


    @Override
    public int getItemCount() {
        return cartItemModelList.size();
    }

    class cartItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage, freeCoupanIcon, cod;
        private LinearLayout deleteBtn, coupanRedemptionLayout;
        private TextView productTitle, freeCoupans, productPrice, cuttedPrice, offersApplied, coupansApplied, productQuantity, coupanRedemptionBody;
        private Button reedemBtn;


        public cartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
//            freeCoupanIcon=itemView.findViewById(R.id.product_title);
            productTitle = itemView.findViewById(R.id.product_title);
//            freeCoupans=itemView.findViewById(R.id.product_title);
            productPrice = itemView.findViewById(R.id.product_price);
//            cuttedPrice=itemView.findViewById(R.id.cutted_price);
//            offersApplied=itemView.findViewById(R.id.product_title);
//            coupansApplied=itemView.findViewById(R.id.product_title);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            deleteBtn = itemView.findViewById(R.id.remove_item_btn);
//            coupanRedemptionLayout=itemView.findViewById(R.id.coupan_redemption_layout);
//            reedemBtn=itemView.findViewById(R.id.coupan_redemption_btn);
//            coupanRedemptionBody=itemView.findViewById(R.id.tv_coupan_redemption);
//            cod=itemView.findViewById(R.id.product_title);

        }

        //, Long offersAppliedNo,
        private void setItemDetails(final String productID, String resource, String title, final String productPriceText, final int position, final Long productQty, final int status) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.mipmap.pic)).into(productImage);
            productTitle.setText(title);
            System.out.println(title + "dsadasdasdsasa");

            final Dialog checkCoupanPricedialog = new Dialog(itemView.getContext());
            checkCoupanPricedialog.setCancelable(false);
            checkCoupanPricedialog.setContentView(R.layout.coupan_redeem_dialog);
            checkCoupanPricedialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            NumberFormat formatter = new DecimalFormat("#,###");
            productPrice.setText(formatter.format(Integer.parseInt(productPriceText))+"VNĐ");
            productPrice.setTextColor(Color.parseColor("#000000"));

            ////// coupan redemption dialog


            ImageView toogleRecyclerView = checkCoupanPricedialog.findViewById(R.id.toggle_recyclerview);
            coupanRecyclerview = checkCoupanPricedialog.findViewById(R.id.coupans_recyclerView);
            selectedCoupan = checkCoupanPricedialog.findViewById(R.id.selected_coupan);
            originalPrice = checkCoupanPricedialog.findViewById(R.id.original_price);
            discountedPrice = checkCoupanPricedialog.findViewById(R.id.discounted_price);
            coupanTitle = checkCoupanPricedialog.findViewById(R.id.coupan_title);
            coupanExpiryDate = checkCoupanPricedialog.findViewById(R.id.coupan_validity);
            coupanBody = checkCoupanPricedialog.findViewById(R.id.coupan_body);
            applyCoupanBtn = checkCoupanPricedialog.findViewById(R.id.apply_btn);
            removeCoupanBtn = checkCoupanPricedialog.findViewById(R.id.remove_btn);
            applyORremoveBtnContainer = checkCoupanPricedialog.findViewById(R.id.apply_or_remove_btn_container);
            footerText = checkCoupanPricedialog.findViewById(R.id.footer_text);
            OkHttpClient client = new OkHttpClient();
            footerText.setVisibility(View.GONE);
            applyORremoveBtnContainer.setVisibility(View.VISIBLE);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            coupanRecyclerview.setLayoutManager(linearLayoutManager);

            ///for coupan dialog
            originalPrice.setText(productPrice.getText());

            ///for coupan dialog
            if (status == 1) {
                deleteBtn.setVisibility(View.GONE);
            }


            toogleRecyclerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialogRecyclerView();
                }
            });


            ////// coupan redemption dialog

            productQuantity.setText("Qty: " + String.valueOf(productQty));

            if (status == 0) {
                productQuantity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog qtyDialog = new Dialog(itemView.getContext());
                        qtyDialog.setContentView(R.layout.quantity_dialog);
                        qtyDialog.setCancelable(false);

                        qtyDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                        final EditText qtyNo = qtyDialog.findViewById(R.id.quantity_no);
                        Button cancelBtn = qtyDialog.findViewById(R.id.cancel_btn);
                        Button okBtn = qtyDialog.findViewById(R.id.ok_btn);

                        cancelBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                qtyDialog.dismiss();
                            }
                        });

                        okBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                productQuantity.setText("Qty: " + qtyNo.getText().toString());
                                DBqueries.cartItemModelList.get(position).setCartQty(Long.parseLong(qtyNo.getText().toString()));
                                System.out.println(DBqueries.cartItemModelList.get(position).getProductQuantity());


                                AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
                                    boolean CheckStatus = false;
                                    @Override
                                    protected String doInBackground(String... params) {
                                        String url = Config.IP_ADDRESS + "/api/giohang/giohang_edit";
                                        RequestBody formBody = new FormBody.Builder()
                                                .add("SDT", DBqueries.email)
                                                .add("MAHANGHOA", DBqueries.cartItemModelList.get(position).getProductID())
                                                .add("SOLUONG",  qtyNo.getText().toString())
                                                .build();
                                        Request request = new Request.Builder()
                                                .url(url)
                                                .patch(formBody)
                                                .header("Accept-Encoding", "identity")
                                                .build();

                                        try (Response response = client.newCall(request).execute()) {
                                            if (!response.isSuccessful()) {
                                                throw new IOException("Unexpected code: " + response);
                                            } else {
                                                String jsonData = response.body().string();

                                                JSONArray json = new JSONArray(jsonData);
                                                System.out.println(json);
                                                CheckStatus = true;
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        return "cartUpdate";
                                    }

                                    protected void onPostExecute(String result) {
                                        DBqueries.tong = 0;
                                        for (int i = 0; i < DBqueries.cartItemModelList.size(); i++) {
                                            DBqueries.tong += Integer.parseInt(DBqueries.cartItemModelList.get(i).getProductPrice())*DBqueries.cartItemModelList.get(i).getProductQuantity();
                                        }
                                        NumberFormat formatter = new DecimalFormat("#,###");
                                        cartTotalAmount.setText(formatter.format(DBqueries.tong)+"VNĐ");
                                        CartFragment.cartAdapter.notifyDataSetChanged();
                                    }

                                    ;
                                };
                                task.execute("cartUpdate");
                                qtyDialog.dismiss();
                            }
                        });
                        qtyDialog.show();
                    }
                });
            }


            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
                        boolean CheckStatus = false;
                        @Override
                        protected String doInBackground(String... params) {
                            String url = Config.IP_ADDRESS + "/api/giohang/giohang_delete_sp";
                            RequestBody formBody = new FormBody.Builder()
                                    .add("SDT", DBqueries.email)
                                    .add("MAHANGHOA", DBqueries.cartItemModelList.get(position).getProductID())
                                    .build();
                            Request request = new Request.Builder()
                                    .url(url)
                                    .post(formBody)
                                    .header("Accept-Encoding", "identity")
                                    .build();

                            try (Response response = client.newCall(request).execute()) {
                                if (!response.isSuccessful()) {
                                    throw new IOException("Unexpected code: " + response);
                                } else {
                                    String jsonData = response.body().string();

                                    JSONArray json = new JSONArray(jsonData);
                                    System.out.println(json);
                                    CheckStatus = true;
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            return "cartDeleteItime";
                        }

                        protected void onPostExecute(String result) {
                            DBqueries.cartItemModelList.remove(position);

                            DBqueries.tong = 0;
                            for (int i = 0; i < DBqueries.cartItemModelList.size(); i++) {
                                DBqueries.tong += Integer.parseInt(DBqueries.cartItemModelList.get(i).getProductPrice())*DBqueries.cartItemModelList.get(i).getProductQuantity();
                            }
                            NumberFormat formatter = new DecimalFormat("#,###");
                            cartTotalAmount.setText(formatter.format(DBqueries.tong)+"VNĐ");
                            CartFragment.cartAdapter.notifyDataSetChanged();
                        }

                        ;
                    };
                    task.execute("cartDeleteItime");

                }
            });

        }


        //                                         productID,        resource,          title,     freecoupans,                  productprice,         cuttedPrice,              position,                inStock,p        roductQty,maxQty,stockQty,qtyError,qtyIDs,codAvailable
        private void showDialogRecyclerView() {
            if (coupanRecyclerview.getVisibility() == View.GONE) {
                coupanRecyclerview.setVisibility(View.VISIBLE);
                selectedCoupan.setVisibility(View.GONE);
            } else {
                coupanRecyclerview.setVisibility(View.GONE);
                selectedCoupan.setVisibility(View.VISIBLE);
            }
        }
    }


    class cartTotalAmountViewHolder extends RecyclerView.ViewHolder {

        private TextView totalItems, totalItemsPrice, deliveryPrice, totalAmount, savedAmount;

        public cartTotalAmountViewHolder(@NonNull View itemView) {
            super(itemView);
            totalItems = itemView.findViewById(R.id.total_items);
            totalItemsPrice = itemView.findViewById(R.id.total_items_price);
            deliveryPrice = itemView.findViewById(R.id.delivery_price);
            totalAmount = itemView.findViewById(R.id.total_price);
            savedAmount = itemView.findViewById(R.id.saved_amount);
        }

        private void setTotalAmount(int totalItemText, int totalItemPriceText, String deliveryPricetext, int totalAmounttext, int savedAmounttext) {
            totalItems.setText("Price(" + totalItemText + ")items");
            totalItemsPrice.setText(totalItemPriceText + "VND");
            if (deliveryPricetext.equals("FREE")) {
                deliveryPrice.setText(deliveryPricetext);
            } else {
                deliveryPrice.setText(deliveryPricetext + "VND");
            }
            totalAmount.setText(totalAmounttext + "VND");
            cartTotalAmount.setText(totalAmounttext + "VND");
            savedAmount.setText("Bạn đã tiết kiệm được " + savedAmounttext + " VND trong đơn hàng này.");

            LinearLayout parent = (LinearLayout) cartTotalAmount.getParent().getParent();
            if (totalItemPriceText == 0) {
                if (DeliveryActivity.fromCart) {
                    cartItemModelList.remove(cartItemModelList.size() - 1);
                    DeliveryActivity.cartItemModelList.remove(DeliveryActivity.cartItemModelList.size() - 1);
                }
                if (showDeleteBtn) {
                    cartItemModelList.remove(cartItemModelList.size() - 1);
                }
                parent.setVisibility(View.GONE);
            } else {
                parent.setVisibility(View.VISIBLE);
            }
        }
    }

}
