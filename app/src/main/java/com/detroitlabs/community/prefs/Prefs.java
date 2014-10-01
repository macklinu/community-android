package com.detroitlabs.community.prefs;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref
public interface Prefs {
    String username();
    String password();
}
