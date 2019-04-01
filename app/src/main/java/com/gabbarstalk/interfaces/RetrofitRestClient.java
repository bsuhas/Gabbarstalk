package com.gabbarstalk.interfaces;

import com.gabbarstalk.models.AgendaListResponse;
import com.gabbarstalk.models.AgendaVideosResponse;
import com.gabbarstalk.models.EmptyResponse;
import com.gabbarstalk.models.LikeData;
import com.gabbarstalk.models.OTPRequestModel;
import com.gabbarstalk.models.RecentVideoResponse;
import com.gabbarstalk.models.RegisterResponseModel;
import com.gabbarstalk.models.UserData;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by SUHAS on 10/03/2017.
 */

public interface RetrofitRestClient {
    @Headers("Content-Type: application/json")
    @POST("userRegister")
    Call<RegisterResponseModel> registerUser(@Body UserData user);

    @Headers("Content-Type: application/json")
    @POST("verifyUser")
    Call<RegisterResponseModel> verifyUser(@Body OTPRequestModel model);

    @Headers("Content-Type: application/json")
    @POST("resendOtp")
    Call<RegisterResponseModel> resendOTP(@Body UserData user);

    @Headers("Content-Type: application/json")
    @GET("getAgendasList")
    Call<AgendaListResponse> getAgendaList();

    @Headers("Content-Type: application/json")
    @GET("getRecentVideos")
    Call<RecentVideoResponse> getRecentVideoList();

    @Headers("Content-Type: application/json")
    @POST("getAgendaVideos")
    Call<AgendaVideosResponse> getAgendaVideos(@Body HashMap<String, String> body);

    @Multipart
    @POST("videoUpload")
    Call<ResponseBody> uploadVideo(@Part MultipartBody.Part video,
                              @Part("user_id") RequestBody userId,
                              @Part("agenda_id") RequestBody agendaId,
                              @Part("agenda_title") RequestBody agendaTitle
    );

    @Headers("Content-Type: application/json")
    @POST("likeVideo")
    Call<EmptyResponse> likeData(@Body LikeData likeData);
}
