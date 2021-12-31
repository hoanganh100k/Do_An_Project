package com.example.e_commerce;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lib.Model.HoaDon;

import java.util.List;

public class HistoryHoaDonAdapter extends BaseAdapter {
    private List<HoaDon> listData;
    public HistoryHoaDonAdapter(List<HoaDon> listDataCS){
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
        return viewHistoRyHoaDon;
    }
}
