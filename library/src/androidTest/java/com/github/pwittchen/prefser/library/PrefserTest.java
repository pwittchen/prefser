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

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public final class PrefserTest {

    private Prefser prefser;

    private class CustomClass {
        private int valueOne;
        private String valueTwo;

        public CustomClass(int valueOne, String valueTwo) {
            this.valueOne = valueOne;
            this.valueTwo = valueTwo;
        }

        @Override
        public boolean equals(Object other) {
            boolean isEqual = false;

            if (other instanceof CustomClass) {
                isEqual = ((CustomClass) other).valueOne == valueOne;
                isEqual |= ((CustomClass) other).valueTwo.equals(valueTwo);
            }

            return isEqual;
        }

        @Override
        @TargetApi(value = 19)
        public int hashCode() {
            return Objects.hash(valueOne, valueTwo);
        }
    }

    @Before
    public void setUp() {
        prefser = new Prefser(InstrumentationRegistry.getContext());
        prefser.clear();
    }

    @After
    public void tearDown() {
        prefser.clear();
    }

    public void testPrefserShouldNotBeNull() {
        // given: prefser declaration

        // when: prefser initialization in setUp() method

        // then
        assertThat(prefser).isNotNull();
    }

    @Test
    public void testPreferencesShouldNotBeNull() {
        // given
        prefser.clear();

        // when
        SharedPreferences preferences = prefser.getPreferences();

        // then
        assertThat(preferences).isNotNull();
    }

    @Test
    public void testContains() throws Exception {
        // given
        prefser.clear();
        String givenValue = "sample value";
        String givenKey = "sampleKey";

        // when
        prefser.put(givenKey, givenValue);

        // then
        assertThat(prefser.contains(givenKey)).isTrue();
        prefser.remove(givenKey);
        assertThat(prefser.contains(givenKey)).isFalse();
    }

    @Test
    public void testSize() throws Exception {
        // given
        prefser.clear();

        // when
        prefser.put("key1", 1);
        prefser.put("key2", 2);
        prefser.put("key3", 3);

        // then
        assertThat(prefser.size()).isEqualTo(3);
        prefser.remove("key1");
        assertThat(prefser.size()).isEqualTo(2);
        prefser.clear();
        assertThat(prefser.size()).isEqualTo(0);
    }

    @Test
    public void testRemove() throws Exception {
        // given
        String givenKey = "key1";
        prefser.put(givenKey, 1);

        // when
        assertThat(prefser.contains(givenKey)).isTrue();
        prefser.remove(givenKey);

        // then
        assertThat(prefser.contains(givenKey)).isFalse();
    }

    @Test
    public void testClear() throws Exception {
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

    @Test
    public void testPutBoolean() throws Exception {
        // given
        prefser.clear();
        String givenKey = "sampleKey";
        Boolean givenValue = true;
        Boolean defaultValue = false;

        // when
        prefser.put(givenKey, givenValue);

        // then
        Boolean readValue = prefser.get(givenKey, Boolean.class, defaultValue);
        assertThat(givenValue).isEqualTo(readValue);
        prefser.remove(givenKey);
    }

    @Test
    public void testPutBooleanPrimitive() throws Exception {
        // given
        prefser.clear();
        String givenKey = "sampleKey";
        boolean givenValue = true;
        boolean defaultValue = false;

        // when
        prefser.put(givenKey, givenValue);

        // then
        boolean readValue = prefser.get(givenKey, Boolean.class, defaultValue);
        assertThat(givenValue).isEqualTo(readValue);
        prefser.remove(givenKey);
    }

    @Test
    public void testPutFloat() throws Exception {
        // given
        prefser.clear();
        String givenKey = "sampleKey";
        Float givenValue = 41f;
        Float defaultValue = 42f;

        // when
        prefser.put(givenKey, givenValue);

        // then
        Float readValue = prefser.get(givenKey, Float.class, defaultValue);
        assertThat(givenValue).isEqualTo(readValue);
        prefser.remove(givenKey);
    }

    @Test
    public void testPutFloatPrimitive() throws Exception {
        // given
        prefser.clear();
        String givenKey = "sampleKey";
        float givenValue = 41f;
        float defaultValue = 42f;

        // when
        prefser.put(givenKey, givenValue);

        // then
        float readValue = prefser.get(givenKey, Float.class, defaultValue);
        assertThat(givenValue).isEqualTo(readValue);
        prefser.remove(givenKey);
    }

    @Test
    public void testPutInteger() throws Exception {
        // given
        prefser.clear();
        String givenKey = "sampleKey";
        Integer givenValue = 42;
        Integer defaultValue = 43;

        // when
        prefser.put(givenKey, givenValue);

        // then
        Integer readValue = prefser.get(givenKey, Integer.class, defaultValue);
        assertThat(givenValue).isEqualTo(readValue);
        prefser.remove(givenKey);
    }

    @Test
    public void testPutIntegerPrimitive() throws Exception {
        // given
        prefser.clear();
        String givenKey = "sampleKey";
        int givenValue = 42;
        int defaultValue = 43;

        // when
        prefser.put(givenKey, givenValue);

        // then
        int readValue = prefser.get(givenKey, Integer.class, defaultValue);
        assertThat(givenValue).isEqualTo(readValue);
        prefser.remove(givenKey);
    }

    @Test
    public void testPutLong() throws Exception {
        // given
        prefser.clear();
        String givenKey = "sampleKey";
        Long givenValue = 43l;
        Long defaultValue = 44l;

        // when
        prefser.put(givenKey, givenValue);

        // then
        Long readValue = prefser.get(givenKey, Long.class, defaultValue);
        assertThat(givenValue).isEqualTo(readValue);
        prefser.remove(givenKey);
    }

    @Test
    public void testPutLongPrimitive() throws Exception {
        // given
        prefser.clear();
        String givenKey = "sampleKey";
        long givenValue = 43l;
        long defaultValue = 44l;

        // when
        prefser.put(givenKey, givenValue);

        // then
        long readValue = prefser.get(givenKey, Long.class, defaultValue);
        assertThat(givenValue).isEqualTo(readValue);
        prefser.remove(givenKey);
    }

    @Test
    public void testPutDouble() throws Exception {
        // given
        prefser.clear();
        String givenKey = "sampleKey";
        Double givenValue = 44.5;
        Double defaultValue = 46.7;

        // when
        prefser.put(givenKey, givenValue);

        // then
        Double readValue = prefser.get(givenKey, Double.class, defaultValue);
        assertThat(givenValue).isEqualTo(readValue);
        prefser.remove(givenKey);
    }

    @Test
    public void testPutDoublePrimitive() throws Exception {
        // given
        prefser.clear();
        String givenKey = "sampleKey";
        double givenValue = 44.5;
        double defaultValue = 48.3;

        // when
        prefser.put(givenKey, givenValue);

        // then
        double readValue = prefser.get(givenKey, Double.class, defaultValue);
        assertThat(givenValue).isEqualTo(readValue);
        prefser.remove(givenKey);
    }

    @Test
    public void testPutString() throws Exception {
        // given
        prefser.clear();
        String givenKey = "sampleKey";
        String givenValue = "sampleValueExplicit";
        String defaultValue = "sampleDefaultValue";

        // when
        prefser.put(givenKey, givenValue);

        // then
        String readValue = prefser.get(givenKey, String.class, defaultValue);
        assertThat(givenValue).isEqualTo(readValue);
        prefser.remove(givenKey);
    }

    @Test
    public void testPutCustomObject() throws Exception {
        // given
        prefser.clear();
        String givenKey = "sampleKey";
        CustomClass givenObject = new CustomClass(23, "someText");
        CustomClass defaultObject = new CustomClass(67, "defaultText");

        // when
        prefser.put(givenKey, givenObject);

        // then
        CustomClass readObject = prefser.get(givenKey, CustomClass.class, defaultObject);
        assertThat(givenObject).isEqualTo(readObject);
        prefser.remove(givenKey);
    }

    @Test
    public void testPutListOfBooleans() throws Exception {
        // given
        prefser.clear();
        String givenKey = "sampleKey";
        List<Boolean> booleans = new ArrayList<>(Arrays.asList(true, false, true));
        List<Boolean> defaultBooleans = new ArrayList<>(Arrays.asList(false, false, false));

        // when
        prefser.put(givenKey, booleans);

        // then
        List<Boolean> readObject = prefser.get(givenKey, List.class, defaultBooleans);
        assertThat(booleans).isEqualTo(readObject);
        prefser.remove(givenKey);
    }

    @Test
    public void testPutListOfDoubles() throws Exception {
        // given
        prefser.clear();
        String givenKey = "sampleKey";
        List<Double> doubles = new ArrayList<>(Arrays.asList(4.0, 5.1, 6.2));
        List<Double> defaultDoubles = new ArrayList<>(Arrays.asList(4.0, 5.1, 6.2));

        // when
        prefser.put(givenKey, doubles);
        List<Double> readObject = prefser.get(givenKey, List.class, defaultDoubles);

        // then
        assertThat(readObject).isEqualTo(doubles);
        prefser.remove(givenKey);
    }

    @Test
    public void testPutListOfStrings() throws Exception {
        // given
        prefser.clear();
        String givenKey = "sampleKey";
        List<String> strings = new ArrayList<>(Arrays.asList("one", "two", "three"));
        List<String> defaultStrings = new ArrayList<>(Arrays.asList("default", "string", "values"));

        // when
        prefser.put(givenKey, strings);
        List<String> readObject = prefser.get(givenKey, List.class, defaultStrings);

        // then
        assertThat(readObject).isEqualTo(strings);
        prefser.remove(givenKey);
    }

    @Test
    public void testPutArrayOfBooleans() {
        // given
        prefser.clear();
        String givenKey = "sampleKey";
        Boolean[] booleans = new Boolean[]{true, false, true};
        Boolean[] defaultArray = new Boolean[]{false, false, false};

        // when
        prefser.put(givenKey, booleans);
        Boolean[] readObject = prefser.get(givenKey, Boolean[].class, defaultArray);

        // then
        assertThat(readObject).isEqualTo(booleans);
        prefser.remove(givenKey);
    }

    @Test
    public void testPutArrayOfFloats() {
        // given
        prefser.clear();
        String givenKey = "sampleKey";
        Float[] floats = new Float[]{1f, 2f, 3f};
        Float[] defaultArray = new Float[]{1f, 1f, 1f};

        // when
        prefser.put(givenKey, floats);
        Float[] readObject = prefser.get(givenKey, Float[].class, defaultArray);

        // then
        assertThat(readObject).isEqualTo(floats);
        prefser.remove(givenKey);
    }

    @Test
    public void testPutArrayOfInts() {
        // given
        prefser.clear();
        String givenKey = "sampleKey";
        Integer[] integers = new Integer[]{1, 2, 3};
        Integer[] defaultArray = new Integer[]{0, 0, 0};

        // when
        prefser.put(givenKey, integers);
        Integer[] readObject = prefser.get(givenKey, Integer[].class, defaultArray);

        // then
        assertThat(readObject).isEqualTo(integers);
        prefser.remove(givenKey);
    }

    @Test
    public void testPutArrayOfLongs() {
        // given
        prefser.clear();
        String givenKey = "sampleKey";
        Long[] longs = new Long[]{1l, 2l, 3l};
        Long[] defaultArray = new Long[]{1l, 1l, 1l};

        // when
        prefser.put(givenKey, longs);
        Long[] readObject = prefser.get(givenKey, Long[].class, defaultArray);

        // then
        assertThat(readObject).isEqualTo(longs);
        prefser.remove(givenKey);
    }

    @Test
    public void testPutArrayOfDoubles() {
        // given
        prefser.clear();
        String givenKey = "sampleKey";
        Double[] doubles = new Double[]{1.0, 2.3, 4.5};
        Double[] defaultArray = new Double[]{1.0, 1.0, 1.0};

        // when
        prefser.put(givenKey, doubles);
        Double[] readObject = prefser.get(givenKey, Double[].class, defaultArray);

        // then
        assertThat(readObject).isEqualTo(doubles);
        prefser.remove(givenKey);
    }

    @Test
    public void testPutArrayOfStrings() {
        // given
        prefser.clear();
        String givenKey = "sampleKey";
        String[] strings = new String[]{"one", "two", "three"};
        String[] defaultArray = new String[]{"", "", ""};

        // when
        prefser.put(givenKey, strings);
        String[] readObject = prefser.get(givenKey, String[].class, defaultArray);

        // then
        assertThat(readObject).isEqualTo(strings);
        prefser.remove(givenKey);
    }

    @Test
    public void testPutArrayOfCustomObjects() {
        // given
        prefser.clear();
        String givenKey = "sampleKey";
        CustomClass[] customClassesArray = new CustomClass[]{
                new CustomClass(1, "one"),
                new CustomClass(2, "two"),
                new CustomClass(3, "three")
        };

        CustomClass[] defaultCustomClassesArray = new CustomClass[]{
                new CustomClass(1, ""),
                new CustomClass(1, ""),
                new CustomClass(1, "")
        };

        // when
        prefser.put(givenKey, customClassesArray);
        CustomClass[] readObject = prefser.get(givenKey, CustomClass[].class, defaultCustomClassesArray);

        // then
        assertThat(readObject).isEqualTo(customClassesArray);
        prefser.remove(givenKey);
    }

    @Test
    @TargetApi(value = 11)
    @SuppressWarnings("deprecation of assertThat for Java Collections")
    public void testPutSetOfStrings() {
        // given
        prefser.clear();
        Set<String> strings = new HashSet<>(Arrays.asList("one", "two", "three"));
        Set<String> defaultStrings = new HashSet<>(Arrays.asList("this", "is", "default"));
        String givenKey = "sampleKey";

        // when
        // we put set of string in a "classical way"
        prefser.getPreferences().edit().putStringSet(givenKey, strings).apply();

        // then
        // we read set of string in a "classical way"
        Set<String> readObject = prefser.getPreferences().getStringSet(givenKey, defaultStrings);
        assertThat(readObject).isEqualTo(strings);
        prefser.remove(givenKey);
    }

    @Test
    @SuppressWarnings("deprecation of assertThat for Java Collections")
    public void testPutSetOfDoubles() {
        // given
        prefser.clear();
        Set<Double> doubles = new HashSet<>(Arrays.asList(1.2, 2.3, 3.0));
        Set<Double> defaultDoubles = new HashSet<>(Arrays.asList(1.0, 1.0, 1.0));
        String givenKey = "sampleKey";

        // when
        prefser.put(givenKey, doubles);

        // then
        Set<Double> readObject = prefser.get(givenKey, Set.class, defaultDoubles);
        assertThat(readObject).isEqualTo(doubles);
        prefser.remove(givenKey);
    }

    @Test
    public void testGetDefaultBoolean() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        boolean defaultValue = true;

        // when
        boolean readValue = prefser.get(keyWhichDoesNotExist, Boolean.class, defaultValue);

        // then
        assertThat(readValue).isEqualTo(defaultValue);
    }

    @Test
    public void testGetDefaultFloat() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        float defaultValue = 42f;

        // when
        float readValue = prefser.get(keyWhichDoesNotExist, Float.class, defaultValue);

        // then
        assertThat(readValue).isEqualTo(defaultValue);
    }

    @Test
    public void testGetDefaultInteger() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        int defaultValue = 43;

        // when
        int readValue = prefser.get(keyWhichDoesNotExist, Integer.class, defaultValue);

        // then
        assertThat(readValue).isEqualTo(defaultValue);
    }

    @Test
    public void testGetDefaultLong() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        long defaultValue = 44l;

        // when
        long readValue = prefser.get(keyWhichDoesNotExist, Long.class, defaultValue);

        // then
        assertThat(readValue).isEqualTo(defaultValue);
    }

    @Test
    public void testGetDefaultDouble() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        double defaultValue = 45.6;

        // when
        double readValue = prefser.get(keyWhichDoesNotExist, Double.class, defaultValue);

        // then
        assertThat(readValue).isEqualTo(defaultValue);
    }

    @Test
    public void testGetDefaultString() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        String defaultValue = "default string value";

        // when
        String readValue = prefser.get(keyWhichDoesNotExist, String.class, defaultValue);

        // then
        assertThat(readValue).isEqualTo(defaultValue);
    }

    @Test
    public void testGetDefaultCustomObject() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        CustomClass defaultValue = new CustomClass(23, "string in default object");

        // when
        CustomClass readValue = prefser.get(keyWhichDoesNotExist, CustomClass.class, defaultValue);

        // then
        assertThat(readValue).isEqualTo(defaultValue);
    }

    @Test
    public void testGetDefaultListOfBooleans() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        List<Boolean> defaultValue = new ArrayList<>(Arrays.asList(true, false, true));

        // when
        List<Boolean> readValue = prefser.get(keyWhichDoesNotExist, List.class, defaultValue);

        // then
        assertThat(readValue).isEqualTo(defaultValue);
    }

    @Test
    public void testGetDefaultListOfFloats() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        List<Float> defaultValue = new ArrayList<>(Arrays.asList(1f, 2f, 3f));

        // when
        List<Float> readValue = prefser.get(keyWhichDoesNotExist, List.class, defaultValue);

        // then
        assertThat(readValue).isEqualTo(defaultValue);
    }

    @Test
    public void testGetDefaultListOfIntegers() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        List<Integer> defaultValue = new ArrayList<>(Arrays.asList(1, 2, 3));

        // when
        List<Integer> readValue = prefser.get(keyWhichDoesNotExist, List.class, defaultValue);

        // then
        assertThat(readValue).isEqualTo(defaultValue);
    }

    @Test
    public void testGetDefaultListOfLongs() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        List<Long> defaultValue = new ArrayList<>(Arrays.asList(1l, 2l, 3l));

        // when
        List<Long> readValue = prefser.get(keyWhichDoesNotExist, List.class, defaultValue);

        // then
        assertThat(readValue).isEqualTo(defaultValue);
    }

    @Test
    public void testGetDefaultListOfDoubles() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        List<Double> defaultValue = new ArrayList<>(Arrays.asList(1.2, 2.3, 3.4));

        // when
        List<Double> readValue = prefser.get(keyWhichDoesNotExist, List.class, defaultValue);

        // then
        assertThat(readValue).isEqualTo(defaultValue);
    }

    @Test
    public void testGetDefaultListOfStrings() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        List<String> defaultValue = new ArrayList<>(Arrays.asList("one", "two", "three"));

        // when
        List<String> readValue = prefser.get(keyWhichDoesNotExist, List.class, defaultValue);

        // then
        assertThat(readValue).isEqualTo(defaultValue);
    }

    @Test
    public void testGetDefaultArrayOfBooleans() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        Boolean[] defaultValue = new Boolean[]{true, false, true};

        // when
        Boolean[] readValue = prefser.get(keyWhichDoesNotExist, Boolean[].class, defaultValue);

        // then
        assertThat(readValue).isEqualTo(defaultValue);
    }

    @Test
    public void testGetDefaultArrayOfFloats() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        Float[] defaultValue = new Float[]{1f, 2f, 3f};

        // when
        Float[] readValue = prefser.get(keyWhichDoesNotExist, Float[].class, defaultValue);

        // then
        assertThat(readValue).isEqualTo(defaultValue);
    }

    @Test
    public void testGetDefaultArrayOfIntegers() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        Integer[] defaultValue = new Integer[]{2, 3, 4};

        // when
        Integer[] readValue = prefser.get(keyWhichDoesNotExist, Integer[].class, defaultValue);

        // then
        assertThat(readValue).isEqualTo(defaultValue);
    }

    @Test
    public void testGetDefaultArrayOfLongs() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        Long[] defaultValue = new Long[]{3l, 4l, 5l};

        // when
        Long[] readValue = prefser.get(keyWhichDoesNotExist, Long[].class, defaultValue);

        // then
        assertThat(readValue).isEqualTo(defaultValue);
    }

    @Test
    public void testGetDefaultArrayOfDoubles() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        Double[] defaultValue = new Double[]{1.2, 3.0, 4.5};

        // when
        Double[] readValue = prefser.get(keyWhichDoesNotExist, Double[].class, defaultValue);

        // then
        assertThat(readValue).isEqualTo(defaultValue);
    }

    @Test
    public void testGetDefaultArrayOfStrings() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        String[] defaultValue = new String[]{"first", "next", "another one"};

        // when
        String[] readValue = prefser.get(keyWhichDoesNotExist, String[].class, defaultValue);

        // then
        assertThat(readValue).isEqualTo(defaultValue);
    }

    @Test
    public void testGetDefaultArrayOfCustomObjects() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        CustomClass[] defaultValue = new CustomClass[]{
                new CustomClass(1, "Hey"),
                new CustomClass(2, "Dude"),
                new CustomClass(3, "Don't make it bad")
        };

        // when
        CustomClass[] readValue = prefser.get(keyWhichDoesNotExist, CustomClass[].class, defaultValue);

        // then
        assertThat(readValue).isEqualTo(defaultValue);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShouldThrownAnExceptionWhenPreferencesAreNull() {
        // given
        SharedPreferences sharedPreferences = null;

        // when
        new Prefser(sharedPreferences);

        // then
        // throw an exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShouldThrowAnExceptionWhenKeyForGetIsNull() {
        // given
        String key = null;
        Class<String> classOfT = String.class;

        // when
        prefser.get(key, classOfT, "");

        // then
        // throw an exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShouldThrowAnExceptionWhenClassOfTForGetIsNull() {
        // given
        String key = "someKey";
        Class classOfT = null;
        prefser.put(key, "someValue");

        // when
        prefser.get(key, classOfT, "");

        // then
        // throw an exception
    }

    @Test(expected = IllegalArgumentException.class)
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

    @Test(expected = IllegalArgumentException.class)
    public void testShouldThrowAnExceptionWhenClassOfTForGetWithDefaultValueIsNull() {
        // given
        String key = "someKey";
        Class<String> classOfT = null;
        String defaultValue = "some default value";

        // when
        prefser.get(key, classOfT, defaultValue);

        // then
        // throw an exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromSharedPreferencesShouldThrowAnExceptionWhenPreferencesAreNull() {
        // given
        SharedPreferences sharedPreferences = null;

        // when
        prefser.observe(sharedPreferences);

        // then
        // throw an exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPutShouldThrowAnExceptionWhenKeyIsNullForPut() {
        // given
        String key = null;
        String value = "someValue";

        // when
        prefser.put(key, value);

        // then
        // throw an exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPutShouldThrowAnExceptionWhenValueIsNullForPut() {
        // given
        String key = "someKey";
        String value = null;

        // when
        prefser.put(key, value);

        // then
        // throw an exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPutShouldThrowAnExceptionWhenKeyIsNullForRemove() {
        // given
        String key = null;

        // when
        prefser.remove(key);

        // then
        // throw an exception
    }

    @Test
    public void testObserveBoolean() {
        // given
        prefser.clear();
        String givenKey = "someKey";
        boolean givenValue = true;
        Boolean defaultValue = false;

        // when
        RecordingObserver<Boolean> observer = new RecordingObserver<>();
        prefser.observe(givenKey, Boolean.class, defaultValue).subscribe(observer);
        prefser.put(givenKey, givenValue);

        // then
        assertThat(observer.takeNext()).isTrue();
        observer.assertNoMoreEvents();
    }

    @Test
    public void testObserveBooleanPrimitive() {
        // given
        prefser.clear();
        String givenKey = "someKey";
        boolean givenValue = true;
        boolean defaultValue = false;

        // when
        RecordingObserver<Boolean> observer = new RecordingObserver<>();
        prefser.observe(givenKey, Boolean.class, defaultValue).subscribe(observer);
        prefser.put(givenKey, givenValue);

        // then
        assertThat(observer.takeNext()).isTrue();
        observer.assertNoMoreEvents();
    }

    @Test
    public void testObserveFloat() {
        // given
        prefser.clear();
        String givenKey = "someKey";
        Float givenValue = 3.0f;
        Float defaultValue = 1.0f;

        // when
        RecordingObserver<Float> observer = new RecordingObserver<>();
        prefser.observe(givenKey, Float.class, defaultValue).subscribe(observer);
        prefser.put(givenKey, givenValue);

        // then
        assertThat(observer.takeNext()).isEqualTo(givenValue);
        observer.assertNoMoreEvents();
    }

    @Test
    public void testObserveFloatPrimitive() {
        // given
        prefser.clear();
        String givenKey = "someKey";
        float givenValue = 3.0f;
        float defaultValue = 1.0f;

        // when
        RecordingObserver<Float> observer = new RecordingObserver<>();
        prefser.observe(givenKey, Float.class, defaultValue).subscribe(observer);
        prefser.put(givenKey, givenValue);

        // then
        assertThat(observer.takeNext()).isEqualTo(givenValue);
        observer.assertNoMoreEvents();
    }

    @Test
    public void testObserveInteger() {
        // given
        prefser.clear();
        String givenKey = "someKey";
        Integer givenValue = 5;
        Integer defaultValue = 8;

        // when
        RecordingObserver<Integer> observer = new RecordingObserver<>();
        prefser.observe(givenKey, Integer.class, defaultValue).subscribe(observer);
        prefser.put(givenKey, givenValue);

        // then
        assertThat(observer.takeNext()).isEqualTo(givenValue);
        observer.assertNoMoreEvents();
    }

    @Test
    public void testObserveIntegerPrimitive() {
        // given
        prefser.clear();
        String givenKey = "someKey";
        int givenValue = 5;
        int defaultValue = 8;

        // when
        RecordingObserver<Integer> observer = new RecordingObserver<>();
        prefser.observe(givenKey, Integer.class, defaultValue).subscribe(observer);
        prefser.put(givenKey, givenValue);

        // then
        assertThat(observer.takeNext()).isEqualTo(givenValue);
        observer.assertNoMoreEvents();
    }

    @Test
    public void testObserveLong() {
        // given
        prefser.clear();
        String givenKey = "someKey";
        Long givenValue = 12l;
        Long defaultValue = 16l;

        // when
        RecordingObserver<Long> observer = new RecordingObserver<>();
        prefser.observe(givenKey, Long.class, defaultValue).subscribe(observer);
        prefser.put(givenKey, givenValue);

        // then
        assertThat(observer.takeNext()).isEqualTo(givenValue);
        observer.assertNoMoreEvents();
    }

    @Test
    public void testObserveLongPrimitive() {
        // given
        prefser.clear();
        String givenKey = "someKey";
        long givenValue = 12l;
        long defaultValue = 16l;

        // when
        RecordingObserver<Long> observer = new RecordingObserver<>();
        prefser.observe(givenKey, Long.class, defaultValue).subscribe(observer);
        prefser.put(givenKey, givenValue);

        // then
        assertThat(observer.takeNext()).isEqualTo(givenValue);
        observer.assertNoMoreEvents();
    }

    @Test
    public void testObserveDouble() {
        // given
        prefser.clear();
        String givenKey = "someKey";
        Double givenValue = 12.4;
        Double defaultValue = 19.9;

        // when
        RecordingObserver<Double> observer = new RecordingObserver<>();
        prefser.observe(givenKey, Double.class, defaultValue).subscribe(observer);
        prefser.put(givenKey, givenValue);

        // then
        assertThat(observer.takeNext()).isEqualTo(givenValue);
        observer.assertNoMoreEvents();
    }

    @Test
    public void testObserveDoublePrimitive() {
        // given
        prefser.clear();
        String givenKey = "someKey";
        double givenValue = 12.4;
        double defaultValue = 19.9;

        // when
        RecordingObserver<Double> observer = new RecordingObserver<>();
        prefser.observe(givenKey, Double.class, defaultValue).subscribe(observer);
        prefser.put(givenKey, givenValue);

        // then
        assertThat(observer.takeNext()).isEqualTo(givenValue);
        observer.assertNoMoreEvents();
    }

    @Test
    public void testObserveString() {
        // given
        prefser.clear();
        String givenKey = "someKey";
        String givenValue = "hi, I'm sample string";
        String defaultValue = "";

        // when
        RecordingObserver<String> observer = new RecordingObserver<>();
        prefser.observe(givenKey, String.class, defaultValue).subscribe(observer);
        prefser.put(givenKey, givenValue);

        // then
        assertThat(observer.takeNext()).isEqualTo(givenValue);
        observer.assertNoMoreEvents();
    }

    @Test
    public void testObserveCustomObject() {
        // given
        prefser.clear();
        String givenKey = "someKey";
        CustomClass customClass = new CustomClass(56, "someValue");
        CustomClass defaultClass = new CustomClass(1, "");

        // when
        RecordingObserver<CustomClass> observer = new RecordingObserver<>();
        prefser.observe(givenKey, CustomClass.class, defaultClass).subscribe(observer);
        prefser.put(givenKey, customClass);

        // then
        assertThat(observer.takeNext()).isEqualTo(customClass);
        observer.assertNoMoreEvents();
    }

    @Test
    public void testObserveListOfBooleans() {
        // given
        prefser.clear();
        String givenKey = "someKey";
        List<Boolean> booleans = Arrays.asList(true, false, true);
        List<Boolean> defaultBooleans = Arrays.asList(false, false, false);

        // when
        RecordingObserver<List<Boolean>> observer = new RecordingObserver<>();
        prefser.observe(givenKey, List.class, defaultBooleans).subscribe(observer);
        prefser.put(givenKey, booleans);

        // then
        assertThat(observer.takeNext()).isEqualTo(booleans);
        observer.assertNoMoreEvents();
    }

    @Test
    public void testObserveListOfDoubles() {
        // given
        prefser.clear();
        String givenKey = "someKey";
        List<Double> doubles = Arrays.asList(1.2, 34.65, 3.6);
        List<Double> defaultDoubles = Arrays.asList(1.0, 1.0, 1.0);

        // when
        RecordingObserver<List<Double>> observer = new RecordingObserver<>();
        prefser.observe(givenKey, List.class, defaultDoubles).subscribe(observer);
        prefser.put(givenKey, doubles);

        // then
        assertThat(observer.takeNext()).isEqualTo(doubles);
        observer.assertNoMoreEvents();
    }

    @Test
    public void testObserveListOfStrings() {
        // given
        prefser.clear();
        String givenKey = "someKey";
        List<String> strings = Arrays.asList("one", "two", "three");
        List<String> defaultStrings = Arrays.asList("some", "default", "strings");

        // when
        RecordingObserver<List<String>> observer = new RecordingObserver<>();
        prefser.observe(givenKey, List.class, defaultStrings).subscribe(observer);
        prefser.put(givenKey, strings);

        // then
        assertThat(observer.takeNext()).isEqualTo(strings);
        observer.assertNoMoreEvents();
    }

    @Test
    public void testObserveArrayOfBooleans() {
        // given
        prefser.clear();
        String givenKey = "someKey";
        Boolean[] booleans = {true, false, true};
        Boolean[] defaultBooleans = {false, false, false};

        // when
        RecordingObserver<Boolean[]> observer = new RecordingObserver<>();
        prefser.observe(givenKey, Boolean[].class, defaultBooleans).subscribe(observer);
        prefser.put(givenKey, booleans);

        // then
        assertThat(observer.takeNext()).isEqualTo(booleans);
        observer.assertNoMoreEvents();
    }


    @Test
    public void testObserveArrayOfBooleansPrimitive() {
        // given
        prefser.clear();
        String givenKey = "someKey";
        boolean[] booleans = {true, false, true};
        boolean[] defaultBooleans = {false, false, false};

        // when
        RecordingObserver<boolean[]> observer = new RecordingObserver<>();
        prefser.observe(givenKey, boolean[].class, defaultBooleans).subscribe(observer);
        prefser.put(givenKey, booleans);

        // then
        assertThat(observer.takeNext()).isEqualTo(booleans);
        observer.assertNoMoreEvents();
    }

    @Test
    public void testObserveArrayOfFloats() {
        // given
        prefser.clear();
        String givenKey = "someKey";
        Float[] floats = {1.1f, 4.5f, 6.8f};
        Float[] defaultFloats = {1.0f, 1.0f, 1.0f};

        // when
        RecordingObserver<Float[]> observer = new RecordingObserver<>();
        prefser.observe(givenKey, Float[].class, defaultFloats).subscribe(observer);
        prefser.put(givenKey, floats);

        // then
        assertThat(observer.takeNext()).isEqualTo(floats);
        observer.assertNoMoreEvents();
    }

    @Test
    public void testObserveArrayOfFloatsPrimitive() {
        // given
        prefser.clear();
        String givenKey = "someKey";
        float[] floats = {1.1f, 4.5f, 6.8f};
        float[] defaultFloats = {1.0f, 1.0f, 1.0f};

        // when
        RecordingObserver<float[]> observer = new RecordingObserver<>();
        prefser.observe(givenKey, float[].class, defaultFloats).subscribe(observer);
        prefser.put(givenKey, floats);

        // then
        assertThat(observer.takeNext()).isEqualTo(floats, 0.1f);
        observer.assertNoMoreEvents();
    }

    @Test
    public void testObserveArrayOfInts() {
        // given
        prefser.clear();
        String givenKey = "someKey";
        Integer[] ints = {4, 5, 6};
        Integer[] defaultInts = {1, 1, 1};

        // when
        RecordingObserver<Integer[]> observer = new RecordingObserver<>();
        prefser.observe(givenKey, Integer[].class, defaultInts).subscribe(observer);
        prefser.put(givenKey, ints);

        // then
        assertThat(observer.takeNext()).isEqualTo(ints);
        observer.assertNoMoreEvents();
    }

    @Test
    public void testObserveArrayOfIntsPrimitive() {
        // given
        prefser.clear();
        String givenKey = "someKey";
        int[] ints = {4, 5, 6};
        int[] defaultInts = {1, 1, 1};

        // when
        RecordingObserver<int[]> observer = new RecordingObserver<>();
        prefser.observe(givenKey, int[].class, defaultInts).subscribe(observer);
        prefser.put(givenKey, ints);

        // then
        assertThat(observer.takeNext()).isEqualTo(ints);
        observer.assertNoMoreEvents();
    }

    @Test
    public void testObserveArrayOfDoubles() {
        // given
        prefser.clear();
        String givenKey = "someKey";
        Double[] doubles = {4.5, 5.6, 6.2};
        Double[] defaultDoubles = {1.1, 1.1, 1.1};

        // when
        RecordingObserver<Double[]> observer = new RecordingObserver<>();
        prefser.observe(givenKey, Double[].class, defaultDoubles).subscribe(observer);
        prefser.put(givenKey, doubles);

        // then
        assertThat(observer.takeNext()).isEqualTo(doubles);
        observer.assertNoMoreEvents();
    }

    @Test
    public void testObserveArrayOfDoublesPrimitive() {
        // given
        prefser.clear();
        String givenKey = "someKey";
        double[] doubles = {4.5, 5.6, 6.2};
        double[] defaultDoubles = {1.1, 1.1, 1.1};

        // when
        RecordingObserver<double[]> observer = new RecordingObserver<>();
        prefser.observe(givenKey, double[].class, defaultDoubles).subscribe(observer);
        prefser.put(givenKey, doubles);

        // then
        assertThat(observer.takeNext()).isEqualTo(doubles, 0.1);
        observer.assertNoMoreEvents();
    }

    @Test
    public void testObserveArrayOfStrings() {
        // given
        prefser.clear();
        String givenKey = "someKey";
        String[] strings = {"one", "given", "array"};
        String[] defaultStrings = {"another", "default", "strings"};

        // when
        RecordingObserver<String[]> observer = new RecordingObserver<>();
        prefser.observe(givenKey, String[].class, defaultStrings).subscribe(observer);
        prefser.put(givenKey, strings);

        // then
        assertThat(observer.takeNext()).isEqualTo(strings);
        observer.assertNoMoreEvents();
    }

    @Test
    public void testObserveArrayOfCustomObjects() {
        // given
        prefser.clear();
        String givenKey = "someKey";
        CustomClass[] customClasses = {
                new CustomClass(1, "one"),
                new CustomClass(2, "two"),
                new CustomClass(3, "three")
        };

        CustomClass[] defaultCustomClasses = {
                new CustomClass(0, "zero"),
                new CustomClass(0, "zero"),
                new CustomClass(0, "zero")
        };

        // when
        RecordingObserver<CustomClass[]> observer = new RecordingObserver<>();
        prefser.observe(givenKey, CustomClass[].class, defaultCustomClasses).subscribe(observer);
        prefser.put(givenKey, customClasses);

        // then
        assertThat(observer.takeNext()).isEqualTo(customClasses);
        observer.assertNoMoreEvents();
    }

    @Test
    public void testObservePreferencesOnPut() {
        // given
        prefser.clear();
        String givenKey = "someKey";
        String givenValue = "someValue";

        // when
        RecordingObserver<String> observer = new RecordingObserver<>();
        prefser.observeDefaultPreferences().subscribe(observer);
        prefser.put(givenKey, givenValue);

        // then
        assertThat(observer.takeNext()).isEqualTo(givenKey);
        observer.assertNoMoreEvents();
    }

    @Test
    public void testObservePreferencesOnUpdate() {
        // given
        prefser.clear();
        String givenKey = "someKey";
        String givenValue = "someValue";
        String anotherGivenValue = "anotherGivenValue";
        prefser.put(givenKey, givenValue);

        // when
        RecordingObserver<String> observer = new RecordingObserver<>();
        prefser.observeDefaultPreferences().subscribe(observer);
        prefser.put(givenKey, anotherGivenValue);

        // then
        assertThat(observer.takeNext()).isEqualTo(givenKey);
        observer.assertNoMoreEvents();
    }

    @Test
    public void testObservePreferencesOnRemove() {
        // given
        prefser.clear();
        String givenKey = "someKey";
        String givenValue = "someValue";
        prefser.put(givenKey, givenValue);

        // when
        RecordingObserver<String> observer = new RecordingObserver<>();
        prefser.observeDefaultPreferences().subscribe(observer);
        prefser.remove(givenKey);

        // then
        assertThat(observer.takeNext()).isEqualTo(givenKey);
        observer.assertNoMoreEvents();
    }
}