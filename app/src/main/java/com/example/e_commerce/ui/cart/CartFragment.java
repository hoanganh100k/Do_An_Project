package com.example.e_commerce.ui.cart;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.Config;
import com.example.e_commerce.DBqueries;
import com.example.e_commerce.DeliveryActivity;
import com.example.e_commerce.R;
import com.example.lib.Model.CartItemModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class CartFragment extends Fragment {

    public CartFragment(){}

    private RecyclerView cartItemRecyclerView;
    public static Button btnContinue;
    private Dialog loadingDialog;
    public static CartAdapter cartAdapter;
    private TextView totalAmount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        //Gắn layout vào fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        totalAmount=view.findViewById(R.id.total_cart_amount);

        //////////loading dialog
        loadingDialog=new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        loadingDialog.show();
        //////////loading dialog

        cartItemRecyclerView= view.findViewById(R.id.cart_items_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemRecyclerView.setLayoutManager(layoutManager);

//        Đổ dữ liệu
        List<CartItemModel> cartItemModelList = new ArrayList<>();
        DBqueries.cartItemModelList = cartItemModelList;
        DBqueries.tong = 0;


        cartAdapter = new CartAdapter(DBqueries.cartItemModelList,totalAmount,true, 0,getContext());
        cartItemRecyclerView.setAdapter(cartAdapter);
        btnContinue = view.findViewById(R.id.cart_continue_btn);
        if(DBqueries.tong == 0){
            btnContinue.setBackgroundTintList(ContextCompat.getColorStateList(getContext(),R.color.DisabaleButton));
            btnContinue.setEnabled(false);
        }

        OkHttpClient client = new OkHttpClient();

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
                        for (int i = 0;i<json.length();i++){
                            JSONObject b = new JSONObject(json.get(i).toString());
                            String img = Config.IP_IMG_ADDRESS + b.getString("HINHANH");
                            cartItemModelList.add(new CartItemModel( (long) Integer.parseInt(b.getString("SoLuong")),b.getString("MaHangHoa"),img,b.getString("tensp"),b.getString("gia")));
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
                for (int i = 0; i < DBqueries.cartItemModelList.size(); i++) {
                    DBqueries.tong += Integer.parseInt(DBqueries.cartItemModelList.get(i).getProductPrice()) * DBqueries.cartItemModelList.get(i).getProductQuantity();
                }
                if(DBqueries.tong != 0){
                    btnContinue.setBackgroundTintList(ContextCompat.getColorStateList(getContext(),R.color.colorPrimary));
                    btnContinue.setEnabled(true);
                }
                NumberFormat formatter = new DecimalFormat("#,###");
                totalAmount.setText(formatter.format(DBqueries.tong)+"VNĐ");
                cartAdapter.notifyDataSetChanged();
            }

            ;
        };
        task.execute("cartLoad");



        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), DeliveryActivity.class));
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
//        cartAdapter.notifyDataSetChanged();


    }
    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}