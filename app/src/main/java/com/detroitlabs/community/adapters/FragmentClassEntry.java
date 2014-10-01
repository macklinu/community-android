package com.detroitlabs.community.adapters;

import android.app.Fragment;

public class FragmentClassEntry {
    private final String displayString;
    private final Class<? extends Fragment> clazz;

    public FragmentClassEntry(String displayString, Class<? extends Fragment> clazz) {
        this.displayString = displayString;
        this.clazz = clazz;
    }

    public Class<? extends Fragment> getFragmentClass() {
        return clazz;
    }

    @Override
    public String toString() {
        return displayString;
    }
}
