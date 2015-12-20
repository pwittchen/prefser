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
