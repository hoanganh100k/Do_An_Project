package com.example.e_commerce;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DatHang extends AppCompatActivity {
    private WebView web;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_hang);
        web = findViewById(R.id.webViewThanhToan);
        Intent intent = getIntent();
        OkHttpClient client = new OkHttpClient();
        System.out.println(intent.getStringExtra("thanhTien"));
        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
            String urlThanhToan = "";
            @Override
            protected String doInBackground(String... params) {
                String url = Config.IP_ADDRESS + "/api/hoadon/thanhToanHoaDon";
                RequestBody formBody = new FormBody.Builder()
                        .add("THANHTIEN",intent.getStringExtra("thanhTien"))
                        .add("MATHANHTOAN", intent.getStringExtra("maHoaDon"))
                        .add("PHUONGTHUC", intent.getStringExtra("phuongThuc"))
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
                        urlThanhToan = jsonData;
                        System.out.println(urlThanhToan);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "getLinkThanhToan";
            }

            @SuppressLint("JavascriptInterface")
            protected void onPostExecute(String result) {
                web.getSettings().setJavaScriptEnabled(true);
                web.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        //url will be the url that you click in your webview.
                        //you can open it with your own webview or do
                        //whatever you want

                        //Here is the example that you open it your own webview.
                        view.loadUrl(url);
                        return true;
                    }
                });
                web.loadUrl(urlThanhToan);
            }

            ;
        };
        task.execute("getLinkThanhToan");
        System.out.println();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DatHang.this,MainActivity.class);
        startActivity(intent);
    }


}