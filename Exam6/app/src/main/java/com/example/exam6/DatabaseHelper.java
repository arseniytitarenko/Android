package com.example.exam6;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DatabaseHelper {
    private static final String DATABASE_NAME = "contacts.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CONTACTS = "contacts";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_COMMENT = "comment";

    private SQLiteDatabase database;
    private SQLiteOpenHelper dbHelper;

    public DatabaseHelper(Context context) {
        dbHelper = new SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long addContact(Contact contact) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PHONE, contact.getPhone());
        values.put(KEY_COMMENT, contact.getComment());
        return database.insert(TABLE_CONTACTS, null, values);
    }

    public ArrayList<Contact> getAllContacts() {
        ArrayList<Contact> contacts = new ArrayList<>();
        Cursor cursor = database.query(TABLE_CONTACTS, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
                @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex(KEY_PHONE));
                @SuppressLint("Range") String comment = cursor.getString(cursor.getColumnIndex(KEY_COMMENT));
                contacts.add(new Contact(id, name, phone, comment));
            }
            cursor.close();
        }
        return contacts;
    }

    public int updateContact(Contact contact) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PHONE, contact.getPhone());
        values.put(KEY_COMMENT, contact.getComment());
        return database.update(TABLE_CONTACTS, values, KEY_ID + " = ?", new String[]{String.valueOf(contact.getId())});
    }

    public void deleteContact(int contactId) {
        database.delete(TABLE_CONTACTS, KEY_ID + " = ?", new String[]{String.valueOf(contactId)});
    }

    private static class SQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {
        public SQLiteOpenHelper(Context context, String name, android.database.sqlite.SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KEY_NAME + " TEXT,"
                    + KEY_PHONE + " TEXT,"
                    + KEY_COMMENT + " TEXT"
                    + ")";
            db.execSQL(CREATE_CONTACTS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
            onCreate(db);
        }
    }
}
