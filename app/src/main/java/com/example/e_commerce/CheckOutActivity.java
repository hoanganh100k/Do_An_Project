package com.example.e_commerce;

import static com.example.e_commerce.DeliveryActivity.SELECT_ADDRESS;
import static com.example.e_commerce.RegisterActivity.setsignUpFragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CheckOutActivity extends AppCompatActivity {
    private EditText edtSdt;
    private EditText edtHoTen;
    private EditText edtGioiTinh;
    private EditText edtNgaySinh;
    private EditText edtDiaChi;
    private EditText edtEmail;
    private Button btnThanhToan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        edtSdt = (EditText)findViewById(R.id.edtSDT);
        edtHoTen = (EditText)findViewById(R.id.edtTen);
        edtGioiTinh = (EditText)findViewById(R.id.edtGT);
        edtNgaySinh = (EditText)findViewById(R.id.edtNS);
        edtDiaChi = (EditText)findViewById(R.id.edtDC);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        btnThanhToan = findViewById(R.id.btnTT);

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sdt = edtSdt.getText().toString();
                String hoTen = edtHoTen.getText().toString();
                String gioiTinh = edtGioiTinh.getText().toString();
                String ngaySinh = edtNgaySinh.getText().toString();
                String diaChi = edtDiaChi.getText().toString();
                String email = edtEmail.getText().toString();
                if (sdt.isEmpty() && hoTen.isEmpty() && gioiTinh.isEmpty() && ngaySinh.isEmpty() && diaChi.isEmpty() && email.isEmpty()) {
                    Toast.makeText(CheckOutActivity.this,"Vui lòng nhập đủ thông tin!",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(CheckOutActivity.this,"Đặt hàng thành công!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CheckOutActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}