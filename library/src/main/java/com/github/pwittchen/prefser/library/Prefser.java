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

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.subscriptions.Subscriptions;

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
 *  prefser.put("key", value);
 *  String getValue = prefser.get("key", String.class, defaultValue);
 * </pre>
 * Subscribing Observable of SharedPreferences:
 * <pre>
 *  prefser.observePreferences()
 *    .subscribeOn(Schedulers.io())
 *    .observeOn(AndroidSchedulers.mainThread())
 *    ...
 *    .subscribe(...);
 * </pre>
 */
public class Prefser {
  private static final String KEY_IS_NULL = "key == null";
  private static final String CLASS_OF_T_IS_NULL = "classOfT == null";
  private static final String TYPE_TOKEN_OF_T_IS_NULL = "typeTokenOfT == null";
  private static final String VALUE_IS_NULL = "value == null";

  private final SharedPreferences preferences;
  private final SharedPreferences.Editor editor;
  private final JsonConverter jsonConverter;
  private final AccessorsProvider accessorProvider;

  /**
   * Creates Prefser object with default SharedPreferences from PreferenceManager.
   *
   * @param context Android Context
   */
  public Prefser(@NonNull Context context) {
    this(context, new GsonConverter());
  }

  /**
   * Creates Prefser object with default SharedPreferences from PreferenceManager.
   * with JsonConverter implementation
   *
   * @param context Android Context
   * @param jsonConverter Json Converter
   */
  public Prefser(@NonNull Context context, @NonNull JsonConverter jsonConverter) {
    this(PreferenceManager.getDefaultSharedPreferences(context), jsonConverter);
  }

  /**
   * Creates Prefser object with provided object of SharedPreferences,
   * which will be wrapped.
   *
   * @param sharedPreferences instance of SharedPreferences
   */
  public Prefser(@NonNull SharedPreferences sharedPreferences) {
    this(sharedPreferences, new GsonConverter());
  }

  /**
   * Creates Prefser object with provided object of SharedPreferences,
   * which will be wrapped with JsonConverter implementation.
   *
   * @param sharedPreferences instance of SharedPreferences
   * @param jsonConverter Json Converter
   */
  @SuppressLint("CommitPrefEdits") public Prefser(@NonNull SharedPreferences sharedPreferences,
      @NonNull JsonConverter jsonConverter) {
    Preconditions.checkNotNull(sharedPreferences, "sharedPreferences == null");
    Preconditions.checkNotNull(jsonConverter, "jsonConverter == null");
    this.preferences = sharedPreferences;
    this.editor = preferences.edit();
    this.jsonConverter = jsonConverter;
    this.accessorProvider = new PreferencesAccessorsProvider(preferences, editor);
  }

  /**
   * Returns SharedPreferences in case, we want to manipulate them without Prefser.
   *
   * @return SharedPreferences instance of SharedPreferences
   */
  public SharedPreferences getPreferences() {
    return preferences;
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
   * Gets value from SharedPreferences with a given key and type
   * as a RxJava Observable, which can be subscribed.
   * If value is not found, we can return defaultValue.
   * Emit preference as first element of the stream even if preferences wasn't changed.
   *
   * @param key key of the preference
   * @param classOfT class of T (e.g. String.class)
   * @param defaultValue default value of the preference (e.g. "" or "undefined")
   * @param <T> return type of the preference (e.g. String)
   * @return Observable value from SharedPreferences associated with given key or default value
   */
  public <T> Observable<T> getAndObserve(String key, Class<T> classOfT, T defaultValue) {
    return getAndObserve(key, TypeToken.fromClass(classOfT), defaultValue);
  }

  /**
   * Gets value from SharedPreferences with a given key and type token
   * as a RxJava Observable, which can be subscribed
   * If value is not found, we can return defaultValue.
   * Emit preference as first element of the stream even if preferences wasn't changed.
   *
   * @param key key of the preference
   * @param typeTokenOfT type token of T (e.g. {@code new TypeToken<List<String>> {})
   * @param defaultValue default value of the preference (e.g. "" or "undefined")
   * @param <T> return type of the preference (e.g. String)
   * @return Observable value from SharedPreferences associated with given key or default value
   */
  public <T> Observable<T> getAndObserve(final String key, final TypeToken<T> typeTokenOfT,
      final T defaultValue) {
    return observe(key, typeTokenOfT, defaultValue) // start observing
        .mergeWith(Observable.defer(new Func0<Observable<T>>() { // then start getting
          @Override public Observable<T> call() {
            return Observable.just(get(key, typeTokenOfT, defaultValue));
          }
        }));
  }

  /**
   * Gets value from SharedPreferences with a given key and type
   * as a RxJava Observable, which can be subscribed.
   * If value is not found, we can return defaultValue.
   *
   * @param key key of the preference
   * @param classOfT class of T (e.g. String.class)
   * @param defaultValue default value of the preference (e.g. "" or "undefined")
   * @param <T> return type of the preference (e.g. String)
   * @return Observable value from SharedPreferences associated with given key or default value
   */
  public <T> Observable<T> observe(@NonNull String key, @NonNull Class<T> classOfT,
      T defaultValue) {
    Preconditions.checkNotNull(key, KEY_IS_NULL);
    Preconditions.checkNotNull(classOfT, CLASS_OF_T_IS_NULL);

    return observe(key, TypeToken.fromClass(classOfT), defaultValue);
  }

  /**
   * Gets value from SharedPreferences with a given key and type token
   * as a RxJava Observable, which can be subscribed.
   * If value is not found, we can return defaultValue.
   *
   * @param key key of the preference
   * @param typeTokenOfT type token of T (e.g. {@code new TypeToken<List<String>> {})
   * @param defaultValue default value of the preference (e.g. "" or "undefined")
   * @param <T> return type of the preference (e.g. String)
   * @return Observable value from SharedPreferences associated with given key or default value
   */
  public <T> Observable<T> observe(@NonNull final String key,
      @NonNull final TypeToken<T> typeTokenOfT, final T defaultValue) {
    Preconditions.checkNotNull(key, KEY_IS_NULL);
    Preconditions.checkNotNull(typeTokenOfT, TYPE_TOKEN_OF_T_IS_NULL);

    return observePreferences().filter(new Func1<String, Boolean>() {
      @Override public Boolean call(String filteredKey) {
        return key.equals(filteredKey);
      }
    }).map(new Func1<String, T>() {
      @Override public T call(String s) {
        return get(key, typeTokenOfT, defaultValue);
      }
    });
  }

  /**
   * Gets value from SharedPreferences with a given key and type.
   * If value is not found, we can return defaultValue.
   *
   * @param key key of the preference
   * @param classOfT class of T (e.g. {@code String.class})
   * @param defaultValue default value of the preference (e.g. "" or "undefined")
   * @param <T> return type of the preference (e.g. String)
   * @return value from SharedPreferences associated with given key or default value
   */
  public <T> T get(@NonNull String key, @NonNull Class<T> classOfT, T defaultValue) {
    Preconditions.checkNotNull(key, KEY_IS_NULL);
    Preconditions.checkNotNull(classOfT, CLASS_OF_T_IS_NULL);

    if (!contains(key) && defaultValue == null) {
      return null;
    }

    return get(key, TypeToken.fromClass(classOfT), defaultValue);
  }

  /**
   * Gets value from SharedPreferences with a given key and type.
   * If value is not found, we can return defaultValue.
   *
   * @param key key of the preference
   * @param typeTokenOfT type token of T (e.g. {@code new TypeToken<List<String>> {})
   * @param defaultValue default value of the preference (e.g. "" or "undefined")
   * @param <T> return type of the preference (e.g. String)
   * @return value from SharedPreferences associated with given key or default value
   */
  public <T> T get(@NonNull String key, @NonNull TypeToken<T> typeTokenOfT, T defaultValue) {
    Preconditions.checkNotNull(key, KEY_IS_NULL);
    Preconditions.checkNotNull(typeTokenOfT, TYPE_TOKEN_OF_T_IS_NULL);

    Type typeOfT = typeTokenOfT.getType();

    for (Map.Entry<Class<?>, Accessor<?>> entry : accessorProvider.getAccessors().entrySet()) {
      if (typeOfT.equals(entry.getKey())) {
        @SuppressWarnings("unchecked") Accessor<T> accessor = (Accessor<T>) entry.getValue();
        return accessor.get(key, defaultValue);
      }
    }

    if (contains(key)) {
      return jsonConverter.fromJson(preferences.getString(key, null), typeOfT);
    } else {
      return defaultValue;
    }
  }

  /**
   * returns RxJava Observable from SharedPreferences used inside Prefser object.
   * You can subscribe this Observable and every time,
   * when SharedPreferences will change, subscriber will be notified
   * about that (e.g. in call() method) and you will be able to read
   * key of the value, which has been changed.
   *
   * @return Observable with String containing key of the value in default SharedPreferences
   */
  public Observable<String> observePreferences() {
    return Observable.unsafeCreate(new Observable.OnSubscribe<String>() {
      // NOTE: Without this OnChangeListener will be GCed.
      Collection<OnChangeListener> listenerReferences =
          Collections.synchronizedList(new ArrayList<OnChangeListener>());

      @Override public void call(final Subscriber<? super String> subscriber) {
        final OnChangeListener onChangeListener = new OnChangeListener(subscriber);
        preferences.registerOnSharedPreferenceChangeListener(onChangeListener);
        listenerReferences.add(onChangeListener);
        subscriber.add(Subscriptions.create(new Action0() {
          @Override public void call() {
            preferences.unregisterOnSharedPreferenceChangeListener(onChangeListener);
            listenerReferences.remove(onChangeListener);
          }
        }));
      }
    });
  }

  private static class OnChangeListener
      implements SharedPreferences.OnSharedPreferenceChangeListener {
    private final Subscriber<? super String> subscriber;

    public OnChangeListener(Subscriber<? super String> subscriber) {
      this.subscriber = subscriber;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
      if (!subscriber.isUnsubscribed()) {
        subscriber.onNext(key);
      }
    }
  }

  /**
   * Puts value to the SharedPreferences.
   *
   * @param key key under which value will be stored
   * @param value value to be stored
   */
  public <T> void put(@NonNull String key, @NonNull T value) {
    Preconditions.checkNotNull(value, VALUE_IS_NULL);
    put(key, value, TypeToken.fromValue(value));
  }

  /**
   * Puts value to the SharedPreferences.
   *
   * @param key key under which value will be stored
   * @param value value to be stored
   * @param typeTokenOfT type token of T (e.g. {@code new TypeToken<> {})
   */
  public <T> void put(@NonNull String key, @NonNull T value, @NonNull TypeToken<T> typeTokenOfT) {
    Preconditions.checkNotNull(key, KEY_IS_NULL);
    Preconditions.checkNotNull(value, VALUE_IS_NULL);
    Preconditions.checkNotNull(typeTokenOfT, TYPE_TOKEN_OF_T_IS_NULL);

    if (!accessorProvider.getAccessors().containsKey(value.getClass())) {
      String jsonValue = jsonConverter.toJson(value, typeTokenOfT.getType());
      editor.putString(key, String.valueOf(jsonValue)).apply();
      return;
    }

    Class<?> classOfValue = value.getClass();

    for (Map.Entry<Class<?>, Accessor<?>> entry : accessorProvider.getAccessors().entrySet()) {
      if (classOfValue.equals(entry.getKey())) {
        @SuppressWarnings("unchecked") Accessor<T> accessor = (Accessor<T>) entry.getValue();
        accessor.put(key, value);
      }
    }
  }

  /**
   * Removes value defined by a given key.
   *
   * @param key key of the preference to be removed
   */
  public void remove(@NonNull String key) {
    Preconditions.checkNotNull(key, KEY_IS_NULL);
    if (!contains(key)) {
      return;
    }

    editor.remove(key).apply();
  }

  /**
   * Clears all SharedPreferences.
   */
  public void clear() {
    if (size() == 0) {
      return;
    }

    editor.clear().apply();
  }

  /**
   * Returns number of all items stored in SharedPreferences.
   *
   * @return number of all stored items
   */
  public int size() {
    return preferences.getAll().size();
  }
}
