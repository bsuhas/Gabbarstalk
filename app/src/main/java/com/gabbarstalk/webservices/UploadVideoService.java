package com.gabbarstalk.webservices;

import android.app.Activity;

import com.gabbarstalk.interfaces.RESTClientResponse;
import com.gabbarstalk.models.EmptyResponse;
import com.gabbarstalk.utils.Utils;
import com.google.gson.Gson;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by suhas.bachewar on 1/24/2017.
 */

public class UploadVideoService {

    public void uploadVideo(final Activity activity, String userId, String agendaId, String agendaTitle, String videoTitle, File file, final RESTClientResponse restClientResponse) {
        //File
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData("video", file.getName(), requestFile);

        //userId
        RequestBody userIdBody = RequestBody.create(MediaType.parse("multipart/form-data"), userId);

        //agendaId
        RequestBody agendaIdBody = RequestBody.create(MediaType.parse("multipart/form-data"), agendaId);

        //agendaId
        RequestBody agendaTitleBody = RequestBody.create(MediaType.parse("multipart/form-data"), agendaTitle);

        //agendaId
        RequestBody videoTitleBody = RequestBody.create(MediaType.parse("multipart/form-data"), videoTitle);


        Call<EmptyResponse> call = Utils.getInstance().getRestClient().uploadVideo(body, userIdBody, agendaIdBody, agendaTitleBody, videoTitleBody);
        call.enqueue(new Callback<EmptyResponse>() {
            @Override
            public void onResponse(Call<EmptyResponse> call, Response<EmptyResponse> response) {

                if (response.isSuccessful()) {
                    restClientResponse.onSuccess(response.body(), response.code());
                } else {
                    if (response.errorBody() != null) {
                        try {
                            EmptyResponse EmptyResponse = new Gson().getAdapter(EmptyResponse.class)
                                    .fromJson(response.errorBody().string());
                            restClientResponse.onSuccess(EmptyResponse, response.code());
                        } catch (Exception e) {
                            e.printStackTrace();
                            restClientResponse.onFailure(e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<EmptyResponse> call, Throwable t) {
                t.printStackTrace();
                restClientResponse.onFailure(t);
            }
        });
    }

}
