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
package com.github.pwittchen.prefser.library.rx2;

import androidx.annotation.NonNull;
import com.google.gson.Gson;
import java.lang.reflect.Type;

public final class GsonConverter implements JsonConverter {

  private final Gson gson;

  public GsonConverter(@NonNull Gson gson) {
    this.gson = gson;
  }

  public GsonConverter() {
    gson = new Gson();
  }

  @Override public <T> T fromJson(String json, Type typeOfT) {
    return gson.fromJson(json, typeOfT);
  }

  @Override public <T> String toJson(T object, Type typeOfT) {
    return gson.toJson(object, typeOfT);
  }
}
