package com.example.lab12new;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.lab12new.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private FloatingActionButton fab;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.tchaikovsky);

        fab.setOnClickListener(view -> {
            if (isPlaying) {
                mediaPlayer.pause();
                fab.setImageResource(android.R.drawable.ic_media_play);
            } else {
                mediaPlayer.start();
                fab.setImageResource(android.R.drawable.ic_media_pause);
                Snackbar.make(view, "You should hear a sound", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            isPlaying = !isPlaying;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            try {
                dialog.setMessage(getTitle().toString()+ " версия "+
                        getPackageManager().getPackageInfo(getPackageName(),0).versionName +
                        "\r\n\nПрограмма с воспроизведением звука \r\n\nАвтор - Титаренко Арсений Владимировия БПИ-2310");
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            dialog.setTitle("О программе");
            dialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.setIcon(R.mipmap.ic_launcher_round);
            AlertDialog alertDialog = dialog.create();
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}