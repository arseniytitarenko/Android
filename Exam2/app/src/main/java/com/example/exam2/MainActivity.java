package com.example.exam2;

import android.content.pm.PackageManager;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GestureOverlayView.OnGesturePerformedListener {

    GestureLibrary gLib;
    GestureOverlayView gestures;
    TextView tvOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gLib = GestureLibraries.fromRawResource(this, R.raw.gestures);
        if (!gLib.load()) {
            finish();
        }
        gestures = findViewById(R.id.gestureOverlayView1);
        tvOut = findViewById(R.id.textView);
        gestures.addOnGesturePerformedListener(this);
    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList<Prediction> predictions = gLib.recognize(gesture);
        if (!predictions.isEmpty()) {
            Prediction prediction = predictions.get(0);
            if (prediction.score > 3.0) {
                tvOut.setText(prediction.name);
            } else {
                tvOut.setText("Жест неизвестен");
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
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
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