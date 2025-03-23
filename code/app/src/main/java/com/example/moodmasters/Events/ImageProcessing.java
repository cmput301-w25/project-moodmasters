package com.example.moodmasters.Events;

// Unused until project part 4

//public class ImageProcessing extends AppCompatActivity {
//    private static final long MAX_IMAGE_SIZE = 65536;
//    private static final int PICK_IMAGE_REQUEST = 1;
//    private ImageView selectedImageView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.alter_mood_screen);
//
//        Button selectImageButton = findViewById(R.id.alter_mood_photo_button);
//        selectedImageView = findViewById(R.id.alter_mood_image_view);
//
//        selectImageButton.setOnClickListener(v -> openGallery());
//    }
//    private void openGallery() {
//        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//        startActivityForResult(intent, PICK_IMAGE_REQUEST);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
//            Uri imageUri = data.getData();
//            if (imageUri != null) {
//                selectedImageView.setImageURI(imageUri);  // Set the selected image to ImageView
//            } else {
//                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//    private void getImageSize(String imagePath) {
//        // Grabs the size of the image
//        File imageFile = new File(imagePath);
//        if (imageFile.exists()) {
//            float fileSizeinBytes = imageFile.length();
//            if (fileSizeinBytes > MAX_IMAGE_SIZE) {
//                compressImage(imagePath);
//            }
//            // The image is fine if it is smaller than the max image size
//        }
//    }
//
//    private void compressImage(String imagePath) {
//        // Compresses the image from its string path
//        Bitmap originalBitMap = BitmapFactory.decodeFile(imagePath);
//        int width = originalBitMap.getWidth();
//        int height = originalBitMap.getHeight();
//
//        // Can be scaled differently if needed
//        int scaledWidth = width / 2;
//        int scaledHeight = height / 2;
//
//        // Scale the image
//        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitMap, scaledWidth, scaledHeight, true);
//
//        // Compress
//        ByteArrayOutputStream output = new ByteArrayOutputStream();
//        boolean isCompressed = scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
//
//        if (isCompressed) {
//            // Convert the BipMap back to bytes
//            byte[] compressedBytes = output.toByteArray();
//            // Length of the compressedBytes array is how many bytes the compressed image is
//            saveCompressedImage(compressedBytes, imagePath);
//        }
//    }
//
//    private void saveCompressedImage(byte[] compressedBytes, String imagePath) {
//        // Saves the compressed image back into the old file
//        File compressedFile = new File(imagePath); // Writing the compressed file onto the old one
//        try (FileOutputStream fos = new FileOutputStream(compressedFile)) {
//            fos.write(compressedBytes); // Write the byte array to the file
//            fos.flush(); // Ensures all the data is written to the file
//        } catch (IOException e) { // Handles exceptions!
//            System.err.println("Error!" + e.getMessage());
//        }
//    }
//}


