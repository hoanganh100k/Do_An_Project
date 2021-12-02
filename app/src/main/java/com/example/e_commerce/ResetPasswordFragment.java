package com.example.e_commerce;


import android.graphics.Color;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.content.Loader;


import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResetPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResetPasswordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    private EditText registeredEmail;
    private Button resetPasswordBnt;
    private TextView goBack;

    private FrameLayout parentFrameLayout;

    private ViewGroup emailIconContainer;
    private ImageView emailIcon;
    private TextView emailIconText;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResetPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResetPasswordFragment newInstance(String param1, String param2) {
        ResetPasswordFragment fragment = new ResetPasswordFragment();
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
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);

        registeredEmail = view.findViewById(R.id.forgot_password_email);
        resetPasswordBnt = view.findViewById(R.id.reset_password_btn);
        goBack = view.findViewById(R.id.tv_forgot_password_go_back);

        emailIconContainer = view.findViewById(R.id.forgot_password_email_icon_container);
        emailIcon = view.findViewById(R.id.forgot_password_email_icon);
        emailIconText = view.findViewById(R.id.forgot_password_email_icon_text);
        progressBar = view.findViewById(R.id.forgot_password_progressbar);

        parentFrameLayout = getActivity().findViewById(R.id.register_framelayout);

        firebaseAuth = FirebaseAuth.getInstance();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registeredEmail.addTextChangedListener(new TextWatcher() {
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

        resetPasswordBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TransitionManager.beginDelayedTransition(emailIconContainer);
                emailIconText.setVisibility(View.GONE);

                TransitionManager.beginDelayedTransition(emailIconContainer);
                emailIcon.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                resetPasswordBnt.setEnabled(false);
                resetPasswordBnt.setTextColor(Color.argb(50,255,255,255));

                firebaseAuth.sendPasswordResetEmail(registeredEmail.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                ScaleAnimation scaleAnimation = new ScaleAnimation(1,0,1,0,emailIcon.getWidth()/2,emailIcon.getHeight()/2);
                                scaleAnimation.setDuration(100);
                                scaleAnimation.setInterpolator(new AccelerateInterpolator());
                                scaleAnimation.setRepeatMode(Animation.REVERSE);
                                scaleAnimation.setRepeatCount(1);

                                scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        emailIconText.setVisibility(View.VISIBLE);
                                        emailIconText.setText("Khôi phục Email của bạn đã hoàn tất! Vui lòng kiểm tra Email.");
                                        emailIconText.setTextColor(getResources().getColor(R.color.green));
                                        TransitionManager.beginDelayedTransition(emailIconContainer);
                                        emailIcon.setVisibility(View.VISIBLE);
                                        registeredEmail.setText("");
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {
                                        emailIcon.setImageResource(R.mipmap.ic_mail_outline_24px);
                                    }
                                });
                                emailIcon.startAnimation(scaleAnimation);
                            }else {
                                String error = ("Sai Email!Vui lòng kiểm tra lại Email");

                                resetPasswordBnt.setEnabled(true);
                                resetPasswordBnt.setTextColor(Color.rgb(255,255,255));

                                emailIconText.setText(error);
                                emailIconText.setTextColor(getResources().getColor(R.color.colorPrimary));
                                TransitionManager.beginDelayedTransition(emailIconContainer);
                                emailIconText.setVisibility(View.VISIBLE);
                            }
                            progressBar.setVisibility(View.GONE);

                        }
                    });


            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFramegment(new SignInFragment());
            }
        });
    }
    private void checkInputs() {
        if(TextUtils.isEmpty(registeredEmail.getText())){
            resetPasswordBnt.setEnabled(false);
            resetPasswordBnt.setTextColor(Color.argb(50,255,255,255));
        }else {
            resetPasswordBnt.setEnabled(true);
            resetPasswordBnt.setTextColor(Color.rgb(255,255,255));
        }
    }
    private void setFramegment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}
