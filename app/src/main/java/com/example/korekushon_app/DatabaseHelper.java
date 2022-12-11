package com.example.korekushon_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Korekushon.db";
    public static final String TABLE_NAME = "USER_ACCOUNT_TABLE";
    public static final String TABLE_NAME1 = "USER_COLLECTION_TABLE";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "USERNAME";
    public static final String COL_3 = "EMAIL";
    public static final String COL_4 = "PASSWORD";

    public DatabaseHelper(Context context) {super(context, DATABASE_NAME, null, 1);}

    @Override
    public void onCreate(SQLiteDatabase db) {

        // CREATES USER BOOKMARK TABLE
        String CREATE_USER_COLLECTION_TABLE = "create table " + TABLE_NAME1
                + " (COLLECTION_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " PRODUCT_NAME TEXT,"
                + " PRODUCT_SUB_INFO TEXT)";

        // CREATES GUEST ACCOUNT
        String CREATE_GUEST_ACCOUNT = "insert into " + TABLE_NAME + " (USERNAME)" + "VALUES('GUEST')";

        // CREATES USER ACCOUNT TABLE
        String CREATE_ACCOUNT_TABLE = "create table " + TABLE_NAME
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " USERNAME TEXT,"
                + " EMAIL TEXT,"
                + " PASSWORD TEXT,"
                + " COLLECTION INTEGER,"
                + " FOREIGN KEY(COLLECTION) REFERENCES USER_COLLECTION_TABLE(COLLECTION_ID))";

        db.execSQL(CREATE_USER_COLLECTION_TABLE);
        db.execSQL(CREATE_ACCOUNT_TABLE);
        db.execSQL(CREATE_GUEST_ACCOUNT);

        Log.i("SQL-Log ", "on create called");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        Log.i("tag 2 ", "on upgrade called");
        onCreate(db);
    }

    public boolean insertData(String USERNAME, String EMAIL, String PASSWORD) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, USERNAME);
        contentValues.put(COL_3, EMAIL);
        contentValues.put(COL_4, PASSWORD);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }


    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public Cursor grabUser(String username) {

        Log.i("SQL", username);
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where USERNAME=?", new String[]{username});
        Log.i("SQL", res.toString());

        return res;
    }

    public String md5(String s) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes(Charset.forName("US-ASCII")),0,s.length());
            byte[] magnitude = digest.digest();
            BigInteger bi = new BigInteger(1, magnitude);
            String hash = String.format("%0" + (magnitude.length << 1) + "x", bi);
            return hash;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


}
