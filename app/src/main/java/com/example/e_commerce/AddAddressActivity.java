package com.example.e_commerce;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.lib.Model.AddressesModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class AddAddressActivity extends AppCompatActivity {

    private String selectedState,stateList[];
    private Button saveBtn;
    private Spinner stateSpinner;
    private EditText city,locality,flatNo,pincode,landmark,name,mobileNo,alternateMobileNo;
    private Dialog loadingDialog;
    private boolean updateAddress=false;
    private AddressesModel addressesModel;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thêm địa chỉ mới");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        saveBtn=findViewById(R.id.save_btn);
        city=findViewById(R.id.tinh_thanhpho_edt);
        locality=findViewById(R.id.quan_huyen_edt);
        flatNo=findViewById(R.id.phuong_xa_edt);
//        pincode=findViewById(R.id.dia_chi_chi_tiet_edt);
        landmark=findViewById(R.id.dia_chi_chi_tiet_edt);
        name=findViewById(R.id.fullname);
        mobileNo=findViewById(R.id.address_phone);
//        alternateMobileNo=findViewById(R.id.alternate_mobile_no);
//        stateSpinner=findViewById(R.id.state_spinner);
//        stateList=getResources().getStringArray(R.array.india_states);

//////////loading dialog

        loadingDialog=new Dialog(AddAddressActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

//////////loading dialog


        ArrayAdapter spinnerAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        stateSpinner.setAdapter(spinnerAdapter);
//        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                selectedState=stateList[i];
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        if(getIntent().getStringExtra("INTENT").equals("update_address")){
            updateAddress=true;
            position=getIntent().getIntExtra("Position",-1);
            addressesModel=DBqueries.addressesModelList.get(position);

            flatNo.setText(addressesModel.getFlatNo());
            locality.setText(addressesModel.getLocality());
            landmark.setText(addressesModel.getLandmark());
            city.setText(addressesModel.getCity());
            pincode.setText(addressesModel.getPincode());
            name.setText(addressesModel.getName());
            mobileNo.setText(addressesModel.getMobileNo());
            alternateMobileNo.setText(addressesModel.getAlternateMobileNo());
//
//            for(int i=0;i<stateList.length;i++){
//                if(stateList[i].equals(addressesModel.getState())){
//                    stateSpinner.setSelection(i);
//                }
//            }
            saveBtn.setText("Update");
        }else {
            position=DBqueries.addressesModelList.size();
        }


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!TextUtils.isEmpty(city.getText())){
                    if(!TextUtils.isEmpty(locality.getText())){
                        if(!TextUtils.isEmpty(flatNo.getText())){
//                            if(!TextUtils.isEmpty(pincode.getText()) && pincode.getText().length() == 6)
//                            {
                                if(!TextUtils.isEmpty(name.getText())){
                                    if(!TextUtils.isEmpty(mobileNo.getText()) && mobileNo.getText().length() == 10){

                                        loadingDialog.show();
                                        //final String fullAddress=flatNo.getText().toString()+","+locality.getText().toString()+","+city.getText().toString()+","+selectedState+"\n"+landmark.getText().toString();

                                        Map<String,Object> addAddress= new HashMap<>();
                                        addAddress.put("mobile_no_" + (position+1), mobileNo.getText().toString());
//                                      addAddress.put("alternate_mobile_no_" + (position+1), alternateMobileNo.getText().toString());
                                        addAddress.put("name_" + (position+1), name.getText().toString());
                                     // addAddress.put("pincode_"+(position+1),pincode.getText().toString());
                                     // addAddress.put("state_"+(position+1),selectedState);
                                        addAddress.put("city_"+(position+1),city.getText().toString());
                                        addAddress.put("locality_"+(position+1),locality.getText().toString());
                                        addAddress.put("flat_no_"+(position+1),flatNo.getText().toString());
                                        addAddress.put("landmark_"+(position+1),landmark.getText().toString());

                                        if(!updateAddress){
                                            addAddress.put("list_size",(long) DBqueries.addressesModelList.size()+1);
                                            if(getIntent().getStringExtra("INTENT").equals("manage")){
                                                if(DBqueries.addressesModelList.size() == 0){
                                                    addAddress.put("selected_"+(position+1),true);
                                                }else {
                                                    addAddress.put("selected_"+(position+1),false);
                                                }
                                            }else {
                                                addAddress.put("selected_"+(position+1),true);
                                            }

                                            if(DBqueries.addressesModelList.size()>0) {
                                                addAddress.put("selected_" + (DBqueries.selectedAddress + 1), false);
                                            }
                                        }

                                        FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("ADDRESSES")
                                                .update(addAddress)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            if(!updateAddress) {
                                                                if (DBqueries.addressesModelList.size() > 0) {
                                                                    DBqueries.addressesModelList.get(DBqueries.selectedAddress).setSelected(false);
                                                                }
                                                                DBqueries.addressesModelList.add(new AddressesModel(
                                                                        city.getText().toString(),
                                                                        locality.getText().toString(),
                                                                        flatNo.getText().toString(),
                                                                   //   pincode.getText().toString(),
                                                                        landmark.getText().toString(),
                                                                        name.getText().toString()
                                                                        , mobileNo.getText().toString()
                                                                      //, alternateMobileNo.getText().toString()
                                                                     // , selectedState
                                                                        , true
                                                                ));
                                                                if(getIntent().getStringExtra("INTENT").equals("manage")){
                                                                    if(DBqueries.addressesModelList.size() == 0){
                                                                        DBqueries.selectedAddress=DBqueries.addressesModelList.size()-1;
                                                                    }
                                                                }else {
                                                                    DBqueries.selectedAddress=DBqueries.addressesModelList.size()-1;
                                                                }


                                                            }else {
                                                                DBqueries.addressesModelList.set(position,new AddressesModel(
                                                                        city.getText().toString(),
                                                                        locality.getText().toString(),
                                                                        flatNo.getText().toString(),
                                                                      //pincode.getText().toString(),
                                                                        landmark.getText().toString(),
                                                                        name.getText().toString()
                                                                        , mobileNo.getText().toString()
                                                                      //, alternateMobileNo.getText().toString()
                                                                      //, selectedState
                                                                        , true
                                                                ));
                                                            }
                                                            if(getIntent().getStringExtra("INTENT").equals("deliveryIntent")) {
                                                                Intent deliveryIntent = new Intent(AddAddressActivity.this, DeliveryActivity.class);
                                                                startActivity(deliveryIntent);
                                                            }else {
                                                                MyAddressActivity.refreshItem(DBqueries.selectedAddress,DBqueries.addressesModelList.size()-1);
                                                            }
                                                            finish();
                                                        }else {
                                                            String error=task.getException().getMessage();
                                                            Toast.makeText(AddAddressActivity.this,error,Toast.LENGTH_SHORT).show();
                                                        }
                                                        loadingDialog.dismiss();
                                                    }
                                                });

                                    }else {
                                        mobileNo.requestFocus();
                                        Toast.makeText(AddAddressActivity.this,"Nhâp đúng số điện thoại.",Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    name.requestFocus();
                                }
//                            }else {
//                                pincode.requestFocus();
//                                Toast.makeText(AddAddressActivity.this,"Please provide valid pincode.",Toast.LENGTH_SHORT).show();
//                            }
                        }else {
                            flatNo.requestFocus();
                        }
                    }else {
                        locality.requestFocus();
                    }
                }else {
                    city.requestFocus();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



//    private Button saveBtn;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_address);
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
//        getSupportActionBar().setTitle("Thêm mới địa chỉ");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        saveBtn = findViewById(R.id.save_btn);
//        saveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent deliveryIntent = new Intent(AddAddressActivity.this, DeliveryActivity.class);
//                startActivity(deliveryIntent);
//                finish();
//            }
//        });
//
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        switch (id){
//            case android.R.id.home:
//                finish();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}