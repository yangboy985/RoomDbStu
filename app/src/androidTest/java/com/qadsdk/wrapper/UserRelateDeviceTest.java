package com.qadsdk.wrapper;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.gson.Gson;
import com.lz.roomdbstu.database.LzDatabase;
import com.lz.roomdbstu.database.entity.UserRelateDevice;
import com.lz.roomdbstu.database.log.LogFactory;
import com.lz.roomdbstu.database.log.NLogger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class UserRelateDeviceTest {
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
        List<UserRelateDevice> list = new ArrayList<>();

        UserRelateDevice relate = new UserRelateDevice();
        relate.userId = 1;
        relate.deviceId = 1;
        list.add(relate);

        relate = new UserRelateDevice();
        relate.userId = 1;
        relate.deviceId = 3;
        list.add(relate);

        relate = new UserRelateDevice();
        relate.userId = 2;
        relate.deviceId = 3;
        list.add(relate);

        relate = new UserRelateDevice();
        relate.userId = 2;
        relate.deviceId = 2;
        list.add(relate);

        relate = new UserRelateDevice();
        relate.userId = 3;
        relate.deviceId = 4;
        list.add(relate);

        // 针对deferred的true和false进行了测试，都回滚了，没明白这个字段的价值
//        relate = new UserRelateDevice();
//        relate.userId = 3;
//        relate.deviceId = 4;
//        list.add(relate);

        relate = new UserRelateDevice();
        relate.userId = 3;
        relate.deviceId = 5;
        list.add(relate);

        relate = new UserRelateDevice();
        relate.userId = 2;
        relate.deviceId = 5;
        list.add(relate);

        // 复合主键生效
//        relate = new UserRelateDevice();
//        relate.userId = 2;
//        relate.deviceId = 5;
//        list.add(relate);

        db.getUserRelateDeviceDao().bulkInsert(list);
    }

    @Test
    public void testQueryAll() {
        log.d(gson.toJson(db.getUserRelateDeviceDao().queryAll()));
    }

    @Test
    public void clearTable() {
        db.runInTransaction(new Runnable() {
            @Override
            public void run() {
                db.getUserRelateDeviceDao().clearTable();
                db.getUserRelateDeviceDao().resetSeq();
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
