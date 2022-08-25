package com.datmt.android.read.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;

@Entity(tableName = "books")
public class Book {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "book_title")
    public String bookTitle;

    @ColumnInfo(name = "location_on_disk")
    public String locationOnDisk;

    @ColumnInfo(name = "last_accessed")
    public Timestamp lastAccessed;
}
