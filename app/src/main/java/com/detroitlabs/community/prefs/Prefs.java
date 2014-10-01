package com.detroitlabs.community.prefs;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

import static org.androidannotations.annotations.sharedpreferences.SharedPref.Scope.UNIQUE;

@SharedPref(value = UNIQUE)
public interface Prefs {
    String user();
}
