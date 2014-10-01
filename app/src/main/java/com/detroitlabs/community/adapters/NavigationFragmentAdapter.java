package com.detroitlabs.community.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

public class NavigationFragmentAdapter extends ArrayAdapter<FragmentClassEntry> {
    public NavigationFragmentAdapter(Context context, FragmentClassEntry[] entries) {
        super(context, android.R.layout.simple_list_item_activated_1, android.R.id.text1, entries);
    }
}
