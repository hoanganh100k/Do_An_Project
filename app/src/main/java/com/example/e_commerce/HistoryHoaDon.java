package com.example.e_commerce;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lib.Model.HoaDon;

import java.util.ArrayList;
import java.util.List;

public class HistoryHoaDon extends AppCompatActivity {
    private ListView listHoaDon;
    private HistoryHoaDonAdapter adapter;
    private List<HoaDon> listHoaDonData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_hoa_don);
        listHoaDon = findViewById(R.id.list_view_history_hoa_don);
        adapter = new HistoryHoaDonAdapter(listHoaDonData);
        listHoaDon.setAdapter(adapter);

        listHoaDonData.add(new HoaDon("12","313","414","414","414","414","414"));
        listHoaDonData.add(new HoaDon("12","313","414","414","414","414","414"));
        adapter.notifyDataSetChanged();



    }
}