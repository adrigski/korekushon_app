package com.example.korekushon_app.ui.account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import com.example.korekushon_app.DatabaseHelper;
import com.example.korekushon_app.R;

public class AccountFragment extends Fragment {

    private TextView username_text;
    private TextView email_text;
    private TextView passhash_text;
    DatabaseHelper db;
    Toolbar accountToolbar;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        SharedPreferences prefs = getActivity().getSharedPreferences("application", Context.MODE_PRIVATE);

        db = new DatabaseHelper(getActivity());

        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        accountToolbar = rootView.findViewById(R.id.account_toolbar);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(accountToolbar);

        username_text = rootView.findViewById(R.id.usernameOutput);
        email_text = rootView.findViewById(R.id.email_output);
        passhash_text = rootView.findViewById(R.id.password_input);

        Cursor res = db.grabUser(prefs.getString("CurrentUser", "NULL"));
        res.moveToFirst();

        username_text.setText(prefs.getString("CurrentUser", "NULL"));
        email_text.setText(res.getString(2));
        passhash_text.setText(res.getString(3));

        requireActivity().addMenuProvider(new MenuProvider() {

            @Override
            public void onCreateMenu(Menu menu, MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.account_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.logout_button:
                        prefs.edit().putString("CurrentUser", null).commit();
                        prefs.edit().putBoolean("Islogin", false).commit();

                        Intent intent=new Intent(getActivity(), User_Login.class);
                        startActivity(intent);
                        getActivity().finish();
                        break;
                }
                return true;
            }
        });
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}