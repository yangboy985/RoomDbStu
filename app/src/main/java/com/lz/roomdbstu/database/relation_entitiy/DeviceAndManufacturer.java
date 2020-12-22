package com.lz.roomdbstu.database.relation_entitiy;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.lz.roomdbstu.database.entity.Device;
import com.lz.roomdbstu.database.entity.Manufacturer;

public class DeviceAndManufacturer {
    @Embedded
    public Device device;

    @Relation(
            parentColumn = "manufacturer_id",
            entityColumn = "id"
    )
    public Manufacturer manufacturer;
}
