package com.example.joannahulek.inventoryapp;

import android.content.ContentValues;

import com.example.joannahulek.inventoryapp.Data.ProductContract.ProductEntry;

import static android.provider.ContactsContract.CommonDataKinds.Phone;

/**
 * Created by Joasia on 21.07.2017.
 */

public class Product {
    private final String productName;
    private final Integer price;
    private final Integer quantity;
    private final String supplier;
    private final Phone phone;

    public Product(String productName, Integer price, Integer quantity, String supplier, Phone phone) {
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
        // values.put(ProductProvider.ProductEntry.COLUMN_PHONE, phone);
        return values;
    }
}
