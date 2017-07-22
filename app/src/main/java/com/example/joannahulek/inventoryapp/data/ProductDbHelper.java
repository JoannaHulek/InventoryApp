package com.example.joannahulek.inventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.joannahulek.inventoryapp.data.ProductContract.ProductEntry.*;
import static com.example.joannahulek.inventoryapp.data.ProductContract.ProductEntry.COLUMN_NAME;
import static com.example.joannahulek.inventoryapp.data.ProductContract.ProductEntry.COLUMN_PHONE;
import static com.example.joannahulek.inventoryapp.data.ProductContract.ProductEntry.COLUMN_PRICE;
import static com.example.joannahulek.inventoryapp.data.ProductContract.ProductEntry.COLUMN_QUANTITY;
import static com.example.joannahulek.inventoryapp.data.ProductContract.ProductEntry.COLUMN_SUPPLIER;
import static com.example.joannahulek.inventoryapp.data.ProductContract.ProductEntry.TABLE_NAME;

/**
 * Created by Joasia on 20.07.2017.
 */

public class ProductDbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = ProductDbHelper.class.getSimpleName();
    private static final String SQL_CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + COLUMN_PRICE + " DOUBLE NOT NULL DEFAULT 0, "
            + COLUMN_QUANTITY + " INTEGER NOT NULL DEFAULT 0, "
            + COLUMN_SUPPLIER + " TEXT, "
            + COLUMN_PHONE + " TEXT NOT NULL);";
    private static final String DATABASE_NAME = "productslist.db";
    private static final int DATABASE_VERSION = 2;

    public ProductDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2) {
            db.execSQL("DROP TABLE " + TABLE_NAME);
            db.execSQL(SQL_CREATE_PRODUCTS_TABLE);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
