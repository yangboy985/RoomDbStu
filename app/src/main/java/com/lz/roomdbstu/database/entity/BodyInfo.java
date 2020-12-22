package com.lz.roomdbstu.database.entity;

import androidx.room.ColumnInfo;

public class BodyInfo {
    @ColumnInfo(name = "height")
    public int height;

    @ColumnInfo(name = "weight")
    public float weight;
}
