package com.example.e_commerce;

import static com.example.e_commerce.DeliveryActivity.SELECT_ADDRESS;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MyAddressActivity extends AppCompatActivity {

    private int previousAddress, mode;
    private RecyclerView recyclerView;
    private LinearLayout addNewAddressBtn;
    private static AddressAdapter addressesAdapter;
    private Button deliverHere;
    private TextView addressesSaved;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
//////////loading dialog

        loadingDialog=new Dialog(MyAddressActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                addressesSaved.setText(String.valueOf(DBqueries.addressesModelList.size())+" addresses");

            }
        });

//////////loading dialog

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Địa chỉ của tôi");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView=findViewById(R.id.address_recyclerview);
        deliverHere=findViewById(R.id.delivery_here_btn);
        addNewAddressBtn=findViewById(R.id.add_new_address_btn);
        addressesSaved=findViewById(R.id.address_saved);

        previousAddress=DBqueries.selectedAddress;

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        mode=getIntent().getIntExtra("MODE",-1);
        if(mode==SELECT_ADDRESS){
            deliverHere.setVisibility(View.VISIBLE);
        }else {
            deliverHere.setVisibility(View.GONE);
        }

        deliverHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(DBqueries.selectedAddress != previousAddress){
                    final int previousAddressIndex=previousAddress;
                    loadingDialog.show();
                    Map<String,Object> updateSelection= new HashMap<>();
                    updateSelection.put("selected_"+String.valueOf(previousAddress+1),false);
                    updateSelection.put("selected_"+String.valueOf(DBqueries.selectedAddress+1),true);

                    previousAddress=DBqueries.selectedAddress;

                    FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("ADDRESSES")
                            .update(updateSelection)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        finish();
                                    }else {
                                        previousAddress=previousAddressIndex;
                                        String error=task.getException().getMessage();
                                        Toast.makeText(MyAddressActivity.this,error,Toast.LENGTH_SHORT).show();
                                    }
                                    loadingDialog.dismiss();
                                }
                            });
                }else {
                    finish();
                }
            }
        });


        addressesAdapter=new AddressAdapter(DBqueries.addressesModelList,mode,loadingDialog);
        addressesAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(addressesAdapter);
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        addNewAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mode != SELECT_ADDRESS){
                    startActivity(new Intent(MyAddressActivity.this,AddAddressActivity.class).putExtra("INTENT","manage"));
                }else {
                    startActivity(new Intent(MyAddressActivity.this,AddAddressActivity.class).putExtra("INTENT","null"));
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        addressesSaved.setText(String.valueOf(DBqueries.addressesModelList.size())+" addresses");

    }

    public static void refreshItem(int deselect, int select){
        addressesAdapter.notifyItemChanged(deselect);
        addressesAdapter.notifyItemChanged(select);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == android.R.id.home){
            if(mode == SELECT_ADDRESS) {
                if (DBqueries.selectedAddress != previousAddress) {
                    DBqueries.addressesModelList.get(DBqueries.selectedAddress).setSelected(false);
                    DBqueries.addressesModelList.get(previousAddress).setSelected(true);
                    DBqueries.selectedAddress = previousAddress;

                }
            }
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(mode == SELECT_ADDRESS) {
            if (DBqueries.selectedAddress != previousAddress) {
                DBqueries.addressesModelList.get(DBqueries.selectedAddress).setSelected(false);
                DBqueries.addressesModelList.get(previousAddress).setSelected(true);
                DBqueries.selectedAddress = previousAddress;

            }
        }
        super.onBackPressed();
    }





//    private RecyclerView myAddressrecyclerView;
//    private Button thanhToanBtn;
//    private static AddressAdapter adapter;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_my_address);
//
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
//        getSupportActionBar().setTitle("Địa chỉ của tôi");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        myAddressrecyclerView = findViewById(R.id.address_recyclerview);
//        thanhToanBtn = findViewById(R.id.delivery_here_btn);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(RecyclerView.VERTICAL);
//        myAddressrecyclerView.setLayoutManager(layoutManager);
//
//        List<AddressesModel> addressesModelList = new ArrayList<>();
//        addressesModelList.add(new AddressesModel("Pham Chi Nghi","Ben Tre",true));
//        addressesModelList.add(new AddressesModel("Le Thi B","Ben Tre",false));
//        addressesModelList.add(new AddressesModel("Nguyen Van A","Ben Tre",false));
//        addressesModelList.add(new AddressesModel("Tran Van C","Ben Tre",false));
//        addressesModelList.add(new AddressesModel("Le Thi B","Ben Tre",false));
//        addressesModelList.add(new AddressesModel("Nguyen Van A","Ben Tre",false));
//        addressesModelList.add(new AddressesModel("Tran Van C","Ben Tre",false));
//        addressesModelList.add(new AddressesModel("Le Thi B","Ben Tre",false));
//        addressesModelList.add(new AddressesModel("Nguyen Van A","Ben Tre",false));
//        addressesModelList.add(new AddressesModel("Tran Van C","Ben Tre",false));
//
//        int mode = getIntent().getIntExtra("MODE", -1);
//        //Set an nut thanh sau khi chon dia chi lai
//        if(mode == SELECT_ADDRESS){
//            thanhToanBtn.setVisibility(View.VISIBLE);
//        }else{
//            thanhToanBtn.setVisibility(View.GONE);
//        }
//        adapter = new AddressAdapter(addressesModelList,mode);
//        myAddressrecyclerView.setAdapter(adapter);
//        ((SimpleItemAnimator)myAddressrecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
//        adapter.notifyDataSetChanged();
//    }
//    //              Ham de luu lai cai item da chon va bo chon
//    public static void refreshItem(int deselect,int select){
//        adapter.notifyItemChanged(deselect);
//        adapter.notifyItemChanged(select);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case android.R.id.home:
//                finish();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}