package com.example.exam6;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditContactActivity extends AppCompatActivity {
    private EditText nameInput, phoneInput, commentInput;
    private Button saveButton;
    private Contact contact;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_contact);

        nameInput = findViewById(R.id.nameInput);
        phoneInput = findViewById(R.id.phoneInput);
        commentInput = findViewById(R.id.commentInput);
        saveButton = findViewById(R.id.saveButton);

        databaseHelper = new DatabaseHelper(this);
        databaseHelper.open();

        if (getIntent().hasExtra("contact")) {
            contact = (Contact) getIntent().getSerializableExtra("contact");
            nameInput.setText(contact.getName());
            phoneInput.setText(contact.getPhone());
            commentInput.setText(contact.getComment());
        } else {
            contact = new Contact("", "", "");
        }

        saveButton.setOnClickListener(v -> {
            contact.setName(nameInput.getText().toString());
            contact.setPhone(phoneInput.getText().toString());
            contact.setComment(commentInput.getText().toString());

            Intent resultIntent = new Intent();
            resultIntent.putExtra("contact", contact);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHelper.close();
    }
}
