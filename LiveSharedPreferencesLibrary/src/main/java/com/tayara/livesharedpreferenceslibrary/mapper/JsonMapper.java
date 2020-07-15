package com.tayara.livesharedpreferenceslibrary.mapper;


import com.google.gson.Gson;

public class JsonMapper {

    private static Gson gson = new Gson();

    public JsonMapper() {
    }

    synchronized public <T> String map(T model) {
        return gson.toJson(model);
    }

    synchronized public <T> T unmap(String json, Class<T> clz) {
        return gson.fromJson(json, clz);
    }
}
