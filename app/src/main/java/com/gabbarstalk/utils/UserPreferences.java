package com.gabbarstalk.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.gabbarstalk.models.ProfileData;
import com.google.gson.Gson;
import com.gabbarstalk.models.UserData;

/**
 * Created by SUHAS on 07/05/2016.
 */
public class UserPreferences {

    private final String APP_PREFERENCE = "vijay_yog_app";

    private SharedPreferences mSharedPreferences;

    private static UserPreferences mInstance;

    public static UserPreferences getInstance(Context context) {
        if (mInstance == null)
            mInstance = new UserPreferences(context);

        return mInstance;
    }

    private UserPreferences(Context context) {
        mSharedPreferences = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
    }

    public void clearData() {
        mSharedPreferences.edit().clear().commit();
    }

    private void saveString(String key, String value) {
        if (mSharedPreferences == null)
            return;

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private void saveInt(String key, int value) {
        if (mSharedPreferences == null)
            return;

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    private void saveBoolean(String key, boolean value) {
        if (mSharedPreferences == null)
            return;

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    private String loadString(String key, String defaultValue) {
        String data = mSharedPreferences.getString(key, defaultValue);
        return data;
    }

    private int loadInt(String key, int defaultValue) {
        int data = mSharedPreferences.getInt(key, defaultValue);
        return data;
    }

    private boolean loadBoolean(String key, boolean defaultValue) {
        boolean data = mSharedPreferences.getBoolean(key, defaultValue);
        return data;
    }

    public void saveUserInfo(UserData userData, boolean keepLogin) {
        Gson gson = new Gson();
        String json = gson.toJson(userData);
        saveString(Constants.USERDATA, json);
        saveBoolean(Constants.KEEP_ME_LOGIN, keepLogin);
    }

    public UserData getUserNameInfo() {
        Gson gson = new Gson();
        String json = loadString(Constants.USERDATA, "");
        UserData userData = gson.fromJson(json, UserData.class);
        return userData;
    }

    public void saveProfileInfo(ProfileData profileData) {
        Gson gson = new Gson();
        String json = gson.toJson(profileData);
        saveString(Constants.PROFILE_DATA, json);
    }

    public ProfileData getProfilInfo() {
        Gson gson = new Gson();
        String json = loadString(Constants.PROFILE_DATA, "");
        ProfileData profileData = gson.fromJson(json, ProfileData.class);
        return profileData;
    }

    //check whether user is login or not
    public boolean isUserLogin() {
        return loadBoolean(Constants.KEEP_ME_LOGIN, false);
    }

    public void saveProfileImage(String path) {
        saveString(Constants.PROFILE_IMAGE, path);
    }

    public String getProfileImage() {
        return loadString(Constants.PROFILE_IMAGE, null);
    }

    public String getUserDefaultLanguage() {
        return loadString(Constants.DEFAULT_LANG, Constants.LANG_ENGLISH);
    }

    public void setUserDefaultLanguage(String lang) {
        saveString(Constants.DEFAULT_LANG, lang);
    }

    public void savePartyLogoImage(String path) {
        saveString(Constants.PARTY_LOGO_IMAGE, path);
    }

    public String getPartyLogoImage() {
        return loadString(Constants.PARTY_LOGO_IMAGE, null);
    }
}
