package com.example.korekushon_app.ui.browse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.korekushon_app.R;

import java.util.List;


public class CustomAdapter extends BaseAdapter {

    private LayoutInflater lInflater;
    private List<ItemObject> listStorage;

    public CustomAdapter(Context context, List<ItemObject> customizedListView) {
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
    }

    @Override
    public int getCount() {
        return listStorage.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView console_name = null;
        TextView product_id = null;
        TextView product_name = null;

        if (convertView == null) {
            convertView = lInflater.inflate(R.layout.browse_listview, parent,
                    false);
        }
        console_name = convertView.findViewById(R.id.console_name);
        product_name = convertView.findViewById(R.id.product_name);
        console_name.setText(listStorage.get(position).getConsole_name());
        product_name.setText(listStorage.get(position).getProduct_name());

        return convertView;
    }
}