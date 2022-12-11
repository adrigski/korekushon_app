package com.example.korekushon_app.ui.browse;
import android.database.Cursor;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuProvider;
import com.example.korekushon_app.DatabaseHelper;
import com.example.korekushon_app.R;

public class ProductView extends AppCompatActivity {

    private TextView productName;
    private TextView consoleName;
    private WebView webView;
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_view);

        db = new DatabaseHelper(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.product_appbar);

        setSupportActionBar(toolbar);

        productName = findViewById(R.id.product_name);
        consoleName = findViewById(R.id.console_name);
        webView = (WebView) findViewById(R.id.webview);

        Intent i = getIntent();
        String listviewTitle = i.getStringExtra("listviewTitle");
        String listviewSecondary = i.getStringExtra("listviewSecondary");

        System.out.println("Makima Is Listening" + productName + "" + consoleName + webView);
        System.out.println("Makima Is Listening" + listviewTitle + "" + listviewSecondary);

        toolbar.setTitle(listviewTitle);


        // Back Button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        addMenuProvider(new MenuProvider() {

            @Override
            public void onCreateMenu(Menu menu, MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.back_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.bookmark:
                            boolean isInserted = db.insertBookmarkData(listviewTitle, listviewSecondary);

                            if (isInserted == true) {
                                Toast.makeText(getApplicationContext(), "Added to Saved Items", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(getApplicationContext(), "Failed to Add Item", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        // Load webpage inside webview
        if(listviewSecondary.contains("shop"))  {
            webView.loadUrl(String.format("https://otakumode.com" +
                    listviewSecondary));
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        else    {
            webView.loadUrl(String.format("https://www.pricecharting.com/game/%s/%s",
                    listviewSecondary.toLowerCase().replace(" ", "-"),
                    listviewTitle.toLowerCase().replace(" ", "-").replace(".", "")));        }
    }
}