package com.gabbarstalk.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gabbarstalk.R;
import com.gabbarstalk.interfaces.RESTClientResponse;
import com.gabbarstalk.models.AgendaDetailsModel;
import com.gabbarstalk.models.EmptyResponse;
import com.gabbarstalk.models.OTPRequestModel;
import com.gabbarstalk.models.RegisterResponseModel;
import com.gabbarstalk.models.UserData;
import com.gabbarstalk.utils.Constants;
import com.gabbarstalk.utils.UserPreferences;
import com.gabbarstalk.utils.Utils;
import com.gabbarstalk.webservices.UploadVideoService;
import com.gabbarstalk.webservices.VerifyUserService;

import java.io.File;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by SUHAS on 19/03/2019.
 */

public class UploadVideoActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private AgendaDetailsModel agendaDetailsModel = null;
    private String mRecordedVideoUrl;
    private String mVideoCaption;
    private EditText edtCaption;

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
        edtCaption = (EditText) findViewById(R.id.edt_caption);
        btnUploadVideo.setOnClickListener(this);
        imgVideoPlay.setOnClickListener(this);

        if (agendaDetailsModel != null)
            tvAgendaTitle.setText(agendaDetailsModel.getAgendaTitle());
        if (mRecordedVideoUrl != null) {
            Bitmap thumb = ThumbnailUtils.createVideoThumbnail(mRecordedVideoUrl, MediaStore.Video.Thumbnails.MINI_KIND);
            Bitmap thumb1 = ThumbnailUtils.extractThumbnail(thumb, 800, 400);
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
                showYesNoDialog(UploadVideoActivity.this,"Cautionary Advice !",getString(R.string.privacy_msg));
                break;
            case R.id.img_video_play:
                Utils.getInstance().openVideoPlayer(this, mRecordedVideoUrl);
                break;
        }
    }

    public void showYesNoDialog(final Activity mActivity, String title, String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
        if (title != null)
            alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                uploadVideo();
                dialog.dismiss();
//                callWebServiceFromMenu(mActivity);
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.create().show();
    }

    private void uploadVideo() {
        mVideoCaption = edtCaption.getText().toString();
        if (TextUtils.isEmpty(mVideoCaption)) {
            Toast.makeText(mContext, "Please enter caption", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Utils.getInstance().isOnline(mContext)) {
            Toast.makeText(mContext, R.string.error_network_unavailable, Toast.LENGTH_SHORT).show();
            return;
        }

        Utils.getInstance().showProgressDialog(UploadVideoActivity.this);
        File file = new File(mRecordedVideoUrl);
        Log.e("TAG", "Request:" + file.getAbsolutePath());

        UserData userData = UserPreferences.getInstance(UploadVideoActivity.this).getUserNameInfo();

        new UploadVideoService().uploadVideo(UploadVideoActivity.this,
                userData.getUserId(), "" + agendaDetailsModel.getAgendaId(),
                agendaDetailsModel.getAgendaTitle(), mVideoCaption, file, new RESTClientResponse() {
                    @Override
                    public void onSuccess(Object response, int statusCode) {
                        if (statusCode == 201) {
                            Utils.getInstance().hideProgressDialog();
                            EmptyResponse model = (EmptyResponse) response;
                            Log.e("TAG", "Response:" + model.toString());
                            if (model.getErrorCode() == 0) {
                                Utils.getInstance().showToast(mContext, model.getErrorMsg());
                                refreshPage();
                            } else {
                                Utils.getInstance().showToast(mContext, getString(R.string.somthing_went_wrong));
                                Utils.getInstance().hideProgressDialog();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Object errorResponse) {
                        Utils.getInstance().showToast(mContext, errorResponse.toString());
                        Utils.getInstance().hideProgressDialog();
                    }
                });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void refreshPage() {
        Intent intent = new Intent(Constants.REFRESH_PAGE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        finish();
    }
}


