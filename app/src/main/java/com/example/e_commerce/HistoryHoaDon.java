package com.example.e_commerce;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lib.Model.HoaDon;
import com.example.lib.Model.HoaDonChiTietModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HistoryHoaDon extends AppCompatActivity {
    private ListView listHoaDon;
    public static HistoryHoaDonAdapter adapter;
    private List<HoaDon> listHoaDonData = new ArrayList<>();
    private List<HoaDonChiTietModel> listHoaDonChiTietData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_hoa_don);
        listHoaDon = findViewById(R.id.list_view_history_hoa_don);
        adapter = new HistoryHoaDonAdapter(listHoaDonData,HistoryHoaDon.this,listHoaDonChiTietData);
        listHoaDon.setAdapter(adapter);
        OkHttpClient client = new OkHttpClient();
        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String url = Config.IP_ADDRESS + "/api/hoadon/searchhoadonmatk/"+DBqueries.email;
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
                            listHoaDonData.add(new HoaDon(b.getString("MAHOADON"),b.getString("TENKHACHHANG"),b.getString("GIOITINH"),b.getString("TRANGTHAI"),b.getString("TONGTIEN"),b.getString("TRANGTHAITT"),b.getString("THANHTIEN")));
                            listHoaDonChiTietData.add(new HoaDonChiTietModel(b.getString("MAHOADON"),b.getString("TENKHACHHANG"),b.getString("SODIENTHOAI"),b.getString("GIOITINH"),b.getString("NGAYSINH"),b.getString("DIACHI"),b.getString("EMAIL"),b.getString("NGAYTAO"),b.getString("NGAYGIAO"),b.getString("TRANGTHAI"),b.getString("TRANGTHAITT"),b.getString("MATHANHTOAN"),b.getString("THANHTOAN"),b.getString("TONGTIEN"),b.getString("THANHTIEN"),b.getString("GHICHU")));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return "HistoryHoaDonLoad";
            }

            protected void onPostExecute(String result) {
                adapter.notifyDataSetChanged();
            }

            ;
        };
        task.execute("HistoryHoaDonLoad");




    }
}