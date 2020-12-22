package com.lz.roomdbstu.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lz.roomdbstu.database.entity.DeviceCollectionInfo;

import java.util.List;

@Dao
public interface DeviceCollectionInfoDao {
    @Insert
    void bulkInsert(List<DeviceCollectionInfo> info);

    @Delete
    void delete(DeviceCollectionInfo info);

    @Update
    void update(DeviceCollectionInfo info);

    @Query("DELETE FROM collection_info WHERE id = :id")
    void deleteById(long id);

    @Query("SELECT * FROM collection_info")
    List<DeviceCollectionInfo> queryAll();

    @Query("DELETE FROM collection_info")
    void clearTable();

    @Query("update SQLITE_SEQUENCE set seq = 0 where name='collection_info'")
    void resetSeq();
}
