package com.example.lab2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = findViewById(R.id.textViewOutput);
        final EditText editText = findViewById(R.id.editTextInput);
        Button button = findViewById(R.id.button);
        textView.setText("");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editText.getText().toString().isEmpty()) {
                    String reversed = new StringBuilder(editText.getText().toString()).reverse().toString();
                    textView.setText("Ваш текст наоборот: " + reversed);
                } else {
                    textView.setText("");
                }


            }
        });
    }
}