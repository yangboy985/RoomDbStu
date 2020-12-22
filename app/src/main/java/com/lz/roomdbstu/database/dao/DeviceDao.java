package com.lz.roomdbstu.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Transaction;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.lz.roomdbstu.database.entity.Device;
import com.lz.roomdbstu.database.entity.UserRelateDevice;
import com.lz.roomdbstu.database.relation_entitiy.DeviceAndManufacturer;

import java.util.List;

@Dao
public interface DeviceDao {
    @Insert
    void insert(Device device);

    @Insert
    @Transaction
    void bulkInsert(List<Device> list);

    @Delete
    void delete(Device device);

    @Update
    void update(Device device);

    @Query("SELECT * FROM device WHERE id = :id")
    Device queryById(long id);

    @Query("SELECT * FROM device WHERE name = :name")
    Device queryByName(String name);

    @Query("SELECT * FROM device")
    List<Device> queryAll();

    @Query("SELECT * FROM device")
    @Transaction
    List<DeviceAndManufacturer> queryTakeManufacturer();

    @Query("DELETE FROM device")
    void clearTable();

    @Query("update SQLITE_SEQUENCE set seq = 0 where name='device'")
    void resetSeq();

    @Transaction
    @RawQuery(observedEntities = {UserRelateDevice.class})
    LiveData<List<DeviceAndManufacturer>> rawQueryDevice(SupportSQLiteQuery query);

    @Transaction
    @Query("select * from device where id in (SELECT device_id from (select device_id, COUNT(*) as c from user_relate_device GROUP BY device_id) where c > 1)")
    LiveData<List<DeviceAndManufacturer>> querySharedDevice();
}
