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

import com.gabbarstalk.R;
import com.gabbarstalk.adapters.AgendaListAdapter;
import com.gabbarstalk.interfaces.RESTClientResponse;
import com.gabbarstalk.models.AgendaDetailsModel;
import com.gabbarstalk.models.AgendaListResponse;
import com.gabbarstalk.models.GetProfileResponse;
import com.gabbarstalk.models.ProfileData;
import com.gabbarstalk.models.UserData;
import com.gabbarstalk.models.VideoDetailsModel;
import com.gabbarstalk.utils.UserPreferences;
import com.gabbarstalk.utils.Utils;
import com.gabbarstalk.webservices.AgendaListService;
import com.gabbarstalk.webservices.GetProfileDataService;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by SUHAS on 19/03/2019.
 */

public class ProfileActivity extends AppCompatActivity {
    private Context mContext;

    private EditText edtName, edtMobile, edtUsername, edtEmailId, edtAddress;
    private Button btnUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);
        mContext = this;
        init();
        getProfileData();
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.drawer_profile);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }

        edtName = (EditText) findViewById(R.id.edt_name);
        edtMobile = (EditText) findViewById(R.id.edt_mobile);
        edtUsername = (EditText) findViewById(R.id.edt_username);
        edtEmailId = (EditText) findViewById(R.id.edt_email_id);
        edtAddress = (EditText) findViewById(R.id.edt_address);
        btnUpdate = (Button) findViewById(R.id.btn_update);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getProfileData() {
        UserData userData = UserPreferences.getInstance(ProfileActivity.this).getUserNameInfo();
        if (userData != null) {
            Utils.getInstance().showProgressDialog(ProfileActivity.this);
            new GetProfileDataService().getProfileData(ProfileActivity.this, userData.getUserId(), new RESTClientResponse() {
                @Override
                public void onSuccess(Object response, int statusCode) {
                    if (statusCode == 201) {
                        Utils.getInstance().hideProgressDialog();
                        GetProfileResponse model = (GetProfileResponse) response;
                        if(model.getProfileDataList().size()>0)
                        setDataInform(model.getProfileDataList().get(0));
                    }
                }

                @Override
                public void onFailure(Object errorResponse) {

                }
            });
        }
    }

    private void setDataInform(ProfileData profileData) {
        edtName.setText(profileData.getName());
        edtMobile.setText(profileData.getMobileNumber());
        edtEmailId.setText(profileData.getEmailId());
        edtUsername.setText(profileData.getUsername());
        edtAddress.setText(profileData.getAddress());

    }
}


