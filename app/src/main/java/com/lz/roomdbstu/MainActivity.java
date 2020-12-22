package com.lz.roomdbstu;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.google.gson.Gson;
import com.lz.roomdbstu.database.LzDatabase;
import com.lz.roomdbstu.database.entity.Device;
import com.lz.roomdbstu.database.entity.UserRelateDevice;
import com.lz.roomdbstu.database.log.LogFactory;
import com.lz.roomdbstu.database.log.NLogger;
import com.lz.roomdbstu.database.relation_entitiy.DeviceAndManufacturer;
import com.lz.roomdbstu.database.utils.FileUtil;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    NLogger log = LogFactory.getLogger("MainActivity");
    private LzDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bt_exclude_db).setOnClickListener(this);
        findViewById(R.id.bt_delete_db).setOnClickListener(this);
        findViewById(R.id.bt_query).setOnClickListener(this);
        findViewById(R.id.bt_add).setOnClickListener(this);
        findViewById(R.id.bt_delete_relation).setOnClickListener(this);
        findViewById(R.id.bt_delete_5).setOnClickListener(this);

        LzDatabase.LzDatabaseFactory.initContext(this);
        db = LzDatabase.LzDatabaseFactory.build();
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getPackageName(), PackageManager.GET_PERMISSIONS);
            ActivityCompat.requestPermissions(this, pi.requestedPermissions, 123);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (R.id.bt_exclude_db == v.getId()) {
            new Thread() {
                @Override
                public void run() {
                    File dbFile = getDatabasePath("lz_db.db");
                    File target = new File(Environment.getExternalStorageDirectory(), "room_test_db");
                    log.dKV("dbFile", dbFile.getAbsolutePath());
                    log.dKV("target", target.getAbsolutePath());
                    FileUtil.clearDir(target);
                    boolean copyRes = FileUtil.copyToDir(dbFile.getParentFile(), target);
                    log.dKV("copyRes", copyRes);
                }
            }.start();
        }
        if (R.id.bt_delete_db == v.getId()) {
            new Thread() {
                @Override
                public void run() {
                    File dbFile = getDatabasePath("lz_db.db");
                    FileUtil.deleteFile(dbFile);
                }
            }.start();
        }
        if (R.id.bt_query == v.getId()) {
            // 查询多人共享设备
//            SupportSQLiteQuery query = new SimpleSQLiteQuery("select * from device where id in " +
//                    "(SELECT device_id from (select device_id, COUNT(*) as c from user_relate_device GROUP BY device_id) where c > 1)");
//            LiveData<List<DeviceAndManufacturer>> liveData = db.getDeviceDao().rawQueryDevice(query);

            LiveData<List<DeviceAndManufacturer>> liveData = db.getDeviceDao().querySharedDevice();
            liveData.observe(this, new Observer<List<DeviceAndManufacturer>>() {
                @Override
                public void onChanged(List<DeviceAndManufacturer> deviceAndManufacturers) {
                    log.d(new Gson().toJson(deviceAndManufacturers));
                }
            });
        }
        if (R.id.bt_add == v.getId()) {
            UserRelateDevice relate = new UserRelateDevice();
            relate.userId = 3;
            relate.deviceId = 1;
            db.getUserRelateDeviceDao().insert(relate);
        }
        if (R.id.bt_delete_relation == v.getId()) {
            UserRelateDevice relate = new UserRelateDevice();
            relate.userId = 3;
            relate.deviceId = 1;
            db.getUserRelateDeviceDao().delete(relate);
        }
        if (R.id.bt_delete_5 == v.getId()) {
            Device device = new Device();
            device.id = 5;
            db.getDeviceDao().delete(device);
        }
    }

    @Override
    protected void onDestroy() {
        if (db != null) {
            db.close();
            db = null;
        }
        super.onDestroy();
    }
}