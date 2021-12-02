package com.example.e_commerce;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        SystemClock.sleep(3000);

        Intent loginIntent = new Intent(SplashActivity.this,RegisterActivity.class);
        startActivity(loginIntent);

//        Intent loginIntent = new Intent(SplashActivity.this,LoginActivity.class);
//        loginIntent = new Intent(SplashActivity.this,MainActivity.class);





//        Intent loginIntent = new Intent(SplashActivity.this,RegisterActivity.class);

//        Intent loginIntent = new Intent(SplashActivity.this, MainActivity.class);




        startActivity(loginIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if(currentUser == null){
            Intent registerIntent = new Intent(SplashActivity.this,RegisterActivity.class);
            startActivity(registerIntent);
            fileList();
        }else {
            Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(mainIntent);
            fileList();

//            mainFullName.setText(currentUser.);
//            mainEmailAddress.setText(currentUser.getEmail().toString());



        }
    }
}


