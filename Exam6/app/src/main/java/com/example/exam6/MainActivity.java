package com.example.exam6;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AppDatabase db;
    private ListView listView;
    private Button btnAdd;
    private List<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getInstance(this);
        listView = findViewById(R.id.listView);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(v -> startActivity(new Intent(this, AddContactActivity.class)));
        loadContacts();
    }

    private void loadContacts() {
        contacts = db.contactDao().getAll();
        listView.setAdapter(new ContactAdapter());
    }

    private class ContactAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return contacts.size();
        }

        @Override
        public Object getItem(int position) {
            return contacts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return contacts.get(position).id;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
            }

            Contact contact = contacts.get(position);
            TextView textName = convertView.findViewById(R.id.textName);
            TextView textPhone = convertView.findViewById(R.id.textPhone);
            Button btnCall = convertView.findViewById(R.id.btnCall);
            Button btnSms = convertView.findViewById(R.id.btnSms);

            textName.setText(contact.name);
            textPhone.setText(contact.phone);

            btnCall.setOnClickListener(v -> {
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contact.phone));
                startActivity(callIntent);
            });

            btnSms.setOnClickListener(v -> {
                Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + contact.phone));
                smsIntent.putExtra("sms_body", "Привет!");
                startActivity(smsIntent);
            });

            convertView.setOnLongClickListener(v -> {
                db.contactDao().delete(contact);
                loadContacts();
                return true;
            });

            return convertView;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadContacts();
    }
}

