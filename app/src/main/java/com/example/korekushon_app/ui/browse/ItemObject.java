package com.example.korekushon_app.ui.browse;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ItemObject {

    String console_name, product_id, product_name;

    public ItemObject(String console_name, String product_name, String product_id) {
        this.console_name = console_name;
        this.product_name = product_name;
        this.product_id = product_id;
    }

    public String getConsole_name() {
        return console_name;
    }

    public void setConsole_name(String console_name) {
        this.console_name = console_name;
    }

    public Bitmap getProduct_id() throws IOException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        URL url = new URL(product_id);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.connect();
        InputStream input = connection.getInputStream();
        Bitmap myBitmap = BitmapFactory.decodeStream(input);
        return myBitmap;  }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

}
