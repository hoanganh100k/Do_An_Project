package com.example.e_commerce;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.e_commerce.ui.cart.CartFragment;
import com.example.e_commerce.ui.coupon.CouponFragment;
import com.example.e_commerce.ui.home.HomePageAdapter;
import com.example.e_commerce.ui.order.MyOrderAdapter;
import com.example.e_commerce.ui.order.OrderFragment;
import com.example.e_commerce.ui.wishlist.WishlistFragment;
import com.example.lib.Model.AddressesModel;
import com.example.lib.Model.CartItemModel;
import com.example.lib.Model.CategoryModel;
import com.example.lib.Model.HomePageModel;
import com.example.lib.Model.HorizontalProductScrollModel;
import com.example.lib.Model.MyOrderItemModel;
import com.example.lib.Model.RewardModel;
import com.example.lib.Model.SliderModel;
import com.example.lib.Model.WishlistModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBqueries {
    public static String email,fullname,profile;

    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<CategoryModel> categoryModelList = new ArrayList<>();
    public static List<CategoryModel> categoryModelList1 = new ArrayList<>();

    public static List<HomePageModel> homePageModelsList = new ArrayList<>();
    public static List<String> wishlist = new ArrayList<>();
    public static List<WishlistModel>wishlistModelList = new ArrayList<>();
    public static List<List<HomePageModel>> lists = new ArrayList<>();
    public static List<String> loadedCategoriesName = new ArrayList<>();
    public static List<String> loadedCategoriesNames=new ArrayList<>();

    public static List<AddressesModel> addressesModelList=new ArrayList<>();
    public static List<NotificationModel> notificationModelList=new ArrayList<>();
    public static List<String> cartList=new ArrayList<>();
    public static List<CartItemModel> cartItemModelList=new ArrayList<>();
    public static List<RewardModel> rewardModelList=new ArrayList<>();
    private static ListenerRegistration registration;
    public static List<Long> Rating=new ArrayList<>();
    public static List<String> RatedIds=new ArrayList<>();
    public static List<MyOrderItemModel> myOrderItemModelList=new ArrayList<>();
    public static int selectedAddress=-1;

    public static void loadCategories(final Context context, final CategoryAdapter categoryAdapter) {
        categoryModelList.add(new CategoryModel(1,"https://cdn-icons-png.flaticon.com/512/2250/2250401.png", "Xăng"));
        categoryModelList.add(new CategoryModel(2,"https://cdn-icons-png.flaticon.com/512/2250/2250401.png", "Sản Phẩm"));
        categoryModelList.add(new CategoryModel(3,"https://cdn-icons-png.flaticon.com/512/2250/2250401.png", "Dầu"));
        categoryAdapter.notifyDataSetChanged();             //Mỗi lần thêm mới nó sẽ refesh lại cái data
    }
    public static void loadCategories1(final Context context, final CategoryAdapter categoryAdapter) {
        categoryModelList1.add(new CategoryModel(4,"https://cdn-icons-png.flaticon.com/512/2250/2250401.png", "Xăng Con"));
        categoryModelList1.add(new CategoryModel(5,"https://cdn-icons-png.flaticon.com/512/2250/2250401.png", "Sản Phẩm Con"));
        categoryModelList1.add(new CategoryModel(6,"https://cdn-icons-png.flaticon.com/512/2250/2250401.png", "Dầu Con"));
        categoryAdapter.notifyDataSetChanged();             //Mỗi lần thêm mới nó sẽ refesh lại cái data
    }

    public static void setFragmentData(final Context context, final HomePageAdapter adapter, final int position, String categoriesName){
        //Banner
        List<SliderModel> sliderModelList = new ArrayList<>();
        sliderModelList.add(new SliderModel("https://free.vector6.com/wp-content/uploads/2020/11/211104082-Vector-thiet-ke-banner-chuyen-nghiep.jpg"));        // lay data cua banner ve gan vao list
        sliderModelList.add(new SliderModel("https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/640px-Image_created_with_a_mobile_phone.png"));        // lay data cua banner ve gan vao list

        lists.get(position).add(new HomePageModel(0, sliderModelList));
        //Sản Phẩm Hot
        //Đây là hàm của category đầu tiên
        List<WishlistModel> viewAllProduct = new ArrayList<>();
        List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(
                "1"
                ,"https://i.ibb.co/vhxHPq1/CNG2-1.png"
                ,"Bột Làm Đẹp"
                ,"Đ"
                ,"100"));                    // lay data cua banner ve gan vao list
        viewAllProduct.add(new WishlistModel(
                "1"
                ,"https://i.ibb.co/vhxHPq1/CNG2-1.png"
                ,"Bột Làm Đẹp"
                ,1000
                ,"3"
                ,5
                ,"1000"
                ,"1000"
                ,false
                ,true));

        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(
                "11"
                ,"https://i.ibb.co/B2dDV4x/CNU2-1.jpg"
                ,"Bột Gạo"
                ,"Đ"
                ,"100"));                    // lay data cua banner ve gan vao list
        viewAllProduct.add(new WishlistModel(
                "11"
                ,"https://i.ibb.co/B2dDV4x/CNU2-1.jpg"
                ,"Bột Gạo"
                ,1000
                ,"3"
                ,5
                ,"1000"
                ,"1000"
                ,false
                ,true));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(
                "16"
                ,"https://i.ibb.co/1zxqfc9/CNU3-1.jpg"
                ,"Phân Bón"
                ,"Đ"
                ,"100"));                    // lay data cua banner ve gan vao list
        viewAllProduct.add(new WishlistModel(
                "16"
                ,"https://i.ibb.co/1zxqfc9/CNU3-1.jpg"
                ,"Phân Bón"
                ,1000
                ,"3"
                ,5
                ,"1000"
                ,"1000"
                ,false
                ,true));
        lists.get(position).add(new HomePageModel(1, "Sản phẩm Hot","#FFFFFF",horizontalProductScrollModelList,viewAllProduct));
        //Sản Phẩm
        List<HorizontalProductScrollModel> gridLayoutModelList = new ArrayList<>();
        gridLayoutModelList.add(new HorizontalProductScrollModel(
                "0"
                ,"https://i.ibb.co/nw2GyRk/DPSK1-1.jpg"
                ,"Dầu mỏ"
                ,"Đ"
                ,"100000"
        ));
        gridLayoutModelList.add(new HorizontalProductScrollModel(
                "1"
                ,"https://i.ibb.co/5RyGsns/DPSK2-1.jpg"
                ,"Dầu mỏ 1"
                ,"Đ"
                ,"100000"
        ));
        gridLayoutModelList.add(new HorizontalProductScrollModel(
                "2"
                ,"https://i.ibb.co/YfZBbV7/DPSK3-1.jpg"
                ,"Dầu mỏ 2"
                ,"Đ"
                ,"100000"
        ));
        gridLayoutModelList.add(new HorizontalProductScrollModel(
                "3"
                ,"https://i.ibb.co/2gLBs0Q/DPSK4-1.jpg"
                ,"Dầu mỏ 3"
                ,"Đ"
                ,"100000"
        ));
        gridLayoutModelList.add(new HorizontalProductScrollModel(
                "4"
                ,"https://i.ibb.co/VNs5mh0/DPSK5-1.jpg"
                ,"Dầu mỏ 4"
                ,"Đ"
                ,"100000"
        ));
        gridLayoutModelList.add(new HorizontalProductScrollModel(
                "5"
                ,"https://i.ibb.co/k88rct6/DPSK6-1.jpg"
                ,"Dầu mỏ 5"
                ,"Đ"
                ,"100000"
        ));
        gridLayoutModelList.add(new HorizontalProductScrollModel(
                "6"
                ,"https://i.ibb.co/FW7Xp7n/DPSK7-1.jpg"
                ,"Dầu mỏ 6"
                ,"Đ"
                ,"100000"
        ));
        // lay data cua banner ve gan vao list
        lists.get(position).add(new HomePageModel(2, "Sản Phẩm","#FFFFFF",gridLayoutModelList));
        adapter.notifyDataSetChanged();             // NHỚ SET ADAPTER CHO THẰNG NÀO PHẢI XEM KĨ


    }

    //Mỗi lần bấm vô category sẽ load lại hàm này
    public static void setCategoryData(final Context context, final HomePageAdapter adapter, final int position, String categoriesName){
        int id_Category = -1; //Đây là ID Của Category
        for (CategoryModel category: categoryModelList) {
            if (category.getCategoryName().equals(categoriesName)){
                id_Category = category.getCategoryID();
                break;
            }
        }
        System.out.println(id_Category);
        List<WishlistModel> viewAllProduct = new ArrayList<>();
        List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(
                "10"
                ,"https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/640px-Image_created_with_a_mobile_phone.png"
                ,"Dầu 1"
                ,"Đ"
                ,"10000"));                    // lay data cua banner ve gan vao list
        viewAllProduct.add(new WishlistModel(
                "10"
                ,"https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/640px-Image_created_with_a_mobile_phone.png"
                ,"Dầu 1"
                ,10000
                ,"3"
                ,5
                ,"10000"
                ,"100000"
                ,true
                ,true));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(
                "21"
                ,"https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/640px-Image_created_with_a_mobile_phone.png"
                ,"Dầu 1"
                ,"Đ"
                ,"10000"));                    // lay data cua banner ve gan vao list
        viewAllProduct.add(new WishlistModel(
                "21"
                ,"https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/640px-Image_created_with_a_mobile_phone.png"
                ,"Dầu 1"
                ,10000
                ,"3"
                ,5
                ,"10000"
                ,"100000"
                ,true
                ,true));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(
                "31"
                ,"https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/640px-Image_created_with_a_mobile_phone.png"
                ,"Dầu 1"
                ,"Đ"
                ,"10000"));                    // lay data cua banner ve gan vao list
        viewAllProduct.add(new WishlistModel(
                "31"
                ,"https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/640px-Image_created_with_a_mobile_phone.png"
                ,"Dầu 1"
                ,10000
                ,"3"
                ,5
                ,"10000"
                ,"100000"
                ,true
                ,true));
        lists.get(position).add(new HomePageModel(1, "Sản Phẩm","#FFFFFF",horizontalProductScrollModelList,viewAllProduct));
        //San pham 2
        //Lưu ý: Phải trên 4 sản phẩm ở mục này
        List<HorizontalProductScrollModel> gridLayoutModelList = new ArrayList<>();
        gridLayoutModelList.add(new HorizontalProductScrollModel(
                "21"
                ,"https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/640px-Image_created_with_a_mobile_phone.png"
                ,"Xăng 1"
                ,"Đ"
                ,"10000"
        ));
        gridLayoutModelList.add(new HorizontalProductScrollModel(
                "22"
                ,"https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/640px-Image_created_with_a_mobile_phone.png"
                ,"Xăng 2"
                ,"Đ"
                ,"10000"
        ));
        gridLayoutModelList.add(new HorizontalProductScrollModel(
                "23"
                ,"https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/640px-Image_created_with_a_mobile_phone.png"
                ,"Xăng 3"
                ,"Đ"
                ,"10000"
        ));
        gridLayoutModelList.add(new HorizontalProductScrollModel(
                "24"
                ,"https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/640px-Image_created_with_a_mobile_phone.png"
                ,"Xăng 4"
                ,"Đ"
                ,"10000"
        ));
        gridLayoutModelList.add(new HorizontalProductScrollModel(
                "25"
                ,"https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/640px-Image_created_with_a_mobile_phone.png"
                ,"Xăng 5"
                ,"Đ"
                ,"10000"
        ));
        lists.get(position).add(new HomePageModel(2, "Sản Phẩm 2","#FFFFFF",gridLayoutModelList));
        adapter.notifyDataSetChanged();             // NHỚ SET ADAPTER CHO THẰNG NÀO PHẢI XEM KĨ

    }

    public static void loadRewards(final Context context, final Dialog loadingDialog,final boolean onRewardFragment) {

        rewardModelList.clear();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            final Date lastseendate=task.getResult().getDate("Last seen");

                            firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_REWARDS")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if(task.isSuccessful()){

                                                for(DocumentSnapshot documentSnapshot:task.getResult()){
                                                    if(documentSnapshot.get("type").toString().equals("Discount") && lastseendate.before(documentSnapshot.getDate("validity"))){  //&& lastseendate.before(documentSnapshot.getDate("validity"))
                                                        rewardModelList.add(new RewardModel(
                                                                documentSnapshot.getId()
                                                                ,documentSnapshot.get("type").toString()
                                                                ,documentSnapshot.get("upper_limit").toString()
                                                                ,documentSnapshot.get("lower_limit").toString()
                                                                ,documentSnapshot.get("percentage").toString()
                                                                ,documentSnapshot.get("body").toString()
                                                                ,(Timestamp) documentSnapshot.get("validity")
                                                                ,(boolean)documentSnapshot.get("alreadyUsed")
                                                        ));
                                                    }
                                                    else if(documentSnapshot.get("type").toString().equals("Flat VND OFF")&& lastseendate.before(documentSnapshot.getDate("validity"))){//&& lastseendate.before(documentSnapshot.getDate("validity"))
                                                        rewardModelList.add(new RewardModel(
                                                                documentSnapshot.getId()
                                                                ,documentSnapshot.get("type").toString()
                                                                ,documentSnapshot.get("upper_limit").toString()
                                                                ,documentSnapshot.get("lower_limit").toString()
                                                                ,documentSnapshot.get("amount").toString()
                                                                ,documentSnapshot.get("body").toString()
                                                                ,(Timestamp)documentSnapshot.get("validity")
                                                                ,(boolean)documentSnapshot.get("alreadyUsed")
                                                        ));
                                                    }
                                                }
                                                if(onRewardFragment) {
                                                    CouponFragment.adapter.notifyDataSetChanged();
                                                }
                                            }else {
                                                String error=task.getException().getMessage();
                                                Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                                            }
                                            loadingDialog.dismiss();
                                        }
                                    });

                        } else {
                            loadingDialog.dismiss();
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public static void loadWishList(final Context context, final Dialog dialog, final boolean loadProductData) {
        wishlist.clear();
//        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid())
//                .collection("USER_DATA").document("WISHLIST").get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (long x = 0; x < (long) task.getResult().get("list_size"); x++) {
//                                wishlist.add(task.getResult().get("product_ID_" + x).toString());
//
//                                if (DBqueries.wishlist.contains(ProductDetailsActivity.productID)) {
//                                    ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = true;
//                                    if (ProductDetailsActivity.addToWishListBtn != null) {
//                                        ProductDetailsActivity.addToWishListBtn.setSupportImageTintList(context.getResources().getColorStateList(R.color.colorPrimary));
//                                    }
//                                } else {
//                                    if (ProductDetailsActivity.addToWishListBtn != null) {
//                                        ProductDetailsActivity.addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
//                                    }
//                                    ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = false;
//                                }
//
//                                if (loadProductData) {
//                                    wishlistModelList.clear();
//                                    final String productId = task.getResult().get("product_ID_" + x).toString();
//                                    firebaseFirestore.collection("PRODUCTS").document(productId).get()
//                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                                    if (task.isSuccessful()) {
//                                                        final DocumentSnapshot documentSnapshot = task.getResult();
//                                                        firebaseFirestore.collection("PRODUCTS").document(productId).collection("QUANTITY").orderBy("time", Query.Direction.ASCENDING).get()
//                                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                                                    @Override
//                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                                                        if (task.isSuccessful()) {
//                                                                            if (task.getResult().getDocuments().size() < (long) documentSnapshot.get("stock_quantity")) {
//                                                                                wishlistModelList.add(new WishlistModel(
//                                                                                        productId
//                                                                                        , documentSnapshot.get("product_image_1").toString()
//                                                                                        , documentSnapshot.get("product_title").toString()
//                                                                                        , (long) documentSnapshot.get("free_coupens")
//                                                                                        , documentSnapshot.get("average_rating").toString()
//                                                                                        , (long) documentSnapshot.get("total_ratings")
//                                                                                        , documentSnapshot.get("product_price").toString()
//                                                                                        , documentSnapshot.get("cutted_price").toString()
//                                                                                        , (boolean) documentSnapshot.get("COD")
//                                                                                        , true
//                                                                                ));
//                                                                            } else {
//                                                                                wishlistModelList.add(new WishlistModel(
//                                                                                        productId
//                                                                                        , documentSnapshot.get("product_image_2").toString()
//                                                                                        , documentSnapshot.get("product_title").toString()
//                                                                                        , (long) documentSnapshot.get("free_coupens")
//                                                                                        , documentSnapshot.get("average_rating").toString()
//                                                                                        , (long) documentSnapshot.get("total_ratings")
//                                                                                        , documentSnapshot.get("product_price").toString()
//                                                                                        , documentSnapshot.get("cutted_price").toString()
//                                                                                        , (boolean) documentSnapshot.get("COD")
//                                                                                        , false
//                                                                                ));
//                                                                            }
//                                                                            WishlistFragment.wishlistAdapter.notifyDataSetChanged();
//                                                                        } else {
//                                                                            String error = task.getException().getMessage();
//                                                                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
//                                                                        }
//                                                                    }
//                                                                });
//
//                                                    } else {
//                                                        String error = task.getException().getMessage();
//                                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
//                                                    }
//                                                }
//                                            });
//                                }
//                            }
//
//                        } else {
//                            String error = task.getException().getMessage();
//                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
//                        }
//                        dialog.dismiss();
//                    }
//                });
    }

    public static void removeFromWishlist(final int index, final Context context) {
        final String removedProductId=wishlist.get(index);
        wishlist.remove(index);

        Map<String, Object> updateWishlist = new HashMap<>();

        for (int x = 0; x < wishlist.size(); x++) {
            updateWishlist.put("product_ID_" + x, wishlist.get(x));
        }
        updateWishlist.put("list_size", (long) wishlist.size());

        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid())
                .collection("USER_DATA").document("WISHLIST").set(updateWishlist)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if(wishlistModelList.size() != 0){
                                wishlistModelList.remove(index);
                                WishlistFragment.wishlistAdapter.notifyDataSetChanged();
                            }
                            ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST=false;
                            Toast.makeText(context,"Xóa thành công!",Toast.LENGTH_SHORT).show();
                        } else {
                            if(ProductDetailsActivity.addToWishListBtn != null) {
                                ProductDetailsActivity.addToWishListBtn.setSupportImageTintList(context.getResources().getColorStateList(R.color.colorPrimary));
                            }
                            wishlist.add(index,removedProductId);
                            String error=task.getException().getMessage();
                            Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                        }
                        ProductDetailsActivity.running_wishlist_querry=false;
                    }
                });

    }

    public static void loadCartList(final Context context, final Dialog dialog,final boolean loadProductData,final TextView badgeCount,final TextView cartTotalAmount){

        cartList.clear();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid())
                .collection("USER_DATA").document("CART").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            for(long x=0;x<(long)task.getResult().get("list_size");x++){
                                cartList.add(task.getResult().get("product_ID_"+x).toString());

                                if(DBqueries.cartList.contains(ProductDetailsActivity.productID)){
                                    ProductDetailsActivity.ALREADY_ADDED_TO_CART=true;
                                }else {
                                    ProductDetailsActivity.ALREADY_ADDED_TO_CART=false;
                                }

                                if(loadProductData) {
                                    cartItemModelList.clear();
                                    final String productId=task.getResult().get("product_ID_" + x).toString();
                                    firebaseFirestore.collection("PRODUCTS").document(productId).get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        final DocumentSnapshot documentSnapshot=task.getResult();
                                                        firebaseFirestore.collection("PRODUCTS").document(productId).collection("QUANTITY").orderBy("time", Query.Direction.ASCENDING).get()
                                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                        if(task.isSuccessful()){

                                                                            int index=0;
                                                                            if(cartList.size()>=2){
                                                                                index=cartList.size()-2;
                                                                            }

                                                                            if(task.getResult().getDocuments().size()< (long)documentSnapshot.get("stock_quantity")){
                                                                                cartItemModelList.add(index,new CartItemModel(CartItemModel.CART_ITEM
                                                                                        ,productId
                                                                                        ,documentSnapshot.get("product_image_1").toString()
                                                                                        , (long) documentSnapshot.get("free_coupens")
                                                                                        ,(long)1
//                                                                                        ,(long)documentSnapshot.get("offers_applied")
                                                                                        ,(long)0
                                                                                        , documentSnapshot.get("product_title").toString()
                                                                                        , documentSnapshot.get("product_price").toString()
                                                                                        , documentSnapshot.get("cutted_price").toString()
                                                                                        ,true
                                                                                        ,(long)documentSnapshot.get("max_quantity")
                                                                                        ,(long)documentSnapshot.get("stock_quantity")
                                                                                        ,(boolean)documentSnapshot.get("COD")
                                                                                ));
                                                                            }else {
                                                                                cartItemModelList.add(index,new CartItemModel(CartItemModel.CART_ITEM
                                                                                        ,productId
                                                                                        ,documentSnapshot.get("product_image_1").toString()
                                                                                        , (long) documentSnapshot.get("free_coupens")
                                                                                        ,(long)1
                                                                                        //,(long)documentSnapshot.get("offers_applied")
                                                                                        ,(long)0
                                                                                        , documentSnapshot.get("product_title").toString()
                                                                                        , documentSnapshot.get("product_price").toString()
                                                                                        , documentSnapshot.get("cutted_price").toString()
                                                                                        ,false
                                                                                        ,(long)documentSnapshot.get("max_quantity")
                                                                                        ,(long)documentSnapshot.get("stock_quantity")
                                                                                        ,(boolean)documentSnapshot.get("COD")
                                                                                ));
                                                                            }
                                                                            if(cartList.size() == 1){
                                                                                cartItemModelList.add(new CartItemModel(CartItemModel.TOTAL_AMOUNT));
                                                                                LinearLayout parent=(LinearLayout)cartTotalAmount.getParent().getParent();
                                                                                parent.setVisibility(View.VISIBLE);
                                                                            }
                                                                            if(cartList.size() == 0) {
                                                                                cartItemModelList.clear();
                                                                            }
                                                                            CartFragment.cartAdapter.notifyDataSetChanged();
                                                                        }else {
                                                                            String error=task.getException().getMessage();
                                                                            Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });
                                                    } else {
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                            }
                            if(cartList.size() != 0){
                                badgeCount.setVisibility(View.VISIBLE);
                            }
                            else {
                                badgeCount.setVisibility(View.INVISIBLE);
                            }
                            if(DBqueries.cartList.size()<99) {
                                badgeCount.setText(String.valueOf(DBqueries.cartList.size()));
                            }else {
                                badgeCount.setText("99");
                            }
                        }
                        else {
                            String error=task.getException().getMessage();
                            Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
    }

    public static void loadRatingList(final Context context){
        if(!ProductDetailsActivity.running_rating_querry) {
            ProductDetailsActivity.running_rating_querry=true;
            RatedIds.clear();
            Rating.clear();
            firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("RATINGS")
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        List<String> orderProductIds=new ArrayList<>();
                        for (int x = 0; x < myOrderItemModelList.size(); x++) {
                            orderProductIds.add(myOrderItemModelList.get(x).getProductId());
                        }

                        for (long x = 0; x < (long) task.getResult().get("list_size"); x++) {
                            RatedIds.add(task.getResult().get("product_ID_" + x).toString());
                            Rating.add((long) task.getResult().get("rating_" + x));
                            if (task.getResult().get("product_ID_" + x).toString().equals(ProductDetailsActivity.productID)) {
                                ProductDetailsActivity.initialRating = Integer.parseInt(String.valueOf((long) task.getResult().get("rating_" + x))) - 1;
                                if(ProductDetailsActivity.rateNowContainer != null) {
                                    ProductDetailsActivity.setRating(ProductDetailsActivity.initialRating);
                                }
                            }

                            if(orderProductIds.contains(task.getResult().get("product_ID_" + x).toString())){
                                myOrderItemModelList.get(orderProductIds.indexOf(task.getResult().get("product_ID_" + x).toString())).setRating(Integer.parseInt(String.valueOf((long) task.getResult().get("rating_" + x))) - 1);
                            }
                        }
                        if(OrderFragment.myOrderAdapter != null){
                            OrderFragment.myOrderAdapter.notifyDataSetChanged();
                        }
                    } else {
                        String error = task.getException().getMessage();
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                    }
                    ProductDetailsActivity.running_rating_querry=false;
                }
            });
        }
    }

    public static void loadOrders(final Context context, @Nullable final MyOrderAdapter myOrderAdapter, final Dialog loadingDialog){
        myOrderItemModelList.clear();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid())
                .collection("USER_ORDERS").orderBy("time", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(!task.getResult().isEmpty()) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                    firebaseFirestore.collection("ORDERS").document(documentSnapshot.get("order_id").toString())
                                            .collection("ORDER_ITEMS").get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        //Toasty.success(context,"order mil gya",Toasty.LENGTH_SHORT,true).show();
                                                        for (DocumentSnapshot orderItems : task.getResult().getDocuments()) {

                                                            MyOrderItemModel myOrderItemModel = new MyOrderItemModel(
                                                                    orderItems.getString("Product Id")
                                                                    , orderItems.getString("Order Status")
                                                                    , orderItems.getString("Address")
                                                                    , orderItems.getString("Coupan Id")
                                                                    , orderItems.getString("Product Price")
                                                                    , orderItems.getString("Cutted Price")
                                                                    , orderItems.getString("Discounted Price")
                                                                    , (Date) orderItems.get("Ordered Date")
                                                                    , (Date) orderItems.get("Packed Date")
                                                                    , (Date) orderItems.get("Shipped Date")
                                                                    , (Date) orderItems.get("Delivered Date")
                                                                    , (Date) orderItems.get("Cancelled Date")
                                                                    , orderItems.getLong("Free Coupens")
                                                                    , orderItems.getLong("Product quantity")
                                                                    , orderItems.getString("FullName")
                                                                    , orderItems.getString("ORDER ID")
                                                                    , orderItems.getString("Payment Method")
                                                                    , orderItems.getString("Pincode")
                                                                    , orderItems.getString("User Id")
                                                                    , orderItems.getString("Product Title")
                                                                    , orderItems.getString("Product Image")
                                                                    , orderItems.getString("Delivery Price")
                                                                    , (boolean) orderItems.get("Cancellation requested")

                                                            );
                                                            myOrderItemModelList.add(myOrderItemModel);
                                                        }
                                                        loadRatingList(context);
                                                        if (myOrderAdapter != null) {
                                                            myOrderAdapter.notifyDataSetChanged();
                                                        }
                                                    } else {
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                                    }
                                                    loadingDialog.dismiss();
                                                }
                                            });
                                }
                            } else {
                                loadingDialog.dismiss();
                                String error = task.getException().getMessage();
                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            loadingDialog.dismiss();
                        }
                    }
                });

    }

    public static void removeFromCart(final int index, final Context context, final TextView cartTotalAmount) {
        final String removedProductId=cartList.get(index);
        cartList.remove(index);

        Map<String, Object> updateCart = new HashMap<>();

        for (int x = 0; x < cartList.size(); x++) {
            updateCart.put("product_ID_" + x, cartList.get(x));
        }
        updateCart.put("list_size", (long) cartList.size());

        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid())
                .collection("USER_DATA").document("CART").set(updateCart)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if(cartItemModelList.size() != 0){
                                cartItemModelList.remove(index);
                                CartFragment.cartAdapter.notifyDataSetChanged();
                            }
                            if(cartList.size() == 0) {
                                LinearLayout parent=(LinearLayout)cartTotalAmount.getParent().getParent();
                                parent.setVisibility(View.GONE);
                                cartItemModelList.clear();
                            }
                            Toast.makeText(context,"Xóa thành công!",Toast.LENGTH_SHORT).show();
                        } else {
                            cartList.add(index,removedProductId);
                            String error=task.getException().getMessage();
                            Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                        }
                        ProductDetailsActivity.running_cart_querry=false;
                    }
                });

    }

    public static void checkNotifications(boolean remove,@Nullable final TextView notifycount){

        if(remove){
            registration.remove();
        }else {
            registration=firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("NOTIFICATIONS")
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                            if(documentSnapshot != null && documentSnapshot.exists()) {
                                notificationModelList.clear();
                                int unread=0;
                                for (long x = 0; x < (long) documentSnapshot.get("list_size"); x++) {
                                    notificationModelList.add(0,new NotificationModel(
                                            documentSnapshot.getString("image_"+x)
                                            ,documentSnapshot.getString("body_"+x)
                                            ,documentSnapshot.getBoolean("readed_"+x)

                                    ));
                                    if(!documentSnapshot.getBoolean("readed_"+x)){
                                        unread++;
                                        if(notifycount != null){
                                            if(unread > 0) {
                                                notifycount.setVisibility(View.VISIBLE);
                                                if (unread < 99) {
                                                    notifycount.setText(String.valueOf(unread));
                                                } else {
                                                    notifycount.setText("99");
                                                }
                                            }else {
                                                notifycount.setVisibility(View.INVISIBLE);
                                            }
                                        }
                                    }
                                }
                                if(NotificationActivity.adapter != null){
                                    NotificationActivity.adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }
    }

    public static void loadAddresses(final Context context, final Dialog loadingDialog, final boolean gotoDeliveryActivity){
        addressesModelList.clear();

        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("ADDRESSES").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            if((long)task.getResult().get("list_size") == 0){
                                context.startActivity(new Intent(context, AddAddressActivity.class).putExtra("INTENT","deliveryIntent"));
                            }else {
                                for(long x=1;x<=(long)task.getResult().get("list_size");x++){
                                    addressesModelList.add(new AddressesModel(
                                            task.getResult().get("city_"+x).toString()
                                            ,task.getResult().get("locality_"+x).toString()
                                            ,task.getResult().get("flat_no_"+x).toString()
                                        //  ,task.getResult().get("pincode_"+x).toString()
                                            ,task.getResult().get("landmark_"+x).toString()
                                            ,task.getResult().get("name_"+x).toString()
                                            ,task.getResult().get("mobile_no_"+x).toString()
//                                          ,task.getResult().get("alternate_mobile_no_"+x).toString()
//                                          ,task.getResult().get("state_"+x).toString()
                                            ,(boolean)task.getResult().get("selected_"+x)
                                    ));
                                    if((boolean)task.getResult().get("selected_"+x)){
                                        selectedAddress=Integer.parseInt(String.valueOf(x-1));
                                    }
                                }
                                if (gotoDeliveryActivity) {
                                    context.startActivity(new Intent(context, DeliveryActivity.class));
                                }
                            }
                        }else {
                            String error=task.getException().getMessage();
                            Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                        }
                        loadingDialog.dismiss();
                    }
                });
    }


    public static void clearData(){
        categoryModelList.clear();
        lists.clear();
        loadedCategoriesNames.clear();
        wishlist.clear();
        wishlistModelList.clear();
        cartList.clear();
        cartItemModelList.clear();
        RatedIds.clear();
        Rating.clear();
        addressesModelList.clear();
        rewardModelList.clear();
        myOrderItemModelList.clear();
    }
}