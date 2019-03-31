package com.gabbarstalk.webservices;

import android.app.Activity;

import com.gabbarstalk.interfaces.RESTClientResponse;
import com.gabbarstalk.models.AgendaDetailsModel;
import com.gabbarstalk.models.AgendaVideosResponse;
import com.gabbarstalk.utils.Utils;
import com.google.gson.Gson;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by suhas.bachewar on 1/24/2017.
 */
public class AgendaVideoListService {

    public void getAgendaList(final Activity activity, AgendaDetailsModel agendaDetailsModel, final RESTClientResponse restClientResponse) {
        HashMap<String, String> body = new HashMap<>();
        body.put("agenda_id", "" + agendaDetailsModel.getAgendaId());
        Call<AgendaVideosResponse> call = Utils.getInstance().getRestClient().getAgendaVideos(body);
        call.enqueue(new Callback<AgendaVideosResponse>() {
            @Override
            public void onResponse(Call<AgendaVideosResponse> call, Response<AgendaVideosResponse> response) {

                if (response.isSuccessful()) {
                    restClientResponse.onSuccess(response.body(), response.code());
                } else {
                    if (response.errorBody() != null) {
                        try {
                            AgendaVideosResponse AgendaVideosResponse = new Gson().getAdapter(AgendaVideosResponse.class)
                                    .fromJson(response.errorBody().string());
                            restClientResponse.onSuccess(AgendaVideosResponse, response.code());
                        } catch (Exception e) {
                            e.printStackTrace();
                            restClientResponse.onFailure(e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AgendaVideosResponse> call, Throwable t) {
                t.printStackTrace();
                restClientResponse.onFailure(t);
            }
        });
    }

}
