package com.example.korekushon_app.ui.browse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.korekushon_app.R;

public class ProductView extends AppCompatActivity {

    private TextView productName;
    private TextView consoleName;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.topAppBar);
        Button toolbar2 = (Button) findViewById(R.id.addBookmark);


        productName = findViewById(R.id.product_name);
        consoleName = findViewById(R.id.console_name);
        webView = (WebView) findViewById(R.id.webview);


        Intent i = getIntent();
        String listviewTitle = i.getStringExtra("listviewTitle");
        String listviewSecondary = i.getStringExtra("listviewSecondary");

        System.out.println("Makima Is Listening" + productName + "" + consoleName + webView);
        System.out.println("Makima Is Listening" + listviewTitle + "" + listviewSecondary);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(listviewTitle);

        // Back Button
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        toolbar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pop up informing user that the item has been saved
                Toast.makeText(getApplicationContext(),"Added to Saved Items",Toast.LENGTH_SHORT).show();

            }
        });

//        productName.setText(product_name);
//        consoleName.setText(console_name);

        // Load webpage inside webview
        webView.loadUrl(String.format("https://www.pricecharting.com/game/%s/%s",
                listviewSecondary.toLowerCase().replace(" ", "-"),
                listviewTitle.toLowerCase().replace(" ", "-").replace(".", "")));
    }
}