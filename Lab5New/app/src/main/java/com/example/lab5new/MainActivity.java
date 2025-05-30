package com.example.lab5new;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Image> images = new ArrayList<Image>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setData();
        RecyclerView recyclerView = findViewById(R.id.review1);
        CustomRecyclerAdapter adapter = new CustomRecyclerAdapter(this, images);
        recyclerView.setAdapter(adapter);
    }
    private void setData(){
        images.add(new Image ("Рим", R.drawable.pic1));
        images.add(new Image ("Милан", R.drawable.pic2));
        images.add(new Image ("Неаполь", R.drawable.pic3));
        images.add(new Image ("Палермо", R.drawable.pic4));
        images.add(new Image ("Москва", R.drawable.pic5));
    }
}