package com.example.korekushon_app.ui.account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.korekushon_app.DatabaseHelper;
import com.example.korekushon_app.MainActivity;
import com.example.korekushon_app.R;
import com.google.android.material.textfield.TextInputLayout;

public class User_Login extends AppCompatActivity {

    private EditText emailInput;
    private EditText nameInput;
    private Button loginButton;
    private Button createButton;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
        db = new DatabaseHelper(this);

        loginButton = findViewById(R.id.login_button);
        createButton = findViewById(R.id.create_button);

        SharedPreferences prefs = this.getSharedPreferences("application", Context.MODE_PRIVATE);

        // checks if user is already logged in
        if(prefs.getBoolean("Islogin", false)) {
            Log.i("Login","User has previously logged in");
            Intent intent=new Intent(User_Login.this, MainActivity.class);
            startActivity(intent);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        emailInput = findViewById(R.id.email_input);
                        nameInput = findViewById(R.id.username_input);

                        Cursor res = db.grabUser(nameInput.getText().toString());

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append(res.getString(1));
                        }

                        if (buffer.toString().equals(nameInput.getText().toString()) && !nameInput.getText().toString().matches("")) {

                            prefs.edit().putString("CurrentUser", buffer.toString()).commit();
                            prefs.edit().putBoolean("Islogin", true).commit();

                            String message = "Welcome back, " + buffer.toString() + "!";

                            Toast.makeText(User_Login.this, message,
                                    Toast.LENGTH_LONG).show();

                            Intent intent=new Intent(User_Login.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        }
                        else {
                            TextInputLayout til = (TextInputLayout) findViewById(R.id.InputUsername);
                            til.setError("User does not exists");
                        }
                    }
                }
        );

        // takes user to sign up screen
        createButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(User_Login.this, AccountSetup.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
