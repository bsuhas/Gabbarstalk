package com.gabbarstalk.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.gabbarstalk.fragments.RecentVideosFragment;
import com.gabbarstalk.models.UserData;
import com.gabbarstalk.utils.CircularImageView;
import com.gabbarstalk.utils.DialogUtils;
import com.gabbarstalk.utils.UserPreferences;
import com.gabbarstalk.utils.Utils;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.squareup.picasso.Picasso;

import java.io.File;

public class HomeScreenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private UserData mUserData;
    private SystemBarTintManager mTintManager;
    private CircularImageView profileImage;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 201;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mUserData = UserPreferences.getInstance(this).getUserNameInfo();
        initToolBar();
        Bundle bundle = new Bundle();
        setFragment(new RecentVideosFragment(), bundle);
//        checkPermissionForWrite();
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
        /*LayoutInflater mInflater = LayoutInflater.from(getApplicationContext());
        @SuppressLint("InflateParams") View mCustomView = mInflater.inflate(R.layout.activity_home_toolbar, null);
        toolbar.addView(mCustomView);*/

//        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
//        tabLayout.setVisibility(View.VISIBLE);
//        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
//        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()));
//        tabLayout.setupWithViewPager(viewPager);
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
            //TODO
        } else if (id == R.id.nav_disclaimer) {
            startActivity(intent);
        } else if (id == R.id.nav_privacy_policy) {
            startActivity(intent);
        } else if (id == R.id.nav_terms_cond) {
            startActivity(intent);
        } else if (id == R.id.nav_contact_us) {
            //TODO
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

}
