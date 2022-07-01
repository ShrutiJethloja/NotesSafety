package com.example.notessafety.Models;

import android.webkit.WebView;
import android.widget.ImageView;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Notes")
public class Notes implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int Id = 0;

    @ColumnInfo(name = "Title")
    String title = "";

    @ColumnInfo(name = "Notes")
    String notes = "";

    @ColumnInfo(name = "Date")
    String  date = "";

    @ColumnInfo(name = "Pinned")
    boolean pinned = false;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }
}
