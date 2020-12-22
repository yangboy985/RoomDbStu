package com.lz.roomdbstu.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "manufacturer",
        indices = {
                @Index(name = "index_manufacturer_registration_id", value = "registration_id", unique = true),
        }
)
public class Manufacturer {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", index = true)
    public long id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "registration_id")
    public String registrationId;
}
