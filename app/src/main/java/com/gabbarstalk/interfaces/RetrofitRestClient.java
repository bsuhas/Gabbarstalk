package com.gabbarstalk.interfaces;

import com.gabbarstalk.models.BoothDataResponse;
import com.gabbarstalk.models.RegisterResponseModel;
import com.gabbarstalk.models.RequestModel;
import com.gabbarstalk.models.SurveyDataRequestModel;
import com.gabbarstalk.models.SurveyDataResponse;
import com.gabbarstalk.models.UserData;
import com.gabbarstalk.models.VoterDataRequestModel;
import com.gabbarstalk.models.VoterDataResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by SUHAS on 10/03/2017.
 */

public interface RetrofitRestClient {
    @Headers("Content-Type: application/json")
    @POST("register")
    Call<RegisterResponseModel> registerUser(@Body UserData user);

    @Headers("Content-Type: application/json")
    @POST("BoothAddress")
    Call<BoothDataResponse> getBoothData(@Body RequestModel model);

    @Headers("Content-Type: application/json")
    @POST("VoterData")
    Call<VoterDataResponseModel> getVoterData(@Body VoterDataRequestModel model);

    @Headers("Content-Type: application/json")
    @POST("SurveyData")
    Call<SurveyDataResponse> getSurveyData(@Body SurveyDataRequestModel model);
}
