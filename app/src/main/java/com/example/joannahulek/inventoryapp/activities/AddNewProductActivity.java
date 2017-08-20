package com.example.joannahulek.inventoryapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.joannahulek.inventoryapp.specifics.Product;
import com.example.joannahulek.inventoryapp.R;
import com.example.joannahulek.inventoryapp.data.ProductContract.ProductEntry;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.joannahulek.inventoryapp.utilities.BitmapUtilities.setBitmap;

public class AddNewProductActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String TAG = AddNewProductActivity.class.getSimpleName();
    private String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);
        addInteractions();
    }

    private void addInteractions() {
        final EditText nameEditText = (EditText) findViewById(R.id.product_name_edit_text);
        final EditText priceEditText = (EditText) findViewById(R.id.price_edit_text);
        final EditText quantityEditText = (EditText) findViewById(R.id.quantity_edit_text);
        final EditText supplierEditText = (EditText) findViewById(R.id.supplier_name_edit_text);
        final EditText phoneEditText = (EditText) findViewById(R.id.supplier_phone_edit_text);

        findViewById(R.id.add_image_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                            File photoFile = null;
                            try {
                                photoFile = createImageFile();
                            } catch (IOException ex) {
                                Log.e(TAG, "error while creating image file", ex);
                            }
                            if (photoFile != null) {
                                Uri photoURI = FileProvider.getUriForFile(AddNewProductActivity.this,
                                        "com.example.android.fileprovider",
                                        photoFile);
                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                            }
                        }
                    }
                });
        findViewById(R.id.save_product_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addProduct(nameEditText, priceEditText, quantityEditText, supplierEditText, phoneEditText);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            setPreview();
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = SimpleDateFormat.getDateInstance().format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void setPreview() {
        final ImageView productImagePreview = (ImageView) findViewById(R.id.product_image_preview);
        setBitmap(productImagePreview, mCurrentPhotoPath);
    }

    private void addProduct(EditText nameEditText, EditText priceEditText, EditText quantityEditText,
                            EditText supplierEditText, EditText phoneEditText) {
        Product product;
        Uri productUri = null;
        try {
            String name = nameEditText.getText().toString();
            Double price = Double.parseDouble(priceEditText.getText().toString());
            Integer quantity = Integer.parseInt(quantityEditText.getText().toString());
            String imageUri = mCurrentPhotoPath;
            String supplier = supplierEditText.getText().toString();
            String phone = phoneEditText.getText().toString();
            product = new Product(name, price, quantity, imageUri, supplier, phone);
            productUri = getContentResolver().insert(ProductEntry.CONTENT_URI, product.transformToContentValues());
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.insert_failed), Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Exception while product saving", e);
        }
        if (productUri != null) {
            Toast.makeText(AddNewProductActivity.this, getString(R.string.product_saved), Toast.LENGTH_SHORT).show();
        }
        Intent i = new Intent(AddNewProductActivity.this, StoreActivity.class);
        startActivity(i);
    }
}