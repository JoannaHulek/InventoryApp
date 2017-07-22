package com.example.joannahulek.inventoryapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.joannahulek.inventoryapp.specifics.Product;
import com.example.joannahulek.inventoryapp.R;
import com.example.joannahulek.inventoryapp.data.ProductContract.ProductEntry;

public class AddNewProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);

        final EditText nameEditText = (EditText) findViewById(R.id.product_name_edit_text);
        final EditText priceEditText = (EditText) findViewById(R.id.price_edit_text);
        final EditText quantityEditText = (EditText) findViewById(R.id.quantity_edit_text);
        final EditText supplierEditText = (EditText) findViewById(R.id.supplier_name_edit_text);
        final EditText phoneEditText = (EditText) findViewById(R.id.supplier_phone_edit_text);

        Button saveProductButton = (Button) findViewById(R.id.save_product_button);
        saveProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct(nameEditText, priceEditText, quantityEditText, supplierEditText, phoneEditText);
            }
        });
    }

    private void addProduct(EditText nameEditText, EditText priceEditText, EditText quantityEditText,
                            EditText supplierEditText, EditText phoneEditText) {
        Product product = null;
        Uri mealUri = null;
        try {
            String name = nameEditText.getText().toString();
            Double price = Double.parseDouble(priceEditText.getText().toString());
            Integer quantity = Integer.parseInt(quantityEditText.getText().toString());
            String supplier = supplierEditText.getText().toString();
            String phone = phoneEditText.getText().toString();
            product = new Product(name, price, quantity, supplier, phone);
            mealUri = getContentResolver().insert(ProductEntry.CONTENT_URI, product.transformToContentValues());
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.insert_failed), Toast.LENGTH_SHORT).show();
        }
        if (mealUri != null) {
            Toast.makeText(AddNewProductActivity.this, getString(R.string.product_saved), Toast.LENGTH_SHORT).show();
        }
        Intent i = new Intent(AddNewProductActivity.this, StoreActivity.class);
        startActivity(i);
    }
}
