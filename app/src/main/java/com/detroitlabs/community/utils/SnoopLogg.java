package com.detroitlabs.community.utils;

import android.util.Log;

public class SnoopLogg {
    public static void e(Throwable throwable) {
        Log.e("ERROR", "Sup cousin? There was an error:", throwable);
    }
}
