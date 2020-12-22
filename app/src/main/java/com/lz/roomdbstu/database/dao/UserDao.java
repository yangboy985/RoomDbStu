package com.lz.roomdbstu.database.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Transaction;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.lz.roomdbstu.database.entity.User;
import com.lz.roomdbstu.database.relation_entitiy.DevicesBelongToUser;
import com.lz.roomdbstu.database.relation_entitiy.UserAndDevice;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Delete
    void delete(User user);

    @Update
    void update(User user);

    @Query("SELECT * FROM user")
    List<User> queryAll();

    @Query("DELETE FROM USER WHERE id = :id")
    void deleteById(long id);

    @Query("SELECT * FROM USER WHERE id = :id")
    Cursor queryById(long id);

    @Query("SELECT * FROM USER WHERE id = :id")
    User getUserById(long id);

    @Query("SELECT * FROM user")
    @Transaction
    List<DevicesBelongToUser> queryDevicesBelongToUser();

    @Query("DELETE FROM user")
    void clearTable();

    @Query("update SQLITE_SEQUENCE set seq = 0 where name='user';")
    void resetSeq();
}
