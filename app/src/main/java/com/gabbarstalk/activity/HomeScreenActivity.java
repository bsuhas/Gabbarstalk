package com.gabbarstalk.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gabbarstalk.R;
import com.gabbarstalk.dialogs.PickMemoryDialog;
import com.gabbarstalk.fragments.RecentVideosFragment;
import com.gabbarstalk.models.UserData;
import com.gabbarstalk.utils.CameraUtils;
import com.gabbarstalk.utils.CircularImageView;
import com.gabbarstalk.utils.Constants;
import com.gabbarstalk.utils.DialogUtils;
import com.gabbarstalk.utils.UserPreferences;
import com.gabbarstalk.utils.Utils;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.channels.FileChannel;

public class HomeScreenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private UserData mUserData;
    private SystemBarTintManager mTintManager;
    private CircularImageView profileImage;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 201;
    private NavigationView navigationView;
    private PickMemoryDialog mPickMemoryDialog;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mUserData = UserPreferences.getInstance(this).getUserNameInfo();
        mContext = this;
        initToolBar();
        Bundle bundle = new Bundle();
        setFragment(new RecentVideosFragment(), bundle);
        checkPermissionForWrite();

        removeFileExposer();
    }

    private void removeFileExposer() {
        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        int colorCode = ContextCompat.getColor(this, R.color.colorPrimary);
        applyKitKatTranslucency(Color.parseColor("#" + Integer.toHexString(colorCode)));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);
        TextView txtProfileName = (TextView) view.findViewById(R.id.txt_profile_name);
        TextView txtMobileNumber = (TextView) view.findViewById(R.id.txt_mobile_number);
        profileImage = (CircularImageView) view.findViewById(R.id.profile_image);

        if (mUserData != null) {
            txtProfileName.setText(mUserData.getName());
            txtMobileNumber.setText(mUserData.getMobileNumber());
        }
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPickMemoryDialog = new PickMemoryDialog(HomeScreenActivity.this, HomeScreenActivity.this);
                mPickMemoryDialog.show();
            }
        });
        setDrawerProfileImage();

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View view) {
                int size = navigationView.getMenu().size();
                for (int i = 0; i < size; i++) {
                    navigationView.getMenu().getItem(i).setChecked(false);
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {

                //drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    public void setDrawerProfileImage() {
        String profileImg = UserPreferences.getInstance(this).getProfileImage();
        if (profileImg == null) {
            profileImage.setImageResource(R.drawable.user);
        } else {
            File file = new File(profileImg);
            if (file.exists()) {
                Picasso.with(HomeScreenActivity.this).load("file:///" + file.getAbsolutePath()).placeholder(R.drawable.user).into(profileImage);
            } else
                profileImage.setImageResource(R.drawable.user);
        }
    }

    public void applyKitKatTranslucency(int color) {
        if (Utils.hasKitKat()) {
            if (mTintManager == null)
                mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setStatusBarTintColor(color);

        }
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                DialogUtils.showYesNoLogoutConfirmDialogAndFinishActivity(this, "", "Do you want to close the app?");
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        /*if (id == R.id.action_settings) {
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = new Intent(this, WebViewActivity.class);

        if (id == R.id.nav_agenda) {
            startActivity(new Intent(this, AgendaListActivity.class));
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
        } else if (id == R.id.nav_my_videos) {
            startActivity(new Intent(this, MyVideosActivity.class));
        } else if (id == R.id.nav_disclaimer) {
            intent.putExtra(Constants.URL, "http://gabbartalks.com/disclaimer");
            intent.putExtra(Constants.PAGE_TYPE, getString(R.string.drawer_disclaimer));
            startActivity(intent);
        } else if (id == R.id.nav_privacy_policy) {
            intent.putExtra(Constants.URL, "http://gabbartalks.com/privacy_policy");
            intent.putExtra(Constants.PAGE_TYPE, getString(R.string.drawer_privacy_policy));
            startActivity(intent);
        } else if (id == R.id.nav_terms_cond) {
            intent.putExtra(Constants.URL, "http://gabbartalks.com/terms_and_conditions");
            intent.putExtra(Constants.PAGE_TYPE, getString(R.string.drawer_terms_cond));
            startActivity(intent);
        } else if (id == R.id.nav_contact_us) {

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "support@gabbartalks.com", null));
            emailIntent.putExtra(Intent.EXTRA_EMAIL, "support@gabbartalks.com");
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Gabbartalks Support Request ");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Oh No!\n" +
                    "\n" +
                    "We are sorry to hear that you are having a problem.\n" +
                    "Please describe what is happening below.\n" +
                    "\n" +
                    "We will be back in touch.\n" +
                    "Gabbartalks Support Team\n" +
                    "\n" +
                    "Please Provide a Brief Problem Description Here:");

            startActivity(Intent.createChooser(emailIntent, "Send Email"));

        }
        return true;
    }


    private void exitFromAPP() {
        DialogUtils.showYesNoLogoutConfirmDialogAndFinishActivity(this, "", getString(R.string.close_the_app));
    }

    public void setFragment(Fragment fragment, Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.replace(R.id.fl_container, fragment);
        fragment.setArguments(bundle);
        fragmentTransaction.commit();
    }

    public UserData getUserData() {
        return mUserData;
    }

    public void showYesNoDialog(final Activity mActivity, String title, String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
        if (title != null)
            alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
//                callWebServiceFromMenu(mActivity);
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.create().show();
    }


    private void checkPermissionForWrite() {
        // Here, mContext is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(HomeScreenActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(HomeScreenActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Image from Camera
        if (requestCode == Constants.CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            String compressedPath = CameraUtils.compressImage(Constants.mCurrentPhotoPath, this);

            UserPreferences.getInstance(HomeScreenActivity.this).saveProfileImage(compressedPath);
            setDrawerProfileImage();
            mPickMemoryDialog.dismiss();

        }
        //Image from Gallery
        else if (requestCode == Constants.GALLERY_INTENT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            try {
                String path = getFilePath(data);
                String compressedPath = CameraUtils.compressImage(path, this);
                File src = new File(compressedPath);
                File dest = getImageUri();
                copyFile(src, dest);

                UserPreferences.getInstance(mContext).saveProfileImage(dest.getAbsolutePath());
                setDrawerProfileImage();
                mPickMemoryDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setImageToView(String path) {
        File file = new File(path);
        if (file.exists()) {
            Picasso.with(HomeScreenActivity.this).load("file:///" + file.getAbsolutePath()).placeholder(R.drawable.user).into(profileImage);
            setDrawerProfileImage();
        } else {
            profileImage.setImageResource(R.drawable.user);
        }
    }

    private String getFilePath(Intent data) {
        String imagePath;
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = mContext.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        imagePath = cursor.getString(columnIndex);
        cursor.close();

        return imagePath;

    }

    private void copyFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }

        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }
    }

    public File getImageUri() {

        File file = new File(Environment.getExternalStorageDirectory().getPath(), Constants.STORED_IMAGE_PATH + "/Images/camera");
        if (!file.exists()) {
            file.mkdirs();
        }
        String currentPhotoPath = (file.getAbsolutePath() + "/" + "IMG_" + "profile.jpg");
        return new File(currentPhotoPath);
    }
}
