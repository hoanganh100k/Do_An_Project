package com.example.e_commerce;

import static com.example.e_commerce.DBqueries.lists2;
import static com.example.e_commerce.DBqueries.loadedCategoriesName;
import static com.example.e_commerce.DBqueries.setFragmentDataSpLienQuan;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.e_commerce.ui.cart.CartFragment;
import com.example.e_commerce.ui.home.HomePageAdapter;
import com.example.lib.Model.CommentModel;
import com.example.lib.Model.HomePageModel;
import com.example.lib.Model.WishlistModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProductDetailsActivity extends AppCompatActivity {

    public static boolean fromSearch = false;
    private ViewPager productImagesViewPager;
    private TextView productTitle;
    private TextView averageRatingMiniView;
    private TextView totalRatingMiniView;
    private TextView productPrice;
    private TextView cuttedPrice;
    private ImageView codIndicator;
    private TextView tvCodIndicator;
    private TabLayout viewpagerIndicator;
    private Button couponRedeemBtn, buyNowBtn;
    private TextView rewardTitle;
    private TextView rewardBody;
    private boolean inStock = false;
    public static boolean ALREADY_ADDED_TO_WISHLIST = false, ALREADY_ADDED_TO_CART = false, running_wishlist_querry = false, running_cart_querry = false, running_rating_querry = false;
    public static Activity productDetailsActivity;
    public static Boolean showCart = false;
    //MOTASANPHAM
    private ConstraintLayout productDetailsOnlyContainer;
    private TextView productOnlyDescriptionBody;
    private ConstraintLayout productDetailsTabsContainer;
    private ViewPager productDetailsViewPager;
    private TabLayout productDetailsTablayout;

    private List<ProductInformationModel> productInformationModelList = new ArrayList<>();
    private String productDescription;
    private String productOtherDetails;

    private TextView badgeCount;
    //MOTASANPHAM

    //CouponDialog
    private Long productPriceValue;
    private LinearLayout coupanRedemLayout;
    private static RecyclerView couponsRecyclerView;
    private static LinearLayout selectedCoupon;
    public static TextView coupenTitle;
    public static TextView coupenExpiryDate;
    public static TextView coupenBody;
    private Dialog loadingDialog;
    private Dialog signInDialog;
    private TextView coupanTitle, discountedPrice, originalPrice;
    //DANHGIA
    public static LinearLayout rateNowContainer;
    private TextView totalRatings, productAvgRating;
    private LinearLayout ratingsNoContainer;
    private TextView totalRatingsFigure;
    private LinearLayout ratingsProgressBarContainer;
    public static TextView averageRatings, productTotalRatings;
    public static int initialRating;

    //DANHGIA
    private LinearLayout addToCartBtn;
    public static boolean ALREADY_ADD_TO_WISHLIST = false;
    public static FloatingActionButton addToWishListBtn;
    public static String productID;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser currentUser;
    private DocumentSnapshot documentSnapshot;
    private RecyclerView homePageRecyclerView;
    private HomePageAdapter adapter;
    private ListView commentList;
    private CommentAdapter commentAdapter;
    private List<CommentModel> listComment = new ArrayList<CommentModel>();
    private Button nhanXetBtn;
    private EditText nhanXetText;
    private String imageUrl = "";
    private String GIA = "";
    private String TEN = "";
    private TextView tonHang;
    private TextView statusTextAddcart;
    private View viewCart;
    private LinearLayout v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lists2.clear();
        productImagesViewPager = findViewById(R.id.product_images_viewpager);
        viewpagerIndicator = findViewById(R.id.viewpager_indicator);

        addToCartBtn = findViewById(R.id.add_to_cart_btn);
        addToWishListBtn = findViewById(R.id.add_to_wishlist_btn);
        productDetailsViewPager = findViewById(R.id.product_detail_viewpager);
        productDetailsTablayout = findViewById(R.id.product_detail_tablayout);
        couponRedeemBtn = findViewById(R.id.coupan_redemption_btn);
        buyNowBtn = findViewById(R.id.buy_now_btn);
        productTitle = findViewById(R.id.product_title);
        coupanRedemLayout = findViewById(R.id.coupan_redemption_layout);
        //       averageRatingMiniView = findViewById(R.id.tv_product_rating_miniview);
//        totalRatingMiniView = findViewById(R.id.total_rating_miniview);
        productPrice = findViewById(R.id.product_price);
        cuttedPrice = findViewById(R.id.cutted_price);
        codIndicator = findViewById(R.id.cod_indicator_imageview);
        tvCodIndicator = findViewById(R.id.tv_cod_indicator);
        rewardTitle = findViewById(R.id.reward_title);
        rewardBody = findViewById(R.id.reward_body);
        productDetailsTabsContainer = findViewById(R.id.product_details_tabs_container);
        productDetailsOnlyContainer = findViewById(R.id.product_details_container);
        productOnlyDescriptionBody = findViewById(R.id.product_details_body);
        totalRatings = findViewById(R.id.total_ratings);
//        rateNowContainer = findViewById(R.id.ratings_number_container);
        totalRatingsFigure = findViewById(R.id.tv_total_ratings_figure);
        ratingsProgressBarContainer = findViewById(R.id.ratings_progressbar_container);
        averageRatings = findViewById(R.id.average_ratings);
        productTotalRatings = findViewById(R.id.total_rating_miniview);
        productAvgRating = findViewById(R.id.tv_product_rating_miniview);
        ratingsNoContainer = findViewById(R.id.ratings_number_container);
        commentList = findViewById(R.id.listViewComment);
        nhanXetBtn = findViewById(R.id.nhanxetbtn);
        nhanXetText = findViewById(R.id.nhanxetText);
        tonHang = findViewById(R.id.text_ton_hang);
        statusTextAddcart = findViewById(R.id.status_add_cart);
        initialRating = -1;
        v = findViewById(R.id.linearLayout3Add);

        homePageRecyclerView = findViewById(R.id.testing);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homePageRecyclerView.setLayoutManager(testingLayoutManager);
        viewCart = (View) findViewById(R.id.flagmentcart);
        viewCart.setVisibility(View.GONE);

////// coupan redemption dialog
        final Dialog checkCoupanPricedialog = new Dialog(ProductDetailsActivity.this);
        checkCoupanPricedialog.setCancelable(true);
        checkCoupanPricedialog.setContentView(R.layout.coupan_redeem_dialog);
        checkCoupanPricedialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView toogleRecyclerView = checkCoupanPricedialog.findViewById(R.id.toggle_recyclerview);
        couponsRecyclerView = checkCoupanPricedialog.findViewById(R.id.coupans_recyclerView);
        selectedCoupon = checkCoupanPricedialog.findViewById(R.id.selected_coupan);
        originalPrice = checkCoupanPricedialog.findViewById(R.id.original_price);
        discountedPrice = checkCoupanPricedialog.findViewById(R.id.discounted_price);
        coupanTitle = checkCoupanPricedialog.findViewById(R.id.coupan_title);
        coupenExpiryDate = checkCoupanPricedialog.findViewById(R.id.coupan_validity);
        coupenBody = checkCoupanPricedialog.findViewById(R.id.coupan_body);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProductDetailsActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        couponsRecyclerView.setLayoutManager(linearLayoutManager);

        toogleRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogRecyclerView();
            }
        });
////// coupan redemption dialog
        addToCartBtn.setVisibility(View.VISIBLE);
        buyNowBtn.setVisibility(View.VISIBLE);

        //Loangding dialog
        loadingDialog = new Dialog(ProductDetailsActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        v.setVisibility(View.GONE);

        firebaseFirestore = FirebaseFirestore.getInstance();

        commentAdapter = new CommentAdapter(getBaseContext(), listComment);
        commentList.setAdapter(commentAdapter);

        final List<String> productImages = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        productID = getIntent().getStringExtra("PRODUCT_ID");
        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
            private JSONObject c = new JSONObject();

            @Override
            protected String doInBackground(String... params) {
                String url = Config.IP_ADDRESS + "/api/product/get/" + productID;
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
                        JSONObject b = new JSONObject(json.get(0).toString());
                        c = b;

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return "categoryLoad";
            }

            protected void onPostExecute(String result) {
                try {
                    String img = Config.IP_IMG_ADDRESS + c.getString("IMGAGESPATH");
                    imageUrl = c.getString("IMGAGESPATH");
                    productImages.add(img);


                    ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
                    productImagesViewPager.setAdapter(productImagesAdapter); //hinhanhchitietsanppham
                    String _tonHang = c.getString("TINHTRANG");
                    if (!_tonHang.equals("0")) {
                        tonHang.setText("Hàng hiện có: " + _tonHang);
                        tonHang.setTextColor(Color.parseColor("#56FF33"));
                    } else {
                        statusTextAddcart.setText("Đã hết hàng");
                        tonHang.setText("Hàng hiện có: Hết Hàng");
                        tonHang.setTextColor(Color.parseColor("#FF0000"));
                        buyNowBtn.setEnabled(false);
                        buyNowBtn.setTextColor(Color.parseColor("#FF0000"));
                    }
                    productTitle.setText(c.getString("TENHANGHOA"));     //tensanpham
                    productAvgRating.setText("5");  //danhgia
                    productTotalRatings.setText("(" + 5000 + ") Đánh giá");
                    NumberFormat formatter = new DecimalFormat("#,###");
                    String gia = formatter.format(Integer.parseInt(c.getString("GIA")));
                    GIA = c.getString("GIA");
                    TEN = c.getString("TENHANGHOA");
                    productPrice.setText(gia + "VND");
                    productOnlyDescriptionBody.setText(c.getString("MOTA"));
                    String ChiTietSanPham = "Xuất xứ: " + c.getString("XUATXU") + "\n" + "Quy Cách: " + c.getString("QUYCACH") + "\n" + "Hạn sử dụng: " + c.getString("HANSUDUNG") + "\n" + "Ngày sản xuất: " + c.getString("NGAYSANXUAT") + "\n" + "Nhà cung cấp: " + c.getString("MANHACUNGCAP");
                    productDetailsViewPager.setAdapter(new ProductDetailsAdpater(getSupportFragmentManager(), productDetailsTablayout.getTabCount(), ChiTietSanPham, "", productInformationModelList));

                    String MaLoai = c.getString("MALOAI");
                    if (lists2.size() == 0) {
                        loadedCategoriesName.add("HOME");
                        lists2.add(new ArrayList<HomePageModel>());
                        adapter = new HomePageAdapter(lists2.get(0));
                        setFragmentDataSpLienQuan(getBaseContext(), adapter, 0, "HOME", MaLoai);
                    } else {
                        adapter = new HomePageAdapter(lists2.get(0));
                        adapter.notifyDataSetChanged();
                    }
                    homePageRecyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            ;
        };
        task.execute("categoryLoad");
        AsyncTask<String, Void, String> task2 = new AsyncTask<String, Void, String>() {
            private JSONArray c = new JSONArray();

            @Override
            protected String doInBackground(String... params) {
                String url = Config.IP_ADDRESS + "/api/product/nhanxet/" + productID;
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
                        c = json;

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return "commentLoad";
            }

            protected void onPostExecute(String result) {
                //Comment
                try {
                    for (int i = 0; i < c.length(); i++) {
                        JSONObject b = new JSONObject(c.get(i).toString());
                        listComment.add(new CommentModel(b.getString("TENKHACHHANG"), b.getString("NOIDUNG")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                commentAdapter.notifyDataSetChanged();
            }

            ;
        };
        task2.execute("commentLoad");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String _NAME = prefs.getString("NAME", null);
        String _MATK = prefs.getString("MATK", null);
        nhanXetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nhanxet = nhanXetText.getText().toString();
                if (nhanxet != "") {
                    AsyncTask<String, Void, String> task2 = new AsyncTask<String, Void, String>() {
                        @Override
                        protected String doInBackground(String... params) {
                            final Calendar c = Calendar.getInstance();
                            int mYear = c.get(Calendar.YEAR); // current year
                            int mMonth = c.get(Calendar.MONTH); // current month
                            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                            String day = mDay + "/" + (mMonth + 1) + "/" + mYear;
                            String url = Config.IP_ADDRESS + "/api/product/nhanxet_insert";
                            RequestBody formBody = new FormBody.Builder()
                                    .add("MAHANGHOA", productID)
                                    .add("SODIENTHOAI", _MATK)
                                    .add("NGAY", day)
                                    .add("NOIDUNG", nhanxet)
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
                                    System.out.println(jsonData);
                                    JSONArray json = new JSONArray(jsonData);

                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            return "Comment";
                        }

                        protected void onPostExecute(String result) {
                            listComment.add(new CommentModel(_NAME, nhanxet));
                            commentAdapter.notifyDataSetChanged();
                            nhanXetText.setText("");
                        }

                        ;
                    };
                    task2.execute("Comment");
                }

            }
        });
        //thêm giỏ hàng
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tonHang.getText().toString().equals("Hàng hiện có: Hết Hàng")) {
                    AsyncTask<String, Void, String> task3 = new AsyncTask<String, Void, String>() {
                        @Override
                        protected String doInBackground(String... params) {
                            String url = Config.IP_ADDRESS + "/api/giohang/giohang_insert";
                            RequestBody formBody = new FormBody.Builder()
                                    .add("MAHANGHOA", productID)
                                    .add("SDT", _MATK)
                                    .add("TENHANGHOA", TEN)
                                    .add("GIA", GIA)
                                    .add("SOLUONG", "1")
                                    .add("HINHANH", imageUrl)
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
                                    System.out.println(jsonData);
                                    JSONArray json = new JSONArray(jsonData);
                                    System.out.println(jsonData + "12314");

                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            return "Cartinsert";
                        }

                        protected void onPostExecute(String result) {
                            Toast.makeText(ProductDetailsActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        }

                        ;
                    };
                    task3.execute("Cartinsert");
                } else {
                    Toast.makeText(getBaseContext(), "Xin lỗi quý khác, Số lượng hàng đã hết vui lòng liên hệ vói chúng tôi!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tonHang.getText().toString().equals("Hàng hiện có: Hết Hàng")) {
                    AsyncTask<String, Void, String> task3 = new AsyncTask<String, Void, String>() {
                        @Override
                        protected String doInBackground(String... params) {
                            String url = Config.IP_ADDRESS + "/api/giohang/giohang_insert";
                            RequestBody formBody = new FormBody.Builder()
                                    .add("MAHANGHOA", productID)
                                    .add("SDT", _MATK)
                                    .add("TENHANGHOA", TEN)
                                    .add("GIA", GIA)
                                    .add("SOLUONG", "1")
                                    .add("HINHANH", imageUrl)
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
                                    System.out.println(jsonData);
                                    JSONArray json = new JSONArray(jsonData);
                                    System.out.println(jsonData + "12314");

                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            return "Cartinsert";
                        }

                        protected void onPostExecute(String result) {
                            viewCart.setVisibility(View.VISIBLE);
                            addToCartBtn.setVisibility(View.GONE);
                            buyNowBtn.setVisibility(View.GONE);
                            replaceFrament(new CartFragment());
                            Toast.makeText(ProductDetailsActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        }

                        ;
                    };
                    task3.execute("Cartinsert");
                } else {
                    Toast.makeText(getBaseContext(), "Xin lỗi quý khác, Số lượng hàng đã hết vui lòng liên hệ vói chúng tôi!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewpagerIndicator.setupWithViewPager(productImagesViewPager, true);

        addToWishListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    if (!running_wishlist_querry) {
                        running_wishlist_querry = true;
                        if (ALREADY_ADDED_TO_WISHLIST) {

                            int index = DBqueries.wishlist.indexOf(productID);
                            DBqueries.removeFromWishlist(index, ProductDetailsActivity.this);
                            ALREADY_ADDED_TO_WISHLIST = false;
                            addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                        } else {
                            addToWishListBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));

                            Map<String, Object> addProduct = new HashMap<>();
                            addProduct.put("product_ID_" + String.valueOf(DBqueries.wishlist.size()), productID);
                            addProduct.put("list_size", (long) (DBqueries.wishlist.size() + 1));

                            firebaseFirestore.collection("USERS").document(currentUser.getUid())
                                    .collection("USER_DATA").document("WISHLIST")
                                    .update(addProduct)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                if (DBqueries.wishlist.size() == 0) {
                                                    DBqueries.wishlistModelList.add(new WishlistModel(
                                                            productID
                                                            , documentSnapshot.get("product_image_1").toString()
                                                            , documentSnapshot.get("product_title").toString()
                                                            , (long) documentSnapshot.get("free_coupens")
                                                            , documentSnapshot.get("average_rating").toString()
                                                            , (long) documentSnapshot.get("total_ratings")
                                                            , documentSnapshot.get("product_price").toString()
                                                            , documentSnapshot.get("cutted_price").toString()
                                                            , (boolean) documentSnapshot.get("COD")
                                                            , inStock
                                                    ));
                                                }

                                                ALREADY_ADDED_TO_WISHLIST = true;
                                                addToWishListBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                                                DBqueries.wishlist.add(productID);
                                                Toast.makeText(ProductDetailsActivity.this, "Đã yêu thích!", Toast.LENGTH_SHORT).show();

                                            } else {
                                                addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                                                String err = task.getException().getMessage();
                                                Toast.makeText(ProductDetailsActivity.this, err, Toast.LENGTH_SHORT).show();
                                            }
                                            running_wishlist_querry = false;
                                        }
                                    });
                        }
                    }
                }
            }
        });
        //mmmm
        // phan mo ta san pham


        productDetailsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTablayout));
        productDetailsTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productDetailsViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        //rating layout
        rateNowContainer = findViewById(R.id.rate_now_container);
        for (int i = 0; i < rateNowContainer.getChildCount(); i++) {
            final int starPosition = i;
            rateNowContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRating(starPosition);
                }
            });
        }

        //////rating layout

        rateNowContainer = findViewById(R.id.rate_now_container);

        for (int x = 0; x < rateNowContainer.getChildCount(); x++) {
            final int starPosition = x;
            rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (currentUser == null) {
                        signInDialog.show();
                    } else {
                        if (starPosition != initialRating) {
                            if (!running_rating_querry) {
                                running_rating_querry = true;

                                setRating(starPosition);
                                Map<String, Object> updateRating = new HashMap<>();

                                if (DBqueries.RatedIds.contains(productID)) {
                                    TextView oldRating = (TextView) ratingsNoContainer.getChildAt(5 - initialRating - 1);
                                    TextView finalRating = (TextView) ratingsNoContainer.getChildAt(5 - starPosition - 1);

                                    updateRating.put(initialRating + 1 + "_star", Long.parseLong(oldRating.getText().toString()) - 1);
                                    updateRating.put(starPosition + 1 + "_star", Long.parseLong(finalRating.getText().toString()) + 1);
                                    updateRating.put("average_rating", calculateAverageRating((long) starPosition - initialRating, true));
                                } else {
                                    updateRating.put(starPosition + 1 + "_star", (long) documentSnapshot.get(starPosition + 1 + "_star") + 1);
                                    updateRating.put("average_rating", calculateAverageRating((long) starPosition + 1, false));
                                    updateRating.put("total_ratings", (long) documentSnapshot.get("total_ratings") + 1);

                                }

                                firebaseFirestore.collection("PRODUCTS").document(productID)
                                        .update(updateRating)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Map<String, Object> myRating = new HashMap<>();

                                                    if (DBqueries.RatedIds.contains(productID)) {
                                                        myRating.put("rating_" + DBqueries.RatedIds.indexOf(productID), (long) starPosition + 1);
                                                    } else {
                                                        myRating.put("product_ID_" + DBqueries.RatedIds.size(), productID);
                                                        myRating.put("rating_" + DBqueries.RatedIds.size(), (long) starPosition + 1);
                                                        myRating.put("list_size", (long) DBqueries.RatedIds.size() + 1);
                                                    }

                                                    firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("RATINGS")
                                                            .update(myRating)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        if (DBqueries.RatedIds.contains(productID)) {
                                                                            DBqueries.Rating.set(DBqueries.RatedIds.indexOf(productID), (long) starPosition + 1);

                                                                            TextView oldRating = (TextView) ratingsNoContainer.getChildAt(5 - initialRating - 1);
                                                                            oldRating.setText(String.valueOf(Integer.parseInt(oldRating.getText().toString()) - 1));
                                                                            TextView finalRating = (TextView) ratingsNoContainer.getChildAt(5 - starPosition - 1);
                                                                            finalRating.setText(String.valueOf(Integer.parseInt(finalRating.getText().toString()) + 1));
                                                                        } else {
                                                                            DBqueries.RatedIds.add(productID);
                                                                            DBqueries.Rating.add((long) starPosition + 1);
                                                                            productTotalRatings.setText("(" + ((long) documentSnapshot.get("total_ratings") + 1) + ")ratings");
                                                                            totalRatings.setText((long) documentSnapshot.get("total_ratings") + 1 + " ratings");
                                                                            totalRatingsFigure.setText(String.valueOf((long) documentSnapshot.get("total_ratings") + 1));

                                                                            TextView rating = (TextView) ratingsNoContainer.getChildAt(5 - starPosition - 1);
                                                                            rating.setText(String.valueOf(Integer.parseInt(rating.getText().toString()) + 1));

                                                                            Toast.makeText(ProductDetailsActivity.this, "Cảm ơn đánh giá của bạn!", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                        for (int x = 0; x < 5; x++) {
                                                                            TextView ratings = (TextView) ratingsNoContainer.getChildAt(x);
                                                                            ProgressBar progressBar = (ProgressBar) ratingsProgressBarContainer.getChildAt(x);
                                                                            int maxProgress = Integer.parseInt(totalRatingsFigure.getText().toString());
                                                                            progressBar.setMax(maxProgress);
                                                                            progressBar.setProgress(Integer.parseInt(ratings.getText().toString()));
                                                                        }
                                                                        initialRating = starPosition;
                                                                        averageRatings.setText(calculateAverageRating(0, true));
                                                                        productAvgRating.setText(calculateAverageRating(0, true));

                                                                        if (DBqueries.wishlist.contains(productID) && DBqueries.wishlistModelList.size() != 0) {
                                                                            int index = DBqueries.wishlist.indexOf(productID);
                                                                            DBqueries.wishlistModelList.get(index).setRating(averageRatings.getText().toString());
                                                                            DBqueries.wishlistModelList.get(index).setTotalRatings(Long.parseLong(totalRatingsFigure.getText().toString()));
                                                                        }
                                                                    } else {
                                                                        setRating(initialRating);
                                                                        String error = task.getException().getMessage();
                                                                        Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                                                                    }
                                                                    running_rating_querry = false;
                                                                }
                                                            });

                                                } else {
                                                    running_rating_querry = false;
                                                    setRating(initialRating);
                                                    String error = task.getException().getMessage();
                                                    Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                            }
                        }
                    }
                }

            });
        }


//////rating layout


/////////// Coupen dialog


        couponRedeemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCoupanPricedialog.show();
            }
        });

//        couponRedeemBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Dialog checkCouponPriceDialog = new Dialog(ProductDetailsActivity.this);
//                checkCouponPriceDialog.setContentView(R.layout.coupan_redeem_dialog);
//                checkCouponPriceDialog.setCancelable(true);
//                checkCouponPriceDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//
//                ImageView toggleRecyclesView = checkCouponPriceDialog.findViewById(R.id.toggle_recyclerview);
//                couponsRecyclerView = checkCouponPriceDialog.findViewById(R.id.coupans_recyclerView);
//                selectedCoupon = checkCouponPriceDialog.findViewById(R.id.selected_coupan);
//                coupenTitle = checkCouponPriceDialog.findViewById(R.id.coupan_title);
//                coupenExpiryDate = checkCouponPriceDialog.findViewById(R.id.coupan_validity);
//                coupenBody = checkCouponPriceDialog.findViewById(R.id.coupan_body);
//
//                TextView originalPrice = checkCouponPriceDialog.findViewById(R.id.original_price);
//                TextView discountedPrice = checkCouponPriceDialog.findViewById(R.id.discounted_price);
//
//                LinearLayoutManager layoutManager = new LinearLayoutManager(ProductDetailsActivity.this);
//                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//                couponsRecyclerView.setLayoutManager(layoutManager);
//
//                List<RewardModel> rewardModelList = new ArrayList<>();
////                rewardModelList.add(new RewardModel("Discount","Đến hết 30/9/2021","Giảm 10% cho sản phẩm dưới 190.000 VND"));
////                rewardModelList.add(new RewardModel("Cashback","Đến hết 30/9/2021","Giảm 10% cho sản phẩm dưới 190.000 VND"));
////                rewardModelList.add(new RewardModel("Buy 1 get 1 free","Đến hết 30/9/2021","Giảm 10% cho sản phẩm dưới 190.000 VND"));
////                rewardModelList.add(new RewardModel("Discount","Đến hết 30/9/2021","Giảm 10% cho sản phẩm dưới 190.000 VND"));
////                rewardModelList.add(new RewardModel("Discount","Đến hết 30/9/2021","Giảm 10% cho sản phẩm dưới 190.000 VND"));
////                rewardModelList.add(new RewardModel("Discount","Đến hết 30/9/2021","Giảm 10% cho sản phẩm dưới 190.000 VND"));
////                rewardModelList.add(new RewardModel("Discount","Đến hết 30/9/2021","Giảm 10% cho sản phẩm dưới 190.000 VND"));
////                rewardModelList.add(new RewardModel("Discount","Đến hết 30/9/2021","Giảm 10% cho sản phẩm dưới 190.000 VND"));
////                rewardModelList.add(new RewardModel("Discount","Đến hết 30/9/2021","Giảm 10% cho sản phẩm dưới 190.000 VND"));
//
//                RewardAdapter adapter = new RewardAdapter(rewardModelList,true);
//                couponsRecyclerView.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//
//                toggleRecyclesView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        showDialogRecyclerView();
//                    }
//                });
//
//                checkCouponPriceDialog.show();
//            }
//        });

        /////// sign in dialog box
        signInDialog = new Dialog(ProductDetailsActivity.this);
        signInDialog.setContentView(R.layout.sign_in_dialog);
        signInDialog.setCancelable(true);

        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button signInDialogBtn = signInDialog.findViewById(R.id.sign_in_btn);
        Button signUpDialogBtn = signInDialog.findViewById(R.id.sign_up_btn);
        /////// sign in dialog box

    }

    public static void showDialogRecyclerView() {
        if (couponsRecyclerView.getVisibility() == View.GONE) {
            couponsRecyclerView.setVisibility(View.VISIBLE);
            selectedCoupon.setVisibility(View.GONE);
        } else {
            couponsRecyclerView.setVisibility(View.GONE);
            selectedCoupon.setVisibility(View.VISIBLE);
        }
    }

    public static void setRating(int starPosition) {
        for (int i = 0; i < rateNowContainer.getChildCount(); i++) {
            ImageView starBtn = (ImageView) rateNowContainer.getChildAt(i);
            starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if (i <= starPosition) {
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFB00")));
            }
        }
    }


    private String calculateAverageRating(long currentUserRating, boolean update) {
        Double totalStars = Double.valueOf(0);
        for (int x = 1; x < 6; x++) {
            TextView ratingNo = (TextView) ratingsNoContainer.getChildAt(5 - x);
            totalStars = totalStars + (Long.parseLong(ratingNo.getText().toString()) * x);
        }
        totalStars = totalStars + currentUserRating;
        if (update) {
            return String.valueOf(totalStars / Long.parseLong(totalRatingsFigure.getText().toString())).substring(0, 3);
        } else {
            return String.valueOf(totalStars / (Long.parseLong(totalRatingsFigure.getText().toString()) + 1)).substring(0, 3);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        //coupon layout
        if (currentUser == null) {
            coupanRedemLayout.setVisibility(View.GONE);
        } else {
            coupanRedemLayout.setVisibility(View.VISIBLE);
        }
        if (currentUser != null) {
            if (DBqueries.Rating.size() == 0) {
                DBqueries.loadRatingList(ProductDetailsActivity.this);
            }
            if (DBqueries.cartList.size() != 0 && DBqueries.wishlist.size() != 0 && DBqueries.rewardModelList.size() != 0) {
                loadingDialog.dismiss();
            }
            if (DBqueries.rewardModelList.size() == 0) {
                DBqueries.loadRewards(ProductDetailsActivity.this, loadingDialog, false);
            }
            if (DBqueries.wishlist.size() == 0) {
                DBqueries.loadWishList(ProductDetailsActivity.this, loadingDialog, false);
            }
        } else {
            loadingDialog.dismiss();
        }

//        if (DBqueries.cartList.contains(productID)) {
//            ALREADY_ADDED_TO_CART = true;
//        } else {
//            ALREADY_ADDED_TO_CART = false;
//        }
        if (DBqueries.RatedIds.contains(productID)) {
            int index = DBqueries.RatedIds.indexOf(productID);
            initialRating = Integer.parseInt(String.valueOf(DBqueries.Rating.get(index))) - 1;
            setRating(initialRating);
        }
        if (DBqueries.wishlist.contains(productID)) {
            ALREADY_ADDED_TO_WISHLIST = true;
            addToWishListBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));

        } else {
            addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
            ALREADY_ADDED_TO_WISHLIST = false;
        }
        if (DBqueries.wishlist.contains(productID)) {
            ALREADY_ADD_TO_WISHLIST = true;
            addToWishListBtn.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
        } else {
            ALREADY_ADD_TO_WISHLIST = false;
        }
        invalidateOptionsMenu();
    }

    public static MenuItem cartItem;

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; thêm các item vào action bar nếu như nó hiển thị
        getMenuInflater().inflate(R.menu.seach_and_cart_icon, menu);
        cartItem = menu.findItem(R.id.main_cart_icon);


        cartItem.setActionView(R.layout.badge_layout);
        ImageView badgeIcon = cartItem.getActionView().findViewById(R.id.badge_icon);
        badgeIcon.setImageResource(R.drawable.shopping_cart);
        badgeCount = cartItem.getActionView().findViewById(R.id.badge_count);

        if (currentUser != null) {
            if (DBqueries.cartList.size() == 0) {
                DBqueries.loadCartList(ProductDetailsActivity.this, loadingDialog, false, badgeCount, new TextView(ProductDetailsActivity.this));
            } else {
                badgeCount.setVisibility(View.VISIBLE);
                if (DBqueries.cartList.size() < 99) {
                    badgeCount.setText(String.valueOf(DBqueries.cartList.size()));
                } else {
                    badgeCount.setText("99");
                }
            }
        }

        cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    showCart = true;
                    startActivity(new Intent(ProductDetailsActivity.this, MainActivity.class));
                }
            }
        });

        return true;
    }

    //------------------------------------------------------Sự kiện click cho menu bar ở trang home
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.main_search_icon) {
            if (fromSearch) {
                finish();
            } else {
                startActivity(new Intent(this, SearchActivity.class));
            }
            return true;
        } else if (id == android.R.id.home) {
            productDetailsActivity = null;
            finish();
            return true;
        } else if (id == R.id.main_cart_icon) {
            if (currentUser == null) {
                signInDialog.show();
            } else {
                showCart = true;
                startActivity(new Intent(ProductDetailsActivity.this, MainActivity.class));
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        productDetailsActivity = null;
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fromSearch = false;
    }

    public void replaceFrament(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_content_detail, fragment);
        fragmentTransaction.commit();
    }
}