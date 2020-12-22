package com.lz.roomdbstu.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.lz.roomdbstu.database.entity.UserRelateDevice;
import com.lz.roomdbstu.database.relation_entitiy.UserAndDevice;

import java.util.List;

@Dao
public interface UserRelateDeviceDao {
    @Insert
    void insert(UserRelateDevice entity);

    @Insert
    void bulkInsert(List<UserRelateDevice> list);

    @Delete
    void delete(UserRelateDevice entity);

    @Query("SELECT * FROM user_relate_device")
    @Transaction
    List<UserAndDevice> queryAll();

    @Query("DELETE FROM user_relate_device")
    void clearTable();

    @Query("update SQLITE_SEQUENCE set seq = 0 where name='user_relate_device'")
    void resetSeq();
}
