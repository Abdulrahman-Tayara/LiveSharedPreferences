package com.tayara.livesharedpreferenceslibrary.sharedpreferences;

import io.reactivex.Observable;

public interface ISharedPreferences {

    void putInt(String key, int value);

    void putLong(String key, long value);

    void putFloat(String key, float value);

    void putString(String key, String value);

    void putBoolean(String key, boolean value);

    <T> void putObject(String key, T model);


    Observable<Integer> getIntObservable(String key, int defaultValue);

    Observable<Long> getLongObservable(String key, long defaultValue);

    Observable<Float> getFloatObservable(String key, float defaultValue);

    Observable<String> getStringObservable(String key, String defaultValue);

    Observable<Boolean> getBooleanObservable(String key, boolean defaultValue);

    <T> Observable<T> getObjectObservable(String key);


    int getInt(String key, int defaultValue);

    long getLong(String key, long defaultValue);

    float getFloat(String key, float defaultValue);

    String getString(String key, String defaultValue);

    boolean getBoolean(String key, boolean defaultValue);

    <T> T getObject(String key, Class<T> clz);

}
