package com.example.e_commerce;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.ui.cart.CartAdapter;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HoaDonActivity extends AppCompatActivity {
    private RecyclerView cartItemRecyclerView;
    private CartAdapter cartAdapter;
    private TextView total;
    private TextView SDT;
    private TextView Hoten;
    private TextView GioiTinh;
    private TextView NgaySinh;
    private TextView DiaChi;
    private TextView Email;
    private TextView TongTien;
    private TextView ThanhTien;
    private Button DatHang;
    private RadioButton radioButtonDefaut;
    private RadioGroup radioGroup;
    private String phuongThucThanhToan = "ck";
    private EditText ghiChu;
    private String ThanhTienValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Lịch sử hóa đơn");
        cartItemRecyclerView = findViewById(R.id.cart_hoadon);
        SDT = findViewById(R.id.textEmailSDT);
        Hoten = findViewById(R.id.textNameTT);
        GioiTinh = findViewById(R.id.textChiTietGT);
        NgaySinh = findViewById(R.id.textBirtdayTT);
        DiaChi = findViewById(R.id.textDiaChiTT);
        Email = findViewById(R.id.textEmailTT);
        DatHang = findViewById(R.id.dathang);
        SDT.setText(DBqueries.checkOutModel.getSDT());
        Hoten.setText(DBqueries.checkOutModel.getName());

        GioiTinh.setText(DBqueries.checkOutModel.getGioiTinh());
        NgaySinh.setText(DBqueries.checkOutModel.getNgaySinh());
        DiaChi.setText(DBqueries.checkOutModel.getDiaChi());
        Email.setText(DBqueries.checkOutModel.getEmail());

        radioGroup = findViewById(R.id.radioGroup);
        TongTien = findViewById(R.id.tongTien);
        ThanhTien = findViewById(R.id.thanhTien);
        ghiChu = findViewById(R.id.ghichuText);
        radioButtonDefaut = findViewById(R.id.chuyenkhoan);
        radioButtonDefaut.setChecked(true);

        TongTien.setText(DBqueries.tong + "VND");
        ThanhTienValue = DBqueries.tong+"";
        ThanhTien.setText( ThanhTienValue+ "VND");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemRecyclerView.setLayoutManager(layoutManager);
        cartAdapter = new CartAdapter(DBqueries.cartItemModelList, total, true, 1);
        System.out.println(cartAdapter.getItemCount() + "cc");
        cartItemRecyclerView.setAdapter(cartAdapter);

        cartAdapter.notifyDataSetChanged();
        long tickA = 621355968000000000L;
        long tickTime =((System.currentTimeMillis() * 10000) + tickA);
        final Calendar d = Calendar.getInstance();
        int mYear = d.get(Calendar.YEAR); // current year
        int mMonth = d.get(Calendar.MONTH); // current month
        int mDay = d.get(Calendar.DAY_OF_MONTH); // current day
        String dateNow = mDay + "/" + (mMonth + 1) + "/" + mYear;
        System.out.println(dateNow);
        OkHttpClient client = new OkHttpClient();
        DatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTask<String, Void, String> task5 = new AsyncTask<String, Void, String>() {
                    String urlThanhToan = "";
                    @Override
                    protected String doInBackground(String... params) {
                        final Calendar c = Calendar.getInstance();
                        int mYear = c.get(Calendar.YEAR); // current year
                        int mMonth = c.get(Calendar.MONTH); // current month
                        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                        String dateNow = mDay + "/" + (mMonth + 1) + "/" + mYear;
                        String url = Config.IP_ADDRESS + "/api/hoadon/hoadon_insert";

                        RequestBody formBody = new FormBody.Builder()
                                .add("MATAIKHOAN", DBqueries.email+"")
                                .add("MAHOADON", tickTime+"")
                                .add("SODIENTHOAI", SDT.getText().toString()+"")
                                .add("TENKHACHHANG", Hoten.getText().toString()+"")
                                .add("GIOITINH", GioiTinh.getText().toString()+"")
                                .add("NGAYSINH", NgaySinh.getText().toString()+"")
                                .add("DIACHI", DiaChi.getText().toString()+"")
                                .add("EMAIL", Email.getText().toString().trim()+"")
                                .add("NGAYTAO", dateNow+"")
                                .add("TONGTIEN", DBqueries.tong+"")
                                .add("THANHTIEN", ThanhTienValue)
                                .add("THANHTOAN", phuongThucThanhToan)
                                .add("GHICHU", ghiChu.getText().toString()+"null")
                                .add("MATHANHTOAN", tickTime+"")
                                .build();

                        Request request = new Request.Builder()
                                .url(url)
                                .post(formBody)
                                .header("Accept-Encoding", "identity")
                                .header("accept-charset","utf8")
                                .build();

                        try (Response response = client.newCall(request).execute()) {
                            if (!response.isSuccessful()) {
                                System.out.println(formBody);
                                throw new IOException("Unexpected code: " + response);
                            } else {
                                String jsonData = response.body().string();

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return "insert_HoaDon";
                    }

                    protected void onPostExecute(String result) {
                        Intent intent = new Intent(HoaDonActivity.this, com.example.e_commerce.DatHang.class);
                        intent.putExtra("phuongThuc",phuongThucThanhToan);
                        intent.putExtra("maHoaDon",tickTime+"");
                        startActivity(intent);
                    }

                };
                task5.execute("insert_HoaDon");
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.chuyenkhoan:
                        phuongThucThanhToan = "ck";
                        break;
                    case R.id.noidia:
                        phuongThucThanhToan = "nd";
                        break;
                    case R.id.quocte:
                        phuongThucThanhToan = "qt";
                        break;
                    case R.id.khinhanhang:
                        phuongThucThanhToan = "off";
                        break;
                }
            }
        });

    }


}