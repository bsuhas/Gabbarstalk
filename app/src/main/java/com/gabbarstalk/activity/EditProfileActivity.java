package com.gabbarstalk.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gabbarstalk.R;
import com.gabbarstalk.interfaces.RESTClientResponse;
import com.gabbarstalk.models.EmptyResponse;
import com.gabbarstalk.models.ProfileData;
import com.gabbarstalk.models.UserData;
import com.gabbarstalk.utils.Constants;
import com.gabbarstalk.utils.UserPreferences;
import com.gabbarstalk.utils.Utils;
import com.gabbarstalk.webservices.UpdateProfileService;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by SUHAS on 19/03/2019.
 */

public class EditProfileActivity extends AppCompatActivity {
    private Context mContext;
    private EditText edtName, edtMobile, edtUsername, edtEmailId, edtAddress,edtStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edt_profile_activity);
        mContext = this;
        Intent intent = getIntent();
        ProfileData profileData = (ProfileData) intent.getSerializableExtra(Constants.PROFILE_DATA);
        init();
        setDataInform(profileData);
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
        edtStatus = (EditText) findViewById(R.id.edt_status);
        edtMobile = (EditText) findViewById(R.id.edt_mobile);
        edtUsername = (EditText) findViewById(R.id.edt_username);
        edtEmailId = (EditText) findViewById(R.id.edt_email_id);
        edtAddress = (EditText) findViewById(R.id.edt_address);
        Button btnUpdate = (Button) findViewById(R.id.btn_update);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateAndUpdateProfile();
            }
        });
    }

    private void validateAndUpdateProfile() {
        if (validate()) {
            UserData userData = UserPreferences.getInstance(EditProfileActivity.this).getUserNameInfo();
            if (userData != null) {
                ProfileData profileData = new ProfileData();
                profileData.setUserId(userData.getUserId());
                profileData.setUsername(edtUsername.getText().toString().trim());
                profileData.setStatus(edtStatus.getText().toString().trim());
                profileData.setMobileNumber(edtMobile.getText().toString().trim());

                profileData.setName(edtName.getText().toString().trim());
                profileData.setEmailId(edtEmailId.getText().toString().trim());
                profileData.setAddress(edtAddress.getText().toString().trim());

                updateProfileData(profileData);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.edit_save) {
            validateAndUpdateProfile();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void updateProfileData(final ProfileData profileData) {
        Utils.getInstance().showProgressDialog(EditProfileActivity.this);
        new UpdateProfileService().updateProfileData(EditProfileActivity.this, profileData, new RESTClientResponse() {
            @Override
            public void onSuccess(Object response, int statusCode) {
                if (statusCode == 201) {
                    Utils.getInstance().hideProgressDialog();
                    EmptyResponse model = (EmptyResponse) response;
                    Utils.getInstance().showToast(mContext, model.getErrorMsg());
                    refreshProfile(profileData);
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

    private void refreshProfile(ProfileData profileData) {
        Intent intent = new Intent(Constants.REFRESH_PROFILE);
        intent.putExtra(Constants.PROFILE_DATA,profileData);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        finish();
    }
}


