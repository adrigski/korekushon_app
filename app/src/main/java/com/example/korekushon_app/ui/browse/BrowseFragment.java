package com.example.korekushon_app.ui.browse;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.widget.SearchView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class BrowseFragment extends Fragment {

    public ListView browse;
    ArrayList<ItemObject> list_data;
    Toolbar toolbar;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_browse, container, false);
        browse = (ListView) rootView.findViewById(R.id.listView1);
        toolbar = rootView.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

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
                        getJSON(String.format("https://www.pricecharting.com/api/products?t=c0b53bce27c1bdab90b1605249e600dc43dfd1d5&q=%s", textQuery));
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

        // Calling API to retrieve JSON format
        getJSON(String.format("https://www.pricecharting.com/api/products?t=c0b53bce27c1bdab90b1605249e600dc43dfd1d5&q=%s", "nintendo switch"));
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
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonChildNode = null;
                jsonChildNode = jsonArray.getJSONObject(i);
                //get all data from stream
                String consoleName = jsonChildNode.getString("console-name");
                String productID = jsonChildNode.getString("id");
                String productName = jsonChildNode.getString("product-name");

                jsonObject.add(new ItemObject(consoleName, productName));

            List<ItemObject> parsedObject = jsonObject;
            CustomAdapter jsonCustomAdapter = new CustomAdapter(getActivity(), parsedObject);
            browse.setAdapter(jsonCustomAdapter);

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