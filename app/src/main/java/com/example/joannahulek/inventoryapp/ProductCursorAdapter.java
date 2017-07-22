package com.example.joannahulek.inventoryapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.joannahulek.inventoryapp.data.ProductContract.ProductEntry;

/**
 * Created by Joasia on 21.07.2017.
 */

public class ProductCursorAdapter extends CursorAdapter {
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
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameTextView = (TextView) view.findViewById(R.id.product_name);
        TextView priceTextView = (TextView) view.findViewById(R.id.product_price);
        TextView quantityTextView = (TextView) view.findViewById(R.id.product_quantity);

        nameTextView.setText(cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_NAME)));
        priceTextView.setText(cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_PRICE)) + " "+ context.getResources().getText(R.string.currency));
        quantityTextView.setText(cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_QUANTITY)));
    }
}
