package com.example.korekushon_app.ui.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.korekushon_app.R;

public class SettingsFragment extends Fragment {

    public ListView listView;

    String[] listOptions = {"Settings", "PriceCharting.com", "Otakumode.com", "Github Repo"};
    int iconOptions[] = {R.drawable.settings_24px, R.drawable.sports_esports_24px, R.drawable.category_24px, R.drawable.mail_24px};

    String url1 = "https://www.pricecharting.com/";
    String url2 = "https://otakumode.com/";
    String url3 = "https://github.com/adrigski/korekushon_app";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = (ListView) view.findViewById(R.id.listView1);
        CustomAdapter adapter = new CustomAdapter(getActivity().getApplicationContext(), listOptions, iconOptions);
        listView.setAdapter(adapter);

        // Select object from listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        Intent intent = new Intent(getActivity(), SettingsActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent option1 = new Intent(Intent.ACTION_VIEW);
                        option1.setData(Uri.parse(url1));
                        startActivity(option1);
                        break;
                    case 2:
                        Intent option2 = new Intent(Intent.ACTION_VIEW);
                        option2.setData(Uri.parse(url2));
                        startActivity(option2);
                        break;
                    case 3:
                        Intent option3 = new Intent(Intent.ACTION_VIEW);
                        option3.setData(Uri.parse(url3));
                        startActivity(option3);
                        break;
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}