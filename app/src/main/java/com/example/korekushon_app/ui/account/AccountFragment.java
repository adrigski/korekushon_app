package com.example.korekushon_app.ui.account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.korekushon_app.DatabaseHelper;
import com.example.korekushon_app.MainActivity;
import com.example.korekushon_app.R;
import com.example.korekushon_app.databinding.FragmentAccountBinding;
import com.example.korekushon_app.ui.browse.ProductView;

import org.w3c.dom.Text;

public class AccountFragment extends Fragment {

    private Button button;
    private TextView username_text;
    private TextView email_text;
    private TextView username_text2;
    DatabaseHelper db;
    Toolbar toolbar;


    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        SharedPreferences prefs = getActivity().getSharedPreferences("application", Context.MODE_PRIVATE);

        db = new DatabaseHelper(getActivity());

        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        button = rootView.findViewById(R.id.button_click);
        username_text = rootView.findViewById(R.id.usernameOutput);
        username_text2 = rootView.findViewById(R.id.username_textview);
        email_text = rootView.findViewById(R.id.email_output);


        Cursor res = db.grabUser(prefs.getString("CurrentUser", "NULL"));

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append(res.getString(2));
        }

        Log.i("Data", buffer.toString());
        username_text.setText(prefs.getString("CurrentUser", "NULL"));
        username_text2.setText(prefs.getString("CurrentUser", "NULL"));
        email_text.setText(buffer.toString());

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                prefs.edit().putString("CurrentUser", null).commit();
                prefs.edit().putBoolean("Islogin", false).commit();


                Intent intent=new Intent(getActivity(), User_Login.class);
                startActivity(intent);
                getActivity().finish();

            }
        });
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}