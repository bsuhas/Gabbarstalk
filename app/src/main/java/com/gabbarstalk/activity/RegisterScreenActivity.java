package com.gabbarstalk.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gabbarstalk.R;
import com.gabbarstalk.interfaces.RESTClientResponse;
import com.gabbarstalk.models.RegisterResponseModel;
import com.gabbarstalk.models.StatusModel;
import com.gabbarstalk.models.UserData;
import com.gabbarstalk.utils.UserPreferences;
import com.gabbarstalk.utils.Utils;
import com.gabbarstalk.webservices.RegisterUserService;


/**
 * Created by SUHAS on 04/03/2017.
 */
public class RegisterScreenActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 200;
    private Context mContext;
    private LinearLayout llWardNumber;
    private EditText edtName, edtMobile, edtLoksabha, edtVidhansabha, edtWardNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);
        mContext = this;
        init();
//        checkPermission();
    }

    private void init() {
        Button btnRegister = (Button) findViewById(R.id.btn_register);

        edtMobile = (EditText) findViewById(R.id.edt_mobile);
        btnRegister.setOnClickListener(this);

        edtMobile.setOnEditorActionListener(new TextView.OnEditorActionListener() {

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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                if (validate()) {
                    sendDataToRegister();
                }
        }
    }

    private void sendDataToRegister() {
        /*if(!checkWriteExternalPermission()){
            showToast(this,"Please Grant the phone state permission ");
            return;
        }

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
*/
        if (!Utils.getInstance().isOnline(RegisterScreenActivity.this)) {
            Toast.makeText(RegisterScreenActivity.this, R.string.error_network_unavailable, Toast.LENGTH_SHORT).show();
            return;
        }
        UserData userData = new UserData();
//        userData.setiMEI(telephonyManager.getDeviceId());
        userData.setMobileNumber(edtMobile.getText().toString().trim());
        callToRegisterUser(userData);
    }

    private boolean checkWriteExternalPermission()
    {
        String permission = "android.permission.READ_PHONE_STATE";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    private void callToRegisterUser(final UserData userData) {
        Log.e("TAG", "Request:" + userData.toString());
        Utils.getInstance().showProgressDialog(RegisterScreenActivity.this);

        new RegisterUserService().registerUser(RegisterScreenActivity.this, userData, new RESTClientResponse() {
            @Override
            public void onSuccess(Object response, int statusCode) {
                if (statusCode == 200) {
                    Utils.getInstance().hideProgressDialog();
                    RegisterResponseModel model = (RegisterResponseModel) response;
                    Log.e("TAG", "Response:" + model.toString());
                    StatusModel statusModel = model.getStatus().get(0);
                    /*if (statusModel.getErrorcode() == 1) {
                        userData.setUserId(statusModel.getUserid());
                        UserPreferences.getInstance(RegisterScreenActivity.this).saveUserInfo(userData, true);
                        Intent intent = new Intent(RegisterScreenActivity.this, HomeActivity.class);
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

        if (TextUtils.isEmpty(edtMobile.getText().toString().trim())) {
            showToast(this, getString(R.string.error_msg_enter_mobile_number));
            return false;
        }
        if (edtMobile.getText().toString().trim().length() < 10) {
            showToast(this, getString(R.string.enter_mobile_valid_number));
            return false;
        }
        return true;
    }

    private void showToast(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

    private void checkPermission() {
        // Here, mContext is the current activity
        if (ContextCompat.checkSelfPermission(mContext,Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterScreenActivity.this,
                    Manifest.permission.READ_PHONE_STATE)) {
            } else {
                ActivityCompat.requestPermissions(RegisterScreenActivity.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}

