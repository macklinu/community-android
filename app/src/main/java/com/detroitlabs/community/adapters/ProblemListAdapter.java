package com.detroitlabs.community.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.detroitlabs.community.model.Problem;

import java.util.List;

/**
 * Created by bobbake4 on 10/1/14.
 */
public class ProblemListAdapter extends ArrayAdapter<Problem> {

    public ProblemListAdapter(Context context, List<Problem> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Problem problem = getItem(position);

        TextView view = (TextView) View.inflate(getContext(), android.R.layout.simple_list_item_1, null);

        view.setBackgroundColor(0xFFFFFF);
        view.setPadding(15,15,15,15);
        view.setText(problem.getDescription() + "\nDistance: " + (int)problem.getDistance() + " miles");

        return view;
    }
}
