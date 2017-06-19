/*
 * Copyright (C) 2017 Piotr Wittchen
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
package com.github.pwittchen.prefser.library.rx2;

import android.content.SharedPreferences;
import java.util.HashMap;
import java.util.Map;

class PreferencesAccessorsProvider implements AccessorsProvider {
  private final SharedPreferences preferences;
  private final SharedPreferences.Editor editor;
  private final Map<Class<?>, Accessor<?>> accessors = new HashMap<>();

  PreferencesAccessorsProvider(SharedPreferences preferences, SharedPreferences.Editor editor) {
    Preconditions.checkNotNull(preferences, "preferences == null");
    Preconditions.checkNotNull(editor, "editor == null");
    this.preferences = preferences;
    this.editor = editor;
    createAccessors();
  }

  @Override public Map<Class<?>, Accessor<?>> getAccessors() {
    return accessors;
  }

  private void createAccessors() {
    createBooleanAccessor();
    createFloatAccessor();
    createIntegerAccessor();
    createLongAccessor();
    createDoubleAccessor();
    createStringAccessor();
  }

  private void createBooleanAccessor() {
    accessors.put(Boolean.class, new Accessor<Boolean>() {
      @Override public Boolean get(String key, Boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
      }

      @Override public void put(String key, Boolean value) {
        editor.putBoolean(key, value).apply();
      }
    });
  }

  private void createFloatAccessor() {
    accessors.put(Float.class, new Accessor<Float>() {
      @Override public Float get(String key, Float defaultValue) {
        return preferences.getFloat(key, defaultValue);
      }

      @Override public void put(String key, Float value) {
        editor.putFloat(key, value).apply();
      }
    });
  }

  private void createIntegerAccessor() {
    accessors.put(Integer.class, new Accessor<Integer>() {
      @Override public Integer get(String key, Integer defaultValue) {
        return preferences.getInt(key, defaultValue);
      }

      @Override public void put(String key, Integer value) {
        editor.putInt(key, value).apply();
      }
    });
  }

  private void createLongAccessor() {
    accessors.put(Long.class, new Accessor<Long>() {
      @Override public Long get(String key, Long defaultValue) {
        return preferences.getLong(key, defaultValue);
      }

      @Override public void put(String key, Long value) {
        editor.putLong(key, value).apply();
      }
    });
  }

  private void createDoubleAccessor() {
    accessors.put(Double.class, new Accessor<Double>() {
      @Override public Double get(String key, Double defaultValue) {
        return Double.valueOf(preferences.getString(key, String.valueOf(defaultValue)));
      }

      @Override public void put(String key, Double value) {
        editor.putString(key, String.valueOf(value)).apply();
      }
    });
  }

  private void createStringAccessor() {
    accessors.put(String.class, new Accessor<String>() {
      @Override public String get(String key, String defaultValue) {
        return preferences.getString(key, String.valueOf(defaultValue));
      }

      @Override public void put(String key, String value) {
        editor.putString(key, String.valueOf(value)).apply();
      }
    });
  }
}
