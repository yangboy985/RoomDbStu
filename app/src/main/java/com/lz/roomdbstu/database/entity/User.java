package com.lz.roomdbstu.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ColumnInfo.UNICODE;

@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "name", collate = UNICODE)
    public String name;

    @ColumnInfo(name = "age", defaultValue = "10")
    public int age;

    @Embedded(prefix = "user_")
    public BodyInfo bodyInfo;

    @Ignore
    public String test;
}
