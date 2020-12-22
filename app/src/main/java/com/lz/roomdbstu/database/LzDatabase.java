package com.lz.roomdbstu.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.lz.roomdbstu.database.converters.BaseCollectionData;
import com.lz.roomdbstu.database.dao.DeviceCollectionInfoDao;
import com.lz.roomdbstu.database.dao.DeviceDao;
import com.lz.roomdbstu.database.dao.ManufacturerDao;
import com.lz.roomdbstu.database.dao.UserDao;
import com.lz.roomdbstu.database.dao.UserRelateDeviceDao;
import com.lz.roomdbstu.database.entity.Device;
import com.lz.roomdbstu.database.entity.DeviceCollectionInfo;
import com.lz.roomdbstu.database.entity.Manufacturer;
import com.lz.roomdbstu.database.entity.User;
import com.lz.roomdbstu.database.entity.UserRelateDevice;

@Database(
        version = 2,
        entities = {
                Device.class,
                DeviceCollectionInfo.class,
                Manufacturer.class,
                User.class,
                UserRelateDevice.class
        }
)
@TypeConverters(value = {BaseCollectionData.class})
public abstract class LzDatabase extends RoomDatabase {
    static LzDatabase getDatabase(Context context) {
        if (context == null) {
            throw new NullPointerException("context is null");
        }
        RoomDatabase.Builder<LzDatabase> builder = Room.databaseBuilder(context.getApplicationContext(),
                LzDatabase.class, "lz_db.db");
        // 内存中创建数据库，不用了就会被回收
//        builder = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), BookDB.class);
        // 添加构建SupportSQLiteOpenHelper的工厂，SQLiteOpenHelper就是在里面实现的，默认是FrameworkSQLiteOpenHelperFactory
//        builder.openHelperFactory(null);
        // 数据库迁移操作添加，相当于SQLiteOpenHelper.onUpgrade的一些操作
        builder.addMigrations(MIGRATION_1_2);
        builder.allowMainThreadQueries(); // 允许主线程执行查询操作
        // 有些APP的数据库需要预置数据，这两个就是导入预置数据库的
//        builder.createFromAsset();
//        builder.createFromFile()
        // 多进程需要调用，这个在inMemoryDatabaseBuilder没用
//        builder.enableMultiInstanceInvalidation();
        // 允许破坏性迁移，数据会被丢弃
//        builder.fallbackToDestructiveMigration();
        // 允许特定版本迁移到当前版本进行破坏性迁移，数据会被丢弃
//        builder.fallbackToDestructiveMigrationFrom(2, 3);
        // 允许降级破坏性迁移，数据会被丢弃
//        builder.fallbackToDestructiveMigrationOnDowngrade();
        // 这个默认就好，默认是WRITE_AHEAD_LOGGING
//        builder.setJournalMode(JournalMode.WRITE_AHEAD_LOGGING);
        // 看名称就知道是啥，这两个都是ArchTaskExecutor，是fixed线程池，核心线程4个，一般来说是够用的
//        builder.setQueryExecutor();
//        builder.setTransactionExecutor();
//        builder.addCallback(new Callback() {
//            @Override
//            public void onCreate(@NonNull SupportSQLiteDatabase db) {
//                // 第一次创建数据库调用
//                super.onCreate(db);
//            }
//
//            @Override
//            public void onOpen(@NonNull SupportSQLiteDatabase db) {
//                // 数据库打开调用
//                super.onOpen(db);
//            }
//
//            @Override
//            public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
//                // 破坏性迁移
//                super.onDestructiveMigration(db);
//            }
//        });

        return builder.build();
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE user ADD COLUMN age interger NOT NULL default 10");
        }
    };

    public abstract DeviceCollectionInfoDao getCollectionInfoDao();

    public abstract DeviceDao getDeviceDao();

    public abstract ManufacturerDao getManufacturerDao();

    public abstract UserDao getUserDao();

    public abstract UserRelateDeviceDao getUserRelateDeviceDao();

    public static class LzDatabaseFactory {
        private static Context context;
        private static volatile LzDatabase database;

        public static void initContext(Context context) {
            LzDatabaseFactory.context = context.getApplicationContext();
        }

        public static LzDatabase build() {
            if (context == null) {
                throw new NullPointerException("LzDatabaseFactory.initContext has't called");
            }
            if (database == null) {
                synchronized (LzDatabaseFactory.class) {
                    if (database == null) {
                        database = LzDatabase.getDatabase(context);
                    }
                }
            }
            return database;
        }
    }
}
