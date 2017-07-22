package com.example.joannahulek.inventoryapp.activities;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joannahulek.inventoryapp.R;
import com.example.joannahulek.inventoryapp.data.ProductContract.ProductEntry;
import com.example.joannahulek.inventoryapp.specifics.Product;

import static com.example.joannahulek.inventoryapp.R.id.decrement_quantity_button;
import static com.example.joannahulek.inventoryapp.R.id.increment_quantity_button;
import static com.example.joannahulek.inventoryapp.R.id.product_name;
import static com.example.joannahulek.inventoryapp.R.id.product_price;
import static com.example.joannahulek.inventoryapp.R.id.product_quantity;
import static com.example.joannahulek.inventoryapp.R.id.supplier_name_text_view;
import static com.example.joannahulek.inventoryapp.R.id.supplier_phone_text_view;

public class ProductDetailsActivity extends AppCompatActivity {

    Integer productQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        final Product currentProduct = (Product) getIntent().getSerializableExtra("current");

        TextView nameTextView = (TextView) findViewById(product_name);
        TextView priceTextView = (TextView) findViewById(product_price);
        final TextView quantityTextView = (TextView) findViewById(product_quantity);
        TextView supplierTextView = (TextView) findViewById(supplier_name_text_view);
        TextView phoneTextView = (TextView) findViewById(supplier_phone_text_view);

        productQuantity = currentProduct.getQuantity();

        nameTextView.setText(currentProduct.getProductName());
        priceTextView.setText(currentProduct.getPrice().toString());
        quantityTextView.setText(productQuantity.toString());
        supplierTextView.setText(currentProduct.getSupplier());
        phoneTextView.setText(currentProduct.getPhone());

        final Button incrementQuantity = (Button) findViewById(increment_quantity_button);
        Button decrementQuantity = (Button) findViewById(decrement_quantity_button);

        incrementQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productQuantity = increment(currentProduct, productQuantity);
                quantityTextView.setText(productQuantity.toString());
            }
        });

        decrementQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productQuantity = decrement(currentProduct, productQuantity);
                quantityTextView.setText(productQuantity.toString());
            }
        });

    }

    private Integer decrement(Product currentProduct, Integer productQuantity) {
        Integer newQuantity = productQuantity - 1;
        if (newQuantity < 0) {
            Toast.makeText(this, getString(R.string.no_more_of_this_product), Toast.LENGTH_SHORT).show();
            return productQuantity;
        } else {
            ContentValues values = currentProduct.transformToContentValues();
            values.put(ProductEntry.COLUMN_QUANTITY, newQuantity);

            Integer id = currentProduct.getId();
            getContentResolver().update(ProductEntry.CONTENT_URI, values, ProductEntry._ID + "=?", new String[]{id.toString()});

            return newQuantity;
        }
    }

    private Integer increment(Product currentProduct, Integer productQuantity) {
        Integer newQuantity = productQuantity + 1;
        ContentValues values = currentProduct.transformToContentValues();
        values.put(ProductEntry.COLUMN_QUANTITY, newQuantity);

        Integer id = currentProduct.getId();
        getContentResolver().update(ProductEntry.CONTENT_URI, values, ProductEntry._ID + "=?", new String[]{id.toString()});
        return newQuantity;
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        if (bundle != null)
            productQuantity = (Integer) bundle.get("quantity");
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        if (bundle != null)
            bundle.putSerializable("quantity", productQuantity);
        super.onSaveInstanceState(bundle);
    }
}
