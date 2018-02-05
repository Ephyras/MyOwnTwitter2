package com.example.javier.mot2;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by Javier on 2/5/2018.
 */

@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "username", childColumns = "owner"))
public class Message {

    @PrimaryKey(autoGenerate = true)
    private int mid;

    @ColumnInfo(name = "owner")
    @NonNull
    private String owner;

    @ColumnInfo(name = "message")
    private String message;

    @ColumnInfo(name="postedat")
    @TypeConverters({TimeStampConverter.class})
    private Date publishedAt;

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }
}
