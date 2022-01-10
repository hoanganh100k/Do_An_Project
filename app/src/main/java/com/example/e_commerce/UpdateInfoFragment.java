package com.example.e_commerce;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.lib.Model.UserModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateInfoFragment extends Fragment {
    public UpdateInfoFragment() {
        // Required empty public constructor
    }

    private EditText SDT;
    private EditText Hoten;
    private EditText GioiTinh;
    private EditText NgaySinh;
    private EditText DiaChi;
    private EditText Email;
    private Button btnUpdate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_info, container, false);
        SDT = view.findViewById(R.id.edtSDT);
        Hoten = view.findViewById(R.id.edtTen);
        GioiTinh = view.findViewById(R.id.edtGT);
        Email = view.findViewById(R.id.edtEmail);
        DiaChi = view.findViewById(R.id.edtDC);
        NgaySinh = view.findViewById(R.id.edtNS);
        btnUpdate = view.findViewById(R.id.btnTT);

        SDT.setText(DBqueries.userInfomation.getSDT());
        Hoten.setText(DBqueries.userInfomation.getUserFullName());
        GioiTinh.setText(DBqueries.userInfomation.getGioiTinh());
        Email.setText(DBqueries.userInfomation.getEmail());
        DiaChi.setText(DBqueries.userInfomation.getDiaChi());
        NgaySinh.setText(DBqueries.userInfomation.getNgaySinh());
        GioiTinh.setShowSoftInputOnFocus(false);
        GioiTinh.setKeyListener(null);

        GioiTinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] GioiTinhChon = {"Nam","Nữ"};

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Chọn Giới tính");
                builder.setItems(GioiTinhChon, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // the user clicked on colors[which]
                        switch (GioiTinhChon[which]) {
                            case "Nam":
                                GioiTinh.setText("Nam");
                                break;
                            case "Nữ":
                                GioiTinh.setText("Nữ");
                                break;

                        }
                    }
                });
                builder.show();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpClient client = new OkHttpClient();
                AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        String url = Config.IP_ADDRESS + "/api/user/update";
                        RequestBody formBody = new FormBody.Builder()
                                .add("MATK",DBqueries.email)
                                .add("HINHANH","null")
                                .add("HOTEN",Hoten.getText().toString())
                                .add("SODIENTHOAI",SDT.getText().toString())
                                .add("GIOITINH",GioiTinh.getText().toString())
                                .add("NGAYSINH",NgaySinh.getText().toString())
                                .add("DIACHI",DiaChi.getText().toString())
                                .add("MAIL",Email.getText().toString())
                                .build();

                        Request request = new Request.Builder()
                                .url(url)
                                .patch(formBody)
                                .header("Accept-Encoding", "identity")
                                .build();

                        try (Response response = client.newCall(request).execute()) {
                            if (!response.isSuccessful()) {
                                throw new IOException("Unexpected code: " + response);
                            } else {
                                String jsonData = response.body().string();
                                System.out.println(jsonData);
                                JSONArray json = new JSONArray(jsonData);
                                DBqueries.userInfomation = new UserModel(Email.getText().toString(),GioiTinh.getText().toString(),Hoten.getText().toString(),DiaChi.getText().toString(),NgaySinh.getText().toString(),SDT.getText().toString());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return "UpdateAcount";
                    }

                    protected void onPostExecute(String result) {
                        Toast.makeText(getActivity(), "Sửa thành công!", Toast.LENGTH_SHORT).show();
                    }

                    ;
                };
                task.execute("UpdateAcount");
            }
        });
        return view;
    }
}