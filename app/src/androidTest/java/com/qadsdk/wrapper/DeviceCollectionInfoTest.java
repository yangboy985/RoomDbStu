package com.qadsdk.wrapper;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.gson.Gson;
import com.lz.roomdbstu.database.LzDatabase;
import com.lz.roomdbstu.database.converters.ThermalMonitorData;
import com.lz.roomdbstu.database.converters.WatchSportsData;
import com.lz.roomdbstu.database.entity.DeviceCollectionInfo;
import com.lz.roomdbstu.database.entity.Manufacturer;
import com.lz.roomdbstu.database.log.LogFactory;
import com.lz.roomdbstu.database.log.NLogger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DeviceCollectionInfoTest {
    private Context appContext;
    NLogger log = LogFactory.getLogger("ManufacturerTest");
    private Gson gson = new Gson();
    LzDatabase db = null;

    @Before
    public void before() {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        LzDatabase.LzDatabaseFactory.initContext(appContext);
        db = LzDatabase.LzDatabaseFactory.build();
    }

    @Test
    public void testInsert() {
        List<DeviceCollectionInfo> list = new ArrayList<>();
        WatchSportsData sportsData = null;
        ThermalMonitorData monitorData = null;

        DeviceCollectionInfo info = new DeviceCollectionInfo();
        info.deviceId = db.getDeviceDao().queryByName("111").id;
        info.data = sportsData = new WatchSportsData();
        sportsData.heartbeat = 70;
        list.add(info);

        info = new DeviceCollectionInfo();
        info.deviceId = db.getDeviceDao().queryByName("111").id;
        info.data = sportsData = new WatchSportsData();
        sportsData.heartbeat = 65;
        list.add(info);

        info = new DeviceCollectionInfo();
        info.deviceId = db.getDeviceDao().queryByName("222").id;
        info.data = monitorData = new ThermalMonitorData();
        monitorData.temperature = 36.5f;
        list.add(info);

        info = new DeviceCollectionInfo();
        info.deviceId = db.getDeviceDao().queryByName("555").id;
        info.data = sportsData = new WatchSportsData();
        sportsData.heartbeat = 77;
        list.add(info);

        info = new DeviceCollectionInfo();
        info.deviceId = db.getDeviceDao().queryByName("333").id;
        info.data = monitorData = new ThermalMonitorData();
        monitorData.temperature = 38.5f;
        list.add(info);

        info = new DeviceCollectionInfo();
        info.deviceId = db.getDeviceDao().queryByName("444").id;
        info.data = monitorData = new ThermalMonitorData();
        monitorData.temperature = 34.5f;
        list.add(info);

        info = new DeviceCollectionInfo();
        info.deviceId = db.getDeviceDao().queryByName("333").id;
        info.data = monitorData = new ThermalMonitorData();
        monitorData.temperature = 37.5f;
        list.add(info);

        db.getCollectionInfoDao().bulkInsert(list);
    }

    @Test
    public void testDelete() {
        db.getCollectionInfoDao().deleteById(2);
    }

    @Test
    public void testQueryAll() {
        log.d(gson.toJson(db.getCollectionInfoDao().queryAll()));
    }

    @Test
    public void clearTable() {
        db.runInTransaction(new Runnable() {
            @Override
            public void run() {
                db.getCollectionInfoDao().clearTable();
                db.getCollectionInfoDao().resetSeq();
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
