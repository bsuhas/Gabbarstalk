package com.gabbarstalk.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.gabbarstalk.R;

import java.util.Locale;

public class DialogUtils {

    public static void showAlertDialog(Context context, String title, String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        if (title != null)
            alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.create().show();
    }

    public static void showYesNoLogoutConfirmDialogAndFinishActivity(final Activity mActivity, String title, String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
        if (title != null)
            alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
                mActivity.finish();
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
}
