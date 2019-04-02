package com.gabbarstalk.webservices;

import android.app.Activity;

import com.gabbarstalk.interfaces.RESTClientResponse;
import com.gabbarstalk.models.GetProfileResponse;
import com.gabbarstalk.utils.Utils;
import com.google.gson.Gson;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by suhas.bachewar on 1/24/2017.
 */
public class GetProfileDataService {

    public void getProfileData(final Activity activity, String userId, final RESTClientResponse restClientResponse) {
        HashMap<String, String> body = new HashMap<>();
        body.put("user_id", userId);
        Call<GetProfileResponse> call = Utils.getInstance().getRestClient().getUserProfileData(body);
        call.enqueue(new Callback<GetProfileResponse>() {
            @Override
            public void onResponse(Call<GetProfileResponse> call, Response<GetProfileResponse> response) {

                if (response.isSuccessful()) {
                    restClientResponse.onSuccess(response.body(), response.code());
                } else {
                    if (response.errorBody() != null) {
                        try {
                            GetProfileResponse GetProfileResponse = new Gson().getAdapter(GetProfileResponse.class)
                                    .fromJson(response.errorBody().string());
                            restClientResponse.onSuccess(GetProfileResponse, response.code());
                        } catch (Exception e) {
                            e.printStackTrace();
                            restClientResponse.onFailure(e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetProfileResponse> call, Throwable t) {
                t.printStackTrace();
                restClientResponse.onFailure(t);
            }
        });
    }

}
