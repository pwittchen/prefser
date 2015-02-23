/*
 * Copyright (C) 2015 Piotr Wittchen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.pwittchen.prefser.library;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Prefser is a wrapper for Android SharedPreferences
 * with object serialization and RxJava Observables.
 * <p/>
 * It uses mechanism of SharedPreferences to store primitives
 * and Gson library to store arrays, lists and custom objects.
 * Thanks to RxJava, Prefser is able to listen changes
 * in SharedPreferences and provides Observable data,
 * which can be subscribed and returns key of the changed value.
 * <p/>
 * Basic Usage:
 * <pre>
 *  Prefser prefser = new Prefser(context);
 *  prefser.put("key", putValue);
 *  String getValue = prefser.get("key", String.class);
 * </pre>
 * Subscribing Observable of SharedPreferences:
 * <pre>
 *  prefser.fromDefaultPreferences()
 *      .observeOn(AndroidSchedulers.mainThread())
 *      .subscribeOn(Schedulers.io())
 *      ...
 *      .subscribe(...);
 * </pre>
 */
public final class Prefser {
    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;
    private final Map<Class, Getter> getters = new HashMap<>();
    private final Gson gson = new Gson();
    private SharedPreferences.OnSharedPreferenceChangeListener onChangeListener;

    private interface Getter {
        <T> T get(String key, Class classOfT, T defaultValue);
    }

    /**
     * Creates Prefser object with default SharedPreferences from PreferenceManager
     *
     * @param context Android Context
     */
    public Prefser(Context context) {
        this(PreferenceManager.getDefaultSharedPreferences(context));
    }

    /**
     * Creates Prefser object with provided object of SharedPreferences,
     * which will be wrapped
     *
     * @param sharedPreferences instance of SharedPreferences
     */
    public Prefser(SharedPreferences sharedPreferences) {
        checkNotNull(sharedPreferences, "sharedPreferences == null");
        this.preferences = sharedPreferences;
        this.editor = preferences.edit();
        createMapOfGetters();
    }

    /**
     * Checks if preferences contains value with a given key
     *
     * @param key provided key
     * @return true if preferences contains key and false if not
     */
    public boolean contains(String key) {
        return preferences.contains(key);
    }

    /**
     * gets value from SharedPreferences with a given key and type,
     * default value is set to null
     *
     * @param key      key of the preference
     * @param classOfT class of T (e.g. String.class)
     * @param <T>      return type of the preference (e.g. String)
     * @return value from SharedPreferences associated with given key
     */
    public <T> T get(String key, Class classOfT) {
        checkNotNull(key, "key == null");

        if (!contains(key)) {
            throw new RuntimeException(String.format("Value with key %s could not be found", key));
        }

        return get(key, classOfT, null);
    }

    /**
     * gets value from SharedPreferences with a given key and type
     * if value is not found, we can return defaultValue
     *
     * @param key          key of the preference
     * @param classOfT     class of T (e.g. String.class)
     * @param defaultValue default value of the preference (e.g. "" or "undefined")
     * @param <T>          return type of the preference (e.g. String)
     * @return value from SharedPreferences associated with given key or default value
     */
    public <T> T get(String key, Class classOfT, T defaultValue) {
        checkNotNull(key, "key == null");
        checkNotNull(classOfT, "classOfT == null");

        for (Map.Entry<Class, Getter> entry : getters.entrySet()) {
            if (classOfT.equals(entry.getKey())) {
                return (entry.getValue()).get(key, classOfT, defaultValue);
            }
        }

        if (contains(key)) {
            return (T) gson.fromJson(preferences.getString(key, null), classOfT);
        } else {
            return defaultValue;
        }
    }

    /**
     * returns RxJava Observable from default SharedPreferences
     * used inside Prefser object.
     * You can subscribe this Observable and every time,
     * when SharedPreferences will change, subscriber will be notified
     * about that (e.g. in call() method) and you will be able to read
     * key of the value, which has been changed
     *
     * @return Observable with String containing key of the value in default SharedPreferences
     */
    public Observable<String> fromDefaultPreferences() {
        return from(preferences);
    }

    /**
     * returns RxJava Observable from SharedPreferences.
     * You can subscribe this Observable and every time,
     * when SharedPreferences will change, subscriber will be notified
     * about that (e.g. in call() method) and you will be able to read
     * key of the value, which has been changed
     *
     * @param sharedPreferences instance of SharedPreferences to be observed
     * @return Observable with String containing key of the value in SharedPreferences
     */
    public Observable<String> from(final SharedPreferences sharedPreferences) {
        checkNotNull(sharedPreferences, "sharedPreferences == null");

        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                onChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                    @Override
                    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                        subscriber.onNext(key);
                    }
                };

                sharedPreferences.registerOnSharedPreferenceChangeListener(onChangeListener);
            }
        });
    }

    /**
     * puts value to the SharedPreferences
     *
     * @param key   key under which value will be stored
     * @param value value to be stored
     */
    public void put(String key, Object value) {
        checkNotNull(key, "key == null");
        checkNotNull(value, "value == null");

        if (!getters.containsKey(value.getClass())) {
            value = gson.toJson(value);
        }

        editor.putString(key, String.valueOf(value)).apply();
    }

    /**
     * removes value defined by a given key
     *
     * @param key key of the preference to be removed
     */
    public void remove(String key) {
        checkNotNull(key, "key == null");
        if (!contains(key)) {
            return;
        }

        editor.remove(key).apply();
    }

    /**
     * clears all SharedPreferences
     */
    public void clear() {
        if (size() == 0) {
            return;
        }

        editor.clear().apply();
    }

    /**
     * returns number of all items stored in SharedPreferences
     *
     * @return number of all stored items
     */
    public int size() {
        return preferences.getAll().size();
    }

    private void createMapOfGetters() {
        getters.put(Boolean.class, new Getter() {
            @Override
            public <T> T get(String key, Class classOfT, T defaultValue) {
                return (T) Boolean.valueOf(preferences.getString(key, String.valueOf(defaultValue)));
            }
        });

        getters.put(Float.class, new Getter() {
            @Override
            public <T> T get(String key, Class classOfT, T defaultValue) {
                return (T) Float.valueOf(preferences.getString(key, String.valueOf(defaultValue)));
            }
        });

        getters.put(Integer.class, new Getter() {
            @Override
            public <T> T get(String key, Class classOfT, T defaultValue) {
                return (T) Integer.valueOf(preferences.getString(key, String.valueOf(defaultValue)));
            }
        });

        getters.put(Long.class, new Getter() {
            @Override
            public <T> T get(String key, Class classOfT, T defaultValue) {
                return (T) Long.valueOf(preferences.getString(key, String.valueOf(defaultValue)));
            }
        });

        getters.put(Double.class, new Getter() {
            @Override
            public <T> T get(String key, Class classOfT, T defaultValue) {
                return (T) Double.valueOf(preferences.getString(key, String.valueOf(defaultValue)));
            }
        });

        getters.put(String.class, new Getter() {
            @Override
            public <T> T get(String key, Class classOfT, T defaultValue) {
                return (T) preferences.getString(key, String.valueOf(defaultValue));
            }
        });
    }

    private void checkNotNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
