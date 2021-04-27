
package com.example.bookstore.CreateListing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bookstore.Authentication.Fragments.Step1Fragment;
import com.example.bookstore.Authentication.Fragments.Step2Fragment;
import com.example.bookstore.Authentication.Fragments.Step3Fragment;
import com.example.bookstore.Authentication.Fragments.Step4Fragment;
import com.example.bookstore.CreateListing.Fragments.CreateListingStep1Fragment;
import com.example.bookstore.CreateListing.Fragments.CreateListingStep2Fragment;
import com.example.bookstore.CreateListing.Fragments.CreateListingStep3Fragment;
import com.example.bookstore.CreateListing.Fragments.CreateListingStep4Fragment;
import com.example.bookstore.CreateListing.Fragments.CreateListingStep5Fragment;
import com.example.bookstore.CreateListing.Fragments.Listener;
import com.example.bookstore.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CreateListingActivity extends AppCompatActivity implements Listener,CreateListingStep4Fragment.ImageCapture {

    private ProgressBar progressBar;
    private String currentPhotoPath;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);
        progressBar=findViewById(R.id.progress_bar);

        Toolbar supportToolBar= findViewById(R.id.tool_bar);
        progressBar =findViewById(R.id.progress_bar);

        setSupportActionBar(supportToolBar);

        ActionBar actionBar= getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            setTitle("Sell your book");
        }
        addFirstFragment();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int count = getSupportFragmentManager().getFragments().size();
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        Fragment fragment=fragments.get(fragments.size()-1);
        if(fragment instanceof CreateListingStep1Fragment)
        {

            progressBar.setProgress(20);
        }
        if(fragment instanceof CreateListingStep2Fragment)
        {

            progressBar.setProgress(40);
        }
        if(fragment instanceof CreateListingStep3Fragment)
        {

            progressBar.setProgress(60);
        }
        if(fragment instanceof CreateListingStep4Fragment)
        {

            progressBar.setProgress(80);
        }
        if(fragment instanceof CreateListingStep5Fragment)
        {

            progressBar.setProgress(100);
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNextClick(String nextFragName) {
        Log.v("onNextClick",nextFragName);
        Fragment nextFragment=null;
        switch (nextFragName)
        {
            case "step1":
                nextFragment=new CreateListingStep1Fragment();
                break;
            case "step2":
                nextFragment=new CreateListingStep2Fragment();
                progressBar.setProgress(40);
                break;
            case "step3":
                nextFragment=new CreateListingStep3Fragment();
                progressBar.setProgress(60);

                break;
            case "step4":
                nextFragment=new CreateListingStep4Fragment();
                progressBar.setProgress(80);

                break;
            case "step5":
                nextFragment=new CreateListingStep5Fragment();
                progressBar.setProgress(100);

                break;
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        CreateListingStep1Fragment fragment = new CreateListingStep1Fragment();
        fragmentTransaction.replace(R.id.fragmentContainer,nextFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void addFirstFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        CreateListingStep1Fragment fragment = new CreateListingStep1Fragment();
        fragmentTransaction.add(R.id.fragmentContainer,fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void showMeassage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void captureImage() {

        dispatchTakePictureIntent();

    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                Log.d("photoUri",photoURI.toString());
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_IMAGE_CAPTURE&&resultCode==RESULT_OK)
        {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
            setPic();

        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        Log.d("imagePath",currentPhotoPath);
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
    private void setPic() {
        // Get the dimensions of the View
        ImageView imageView=getSupportFragmentManager().getFragments().get(0).getView().findViewById(R.id.image_view);
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.max(1, Math.min(photoW/targetW, photoH/targetH));

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }
}