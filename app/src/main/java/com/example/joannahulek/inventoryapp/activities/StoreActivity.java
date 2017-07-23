package com.example.joannahulek.inventoryapp.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.example.joannahulek.inventoryapp.R;
import com.example.joannahulek.inventoryapp.adapters.ProductCursorAdapter;

import static com.example.joannahulek.inventoryapp.data.ProductContract.ProductEntry;

public class StoreActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    ProductCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        adapter = new ProductCursorAdapter(this, null);

        GridView storeGridView = (GridView) findViewById(R.id.store_grid_view);
        storeGridView.setEmptyView(findViewById(R.id.empty_view));
        storeGridView.setAdapter(adapter);
        getSupportLoaderManager().initLoader(0, null, this);

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
        String[] projection = {
                ProductEntry._ID,
                ProductEntry.COLUMN_NAME,
                ProductEntry.COLUMN_PRICE,
                ProductEntry.COLUMN_QUANTITY,
                ProductEntry.COLUMN_SUPPLIER,
                ProductEntry.COLUMN_PHONE};
        return new CursorLoader(this, ProductEntry.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}