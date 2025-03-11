package com.example.lab15;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText edit1;
    private SQLiteDatabase db;
    public static ListView listView;
    private MyCursorAdapter scAdapter;
    private String[] from = new String[]{"Name"};
    private int[] to = new int[]{R.id.listTxt};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit1 = findViewById(R.id.addTxt);
        SharedPreferences save = getSharedPreferences("SAVE",0);
        edit1.setText(save.getString("text",""));

        listView = findViewById(R.id.lstView);
        Button btnAdd = findViewById(R.id.addBtn);

        db = openOrCreateDatabase("DBName", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS MyTable5 (_id INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT);");

        loadList();

        btnAdd.setOnClickListener(view -> {
            String text = edit1.getText().toString().trim();
            if (!text.isEmpty()) {
                db.execSQL("INSERT INTO MyTable5 (Name) VALUES (?);", new Object[]{text});
                loadList();
                Toast.makeText(this, "Строка добавлена в таблицу", Toast.LENGTH_LONG).show();
                edit1.setText("");
            } else {
                Toast.makeText(this, "Введите текст", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadList() {
        Cursor cursor = db.rawQuery("SELECT * FROM MyTable5", null);
        scAdapter = new MyCursorAdapter(this, R.layout.list_item, cursor, from, to);
        listView.setAdapter(scAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.about_settings) {
            showAboutDialog();
        } else if (id == R.id.settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAboutDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        try {
            dialog.setMessage(getTitle().toString() + " версия " +
                    getPackageManager().getPackageInfo(getPackageName(), 0).versionName +
                    "\r\n\nАвтор - Титаренко Арсений Владимировия БПИ-2310");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        dialog.setTitle("О программе")
                .setNeutralButton("OK", (dialog1, which) -> dialog1.dismiss())
                .setIcon(R.mipmap.ic_launcher_round)
                .create()
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onStop() {
        SharedPreferences save = getSharedPreferences("SAVE", 0);
        save.edit().putString("text", edit1.getText().toString()).apply();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (db != null) db.close();
        super.onDestroy();
    }
}