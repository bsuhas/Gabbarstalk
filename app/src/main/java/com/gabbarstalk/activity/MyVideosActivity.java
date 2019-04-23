package com.gabbarstalk.activity;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gabbarstalk.R;
import com.gabbarstalk.adapters.MyVideosListAdapter;
import com.gabbarstalk.adapters.RecentVideosListAdapter;
import com.gabbarstalk.interfaces.RESTClientResponse;
import com.gabbarstalk.models.GetProfileResponse;
import com.gabbarstalk.models.MyVideoResponse;
import com.gabbarstalk.models.ProfileData;
import com.gabbarstalk.models.RecentVideoResponse;
import com.gabbarstalk.models.UserData;
import com.gabbarstalk.models.VideoDetailsModel;
import com.gabbarstalk.utils.SimpleDividerItemDecoration;
import com.gabbarstalk.utils.UserPreferences;
import com.gabbarstalk.utils.Utils;
import com.gabbarstalk.webservices.GetProfileDataService;
import com.gabbarstalk.webservices.MyVideoListService;
import com.gabbarstalk.webservices.RecentVideoListService;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by SUHAS on 19/03/2019.
 */

public class MyVideosActivity extends AppCompatActivity {
    private Context mContext;
    private RecyclerView rvMyVideoList;
    private List<VideoDetailsModel> videoDetailsModelList;
    private MyVideosListAdapter adapter;
    private SwipeRefreshLayout swipeContainer;
    private TextView tvNoRecord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_video_activity);
        mContext = this;
        init();
        getMyVideoList(true);
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.drawer_my_videos);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }
        rvMyVideoList = (RecyclerView) findViewById(R.id.rv_my_video_list);
        tvNoRecord = (TextView) findViewById(R.id.tv_no_record);


        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvMyVideoList.setHasFixedSize(true);
//        rvMyVideoList.addItemDecoration(new SimpleDividerItemDecoration(mContext));
        rvMyVideoList.setLayoutManager(llm);

        videoDetailsModelList = new ArrayList<>();

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        adapter = new MyVideosListAdapter(MyVideosActivity.this, mContext, videoDetailsModelList);
        rvMyVideoList.setAdapter(adapter);

        swipeContainer.setColorSchemeResources(R.color.gplus_color_1,
                R.color.gplus_color_2,
                R.color.gplus_color_3,
                R.color.gplus_color_4);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMyVideoList(false);
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getMyVideoList(boolean isShowProgressBar) {

        UserData userData = UserPreferences.getInstance(MyVideosActivity.this).getUserNameInfo();
        if (userData != null) {
            if (isShowProgressBar)
                Utils.getInstance().showProgressDialog(MyVideosActivity.this);


            new MyVideoListService().getMyVideoList(MyVideosActivity.this, userData.getUserId(), new RESTClientResponse() {
                @Override
                public void onSuccess(Object response, int statusCode) {
                    if (statusCode == 201) {
                        Utils.getInstance().hideProgressDialog();
                        MyVideoResponse model = (MyVideoResponse) response;
                        if (model.getErrorCode() == 1) {
                            Utils.getInstance().showToast(mContext, model.getErrorMsg());
                            tvNoRecord.setVisibility(View.VISIBLE);
                            swipeContainer.setVisibility(View.GONE);
                            rvMyVideoList.setVisibility(View.GONE);


                        } else {
                            videoDetailsModelList = model.getVideoDetailsModelList();
                            if (videoDetailsModelList != null ) {
                                if(videoDetailsModelList.size() > 0)
                                adapter.refreshAdapter(videoDetailsModelList);
                            }
                            tvNoRecord.setVisibility(View.GONE);
                            swipeContainer.setVisibility(View.VISIBLE);
                            rvMyVideoList.setVisibility(View.VISIBLE);
                        }
                        swipeContainer.setRefreshing(false);
                    }else {
                        Utils.getInstance().showToast(mContext, mContext.getString(R.string.somthing_went_wrong));
                        Utils.getInstance().hideProgressDialog();
                    }
                }

                @Override
                public void onFailure(Object errorResponse) {

                }
            });
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}


