package com.example.lab9;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView textView = findViewById(R.id.text1);
        registerForContextMenu(textView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.contextmenu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        TextView textView = (TextView) findViewById(R.id.text1);
        if (id == R.id.color_red){
            textView.setTextColor(Color.parseColor("red"));
        } else if (id == R.id.color_black){
            textView.setTextColor(Color.parseColor("black"));
        } else if (id == R.id.increase_font) {
            float currentSize = textView.getTextSize();
            textView.setTextSize(currentSize / getResources().getDisplayMetrics().scaledDensity + 2);
        } else if (id == R.id.decrease_font) {
            float currentSize = textView.getTextSize();
            textView.setTextSize(currentSize / getResources().getDisplayMetrics().scaledDensity - 2);
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.show_text) {
            TextView textView = findViewById(R.id.text1);
            if (item.isChecked()) {
                textView.setVisibility(View.VISIBLE);
                item.setChecked(false);
            } else {
                textView.setVisibility(View.INVISIBLE);
                item.setChecked(true);
            }
        } else if (id == R.id.about) {
            Toast.makeText(MainActivity.this, R.string.fio, Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

}