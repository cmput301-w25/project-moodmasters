package com.example.moodmasters.Objects.ObjectsApp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class PhotoDecoderEncoder {
    public static String photoEncoder(Bitmap bitmap){
        ByteArrayOutputStream baos =new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }
    public static Bitmap photoDecoder(String bitmap_string){
        byte [] encode_byte= Base64.decode(bitmap_string, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(encode_byte, 0, encode_byte.length);
    }
}
