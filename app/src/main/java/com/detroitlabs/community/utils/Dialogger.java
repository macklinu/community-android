package com.detroitlabs.community.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;

import static android.os.Looper.getMainLooper;

public class Dialogger {
    public static void showWebRequestErrorDialog(final Context context) {
        final Handler handler = new Handler(getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(context)
                        .setMessage("Hold up partner! There was an error making your web request.")
                        .setPositiveButton("Aye aye, captain!", null)
                        .show();
            }
        });
    }
}
