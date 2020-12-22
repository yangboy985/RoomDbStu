package com.qadsdk.wrapper;

import android.content.Context;
import android.database.Cursor;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.gson.Gson;
import com.lz.roomdbstu.database.LzDatabase;
import com.lz.roomdbstu.database.entity.BodyInfo;
import com.lz.roomdbstu.database.entity.User;
import com.lz.roomdbstu.database.log.LogFactory;
import com.lz.roomdbstu.database.log.NLogger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class UserTest {
    private Context appContext;
    NLogger log = LogFactory.getLogger("UserTest");
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
//        User user = new User();
//        user.name = "张三";
//        user.bodyInfo = new BodyInfo();
//        user.bodyInfo.height = 180;
//        user.bodyInfo.weight = 70;
//        db.getUserDao().insert(user);
//
//        user = new User();
//        user.name = "李四";
//        user.bodyInfo = new BodyInfo();
//        user.bodyInfo.height = 170;
//        user.bodyInfo.weight = 60;
//        db.getUserDao().insert(user);
//
//        user = new User();
//        user.name = "王五";
//        user.bodyInfo = new BodyInfo();
//        user.bodyInfo.height = 160;
//        user.bodyInfo.weight = 60;
//        db.getUserDao().insert(user);

        User user = new User();
        user.name = "赵六";
        user.age = 20;
        user.bodyInfo = new BodyInfo();
        user.bodyInfo.height = 180;
        user.bodyInfo.weight = 70;
        db.getUserDao().insert(user);
    }

    @Test
    public void testDelete() {
        db.getUserDao().deleteById(3);
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.id = 1;
        user.name = "张三";
        user.bodyInfo = new BodyInfo();
        user.bodyInfo.height = 180;
        user.bodyInfo.weight = 80;
        db.getUserDao().update(user);
    }

    @Test
    public void testQueryAll() {
        log.d(gson.toJson(db.getUserDao().queryAll()));
    }

    @Test
    public void testQueryDevicesBelongToUser() {
        log.d(gson.toJson(db.getUserDao().queryDevicesBelongToUser()));
    }

    @Test
    public void testQueryById() {
        Cursor cursor = db.getUserDao().queryById(1);
        assert cursor != null;
        cursor.moveToPosition(-1);
        cursor.moveToFirst();
        User user = new User();
        user.bodyInfo = new BodyInfo();
        user.id = cursor.getLong(cursor.getColumnIndex("id"));
        user.name = cursor.getString(cursor.getColumnIndex("name"));
        user.bodyInfo.height = cursor.getInt(cursor.getColumnIndex("user_height"));
        user.bodyInfo.weight = cursor.getFloat(cursor.getColumnIndex("user_weight"));

        log.d(gson.toJson(user));
    }

    @Test
    public void clearTable() {
        db.runInTransaction(new Runnable() {
            @Override
            public void run() {
                db.getUserDao().clearTable();
                db.getUserDao().resetSeq();
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
