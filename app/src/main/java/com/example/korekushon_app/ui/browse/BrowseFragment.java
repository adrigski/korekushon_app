package com.example.korekushon_app.ui.browse;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.example.korekushon_app.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import android.os.AsyncTask;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class BrowseFragment extends Fragment {

    public ListView browse;
    ArrayList<ItemObject> list_data;
    Toolbar toolbar;
    Boolean switchState;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_browse, container, false);
        browse = (ListView) rootView.findViewById(R.id.listView1);
        toolbar = rootView.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        View rootView2 = inflater.inflate(R.layout.switch_item, container, false);
        Switch simpleSwitch = (Switch) rootView2.findViewById(R.id.simpleSwitch);

        switchState = simpleSwitch.isChecked();
        if(switchState == false) {
            Toast.makeText(getContext(), "False", Toast.LENGTH_SHORT).show();
        }
        else if(switchState == true) {
            Toast.makeText(getContext(), "True", Toast.LENGTH_SHORT).show();
        }




        // Select object from listview
        browse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                TextView listviewTitle = (TextView) view.findViewById(R.id.product_name);
                TextView listviewSecondary = (TextView) view.findViewById(R.id.console_name);

                Intent intent=new Intent(getActivity(), ProductView.class);
                intent.putExtra("listviewTitle", listviewTitle.getText().toString());
                intent.putExtra("listviewSecondary", listviewSecondary.getText().toString());
                startActivity(intent);
            }
        });


        // Top Bar Search
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.clear();
                menuInflater.inflate(R.menu.toolbar, menu);
                SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
                SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                    @Override
                    public boolean onQueryTextSubmit(String textQuery) {
                        if(switchState == false) {
                            Toast.makeText(getContext(), "False", Toast.LENGTH_SHORT).show();

                                getJSON(String.format("https://www.pricecharting.com/api/products?t=%s&q=%s", prefs.getString("PC_API_Key", "c0b53bce27c1bdab90b1605249e600dc43dfd1d5"), textQuery));

                        }
                        else if(switchState == true)    {
                            Toast.makeText(getContext(), "True", Toast.LENGTH_SHORT).show();
                            getJSON(String.format("https://otakumode.com/search/api/products?keyword=%s", textQuery));
                        }

                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String textQuery) {
                        return false;
                    }
                });



            }
            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        });
        return rootView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());



        // Calling API to retrieve JSON format
        getJSON(String.format("https://www.pricecharting.com/api/products?t=%s&q=%s", prefs.getString("PC_API_Key", "c0b53bce27c1bdab90b1605249e600dc43dfd1d5"), prefs.getString("Default_PC_Term", "")));
        getJSON(String.format("https://otakumode.com/search/api/products?keyword=%s", prefs.getString("Default_TOM_Term", "")));


    }

    private void loadIntoListView(String json) throws JSONException {

        List<ItemObject> jsonObject = new ArrayList<ItemObject>();
        JSONObject resultObject;
        JSONArray jsonArray;


            resultObject = new JSONObject(json);
            System.out.println("Preparsed JSON object " +
                    resultObject.toString());
            // set up json Array to be parsed
            jsonArray = resultObject.optJSONArray("products");

        if (switchState == false ) {
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonChildNode = null;
                jsonChildNode = jsonArray.getJSONObject(i);
                //get all data from stream
                String consoleName = jsonChildNode.getString("console-name");
                String productID = jsonChildNode.getString("id");
                String productName = jsonChildNode.getString("product-name");

                jsonObject.add(new ItemObject(consoleName, productName, productID));

                List<ItemObject> parsedObject = jsonObject;
                CustomAdapter jsonCustomAdapter = new CustomAdapter(getActivity(), parsedObject);
                browse.setAdapter(jsonCustomAdapter);
            }

        }
        else if (switchState == true) {
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonChildNode = null;
                jsonChildNode = jsonArray.getJSONObject(i);
                String imgURL = "https://resize.cdn.otakumode.com/ex/250.250";
                //get all data from stream

                JSONObject volumeInfo = jsonChildNode.getJSONObject("main_image");
                String fullURL = volumeInfo.getString("source");
                String productID = imgURL + fullURL;
                
                String consoleName = jsonChildNode.getString("url");
                String productName = jsonChildNode.getString("title");

                jsonObject.add(new ItemObject(consoleName, productName, productID));

                List<ItemObject> parsedObject = jsonObject;
                CustomAdapter jsonCustomAdapter = new CustomAdapter(getActivity(), parsedObject);
                browse.setAdapter(jsonCustomAdapter);
            }
        }


    }

    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            protected String doInBackground(Void... voids) {

                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;

                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }

            }
        }

        GetJSON getJSON = new GetJSON();
        getJSON.execute();

    } //end AsyncDataClass class

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}