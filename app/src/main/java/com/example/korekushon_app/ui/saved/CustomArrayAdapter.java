package com.example.korekushon_app.ui.saved;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.korekushon_app.R;

import java.util.ArrayList;

public class CustomArrayAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> title;
    ArrayList<String> subtitle;
    LayoutInflater inflater;

    public CustomArrayAdapter(Context context, ArrayList<String> title, ArrayList<String> subtitle) {
        this.context = context;
        this.title = title;
        this.subtitle = subtitle;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {return title.size();}

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

        view = inflater.inflate(R.layout.saved_listview, null);
        TextView textView = (TextView) view.findViewById(R.id.product_name);
        TextView textView1 = (TextView) view.findViewById(R.id.console_name);

        textView.setText(title.get(position));
        textView1.setText(subtitle.get(position));


        return view;
    }
}
