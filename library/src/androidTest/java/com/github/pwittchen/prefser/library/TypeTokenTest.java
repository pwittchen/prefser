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

import android.support.test.runner.AndroidJUnit4;
import java.lang.reflect.Type;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class) final public class TypeTokenTest {

  @Test(expected = RuntimeException.class)
  public void testShouldThrowRuntimeExceptionWithMissingTypeParameter() {
    TypeToken typeTokenWithMissingType = new TypeToken() {
    };
  }

  @Test(expected = NullPointerException.class)
  public void testShouldThrowNullPointerExceptionForNullClassOfTInFromClassMethod() {
    TypeToken.fromClass(null);
  }

  @Test(expected = NullPointerException.class)
  public void testShouldThrowNullPointerExceptionForNullClassOfTInFromValueMethod() {
    TypeToken.fromValue(null);
  }

  @Test public void testShouldReturnTypeWhenItIsGivenWhileObjectCreation() {
    // given
    TypeToken<Boolean> typeToken = new TypeToken<Boolean>() {
    };

    // when
    Type type = typeToken.getType();

    // then
    assertThat(type).isEqualTo(Boolean.class);
  }
}
