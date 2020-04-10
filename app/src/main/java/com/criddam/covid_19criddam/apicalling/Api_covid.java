package com.criddam.covid_19criddam.apicalling;

import com.criddam.covid_19criddam.model.Getdata_doctor;
import com.criddam.covid_19criddam.model.Post;
import com.criddam.covid_19criddam.model.Post_supply;
import com.criddam.covid_19criddam.model.Responseclass;
import com.criddam.covid_19criddam.model.Signin;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api_covid {


    @POST("what_u_need")
    Call<Post> newPost(@Body Post post);

    @FormUrlEncoded
    @POST("what_u_need")
    Call<ResponseBody> createPost(

            @Field("usertype") String usertype,
            @Field("fullname") String fullname,
            @Field("mobile") String mobile,
            @Field("email") String email,
            @Field("what_u_need") String what_u_need,
            @Field("how_soon_do_u_need_it") String how_soon_do_u_need_it,
            @Field("password") String password,
            @Field("hospital") String hospital,
            @Field("location") String location

    );



    @FormUrlEncoded
    @POST("what_u_need")
    Call<ResponseBody> createPost_supply(
            @Field("usertype") String usertype,
            @Field("fullname") String fullname,
            @Field("mobile") String mobile,
            @Field("email") String email,
            @Field("what_u_supply") String what_u_supply,
            @Field("how_soon_can_u_supply") String how_soon_can_u_supply,
            @Field("password") String password,
            @Field("location") String location,
            @Field("what_u_supply_other") String what_u_supply_other


    );
    @FormUrlEncoded
    @POST("clogin")
    Call<ResponseBody> createPost_Signin(
            @Field("mobile") String mobile,
            @Field("password") String password
    );
    @FormUrlEncoded
    @POST("what_u_need")
    Call<ResponseBody> createPost_Signinsuccess(
            @Field("what_u_need") String what_u_need,
            @Field("how_soon_do_u_need_it") String how_soon_do_u_need_it
    );

    @GET("{mobile_no}")
    Call<Responseclass> getData(@Path("mobile_no")String mobile_no);



}
