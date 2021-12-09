package com.example.e_commerce;

import android.content.Intent;
import android.graphics.Color;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignInFragment extends Fragment {


    private boolean onResetPasswordFragment;

    public SignInFragment() {
        // Required empty public constructor
    }

    private TextView dontHaveAnAccount;
    private FrameLayout parentFrameLayout;


    private EditText email;
    private EditText password;

    private ProgressBar progressBar;

    private ImageButton closeBtn;
    private Button signInBtn;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;


    private String emailPattern ="[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";


    private TextView forgotPassword;

    public static boolean disableCloseBtn = false;


    public static SignInFragment newInstance(String param1, String param2) {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        dontHaveAnAccount = view.findViewById(R.id.tv_new_user_sign_up);

        parentFrameLayout = getActivity().findViewById(R.id.register_framelayout);

        email = view.findViewById(R.id.sign_in_email);
        password = view.findViewById(R.id.sign_in_password);

        progressBar = view.findViewById(R.id.sign_in_progressbar);

        closeBtn = view.findViewById(R.id.sign_in_close_btn);
        signInBtn = view.findViewById(R.id.sign_in_btn);

        firebaseAuth = FirebaseAuth.getInstance();

        parentFrameLayout = getActivity().findViewById(R.id.register_framelayout);
       // dontHaveAnAccount = view.findViewById(R.id.tv_new_user_sign_up);

        forgotPassword = view.findViewById(R.id.sign_in_forgot_password);

        if(disableCloseBtn){
            closeBtn.setVisibility(View.GONE);

        }else {
            closeBtn.setVisibility(View.VISIBLE);
        }



        return view;
    }
//setFramegment
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFramegment(new SignUpFragment());
            }
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
                checkInput();
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
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailAndPassword();
            }
        });
        // chuyen qua quen mat khau

//        dontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View view) {
//                    checkEmailAndPassword();
//                }
//            });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onResetPasswordFragment = true;
                    setFramegment(new ResetPasswordFragment());
                }
            });
        }

        private void checkInput() {
            if(!TextUtils.isEmpty(email.getText())){
                if(!TextUtils.isEmpty(password.getText())){
                    signInBtn.setEnabled(true);
                    signInBtn.setTextColor(Color.rgb(255,255,255));
                }else {
                    signInBtn.setEnabled(false);
                    signInBtn.setTextColor(Color.argb(50,255,255,255));
                }
            }else {
                signInBtn.setEnabled(false);
                signInBtn.setTextColor(Color.argb(50,255,255,255));
            }
        }

        private void checkEmailAndPassword(){
            if (email.getText().toString().matches(emailPattern)){
                if (password.length() >= 8){

                    progressBar.setVisibility(View.VISIBLE);
                    signInBtn.setEnabled(false);
                    signInBtn.setTextColor(Color.argb(50,255,255,255));
                    progressBar.setVisibility(View.INVISIBLE);
                    signInBtn.setEnabled(true);
                    signInBtn.setTextColor(Color.rgb(255,255,255));
                    mainIntent();
//                    firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
//                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    if (task.isSuccessful()){
//                                        mainIntent();
//                                    }else {
//                                        progressBar.setVisibility(View.INVISIBLE);
//                                        signInBtn.setEnabled(true);
//                                        signInBtn.setTextColor(Color.rgb(255,255,255));
//                                        String error = task.getException().getMessage();
//                                        Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
//                                    }
//
//                                }
//                            });

                }else {
                    Toast.makeText(getActivity(),"Incorrect email or password!",Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getActivity(), "Email hoặc mật khẩu không chính xác!",Toast.LENGTH_SHORT).show();
            }

        }
        private void mainIntent(){
            Intent intent = new Intent(getActivity(),MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }

        private void setFramegment(Fragment fragment) {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
            fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
            fragmentTransaction.commit();
        }
    }