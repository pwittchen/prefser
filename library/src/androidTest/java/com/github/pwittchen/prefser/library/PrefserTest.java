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

import android.content.SharedPreferences;
import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PrefserTest extends AndroidTestCase {

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
    }

    @Override
    public void setUp() {
        prefser = new Prefser(getContext());
    }

    @Override
    public void tearDown() {
        prefser.clear();
    }

    public void testContains() throws Exception {
        // given
        String givenValue = "sample value";
        String givenKey = "sampleKey";

        // when
        prefser.put(givenKey, givenValue);

        // then
        assertTrue(prefser.contains(givenKey));
        prefser.remove(givenKey);
        assertFalse(prefser.contains(givenKey));
    }

    public void testSize() throws Exception {
        // given
        prefser.clear();

        // when
        prefser.put("key1", 1);
        prefser.put("key2", 2);
        prefser.put("key3", 3);

        // then
        assertEquals(prefser.size(), 3);
        prefser.remove("key1");
        assertEquals(prefser.size(), 2);
        prefser.clear();
        assertEquals(prefser.size(), 0);
    }

    public void testRemove() throws Exception {
        // given
        prefser.put("key1", 1);

        // when
        assertTrue(prefser.contains("key1"));
        prefser.remove("key1");

        // then
        assertFalse(prefser.contains("key1"));

    }

    public void testClear() throws Exception {
        // given
        prefser.put("key1", 1);
        prefser.put("key2", 2);
        prefser.put("key3", 3);

        // when
        assertEquals(prefser.size(), 3);
        prefser.clear();

        // then
        assertEquals(prefser.size(), 0);
    }

    public void testPutBoolean() throws Exception {
        // given
        String givenKey = "sampleKey";
        Boolean givenValue = true;

        // when
        prefser.put(givenKey, givenValue);

        // then
        Boolean readValue = prefser.get(givenKey, Boolean.class);
        assertEquals(readValue, givenValue);
        prefser.remove(givenKey);
    }

    public void testPutBooleanPrimitive() throws Exception {
        // given
        String givenKey = "sampleKey";
        boolean givenValue = true;

        // when
        prefser.put(givenKey, givenValue);

        // then
        boolean readValue = prefser.get(givenKey, boolean.class);
        assertEquals(readValue, givenValue);
        prefser.remove(givenKey);
    }

    public void testPutFloat() throws Exception {
        // given
        String givenKey = "sampleKey";
        Float givenValue = 41f;

        // when
        prefser.put(givenKey, givenValue);

        // then
        Float readValue = prefser.get(givenKey, Float.class);
        assertEquals(readValue, givenValue);
        prefser.remove(givenKey);
    }

    public void testPutFloatPrimitive() throws Exception {
        // given
        String givenKey = "sampleKey";
        float givenValue = 41f;

        // when
        prefser.put(givenKey, givenValue);

        // then
        float readValue = prefser.get(givenKey, float.class);
        assertEquals(readValue, givenValue);
        prefser.remove(givenKey);
    }

    public void testPutInteger() throws Exception {
        // given
        String givenKey = "sampleKey";
        Integer givenValue = 42;

        // when
        prefser.put(givenKey, givenValue);

        // then
        Integer readValue = prefser.get(givenKey, Integer.class);
        assertEquals(readValue, givenValue);
        prefser.remove(givenKey);
    }

    public void testPutIntegerPrimitive() throws Exception {
        // given
        String givenKey = "sampleKey";
        int givenValue = 42;

        // when
        prefser.put(givenKey, givenValue);

        // then
        int readValue = prefser.get(givenKey, int.class);
        assertEquals(readValue, givenValue);
        prefser.remove(givenKey);
    }

    public void testPutLong() throws Exception {
        // given
        String givenKey = "sampleKey";
        Long givenValue = 43l;

        // when
        prefser.put(givenKey, givenValue);

        // then
        Long readValue = prefser.get(givenKey, Long.class);
        assertEquals(readValue, givenValue);
        prefser.remove(givenKey);
    }

    public void testPutLongPrimitive() throws Exception {
        // given
        String givenKey = "sampleKey";
        long givenValue = 43l;

        // when
        prefser.put(givenKey, givenValue);

        // then
        long readValue = prefser.get(givenKey, long.class);
        assertEquals(readValue, givenValue);
        prefser.remove(givenKey);
    }

    public void testPutDouble() throws Exception {
        // given
        String givenKey = "sampleKey";
        Double givenValue = 44.5;

        // when
        prefser.put(givenKey, givenValue);

        // then
        Double readValue = prefser.get(givenKey, Double.class);
        assertEquals(readValue, givenValue);
        prefser.remove(givenKey);
    }

    public void testPutDoublePrimitive() throws Exception {
        // given
        String givenKey = "sampleKey";
        double givenValue = 44.5;

        // when
        prefser.put(givenKey, givenValue);

        // then
        double readValue = prefser.get(givenKey, double.class);
        assertEquals(readValue, givenValue);
        prefser.remove(givenKey);
    }

    public void testPutString() throws Exception {
        // given
        String givenKey = "sampleKey";
        String givenValue = "sampleValueExplicit";

        // when
        prefser.put(givenKey, givenValue);

        // then
        String readValue = prefser.get(givenKey, String.class);
        assertEquals(readValue, givenValue);
        prefser.remove(givenKey);
    }

    public void testPutCustomObject() throws Exception {
        // given
        String givenKey = "sampleKey";
        CustomClass givenObject = new CustomClass(23, "someText");

        // when
        prefser.put(givenKey, givenObject);

        // then
        CustomClass readObject = prefser.get(givenKey, CustomClass.class);
        assertTrue(readObject.equals(givenObject));
        prefser.remove(givenKey);
    }

    public void testPutListOfBooleans() throws Exception {
        // given
        String givenKey = "sampleKey";
        List<Boolean> booleans = new ArrayList<>(Arrays.asList(true, false, true));

        // when
        prefser.put(givenKey, booleans);

        // then
        List<Boolean> readObject = prefser.get(givenKey, List.class);
        assertEquals(readObject, booleans);
        prefser.remove(givenKey);
    }

    public void testPutListOfFloats() throws Exception {
        // given
        String givenKey = "sampleKey";
        List<Float> floats = new ArrayList<>(Arrays.asList(4f, 5f, 6f));

        // when
        prefser.put(givenKey, floats);

        // then
        // if we read list of different type of number than double,
        // we should declare List<Number> due to implementation of Gson
        List<Number> readObject = prefser.get(givenKey, List.class);

        Double expectedNumberFirst = readObject.get(0).doubleValue();
        Double expectedNumberSecond = readObject.get(1).doubleValue();
        Double expectedNumberThird = readObject.get(2).doubleValue();

        assertEquals(expectedNumberFirst.floatValue(), floats.get(0).floatValue());
        assertEquals(expectedNumberSecond.floatValue(), floats.get(1).floatValue());
        assertEquals(expectedNumberThird.floatValue(), floats.get(2).floatValue());


        prefser.remove(givenKey);
    }

    public void testPutListOfInts() throws Exception {
        // given
        String givenKey = "sampleKey";
        List<Integer> ints = new ArrayList<>(Arrays.asList(4, 5, 6));

        // when
        prefser.put(givenKey, ints);

        // then
        // if we read list of different type of number than double,
        // we should declare List<Number> due to implementation of Gson
        List<Number> readObject = prefser.get(givenKey, List.class);

        Double expectedNumberFirst = readObject.get(0).doubleValue();
        Double expectedNumberSecond = readObject.get(1).doubleValue();
        Double expectedNumberThird = readObject.get(2).doubleValue();

        assertEquals(expectedNumberFirst.intValue(), ints.get(0).intValue());
        assertEquals(expectedNumberSecond.intValue(), ints.get(1).intValue());
        assertEquals(expectedNumberThird.intValue(), ints.get(2).intValue());

        prefser.remove(givenKey);
    }

    public void testPutListOfLongs() throws Exception {
        // given
        String givenKey = "sampleKey";
        List<Long> ints = new ArrayList<>(Arrays.asList(4l, 5l, 6l));

        // when
        prefser.put(givenKey, ints);

        // then
        // if we read list of different type of number than double,
        // we should declare List<Number> due to implementation of Gson
        List<Number> readObject = prefser.get(givenKey, List.class);

        Double expectedNumberFirst = readObject.get(0).doubleValue();
        Double expectedNumberSecond = readObject.get(1).doubleValue();
        Double expectedNumberThird = readObject.get(2).doubleValue();

        assertEquals(expectedNumberFirst.longValue(), ints.get(0).longValue());
        assertEquals(expectedNumberSecond.longValue(), ints.get(1).longValue());
        assertEquals(expectedNumberThird.longValue(), ints.get(2).longValue());

        prefser.remove(givenKey);
    }

    public void testPutListOfDoubles() throws Exception {
        // given
        String givenKey = "sampleKey";
        List<Double> doubles = new ArrayList<>(Arrays.asList(4.0, 5.1, 6.2));

        // when
        prefser.put(givenKey, doubles);

        // then
        List<Double> readObject = prefser.get(givenKey, List.class);

        assertEquals(readObject.get(0), doubles.get(0));
        assertEquals(readObject.get(1), doubles.get(1));
        assertEquals(readObject.get(2), doubles.get(2));

        prefser.remove(givenKey);
    }

    public void testPutListOfStrings() throws Exception {
        // given
        String givenKey = "sampleKey";
        List<String> strings = new ArrayList<>(Arrays.asList("one", "two", "three"));

        // when
        prefser.put(givenKey, strings);

        // then
        List<String> readObject = prefser.get(givenKey, List.class);
        assertEquals(readObject.get(0), strings.get(0));
        assertEquals(readObject.get(1), strings.get(1));
        assertEquals(readObject.get(2), strings.get(2));

        prefser.remove(givenKey);
    }

    public void testPutArrayOfBooleans() {
        // given
        String givenKey = "sampleKey";
        Boolean[] array = new Boolean[]{true, false, true};

        // when
        prefser.put(givenKey, array);

        // then
        Boolean[] readObject = prefser.get(givenKey, Boolean[].class);
        assertEquals(readObject[0], array[0]);
        assertEquals(readObject[1], array[1]);
        assertEquals(readObject[2], array[2]);

        prefser.remove(givenKey);
    }

    public void testPutArrayOfFloats() {
        // given
        String givenKey = "sampleKey";
        Float[] array = new Float[]{1f, 2f, 3f};

        // when
        prefser.put(givenKey, array);

        // then
        Float[] readObject = prefser.get(givenKey, Float[].class);
        assertEquals(readObject[0], array[0]);
        assertEquals(readObject[1], array[1]);
        assertEquals(readObject[2], array[2]);

        prefser.remove(givenKey);
    }

    public void testPutArrayOfInts() {
        // given
        String givenKey = "sampleKey";
        Integer[] array = new Integer[]{1, 2, 3};

        // when
        prefser.put(givenKey, array);

        // then
        Integer[] readObject = prefser.get(givenKey, Integer[].class);
        assertEquals(readObject[0], array[0]);
        assertEquals(readObject[1], array[1]);
        assertEquals(readObject[2], array[2]);

        prefser.remove(givenKey);
    }

    public void testPutArrayOfLongs() {
        // given
        String givenKey = "sampleKey";
        Long[] array = new Long[]{1l, 2l, 3l};

        // when
        prefser.put(givenKey, array);

        // then
        Long[] readObject = prefser.get(givenKey, Long[].class);
        assertEquals(readObject[0], array[0]);
        assertEquals(readObject[1], array[1]);
        assertEquals(readObject[2], array[2]);

        prefser.remove(givenKey);
    }

    public void testPutArrayOfDoubles() {
        // given
        String givenKey = "sampleKey";
        Double[] array = new Double[]{1.0, 2.3, 4.5};

        // when
        prefser.put(givenKey, array);

        // then
        Double[] readObject = prefser.get(givenKey, Double[].class);
        assertEquals(readObject[0], array[0]);
        assertEquals(readObject[1], array[1]);
        assertEquals(readObject[2], array[2]);

        prefser.remove(givenKey);
    }

    public void testPutArrayOfStrings() {
        // given
        String givenKey = "sampleKey";
        String[] array = new String[]{"one", "two", "three"};

        // when
        prefser.put(givenKey, array);

        // then
        String[] readObject = prefser.get(givenKey, String[].class);
        assertEquals(readObject[0], array[0]);
        assertEquals(readObject[1], array[1]);
        assertEquals(readObject[2], array[2]);

        prefser.remove(givenKey);
    }

    public void testPutArrayOfCustomObjects() {
        // given
        String givenKey = "sampleKey";
        CustomClass[] customClassesArray = new CustomClass[]{
                new CustomClass(1, "one"),
                new CustomClass(2, "two"),
                new CustomClass(3, "three")
        };

        // when
        prefser.put(givenKey, customClassesArray);

        // then
        CustomClass[] readObject = prefser.get(givenKey, CustomClass[].class);
        assertEquals(readObject[0], customClassesArray[0]);
        assertEquals(readObject[1], customClassesArray[1]);
        assertEquals(readObject[2], customClassesArray[2]);

        prefser.remove(givenKey);
    }

    public void testPutSetOfStrings() {
        // given
        Set<String> strings = new HashSet<>(Arrays.asList("one", "two", "three"));
        String givenKey = "sampleKey";

        // when
        prefser.put(givenKey, strings);

        // then
        Set<String> readObject = prefser.get(givenKey, Set.class);
        assertEquals(readObject, strings);
        prefser.remove(givenKey);
    }

    public void testPutSetOfDoubles() {
        // given
        Set<Double> strings = new HashSet<>(Arrays.asList(1.2, 2.3, 3.0));
        String givenKey = "sampleKey";

        // when
        prefser.put(givenKey, strings);

        // then
        Set<Double> readObject = prefser.get(givenKey, Set.class);
        assertEquals(readObject, strings);
        prefser.remove(givenKey);
    }

    public void testShouldThrowAnExceptionWhenGettingValueForNotDefinedKeyAndDefaultValue() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        String exceptionMessageFormat = "Value with key %s could not be found";

        try {
            // when
            boolean readValue = prefser.get(keyWhichDoesNotExist, Boolean.class);
        } catch (Exception e) {
            // then
            assertEquals(e.getMessage(), String.format(exceptionMessageFormat, keyWhichDoesNotExist));
        }
    }

    public void testGetDefaultBoolean() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        boolean defaultValue = true;

        // when
        boolean readValue = prefser.get(keyWhichDoesNotExist, Boolean.class, defaultValue);

        // then
        assertEquals(readValue, defaultValue);
    }

    public void testGetDefaultFloat() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        float defaultValue = 42f;

        // when
        float readValue = prefser.get(keyWhichDoesNotExist, Float.class, defaultValue);

        // then
        assertEquals(readValue, defaultValue);
    }

    public void testGetDefaultInteger() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        int defaultValue = 43;

        // when
        int readValue = prefser.get(keyWhichDoesNotExist, Integer.class, defaultValue);

        // then
        assertEquals(readValue, defaultValue);
    }

    public void testGetDefaultLong() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        long defaultValue = 44l;

        // when
        long readValue = prefser.get(keyWhichDoesNotExist, Long.class, defaultValue);

        // then
        assertEquals(readValue, defaultValue);
    }

    public void testGetDefaultDouble() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        double defaultValue = 45.6;

        // when
        double readValue = prefser.get(keyWhichDoesNotExist, Double.class, defaultValue);

        // then
        assertEquals(readValue, defaultValue);
    }

    public void testGetDefaultString() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        String defaultValue = "default string value";

        // when
        String readValue = prefser.get(keyWhichDoesNotExist, String.class, defaultValue);

        // then
        assertEquals(readValue, defaultValue);
    }

    public void testGetDefaultCustomObject() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        CustomClass defaultValue = new CustomClass(23, "string in default object");

        // when
        CustomClass readValue = prefser.get(keyWhichDoesNotExist, CustomClass.class, defaultValue);

        // then
        assertEquals(readValue, defaultValue);
    }

    public void testGetDefaultListOfBooleans() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        List<Boolean> defaultValue = new ArrayList<>(Arrays.asList(true, false, true));

        // when
        List<Boolean> readValue = prefser.get(keyWhichDoesNotExist, List.class, defaultValue);

        // then
        assertEquals(readValue, defaultValue);
    }

    public void testGetDefaultListOfFloats() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        List<Float> defaultValue = new ArrayList<>(Arrays.asList(1f, 2f, 3f));

        // when
        List<Float> readValue = prefser.get(keyWhichDoesNotExist, List.class, defaultValue);

        // then
        assertEquals(readValue, defaultValue);
    }

    public void testGetDefaultListOfIntegers() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        List<Integer> defaultValue = new ArrayList<>(Arrays.asList(1, 2, 3));

        // when
        List<Integer> readValue = prefser.get(keyWhichDoesNotExist, List.class, defaultValue);

        // then
        assertEquals(readValue, defaultValue);
    }

    public void testGetDefaultListOfLongs() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        List<Long> defaultValue = new ArrayList<>(Arrays.asList(1l, 2l, 3l));

        // when
        List<Long> readValue = prefser.get(keyWhichDoesNotExist, List.class, defaultValue);

        // then
        assertEquals(readValue, defaultValue);
    }

    public void testGetDefaultListOfDoubles() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        List<Double> defaultValue = new ArrayList<>(Arrays.asList(1.2, 2.3, 3.4));

        // when
        List<Double> readValue = prefser.get(keyWhichDoesNotExist, List.class, defaultValue);

        // then
        assertEquals(readValue, defaultValue);
    }

    public void testGetDefaultListOfStrings() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        List<String> defaultValue = new ArrayList<>(Arrays.asList("one", "two", "three"));

        // when
        List<String> readValue = prefser.get(keyWhichDoesNotExist, List.class, defaultValue);

        // then
        assertEquals(readValue, defaultValue);
    }

    public void testGetDefaultArrayOfBooleans() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        Boolean[] defaultValue = new Boolean[]{true, false, true};

        // when
        Boolean[] readValue = prefser.get(keyWhichDoesNotExist, Boolean[].class, defaultValue);

        // then
        assertEquals(readValue, defaultValue);
    }

    public void testGetDefaultArrayOfFloats() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        Float[] defaultValue = new Float[]{1f, 2f, 3f};

        // when
        Float[] readValue = prefser.get(keyWhichDoesNotExist, Float[].class, defaultValue);

        // then
        assertEquals(readValue, defaultValue);
    }

    public void testGetDefaultArrayOfIntegers() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        Integer[] defaultValue = new Integer[]{2, 3, 4};

        // when
        Integer[] readValue = prefser.get(keyWhichDoesNotExist, Integer[].class, defaultValue);

        // then
        assertEquals(readValue, defaultValue);
    }

    public void testGetDefaultArrayOfLongs() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        Long[] defaultValue = new Long[]{3l, 4l, 5l};

        // when
        Long[] readValue = prefser.get(keyWhichDoesNotExist, Long[].class, defaultValue);

        // then
        assertEquals(readValue, defaultValue);
    }

    public void testGetDefaultArrayOfDoubles() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        Double[] defaultValue = new Double[]{1.2, 3.0, 4.5};

        // when
        Double[] readValue = prefser.get(keyWhichDoesNotExist, Double[].class, defaultValue);

        // then
        assertEquals(readValue, defaultValue);
    }

    public void testGetDefaultArrayOfStrings() {
        // given
        prefser.clear();
        String keyWhichDoesNotExist = "keyWhichDoesNotExist";
        String[] defaultValue = new String[]{"first", "next", "another one"};

        // when
        String[] readValue = prefser.get(keyWhichDoesNotExist, String[].class, defaultValue);

        // then
        assertEquals(readValue, defaultValue);
    }

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
        assertEquals(readValue, defaultValue);
    }

    public void testShouldThrownAnExceptionWhenPreferencesAreNull() {
        // given
        SharedPreferences sharedPreferences = null;

        try {
            // when
            new Prefser(sharedPreferences);
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("sharedPreferences == null", e.getMessage());
        }
    }

    public void testShouldThrowAnExceptionWhenKeyForGetIsNull() {
        // given
        String key = null;
        Class<String> classOfT = String.class;

        try {
            // when
            prefser.get(key, classOfT);
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("key == null", e.getMessage());
        }
    }

    public void testShouldThrowAnExceptionWhenClassOfTForGetIsNull() {
        // given
        String key = "someKey";
        Class classOfT = null;
        prefser.put(key, "someValue");

        try {
            // when
            prefser.get(key, classOfT);
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("classOfT == null", e.getMessage());
        } finally {
            prefser.remove(key);
        }
    }

    public void testShouldThrowAnExceptionWhenKeyForGetWithDefaultValueIsNull() {
        // given
        String key = null;
        Class<String> classOfT = String.class;
        String defaultValue = "some default value";

        try {
            // when
            prefser.get(key, classOfT, defaultValue);
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("key == null", e.getMessage());
        }
    }

    public void testShouldThrowAnExceptionWhenClassOfTForGetWithDefaultValueIsNull() {
        // given
        String key = "someKey";
        Class<String> classOfT = null;
        String defaultValue = "some default value";
        prefser.put(key, "someValue");

        try {
            // when
            prefser.get(key, classOfT, defaultValue);
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("classOfT == null", e.getMessage());
        } finally {
            prefser.remove(key
            );
        }
    }

    public void testFromSharedPreferencesShouldThrowAnExceptionWhenPreferencesAreNull() {
        // given
        SharedPreferences sharedPreferences = null;

        try {
            // when
            prefser.from(sharedPreferences);
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("sharedPreferences == null", e.getMessage());
        }
    }

    public void testPutShouldThrowAnExceptionWhenKeyIsNullForPut() {
        // given
        String key = null;
        String value = "someValue";

        try {
            // when
            prefser.put(key, value);
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("key == null", e.getMessage());
        }
    }

    public void testPutShouldThrowAnExceptionWhenValueIsNullForPut() {
        // given
        String key = "someKey";
        String value = null;

        try {
            // when
            prefser.put(key, value);
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("value == null", e.getMessage());
        }
    }

    public void testPutShouldThrowAnExceptionWhenKeyIsNullForRemove() {
        // given
        String key = null;

        try {
            // when
            prefser.remove(key);
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("key == null", e.getMessage());
        }
    }
}