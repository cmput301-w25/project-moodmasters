package com.example.moodmasters.Objects.ObjectsApp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Static Class that is responsible for decoding and encoding photos to and from the database
 * */
public class PhotoDecoderEncoder {
    /**
     * Photo Encoding function
     * @param bitmap
     *  Bitmap parameter that will be encoded for easy storage on database
     * @return
     *  String for bitmap
     * */
    public static String photoEncoder(Bitmap bitmap){
        ByteArrayOutputStream baos =new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }
    /**
     * Photo bitmap_string function
     * @param bitmap_string
     *  String parameter that will be decoded to a Bitmap for user viewing
     * @return
     *  Bitmap for string
     * */
    public static Bitmap photoDecoder(String bitmap_string){
        byte [] encode_byte= Base64.decode(bitmap_string, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(encode_byte, 0, encode_byte.length);
    }
}
