package com.example.korekushon_app.ui.browse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;;
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

        productName = findViewById(R.id.product_name);
        consoleName = findViewById(R.id.console_name);
        webView = (WebView) findViewById(R.id.webview);

        Intent i = getIntent();
        String listviewTitle = i.getStringExtra("listviewTitle");
        String listviewSecondary = i.getStringExtra("listviewSecondary");

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(listviewTitle);

        // Back Button
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onBackPressed();
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