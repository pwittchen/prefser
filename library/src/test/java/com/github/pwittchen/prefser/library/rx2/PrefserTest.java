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

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class) @Config(manifest = Config.NONE)
public final class PrefserTest {

  private static final String GIVEN_KEY = "givenKey";
  private static final String GIVEN_STRING_VALUE = "givenStringValue";
  private static final String KEY_WHICH_DOES_NOT_EXIST = "keyWhichDoesNotExist";
  private Prefser prefser;

  private class CustomClass {
    private int valueOne;
    private String valueTwo;

    private CustomClass(int valueOne, String valueTwo) {
      this.valueOne = valueOne;
      this.valueTwo = valueTwo;
    }

    @Override public boolean equals(Object o) {
      if (this == o) {
        return true;
      }

      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      CustomClass that = (CustomClass) o;

      if (valueOne != that.valueOne) {
        return false;
      }

      return valueTwo != null ? valueTwo.equals(that.valueTwo) : that.valueTwo == null;
    }

    @Override public int hashCode() {
      int result = valueOne;
      result = 31 * result + (valueTwo != null ? valueTwo.hashCode() : 0);
      return result;
    }
  }

  @Before public void setUp() {
    final Context context = RuntimeEnvironment.application.getApplicationContext();
    prefser = new Prefser(context);
    prefser.clear();
  }

  @After public void tearDown() {
    prefser.clear();
  }

  @Test public void testPrefserShouldNotBeNull() {
    // given: prefser declaration

    // when: prefser initialization in setUp() method

    // then
    assertThat(prefser).isNotNull();
  }

  @Test public void testPrefserShouldNotBeNullWhenCreatedWithPreferences() {
    // given
    SharedPreferences sharedPreferences = mock(SharedPreferences.class);
    SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);
    when(sharedPreferences.edit()).thenReturn(editor);

    // when
    Prefser customPrefser = new Prefser(sharedPreferences);

    // then
    assertThat(customPrefser).isNotNull();
  }

  @Test public void testPrefserWithJsonConverterShouldNotBeNull() {
    // given
    JsonConverter jsonConverter = mock(JsonConverter.class);
    Context context = RuntimeEnvironment.application.getApplicationContext();

    // when
    Prefser customPrefser = new Prefser(context, jsonConverter);

    // then
    assertThat(customPrefser).isNotNull();
  }

  @Test(expected = NullPointerException.class)
  public void testPrefserWithJsonConverterShouldThrowAnExceptionWhenConverterIsNull() {
    // given
    JsonConverter jsonConverter = null;
    Context context = RuntimeEnvironment.application.getApplicationContext();

    // when
    new Prefser(context, jsonConverter);

    // then throw an exception
  }

  @Test public void testPrefserWithSharedPreferencesAndJsonConverterShouldNotBeNull() {
    // given
    JsonConverter jsonConverter = mock(JsonConverter.class);
    SharedPreferences sharedPreferences = mock(SharedPreferences.class);
    SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);
    when(sharedPreferences.edit()).thenReturn(editor);

    // when
    Prefser customPrefser = new Prefser(sharedPreferences, jsonConverter);

    // then
    assertThat(customPrefser).isNotNull();
  }

  @Test(expected = NullPointerException.class)
  public void testPrefserWithSharedPreferencesShouldThrowAnExceptionWhenConverterIsNull() {
    // given
    JsonConverter jsonConverter = null;
    SharedPreferences sharedPreferences = mock(SharedPreferences.class);

    // when
    new Prefser(sharedPreferences, jsonConverter);

    // then throw an exception
  }

  @Test public void testPreferencesShouldNotBeNull() {
    // given
    prefser.clear();

    // when
    SharedPreferences preferences = prefser.getPreferences();

    // then
    assertThat(preferences).isNotNull();
  }

  @Test public void testContains() throws Exception {
    // given
    prefser.clear();
    String givenValue = GIVEN_STRING_VALUE;
    String givenKey = GIVEN_KEY;

    // when
    prefser.put(givenKey, givenValue);

    // then
    assertThat(prefser.contains(givenKey)).isTrue();
    prefser.remove(givenKey);
    assertThat(prefser.contains(givenKey)).isFalse();
  }

  @Test public void testSize() throws Exception {
    // given
    prefser.clear();
    String keyToRemove = "key1";

    // when
    prefser.put(keyToRemove, 1);
    prefser.put("key2", 2);
    prefser.put("key3", 3);

    // then
    assertThat(prefser.size()).isEqualTo(3);
    prefser.remove(keyToRemove);
    assertThat(prefser.size()).isEqualTo(2);
    prefser.clear();
    assertThat(prefser.size()).isEqualTo(0);
  }

  @Test public void testRemove() throws Exception {
    // given
    String givenKey = GIVEN_KEY;
    prefser.put(givenKey, 1);

    // when
    assertThat(prefser.contains(givenKey)).isTrue();
    prefser.remove(givenKey);

    // then
    assertThat(prefser.contains(givenKey)).isFalse();
  }

  @Test public void testRemoveShouldNotCauseErrorWhileRemovingKeyWhichDoesNotExist() {
    // given
    prefser.clear();
    String keyWhichDoesNotExist = KEY_WHICH_DOES_NOT_EXIST;

    // when
    prefser.remove(keyWhichDoesNotExist);

    // then
    assertThat(prefser.contains(keyWhichDoesNotExist)).isFalse();
  }

  @Test(expected = NullPointerException.class)
  public void testShouldThrowAnExceptionWhileRemovingNullKey() {
    // given: nothing

    // when
    prefser.remove(null);

    // then should throw an exception
  }

  @Test public void testClear() throws Exception {
    // given
    prefser.clear();
    prefser.put("key1", 1);
    prefser.put("key2", 2);
    prefser.put("key3", 3);

    // when
    assertThat(prefser.size()).isEqualTo(3);
    prefser.clear();

    // then
    assertThat(prefser.size()).isEqualTo(0);
  }

  @Test public void testPutBoolean() throws Exception {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    Boolean givenValue = true;
    Boolean defaultValue = false;

    // when
    prefser.put(givenKey, givenValue);

    // then
    Boolean readValue = prefser.get(givenKey, Boolean.class, defaultValue);
    assertThat(givenValue).isEqualTo(readValue);
    prefser.remove(givenKey);
  }

  @Test public void testPutBooleanPrimitive() throws Exception {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    boolean givenValue = true;
    boolean defaultValue = false;

    // when
    prefser.put(givenKey, givenValue);

    // then
    boolean readValue = prefser.get(givenKey, Boolean.class, defaultValue);
    assertThat(givenValue).isEqualTo(readValue);
    prefser.remove(givenKey);
  }

  @Test public void testPutFloat() throws Exception {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    Float givenValue = 41f;
    Float defaultValue = 42f;

    // when
    prefser.put(givenKey, givenValue);

    // then
    Float readValue = prefser.get(givenKey, Float.class, defaultValue);
    assertThat(givenValue).isEqualTo(readValue);
    prefser.remove(givenKey);
  }

  @Test public void testPutFloatPrimitive() throws Exception {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    float givenValue = 41f;
    float defaultValue = 42f;

    // when
    prefser.put(givenKey, givenValue);

    // then
    float readValue = prefser.get(givenKey, Float.class, defaultValue);
    assertThat(givenValue).isEqualTo(readValue);
    prefser.remove(givenKey);
  }

  @Test public void testPutInteger() throws Exception {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    Integer givenValue = 42;
    Integer defaultValue = 43;

    // when
    prefser.put(givenKey, givenValue);

    // then
    Integer readValue = prefser.get(givenKey, Integer.class, defaultValue);
    assertThat(givenValue).isEqualTo(readValue);
    prefser.remove(givenKey);
  }

  @Test public void testPutIntegerPrimitive() throws Exception {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    int givenValue = 42;
    int defaultValue = 43;

    // when
    prefser.put(givenKey, givenValue);

    // then
    int readValue = prefser.get(givenKey, Integer.class, defaultValue);
    assertThat(givenValue).isEqualTo(readValue);
    prefser.remove(givenKey);
  }

  @Test public void testPutLong() throws Exception {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    Long givenValue = 43L;
    Long defaultValue = 44L;

    // when
    prefser.put(givenKey, givenValue);

    // then
    Long readValue = prefser.get(givenKey, Long.class, defaultValue);
    assertThat(givenValue).isEqualTo(readValue);
    prefser.remove(givenKey);
  }

  @Test public void testPutLongPrimitive() throws Exception {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    long givenValue = 43L;
    long defaultValue = 44L;

    // when
    prefser.put(givenKey, givenValue);

    // then
    long readValue = prefser.get(givenKey, Long.class, defaultValue);
    assertThat(givenValue).isEqualTo(readValue);
    prefser.remove(givenKey);
  }

  @Test public void testPutDouble() throws Exception {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    Double givenValue = 44.5;
    Double defaultValue = 46.7;

    // when
    prefser.put(givenKey, givenValue);

    // then
    Double readValue = prefser.get(givenKey, Double.class, defaultValue);
    assertThat(givenValue).isEqualTo(readValue);
    prefser.remove(givenKey);
  }

  @Test public void testPutDoublePrimitive() throws Exception {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    double givenValue = 44.5;
    double defaultValue = 48.3;

    // when
    prefser.put(givenKey, givenValue);

    // then
    double readValue = prefser.get(givenKey, Double.class, defaultValue);
    assertThat(givenValue).isEqualTo(readValue);
    prefser.remove(givenKey);
  }

  @Test public void testPutString() throws Exception {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    String givenValue = "sampleValueExplicit";
    String defaultValue = "sampleDefaultValue";

    // when
    prefser.put(givenKey, givenValue);

    // then
    String readValue = prefser.get(givenKey, String.class, defaultValue);
    assertThat(givenValue).isEqualTo(readValue);
    prefser.remove(givenKey);
  }

  @Test public void testPutCustomObject() throws Exception {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    CustomClass givenObject = new CustomClass(23, "someText");
    CustomClass defaultObject = new CustomClass(67, "defaultText");

    // when
    prefser.put(givenKey, givenObject);

    // then
    CustomClass readObject = prefser.get(givenKey, CustomClass.class, defaultObject);
    assertThat(givenObject).isEqualTo(readObject);
    prefser.remove(givenKey);
  }

  @Test public void testPutListOfBooleans() throws Exception {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    List<Boolean> booleans = Arrays.asList(true, false, true);
    List<Boolean> defaultBooleans = Arrays.asList(false, false, false);

    // when
    prefser.put(givenKey, booleans);

    // then
    TypeToken<List<Boolean>> typeToken = new TypeToken<List<Boolean>>() {
    };
    List<Boolean> readObject = prefser.get(givenKey, typeToken, defaultBooleans);
    assertThat(booleans).isEqualTo(readObject);
    prefser.remove(givenKey);
  }

  @Test public void testPutListOfFloats() throws Exception {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    List<Float> floats = Arrays.asList(1.0f, 2.1f, 3.4f);
    List<Float> defaultFloats = Arrays.asList(0f, 0f, 0f);

    // when
    prefser.put(givenKey, floats);

    // then
    TypeToken<List<Float>> typeToken = new TypeToken<List<Float>>() {
    };
    List<Float> readObject = prefser.get(givenKey, typeToken, defaultFloats);
    assertThat(floats).isEqualTo(readObject);
    prefser.remove(givenKey);
  }

  @Test public void testPutListOfIntegers() throws Exception {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    List<Integer> integers = Arrays.asList(1, 2, 3);
    List<Integer> defaultIntegers = Arrays.asList(0, 0, 0);

    // when
    prefser.put(givenKey, integers);

    // then
    TypeToken<List<Integer>> typeToken = new TypeToken<List<Integer>>() {
    };
    List<Integer> readObject = prefser.get(givenKey, typeToken, defaultIntegers);
    assertThat(integers).isEqualTo(readObject);
    prefser.remove(givenKey);
  }

  @Test public void testPutListOfLongs() throws Exception {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    List<Long> longs = Arrays.asList(1L, 2L, 3L);
    List<Long> defaultLongs = Arrays.asList(0L, 0L, 0L);

    // when
    prefser.put(givenKey, longs);

    // then
    TypeToken<List<Long>> typeToken = new TypeToken<List<Long>>() {
    };
    List<Long> readObject = prefser.get(givenKey, typeToken, defaultLongs);
    assertThat(longs).isEqualTo(readObject);
    prefser.remove(givenKey);
  }

  @Test public void testPutListOfDoubles() throws Exception {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    List<Double> doubles = Arrays.asList(4.0, 5.1, 6.2);
    List<Double> defaultDoubles = Arrays.asList(4.0, 5.1, 6.2);

    // when
    prefser.put(givenKey, doubles);
    TypeToken<List<Double>> typeToken = new TypeToken<List<Double>>() {
    };
    List<Double> readObject = prefser.get(givenKey, typeToken, defaultDoubles);

    // then
    assertThat(readObject).isEqualTo(doubles);
    prefser.remove(givenKey);
  }

  @Test public void testPutListOfStrings() throws Exception {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    List<String> strings = Arrays.asList("yet", "another", "list");
    List<String> defaultStrings = Arrays.asList("default", "string", "values");

    // when
    prefser.put(givenKey, strings);
    TypeToken<List<String>> typeToken = new TypeToken<List<String>>() {
    };
    List<String> readObject = prefser.get(givenKey, typeToken, defaultStrings);

    // then
    assertThat(readObject).isEqualTo(strings);
    prefser.remove(givenKey);
  }

  @Test public void testPutListOfCustomObjects() throws Exception {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    CustomClass defaultCustomObject = new CustomClass(0, "zero");
    List<CustomClass> customObjects =
        Arrays.asList(new CustomClass(1, "this is one"), new CustomClass(2, "this is two"),
            new CustomClass(3, "three"));
    List<CustomClass> defaultCustomObjects =
        Arrays.asList(defaultCustomObject, defaultCustomObject, defaultCustomObject);

    // when
    prefser.put(givenKey, customObjects);
    TypeToken<List<CustomClass>> typeToken = new TypeToken<List<CustomClass>>() {
    };
    List<CustomClass> readObject = prefser.get(givenKey, typeToken, defaultCustomObjects);

    // then
    assertThat(readObject).isEqualTo(customObjects);
    prefser.remove(givenKey);
  }

  @Test public void testPutArrayOfBooleans() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    Boolean[] booleans = new Boolean[] { true, false, true };
    Boolean[] defaultArray = new Boolean[] { false, false, false };

    // when
    prefser.put(givenKey, booleans);
    Boolean[] readObject = prefser.get(givenKey, Boolean[].class, defaultArray);

    // then
    assertThat(readObject[0]).isEqualTo(booleans[0]);
    assertThat(readObject[1]).isEqualTo(booleans[1]);
    assertThat(readObject[2]).isEqualTo(booleans[2]);
    prefser.remove(givenKey);
  }

  @Test public void testPutArrayOfFloats() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    Float[] floats = new Float[] { 1f, 2f, 3f };
    Float[] defaultArray = new Float[] { 1f, 1f, 1f };

    // when
    prefser.put(givenKey, floats);
    Float[] readObject = prefser.get(givenKey, Float[].class, defaultArray);

    // then
    assertThat(readObject[0]).isEqualTo(floats[0]);
    assertThat(readObject[1]).isEqualTo(floats[1]);
    assertThat(readObject[2]).isEqualTo(floats[2]);
    prefser.remove(givenKey);
  }

  @Test public void testPutArrayOfInts() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    Integer[] integers = new Integer[] { 1, 2, 3 };
    Integer[] defaultArray = new Integer[] { 0, 0, 0 };

    // when
    prefser.put(givenKey, integers);
    Integer[] readObject = prefser.get(givenKey, Integer[].class, defaultArray);

    // then
    assertThat(readObject[0]).isEqualTo(integers[0]);
    assertThat(readObject[1]).isEqualTo(integers[1]);
    assertThat(readObject[2]).isEqualTo(integers[2]);
    prefser.remove(givenKey);
  }

  @Test public void testPutArrayOfLongs() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    Long[] longs = new Long[] { 1L, 2L, 3L };
    Long[] defaultArray = new Long[] { 1L, 1L, 1L };

    // when
    prefser.put(givenKey, longs);
    Long[] readObject = prefser.get(givenKey, Long[].class, defaultArray);

    // then
    assertThat(readObject[0]).isEqualTo(longs[0]);
    assertThat(readObject[1]).isEqualTo(longs[1]);
    assertThat(readObject[2]).isEqualTo(longs[2]);
    prefser.remove(givenKey);
  }

  @Test public void testPutArrayOfDoubles() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    Double[] doubles = new Double[] { 1.0, 2.3, 4.5 };
    Double[] defaultArray = new Double[] { 1.0, 1.0, 1.0 };

    // when
    prefser.put(givenKey, doubles);
    Double[] readObject = prefser.get(givenKey, Double[].class, defaultArray);

    // then
    assertThat(readObject[0]).isEqualTo(doubles[0]);
    assertThat(readObject[1]).isEqualTo(doubles[1]);
    assertThat(readObject[2]).isEqualTo(doubles[2]);
    prefser.remove(givenKey);
  }

  @Test public void testPutArrayOfStrings() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    String[] strings = new String[] { "hey", "I am", "array of strings!" };
    String[] defaultArray = new String[] { "", "", "" };

    // when
    prefser.put(givenKey, strings);
    String[] readObject = prefser.get(givenKey, String[].class, defaultArray);

    // then
    assertThat(readObject[0]).isEqualTo(strings[0]);
    assertThat(readObject[1]).isEqualTo(strings[1]);
    assertThat(readObject[2]).isEqualTo(strings[2]);
    prefser.remove(givenKey);
  }

  @Test public void testPutArrayOfCustomObjects() {
    // given
    prefser.clear();
    String givenKey = GIVEN_KEY;
    CustomClass defaultCustomObject = new CustomClass(1, "");

    CustomClass[] customClassesArray = new CustomClass[] {
        new CustomClass(1, "first"), new CustomClass(2, "second"), new CustomClass(3, "third")
    };

    CustomClass[] defaultCustomClassesArray = new CustomClass[] {
        defaultCustomObject, defaultCustomObject, defaultCustomObject
    };

    // when
    prefser.put(givenKey, customClassesArray);
    CustomClass[] readObject =
        prefser.get(givenKey, CustomClass[].class, defaultCustomClassesArray);

    // then
    assertThat(readObject[0]).isEqualTo(customClassesArray[0]);
    assertThat(readObject[1]).isEqualTo(customClassesArray[1]);
    assertThat(readObject[2]).isEqualTo(customClassesArray[2]);
    prefser.remove(givenKey);
  }

  @Test @TargetApi(value = 11) @SuppressWarnings("deprecation of assertThat for Java Collections")
  public void testPutSetOfStrings() {
    // given
    prefser.clear();
    Set<String> strings = new HashSet<>(Arrays.asList("one", "two", "three"));
    Set<String> defaultStrings = new HashSet<>(Arrays.asList("this", "is", "default"));
    String givenKey = GIVEN_KEY;

    // when
    // we put set of string in a "classical way"
    prefser.getPreferences().edit().putStringSet(givenKey, strings).apply();

    // then
    // we read set of string in a "classical way"
    Set<String> readObject = prefser.getPreferences().getStringSet(givenKey, defaultStrings);
    assertThat(readObject).isEqualTo(strings);
    prefser.remove(givenKey);
  }

  @Test public void testPutSetOfDoubles() {
    // given
    prefser.clear();
    Set<Double> doubles = new HashSet<>(Arrays.asList(1.2, 2.3, 3.0));
    Set<Double> defaultDoubles = new HashSet<>(Arrays.asList(1.0, 1.0, 1.0));
    String givenKey = GIVEN_KEY;

    // when
    prefser.put(givenKey, doubles);

    // then
    TypeToken<Set<Double>> typeToken = new TypeToken<Set<Double>>() {
    };
    Set<Double> readObject = prefser.get(givenKey, typeToken, defaultDoubles);
    assertThat(readObject).isEqualTo(doubles);
    assertThat(readObject).isInstanceOf(Set.class);
    prefser.remove(givenKey);
  }

  @Test public void testGetDefaultBoolean() {
    // given
    prefser.clear();
    String keyWhichDoesNotExist = KEY_WHICH_DOES_NOT_EXIST;
    boolean defaultValue = true;

    // when
    boolean readValue = prefser.get(keyWhichDoesNotExist, Boolean.class, defaultValue);

    // then
    assertThat(readValue).isEqualTo(defaultValue);
  }

  @Test public void testGetDefaultFloat() {
    // given
    prefser.clear();
    String keyWhichDoesNotExist = KEY_WHICH_DOES_NOT_EXIST;
    float defaultValue = 42f;

    // when
    float readValue = prefser.get(keyWhichDoesNotExist, Float.class, defaultValue);

    // then
    assertThat(readValue).isEqualTo(defaultValue);
  }

  @Test public void testGetDefaultInteger() {
    // given
    prefser.clear();
    String keyWhichDoesNotExist = KEY_WHICH_DOES_NOT_EXIST;
    int defaultValue = 43;

    // when
    int readValue = prefser.get(keyWhichDoesNotExist, Integer.class, defaultValue);

    // then
    assertThat(readValue).isEqualTo(defaultValue);
  }

  @Test public void testGetDefaultLong() {
    // given
    prefser.clear();
    String keyWhichDoesNotExist = KEY_WHICH_DOES_NOT_EXIST;
    long defaultValue = 44L;

    // when
    long readValue = prefser.get(keyWhichDoesNotExist, Long.class, defaultValue);

    // then
    assertThat(readValue).isEqualTo(defaultValue);
  }

  @Test public void testGetDefaultDouble() {
    // given
    prefser.clear();
    String keyWhichDoesNotExist = KEY_WHICH_DOES_NOT_EXIST;
    double defaultValue = 45.6;

    // when
    double readValue = prefser.get(keyWhichDoesNotExist, Double.class, defaultValue);

    // then
    assertThat(readValue).isEqualTo(defaultValue);
  }

  @Test public void testGetDefaultString() {
    // given
    prefser.clear();
    String keyWhichDoesNotExist = KEY_WHICH_DOES_NOT_EXIST;
    String defaultValue = "default string value";

    // when
    String readValue = prefser.get(keyWhichDoesNotExist, String.class, defaultValue);

    // then
    assertThat(readValue).isEqualTo(defaultValue);
  }

  @Test public void testGetDefaultCustomObject() {
    // given
    prefser.clear();
    String keyWhichDoesNotExist = KEY_WHICH_DOES_NOT_EXIST;
    CustomClass defaultValue = new CustomClass(23, "string in default object");

    // when
    CustomClass readValue = prefser.get(keyWhichDoesNotExist, CustomClass.class, defaultValue);

    // then
    assertThat(readValue).isEqualTo(defaultValue);
  }

  @Test public void testGetDefaultListOfBooleans() {
    // given
    prefser.clear();
    String keyWhichDoesNotExist = KEY_WHICH_DOES_NOT_EXIST;
    List<Boolean> defaultValue = Arrays.asList(true, false, true);

    // when
    TypeToken<List<Boolean>> typeToken = new TypeToken<List<Boolean>>() {
    };
    List<Boolean> readValue = prefser.get(keyWhichDoesNotExist, typeToken, defaultValue);

    // then
    assertThat(readValue).isEqualTo(defaultValue);
  }

  @Test public void testGetDefaultListOfFloats() {
    // given
    prefser.clear();
    String keyWhichDoesNotExist = KEY_WHICH_DOES_NOT_EXIST;
    List<Float> defaultValue = Arrays.asList(1f, 2f, 3f);

    // when
    TypeToken<List<Float>> typeToken = new TypeToken<List<Float>>() {
    };
    List<Float> readValue = prefser.get(keyWhichDoesNotExist, typeToken, defaultValue);

    // then
    assertThat(readValue).isEqualTo(defaultValue);
  }

  @Test public void testGetDefaultListOfIntegers() {
    // given
    prefser.clear();
    String keyWhichDoesNotExist = KEY_WHICH_DOES_NOT_EXIST;
    List<Integer> defaultValue = new ArrayList<>(Arrays.asList(1, 2, 3));

    // when
    TypeToken<List<Integer>> typeToken = new TypeToken<List<Integer>>() {
    };
    List<Integer> readValue = prefser.get(keyWhichDoesNotExist, typeToken, defaultValue);

    // then
    assertThat(readValue).isEqualTo(defaultValue);
  }

  @Test public void testGetDefaultListOfLongs() {
    // given
    prefser.clear();
    String keyWhichDoesNotExist = KEY_WHICH_DOES_NOT_EXIST;
    List<Long> defaultValue = new ArrayList<>(Arrays.asList(1L, 2L, 3L));

    // when
    TypeToken<List<Long>> typeToken = new TypeToken<List<Long>>() {
    };
    List<Long> readValue = prefser.get(keyWhichDoesNotExist, typeToken, defaultValue);

    // then
    assertThat(readValue).isEqualTo(defaultValue);
  }

  @Test public void testGetDefaultListOfDoubles() {
    // given
    prefser.clear();
    String keyWhichDoesNotExist = KEY_WHICH_DOES_NOT_EXIST;
    List<Double> defaultValue = new ArrayList<>(Arrays.asList(1.2, 2.3, 3.4));

    // when
    TypeToken<List<Double>> typeToken = new TypeToken<List<Double>>() {
    };
    List<Double> readValue = prefser.get(keyWhichDoesNotExist, typeToken, defaultValue);

    // then
    assertThat(readValue).isEqualTo(defaultValue);
  }

  @Test public void testGetDefaultListOfStrings() {
    // given
    prefser.clear();
    String keyWhichDoesNotExist = KEY_WHICH_DOES_NOT_EXIST;
    List<String> defaultValue =
        new ArrayList<>(Arrays.asList("value one", " value two", " value three"));

    // when
    TypeToken<List<String>> typeToken = new TypeToken<List<String>>() {
    };
    List<String> readValue = prefser.get(keyWhichDoesNotExist, typeToken, defaultValue);

    // then
    assertThat(readValue).isEqualTo(defaultValue);
  }

  @Test public void testGetDefaultListOfCustomObjects() {
    // given
    prefser.clear();
    String keyWhichDoesNotExist = KEY_WHICH_DOES_NOT_EXIST;
    CustomClass defaultCustomObject = new CustomClass(0, "zero");
    List<CustomClass> defaultObjects =
        Arrays.asList(defaultCustomObject, defaultCustomObject, defaultCustomObject);

    // when
    TypeToken<List<CustomClass>> typeToken = new TypeToken<List<CustomClass>>() {
    };
    List<CustomClass> readValue = prefser.get(keyWhichDoesNotExist, typeToken, defaultObjects);

    // then
    assertThat(readValue).isEqualTo(defaultObjects);
  }

  @Test public void testGetDefaultArrayOfBooleans() {
    // given
    prefser.clear();
    String keyWhichDoesNotExist = KEY_WHICH_DOES_NOT_EXIST;
    Boolean[] defaultValue = new Boolean[] { true, false, true };

    // when
    Boolean[] readValue = prefser.get(keyWhichDoesNotExist, Boolean[].class, defaultValue);

    // then
    assertThat(readValue).isEqualTo(defaultValue);
  }

  @Test public void testGetDefaultArrayOfFloats() {
    // given
    prefser.clear();
    String keyWhichDoesNotExist = KEY_WHICH_DOES_NOT_EXIST;
    Float[] defaultValue = new Float[] { 1f, 2f, 3f };

    // when
    Float[] readValue = prefser.get(keyWhichDoesNotExist, Float[].class, defaultValue);

    // then
    assertThat(readValue).isEqualTo(defaultValue);
  }

  @Test public void testGetDefaultArrayOfIntegers() {
    // given
    prefser.clear();
    String keyWhichDoesNotExist = KEY_WHICH_DOES_NOT_EXIST;
    Integer[] defaultValue = new Integer[] { 2, 3, 4 };

    // when
    Integer[] readValue = prefser.get(keyWhichDoesNotExist, Integer[].class, defaultValue);

    // then
    assertThat(readValue).isEqualTo(defaultValue);
  }

  @Test public void testGetDefaultArrayOfLongs() {
    // given
    prefser.clear();
    String keyWhichDoesNotExist = KEY_WHICH_DOES_NOT_EXIST;
    Long[] defaultValue = new Long[] { 3L, 4L, 5L };

    // when
    Long[] readValue = prefser.get(keyWhichDoesNotExist, Long[].class, defaultValue);

    // then
    assertThat(readValue).isEqualTo(defaultValue);
  }

  @Test public void testGetDefaultArrayOfDoubles() {
    // given
    prefser.clear();
    String keyWhichDoesNotExist = KEY_WHICH_DOES_NOT_EXIST;
    Double[] defaultValue = new Double[] { 1.2, 3.0, 4.5 };

    // when
    Double[] readValue = prefser.get(keyWhichDoesNotExist, Double[].class, defaultValue);

    // then
    assertThat(readValue).isEqualTo(defaultValue);
  }

  @Test public void testGetDefaultArrayOfStrings() {
    // given
    prefser.clear();
    String keyWhichDoesNotExist = KEY_WHICH_DOES_NOT_EXIST;
    String[] defaultValue = new String[] { "first", "next", "another one" };

    // when
    String[] readValue = prefser.get(keyWhichDoesNotExist, String[].class, defaultValue);

    // then
    assertThat(readValue).isEqualTo(defaultValue);
  }

  @Test public void testGetDefaultArrayOfCustomObjects() {
    // given
    prefser.clear();
    String keyWhichDoesNotExist = KEY_WHICH_DOES_NOT_EXIST;
    CustomClass[] defaultValue = new CustomClass[] {
        new CustomClass(1, "Hey"), new CustomClass(2, "Dude"),
        new CustomClass(3, "Don't make it bad")
    };

    // when
    CustomClass[] readValue = prefser.get(keyWhichDoesNotExist, CustomClass[].class, defaultValue);

    // then
    assertThat(readValue).isEqualTo(defaultValue);
  }

  @Test(expected = NullPointerException.class)
  public void testShouldThrownAnExceptionWhenPreferencesAreNull() {
    // given
    SharedPreferences sharedPreferences = null;

    // when
    new Prefser(sharedPreferences);

    // then
    // throw an exception
  }

  @Test(expected = NullPointerException.class)
  public void testShouldThrowAnExceptionWhenKeyForGetIsNull() {
    // given
    String key = null;
    Class<String> classOfT = String.class;

    // when
    prefser.get(key, classOfT, "");

    // then
    // throw an exception
  }

  @Test(expected = NullPointerException.class)
  public void testShouldThrowAnExceptionWhenClassOfTForGetIsNull() {
    // given
    String key = GIVEN_KEY;
    Class classOfT = null;
    prefser.put(key, GIVEN_STRING_VALUE);

    // when
    prefser.get(key, classOfT, "");

    // then
    // throw an exception
  }

  @Test(expected = NullPointerException.class)
  public void testShouldThrowAnExceptionWhenKeyForGetWithDefaultValueIsNull() {
    // given
    String key = null;
    Class<String> classOfT = String.class;
    String defaultValue = "some default value";

    // when
    prefser.get(key, classOfT, defaultValue);

    // then
    // throw an exception
  }

  @Test(expected = NullPointerException.class)
  public void testShouldThrowAnExceptionWhenClassOfTForGetWithDefaultValueIsNull() {
    // given
    String key = GIVEN_KEY;
    Class<String> classOfT = null;
    String defaultValue = "some default value";

    // when
    prefser.get(key, classOfT, defaultValue);

    // then
    // throw an exception
  }

  @Test(expected = NullPointerException.class)
  public void testPutShouldThrowAnExceptionWhenKeyIsNullForPut() {
    // given
    String key = null;
    String value = GIVEN_STRING_VALUE;

    // when
    prefser.put(key, value);

    // then
    // throw an exception
  }

  @Test(expected = NullPointerException.class)
  public void testPutShouldThrowAnExceptionWhenValueIsNullForPut() {
    // given
    String key = GIVEN_KEY;
    String value = null;

    // when
    prefser.put(key, value);

    // then
    // throw an exception
  }

  @Test(expected = NullPointerException.class)
  public void testPutShouldThrowAnExceptionWhenKeyAndValueAreNullForPut() {
    // given
    String key = null;
    String value = null;

    // when
    prefser.put(key, value);

    // then
    // throw an exception
  }

  @Test(expected = NullPointerException.class)
  public void testPutShouldThrowAnExceptionWhenKeyIsNullForRemove() {
    // given
    String key = null;

    // when
    prefser.remove(key);

    // then
    // throw an exception
  }

  @Test public void testGetShouldReturnNullForStringType() {
    // given
    prefser.clear();
    String keyWhichDoesNotExist = KEY_WHICH_DOES_NOT_EXIST;

    // when
    String value = prefser.get(keyWhichDoesNotExist, String.class, null);

    // then
    assertThat(value).isNull();
  }
}