package com.lz.roomdbstu.database.relation_entitiy;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.lz.roomdbstu.database.entity.Device;
import com.lz.roomdbstu.database.entity.Manufacturer;

import java.util.List;

public class ManufacturerProducts {
    @Embedded
    public Manufacturer manufacturer;

    @Relation(
            parentColumn = "id",
            entityColumn = "manufacturer_id"
    )
    public List<Device> products;
}
