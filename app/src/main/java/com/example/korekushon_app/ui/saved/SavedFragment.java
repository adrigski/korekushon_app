package com.example.korekushon_app.ui.saved;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.korekushon_app.DatabaseHelper;
import com.example.korekushon_app.R;
import com.example.korekushon_app.ui.browse.ProductView;

import java.util.ArrayList;

public class SavedFragment extends Fragment {

    public ListView listView;
    DatabaseHelper db;
    ArrayList<String> listItem;
    ArrayList<String> listItem1;
    CustomArrayAdapter adapter;


    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_saved, container, false);

        listItem = new ArrayList<>();
        listItem1 = new ArrayList<>();

        db = new DatabaseHelper(getActivity());

        listView = (ListView) rootView.findViewById(R.id.bookmark_listView);

        Cursor res = db.populateCollection();

        if (res.getCount() == 0) {
            Toast.makeText(getActivity(), "No data to show", Toast.LENGTH_SHORT).show();
        } else {
            while (res.moveToNext()) {
                listItem.add(res.getString(1));
                listItem1.add(res.getString(2));
            }

            adapter = new CustomArrayAdapter(getActivity().getApplicationContext(), listItem, listItem1);
            listView.setAdapter(adapter);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                TextView listviewTitle = (TextView) view.findViewById(R.id.product_name);
                TextView listviewSecondary = (TextView) view.findViewById(R.id.console_name);

                Intent intent = new Intent(getActivity(), ProductView.class);
                intent.putExtra("listviewTitle", listviewTitle.getText().toString());
                intent.putExtra("listviewSecondary", listviewSecondary.getText().toString());
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}