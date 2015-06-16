package com.github.pwittchen.prefser.library;

public interface JsonConverter {
    <T> T fromJson(String json, Class classOfT);
    String toJson(Object object);
}
