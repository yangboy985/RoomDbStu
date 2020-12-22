package com.lz.roomdbstu.database.converters;

import android.text.TextUtils;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;

public class BaseCollectionData {
    public static final int THERMAL_MONITOR = 1;
    public static final int WATCH = 2;

    @SerializedName("clzName")
    private String clzName;

    {
        clzName = this.getClass().getName();
    }

    public String getClzName() {
        return clzName;
    }

    @TypeConverter
    public static BaseCollectionData fromJson(String json) {
        JsonElement element = JsonParser.parseString(json);
        if (element == null || !element.isJsonObject()) {
            return null;
        }
        JsonObject obj = element.getAsJsonObject();
        if (!obj.has("clzName")) {
            return null;
        }
        try {
            String clzName = obj.get("clzName").getAsString();
            if (TextUtils.isEmpty(clzName)) {
                return null;
            }
            return (BaseCollectionData) new Gson().fromJson(json, Class.forName(clzName));
        } catch (Exception ignore) {
        }
        return null;
    }

    @TypeConverter
    public static String toJson(BaseCollectionData person) {
        return new Gson().toJson(person);
    }
}
