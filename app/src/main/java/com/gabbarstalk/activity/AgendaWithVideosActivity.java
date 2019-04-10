package com.gabbarstalk.activity;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gabbarstalk.R;
import com.gabbarstalk.adapters.AgendaVideosListAdapter;
import com.gabbarstalk.interfaces.RESTClientResponse;
import com.gabbarstalk.models.AgendaDetailsModel;
import com.gabbarstalk.models.AgendaVideosResponse;
import com.gabbarstalk.models.VideoDetailsModel;
import com.gabbarstalk.utils.CameraUtils;
import com.gabbarstalk.utils.Constants;
import com.gabbarstalk.utils.SimpleDividerItemDecoration;
import com.gabbarstalk.utils.Utils;
import com.gabbarstalk.webservices.AgendaVideoListService;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AgendaWithVideosActivity extends AppCompatActivity {
    private Context mContext;
    private AgendaDetailsModel agendaDetailsModel;
    private RecyclerView rvAgendaVideoList;
    private ArrayList<VideoDetailsModel> videoDetailsModelList;
    private AgendaVideosListAdapter adapter;
    private String recordedVideoPath;
    private int CAMERA_PERMISSION = 454;

    private BroadcastReceiver mRefreshReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("TAG","in side onReceive");
            getVideoList(agendaDetailsModel);
        }
    };

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
        getVideoList(agendaDetailsModel);
        LocalBroadcastManager.getInstance(this).registerReceiver(mRefreshReceiver,
                new IntentFilter(Constants.REFRESH_PAGE));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRefreshReceiver);
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

        adapter = new AgendaVideosListAdapter(this, this, videoDetailsModelList);
        rvAgendaVideoList.setAdapter(adapter);

        FloatingActionButton fabButton = (FloatingActionButton) findViewById(R.id.fab_btn);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(AgendaWithVideosActivity.this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(AgendaWithVideosActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
                } else {
                    recordedVideoPath = CameraUtils.startCameraIntent(AgendaWithVideosActivity.this);
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getVideoList(final AgendaDetailsModel agendaDetailsModel) {
        if (!Utils.getInstance().isOnline(mContext)) {
            Toast.makeText(mContext, R.string.error_network_unavailable, Toast.LENGTH_SHORT).show();
            return;
        }

        Utils.getInstance().showProgressDialog(AgendaWithVideosActivity.this);

        new AgendaVideoListService().getAgendaList(AgendaWithVideosActivity.this, agendaDetailsModel, new RESTClientResponse() {
            @Override
            public void onSuccess(Object response, int statusCode) {
                if (statusCode == 201) {
                    Utils.getInstance().hideProgressDialog();
                    AgendaVideosResponse model = (AgendaVideosResponse) response;
                    Log.e("TAG", "Response:" + model.toString());

                    if (model.getErrorCode() == 0) {
                        videoDetailsModelList = (ArrayList<VideoDetailsModel>) model.getVideoDetailsModelList();
                        adapter.refreshAdapter(videoDetailsModelList);
                    } else {
                        Utils.getInstance().showToast(AgendaWithVideosActivity.this, model.getErrorMsg());
                    }
                }else{
                    Utils.getInstance().showToast(mContext,getString(R.string.somthing_went_wrong));
                    Utils.getInstance().hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Object errorResponse) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CameraUtils.ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Intent intent = new Intent(AgendaWithVideosActivity.this, UploadVideoActivity.class);
                intent.putExtra(Constants.AGENDA_DETAILS_MODEL, agendaDetailsModel);
                intent.putExtra(Constants.RECORDED_VIDEO, recordedVideoPath);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                recordedVideoPath = CameraUtils.startCameraIntent(AgendaWithVideosActivity.this);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}


