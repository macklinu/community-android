package com.detroitlabs.community.prefs;

import com.detroitlabs.community.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;

@EBean
public class AppPrefs {
    @Pref
    Prefs_ prefs;

    private Gson gson;

    @AfterInject
    void afterInject() {
        gson = new GsonBuilder()
                .setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    public User getUser() {
        final String userJsonString = prefs.user().getOr(null);
        return gson.fromJson(userJsonString, User.class);
    }

    public void setUser(User user) {
        final String userJsonString = gson.toJson(user, User.class);
        prefs.user().put(userJsonString);
    }

    public boolean isUserSignedIn() {
        return getUser() != null;
    }
}
