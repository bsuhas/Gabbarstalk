package com.gabbarstalk.webservices;
import android.app.Activity;

import com.gabbarstalk.interfaces.RESTClientResponse;
import com.gabbarstalk.models.RecentVideoResponse;
import com.gabbarstalk.models.RecentVideoResponse;
import com.gabbarstalk.utils.Utils;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by suhas.bachewar on 1/24/2017.
 */
public class RecentVideoListService {

    public void getRecentVideoList(final Activity activity, final RESTClientResponse restClientResponse) {

        Call<RecentVideoResponse> call = Utils.getInstance().getRestClient().getRecentVideoList();
        call.enqueue(new Callback<RecentVideoResponse>() {
            @Override
            public void onResponse(Call<RecentVideoResponse> call, Response<RecentVideoResponse> response) {

                if (response.isSuccessful()) {
                    restClientResponse.onSuccess(response.body(), response.code());
                } else {
                    if (response.errorBody() != null) {
                        try {
                            RecentVideoResponse RecentVideoResponse = new Gson().getAdapter(RecentVideoResponse.class)
                                    .fromJson(response.errorBody().string());
                            restClientResponse.onSuccess(RecentVideoResponse, response.code());
                        } catch (Exception e) {
                            e.printStackTrace();
                            restClientResponse.onFailure(e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<RecentVideoResponse> call, Throwable t) {
                t.printStackTrace();
                restClientResponse.onFailure(t);
            }
        });
    }

}
