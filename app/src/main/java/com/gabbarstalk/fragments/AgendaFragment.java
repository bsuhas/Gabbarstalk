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
import com.gabbarstalk.adapters.AgendaListAdapter;
import com.gabbarstalk.interfaces.RESTClientResponse;
import com.gabbarstalk.models.AgendaDetailsModel;
import com.gabbarstalk.models.AgendaListResponse;
import com.gabbarstalk.utils.Utils;
import com.gabbarstalk.webservices.AgendaListService;

import java.util.ArrayList;
import java.util.List;


public class AgendaFragment extends Fragment {

    private Activity mActivity;
    private RecyclerView rvAgendaList;
    private List<AgendaDetailsModel> agendaDetailList;
    private AgendaListAdapter adapter;

    public static AgendaFragment newInstance() {
        return new AgendaFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agenda, container, false);
        this.mActivity = getActivity();
        init(view);
        getAgendaList();
        return view;
    }

    private void init(View view) {
        rvAgendaList = (RecyclerView) view.findViewById(R.id.rv_agenda_list);
        LinearLayoutManager llm = new LinearLayoutManager(mActivity);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvAgendaList.setHasFixedSize(true);
//        rvAgendaList.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        rvAgendaList.setLayoutManager(llm);

        agendaDetailList = new ArrayList<>();
        adapter = new AgendaListAdapter(mActivity, agendaDetailList);
        rvAgendaList.setAdapter(adapter);
    }

    private void getAgendaList() {
        Utils.getInstance().showProgressDialog(mActivity);

        new AgendaListService().getAgendaList(mActivity, new RESTClientResponse() {
            @Override
            public void onSuccess(Object response, int statusCode) {
                if (statusCode == 201) {
                    Utils.getInstance().hideProgressDialog();
                    AgendaListResponse model = (AgendaListResponse) response;
                    agendaDetailList = model.getAgendaDetailList();
                    adapter.refreshAdapter(agendaDetailList);
                }
            }

            @Override
            public void onFailure(Object errorResponse) {

            }
        });
    }

}
