package com.github.pwittchen.prefser.library;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

// Inspired by Gson's TypeToken
public abstract class TypeToken<T> {
    private final Type type;

    public TypeToken() {
        // Superclass of anonymous class retains its type parameter despite of type erasure.
        Type superclass = getClass().getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterizedSuperclass = (ParameterizedType) superclass;
        type = parameterizedSuperclass.getActualTypeArguments()[0];
    }

    private TypeToken(Class<?> classOfT) {
        if (classOfT == null) {
            throw new NullPointerException("classOfT == null");
        }
        this.type = classOfT;
    }

    static <T> TypeToken<T> fromClass(Class<T> classForT) {
        return new TypeToken<T>(classForT) {
        };
    }

    static <T> TypeToken<T> fromValue(T value) {
        return new TypeToken<T>(value.getClass()) {
        };
    }

    public Type getType() {
        return type;
    }
}
