package com.gabbarstalk.webservices;
import android.app.Activity;

import com.gabbarstalk.interfaces.RESTClientResponse;
import com.gabbarstalk.models.AgendaListResponse;
import com.gabbarstalk.models.AgendaListResponse;
import com.gabbarstalk.models.UserData;
import com.gabbarstalk.utils.Utils;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by suhas.bachewar on 1/24/2017.
 */
public class AgendaListService {

    public void getAgendaList(final Activity activity, final RESTClientResponse restClientResponse) {

        Call<AgendaListResponse> call = Utils.getInstance().getRestClient().getAgendaList();
        call.enqueue(new Callback<AgendaListResponse>() {
            @Override
            public void onResponse(Call<AgendaListResponse> call, Response<AgendaListResponse> response) {

                if (response.isSuccessful()) {
                    restClientResponse.onSuccess(response.body(), response.code());
                } else {
                    if (response.errorBody() != null) {
                        try {
                            AgendaListResponse AgendaListResponse = new Gson().getAdapter(AgendaListResponse.class)
                                    .fromJson(response.errorBody().string());
                            restClientResponse.onSuccess(AgendaListResponse, response.code());
                        } catch (Exception e) {
                            e.printStackTrace();
                            restClientResponse.onFailure(e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AgendaListResponse> call, Throwable t) {
                t.printStackTrace();
                restClientResponse.onFailure(t);
            }
        });
    }

}
