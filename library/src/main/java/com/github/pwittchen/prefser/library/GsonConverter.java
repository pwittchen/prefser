package com.github.pwittchen.prefser.library;

import com.google.gson.Gson;

public final class GsonConverter implements JsonConverter {

    private final Gson gson = new Gson();

    @Override
    public <T> T fromJson(String json, Class classOfT) {
        return (T) gson.fromJson(json, classOfT);
    }

    @Override
    public String toJson(Object object) {
        return gson.toJson(object);
    }
}
