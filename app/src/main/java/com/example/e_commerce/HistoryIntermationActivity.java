package com.example.e_commerce;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.lib.Model.HoaDonChiTietModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HistoryIntermationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infomation_hoa_don);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
            }
        });
        Intent intent = getIntent();
        HoaDonChiTietModel ThongTinChiTiet = (HoaDonChiTietModel) intent.getSerializableExtra("getThongTinChiTiet");
        String MaHD = ThongTinChiTiet.getMaHoaDon();
        getSupportActionBar().setTitle("Hóa Đơn:#" + MaHD);
        ((TextView) findViewById(R.id.textView_ma_hoa_don)).setText(MaHD);
        ((TextView) findViewById(R.id.textView_ho_ten)).setText(ThongTinChiTiet.getHoTen());
        ((TextView) findViewById(R.id.textView_sdt)).setText(ThongTinChiTiet.getSDT());
        ((TextView) findViewById(R.id.textView_gioi_tinh)).setText(ThongTinChiTiet.getGioiTinh());
        ((TextView) findViewById(R.id.textView_ngay_sinh)).setText(ThongTinChiTiet.getNgaySinh());
        ((TextView) findViewById(R.id.textView_dia_chi)).setText(ThongTinChiTiet.getDiaChi());
        ((TextView) findViewById(R.id.textView_email)).setText(ThongTinChiTiet.getEmail());
        String[] arrString = ThongTinChiTiet.getNgayTao().split("-");
        String NgayTao = arrString[2] + "/" + arrString[1] + "/" + arrString[0];
        ((TextView) findViewById(R.id.textView_ngay_tao)).setText(NgayTao);
        ((TextView) findViewById(R.id.textView_ngay_giao)).setText((ThongTinChiTiet.getNgayGiao().equals("null")) ? "Chưa giao" : ThongTinChiTiet.getNgayGiao());
        ((TextView) findViewById(R.id.textView_trang_thai)).setText(ThongTinChiTiet.getTrangThai());
        ((TextView) findViewById(R.id.textView_thanh_toan)).setText(ThongTinChiTiet.getThanhToan());
        ((TextView) findViewById(R.id.textViewma_thanhtoan)).setText(ThongTinChiTiet.getMaThanhToan());
        String phuongThuc = "";
        switch (ThongTinChiTiet.getLoaiThanhToan()) {
            case "ck":
                phuongThuc = "Chuyển khoản";
                break;
            case "nd":
                phuongThuc = "Nội địa";
                break;
            case "off":
                phuongThuc = "Thanh toán khi giao hàng";
                break;
            case "qt":
                phuongThuc = "Quốc tế";
                break;
        }
        ((TextView) findViewById(R.id.textView_loai_thanh_toan)).setText(phuongThuc);
        NumberFormat formatter = new DecimalFormat("#,###");
        ((TextView) findViewById(R.id.textViewTongtien)).setText(formatter.format(Integer.parseInt(ThongTinChiTiet.getTongTien())));
        ((TextView) findViewById(R.id.textViewThanhTien)).setText(formatter.format(Integer.parseInt(ThongTinChiTiet.getThanhTien())));
        ((TextView) findViewById(R.id.textViewGhiCHu)).setText(ThongTinChiTiet.getGhiChu().equals("null") ? "không có ghi chú" : ThongTinChiTiet.getGhiChu());
        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayoutProductHistory);

        OkHttpClient client = new OkHttpClient();
        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
            private String jsonString = "";

            @Override
            protected String doInBackground(String... params) {
                String url = Config.IP_ADDRESS + "/api/hoadon/searchchitiethoadon/" + MaHD;
                Request request = new Request.Builder()
                        .url(url)
                        .header("Accept-Encoding", "identity")
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code: " + response);
                    } else {
                        String jsonData = response.body().string();
                        jsonString = jsonData;


                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "HistoryHoaDonLoad";
            }

            protected void onPostExecute(String result) {
                JSONArray json = null;
                try {
                    json = new JSONArray(jsonString);
                    for (int i = 0; i < json.length(); i++) {
                        JSONObject b = new JSONObject(json.get(i).toString());
                        System.out.println(b.getString("TENHANG"));
                        TableRow row = new TableRow(HistoryIntermationActivity.this);
                        TextView ten = new TextView(HistoryIntermationActivity.this);
                        ten.setText(b.getString("TENHANG"));
                        ten.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        row.addView(ten);
                        TextView maHH = new TextView(HistoryIntermationActivity.this);
                        maHH.setText(b.getString("MAHANGHOA"));
                        maHH.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        row.addView(maHH);
                        TextView SoLuong = new TextView(HistoryIntermationActivity.this);
                        SoLuong.setText(b.getString("SOLUONG"));
                        SoLuong.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        row.addView(SoLuong);
                        TextView Gia = new TextView(HistoryIntermationActivity.this);
                        Gia.setText(formatter.format(Integer.parseInt(b.getString("DONGIA"))) + "VNĐ");
                        Gia.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        row.addView(Gia);
                        tableLayout.addView(row);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            ;
        };
        task.execute("HistoryHoaDonLoad");


    }
}
