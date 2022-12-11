package com.example.korekushon_app.ui.settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.ListAdapter;

import com.example.korekushon_app.R;
import com.example.korekushon_app.ui.browse.ItemObject;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    Context context;
    String listOptions[];
    int iconOptions[];
    LayoutInflater inflater;

    public CustomAdapter(Context context, String [] listOptions, int[] iconOptions) {
        this.context = context;
        this.listOptions = listOptions;
        this.iconOptions = iconOptions;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {return listOptions.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position,View view,ViewGroup parent) {

        view = inflater.inflate(R.layout.options_list, null);
        TextView textView = (TextView) view.findViewById(R.id.title);
        ImageView iconView = (ImageView) view.findViewById(R.id.icon);
        textView.setText(listOptions[position]);
        iconView.setImageResource(iconOptions[position]);

        return view;
    }
}
