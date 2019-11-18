package com.hunter.naiie.barber;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;

import com.hunter.naiie.R;
import com.hunter.naiie.SelectLoginActivity;
import com.hunter.naiie.SharedPrefManager;
import com.hunter.naiie.model.BarberLoginResult;
import com.hunter.naiie.service.RegisterInterface;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivitySignin extends AppCompatActivity {

    EditText contact, password;
    String b_contact, b_pass;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        contact = findViewById(R.id.editTextCon);
        password = findViewById(R.id.editTextPass);


        sharedPrefManager = new SharedPrefManager(getApplicationContext());
        sessionData();

        findViewById(R.id.cirLoginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBarber();
            }
        });
    }

    public void loginBarber() {

        b_contact = contact.getText().toString().trim();
        b_pass = password.getText().toString();


        if (b_contact.isEmpty()) {
            contact.setError("Invalid");
            contact.requestFocus();
        } else if (b_pass.isEmpty()) {
            password.setError("Invalid");
            password.requestFocus();
        } else {
//defining a progress dialog to show while signing up
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("please wait...");
            progressDialog.show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(RegisterInterface.REGIURL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();

            RegisterInterface registerInterface = retrofit.create(RegisterInterface.class);
            Call<String> api = registerInterface.barberLogin(b_contact, b_pass);
            api.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.i("Responsestring", response.body().toString());

                    progressDialog.dismiss();

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
    }

    private void parseRegData(String jsonresponse) {

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonresponse);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonObject.optString("status").equals("true")) {

            Toast.makeText(this, "Login Successfully!", Toast.LENGTH_SHORT).show();

            //verification successful we will start the profile activity
            Intent intent = new Intent(this, WelcomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            sharedPrefManager.writeLoginStatus(true);
            this.finish();
        } else {

            Toast.makeText(this, "Invalid login details", Toast.LENGTH_SHORT).show();
            contact.setText("");
            password.setText("");
            contact.requestFocus();
            password.requestFocus();

        }


    }

    public void viewRegisterClicked(View view) {

        startActivity(new Intent(this, ActivitySignup.class));

    }

    private void sessionData() {
        boolean status = sharedPrefManager.readLoginStatus();
        Log.e("Status", "" + status);
        if (sharedPrefManager.readLoginStatus()) {
            startActivity(new Intent(this, WelcomeActivity.class));
            finish();
        }
    }

    public void closeActivity(View view) {

        startActivity(new Intent(this, SelectLoginActivity.class));
        finish();
    }
}
