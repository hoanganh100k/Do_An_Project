package com.example.e_commerce.ui.account;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.e_commerce.Config;
import com.example.e_commerce.DBqueries;
import com.example.e_commerce.MyAddressActivity;
import com.example.e_commerce.R;
import com.example.e_commerce.RegisterActivity;
import com.example.e_commerce.UpdateInfoActivity;
import com.example.lib.Model.MyOrderItemModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyAccountFragment extends Fragment {


    public MyAccountFragment() {
    }

    private FloatingActionButton settingsBtn;
    public final static int MANAGE_ADDRESS =1;
    private Button viewAllAddressButton,signOutBtn;
    private CircleImageView profileView,currentOrderImage;
    private TextView name,email,currentOrderstatus,recentOrdersTitle,addressname,address;
    private LinearLayout layoutContainer,recentOrdersContainer;
    private Dialog loadingDialog;
    private ImageView orderedIndicator,packedIndicator,shippedIndicator,deliveredIndicator;
    private ProgressBar O_P_progress,P_S_progress,S_D_progress;
    private TextView textName,textBirtday,textDiaChi,textChiTiet,textEmail;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_my_account, container, false);
        viewAllAddressButton=root.findViewById(R.id.view_all_addreses_btn);
        profileView=root.findViewById(R.id.main_profile_image);
        name=root.findViewById(R.id.username);
        email=root.findViewById(R.id.user_email);
        layoutContainer=root.findViewById(R.id.layout_container);
        currentOrderImage=root.findViewById(R.id.current_order_image);
        currentOrderstatus=root.findViewById(R.id.tv_current_order_status);

        orderedIndicator=root.findViewById(R.id.ordered_indicator);
        packedIndicator=root.findViewById(R.id.packed_indicator);
        shippedIndicator=root.findViewById(R.id.shipped_indicator);
        deliveredIndicator=root.findViewById(R.id.delivered_indicator);

        O_P_progress=root.findViewById(R.id.order_packed_progress);
        P_S_progress=root.findViewById(R.id.packed_shipped_progress);
        S_D_progress=root.findViewById(R.id.shipped_delivered_progress);

        recentOrdersTitle=root.findViewById(R.id.your_recent_orders_title);
        recentOrdersContainer=root.findViewById(R.id.recent_order_conteriner);

        addressname=root.findViewById(R.id.fullname);
        address=root.findViewById(R.id.address);
        signOutBtn=root.findViewById(R.id.sign_out_btn);
        settingsBtn=root.findViewById(R.id.setting_btn);


//////////loading dialog

        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        loadingDialog.show();
//////////loading dialog

        layoutContainer.getChildAt(1).setVisibility(View.GONE);
        loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

                for(MyOrderItemModel orderItemModel:DBqueries.myOrderItemModelList){
                    if(!orderItemModel.isCancellationrequested()){
                        if(!orderItemModel.getOrderStatus().equals("Delivered") && !orderItemModel.getOrderStatus().equals("Cancelled")){
                            layoutContainer.getChildAt(1).setVisibility(View.VISIBLE);
                            Glide.with(getContext()).load(orderItemModel.getProductImage()).apply(new RequestOptions().placeholder(R.mipmap.pic)).into(currentOrderImage);
                            currentOrderstatus.setText(orderItemModel.getOrderStatus());

                            switch (orderItemModel.getOrderStatus()){
                                case "Ordered":
                                    orderedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                                    break;
                                case "Packed":
                                    orderedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                                    packedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                                    O_P_progress.setProgress(100);
                                    break;
                                case "Shipped":
                                    shippedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                                    orderedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                                    packedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                                    O_P_progress.setProgress(100);
                                    P_S_progress.setProgress(100);
                                    break;
                                case "out for Delivery":
                                    deliveredIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                                    shippedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                                    orderedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                                    packedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                                    O_P_progress.setProgress(100);
                                    P_S_progress.setProgress(100);
                                    S_D_progress.setProgress(100);

                                    break;
                            }


                        }
                    }
                }
                int i=0;
                for(MyOrderItemModel myOrderItemModel:DBqueries.myOrderItemModelList){
                    if(i<4) {
                        if (myOrderItemModel.getOrderStatus().equals("Delivered")) {
                            Glide.with(getContext()).load(myOrderItemModel.getProductImage()).apply(new RequestOptions().placeholder(R.mipmap.pic)).into((CircleImageView) recentOrdersContainer.getChildAt(i));
                            i++;
                        }
                    }else {
                        break;
                    }
                }
                if(i==0){
                    recentOrdersTitle.setText("????n h??ng hi???n t???i");
                }
                if(i<3){
                    for (int x=i ; x<4;x++){
                        recentOrdersContainer.getChildAt(x).setVisibility(View.GONE);
                    }
                }
                loadingDialog.show();
                loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        loadingDialog.setOnDismissListener(null);
                        if(DBqueries.addressesModelList.size() == 0){
                            addressname.setText("No Address");
                            address.setText("-");
                        }else {
                            setAddress();
                        }
                    }
                });
                DBqueries.loadAddresses(getContext(),loadingDialog,false);

            }
        });

        DBqueries.loadOrders(getContext(),null,loadingDialog);

        viewAllAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(getContext(), MyAddressActivity.class).putExtra("MODE",MANAGE_ADDRESS));
            }
        });
        //th??ng tin
        textName = root.findViewById(R.id.textName);
        textBirtday = root.findViewById(R.id.textBirtday);
        textDiaChi = root.findViewById(R.id.textDiaChi);
        textChiTiet = root.findViewById(R.id.textChiTiet);
        textEmail = root.findViewById(R.id.textEmail);

        textName.setText(DBqueries.userInfomation.getUserFullName());
        textBirtday.setText(DBqueries.userInfomation.getNgaySinh());
        textDiaChi.setText(DBqueries.userInfomation.getDiaChi());
        textEmail.setText(DBqueries.userInfomation.getEmail());
        final View root1 = inflater.inflate(R.layout.infomation_user, container, false);
        textName = root1.findViewById(R.id.textName);
        textBirtday = root1.findViewById(R.id.textBirtday);
        textDiaChi = root1.findViewById(R.id.textDiaChi);
        textChiTiet = root1.findViewById(R.id.textChiTiet);
        textEmail = root1.findViewById(R.id.textEmail);
        OkHttpClient client = new OkHttpClient();
        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
            private JSONObject c = new JSONObject();

            @Override
            protected String doInBackground(String... params) {
                String url = Config.IP_ADDRESS + "/api/user/get/" + email.getText();
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
                        JSONObject b = new JSONObject(json.get(0).toString());
                        c = b;

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return "AccountLoad";
            }

            protected void onPostExecute(String result) {
                try {
                    System.out.println(c);
                    textName.setText(c.getString("TENKHACHHANG"));
                    textBirtday.setText(c.getString("NGAYSINH"));
                    textDiaChi.setText(c.getString("DIACHI"));
                    textChiTiet.setText(c.getString("CHITIET"));
                    textEmail.setText(c.getString("EMAIL"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            ;
        };
        task.execute("AccountLoad");

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBqueries.clearData();
                DBqueries.email = null;  //my code
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.remove("MATK"); // delete data by key key_name3
                editor.remove("NAME"); // delete data by key key_name3
                editor.remove("EMAIL"); // delete data by key key_name3
                editor.apply();
                startActivity(new Intent(getContext(), RegisterActivity.class));
                getActivity().finish();
            }
        });


        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateUserInfo = new Intent(getContext(), UpdateInfoActivity.class);
                updateUserInfo.putExtra("Name",name.getText());
                updateUserInfo.putExtra("Email",email.getText());
                updateUserInfo.putExtra("Photo",DBqueries.profile);
                startActivity(updateUserInfo);
            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        name.setText(DBqueries.fullname);
        email.setText(DBqueries.email);
        if(!DBqueries.profile.equals("")){
            Glide.with(getContext()).load(DBqueries.profile).apply(new RequestOptions().placeholder(R.mipmap.profile_placeholder)).into(profileView);
        }else {
            profileView.setImageResource(R.mipmap.profile_placeholder);
        }

        if(!loadingDialog.isShowing()){
            if(DBqueries.addressesModelList.size() == 0){
                addressname.setText("No Address");
                address.setText("-");
            }else {
                setAddress();
            }
        }
    }

    private void setAddress() {
        String nametext,mobileNo;
        nametext = DBqueries.addressesModelList.get(DBqueries.selectedAddress).getName();
        mobileNo = DBqueries.addressesModelList.get(DBqueries.selectedAddress).getMobileNo();

//        if(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getAlternateMobileNo().equals("")){
         addressname.setText(nametext + " - " + mobileNo);
//        }else {
//            addressname.setText(nametext + " - " + mobileNo+" or "+DBqueries.addressesModelList.get(DBqueries.selectedAddress).getAlternateMobileNo());
//        }

        String flatNo = DBqueries.addressesModelList.get(DBqueries.selectedAddress).getFlatNo();
        String city = DBqueries.addressesModelList.get(DBqueries.selectedAddress).getCity();
        String locality = DBqueries.addressesModelList.get(DBqueries.selectedAddress).getLocality();


        address.setText(flatNo+","+locality+","+city);



    }




//    private Dialog loadingDialog;
//    public MyAccountFragment() {
//    }
//
//    private Button viewAllAddressBtn;
//    public static final int MANAGE_ADDRESS = 1;             //????? qu???n l?? ch???n 1 addres ??? ph???n ch???n address
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
////////////loading dialog
//        loadingDialog = new Dialog(getContext());
//        loadingDialog.setContentView(R.layout.loading_progress_dialog);
//        loadingDialog.setCancelable(false);
//        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
//        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        loadingDialog.show();
////////////loading dialog
//
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_my_account, container, false);
//        viewAllAddressBtn = view.findViewById(R.id.view_all_addreses_btn);
//        viewAllAddressBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent addressIntent = new Intent(getContext(), MyAddressActivity.class);
//                addressIntent.putExtra("MODE", MANAGE_ADDRESS);
//                startActivity(addressIntent);
//            }
//        });
//        DBqueries.loadOrders(getContext(),null,loadingDialog);
//        return view;
//    }
}