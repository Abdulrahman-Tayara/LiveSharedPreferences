package com.tayara.livesharedpreferenceslibrary.sharedpreferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.tayara.livesharedpreferenceslibrary.mapper.JsonMapper;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created By: Abdulrahman-Tayara
 * Date: 05/2020
 */
@SuppressLint("CommitPrefEdits")
public class LiveSharedPreferences implements ISharedPreferences {

    private static final String SHARED_PREFERENCES_NAME = "live_shared_preferences";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private Map<String, BehaviorSubject> subjectMap;

    private JsonMapper jsonMapper = new JsonMapper();

    public LiveSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        this.subjectMap = new HashMap<>();
        this.editor = sharedPreferences.edit();
    }

    public LiveSharedPreferences(Context context, String name) {
        this(context.getSharedPreferences(name, Context.MODE_PRIVATE));
    }

    public LiveSharedPreferences(Context context) {
        this(context, SHARED_PREFERENCES_NAME);
    }

    private <T> BehaviorSubject<T> getSubject(String key, T defaultValue) {
        BehaviorSubject<T> behaviorSubject = subjectMap.get(key);
        if (behaviorSubject == null) {
            behaviorSubject = BehaviorSubject.create();
            behaviorSubject.onNext(defaultValue);
            subjectMap.put(key, behaviorSubject);
        }
        return behaviorSubject;
    }

    private <T> BehaviorSubject<T> getSubject(String key) {
        BehaviorSubject<T> behaviorSubject = subjectMap.get(key);
        if (behaviorSubject == null) {
            behaviorSubject = BehaviorSubject.create();
            subjectMap.put(key, behaviorSubject);
        }
        return behaviorSubject;
    }

    private <T> void observe(String key, T value) {
        if (value == null)
            return;
        BehaviorSubject behaviorSubject = getSubject(key);
        behaviorSubject.onNext(value);
    }

    @Override
    public void putInt(String key, int value) {
        editor.putInt(key, value).apply();
        observe(key, value);
    }

    @Override
    public void putLong(String key, long value) {
        editor.putLong(key, value).apply();
        observe(key, value);
    }

    @Override
    public void putFloat(String key, float value) {
        editor.putFloat(key, value).apply();
        observe(key, value);
    }

    @Override
    public void putString(String key, String value) {
        editor.putString(key, value).apply();
        observe(key, value);
    }

    @Override
    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value).apply();
        observe(key, value);
    }

    // convert the object to json then cache it
    @Override
    public <T> void putObject(String key, T object) {
        String json = jsonMapper.map(object);
        editor.putString(key, json).apply();
        observe(key, object);
    }

    @Override
    public Observable<Integer> getIntObservable(String key, int defaultValue) {
        return getSubject(key, defaultValue);
    }

    @Override
    public Observable<Long> getLongObservable(String key, long defaultValue) {
        return getSubject(key, defaultValue);
    }

    @Override
    public Observable<Float> getFloatObservable(String key, float defaultValue) {
        return getSubject(key, defaultValue);
    }

    @Override
    public Observable<String> getStringObservable(String key, String defaultValue) {
        return getSubject(key, defaultValue);
    }

    @Override
    public Observable<Boolean> getBooleanObservable(String key, boolean defaultValue) {
        return getSubject(key, defaultValue);
    }

    @Override
    public <T> Observable<T> getObjectObservable(String key) {
        BehaviorSubject<T> subject = subjectMap.get(key);
        if (subject == null) {
            subject = BehaviorSubject.create();
            subjectMap.put(key, subject);
        }
        return subject;
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }

    @Override
    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    @Override
    public <T> T getObject(String key, Class<T> clz) {
        String json = sharedPreferences.getString(key, "");
        if (json.isEmpty())
            return null;
        return jsonMapper.unmap(json, clz);
    }
}
