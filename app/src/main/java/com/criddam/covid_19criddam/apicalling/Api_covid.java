package com.criddam.covid_19criddam.apicalling;

import com.criddam.covid_19criddam.model.Post;
import com.criddam.covid_19criddam.model.Post_supply;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api_covid {


    @POST("what_u_need")
    Call<Post> newPost(@Body Post post);

    @FormUrlEncoded
    @POST("what_u_need")
    Call<Post> createPost(

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
    Call<Post_supply> createPost_supply(
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

}
