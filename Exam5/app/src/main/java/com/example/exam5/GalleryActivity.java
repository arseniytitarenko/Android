package com.example.exam5;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    private int currentImage = 0;
    private ArrayList<String> images;
    private ImageView imageView;
    private TextView nameView;
    private Button btnNext, btnPrevious;

    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_CODE);
                return;
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
                return;
            }
        }

        nameView = findViewById(R.id.imageName);
        imageView = findViewById(R.id.image);
        btnNext = findViewById(R.id.btnNext);
        btnPrevious = findViewById(R.id.btnPrevious);

        btnNext.setOnClickListener(this::onNext);
        btnPrevious.setOnClickListener(this::onPrevious);

        loadImages();
        updateNavigationButtons();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                loadImages();
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                loadImages();
            }
        }
        updateNavigationButtons();
    }

    @Override
    protected void onPause() {
        super.onPause();
        images.clear();
    }

    private void loadImages() {
        images = searchImage();
        if (!images.isEmpty()) {
            updatePhoto(Uri.fromFile(new File(images.get(currentImage))));
            btnNext.setEnabled(true);
            btnPrevious.setEnabled(true);
        } else {
            nameView.setText("Нет изображений");
            btnNext.setEnabled(false);
            btnPrevious.setEnabled(false);
        }
    }

    private ArrayList<String> searchImage() {
        ArrayList<String> imagesFound = new ArrayList<>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.Media.DATA};

        String selection = MediaStore.Images.Media.MIME_TYPE + " LIKE ?";
        String[] selectionArgs = new String[] { "image%" };

        Cursor cursor = getContentResolver().query(uri, projection, selection, selectionArgs, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                imagesFound.add(imagePath);
            }
            cursor.close();
        }
        return imagesFound;
    }

    private void updatePhoto(Uri uri) {
        if (!images.isEmpty()) {
            nameView.setText((currentImage + 1) + " / " + images.size());
            imageView.setImageURI(uri);
        }
    }

    public void onNext(View v) {
        if (currentImage + 1 < images.size()) {
            currentImage++;
            updatePhoto(Uri.fromFile(new File(images.get(currentImage))));
            updateNavigationButtons();
        }
    }

    public void onPrevious(View v) {
        if (currentImage > 0) {
            currentImage--;
            updatePhoto(Uri.fromFile(new File(images.get(currentImage))));
            updateNavigationButtons();
        }
    }

    private void updateNavigationButtons() {
        btnPrevious.setEnabled(currentImage > 0);
        btnNext.setEnabled(currentImage < images.size() - 1);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadImages();
            } else {
                Toast.makeText(this, "Permission denied. Cannot load images.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(GalleryActivity.this);
            try {
                dialog.setMessage(getTitle().toString() + " версия " +
                        getPackageManager().getPackageInfo(getPackageName(), 0).versionName +
                        "\r\n\nАвтор - Титаренко Арсений Владимировия БПИ-2310");
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            dialog.setTitle("О программе");
            dialog.setNeutralButton("OK", (dialog1, which) -> dialog1.dismiss());
            dialog.setIcon(R.mipmap.ic_launcher_round);
            AlertDialog alertDialog = dialog.create();
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
