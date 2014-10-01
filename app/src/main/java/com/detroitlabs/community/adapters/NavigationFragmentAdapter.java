package com.detroitlabs.community.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NavigationFragmentAdapter extends ArrayAdapter<FragmentClassEntry> {

    public NavigationFragmentAdapter(Context context, FragmentClassEntry[] entries) {
        super(context, android.R.layout.simple_list_item_activated_1, android.R.id.text1, entries);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FragmentClassEntry fragmentClassEntry = getItem(position);

        TextView view = (TextView) View.inflate(getContext(), android.R.layout.simple_list_item_activated_1, null);
        view.setText(fragmentClassEntry.getDisplayString());

        return view;
    }
}
