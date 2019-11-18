package com.hunter.naiie;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //checkLogin();



        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                // This method will be executed once the timer is over
                Intent i = new Intent(SplashActivity.this, SelectLoginActivity.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }

}
