package com.detroitlabs.community.activities;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

import com.detroitlabs.community.R;
import com.detroitlabs.community.prefs.AppPrefs;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_login)
public class LoginActivity extends Activity {

    @Bean
    AppPrefs appPrefs;

    @AfterInject
    void afterInject() {
        if (appPrefs.isUserSignedIn()) {
            NavigationActivity_.intent(this)
                    .start();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
