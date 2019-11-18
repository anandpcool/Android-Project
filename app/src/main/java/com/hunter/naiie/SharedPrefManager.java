package com.hunter.naiie;

import android.content.Context;
import android.content.SharedPreferences;

import com.hunter.naiie.model.BarberLoginResult;

public class SharedPrefManager {

    private static SharedPreferences sharedPreferences;
    Context context;

    private static final String SHARED_PREF_NAME = "barberlogin";

    private static final String BARBER_name = "barbername";
    private static final String BARBER_ONAME = "barberonm";
    private static final String BARBER_CONTACT = "contactno";
    private static final String BARBER_EMAIL = "barberemail";
    private static final String BARBER_SERVICE = "barberservice";
    private static final String BARBER_REGISTER_DATE = "registerdate";
    private static final String BARBER_STATUS = "barberstatus";


    /*private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context)
    {
        if (mInstance==null)
        {
            mInstance=new SharedPrefManager(context);
        }
        return mInstance;
    }*/


    public SharedPrefManager(Context context) {
        this.context=context;
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);

    }

    public void writeLoginStatus(boolean  status)
    {
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putBoolean("status",status);
        editor.commit();
    }

    public boolean readLoginStatus()
    {
        boolean status = false;
        status = sharedPreferences.getBoolean("status",false);

        return status;
    }

    public String readContactNumber()
    {
        return sharedPreferences.getString("contact",null);
    }



    public boolean logout() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

}
