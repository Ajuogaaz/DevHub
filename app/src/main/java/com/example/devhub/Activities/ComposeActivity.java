package com.example.devhub.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.devhub.R;
import com.example.devhub.databinding.ActivityComposeBinding;
import com.example.devhub.databinding.ActivityProfileBinding;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class ComposeActivity extends AppCompatActivity {

    private static final String TAG = "ComposeActivity";
    ActivityComposeBinding binding;
    String ImageUrl;
    File photoFile;
    public String photoFileName = "post.jpg";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityComposeBinding.inflate(getLayoutInflater());

        final View view = binding.getRoot();
        setContentView(view);

        binding.tvName.setText(ParseUser.getCurrentUser().getString("PreferredName"));
        binding.gitHubUserName.setText(ParseUser.getCurrentUser().getString("gitHubUserName"));


        if(ParseUser.getCurrentUser().getBoolean("HasUploadedPic")){
            ImageUrl = ParseUser.getCurrentUser().getParseFile("ProfilePic").getUrl();
        }else{
            ImageUrl = ParseUser.getCurrentUser().getString("githubProfilePic");
        }

        if (!ImageUrl.isEmpty()) {
            Glide.with(this)
                    .load(ImageUrl)
                    .into(binding.ivProfileImage);
        }

        binding.Cancel.setOnClickListener(view1 -> {

            Toast.makeText(this, "Cancel Clicked", Toast.LENGTH_SHORT).show();

        });
        binding.toolbarPost.setOnClickListener(view2 ->{

            Toast.makeText(this, "Posting", Toast.LENGTH_SHORT).show();
        });

        binding.CameraIcon.setOnClickListener(view3 ->{
            Toast.makeText(this, "Camera", Toast.LENGTH_SHORT).show();
            TakePictureFromCamera();

        });

        binding.GalleryIcon.setOnClickListener(view4 -> {
            Toast.makeText(this, "Gallery", Toast.LENGTH_SHORT).show();
            TakePictureFromGallery();
        });


    }

    private void TakePictureFromGallery() {
    }

    private void TakePictureFromCamera() {

        launchCamera();
    }

    private void launchCamera() {
        //Create an intent to take pictures and return control to the app
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Create a file reference to access to future access
        photoFile = getPhotoFileUri(photoFileName);

        //wrap file object into a content provider
        Uri fileProvider = FileProvider.getUriForFile(this, "com.codepath.fileprovider.DevHub", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }

    }

    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.i(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    // by this point we have the camera photo on disk
                    Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                    // RESIZE BITMAP, see section below
                    // Load the taken image into a preview
                    binding.PostImage.setImageBitmap(takenImage);
                    binding.PostImage.setVisibility(View.VISIBLE);
                } else { // Result was a failure
                    Toast.makeText(ComposeActivity.this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
                }

            }/*if((data != null) && requestCode == PICK_PHOTO_CODE){
                Uri photoUri = data.getData();
                //Load the image located in the photo Uri
                Bitmap selectedImage = loadFromUri(photoUri);

                ivPostImage.setImageBitmap(selectedImage);*/

        }
    }



}