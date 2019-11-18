package com.hunter.naiie.barber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hunter.naiie.Common;
import com.hunter.naiie.R;
import com.hunter.naiie.service.RegisterInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity {


    String sName, bName, bContact, bEmail, pass, bAddress,bcityname,bpincode, exName, radiotext;

    EditText enterOtp;
    Button verifyOtp;
    //It is the verification id that will be sent to the user
    private String mVerificationId;
    //firebase auth object
    private FirebaseAuth mAuth;

    ProgressBar progressBar;

    String sysdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);


        //initializing objects
        mAuth = FirebaseAuth.getInstance();
        enterOtp = findViewById(R.id.enter_otp);
        progressBar = findViewById(R.id.progressbar);

//getting mobile number from the previous activity
        //and sending the verification code to the number
        sName = getIntent().getStringExtra("sName");
        bName = getIntent().getStringExtra("bName");
        bContact = getIntent().getStringExtra("bContact");
        bEmail = getIntent().getStringExtra("bEmail");
        pass = getIntent().getStringExtra("pass");
        bAddress = getIntent().getStringExtra("bAddress");
        bcityname=getIntent().getStringExtra("bcityname");
        bpincode=getIntent().getStringExtra("bpincode");
        exName = getIntent().getStringExtra("exName");
        radiotext = getIntent().getStringExtra("radiotext");


        DateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyy");
        Date date = new Date();
        sysdate = simpleDateFormat.format(date);

        Log.e("Date", sysdate);

        if (Common.isInternetAvailable(getApplicationContext())) {
            // call services to show executive name
           sendVerificationCode(bContact);

        } else {
            Toast.makeText(this, "Please Check Internet connection", Toast.LENGTH_SHORT).show();

        }

        progressBar.setVisibility(View.GONE);


        //if the automatic sms detection did not work, user can also enter the code manually
        //so adding a click listener to the button
        findViewById(R.id.verifyOtp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = enterOtp.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    enterOtp.setError("Enter valid code");
                    enterOtp.requestFocus();
                    return;
                }

                //verifying the code entered manually
                verifyVerificationCode(code);

            }
        });


    }


    //the method is sending verification code
    //the country id is concatenated
    //you can take the country id as user input as well
    private void sendVerificationCode(String bContact) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + bContact,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            progressBar.setVisibility(View.GONE);
            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                enterOtp.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            progressBar.setVisibility(View.GONE);

            Toast.makeText(VerifyPhoneActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            progressBar.setVisibility(View.GONE);
            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };

    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            checkInternet();


                        } else {

                            //verification unsuccessful.. display an error message
                            Toast.makeText(VerifyPhoneActivity.this, "Invalid Otp", Toast.LENGTH_SHORT).show();


                        }
                    }
                });
    }

    // check connection
    private void checkInternet() {
        if (Common.isInternetAvailable(getApplicationContext())) {
            // call services to show executive name
            saveRegistrationDetails();

        } else {
            Toast.makeText(this, "Please Check Internet connection", Toast.LENGTH_SHORT).show();

        }
    }

// save barber data to database by retrofit2
    private void saveRegistrationDetails() {
        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing Up...");
        progressDialog.show();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RegisterInterface.REGIURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        RegisterInterface registerInterface = retrofit.create(RegisterInterface.class);
        Call<String> api = registerInterface.getUserRegi(sName, bName, bContact, bEmail, pass, bAddress,bcityname,bpincode, radiotext, exName, sysdate);

        api.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("Responsestring", response.body());

                progressDialog.dismiss();
                //Toast.makeText()

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());

                        String jsonresponse = response.body().toString();
                        parseRegData(jsonresponse);

                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }
// parse jsondata after response
    private void parseRegData(String jsonresponse) {

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonresponse);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonObject.optString("status").equals("true")) {

            Toast.makeText(this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
            //verification successful we will start the profile activity
            Intent intent = new Intent(VerifyPhoneActivity.this, WelcomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            this.finish();
        } else {

            try {
                Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
