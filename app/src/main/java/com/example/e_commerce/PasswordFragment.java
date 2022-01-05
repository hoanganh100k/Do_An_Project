package com.example.e_commerce;


import android.app.Dialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class PasswordFragment extends Fragment {
    public PasswordFragment() {
        // Required empty public constructor
    }

    private EditText oldPassword,newPassword,confirmNewPass;
    private Button updatePassBtn;
    private Dialog loadingDialog;
    private String emaill;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_password, container, false);
        oldPassword=view.findViewById(R.id.old_password);
        newPassword=view.findViewById(R.id.new_password);
        confirmNewPass=view.findViewById(R.id.confirm_new_password);
        updatePassBtn=view.findViewById(R.id.update_pass_btn);
//////////loading dialog

        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //////////loading dialog

        oldPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        newPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        confirmNewPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        emaill=getArguments().getString("Email");

        updatePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmailandPass();
            }
        });

        return view;
    }


    private void checkEmailandPass() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(newPassword.getText().toString().equals(confirmNewPass.getText().toString())){
                loadingDialog.show();
                OkHttpClient client = new OkHttpClient();
                AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
                    private String statusChangePassword = "";
                    @Override
                    protected String doInBackground(String... params) {
                        String url = Config.IP_ADDRESS + "/api/user/changepass";
                        RequestBody formBody = new FormBody.Builder()
                                .add("MATK", DBqueries.email)
                                .add("NEWPASS", newPassword.getText().toString())
                                .add("OLDPASS", oldPassword.getText().toString())
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
                                statusChangePassword = jsonData;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return "ChangePassword";
                    }

                    protected void onPostExecute(String result) {
                        System.out.println(statusChangePassword);
                        if(statusChangePassword.equals("True")){
                            loadingDialog.dismiss();
                            Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            loadingDialog.dismiss();
                            Toast.makeText(getContext(), "Đổi mật khẩu không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }

                    ;
                };
                task.execute("ChangePassword");

            }else {
                confirmNewPass.setError("Password không khớp !");
            }

    }

    private void checkInputs() {

        if(!TextUtils.isEmpty(oldPassword.getText()) && oldPassword.length()>=8){
            if(!TextUtils.isEmpty(newPassword.getText()) && newPassword.length()>=8){
                if(!TextUtils.isEmpty(confirmNewPass.getText()) && confirmNewPass.length()>=8){
                    updatePassBtn.setEnabled(true);
                    updatePassBtn.setTextColor(Color.rgb(255,255,255));
                }else{
                    updatePassBtn.setEnabled(false);
                    updatePassBtn.setTextColor(Color.argb(50,255,255,255));
                }
            }else{
                updatePassBtn.setEnabled(false);
                updatePassBtn.setTextColor(Color.argb(50,255,255,255));
            }
        }else{
            updatePassBtn.setEnabled(false);
            updatePassBtn.setTextColor(Color.argb(50,255,255,255));
        }

    }

}
