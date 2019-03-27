package com.gabbarstalk.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gabbarstalk.R;
import com.gabbarstalk.interfaces.RESTClientResponse;
import com.gabbarstalk.models.AgendaDetailsModel;
import com.gabbarstalk.models.OTPRequestModel;
import com.gabbarstalk.models.RegisterResponseModel;
import com.gabbarstalk.utils.Constants;
import com.gabbarstalk.utils.Utils;
import com.gabbarstalk.webservices.VerifyUserService;
import com.squareup.picasso.Picasso;


/**
 * Created by SUHAS on 19/03/2019.
 */

public class UpdateVideoActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private AgendaDetailsModel agendaDetailsModel = null;
    private String mRecordedVideoUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_video_activity);
        mContext = this;
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            agendaDetailsModel = (AgendaDetailsModel) intent.getSerializableExtra(Constants.AGENDA_DETAILS_MODEL);
            mRecordedVideoUrl = intent.getStringExtra(Constants.RECORDED_VIDEO);
        }
        init();
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.upload_video);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }

        Button btnUploadVideo = (Button) findViewById(R.id.btn_upload_video);
        TextView tvAgendaTitle = (TextView) findViewById(R.id.tv_agenda_title);
        ImageView imgVideoThumb = (ImageView) findViewById(R.id.img_video_thumb);
        ImageView imgVideoPlay = (ImageView) findViewById(R.id.img_video_play);
        btnUploadVideo.setOnClickListener(this);
        imgVideoPlay.setOnClickListener(this);

        if (agendaDetailsModel != null)
            tvAgendaTitle.setText(agendaDetailsModel.getAgendaTitle());
        if (mRecordedVideoUrl != null) {
            Bitmap thumb = ThumbnailUtils.createVideoThumbnail(mRecordedVideoUrl, MediaStore.Video.Thumbnails.MINI_KIND);
            Bitmap thumb1 = ThumbnailUtils.extractThumbnail(thumb,800,400);
            imgVideoThumb.setImageBitmap(thumb1);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_upload_video:
//                sendDataToRegister();
                break;
            case R.id.img_video_play:
                Utils.getInstance().openVideoPlayer(this, mRecordedVideoUrl);
                break;
        }
    }

    private void sendDataToRegister() {
        if (!Utils.getInstance().isOnline(mContext)) {
            Toast.makeText(mContext, R.string.error_network_unavailable, Toast.LENGTH_SHORT).show();
            return;
        }
//        OTPRequestModel otpRequestModel = new OTPRequestModel();
//        otpRequestModel.setMobileNumber(mobileNumber);
//        otpRequestModel.setOtp(edtOTP.getText().toString().trim());
//
//        callToSubmitOtp(otpRequestModel);
    }


    private void callToSubmitOtp(final OTPRequestModel oTPRequestModel) {
        Log.e("TAG", "Request:" + oTPRequestModel.toString());
        Utils.getInstance().showProgressDialog(UpdateVideoActivity.this);

        new VerifyUserService().verifyUser(UpdateVideoActivity.this, oTPRequestModel, new RESTClientResponse() {
            @Override
            public void onSuccess(Object response, int statusCode) {
                if (statusCode == 201) {
                    Utils.getInstance().hideProgressDialog();
                    RegisterResponseModel model = (RegisterResponseModel) response;
                    Log.e("TAG", "Response:" + model.toString());
                    Intent intent = new Intent(mContext, HomeScreenActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    /*if (statusModel.getErrorcode() == 1) {
                    }else{
                        showToast(mContext,"This user is already register, please contact to administer ");
                    }
*/
                }
            }

            @Override
            public void onFailure(Object errorResponse) {

            }
        });

    }
}


