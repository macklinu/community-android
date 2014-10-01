package com.detroitlabs.community.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

import com.detroitlabs.community.R;

public abstract class BaseActivity extends Activity {

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
            invalidateOptionsMenu();
        } else {
            finish();
        }
    }

    public void changeFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
        }

        fragmentTransaction.replace(R.id.container, fragment);
        invalidateOptionsMenu();
    }

    public void addFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
        }

        fragmentTransaction.add(R.id.container, fragment, fragment.getClass().getName());
        invalidateOptionsMenu();
    }
}
