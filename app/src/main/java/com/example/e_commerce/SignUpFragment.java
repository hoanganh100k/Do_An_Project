package com.example.e_commerce;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignUpFragment() {
        // Required empty public constructor
    }

    private TextView successSignUp;
    private TextView alreadyHaveAnAccount;
    private FrameLayout parentFrameLayout;


    private EditText email;
    private EditText fullName;
    private EditText password;
    private EditText confirmPassword;

    private ImageButton closeBtn;
    private Button signUpBtn;

    private ProgressBar progressBar;
    private FirebaseFirestore firebaseFirestore;

    private FirebaseAuth firebaseAuth;
    private String emailPattern ="[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    public static boolean disableCloseBtn = false;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
       alreadyHaveAnAccount = view.findViewById(R.id.tv_already_have_an_account);

       parentFrameLayout = getActivity().findViewById(R.id.register_framelayout);

       email = view.findViewById(R.id.sign_up_email);
       fullName = view.findViewById(R.id.sign_up_fullname);
       password = view.findViewById(R.id.sign_up_password);
       confirmPassword = view.findViewById(R.id.sign_up_confirm_password);

       successSignUp = view.findViewById(R.id.tv_success);

       closeBtn = view.findViewById(R.id.sign_in_close_btn);
       signUpBtn = view.findViewById(R.id.sign_up_btn);

       progressBar = view.findViewById(R.id.sign_up_progressbar);

       firebaseAuth = FirebaseAuth.getInstance();
       firebaseFirestore = FirebaseFirestore.getInstance();

        if(disableCloseBtn){
            closeBtn.setVisibility(View.GONE);

        }else {
            closeBtn.setVisibility(View.VISIBLE);
        }

       return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        alreadyHaveAnAccount.setOnClickListener((view1) -> {
            setFramegment(new SignInFragment());
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainIntent();
            }
        });

        email.addTextChangedListener(new TextWatcher() {
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
        fullName.addTextChangedListener(new TextWatcher() {
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
        password.addTextChangedListener(new TextWatcher() {
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
        confirmPassword.addTextChangedListener(new TextWatcher() {
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




        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                checkEmailAndPassword();
            }
        });
    }



    private void setFramegment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
    private void checkInputs(){
        if(!TextUtils.isEmpty(email.getText())){
            if(!TextUtils.isEmpty(fullName.getText())){
                if(!TextUtils.isEmpty(password.getText()) && password.length() >= 8){
                    if (!TextUtils.isEmpty(confirmPassword.getText())){
                        signUpBtn.setEnabled(true);
                        signUpBtn.setTextColor(Color.rgb(255,255,255));
                    }else{
                        signUpBtn.setEnabled(false);
                        signUpBtn.setTextColor(Color.argb(50,255,255,255));
                    }
                }else {
                    signUpBtn.setEnabled(false);
                    signUpBtn.setTextColor(Color.argb(50,255,255,255));
                }
            }else {
                signUpBtn.setEnabled(false);
                signUpBtn.setTextColor(Color.argb(50,255,255,255));
            }
        }else {
            signUpBtn.setEnabled(false);
            signUpBtn.setTextColor(Color.argb(50,255,255,255));
        }
    }
    private void checkEmailAndPassword(){

        Drawable customErrorIcon = getResources().getDrawable(R.mipmap.custom_error_icon);
        customErrorIcon.setBounds(0,0,customErrorIcon.getIntrinsicWidth(),customErrorIcon.getIntrinsicHeight());


        if (email.getText().toString().matches(emailPattern)){
            if (password.getText().toString().equals(confirmPassword.getText().toString())){

                progressBar.setVisibility(View.VISIBLE);
                signUpBtn.setEnabled(false);
                signUpBtn.setTextColor(Color.argb(50,255,255,255));

                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                    Map<Object,String> userdata = new HashMap<>();
                                    userdata.put("fullname",fullName.getText().toString());
                                    userdata.put("email",email.getText().toString());
                                    userdata.put("profile","");

                                    firebaseFirestore.collection("USERS").document(firebaseAuth.getUid())
                                        .set(userdata)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){

                                                    CollectionReference userDataReference=firebaseFirestore.collection("USERS").document(firebaseAuth.getUid()).collection("USER_DATA");

                                                    Map<String,Object> wishlistMap= new HashMap<>();
                                                    wishlistMap.put("list_size", (long) 0);

                                                    Map<String,Object> ratingsMap= new HashMap<>();
                                                    ratingsMap.put("list_size", (long) 0);

                                                    Map<String,Object> cartMap= new HashMap<>();
                                                    cartMap.put("list_size", (long) 0);

                                                    Map<String,Object> AddressesMap= new HashMap<>();
                                                    AddressesMap.put("list_size", (long) 0);

                                                    Map<String,Object> notificationMap= new HashMap<>();
                                                    notificationMap.put("list_size", (long) 0);

                                                    List<String> documentNames = new ArrayList<>();
                                                    documentNames.add("WISHLIST");
                                                    documentNames.add("RATINGS");
                                                    documentNames.add("CART");
                                                    documentNames.add("ADDRESSES");
                                                    documentNames.add("NOTIFICATIONS");

                                                    final List<Map<String,Object>> documentFields = new ArrayList<>();
                                                    documentFields.add(wishlistMap);
                                                    documentFields.add(ratingsMap);
                                                    documentFields.add(cartMap);
                                                    documentFields.add(AddressesMap);
                                                    documentFields.add(notificationMap);

                                                    for (int x=0; x<documentNames.size() ;x++){
                                                        final int finalX = x;
                                                        userDataReference.document(documentNames.get(x))
                                                                .set(documentFields.get(x))
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if(task.isSuccessful()){
                                                                            if(finalX == documentFields.size()-1) {
                                                                                if (disableCloseBtn) {
                                                                                    disableCloseBtn = false;
                                                                                } else {
                                                                                    startActivity(new Intent(getActivity(), MainActivity.class));
                                                                                }
                                                                                getActivity().finish();
                                                                            }
                                                                        }else {
                                                                            progressBar.setVisibility(View.INVISIBLE);
                                                                            signUpBtn.setEnabled(true);
                                                                            signUpBtn.setTextColor(Color.rgb(255,255,255));
                                                                            String error=task.getException().getMessage();
                                                                            Toast.makeText(getActivity(), error,Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                }
                                                else {
                                                    String error = task.getException().getMessage();
                                                    Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    successSignUp.setVisibility(View.VISIBLE);
                                    successSignUp.setText("Đăng kí thành công");
                                    progressBar.setVisibility(View.GONE);
                                }
                                else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    signUpBtn.setEnabled(true);
                                    signUpBtn.setTextColor(Color.rgb(255,255,255));
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }else {
                confirmPassword.setError("Mật khẩu không khớp!",customErrorIcon);
            }
        }else {
            email.setError("Email không hợp lệ!",customErrorIcon);
        }
    }

    private void mainIntent(){
        Intent mainIntent = new Intent(getActivity(),MainActivity.class);
        startActivity(mainIntent);
        getActivity().finish();
    }
}