package com.qadsdk.wrapper;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.gson.Gson;
import com.lz.roomdbstu.database.LzDatabase;
import com.lz.roomdbstu.database.entity.Manufacturer;
import com.lz.roomdbstu.database.log.LogFactory;
import com.lz.roomdbstu.database.log.NLogger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ManufacturerTest {
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
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.name = "aaa";
        manufacturer.registrationId = "aaa";
        long res = db.getManufacturerDao().insert(manufacturer);
        assert res != -1;

        manufacturer = new Manufacturer();
        manufacturer.name = "bbb";
        manufacturer.registrationId = "bbb";
        res = db.getManufacturerDao().insert(manufacturer);
        assert res != -1;

        manufacturer = new Manufacturer();
        manufacturer.name = "ccc";
        manufacturer.registrationId = "ccc";
        res = db.getManufacturerDao().insert(manufacturer);
        assert res != -1;
    }

    @Test
    public void testDelete() {
        db.getManufacturerDao().deleteById(2);
    }

    @Test
    public void testQueryAll() {
        log.d(gson.toJson(db.getManufacturerDao().queryAll()));
    }

    @Test
    public void clearTable() {
        db.runInTransaction(new Runnable() {
            @Override
            public void run() {
                db.getManufacturerDao().clearTable();
                db.getManufacturerDao().resetSeq();
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
