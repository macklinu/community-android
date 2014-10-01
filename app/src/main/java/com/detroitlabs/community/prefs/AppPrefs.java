package com.detroitlabs.community.prefs;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

@EBean
public class AppPrefs {
    @Pref
    Prefs_ prefs;

    public String getUsername() {
        return prefs.username().get();
    }

    public String getPassword() {
        return prefs.password().get();
    }

    public void setUsername(String username) {
        prefs.username().put(username);
    }

    public void setPassword(String password) {
        prefs.password().put(password);
    }

    public boolean isUserSignedIn() {
        return getUsername() != null &&
                getPassword() != null;
    }
}
