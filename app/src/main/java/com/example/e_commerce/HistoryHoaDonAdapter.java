package com.example.e_commerce;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.lib.Model.HoaDon;

import java.util.List;

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
        if (!((TextView) viewHistoRyHoaDon.findViewById(R.id.textMaTT)).getText().toString().equals("Chưa thanh toán")) {
            btn.setVisibility(View.GONE);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String[] phuongthuc = {"Chuyển Khoản", "Nội Địa", "Quốc tế", "Khi nhận hàng"};

                    builder = new AlertDialog.Builder(viewHistoRyHoaDon.getContext());
                    builder.setTitle("Chọn phương thức thanh toán");
                    builder.setItems(phuongthuc, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // the user clicked on colors[which]
                            String maPhuongThuc = "";
                            switch (phuongthuc[which]) {
                                case "Chuyển Khoản":
                                    maPhuongThuc = "ck";
                                    break;
                                case "Nội Địa":
                                    maPhuongThuc = "nd";
                                    break;
                                case "Quốc tế":
                                    maPhuongThuc = "qt";
                                    break;
                                case "Khi nhận hàng":
                                    maPhuongThuc = "off";
                                    break;
                            }
                            if (maPhuongThuc != "") {
                                Intent intent = new Intent(viewHistoRyHoaDon.getContext(),DatHang.class);
                                intent.putExtra("maHoaDon",((TextView) viewHistoRyHoaDon.findViewById(R.id.textMaHD)).getText().toString());
                                intent.putExtra("phuongThuc",maPhuongThuc);
                                DBqueries.tong = Integer.parseInt(((TextView) viewHistoRyHoaDon.findViewById(R.id.textThanhTien)).getText().toString());
                                viewHistoRyHoaDon.getContext().startActivity(intent);
                            }
                        }
                    });
                    builder.show();
                }
            });
        } else {
            btn.setVisibility(View.VISIBLE);
        }
        return viewHistoRyHoaDon;
    }
}
