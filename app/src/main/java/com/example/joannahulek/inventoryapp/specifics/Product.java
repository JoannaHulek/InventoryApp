package com.example.joannahulek.inventoryapp.specifics;

import android.content.ContentValues;

import com.example.joannahulek.inventoryapp.data.ProductContract.ProductEntry;

import java.io.Serializable;
import java.math.BigDecimal;

import static android.provider.BaseColumns._ID;

/**
 * Created by Joasia on 21.07.2017.
 */

public class Product implements Serializable {
    public Integer getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getSupplier() {
        return supplier;
    }

    public String getPhone() {
        return phone;
    }

    private final Integer id;
    private final String productName;
    private final Double price;
    private final Integer quantity;
    private final String supplier;
    private final String phone;

    public Product(Integer id, String productName, Double price, Integer quantity, String supplier, String phone) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.supplier = supplier;
        this.phone = phone;
        this.id = id;
    }

    public Product(String productName, Double price, Integer quantity, String supplier, String phone) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.supplier = supplier;
        this.phone = phone;
        id = null;
    }

    public ContentValues transformToContentValues() {
        ContentValues values = new ContentValues();
        values.put(ProductEntry.COLUMN_NAME, productName);
        values.put(ProductEntry.COLUMN_PRICE, BigDecimal.valueOf(price).setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
        values.put(ProductEntry.COLUMN_QUANTITY, quantity);
        values.put(ProductEntry.COLUMN_SUPPLIER, supplier);
        values.put(ProductEntry.COLUMN_PHONE, phone);
        return values;
    }
}
