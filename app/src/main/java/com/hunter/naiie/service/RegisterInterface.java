package com.hunter.naiie.service;

import com.hunter.naiie.model.BarberLoginResult;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterInterface {

    String REGIURL = "https://vasugyanjoti.org/client/naiie/";

    @FormUrlEncoded
    @POST("registerbarbar.php")
    Call<String> getUserRegi(
            @Field("shopnm") String shopnm,
            @Field("ownm") String ownm,
            @Field("mono") String mono,
            @Field("email") String email,
            @Field("pswd") String pswd,
            @Field("addr") String addr,
            @Field("citynm") String citynm,
            @Field("pincode") String pincode,
            @Field("pserv") String pserv,
            @Field("exname") String exname,
            @Field("rdate") String rdate);

    //the signin call
    @FormUrlEncoded
    @POST("barbarlogin.php")
    Call<String> barberLogin(
            @Field("contact") String contact,
            @Field("pass") String pass

    );


}
