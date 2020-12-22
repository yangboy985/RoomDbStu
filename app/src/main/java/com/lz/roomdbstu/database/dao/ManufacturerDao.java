package com.lz.roomdbstu.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.lz.roomdbstu.database.entity.Manufacturer;

import java.util.List;

@Dao
public interface ManufacturerDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Manufacturer manufacturer);

    @Query("DELETE FROM manufacturer WHERE id = :id")
    void deleteById(long id);

    @Query("SELECT * FROM manufacturer")
    List<Manufacturer> queryAll();

    @Query("SELECT * FROM manufacturer where name = :name")
    Manufacturer queryByName(String name);

    @Query("DELETE FROM manufacturer")
    void clearTable();

    @Query("update SQLITE_SEQUENCE set seq = 0 where name='manufacturer'")
    void resetSeq();
}
