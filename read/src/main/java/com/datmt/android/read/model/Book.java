package com.datmt.android.read.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;

import lombok.Builder;

@Entity(tableName = "books")
public class Book {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "book_title")
    public String bookTitle;

    @ColumnInfo(name = "book_author")
    public String bookAuthor;


    @ColumnInfo(name = "location_on_disk")
    public String locationOnDisk;

    @ColumnInfo(name = "last_accessed")
    public Timestamp lastAccessed;

    public Book() {

    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getLocationOnDisk() {
        return locationOnDisk;
    }

    public void setLocationOnDisk(String locationOnDisk) {
        this.locationOnDisk = locationOnDisk;
    }

    public Timestamp getLastAccessed() {
        return lastAccessed;
    }

    public void setLastAccessed(Timestamp lastAccessed) {
        this.lastAccessed = lastAccessed;
    }
}
