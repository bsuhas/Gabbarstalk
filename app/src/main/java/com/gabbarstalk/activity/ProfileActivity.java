package com.gabbarstalk.activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gabbarstalk.R;
import com.gabbarstalk.interfaces.RESTClientResponse;
import com.gabbarstalk.models.GetProfileResponse;
import com.gabbarstalk.models.ProfileData;
import com.gabbarstalk.models.UserData;
import com.gabbarstalk.utils.Constants;
import com.gabbarstalk.utils.UserPreferences;
import com.gabbarstalk.utils.Utils;
import com.gabbarstalk.webservices.GetProfileDataService;
import com.squareup.picasso.Picasso;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by SUHAS on 19/03/2019.
 */

public class ProfileActivity extends AppCompatActivity {
    private Context mContext;

    private TextView tvName, tvStatus, tvMobile, tvUsername, tvEmailId, tvAddress;
    private ProfileData mProfileData;
    private ImageView profileImage;

    private BroadcastReceiver mRefreshProfileReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("TAG", "in side onReceive");
            mProfileData = (ProfileData) intent.getSerializableExtra(Constants.PROFILE_DATA);
            setDataInform(mProfileData);
            UserPreferences.getInstance(ProfileActivity.this).saveProfileInfo(mProfileData);

            Intent intentRefresh = new Intent(Constants.REFRESH_RECENT_VIDEOS);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intentRefresh);

            Intent intentProfileRefresh = new Intent(Constants.REFRESH_PROFILE_DATA);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intentProfileRefresh);
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        mContext = this;
        init();
        getProfileData();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRefreshProfileReceiver,
                new IntentFilter(Constants.REFRESH_PROFILE));
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.drawer_profile);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }

        tvName = (TextView) findViewById(R.id.tv_name);
        tvStatus = (TextView) findViewById(R.id.tv_status);
        tvMobile = (TextView) findViewById(R.id.tv_mobile_number);
        tvUsername = (TextView) findViewById(R.id.tv_username);
        tvEmailId = (TextView) findViewById(R.id.tv_email_id);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        profileImage = (ImageView) findViewById(R.id.profile_image);
        Button btnEdit = (Button) findViewById(R.id.btn_edit);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditProfile();
            }
        });
    }

    private void openEditProfile() {
        Intent intent = new Intent(mContext, EditProfileActivity.class);
        intent.putExtra(Constants.PROFILE_DATA, mProfileData);
        mContext.startActivity(intent);
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
        if (id == R.id.edit_profile) {
            openEditProfile();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        if (TextUtils.isEmpty(profileData.getProfileImgURL())) {
            profileImage.setImageResource(R.drawable.user);
        } else {
            Picasso.with(mContext).load(profileData.getProfileImgURL()).placeholder(R.drawable.user).into(profileImage);
        }
        mProfileData = profileData;
        tvName.setText(profileData.getName());
        tvStatus.setText(profileData.getStatus());
        tvMobile.setText(profileData.getMobileNumber());
        tvEmailId.setText(profileData.getEmailId());
        tvUsername.setText(profileData.getUsername());
        tvAddress.setText(profileData.getAddress());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRefreshProfileReceiver);
    }
}


