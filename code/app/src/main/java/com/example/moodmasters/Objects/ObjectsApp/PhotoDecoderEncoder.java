package com.example.moodmasters.Objects.ObjectsApp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class PhotoDecoderEncoder {
    public static String photoEncoder(Bitmap bitmap) {
        // Compress image and log the size
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos); // Using JPEG compression
        byte[] b = baos.toByteArray();

        // Log the size of the compressed photo
        Log.d("PhotoEncoder", "Compressed photo size: " + b.length + " bytes");

        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static Bitmap photoDecoder(String bitmap_string){
        byte [] encode_byte= Base64.decode(bitmap_string, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(encode_byte, 0, encode_byte.length);
    }
}
