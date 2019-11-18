package com.hunter.naiie.barber;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.hunter.naiie.Common;
import com.hunter.naiie.R;
import com.hunter.naiie.SelectLoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class ActivitySignup extends AppCompatActivity {

    EditText shopName;
    EditText brName;
    EditText emailAdd;
    EditText contactNo;
    EditText password;
    EditText cnPassword;
    EditText address;
    EditText bCityName;
    EditText bPincode;
    RadioGroup radioGroup;
    RadioButton radioSexButton;
    Spinner spinner;
    CheckBox checkBox;
    AwesomeValidation awesomeValidation;
    String bContact;
    AlertDialog loading;
    List<String> exname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        exname = new ArrayList<>();

        findAllUIId();

        addDataInSpinner();

    }

    private void addDataInSpinner() {

        // check connection
        checkInternet();

/*  for static data
    exname.add(getResources().getString(R.string.select));
        exname.add(getResources().getString(R.string.Anand));
        exname.add(getResources().getString(R.string.Dinesh));
        exname.add(getResources().getString(R.string.Swapnil));*/


    }

    // call service for executive name

    private void callExecutiveService() {

        showDialog();

        StringRequest request = new StringRequest(Common.BASE_URL + "bselectexicutive.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();

                        JSONObject object = null;
                        try {
                            object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("result");
                            Log.e("Result", "" + jsonArray);

                            //parse json data
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                exname.add(jsonObject.getString("exnm"));

                            }
                            //set data to spinner
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ActivitySignup.this, android.R.layout.simple_spinner_dropdown_item, exname);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(arrayAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);


    }

    // find all UI id
    private void findAllUIId() {
        shopName = findViewById(R.id.br_shop_name);
        brName = findViewById(R.id.br_name);
        contactNo = findViewById(R.id.mob_no);
        emailAdd = findViewById(R.id.email_add);
        password = findViewById(R.id.pass);
        cnPassword = findViewById(R.id.c_pass);
        address = findViewById(R.id.b_address);
        bCityName=findViewById(R.id.b_city_name);
        bPincode=findViewById(R.id.b_pincode);
        radioGroup = findViewById(R.id.selectsex);
        spinner = findViewById(R.id.select_executive_name);
        checkBox = findViewById(R.id.chkbox);


        // for check validation
        awesomeValidation.addValidation(this, R.id.br_shop_name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.br_name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.email_add, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        //awesomeValidation.addValidation(this, R.id.mob_no, String.valueOf(Patterns.PHONE.matcher(bContact).matches()), R.string.mobileerror);


    }

    // register the account on button click
    public void registerAccount(View view) {

        String sName = shopName.getText().toString().trim();
        String bName = brName.getText().toString().trim();
        bContact = contactNo.getText().toString().trim();
        String bEmail = emailAdd.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String cpass = cnPassword.getText().toString().trim();
        String bAddress = address.getText().toString().trim();
        String cityname=bCityName.getText().toString().trim();
        String pincode=bPincode.getText().toString().trim();
        String exName = spinner.getSelectedItem().toString().trim();


        // get selected radio button from radioGroup
        int selectedId = radioGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioSexButton = findViewById(selectedId);
        String radiotext = radioSexButton.getText().toString().trim();
//Toast.makeText(this,radioSexButton.getText(), Toast.LENGTH_SHORT).show();

        if (awesomeValidation.validate()) {
            if (bContact.isEmpty()) {
                contactNo.setError(getResources().getString(R.string.mobileerror));
                contactNo.requestFocus();
            } else if (bContact.length() < 10) {
                contactNo.setError("Valid Number Required");
            } else if (pass.isEmpty()) {
                password.setError("Invalid");
                password.requestFocus();

            } else if (cpass.isEmpty()) {
                cnPassword.setError("Invalid");
                cnPassword.requestFocus();
            } else if (bAddress.isEmpty()) {
                address.setError("Invalid");
                address.requestFocus();
            }else if (cityname.isEmpty())
            {
                bCityName.setError("Invalid");
                bCityName.requestFocus();
            }
            else if (pincode.isEmpty())
            {
                bPincode.setError("Invalid");
                bPincode.requestFocus();
            }
            if (pincode.length()<6)
            {
                bPincode.setError("Enter valid pincode");
                bPincode.setText("");
                bPincode.requestFocus();
            }

            else if (exName.equals("Select Executive Name")) {
                Toast.makeText(this, "Please select Name", Toast.LENGTH_SHORT).show();
            } else if (pass.equals(cpass)) {
                if (checkBox.isChecked()) {
                    //Toast.makeText(this, "Registration Successfully", Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(this, VerifyPhoneActivity.class);
                    intent.putExtra("sName", sName);
                    intent.putExtra("bName", bName);
                    intent.putExtra("bContact", bContact);
                    intent.putExtra("bEmail", bEmail);
                    intent.putExtra("pass", pass);
                    intent.putExtra("bAddress", bAddress);
                    intent.putExtra("bcityname",cityname);
                    intent.putExtra("bpincode",pincode);
                    intent.putExtra("exName", exName);
                    intent.putExtra("radiotext", radiotext);
                    startActivity(intent);
                    finish();


                } else {
                    Toast.makeText(this, "Please select T&C", Toast.LENGTH_SHORT).show();
                }


            } else {

                cnPassword.setError("Password not match");
                password.setText("");
                cnPassword.setText("");
                password.requestFocus();
                Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT).show();
            }

        }


    }

    public void signIn(View view) {

        startActivity(new Intent(this, ActivitySignin.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //View view = getLayoutInflater().inflate(R.layout.progress);
        builder.setView(R.layout.loading_dialog);
        loading = builder.create();
        loading.setCancelable(false);
        builder.setCancelable(false);
        loading.show();

    }
// check connection
    private void checkInternet() {
        if (Common.isInternetAvailable(getApplicationContext())) {
            // call services to show executive name
            callExecutiveService();

        } else {
            Toast.makeText(this, "Please Check Internet connection", Toast.LENGTH_SHORT).show();

        }
    }

    public void closeSignActivity(View view) {

        startActivity(new Intent(this, SelectLoginActivity.class));
        finish();
    }
}
