package com.lz.roomdbstu.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        tableName = "user_relate_device",
        primaryKeys = {"user_id", "device_id"},
        foreignKeys = {
                @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id", onDelete = CASCADE),
                @ForeignKey(entity = Device.class, parentColumns = "id", childColumns = "device_id", onDelete = CASCADE)
        }
)
public class UserRelateDevice {
    @ColumnInfo(name = "user_id")
    public long userId;

    @ColumnInfo(name = "device_id")
    public long deviceId;
}
