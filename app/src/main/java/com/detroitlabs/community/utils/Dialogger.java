package com.detroitlabs.community.utils;

import android.app.AlertDialog;
import android.content.Context;

public class Dialogger {
    public static void showWebRequestErrorDialog(Context context) {
        new AlertDialog.Builder(context)
                .setMessage("Hold up partner! There was an error making your web request.")
                .setPositiveButton("Aye aye, captain!", null)
                .show();
    }
}
