package com.lz.roomdbstu.database.relation_entitiy;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.lz.roomdbstu.database.entity.Device;
import com.lz.roomdbstu.database.entity.User;
import com.lz.roomdbstu.database.entity.UserRelateDevice;

import java.util.List;

public class DevicesBelongToUser {
    @Embedded
    public User user;

    @Relation(
            parentColumn = "id",
            entityColumn = "id",
            associateBy = @Junction(value = UserRelateDevice.class, parentColumn = "user_id", entityColumn = "device_id"),
            entity = Device.class
    )
    public List<DeviceAndManufacturer> devices;
}
