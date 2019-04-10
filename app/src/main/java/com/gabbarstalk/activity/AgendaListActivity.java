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

import com.gabbarstalk.R;
import com.gabbarstalk.adapters.AgendaListAdapter;
import com.gabbarstalk.interfaces.RESTClientResponse;
import com.gabbarstalk.models.AgendaDetailsModel;
import com.gabbarstalk.models.AgendaListResponse;
import com.gabbarstalk.models.VideoDetailsModel;
import com.gabbarstalk.utils.Utils;
import com.gabbarstalk.webservices.AgendaListService;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by SUHAS on 19/03/2019.
 */

public class AgendaListActivity extends AppCompatActivity {
    private Context mContext;
    private RecyclerView rvAgendaList;
    private List<AgendaDetailsModel> agendaDetailList;
    private AgendaListAdapter adapter;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agenda_list_activity);
        mContext = this;
        init();
        getAgendaList(true);
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.drawer_agenda);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }

        rvAgendaList = (RecyclerView) findViewById(R.id.rv_agenda_list);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvAgendaList.setHasFixedSize(true);
//        rvAgendaList.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        rvAgendaList.setLayoutManager(llm);

        agendaDetailList = new ArrayList<>();
        adapter = new AgendaListAdapter(AgendaListActivity.this, mContext, agendaDetailList);
        rvAgendaList.setAdapter(adapter);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(R.color.gplus_color_1,
                R.color.gplus_color_2,
                R.color.gplus_color_3,
                R.color.gplus_color_4);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAgendaList(false);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getAgendaList(boolean isShowProgressBar) {
        if (isShowProgressBar)
            Utils.getInstance().showProgressDialog(AgendaListActivity.this);

        new AgendaListService().getAgendaList(AgendaListActivity.this, new RESTClientResponse() {
            @Override
            public void onSuccess(Object response, int statusCode) {
                if (statusCode == 201) {
                    Utils.getInstance().hideProgressDialog();
                    AgendaListResponse model = (AgendaListResponse) response;
                    agendaDetailList = model.getAgendaDetailList();
                    adapter.refreshAdapter(agendaDetailList);
                    swipeContainer.setRefreshing(false);
                }else{
                    Utils.getInstance().showToast(AgendaListActivity.this,getString(R.string.somthing_went_wrong));
                    Utils.getInstance().hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Object errorResponse) {

            }
        });
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}


