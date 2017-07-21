package com.example.joannahulek.inventoryapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

public class StoreActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    ProductCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        View emptyView = findViewById(R.id.empty_view);

        adapter = new ProductCursorAdapter(this, null);

        GridView storeGridView = (GridView) findViewById(R.id.store_grid_view);
        storeGridView.setAdapter(adapter);


        Button addNewButton = (Button) findViewById(R.id.add_new_product_button);
        addNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StoreActivity.this, AddNewProductActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}