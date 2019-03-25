package com.gabbarstalk.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.gabbarstalk.R;
import com.gabbarstalk.adapters.AgendaVideosListAdapter;
import com.gabbarstalk.models.AgendaDetailsModel;
import com.gabbarstalk.models.VideoDetailsModel;
import com.gabbarstalk.utils.Constants;
import com.gabbarstalk.utils.SimpleDividerItemDecoration;
import com.gabbarstalk.utils.Utils;

import java.util.ArrayList;

public class AgendaWithVideosActivity extends AppCompatActivity {
    private Context mContext;
    private AgendaDetailsModel agendaDetailsModel;
    private RecyclerView rvAgendaVideoList;
    private ArrayList<VideoDetailsModel> videoDetailsModelList;
    private AgendaVideosListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agenda_with_video_activity);
        mContext = this;
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            agendaDetailsModel = (AgendaDetailsModel) intent.getSerializableExtra(Constants.AGENDA_MODEL);
        }
        init();
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.agenda_details);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }

        TextView txtAgendaName = (TextView) findViewById(R.id.txt_agenda_name);
        txtAgendaName.setText(agendaDetailsModel.getAgendaTitle());


        rvAgendaVideoList = (RecyclerView) findViewById(R.id.rv_agenda_video_list);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvAgendaVideoList.setHasFixedSize(true);
        rvAgendaVideoList.addItemDecoration(new SimpleDividerItemDecoration(this));
        rvAgendaVideoList.setLayoutManager(llm);

        videoDetailsModelList = new ArrayList<>();

        //TODO hardcoded

        VideoDetailsModel model = new VideoDetailsModel();
        model.setUserName("Suhas Bachewar");
        model.setVideoThumbnail("https://www.webslake.com/w_img/t_i/plc.png");
        model.setVideoUrl("http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4");

        for (int i = 0; i < 5; i++) {
            videoDetailsModelList.add(model);
        }

        adapter = new AgendaVideosListAdapter(this, this, videoDetailsModelList);
        rvAgendaVideoList.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void sendDataToRegister() {
        if (!Utils.getInstance().isOnline(mContext)) {
            Toast.makeText(mContext, R.string.error_network_unavailable, Toast.LENGTH_SHORT).show();
            return;
        }
//        OTPRequestModel otpRequestModel = new OTPRequestModel();
//        otpRequestModel.setMobileNumber(mobileNumber);
//        otpRequestModel.setOtp(edtOTP.getText().toString().trim());

//        callToSubmitOtp(otpRequestModel);
    }


    /*private void callToSubmitOtp(final OTPRequestModel oTPRequestModel) {
        Log.e("TAG", "Request:" + oTPRequestModel.toString());
        Utils.getInstance().showProgressDialog(OTPValidateActivity.this);

        new VerifyUserService().verifyUser(OTPValidateActivity.this, oTPRequestModel, new RESTClientResponse() {
            @Override
            public void onSuccess(Object response, int statusCode) {
                if (statusCode == 201) {
                    Utils.getInstance().hideProgressDialog();
                    RegisterResponseModel model = (RegisterResponseModel) response;
                    Log.e("TAG", "Response:" + model.toString());
                    Intent intent = new Intent(mContext, HomeScreenActivity.class);
                    startActivity(intent);
                    finish();
                    *//*if (statusModel.getErrorcode() == 1) {
                    }else{
                        showToast(mContext,"This user is already register, please contact to administer ");
                    }
*//*
                }
            }

            @Override
            public void onFailure(Object errorResponse) {

            }
        });

    }*/

}


