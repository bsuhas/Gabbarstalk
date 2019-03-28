package com.gabbarstalk.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gabbarstalk.R;
import com.gabbarstalk.adapters.RecentVideosListAdapter;
import com.gabbarstalk.interfaces.RESTClientResponse;
import com.gabbarstalk.models.VideoDetailsModel;
import com.gabbarstalk.utils.SimpleDividerItemDecoration;
import com.gabbarstalk.utils.Utils;
import com.gabbarstalk.webservices.AgendaListService;

import java.util.ArrayList;
import java.util.List;


public class RecentVideosFragment extends Fragment {

    private Activity mActivity;
    private RecyclerView rvAgendaList;
    private List<VideoDetailsModel> videoDetailsModelList;
    private RecentVideosListAdapter adapter;

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
//        getAgendaList();
        return view;
    }

    private void init(View view) {
        rvAgendaList = (RecyclerView) view.findViewById(R.id.rv_most_liked_list);
        LinearLayoutManager llm = new LinearLayoutManager(mActivity);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvAgendaList.setHasFixedSize(true);
        rvAgendaList.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        rvAgendaList.setLayoutManager(llm);

        videoDetailsModelList = new ArrayList<>();

        //TODO hardcoded

        VideoDetailsModel model = new VideoDetailsModel();
        model.setUserName("Suhas Bachewar");
        model.setVideoThumbnail("https://www.webslake.com/w_img/t_i/plc.png");
        model.setVideoUrl("http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4");

        for (int i=0;i<5;i++){
            videoDetailsModelList.add(model);
        }
        adapter = new RecentVideosListAdapter(mActivity, getActivity(), videoDetailsModelList);
        rvAgendaList.setAdapter(adapter);
    }

    private void getAgendaList() {
        Utils.getInstance().showProgressDialog(mActivity);

        new AgendaListService().getAgendaList(mActivity, new RESTClientResponse() {
            @Override
            public void onSuccess(Object response, int statusCode) {
                if (statusCode == 201) {
                    Utils.getInstance().hideProgressDialog();
//                    AgendaListResponse model = (AgendaListResponse) response;
//                    videoDetailsModelList = model.getAgendaDetailList();
//                    adapter.refreshAdapter(videoDetailsModelList);
                }
            }

            @Override
            public void onFailure(Object errorResponse) {

            }
        });
    }


    /*public static RecentVideosFragment newInstance() {
        return new RecentVideosFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_most_recent_videos, container, false);
    }
*/
}
