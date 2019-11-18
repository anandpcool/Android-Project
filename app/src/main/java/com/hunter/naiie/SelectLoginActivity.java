package com.hunter.naiie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.hunter.naiie.barber.ActivitySignin;
import com.hunter.naiie.barber.WelcomeActivity;

public class SelectLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_login);

        //checkLogin();

        findViewById(R.id.b_LoginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barberLogin();
            }
        });

    }

    public void barberLogin() {

        startActivity(new Intent(this, ActivitySignin.class)
        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }

    public void userLogin(View view) {

    }
    public void checkLogin() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(this, WelcomeActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

        }
    }
}
