package com.example.joannahulek.inventoryapp.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joannahulek.inventoryapp.R;
import com.example.joannahulek.inventoryapp.activities.ProductDetailsActivity;
import com.example.joannahulek.inventoryapp.data.ProductContract.ProductEntry;
import com.example.joannahulek.inventoryapp.specifics.Product;

import java.io.File;
import java.io.Serializable;

import static com.example.joannahulek.inventoryapp.data.ProductContract.ProductEntry.COLUMN_PRICE;
import static com.example.joannahulek.inventoryapp.utilities.BitmapUtilities.setBitmap;

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

        ImageView productImageView = (ImageView) view.findViewById(R.id.product_image_view);
        TextView nameTextView = (TextView) view.findViewById(R.id.product_name);
        TextView priceTextView = (TextView) view.findViewById(R.id.product_price);
        TextView quantityTextView = (TextView) view.findViewById(R.id.product_quantity);
        final Product product = getProductFromCursor(cursor);
        if (product.getImageUri() == null) {
            productImageView.setImageResource(R.drawable.default_product_image);
        } else {
            setBitmap(productImageView, product.getImageUri());
        }

        nameTextView.setText(product.getProductName());
        priceTextView.setText(String.format("%s %s", product.getPrice(), context.getResources().getText(R.string.currency)));
        quantityTextView.setText(String.format("%s", product.getQuantity()));

        Button sellButton = (Button) view.findViewById(R.id.sell_product_button);
        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellProduct(product);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDetails(context, product);
            }
        });
    }

    private Product getProductFromCursor(final Cursor cursor) {
        Integer productId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ProductEntry._ID)));
        String productName = cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_NAME));
        Double productPrice = Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_PRICE)));
        Integer productQuantity = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_QUANTITY)));
        String imageUri = cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_IMAGE_URI));
        String supplierName = cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_SUPPLIER));
        String supplierPhone = cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_PHONE));
        return new Product(productId, productName, productPrice, productQuantity, imageUri, supplierName, supplierPhone);
    }

    private void goToDetails(Context context, Product currentProduct) {
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtra("current", currentProduct);
        context.startActivity(intent);
    }

    private void sellProduct(Product currentProduct) {
        Integer newQuantity = currentProduct.getQuantity() - 1;
        if (newQuantity < 0) {
            Toast.makeText(context, context.getResources().getString(R.string.no_more_of_this_product), Toast.LENGTH_SHORT).show();
        } else {
            ContentValues values = currentProduct.transformToContentValues();
            values.put(ProductEntry.COLUMN_QUANTITY, newQuantity);
            Integer id = currentProduct.getId();
            context.getContentResolver().update(ProductEntry.CONTENT_URI, values, ProductEntry._ID + "=?", new String[]{id.toString()});
        }
    }
}
