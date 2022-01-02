package com.example.e_commerce;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lib.Model.HoaDon;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HistoryHoaDonAdapter extends BaseAdapter {
    private List<HoaDon> listData;
    private AlertDialog.Builder builder;

    public HistoryHoaDonAdapter(List<HoaDon> listDataCS) {
        this.listData = listDataCS;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View viewHistoRyHoaDon;
        if (convertView == null) {
            viewHistoRyHoaDon = View.inflate(parent.getContext(), R.layout.history_hoa_don_item, null);
        } else viewHistoRyHoaDon = convertView;

        //Bind sữ liệu phần tử vào View
        HoaDon historyHoaDonn = listData.get(i);
        ((TextView) viewHistoRyHoaDon.findViewById(R.id.textMaHD)).setText(historyHoaDonn.getMaHD());
        ((TextView) viewHistoRyHoaDon.findViewById(R.id.textTenHD)).setText(historyHoaDonn.getTenKH());
        ((TextView) viewHistoRyHoaDon.findViewById(R.id.textGioiTinhHD)).setText(historyHoaDonn.getGioiTinh());
        ((TextView) viewHistoRyHoaDon.findViewById(R.id.textTTHD)).setText(historyHoaDonn.getTrangThai());
        ((TextView) viewHistoRyHoaDon.findViewById(R.id.textTONGTIENHD)).setText(historyHoaDonn.getTongTien());
        ((TextView) viewHistoRyHoaDon.findViewById(R.id.textMaTT)).setText(historyHoaDonn.getMaTT());
        ((TextView) viewHistoRyHoaDon.findViewById(R.id.textThanhTien)).setText(historyHoaDonn.getThanhTien());
        Button btn = (Button) viewHistoRyHoaDon.findViewById(R.id.btnThanhToanLai);
        String maHD = ((TextView) viewHistoRyHoaDon.findViewById(R.id.textMaHD)).getText().toString();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] lyDoDuy = {"Chờ giao hàng lâu", "Tìm thấy sản phẩm khác tốt hơn", "Không muốn mua tiếp", "Khác"};

                builder = new AlertDialog.Builder(viewHistoRyHoaDon.getContext());
                builder.setTitle("Chọn lý do hủy");
                builder.setItems(lyDoDuy, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // the user clicked on colors[which]
                        String lyDoHuyStr = "";
                        switch (lyDoDuy[which]) {
                            case "Chờ giao hàng lâu":
                                lyDoHuyStr = "Chờ giao hàng lâu";
                                deleteHoadon(lyDoHuyStr, maHD, "null", i);
                                break;
                            case "Tìm thấy sản phẩm khác tốt hơn":
                                lyDoHuyStr = "Tìm thấy sản phẩm khác tốt hơn";
                                deleteHoadon(lyDoHuyStr, maHD, "null", i);
                                break;
                            case "Không muốn mua tiếp":
                                lyDoHuyStr = "Không muốn mua tiếp";
                                deleteHoadon(lyDoHuyStr, maHD, "null", i);
                                break;
                            case "Khác":
                                lyDoHuyStr = "Khác";
                                LyDoKhacText(viewHistoRyHoaDon.getContext(), i, maHD, lyDoHuyStr);
//                                deleteHoadon(lyDoHuyStr,maHD,"null",i);
                                break;
                        }
                    }
                });
                builder.show();
            }
        });
        return viewHistoRyHoaDon;
    }

    public void deleteHoadon(String str, String MaHoaDon, String other, int postion) {
        OkHttpClient client = new OkHttpClient();
        AsyncTask<String, Void, String> task2 = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String url = Config.IP_ADDRESS + "/api/hoadon/hoadon_Huy";
                RequestBody formBody = new FormBody.Builder()
                        .add("MAHOADON", MaHoaDon)
                        .add("MATAIKHOAN", DBqueries.email)
                        .add("LYDOHUY", str)
                        .add("LYDOKHAC", other)
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
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "DeleteHoaDon";
            }

            protected void onPostExecute(String result) {
                listData.remove(postion);
                HistoryHoaDon.adapter.notifyDataSetChanged();
            }

            ;
        };
        task2.execute("DeleteHoaDon");
    }

    public void LyDoKhacText(Context context, int postion, String MaHoaDon, String lyDo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.AlertDialogTheme);
        builder.setTitle("Nhập lý do khác");

// Set up the input
        final EditText input = new EditText(context);
        input.setHint("Nhập lý do khác");
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteHoadon(lyDo, MaHoaDon, input.getText().toString(), postion);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
