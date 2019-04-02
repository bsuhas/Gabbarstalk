package com.gabbarstalk.webservices;

import android.app.Activity;

import com.gabbarstalk.interfaces.RESTClientResponse;
import com.gabbarstalk.models.MyVideoResponse;
import com.gabbarstalk.models.UserData;
import com.gabbarstalk.utils.Utils;
import com.google.gson.Gson;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by suhas.bachewar on 1/24/2017.
 */
public class MyVideoListService {

    public void getMyVideoList(final Activity activity, String userId, final RESTClientResponse restClientResponse) {
        HashMap<String, String> body = new HashMap<>();
        body.put("user_id", userId);
        Call<MyVideoResponse> call = Utils.getInstance().getRestClient().getRecentVideoList(body);
        call.enqueue(new Callback<MyVideoResponse>() {
            @Override
            public void onResponse(Call<MyVideoResponse> call, Response<MyVideoResponse> response) {

                if (response.isSuccessful()) {
                    restClientResponse.onSuccess(response.body(), response.code());
                } else {
                    if (response.errorBody() != null) {
                        try {
                            MyVideoResponse MyVideoResponse = new Gson().getAdapter(MyVideoResponse.class)
                                    .fromJson(response.errorBody().string());
                            restClientResponse.onSuccess(MyVideoResponse, response.code());
                        } catch (Exception e) {
                            e.printStackTrace();
                            restClientResponse.onFailure(e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MyVideoResponse> call, Throwable t) {
                t.printStackTrace();
                restClientResponse.onFailure(t);
            }
        });
    }

}
