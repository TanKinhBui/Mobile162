package com.example.tankinhbui.mobile_convert_money;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tankinhbui on 08/04/2017.
 */

public class ListAdapter extends ArrayAdapter<Money> {

    public ListAdapter(Context context, int resource, List<Money> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view =  inflater.inflate(R.layout.item, null);
        }
        Money p = getItem(position);
        if (p != null) {
            // Anh xa + Gan gia tri
            TextView tv1 = (TextView) view.findViewById(R.id.textViewName);
            tv1.setText(p.getName());
            TextView tv2 = (TextView) view.findViewById(R.id.textViewRate);
            tv2.setText(String.valueOf(p.getRate()));

        }
        return view;
    }

}