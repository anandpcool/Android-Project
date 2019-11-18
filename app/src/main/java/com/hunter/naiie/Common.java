package com.hunter.naiie;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.hunter.naiie.model.ServiceList;

import java.util.ArrayList;

public class Common {

    public static final ArrayList<ServiceList> listArrayList=new ArrayList<>();

    public static final String BASE_URL="https://vasugyanjoti.org/client/naiie/";


    /*
     * Checks if WiFi or 3G is enabled or not. server
     */
    public static boolean isInternetAvailable(Context context) {
        return isWiFiAvailable(context) || isMobileDateAvailable(context);
    }

    /**
     * Checks if the mobile data is enabled on user's device
     */
    private static boolean isMobileDateAvailable(Context context) {

        // ConnectivityManager is used to check available 3G network.
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        // 3G network is available.
        return networkInfo != null
                && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * Checks if the WiFi is enabled on user's device
     */
    private static boolean isWiFiAvailable(Context context) {

        // ConnectivityManager is used to check available wifi network.
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network_info = connectivityManager.getActiveNetworkInfo();
        // Wifi network is available.
        return network_info != null
                && network_info.getType() == ConnectivityManager.TYPE_WIFI;
    }

}
