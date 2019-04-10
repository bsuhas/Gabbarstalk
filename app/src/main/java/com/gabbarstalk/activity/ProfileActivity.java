package com.gabbarstalk.activity;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gabbarstalk.R;
import com.gabbarstalk.interfaces.RESTClientResponse;
import com.gabbarstalk.models.EmptyResponse;
import com.gabbarstalk.models.GetProfileResponse;
import com.gabbarstalk.models.ProfileData;
import com.gabbarstalk.models.UserData;
import com.gabbarstalk.utils.UserPreferences;
import com.gabbarstalk.utils.Utils;
import com.gabbarstalk.webservices.GetProfileDataService;
import com.gabbarstalk.webservices.UpdateProfileService;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


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
        setContentView(R.layout.profile_activity);
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
                if (validate()) {
                    UserData userData = UserPreferences.getInstance(ProfileActivity.this).getUserNameInfo();
                    if (userData != null) {
                        ProfileData profileData = new ProfileData();
                        profileData.setUserId(userData.getUserId());
                        profileData.setUsername(edtUsername.getText().toString().trim());
                        profileData.setName(edtName.getText().toString().trim());
                        profileData.setEmailId(edtEmailId.getText().toString().trim());
                        profileData.setAddress(edtAddress.getText().toString().trim());
                        updateProfileData(profileData);
                    }
                }
            }
        });
    }

    private void updateProfileData(ProfileData profileData) {
        Utils.getInstance().showProgressDialog(ProfileActivity.this);
        new UpdateProfileService().updateProfileData(ProfileActivity.this, profileData, new RESTClientResponse() {
            @Override
            public void onSuccess(Object response, int statusCode) {
                if (statusCode == 201) {
                    Utils.getInstance().hideProgressDialog();
                    EmptyResponse model = (EmptyResponse) response;
                    Utils.getInstance().showToast(mContext, model.getErrorMsg());
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
                        if (model.getProfileDataList().size() > 0)
                            setDataInform(model.getProfileDataList().get(0));
                            UserPreferences.getInstance(ProfileActivity.this).saveProfileInfo(model.getProfileDataList().get(0));

                    } else {
                        Utils.getInstance().showToast(mContext, getString(R.string.somthing_went_wrong));
                        Utils.getInstance().hideProgressDialog();
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

    private boolean validate() {

        if (TextUtils.isEmpty(edtName.getText().toString().trim())) {
            Utils.getInstance().showToast(this, getString(R.string.error_msg_enter_name));
            return false;
        }
        if (TextUtils.isEmpty(edtUsername.getText().toString().trim())) {
            Utils.getInstance().showToast(this, getString(R.string.error_msg_enter_username));
            return false;
        }
        if (TextUtils.isEmpty(edtEmailId.getText().toString().trim())) {
            Utils.getInstance().showToast(this, getString(R.string.error_msg_enter_email_id));
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(edtEmailId.getText().toString().trim()).matches()) {
            Utils.getInstance().showToast(this, getString(R.string.error_msg_enter_valid_email_id));
            return false;
        }
        if (TextUtils.isEmpty(edtAddress.getText().toString().trim())) {
            Utils.getInstance().showToast(this, getString(R.string.error_msg_enter_address));
            return false;
        }

        return true;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}


