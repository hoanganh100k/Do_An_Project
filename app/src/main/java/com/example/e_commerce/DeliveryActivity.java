package com.example.e_commerce;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.ui.cart.CartAdapter;
import com.example.lib.Model.CartItemModel;
import com.example.lib.Model.CheckOutModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DeliveryActivity extends AppCompatActivity {

    public static boolean ordered = false;
    public static List<CartItemModel> cartItemModelList;
    private Toolbar toolbar;
    private RecyclerView deliveryRecyclerView;
    private Button changeORaddNewAddressBtn, continueBtn;
    public final static int SELECT_ADDRESS = 0;
    private TextView totalAmount, fullname, fullAddress, orderId;
    private Dialog paymentMethodDialog;
    public static Dialog loadingDialog;
    private ImageButton paytm, cod, continueShoppingBtn;
    private ConstraintLayout orderConfirmationLayout;
    private boolean successResponse = false;
    public static boolean fromCart, codOrderConfirmed = false, getQTYIDs = true;
    private String name, mobileNo, paymentMethod;
    private String order_id;
    private FirebaseFirestore firebaseFirestore;
    public static CartAdapter cartAdapter;
    private TextView codBtnTitle;
    private View divider;
    private EditText SDT;
    private EditText Hoten;
    private EditText GioiTinh;
    private EditText NgaySinh;
    private EditText DiaChi;
    private EditText Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        firebaseFirestore = FirebaseFirestore.getInstance();
        getQTYIDs = true;
//////////loading dialog
        loadingDialog = new Dialog(DeliveryActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false); //true
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

//////////loading dialog

//////////paymentMethodDialog

        paymentMethodDialog = new Dialog(DeliveryActivity.this);
        paymentMethodDialog.setContentView(R.layout.payment_method);
        paymentMethodDialog.setCancelable(true);
        paymentMethodDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        paymentMethodDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paytm = paymentMethodDialog.findViewById(R.id.paytm);
        cod = paymentMethodDialog.findViewById(R.id.cod_btn);
        codBtnTitle = paymentMethodDialog.findViewById(R.id.cod_btn_title);
        divider = paymentMethodDialog.findViewById(R.id.payment_divider);

//////////paymentMethodDialog
        order_id = UUID.randomUUID().toString().substring(0, 28);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Giao hàng");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        totalAmount = findViewById(R.id.total_cart_amount);
        fullname = findViewById(R.id.fullname);
        fullAddress = findViewById(R.id.address);
        SDT = findViewById(R.id.edtSDT);
        Hoten = findViewById(R.id.edtTen);
        GioiTinh = findViewById(R.id.edtGT);
        Email = findViewById(R.id.edtEmail);
        DiaChi = findViewById(R.id.edtDC);
        NgaySinh = findViewById(R.id.edtNS);

        SDT.setText(DBqueries.userInfomation.getSDT());
        Hoten.setText(DBqueries.userInfomation.getUserFullName());
        GioiTinh.setText(DBqueries.userInfomation.getGioiTinh());
        Email.setText(DBqueries.userInfomation.getEmail());
        DiaChi.setText(DBqueries.userInfomation.getDiaChi());
        NgaySinh.setText(DBqueries.userInfomation.getNgaySinh());

//        pincode = findViewById(R.id.pincode);
        continueBtn = findViewById(R.id.btnTT);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        deliveryRecyclerView.setLayoutManager(linearLayoutManager);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _SDT = SDT.getText().toString();
                String _HoTen = Hoten.getText().toString();
                String _GioiTinh = GioiTinh.getText().toString();
                String _Email = Email.getText().toString();
                String _DiaChi = DiaChi.getText().toString();
                String _NgaySinh = NgaySinh.getText().toString();
                String reg = "^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$";
                String regEmail = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
                if (_Email.matches(regEmail) && _SDT.matches(reg)) {
                    if (_SDT != "" && _HoTen != "" && _GioiTinh != "" && _Email != "" && _DiaChi != "" && _NgaySinh != "") {
                        DBqueries.checkOutModel = new CheckOutModel(_SDT, _HoTen, _GioiTinh, _NgaySinh, _DiaChi, _Email);
                        startActivity(new Intent(DeliveryActivity.this, HoaDonActivity.class));
                    } else {
                        Toast.makeText(DeliveryActivity.this, "Bạn Chưa nhập đủ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onPause() {
        super.onPause();
        loadingDialog.dismiss();

    }

    @Override
    public void onBackPressed() {
        if (successResponse) {
            finish();
            return;
        }
        super.onBackPressed();
    }

    private void showConfirmationLayout() {
        codOrderConfirmed = false;
        successResponse = true;
        getQTYIDs = false;
        for (int x = 0; x < cartItemModelList.size() - 1; x++) {

            for (String qtyID : cartItemModelList.get(x).getQtyIDs()) {
                firebaseFirestore.collection("PRODUCTS").document(cartItemModelList.get(x).getProductID()).collection("QUANTITY").document(qtyID).update("user_ID", FirebaseAuth.getInstance().getUid());
            }

        }

        if (MainActivity.mainActivity != null) {
            MainActivity.mainActivity.finish();
            MainActivity.mainActivity = null;
            MainActivity.showCart = false;
        } else {
            MainActivity.resetMainActivity = true;
        }

        if (ProductDetailsActivity.productDetailsActivity != null) {
            ProductDetailsActivity.productDetailsActivity.finish();
            ProductDetailsActivity.productDetailsActivity = null;
        }

        if (fromCart) {
            loadingDialog.show();
            Map<String, Object> updateCart = new HashMap<>();
            long cartListSize = 0;
            final List<Integer> indexList = new ArrayList<>();
            for (int x = 0; x < DBqueries.cartList.size(); x++) {
                if (!cartItemModelList.get(x).isInStock()) {
                    updateCart.put("product_ID_" + cartListSize, cartItemModelList.get(x).getProductID());
                    cartListSize++;
                } else {
                    indexList.add(x);
                }

            }
            updateCart.put("list_size", cartListSize);

        }
        continueBtn.setEnabled(false);
        changeORaddNewAddressBtn.setEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        orderId.setText("Order ID " + order_id);
        orderConfirmationLayout.setVisibility(View.VISIBLE);
        continueShoppingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}