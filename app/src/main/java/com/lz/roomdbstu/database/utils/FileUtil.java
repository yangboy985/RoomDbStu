package com.lz.roomdbstu.database.utils;

import com.lz.roomdbstu.database.log.LogFactory;
import com.lz.roomdbstu.database.log.NLogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class FileUtil {
    private static final NLogger log = LogFactory.getLogger("FileUtil");
    public static void searchAllFile(File directory, List<File> result) {
        if (directory == null) {
            return;
        }
        if (!directory.isDirectory()) {
            result.add(directory);
            return;
        }
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            searchAllFile(f, result);
        }
    }

    public static boolean clearDir(File file) {
        if (file == null || !file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return deleteFile(file);
        }
        File[] files = file.listFiles();
        if (files == null) {
            return true;
        }
        boolean isSuccess = true;
        for (File f : files) {
            isSuccess &= clearDir(f);
            if (f.isDirectory()) {
                isSuccess &= f.delete();
            }
        }
        return isSuccess;
    }

    public static boolean deleteFile(File file) {
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return file.delete();
        }
        return true;
    }

    private static boolean createNotExistFile(File file) {
        try {
            File parent = file.getParentFile();
            if (parent == null) {
                return file.createNewFile();
            }
            if (!parent.exists() && !parent.mkdirs()) {
                return false;
            }
            return file.createNewFile();
        } catch (IOException ignore) {
        }
        return false;
    }

    public static boolean createOrClearFile(File file) {
        if (deleteFile(file)) {
            return createNotExistFile(file);
        }
        return false;
    }

    public static boolean createFileIfNotExist(File file) {
        if (file == null) {
            throw new IllegalArgumentException("file is null");
        }
        if (file.exists()) {
            return true;
        }
        return createNotExistFile(file);
    }

    public static boolean copyToDir(File src, File target) {
        if (!src.exists()) {
            log.d("src is't exist");
            return false;
        }
        if (!target.exists() && !target.mkdirs() || !target.isDirectory()) {
            log.d("target is abnormal");
            return false;
        }
        if (src.isFile()) {
            return copyFileToDir(src, target);
        }
        File[] sources = src.listFiles();
        if (sources == null) {
            return true;
        }
        boolean isSuccess = true;
        for (File f : sources) {
            if (f.isDirectory()) {
                isSuccess &= copyToDir(f, new File(target, f.getName()));
            } else {
                isSuccess &= copyFileToDir(f, target);
            }
        }
        return isSuccess;
    }

    private static boolean copyFileToDir(File src, File target) {
        if (!target.exists() && !target.mkdirs()) {
            return false;
        }
        File file = new File(target, src.getName());
        if (!createNotExistFile(file)) {
            return false;
        }
        byte[] b = new byte[1024 * 8];
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(src);
            fos = new FileOutputStream(file);
            int readLen = -1;
            while ((readLen = fis.read(b)) > 0) {
                fos.write(b, 0, readLen);
            }
            return true;
        } catch (Exception ignore) {
            log.d("copy file err", ignore);
            deleteFile(file);
        }
        return false;
    }
}
