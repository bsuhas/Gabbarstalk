package com.gabbarstalk.utils;

import android.app.Activity;
import android.os.Environment;

import java.io.File;


public class Constants {


    public static final String BASE_URL = "http://app.api.gabbartalks.com/restful/api/";
    public static final String APP_DIRECTORY = "GabbarsTalk";
    public static final String CAMERA_OUTPUT_NAME = APP_DIRECTORY + "_";
    private static final String YOUVOXX_TEMPORARY_DIRECTORY = "Temporary Files";


    public static final String PROFILE_IMAGE = "Profile_Path";
    public static final String PARTY_LOGO_IMAGE = "Party_Logo_Path";
    public static final String DEFAULT_LANG = "Profile_Path";
    public static final String AGENDA_MODEL = "Agenda_model";
    public static final String LANG_ENGLISH = "en";
    //Shared preference
    public static final String AGENDA_DETAILS_MODEL = "agenda_details_model";
    public static final String RECORDED_VIDEO = "recorded_video";
    public static final String USERDATA = "username";
    public static final String KEEP_ME_LOGIN = "keepmelogin";

    public static Boolean isExternalStorageDirectoryExist = null;
    public static boolean isExternalHiddenStorageDirectoryExist = false;
    public static final String HIDE_APP_DIRECTORY = ".YouVOXX";
    public static String URL = "url";
    public static String PAGE_TYPE = "page_type";

    public static String getTemporaryFilePath(Activity activity) {
        return getFilePath(YOUVOXX_TEMPORARY_DIRECTORY + File.separator, activity);
    }

    public static String getFilePath(Activity activity) {
        String path = YOUVOXX_TEMPORARY_DIRECTORY + File.separator;
        if (isExternalStorageDirectoryExist == null) {
            setIsExternalStorageDirectoryExistFlag(path);
        }

        if (isExternalStorageDirectoryExist != null && isExternalStorageDirectoryExist) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + APP_DIRECTORY + File.separator + path;
        } else if (isExternalHiddenStorageDirectoryExist) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + HIDE_APP_DIRECTORY + File.separator + path;
        }
        return activity.getExternalCacheDir() + File.separator + APP_DIRECTORY + File.separator + path;
    }

    private static String getFilePath(String path, Activity activity) {
        if (isExternalStorageDirectoryExist == null) {
            setIsExternalStorageDirectoryExistFlag(path);
        }

        if (isExternalStorageDirectoryExist != null && isExternalStorageDirectoryExist) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + APP_DIRECTORY + File.separator + path;
        } else if (isExternalHiddenStorageDirectoryExist) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + HIDE_APP_DIRECTORY + File.separator + path;
        }
        return activity.getExternalCacheDir() + File.separator + APP_DIRECTORY + File.separator + path;
    }

    private static void setIsExternalStorageDirectoryExistFlag(String path) {
        File mydir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + APP_DIRECTORY + File.separator + path);
        if (!mydir.exists()) {
            isExternalStorageDirectoryExist = mydir.mkdirs();
        }
        if (isExternalStorageDirectoryExist != null && !isExternalStorageDirectoryExist) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + HIDE_APP_DIRECTORY + File.separator + path;
            mydir = new File(path);
            if (!mydir.exists()) {
                isExternalHiddenStorageDirectoryExist = mydir.mkdirs();
            }
        }
    }

    /*private static String getFilePath(String path, Application app) {
        if (isExternalStorageDirectoryExist == null) {
            setIsExternalStorageDirectoryExistFlag(path);
        }

        if (isExternalStorageDirectoryExist != null && isExternalStorageDirectoryExist) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + APP_DIRECTORY + File.separator + path;
        } else if (isExternalHiddenStorageDirectoryExist) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + HIDE_APP_DIRECTORY + File.separator + path;
        }
        return app.getExternalCacheDir() + File.separator + APP_DIRECTORY + File.separator + path;
    }*/
}
