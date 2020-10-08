package com.anm.bslndmag.API;

import com.anm.bslndmag.LoginFreshDevice;
import com.anm.bslndmag.Model.ANMDetailsRequest;
import com.anm.bslndmag.Model.AddMotherRequest;
import com.anm.bslndmag.Model.AddmotherResponse;
import com.anm.bslndmag.Model.AuthResponse;
import com.anm.bslndmag.Model.DueVaccineListRequest;
import com.anm.bslndmag.Model.DueVaccineResponse;
import com.anm.bslndmag.Model.ForgotPinResponse;
import com.anm.bslndmag.Model.HomeRequest;
import com.anm.bslndmag.Model.HomeResponse;
import com.anm.bslndmag.Model.LoginRequest;
import com.anm.bslndmag.Model.LoginResponse;
import com.anm.bslndmag.Model.LoginwithPinRequest;
import com.anm.bslndmag.Model.MagazineResponse;
import com.anm.bslndmag.Model.OfflinemotherChildResponse;
import com.anm.bslndmag.Model.RegisterRequest;
import com.anm.bslndmag.Model.RequestANMMotherChild;
import com.anm.bslndmag.Model.ResponseData;
import com.anm.bslndmag.Model.SearchChildRequest;
import com.anm.bslndmag.Model.SearchChildResponse;
import com.anm.bslndmag.Model.SearchMotherRequest;
import com.anm.bslndmag.Model.SearchMotherResponse;
import com.anm.bslndmag.Model.SubscribePlanRequest;
import com.anm.bslndmag.Model.SubscribePlanResponse;
import com.anm.bslndmag.Model.SubscriptionPlansRequest;
import com.anm.bslndmag.Model.SubscriptionPlansResponse;
import com.anm.bslndmag.Model.UpdateChildVaccineRequest;
import com.anm.bslndmag.Model.VaccinationlistResponse;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PostService {
    @GET("services/get-request-token")
    retrofit2.Call<AuthResponse> getAuthToken();

//    @Headers({"Accept:application/json","User-Agent:android"})
//    @FormUrlEncoded
//    @POST("user/logout")
//    retrofit2.Call<AuthResponse> Logout (
//            @Header("Cookie") String sessionIdAndToken,
//            @Header("Auth-Token") String authorization,
//            @Field("_token") String token
//    );
//,"Accept:application/json","User-Agent:android"
    @Headers({
            "Client-Service:frontend-client",
            "Auth-Key:authkey",
            "Content-Type:application/json",
            "Accept:application/json; charset=UTF-8",
            "User-Agent:android"})

    @POST("api/check-mobile")
    retrofit2.Call<LoginResponse> LoginAuth(@Body LoginRequest body
//            @Header("Cookie") String sessionIdAndToken,
//            @Field("_token") String token,
//            @Field("mobile") String mobile,
//            @Field("code") String password
//            @Field("device_token") String device_token
    );



    @POST("api/subscription-check")
    retrofit2.Call<SubscribePlanResponse> subscribePlan(@Body SubscribePlanRequest body,
//            @Header("Cookie") String sessionIdAndToken,
            @Header("Authorization") String authorization
//            @Field("mobile") String mobile,
//            @Field("code") String password
//            @Field("device_token") String device_token
                                                        );



    @POST("api/magazine")
    retrofit2.Call<MagazineResponse> Magazine(@Body SubscribePlanRequest body,
//            @Header("Cookie") String sessionIdAndToken,
                                              @Header("Authorization") String authorization
//            @Field("mobile") String mobile,
//            @Field("code") String password
//            @Field("device_token") String device_token
    );


    @Headers({
            "Client-Service:frontend-client",
            "Auth-Key:authkey",
            "Content-Type:application/json",
            "Accept:application/json; charset=UTF-8",
            "User-Agent:android"})

    @POST("api/plans")
    retrofit2.Call<SubscriptionPlansResponse> getSubscribePlan(@Body SubscriptionPlansRequest body,
//            @Header("Cookie") String sessionIdAndToken,
            @Header("Authorization") String token
//            @Field("mobile") String mobile,
//            @Field("code") String password
//            @Field("device_token") String device_token
    );

    @Headers({
            "Client-Service:frontend-client",
            "Auth-Key:authkey",
            "Content-Type:application/json",
            "Accept:application/json; charset=UTF-8",
            "User-Agent:android"})

    @POST("api/register-user")
    retrofit2.Call<LoginResponse> RegisterUser(@Body RegisterRequest body
//            @Header("Cookie") String sessionIdAndToken,
//            @Field("_token") String token,
//            @Field("mobile") String mobile,
//            @Field("code") String password
//            @Field("device_token") String device_token
    );

    @Headers({
            "Client-Service:frontend-client",
            "Auth-Key:authkey",
            "Content-Type:application/json",
            "Accept:application/json; charset=UTF-8",
            "User-Agent:android"})

    @POST("api/login-user")
    retrofit2.Call<LoginResponse> LoginFreshdevice(@Body LoginwithPinRequest body
//            @Header("Cookie") String sessionIdAndToken,
//            @Field("_token") String token,
//            @Field("mobile") String mobile,
//            @Field("code") String password
//            @Field("device_token") String device_token
    );



    @Headers({
            "Client-Service:frontend-client",
            "Auth-Key:authkey",
            "Content-Type:application/json",
            "Accept:application/json; charset=UTF-8",
            "User-Agent:android"})

    @POST("api/verify-otp")
    retrofit2.Call<LoginResponse> VerifyOTP(@Body LoginwithPinRequest body
//            @Header("Cookie") String sessionIdAndToken,
//            @Field("_token") String token,
//            @Field("mobile") String mobile,
//            @Field("code") String password
//            @Field("device_token") String device_token
    );



    @Headers({
            "Client-Service:frontend-client",
            "Auth-Key:authkey",
            "Content-Type:application/json",
            "Accept:application/json; charset=UTF-8",
            "User-Agent:android"})

    @POST("api/forgot-password")
    retrofit2.Call<ForgotPinResponse> ForgotPin(@Body LoginRequest body
//            @Header("Cookie") String sessionIdAndToken,
//            @Field("_token") String token,
//            @Field("mobile") String mobile,
//            @Field("code") String password
//            @Field("device_token") String device_token
    );






    @Headers({
            "Client-Service:frontend-client",
            "Auth-Key:authkey",
            "Content-Type:application/json",
            "Accept:application/json; charset=UTF-8",
            "User-Agent:android"})

    @POST("api/homepage")
    retrofit2.Call<HomeResponse> getHomeData(
            @Header("Authorization") String authorization,
            @Body HomeRequest body
    );

    @Headers({
            "Client-Service:frontend-client",
            "Auth-Key:authkey",
            "Content-Type:application/json",
            "Accept:application/json; charset=UTF-8",
            "User-Agent:android"})

    @POST("api/get_vaccine_list")
    retrofit2.Call<VaccinationlistResponse> getVaccinations(
            @Header("Authorization") String authorization,
            @Header("User-ID") String User_ID
    );
//    @Headers()
    @Headers({
            "Client-Service:frontend-client",

            "Content-Type:application/json; charset=UTF-8",
            "Accept:application/json; charset=UTF-8",
            "User-Agent:android",
            "Connection:close"})

    @POST("api/add_mother_details")
    retrofit2.Call<AddmotherResponse> addMotherchild(
            @Header("Authorization") String authorization,
            @Header("User-ID") String User_ID,
            @Body AddMotherRequest addMotherRequest
    );

    @POST("api/add_mother_details")
    retrofit2.Call<AddmotherResponse> addMotherchildonline(
            @Header("Authorization") String authorization,
            @Header("User-ID") String User_ID,
            @Body String addMotherRequest
    );

    @POST("api/get_due_list")
    retrofit2.Call<DueVaccineResponse> getdueVaccinelist(
            @Header("Authorization") String authorization,
            @Header("User-ID") String User_ID,
            @Body DueVaccineListRequest addMotherRequest
    );

    @POST("api/get_child_by_id_and_phone")
    retrofit2.Call<SearchChildResponse> getChilddetailsbyID(
            @Header("Authorization") String authorization,
            @Header("User-ID") String User_ID,
            @Body SearchChildRequest searchChildRequest
    );

    @POST("api/update_vaccination")
    retrofit2.Call<AddmotherResponse> updateChildVaccine(
            @Header("Authorization") String authorization,
            @Header("User-ID") String User_ID,
            @Body UpdateChildVaccineRequest searchChildRequest
    );
    @POST("api/search_number")
    retrofit2.Call<SearchMotherResponse> searchmother(
            @Header("Authorization") String authorization,
            @Header("User-ID") String User_ID,
            @Body SearchMotherRequest searchChildRequest
    );


//    @Headers({
//            "Client-Service:frontend-client",
//            "Auth-Key:authkey",
//            "Content-Type:application/json",
//            "Accept:application/json; charset=UTF-8",
//            "User-Agent:android"})
//
//    @POST("api/get_anm_mother_details")
//    retrofit2.Call<ANMMotherData> getANMMotherData(
//            @Header("Authorization") String authorization,
//            @Header("User-ID") String User_ID,
//            @Body RequestANMMotherChild anm_mobile
//    );


//    @Headers({
//            "Client-Service:frontend-client",
//            "Auth-Key:authkey",
//            "Content-Type:application/json",
//            "Accept:application/json; charset=UTF-8",
//            "User-Agent:android"})

//    @POST("api/get_anm_child_details")
//    retrofit2.Call<ANMChildData> getANMChildData(
//            @Header("Authorization") String authorization,
//
//            @Header("User-ID") String User_ID,
//            @Body RequestANMMotherChild anm_mobile
//    );

    @Headers({
            "Client-Service:frontend-client",
            "Auth-Key:authkey",
            "Content-Type:application/json",
            "Accept:application/json; charset=UTF-8",
            "User-Agent:android"})
    @POST("okc_ivr_apis/Offline_Store_Anm")
    retrofit2.Call<OfflinemotherChildResponse> getANMMotherChildData(
            @Header("Authorization") String authorization,

            @Header("User-ID") String User_ID,
            @Body RequestANMMotherChild anm_mobile
    );

}
