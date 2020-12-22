package com.qadsdk.wrapper;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.gson.Gson;
import com.lz.roomdbstu.database.LzDatabase;
import com.lz.roomdbstu.database.converters.BaseCollectionData;
import com.lz.roomdbstu.database.entity.Device;
import com.lz.roomdbstu.database.log.LogFactory;
import com.lz.roomdbstu.database.log.NLogger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DeviceTest {
    private Context appContext;
    NLogger log = LogFactory.getLogger("DeviceTest");
    private Gson gson = new Gson();
    LzDatabase db = null;

    @Before
    public void before() {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        LzDatabase.LzDatabaseFactory.initContext(appContext);
        db = LzDatabase.LzDatabaseFactory.build();
    }

    @Test
    public void testBulkInsert() {
        List<Device> list = new ArrayList<>();
        Device device = new Device();
        device.deviceType = BaseCollectionData.WATCH;
        device.name = "111";
        device.manufacturerId = db.getManufacturerDao().queryByName("aaa").id;
        list.add(device);

        device = new Device();
        device.deviceType = BaseCollectionData.THERMAL_MONITOR;
        device.name = "222";
        device.manufacturerId = db.getManufacturerDao().queryByName("aaa").id;
        list.add(device);

        device = new Device();
        device.deviceType = BaseCollectionData.THERMAL_MONITOR;
        device.name = "333";
        device.manufacturerId = db.getManufacturerDao().queryByName("bbb").id;
        list.add(device);

        device = new Device();
        device.deviceType = BaseCollectionData.THERMAL_MONITOR;
        device.name = "444";
        device.manufacturerId = db.getManufacturerDao().queryByName("ccc").id;
        list.add(device);

        device = new Device();
        device.deviceType = BaseCollectionData.WATCH;
        device.name = "555";
        device.manufacturerId = db.getManufacturerDao().queryByName("ccc").id;
        list.add(device);

        // 外键约束
//        device = new Device();
//        device.deviceType = BaseCollectionData.THERMAL_MONITOR;
//        device.name = "666";
//        device.manufacturerId = 999;
//        list.add(device);

        db.getDeviceDao().bulkInsert(list);
    }

    @Test
    public void testDelete() {
        Device device = db.getDeviceDao().queryById(6);
        db.getDeviceDao().delete(device);
    }

    @Test
    public void testQueryAll() {
        log.d(gson.toJson(db.getDeviceDao().queryAll()));
    }

    @Test
    public void clearTable() {
        db.runInTransaction(new Runnable() {
            @Override
            public void run() {
                db.getDeviceDao().clearTable();
                db.getDeviceDao().resetSeq();
            }
        });
    }

    @After
    public void after() {
        appContext = null;
        db.close();
        db = null;
    }
}
