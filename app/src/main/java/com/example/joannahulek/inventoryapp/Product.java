package com.example.joannahulek.inventoryapp;

import android.content.ContentValues;

import com.example.joannahulek.inventoryapp.data.ProductContract.ProductEntry;

/**
 * Created by Joasia on 21.07.2017.
 */

public class Product {
    private final String productName;
    private final Double price;
    private final Integer quantity;
    private final String supplier;
    private final String phone;

    public Product(String productName, Double price, Integer quantity, String supplier, String phone) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.supplier = supplier;
        this.phone = phone;
    }

    public ContentValues transformToContentValues() {
        ContentValues values = new ContentValues();
        values.put(ProductEntry.COLUMN_NAME, productName);
        values.put(ProductEntry.COLUMN_PRICE, price);
        values.put(ProductEntry.COLUMN_QUANTITY, quantity);
        values.put(ProductEntry.COLUMN_SUPPLIER, supplier);
        values.put(ProductEntry.COLUMN_PHONE, phone);
        return values;
    }
}
