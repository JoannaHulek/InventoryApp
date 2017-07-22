package com.example.joannahulek.inventoryapp.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joannahulek.inventoryapp.R;
import com.example.joannahulek.inventoryapp.activities.ProductDetailsActivity;
import com.example.joannahulek.inventoryapp.data.ProductContract.ProductEntry;
import com.example.joannahulek.inventoryapp.specifics.Product;

import java.io.Serializable;

import static com.example.joannahulek.inventoryapp.data.ProductContract.ProductEntry.COLUMN_PRICE;

/**
 * Created by Joasia on 21.07.2017.
 */

public class ProductCursorAdapter extends CursorAdapter implements Serializable {
    private final Context context;
    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.store_grid_view_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        TextView nameTextView = (TextView) view.findViewById(R.id.product_name);
        TextView priceTextView = (TextView) view.findViewById(R.id.product_price);
        TextView quantityTextView = (TextView) view.findViewById(R.id.product_quantity);

        Integer productId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ProductEntry._ID)));
        String productName = cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_NAME));
        Double productPrice = Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_PRICE)));
        final Integer productQuantity = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_QUANTITY)));
        String supplierName = cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_SUPPLIER));
        String supplierPhone = cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_PHONE));

        nameTextView.setText(productName);
        priceTextView.setText(productPrice.toString() + " "+ context.getResources().getText(R.string.currency));
        quantityTextView.setText(productQuantity.toString());

        final Product currentProduct = new Product(productId, productName, productPrice, productQuantity, supplierName, supplierPhone);

        Button sellButton = (Button) view.findViewById(R.id.sell_product_button);
        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellProduct(currentProduct, productQuantity);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDetails(context, currentProduct);
            }
        });
    }

    private void goToDetails(Context context, Product currentProduct) {
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtra("current", (Serializable) currentProduct);
        context.startActivity(intent);
    }

    private Integer sellProduct (Product currentProduct, Integer productQuantity) {
        Integer newQuantity = productQuantity - 1;
        if (newQuantity < 0) {
            Toast.makeText(context, context.getResources().getString(R.string.no_more_of_this_product), Toast.LENGTH_SHORT).show();
            return productQuantity;
        } else {
            ContentValues values = currentProduct.transformToContentValues();
            values.put(ProductEntry.COLUMN_QUANTITY, newQuantity);

            Integer id = currentProduct.getId();
            context.getContentResolver().update(ProductEntry.CONTENT_URI, values, ProductEntry._ID + "=?", new String[]{id.toString()});

            return newQuantity;
        }
    }
}
