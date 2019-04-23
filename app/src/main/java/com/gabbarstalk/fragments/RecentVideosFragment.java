package com.gabbarstalk.fragments;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gabbarstalk.R;
import com.gabbarstalk.adapters.RecentVideosListAdapter;
import com.gabbarstalk.interfaces.RESTClientResponse;
import com.gabbarstalk.models.RecentVideoResponse;
import com.gabbarstalk.models.VideoDetailsModel;
import com.gabbarstalk.utils.Constants;
import com.gabbarstalk.utils.SimpleDividerItemDecoration;
import com.gabbarstalk.utils.Utils;
import com.gabbarstalk.webservices.RecentVideoListService;

import java.util.ArrayList;
import java.util.List;


public class RecentVideosFragment extends Fragment {

    private Activity mActivity;
    private RecyclerView rvAgendaList;
    private List<VideoDetailsModel> videoDetailsModelList;
    private RecentVideosListAdapter adapter;
    private SwipeRefreshLayout swipeContainer;
    private TextView tvEmpty;

    public static RecentVideosFragment newInstance() {
        return new RecentVideosFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_most_recent_videos, container, false);
        this.mActivity = getActivity();
        init(view);
        getRecentVideoList(true);
        LocalBroadcastManager.getInstance(mActivity).registerReceiver(mRefreshRecentVideoListReceiver,
                new IntentFilter(Constants.REFRESH_RECENT_VIDEOS));
        return view;
    }

    private void init(View view) {
        rvAgendaList = (RecyclerView) view.findViewById(R.id.rv_most_liked_list);
        LinearLayoutManager llm = new LinearLayoutManager(mActivity);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvAgendaList.setHasFixedSize(true);
        //rvAgendaList.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        rvAgendaList.setLayoutManager(llm);

        videoDetailsModelList = new ArrayList<>();

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        tvEmpty = (TextView) view.findViewById(R.id.tv_empty);
        adapter = new RecentVideosListAdapter(mActivity, getActivity(), videoDetailsModelList);
        rvAgendaList.setAdapter(adapter);

        swipeContainer.setColorSchemeResources(R.color.gplus_color_1,
                R.color.gplus_color_2,
                R.color.gplus_color_3,
                R.color.gplus_color_4);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRecentVideoList(false);
            }
        });
    }

    private void getRecentVideoList(boolean isShowProgressBar) {
        if (isShowProgressBar)
            Utils.getInstance().showProgressDialog(mActivity);

        new RecentVideoListService().getRecentVideoList(mActivity, new RESTClientResponse() {
            @Override
            public void onSuccess(Object response, int statusCode) {
                if (statusCode == 201) {
                    Utils.getInstance().hideProgressDialog();
                    RecentVideoResponse model = (RecentVideoResponse) response;
                    if (model.getErrorCode() != 1) {
                        videoDetailsModelList = model.getVideoDetailsModelList();
                        adapter.refreshAdapter(videoDetailsModelList);
                        swipeContainer.setRefreshing(false);
                        swipeContainer.setVisibility(View.VISIBLE);
                        rvAgendaList.setVisibility(View.VISIBLE);
                        tvEmpty.setVisibility(View.GONE);
                    } else {
                        swipeContainer.setVisibility(View.GONE);
                        rvAgendaList.setVisibility(View.GONE);
                        tvEmpty.setVisibility(View.VISIBLE);
                        Utils.getInstance().showToast(mActivity, model.getErrorMsg());
                    }
                } else {
                    Utils.getInstance().showToast(mActivity, mActivity.getString(R.string.somthing_went_wrong));
                    Utils.getInstance().hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Object errorResponse) {
                Utils.getInstance().hideProgressDialog();
            }
        });
    }

    private BroadcastReceiver mRefreshRecentVideoListReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("TAG", "in side onReceive mRefreshRecentVideoListReceiver");
            getRecentVideoList(false);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mRefreshRecentVideoListReceiver);
    }
}
