package com.example.e_commerce;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.example.lib.Model.CheckOutModel;
import com.example.lib.Model.HomePageModel;
import com.example.lib.Model.HorizontalProductScrollModel;
import com.example.lib.Model.MyOrderItemModel;
import com.example.lib.Model.RewardModel;
import com.example.lib.Model.SliderModel;
import com.example.lib.Model.UserModel;
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
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DBqueries {
    public static String email, fullname, profile;

    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<CategoryModel> categoryModelList = new ArrayList<>();
    public static List<CategoryModel> categoryModelList1 = new ArrayList<>();

    public static List<HomePageModel> homePageModelsList = new ArrayList<>();
    public static List<String> wishlist = new ArrayList<>();
    public static List<WishlistModel> wishlistModelList = new ArrayList<>();
    public static List<List<HomePageModel>> lists = new ArrayList<>();
    public static List<List<HomePageModel>> lists2 = new ArrayList<>();

    public static List<String> loadedCategoriesName = new ArrayList<>();
    public static List<String> loadedCategoriesNames = new ArrayList<>();

    public static CheckOutModel checkOutModel;
    public static UserModel userInfomation;
    public static String urlThanhToan = "";
    public static List<AddressesModel> addressesModelList = new ArrayList<>();
    public static List<NotificationModel> notificationModelList = new ArrayList<>();
    public static List<String> cartList = new ArrayList<>();
    public static List<CartItemModel> cartItemModelList = new ArrayList<>();
    public static List<RewardModel> rewardModelList = new ArrayList<>();
    private static ListenerRegistration registration;
    public static List<Long> Rating = new ArrayList<>();
    public static List<String> RatedIds = new ArrayList<>();
    public static List<MyOrderItemModel> myOrderItemModelList = new ArrayList<>();
    public static int selectedAddress = -1;
    public static int tong = 0;
    private static OkHttpClient client = new OkHttpClient();

    public static void loadCategories(final Context context, final CategoryAdapter categoryAdapter) {
        categoryModelList.add(new CategoryModel("-1", "https://cdn-icons-png.flaticon.com/512/2250/2250401.png", "Trang ch???", 0));

        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String url = Config.IP_ADDRESS + "/api/category/all_linhvuc";
                Request request = new Request.Builder()
                        .url(url)
                        .header("Accept-Encoding", "identity")
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code: " + response);
                    } else {
                        String jsonData = response.body().string();
                        JSONArray json = new JSONArray(jsonData);
                        System.out.println(jsonData);
                        for (int i = 0; i < json.length(); i++) {
                            JSONObject b = new JSONObject(json.get(i).toString());
                            String name = b.getString("TENLINHVUC");
                            String id = b.getString("MALINHVUC");
                            categoryModelList.add(new CategoryModel(id, "https://cdn-icons-png.flaticon.com/512/2250/2250401.png", name, 0));
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return "categoryLoad";
            }

            protected void onPostExecute(String result) {
                categoryAdapter.notifyDataSetChanged();
            }

            ;
        };
        task.execute("categoryLoad");

    }

    public static void loadCategories1(final Context context, final CategoryAdapter categoryAdapter, String categoriesName) {
        String id_Category = "-1"; //????y l?? ID C???a Category
        categoryModelList1.add(new CategoryModel(id_Category, "https://cdn-icons-png.flaticon.com/512/2250/2250401.png", categoriesName, 1));
        for (CategoryModel category : categoryModelList) {
            if (category.getCategoryName().equals(categoriesName)) {
                id_Category = category.getCategoryID();
                break;
            }
        }
        System.out.println(id_Category + "_ID_Category");
        String finalId_Category = id_Category;
        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String url = Config.IP_ADDRESS + "/api/category/loaihang_LinhVuc/" + finalId_Category;
                Request request = new Request.Builder()
                        .url(url)
                        .header("Accept-Encoding", "identity")
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code: " + response);
                    } else {
                        String jsonData = response.body().string();
                        JSONArray json = new JSONArray(jsonData);
                        System.out.println(jsonData);
                        for (int i = 0; i < json.length(); i++) {
                            JSONObject b = new JSONObject(json.get(i).toString());
                            String name = b.getString("TENLOAI");
                            String id = b.getString("MALOAI");
                            categoryModelList1.add(new CategoryModel(id, "https://cdn-icons-png.flaticon.com/512/2250/2250401.png", name, 1));
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return "categoryLoad1";
            }

            protected void onPostExecute(String result) {
                categoryAdapter.notifyDataSetChanged();
            }

            ;
        };
        task.execute("categoryLoad1");
    }

    public static void setFragmentData(final Context context, final HomePageAdapter adapter, final int position, String categoriesName) {
        //Banner
        List<SliderModel> sliderModelList = new ArrayList<>();
        sliderModelList.add(new SliderModel("https://www.kienvuongchemistry.tk/img/icon/first_banner.png"));        // lay data cua banner ve gan vao list
        sliderModelList.add(new SliderModel("https://www.kienvuongchemistry.tk/img/icon/banner2.png"));        // lay data cua banner ve gan vao list
        sliderModelList.add(new SliderModel("https://www.kienvuongchemistry.tk/img/icon/banner3.png"));
        sliderModelList.add(new SliderModel("https://www.kienvuongchemistry.tk/img/icon/banner4.png"));
        sliderModelList.add(new SliderModel("https://www.kienvuongchemistry.tk/img/icon/banner5.png"));
        sliderModelList.add(new SliderModel("https://www.kienvuongchemistry.tk/img/icon/banner6.png"));
        sliderModelList.add(new SliderModel("https://www.kienvuongchemistry.tk/img/icon/banner7.png"));
        lists.get(position).add(new HomePageModel(0, sliderModelList));
        //S???n Ph???m Hot
        //????y l?? h??m c???a category ?????u ti??n
        List<WishlistModel> viewAllProduct = new ArrayList<>();
        List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
        AsyncTask<String, Void, String> task1 = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String url = Config.IP_ADDRESS + "/api/product/all";
                Request request = new Request.Builder()
                        .url(url)
                        .header("Accept-Encoding", "identity")
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code: " + response);
                    } else {
                        String jsonData = response.body().string();
                        JSONArray json = new JSONArray(jsonData);
                        for (int i = 0; i < json.length(); i++) {
                            JSONObject b = new JSONObject(json.get(i).toString());
                            String name = b.getString("TENHANGHOA");
                            String id = b.getString("MAHANGHOA");
                            NumberFormat formatter = new DecimalFormat("#,###");
                            String gia = formatter.format(Integer.parseInt(b.getString("GIA")));
                            String img = Config.IP_IMG_ADDRESS + b.getString("IMGAGESPATH");
                            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(
                                    id
                                    , img
                                    , name
                                    , "VN??"
                                    , gia));                    // lay data cua banner ve gan vao list
                            viewAllProduct.add(new WishlistModel(
                                    id
                                    , img
                                    , name
                                    , 1000
                                    , ""
                                    , 0
                                    , gia
                                    , "1000"
                                    , false
                                    , true));
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return "Load_San_Pham_Hot";
            }

            protected void onPostExecute(String result) {
                lists.get(position).add(new HomePageModel(1, "S???n ph???m Hot", "#FFFFFF", horizontalProductScrollModelList, viewAllProduct));
                adapter.notifyDataSetChanged();             // NH??? SET ADAPTER CHO TH???NG N??O PH???I XEM K??
            }

            ;
        };
        task1.execute("Load_San_Pham_Hot");
        //S???n Ph???m
        List<HorizontalProductScrollModel> gridLayoutModelList = new ArrayList<>();
        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String url = Config.IP_ADDRESS + "/api/product/all";
                Request request = new Request.Builder()
                        .url(url)
                        .header("Accept-Encoding", "identity")
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code: " + response);
                    } else {
                        String jsonData = response.body().string();
                        JSONArray json = new JSONArray(jsonData);
                        for (int i = 0; i < json.length(); i++) {
                            JSONObject b = new JSONObject(json.get(i).toString());
                            String name = b.getString("TENHANGHOA");
                            String id = b.getString("MAHANGHOA");
                            NumberFormat formatter = new DecimalFormat("#,###");
                            String gia = formatter.format(Integer.parseInt(b.getString("GIA")));
                            String img = Config.IP_IMG_ADDRESS + b.getString("IMGAGESPATH");
                            gridLayoutModelList.add(new HorizontalProductScrollModel(
                                    id
                                    , img
                                    , (name.length() > 14) ? name.substring(0, 14) + "..." : name
                                    , "VN??"
                                    , gia
                            ));
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return "Load_San_Pham";
            }

            protected void onPostExecute(String result) {
                lists.get(position).add(new HomePageModel(2, "S???n Ph???m", "#FFFFFF", gridLayoutModelList));
                adapter.notifyDataSetChanged();             // NH??? SET ADAPTER CHO TH???NG N??O PH???I XEM K??
            }

            ;
        };
        task.execute("Load_San_Pham");


    }

    public static void setFragmentDataSpLienQuan(final Context context, final HomePageAdapter adapter, final int position, String categoriesName, String maloai) {
        //S???n Ph???m Hot
        //????y l?? h??m c???a category ?????u ti??n
        List<WishlistModel> viewAllProduct = new ArrayList<>();
        List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
        AsyncTask<String, Void, String> task1 = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String url = Config.IP_ADDRESS + "/api/product/categoryMaLoai/" + maloai;
                Request request = new Request.Builder()
                        .url(url)
                        .header("Accept-Encoding", "identity")
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code: " + response);
                    } else {
                        String jsonData = response.body().string();
                        JSONArray json = new JSONArray(jsonData);
                        for (int i = 0; i < json.length(); i++) {
                            JSONObject b = new JSONObject(json.get(i).toString());
                            String name = b.getString("TENHANGHOA");
                            String id = b.getString("MAHANGHOA");
                            NumberFormat formatter = new DecimalFormat("#,###");
                            String gia = formatter.format(Integer.parseInt(b.getString("GIA")));
                            String img = Config.IP_IMG_ADDRESS + b.getString("IMGAGESPATH");
                            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(
                                    id
                                    , img
                                    , name
                                    , "VN??"
                                    , gia));                    // lay data cua banner ve gan vao list
                            viewAllProduct.add(new WishlistModel(
                                    id
                                    , img
                                    , name
                                    , 1000
                                    , ""
                                    , 0
                                    , gia
                                    , "1000"
                                    , false
                                    , true));
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return "Load_San_Pham_Hot";
            }

            protected void onPostExecute(String result) {
                lists2.get(position).add(new HomePageModel(1, "S???n ph???m li??n quan", "#FFFFFF", horizontalProductScrollModelList, viewAllProduct));
                adapter.notifyDataSetChanged();             // NH??? SET ADAPTER CHO TH???NG N??O PH???I XEM K??
            }

            ;
        };
        task1.execute("Load_San_Pham_Hot");
    }

    //M???i l???n b???m v?? category s??? load l???i h??m n??y
    public static void setCategoryData(final Context context, final HomePageAdapter adapter, final int position, String categoriesName,Dialog dialog) {
        String id_Category = "-1"; //????y l?? ID C???a Category
        int categoryType = -1;
        boolean statusCheckType = false;
        for (CategoryModel category : categoryModelList) {
            if (category.getCategoryName().equals(categoriesName) && statusCheckType == false) {
                id_Category = category.getCategoryID();
                categoryType = category.getCategoryType();
                statusCheckType = true;
                break;
            }
        }
        for (CategoryModel category : categoryModelList1) {
            if (category.getCategoryName().equals(categoriesName) && statusCheckType == false) {
                id_Category = category.getCategoryID();
                categoryType = category.getCategoryType();
                statusCheckType = true;
                break;
            }
        }


        //S???n Ph???m Hot
        //????y l?? h??m c???a category ?????u ti??n
        List<WishlistModel> viewAllProduct = new ArrayList<>();
        List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
        String finalId_Category = id_Category;
        int finalCategoryType = categoryType;
        dialog.show();

        AsyncTask<String, Void, String> task1 = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String url = "";
                if (finalCategoryType != 0) {
                    url = Config.IP_ADDRESS + "/api/product/category/" + finalId_Category;
                } else {
                    url = Config.IP_ADDRESS + "/api/product/categoryLinhvuc/" + finalId_Category;
                }
                Request request = new Request.Builder()
                        .url(url)
                        .header("Accept-Encoding", "identity")
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code: " + response);
                    } else {
                        String jsonData = response.body().string();
                        JSONArray json = new JSONArray(jsonData);
                        for (int i = 0; i < json.length(); i++) {
                            JSONObject b = new JSONObject(json.get(i).toString());
                            String name = b.getString("TENHANGHOA");
                            String id = b.getString("MAHANGHOA");
                            NumberFormat formatter = new DecimalFormat("#,###");
                            String gia = formatter.format(Integer.parseInt(b.getString("GIA")));
                            String img = Config.IP_IMG_ADDRESS + b.getString("IMGAGESPATH");
                            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(
                                    id
                                    , img
                                    , name
                                    , "VN??"
                                    , gia));                    // lay data cua banner ve gan vao list
                            viewAllProduct.add(new WishlistModel(
                                    id
                                    , img
                                    , name
                                    , 1000
                                    , "3"
                                    , 5
                                    , gia
                                    , "1000"
                                    , false
                                    , true));
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return "Load_San_Pham_Hot";
            }

            protected void onPostExecute(String result) {
                lists.get(position).add(new HomePageModel(1, "S???n ph???m", "#FFFFFF", horizontalProductScrollModelList, viewAllProduct));
                if (horizontalProductScrollModelList.size() >= 4) {
                    List<HorizontalProductScrollModel> gridLayoutModelList = new ArrayList<>();
                    AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
                        @Override
                        protected String doInBackground(String... params) {
                            String url = "";
                            if (finalCategoryType != 0) {
                                url = Config.IP_ADDRESS + "/api/product/category/" + finalId_Category;
                            } else {
                                url = Config.IP_ADDRESS + "/api/product/categoryLinhvuc/" + finalId_Category;
                            }
                            Request request = new Request.Builder()
                                    .url(url)
                                    .header("Accept-Encoding", "identity")
                                    .build();

                            try (Response response = client.newCall(request).execute()) {
                                if (!response.isSuccessful()) {
                                    throw new IOException("Unexpected code: " + response);
                                } else {
                                    String jsonData = response.body().string();
                                    JSONArray json = new JSONArray(jsonData);
                                    for (int i = 0; i < json.length(); i++) {
                                        JSONObject b = new JSONObject(json.get(i).toString());
                                        String name = b.getString("TENHANGHOA");
                                        String id = b.getString("MAHANGHOA");
                                        NumberFormat formatter = new DecimalFormat("#,###");
                                        String gia = formatter.format(Integer.parseInt(b.getString("GIA")));
                                        String img = Config.IP_IMG_ADDRESS + b.getString("IMGAGESPATH");
                                        gridLayoutModelList.add(new HorizontalProductScrollModel(
                                                id
                                                , img
                                                , (name.length() > 14) ? name.substring(0, 14) + "..." : name
                                                , "VN??"
                                                , gia
                                        ));
                                    }

                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            return "Load_San_Pham";
                        }

                        protected void onPostExecute(String result) {
                            lists.get(position).add(new HomePageModel(2, "S???n Ph???m", "#FFFFFF", gridLayoutModelList));
                            adapter.notifyDataSetChanged();             // NH??? SET ADAPTER CHO TH???NG N??O PH???I XEM K??
                            dialog.dismiss();

                        }

                        ;
                    };
                    task.execute("Load_San_Pham");
                }
                dialog.dismiss();
                adapter.notifyDataSetChanged();             // NH??? SET ADAPTER CHO TH???NG N??O PH???I XEM K??
            }

            ;
        };
        task1.execute("Load_San_Pham_Hot");
        //S???n Ph???m


    }

    public static void loadRewards(final Context context, final Dialog loadingDialog, final boolean onRewardFragment) {

        rewardModelList.clear();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            final Date lastseendate = task.getResult().getDate("Last seen");

                            firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_REWARDS")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {

                                                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                                    if (documentSnapshot.get("type").toString().equals("Discount") && lastseendate.before(documentSnapshot.getDate("validity"))) {  //&& lastseendate.before(documentSnapshot.getDate("validity"))
                                                        rewardModelList.add(new RewardModel(
                                                                documentSnapshot.getId()
                                                                , documentSnapshot.get("type").toString()
                                                                , documentSnapshot.get("upper_limit").toString()
                                                                , documentSnapshot.get("lower_limit").toString()
                                                                , documentSnapshot.get("percentage").toString()
                                                                , documentSnapshot.get("body").toString()
                                                                , (Timestamp) documentSnapshot.get("validity")
                                                                , (boolean) documentSnapshot.get("alreadyUsed")
                                                        ));
                                                    } else if (documentSnapshot.get("type").toString().equals("Flat VND OFF") && lastseendate.before(documentSnapshot.getDate("validity"))) {//&& lastseendate.before(documentSnapshot.getDate("validity"))
                                                        rewardModelList.add(new RewardModel(
                                                                documentSnapshot.getId()
                                                                , documentSnapshot.get("type").toString()
                                                                , documentSnapshot.get("upper_limit").toString()
                                                                , documentSnapshot.get("lower_limit").toString()
                                                                , documentSnapshot.get("amount").toString()
                                                                , documentSnapshot.get("body").toString()
                                                                , (Timestamp) documentSnapshot.get("validity")
                                                                , (boolean) documentSnapshot.get("alreadyUsed")
                                                        ));
                                                    }
                                                }
                                                if (onRewardFragment) {
                                                    CouponFragment.adapter.notifyDataSetChanged();
                                                }
                                            } else {
                                                String error = task.getException().getMessage();
                                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
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
        final String removedProductId = wishlist.get(index);
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
                            if (wishlistModelList.size() != 0) {
                                wishlistModelList.remove(index);
                                WishlistFragment.wishlistAdapter.notifyDataSetChanged();
                            }
                            ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = false;
                            Toast.makeText(context, "X??a th??nh c??ng!", Toast.LENGTH_SHORT).show();
                        } else {
                            if (ProductDetailsActivity.addToWishListBtn != null) {
                                ProductDetailsActivity.addToWishListBtn.setSupportImageTintList(context.getResources().getColorStateList(R.color.colorPrimary));
                            }
                            wishlist.add(index, removedProductId);
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                        ProductDetailsActivity.running_wishlist_querry = false;
                    }
                });

    }

    public static void loadCartList(final Context context, final Dialog dialog, final boolean loadProductData, final TextView badgeCount, final TextView cartTotalAmount) {

        cartList.clear();
        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String url = Config.IP_ADDRESS + "/api/giohang/giohang_select/" + DBqueries.email;
                Request request = new Request.Builder()
                        .url(url)
                        .header("Accept-Encoding", "identity")
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code: " + response);
                    } else {
                        String jsonData = response.body().string();

                        JSONArray json = new JSONArray(jsonData);
                        System.out.println(json);
                        for (int i = 0; i < json.length(); i++) {
                            JSONObject b = new JSONObject(json.get(i).toString());
                            String img = Config.IP_IMG_ADDRESS + b.getString("HINHANH");
                            cartItemModelList.add(new CartItemModel((long) Integer.parseInt(b.getString("SoLuong")), b.getString("MaHangHoa"), img, b.getString("tensp"), b.getString("gia")));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return "cartLoad";
            }

            protected void onPostExecute(String result) {
                if (cartItemModelList.size() < 99) {
                    badgeCount.setText(cartItemModelList.size()+"");
                } else {
                    badgeCount.setText("99");
                }
            }

            ;
        };
        task.execute("cartLoad");
    }

    public static void loadRatingList(final Context context) {
        if (!ProductDetailsActivity.running_rating_querry) {
            ProductDetailsActivity.running_rating_querry = true;
            RatedIds.clear();
            Rating.clear();
            firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("RATINGS")
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        List<String> orderProductIds = new ArrayList<>();
                        for (int x = 0; x < myOrderItemModelList.size(); x++) {
                            orderProductIds.add(myOrderItemModelList.get(x).getProductId());
                        }

                        for (long x = 0; x < (long) task.getResult().get("list_size"); x++) {
                            RatedIds.add(task.getResult().get("product_ID_" + x).toString());
                            Rating.add((long) task.getResult().get("rating_" + x));
                            if (task.getResult().get("product_ID_" + x).toString().equals(ProductDetailsActivity.productID)) {
                                ProductDetailsActivity.initialRating = Integer.parseInt(String.valueOf((long) task.getResult().get("rating_" + x))) - 1;
                                if (ProductDetailsActivity.rateNowContainer != null) {
                                    ProductDetailsActivity.setRating(ProductDetailsActivity.initialRating);
                                }
                            }

                            if (orderProductIds.contains(task.getResult().get("product_ID_" + x).toString())) {
                                myOrderItemModelList.get(orderProductIds.indexOf(task.getResult().get("product_ID_" + x).toString())).setRating(Integer.parseInt(String.valueOf((long) task.getResult().get("rating_" + x))) - 1);
                            }
                        }
                        if (OrderFragment.myOrderAdapter != null) {
                            OrderFragment.myOrderAdapter.notifyDataSetChanged();
                        }
                    } else {
                        String error = task.getException().getMessage();
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                    }
                    ProductDetailsActivity.running_rating_querry = false;
                }
            });
        }
    }

    public static void loadOrders(final Context context, @Nullable final MyOrderAdapter myOrderAdapter, final Dialog loadingDialog) {
        myOrderItemModelList.clear();
        MyOrderItemModel myOrderItemModel = new MyOrderItemModel(
                "1"
                , ""
                , ""
                , ""
                , ""
                , ""
                , ""
                , new Date("11/12/2022")
                , new Date("11/12/2022")
                , new Date("11/12/2022")
                , new Date("11/12/2022")
                , new Date("11/12/2022")
                , (long) 10000
                , (long) 10000
                , ""
                , ""
                , ""
                , ""
                , ""
                , ""
                , ""
                , ""
                , false

        );
        myOrderItemModelList.add(myOrderItemModel);
        if (myOrderAdapter != null) {
            myOrderAdapter.notifyDataSetChanged();
        }

    }

    public static void removeFromCart(final int index, final Context context, final TextView cartTotalAmount) {
        final String removedProductId = cartList.get(index);
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
                            if (cartItemModelList.size() != 0) {
                                cartItemModelList.remove(index);
                                CartFragment.cartAdapter.notifyDataSetChanged();
                            }
                            if (cartList.size() == 0) {
                                LinearLayout parent = (LinearLayout) cartTotalAmount.getParent().getParent();
                                parent.setVisibility(View.GONE);
                                cartItemModelList.clear();
                            }
                            Toast.makeText(context, "X??a th??nh c??ng!", Toast.LENGTH_SHORT).show();
                        } else {
                            cartList.add(index, removedProductId);
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                        ProductDetailsActivity.running_cart_querry = false;
                    }
                });

    }

    public static void checkNotifications(boolean remove, @Nullable final TextView notifycount) {

        if (remove) {
            registration.remove();
        } else {
            registration = firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("NOTIFICATIONS")
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                            if (documentSnapshot != null && documentSnapshot.exists()) {
                                notificationModelList.clear();
                                int unread = 0;
                                for (long x = 0; x < (long) documentSnapshot.get("list_size"); x++) {
                                    notificationModelList.add(0, new NotificationModel(
                                            documentSnapshot.getString("image_" + x)
                                            , documentSnapshot.getString("body_" + x)
                                            , documentSnapshot.getBoolean("readed_" + x)

                                    ));
                                    if (!documentSnapshot.getBoolean("readed_" + x)) {
                                        unread++;
                                        if (notifycount != null) {
                                            if (unread > 0) {
                                                notifycount.setVisibility(View.VISIBLE);
                                                if (unread < 99) {
                                                    notifycount.setText(String.valueOf(unread));
                                                } else {
                                                    notifycount.setText("99");
                                                }
                                            } else {
                                                notifycount.setVisibility(View.INVISIBLE);
                                            }
                                        }
                                    }
                                }
                                if (NotificationActivity.adapter != null) {
                                    NotificationActivity.adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }
    }

    public static void loadAddresses(final Context context, final Dialog loadingDialog, final boolean gotoDeliveryActivity) {
        addressesModelList.clear();

        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("ADDRESSES").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if ((long) task.getResult().get("list_size") == 0) {
                                context.startActivity(new Intent(context, AddAddressActivity.class).putExtra("INTENT", "deliveryIntent"));
                            } else {
                                for (long x = 1; x <= (long) task.getResult().get("list_size"); x++) {
                                    addressesModelList.add(new AddressesModel(
                                            task.getResult().get("city_" + x).toString()
                                            , task.getResult().get("locality_" + x).toString()
                                            , task.getResult().get("flat_no_" + x).toString()
                                            //  ,task.getResult().get("pincode_"+x).toString()
                                            , task.getResult().get("landmark_" + x).toString()
                                            , task.getResult().get("name_" + x).toString()
                                            , task.getResult().get("mobile_no_" + x).toString()
//                                          ,task.getResult().get("alternate_mobile_no_"+x).toString()
//                                          ,task.getResult().get("state_"+x).toString()
                                            , (boolean) task.getResult().get("selected_" + x)
                                    ));
                                    if ((boolean) task.getResult().get("selected_" + x)) {
                                        selectedAddress = Integer.parseInt(String.valueOf(x - 1));
                                    }
                                }
                                if (gotoDeliveryActivity) {
                                    context.startActivity(new Intent(context, DeliveryActivity.class));
                                }
                            }
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                        loadingDialog.dismiss();
                    }
                });
    }


    public static void clearData() {
        categoryModelList.clear();
        lists.clear();
        lists2.clear();
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