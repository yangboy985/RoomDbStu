package com.lz.roomdbstu.database.relation_entitiy;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.lz.roomdbstu.database.entity.Device;
import com.lz.roomdbstu.database.entity.User;
import com.lz.roomdbstu.database.entity.UserRelateDevice;

public class UserAndDevice {
    @Embedded
    public UserRelateDevice userRelateDevice;

    @Relation(
            parentColumn = "user_id",
            entityColumn = "id",
            entity = User.class
    )
    public User user;

    @Relation(
            parentColumn = "device_id",
            entityColumn = "id",
            entity = Device.class
    )
    public DeviceAndManufacturer device;
}
