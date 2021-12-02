package com.example.e_commerce;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class RegisterActivity extends AppCompatActivity {


    private FrameLayout frameLayout;
    public static boolean onResetPasswordFragment=false;
    public static boolean setsignUpFragment=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        frameLayout = findViewById(R.id.register_framelayout);
        //setDefaultFragment(new SignInFragment());
        if(setsignUpFragment){
            setsignUpFragment=false;
            setDefaultFragment(new SignUpFragment());
        }else {
            setDefaultFragment(new SignInFragment());
        }
    }
    //Tuy` Chinh phim quay lai
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //  quay trở về người dùng di chuyển ngược lại lịch sử của các màn hình mà họ đã truy cập trước đó
//        if (keyCode == KeyEvent.KEYCODE_BACK){
//            if (onResetPasswordFragment){
//                onResetPasswordFragment = false;
//                setFragment(new SignInFragment());
//                return false;
//            }
//        }
        if(keyCode==KeyEvent.KEYCODE_BACK){
            SignInFragment.disableCloseBtn=false;
            SignUpFragment.disableCloseBtn=false;
            if(onResetPasswordFragment){
                onResetPasswordFragment=false;
                setFragment(new SignInFragment());
                return false;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    private void setDefaultFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slideout_from_right);
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }

}