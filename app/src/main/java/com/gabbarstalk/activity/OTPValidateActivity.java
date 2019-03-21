package com.gabbarstalk.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gabbarstalk.R;
import com.gabbarstalk.interfaces.RESTClientResponse;
import com.gabbarstalk.models.RegisterResponseModel;
import com.gabbarstalk.models.StatusModel;
import com.gabbarstalk.models.UserData;
import com.gabbarstalk.utils.Utils;
import com.gabbarstalk.webservices.RegisterUserService;


/**
 * Created by SUHAS on 19/03/2019.
 */

public class OTPValidateActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private EditText edtOTP;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_screen);
        mContext = this;
        init();
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.otp_screen);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }

        Button btnRegister = (Button) findViewById(R.id.btn_submit_otp);

        edtOTP = (EditText) findViewById(R.id.edt_otp);
        btnRegister.setOnClickListener(this);

        edtOTP.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if ((actionId == EditorInfo.IME_ACTION_DONE)) {
                    if (validate()) {
                        sendDataToRegister();
                    }
                }
                return false;
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                if (validate()) {
                    sendDataToRegister();
                }
        }
    }

    private void sendDataToRegister() {
        if (!Utils.getInstance().isOnline(mContext)) {
            Toast.makeText(mContext, R.string.error_network_unavailable, Toast.LENGTH_SHORT).show();
            return;
        }
//        userData.setMobileNumber(edtOTP.getText().toString().trim());
//        callToSubmitOtp(userData);
        Intent intent = new Intent(OTPValidateActivity.this, HomeScreenActivity.class);
        startActivity(intent);
        finish();

    }


    private void callToSubmitOtp(final UserData userData) {
        Log.e("TAG", "Request:" + userData.toString());
        Utils.getInstance().showProgressDialog(OTPValidateActivity.this);

        new RegisterUserService().registerUser(OTPValidateActivity.this, userData, new RESTClientResponse() {
            @Override
            public void onSuccess(Object response, int statusCode) {
                if (statusCode == 200) {
                    Utils.getInstance().hideProgressDialog();
                    RegisterResponseModel model = (RegisterResponseModel) response;
                    Log.e("TAG", "Response:" + model.toString());
                    StatusModel statusModel = model.getStatus().get(0);
                    /*if (statusModel.getErrorcode() == 1) {
                        userData.setUserId(statusModel.getUserid());
                        UserPreferences.getInstance(mContext).saveUserInfo(userData, true);
                        Intent intent = new Intent(mContext, HomeScreenActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        showToast(mContext,"This user is already register, please contact to administer ");
                    }
*/
                }
            }

            @Override
            public void onFailure(Object errorResponse) {

            }
        });

    }

    private boolean validate() {

        if (TextUtils.isEmpty(edtOTP.getText().toString().trim())) {
            showToast(this, getString(R.string.error_msg_enter_otp));
            return false;
        }
        if (edtOTP.getText().toString().trim().length() < 4) {
            showToast(this, getString(R.string.enter_valid_otp));
            return false;
        }
        return true;
    }

    private void showToast(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }
}


