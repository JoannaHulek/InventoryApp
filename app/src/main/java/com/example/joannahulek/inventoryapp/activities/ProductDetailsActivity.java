package com.example.joannahulek.inventoryapp.activities;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

    private Integer productQuantity;
    private Uri currentProductUri;
    String supplierPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        final Product currentProduct = (Product) getIntent().getSerializableExtra("current");
        fillData(currentProduct);

        if (savedInstanceState != null && savedInstanceState.containsKey("quantity"))
            productQuantity = savedInstanceState.getInt("quantity");
        else
            productQuantity = currentProduct.getQuantity();

        final TextView quantityTextView = (TextView) findViewById(product_quantity);
        quantityTextView.setText(productQuantity.toString());

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

        Button removeProductButton = (Button) findViewById(R.id.remove_product_button);
        removeProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRemoveConfirmationDialog();
            }
        });

        Button orderProductButton = (Button) findViewById(R.id.order_product_button);
        orderProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + supplierPhoneNumber));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

    private void fillData(Product currentProduct) {
        currentProductUri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, currentProduct.getId());
        supplierPhoneNumber = currentProduct.getPhone();

        TextView nameTextView = (TextView) findViewById(product_name);
        TextView priceTextView = (TextView) findViewById(product_price);
        TextView supplierTextView = (TextView) findViewById(supplier_name_text_view);
        TextView phoneTextView = (TextView) findViewById(supplier_phone_text_view);

        nameTextView.setText(currentProduct.getProductName());
        priceTextView.setText(currentProduct.getPrice().toString());
        supplierTextView.setText(currentProduct.getSupplier());
        phoneTextView.setText(supplierPhoneNumber);
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
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        if (bundle != null)
            bundle.putInt("quantity", productQuantity);
        super.onSaveInstanceState(bundle);
    }

    private void showRemoveConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.remove_dialog_msg);
        builder.setPositiveButton(R.string.remove, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                removeProduct();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        builder.create().show();
    }

    private void removeProduct() {
        if (currentProductUri != null) {
            int rowsDeleted = getContentResolver().delete(currentProductUri, null, null);
            if (rowsDeleted == 0) {
                Toast.makeText(this, getString(R.string.remove_product_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.remove_product_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }
}
