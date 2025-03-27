package com.example.exam6;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;

import java.util.List;

@Dao
public interface ContactDao {
    @Insert
    void insert(Contact contact);

    @Delete
    void delete(Contact contact);

    @Query("SELECT * FROM Contact ORDER BY name")
    List<Contact> getAll();
}

