package com.detroitlabs.community.adapters;

public class FragmentClassEntry {
    private final String displayString;
    private final Class fragment;

    public FragmentClassEntry(String displayString, Class fragment) {
        this.displayString = displayString;
        this.fragment = fragment;
    }

    public String getDisplayString() {
        return displayString;
    }

    public Class getFragment() {
        return fragment;
    }
}
