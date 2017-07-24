package com.example.joannahulek.inventoryapp.utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by Joasia on 24.07.2017.
 */
public class BitmapUtilities {
    private BitmapUtilities() {
    }
    public static void setBitmap(ImageView imageHolder, String imagePath) {
        File picture = new File(imagePath);
        if (picture.exists()) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap myBitmap = BitmapFactory.decodeFile(picture.getAbsolutePath(), options);
            imageHolder.setImageBitmap(myBitmap);
        }
    }
}
