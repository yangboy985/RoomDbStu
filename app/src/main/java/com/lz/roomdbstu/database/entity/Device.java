package com.lz.roomdbstu.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "device",
        foreignKeys = @ForeignKey(entity = Manufacturer.class, parentColumns = "id", childColumns = "manufacturer_id", onDelete = ForeignKey.CASCADE)
)
public class Device {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "manufacturer_id")
    public long manufacturerId;

    @ColumnInfo(name = "device_type")
    public int deviceType;
}
