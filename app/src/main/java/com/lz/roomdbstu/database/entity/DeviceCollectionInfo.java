package com.lz.roomdbstu.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.lz.roomdbstu.database.converters.BaseCollectionData;

@Entity(
        tableName = "collection_info",
        foreignKeys = @ForeignKey(entity = Device.class, parentColumns = "id", childColumns = "device_id", onDelete = ForeignKey.CASCADE)
)
public class DeviceCollectionInfo {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "device_id")
    public long deviceId;

    @ColumnInfo(name = "data")
    public BaseCollectionData data;
}
