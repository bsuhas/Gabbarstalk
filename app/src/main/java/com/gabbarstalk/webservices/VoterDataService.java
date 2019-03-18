package com.gabbarstalk.webservices;

/**
 * Created by SUHAS on 10/03/2017.
 */


import android.app.Activity;

import com.google.gson.Gson;
import com.gabbarstalk.interfaces.RESTClientResponse;
import com.gabbarstalk.models.VoterDataRequestModel;
import com.gabbarstalk.models.VoterDataResponseModel;
import com.gabbarstalk.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by suhas.bachewar on 1/24/2017.
 */
public class VoterDataService {

    public void getVoterData(final Activity activity, VoterDataRequestModel requestModel, final RESTClientResponse restClientResponse) {

        Call<VoterDataResponseModel> call = Utils.getInstance().getRestClient().getVoterData(requestModel);
        call.enqueue(new Callback<VoterDataResponseModel>() {
            @Override
            public void onResponse(Call<VoterDataResponseModel> call, Response<VoterDataResponseModel> response) {

                if (response.isSuccessful()) {
                    restClientResponse.onSuccess(response.body(), response.code());
                } else {
                    if (response.errorBody() != null) {
                        try {
                            VoterDataResponseModel VoterDataResponseModel = new Gson().getAdapter(VoterDataResponseModel.class)
                                    .fromJson(response.errorBody().string());
                            restClientResponse.onSuccess(VoterDataResponseModel, response.code());
                        } catch (Exception e) {
                            e.printStackTrace();
                            restClientResponse.onFailure(e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<VoterDataResponseModel> call, Throwable t) {
                t.printStackTrace();
                restClientResponse.onFailure(t);
            }
        });
    }

}
