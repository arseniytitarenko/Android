package com.example.exam6;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class AddContactActivity extends AppCompatActivity {
    private EditText editName, editPhone;
    private Button btnSave;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        editName = findViewById(R.id.textName);
        editPhone = findViewById(R.id.textPhone);
        btnSave = findViewById(R.id.btnSave);
        db = AppDatabase.getInstance(this);

        btnSave.setOnClickListener(v -> {
            Contact contact = new Contact();
            contact.name = editName.getText().toString();
            contact.phone = editPhone.getText().toString();
            db.contactDao().insert(contact);
            finish();
        });
    }
}
