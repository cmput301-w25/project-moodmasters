package com.example.moodmasters.Events;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;

import com.example.moodmasters.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageProcessing {
    private static final long MAX_IMAGE_SIZE = 65536;
    private void getImageSize(String imagePath) {
        // Grabs the size of the image
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            float fileSizeinBytes = imageFile.length();
            if (fileSizeinBytes > MAX_IMAGE_SIZE) {
                compressImage(imagePath);
            }
            // The image is fine if it is smaller than the max image size
        }
    }

    private void compressImage(String imagePath) {
        // Compresses the image from its string path
        Bitmap originalBitMap = BitmapFactory.decodeFile(imagePath);
        int width = originalBitMap.getWidth();
        int height = originalBitMap.getHeight();

        // Can be scaled differently if needed
        int scaledWidth = width / 2;
        int scaledHeight = height / 2;

        // Scale the image
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitMap, scaledWidth, scaledHeight, true);

        // Compress
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        boolean isCompressed = scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);

        if (isCompressed) {
            // Convert the BipMap back to bytes
            byte[] compressedBytes = output.toByteArray();
            // Length of the compressedBytes array is how many bytes the compressed image is
            saveCompressedImage(compressedBytes, imagePath);
        }
    }

    private void saveCompressedImage(byte[] compressedBytes, String imagePath) {
        // Saves the compressed image back into the old file
        File compressedFile = new File(imagePath); // Writing the compressed file onto the old one
        try (FileOutputStream fos = new FileOutputStream(compressedFile)) {
            fos.write(compressedBytes); // Write the byte array to the file
            fos.flush(); // Ensures all the data is written to the file
        } catch (IOException e) { // Handles exceptions!
            System.err.println("Error!" + e.getMessage());
        }
    }
}


