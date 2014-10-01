package com.detroitlabs.community.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.detroitlabs.community.R;
import com.detroitlabs.community.model.Event;

import org.joda.time.DateTime;

import java.util.List;

public class EventAdapter extends ArrayAdapter<Event>{

    public EventAdapter(Context context, List<Event> objects){
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){

        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.row_event, null);
        }

        TextView tv = (TextView)view.findViewById(R.id.eventText);

        tv.setText(getItem(position).getDescription() + " - " + new DateTime(getItem(position).getStartTime()).toString("MMMM d h:mm"));

        return view;
    }
}
