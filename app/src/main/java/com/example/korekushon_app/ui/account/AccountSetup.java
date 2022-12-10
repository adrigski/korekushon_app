package com.example.korekushon_app.ui.account;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.korekushon_app.DatabaseHelper;
import com.example.korekushon_app.R;
import com.google.android.material.textfield.TextInputLayout;

public class AccountSetup extends AppCompatActivity {

    DatabaseHelper db;
    private EditText usernameInput;
    private EditText emailInput;
    private EditText passwordInput;
    private Button submitButton;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        Intent i = getIntent();

        db = new DatabaseHelper(this);

        usernameInput = findViewById(R.id.uname_input);
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.pass_input);
        submitButton = findViewById(R.id.signup_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor res = db.grabUser(usernameInput.getText().toString());

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append(res.getString(1));
                }

                if (!buffer.toString().equals(usernameInput.getText().toString()) && !usernameInput.getText().toString().matches("")) {
                    Log.i("Login", "Account does not exist, creating...");
                    boolean isInserted = db.insertData(usernameInput.getText().toString(),emailInput.getText().toString(),passwordInput.getText().toString());

                    if (isInserted == true) {
                        Toast.makeText(AccountSetup.this, "Account Created!", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(AccountSetup.this, "Account Creation Failed", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    TextInputLayout til = (TextInputLayout) findViewById(R.id.unameInputLayout);
                    til.setError("Username exists");
                }
            }
        }
        );
    }
}
